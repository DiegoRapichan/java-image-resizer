package com.imageresizer.controller;

import com.imageresizer.model.ImageResponse;
import com.imageresizer.model.ResizeRequest;
import com.imageresizer.service.ImageProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * REST Controller para processamento de imagens
 */
@Slf4j
@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
@Tag(name = "Image Processing", description = "Endpoints para redimensionamento e otimização de imagens")
public class ImageController {

    private final ImageProcessingService imageProcessingService;
    private final ObjectMapper objectMapper;

    public ImageController(ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Processar imagem", description = "Redimensiona, otimiza e aplica transformações na imagem")
    public ResponseEntity<ImageResponse> processImage(
            @Parameter(description = "Arquivo de imagem", required = true)
            @RequestParam("file") MultipartFile file,
            
            @Parameter(description = "Largura desejada (pixels)")
            @RequestParam(value = "width", required = false) Integer width,
            
            @Parameter(description = "Altura desejada (pixels)")
            @RequestParam(value = "height", required = false) Integer height,
            
            @Parameter(description = "Porcentagem de redimensionamento (ex: 50 para 50%)")
            @RequestParam(value = "percentage", required = false) Integer percentage,
            
            @Parameter(description = "Manter proporção", example = "true")
            @RequestParam(value = "keepAspectRatio", defaultValue = "true") Boolean keepAspectRatio,
            
            @Parameter(description = "Qualidade JPEG (1-100)", example = "85")
            @RequestParam(value = "quality", defaultValue = "85") Integer quality,
            
            @Parameter(description = "Formato de saída", example = "jpg")
            @RequestParam(value = "outputFormat", required = false) String outputFormat,
            
            @Parameter(description = "Rotação em graus (90, 180, 270)")
            @RequestParam(value = "rotation", required = false) Integer rotation,
            
            @Parameter(description = "Espelhar horizontal")
            @RequestParam(value = "flipHorizontal", defaultValue = "false") Boolean flipHorizontal,
            
            @Parameter(description = "Espelhar vertical")
            @RequestParam(value = "flipVertical", defaultValue = "false") Boolean flipVertical,
            
            @Parameter(description = "Converter para preto e branco")
            @RequestParam(value = "grayscale", defaultValue = "false") Boolean grayscale,
            
            @Parameter(description = "Crop X (pixels)")
            @RequestParam(value = "cropX", required = false) Integer cropX,
            
            @Parameter(description = "Crop Y (pixels)")
            @RequestParam(value = "cropY", required = false) Integer cropY,
            
            @Parameter(description = "Crop largura (pixels)")
            @RequestParam(value = "cropWidth", required = false) Integer cropWidth,
            
            @Parameter(description = "Crop altura (pixels)")
            @RequestParam(value = "cropHeight", required = false) Integer cropHeight
    ) {
        log.info("Received image processing request: {}", file.getOriginalFilename());
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ImageResponse.builder()
                            .success(false)
                            .message("File is empty")
                            .build());
        }
        
        ResizeRequest request = ResizeRequest.builder()
                .width(width)
                .height(height)
                .percentage(percentage)
                .keepAspectRatio(keepAspectRatio)
                .quality(quality)
                .outputFormat(outputFormat)
                .rotation(rotation)
                .flipHorizontal(flipHorizontal)
                .flipVertical(flipVertical)
                .grayscale(grayscale)
                .cropX(cropX)
                .cropY(cropY)
                .cropWidth(cropWidth)
                .cropHeight(cropHeight)
                .build();
        
        ImageResponse response = imageProcessingService.processImage(file, request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "Baixar imagem processada", description = "Faz download da imagem processada")
    public ResponseEntity<Resource> downloadImage(
            @Parameter(description = "Nome do arquivo", required = true)
            @PathVariable String fileName
    ) throws IOException {
        log.info("Download request for file: {}", fileName);
        
        File file = imageProcessingService.getProcessedFile(fileName);
        Resource resource = new FileSystemResource(file);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/info/{fileName}")
    @Operation(summary = "Informações da imagem", description = "Retorna metadados da imagem processada")
    public ResponseEntity<ImageInfo> getImageInfo(
            @PathVariable String fileName
    ) throws IOException {
        File file = imageProcessingService.getProcessedFile(fileName);
        
        // Aqui você pode adicionar lógica para extrair metadados da imagem
        ImageInfo info = ImageInfo.builder()
                .fileName(fileName)
                .fileSize(file.length())
                .build();
        
        return ResponseEntity.ok(info);
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica se a API está funcionando")
    public ResponseEntity<HealthResponse> healthCheck() {
        return ResponseEntity.ok(new HealthResponse("Image Resizer API is running", "1.0.0"));
    }

    // DTOs internos
    public record ImageInfo(String fileName, Long fileSize) {
        public static ImageInfoBuilder builder() {
            return new ImageInfoBuilder();
        }
        
        public static class ImageInfoBuilder {
            private String fileName;
            private Long fileSize;
            
            public ImageInfoBuilder fileName(String fileName) {
                this.fileName = fileName;
                return this;
            }
            
            public ImageInfoBuilder fileSize(Long fileSize) {
                this.fileSize = fileSize;
                return this;
            }
            
            public ImageInfo build() {
                return new ImageInfo(fileName, fileSize);
            }
        }
    }
    
    public record HealthResponse(String status, String version) {}
}
