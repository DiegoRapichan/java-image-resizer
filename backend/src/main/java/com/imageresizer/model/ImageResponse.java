package com.imageresizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de processamento de imagem
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    
    private boolean success;
    private String message;
    
    // Informações da imagem original
    private String originalFileName;
    private Integer originalWidth;
    private Integer originalHeight;
    private Long originalSizeBytes;
    
    // Informações da imagem processada
    private String processedFileName;
    private Integer processedWidth;
    private Integer processedHeight;
    private Long processedSizeBytes;
    
    // Estatísticas
    private Double compressionRatio; // Porcentagem de redução
    private String downloadUrl;
    
    // Erro
    private String errorDetails;
}
