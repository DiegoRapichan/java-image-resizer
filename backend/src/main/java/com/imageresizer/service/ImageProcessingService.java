package com.imageresizer.service;

import com.imageresizer.model.ImageFormat;
import com.imageresizer.model.ImageResponse;
import com.imageresizer.model.ResizeRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Canvas;
import net.coobird.thumbnailator.geometry.Positions;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Service para processamento de imagens
 */
@Slf4j
@Service
public class ImageProcessingService {

    private final String OUTPUT_DIR = "output/";

    public ImageProcessingService() {
        createOutputDirectory();
    }

    /**
     * Processa imagem com base nos parâmetros
     */
    public ImageResponse processImage(MultipartFile file, ResizeRequest request) {
        try {
            log.info("Processing image: {}", file.getOriginalFilename());
            
            // Lê imagem original
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            long originalSize = file.getSize();
            
            // Processa imagem
            BufferedImage processedImage = applyTransformations(originalImage, request);
            
            // Determina formato de saída
            ImageFormat outputFormat = determineOutputFormat(file.getOriginalFilename(), request.getOutputFormat());
            
            // Salva imagem processada
            String outputFileName = generateOutputFileName(file.getOriginalFilename(), outputFormat);
            Path outputPath = Paths.get(OUTPUT_DIR + outputFileName);
            
            saveImage(processedImage, outputPath.toFile(), outputFormat, request.getQuality());
            
            long processedSize = Files.size(outputPath);
            double compressionRatio = calculateCompressionRatio(originalSize, processedSize);
            
            log.info("Image processed successfully: {} -> {} ({}% reduction)",
                    file.getOriginalFilename(), outputFileName, String.format("%.1f", compressionRatio));
            
            return ImageResponse.builder()
                    .success(true)
                    .message("Image processed successfully")
                    .originalFileName(file.getOriginalFilename())
                    .originalWidth(originalWidth)
                    .originalHeight(originalHeight)
                    .originalSizeBytes(originalSize)
                    .processedFileName(outputFileName)
                    .processedWidth(processedImage.getWidth())
                    .processedHeight(processedImage.getHeight())
                    .processedSizeBytes(processedSize)
                    .compressionRatio(compressionRatio)
                    .downloadUrl("/download/" + outputFileName)  
                    .build();
                    
        } catch (Exception e) {
            log.error("Error processing image", e);
            return ImageResponse.builder()
                    .success(false)
                    .message("Error processing image")
                    .errorDetails(e.getMessage())
                    .build();
        }
    }

    /**
     * Aplica todas as transformações à imagem
     */
    private BufferedImage applyTransformations(BufferedImage image, ResizeRequest request) throws IOException {
        BufferedImage result = image;
        
        // 1. Crop primeiro (se solicitado)
        if (request.getCropX() != null && request.getCropWidth() != null) {
            result = cropImage(result, request);
        }
        
        // 2. Resize
        if (request.getWidth() != null || request.getHeight() != null || request.getPercentage() != null) {
            result = resizeImage(result, request);
        }
        
        // 3. Rotação
        if (request.getRotation() != null && request.getRotation() != 0) {
            result = rotateImage(result, request.getRotation());
        }
        
        // 4. Flip
        if (Boolean.TRUE.equals(request.getFlipHorizontal())) {
            result = Scalr.rotate(result, Scalr.Rotation.FLIP_HORZ);
        }
        if (Boolean.TRUE.equals(request.getFlipVertical())) {
            result = Scalr.rotate(result, Scalr.Rotation.FLIP_VERT);
        }
        
        // 5. Grayscale
        if (Boolean.TRUE.equals(request.getGrayscale())) {
            result = convertToGrayscale(result);
        }
        
        return result;
    }

    /**
     * Redimensiona imagem
     */
    private BufferedImage resizeImage(BufferedImage image, ResizeRequest request) throws IOException {
        int targetWidth;
        int targetHeight;
        
        // Redimensionar por porcentagem
        if (request.getPercentage() != null) {
            double scale = request.getPercentage() / 100.0;
            targetWidth = (int) (image.getWidth() * scale);
            targetHeight = (int) (image.getHeight() * scale);
        } else {
            targetWidth = request.getWidth() != null ? request.getWidth() : image.getWidth();
            targetHeight = request.getHeight() != null ? request.getHeight() : image.getHeight();
        }
        
        // Manter proporção
        if (Boolean.TRUE.equals(request.getKeepAspectRatio())) {
            return Thumbnails.of(image)
                    .size(targetWidth, targetHeight)
                    .asBufferedImage();
        } else {
            return Thumbnails.of(image)
                    .forceSize(targetWidth, targetHeight)
                    .asBufferedImage();
        }
    }

    /**
     * Recorta imagem
     */
    private BufferedImage cropImage(BufferedImage image, ResizeRequest request) {
        int x = request.getCropX();
        int y = request.getCropY();
        int width = Math.min(request.getCropWidth(), image.getWidth() - x);
        int height = Math.min(request.getCropHeight(), image.getHeight() - y);
        
        return image.getSubimage(x, y, width, height);
    }

    /**
     * Rotaciona imagem
     */
    private BufferedImage rotateImage(BufferedImage image, int degrees) {
        Scalr.Rotation rotation;
        switch (degrees) {
            case 90:
                rotation = Scalr.Rotation.CW_90;
                break;
            case 180:
                rotation = Scalr.Rotation.CW_180;
                break;
            case 270:
                rotation = Scalr.Rotation.CW_270;
                break;
            default:
                return image;
        }
        return Scalr.rotate(image, rotation);
    }

    /**
     * Converte para preto e branco
     */
    private BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayscale = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = grayscale.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return grayscale;
    }

    /**
     * Salva imagem com qualidade especificada
     */
    private void saveImage(BufferedImage image, File output, ImageFormat format, Integer quality) throws IOException {
        if (format == ImageFormat.JPG || format == ImageFormat.JPEG) {
            // JPEG com controle de qualidade
            float jpegQuality = quality != null ? quality / 100f : 0.85f;
            Thumbnails.of(image)
                    .scale(1.0)
                    .outputQuality(jpegQuality)
                    .outputFormat(format.getExtension())
                    .toFile(output);
        } else {
            // Outros formatos
            ImageIO.write(image, format.getExtension(), output);
        }
    }

    /**
     * Retorna arquivo processado para download
     */
    public File getProcessedFile(String fileName) throws IOException {
        Path filePath = Paths.get(OUTPUT_DIR + fileName);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + fileName);
        }
        return filePath.toFile();
    }

    /**
     * Determina formato de saída
     */
    private ImageFormat determineOutputFormat(String originalFileName, String requestedFormat) {
        if (requestedFormat != null && !requestedFormat.isEmpty()) {
            return ImageFormat.fromExtension(requestedFormat);
        }
        
        // Mantém formato original
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return ImageFormat.fromExtension(extension);
    }

    /**
     * Gera nome único para arquivo de saída
     */
    private String generateOutputFileName(String originalFileName, ImageFormat format) {
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return baseName + "_" + uniqueId + "." + format.getExtension();
    }

    /**
     * Calcula taxa de compressão
     */
    private double calculateCompressionRatio(long originalSize, long processedSize) {
        if (originalSize == 0) return 0;
        return ((originalSize - processedSize) / (double) originalSize) * 100;
    }

    /**
     * Cria diretório de saída
     */
    private void createOutputDirectory() {
        try {
            Path outputPath = Paths.get(OUTPUT_DIR);
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
                log.info("Output directory created: {}", OUTPUT_DIR);
            }
        } catch (IOException e) {
            log.error("Failed to create output directory", e);
        }
    }
}
