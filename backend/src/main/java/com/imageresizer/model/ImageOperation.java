package com.imageresizer.model;

/**
 * Tipos de operações suportadas
 */
public enum ImageOperation {
    RESIZE("Redimensionar"),
    CROP("Recortar"),
    ROTATE("Rotacionar"),
    FLIP("Espelhar"),
    GRAYSCALE("Preto e Branco"),
    BLUR("Desfocar"),
    SHARPEN("Nitidez"),
    CONVERT("Converter Formato");

    private final String description;

    ImageOperation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
