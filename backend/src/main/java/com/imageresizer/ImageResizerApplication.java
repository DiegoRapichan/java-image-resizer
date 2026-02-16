package com.imageresizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Image Resizer API - Spring Boot Application
 * 
 * API REST para redimensionamento e otimização de imagens com features avançadas:
 * - Redimensionar por dimensões ou porcentagem
 * - Manter proporção automático
 * - Ajustar qualidade JPEG
 * - Converter entre formatos (PNG, JPG, WebP, GIF)
 * - Rotação e flip
 * - Crop (recorte)
 * - Filtros (grayscale, blur, sharpen)
 * - Batch processing
 * 
 * @author Diego Rapichan
 * @version 1.0.0
 */
@SpringBootApplication
public class ImageResizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageResizerApplication.class, args);
    }
}
