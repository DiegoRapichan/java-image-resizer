package com.imageresizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de redimensionamento de imagem
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResizeRequest {
    
    // Dimensões
    private Integer width;
    private Integer height;
    private Integer percentage;
    
    // Opções
    private Boolean keepAspectRatio = true;
    private Integer quality = 85; // 1-100 para JPEG
    
    // Formato
    private String outputFormat; // png, jpg, webp, gif
    
    // Operações adicionais
    private Integer rotation; // 90, 180, 270
    private Boolean flipHorizontal;
    private Boolean flipVertical;
    private Boolean grayscale;
    
    // Crop
    private Integer cropX;
    private Integer cropY;
    private Integer cropWidth;
    private Integer cropHeight;
}
