package com.imageresizer.model;

/**
 * Formatos de imagem suportados
 */
public enum ImageFormat {
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    WEBP("webp", "image/webp"),
    GIF("gif", "image/gif"),
    BMP("bmp", "image/bmp");

    private final String extension;
    private final String mimeType;

    ImageFormat(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static ImageFormat fromExtension(String ext) {
        String cleanExt = ext.toLowerCase().replace(".", "");
        for (ImageFormat format : values()) {
            if (format.extension.equals(cleanExt)) {
                return format;
            }
        }
        return PNG; // Default
    }
}
