// Types para o Image Resizer

export interface ResizeRequest {
  width?: number;
  height?: number;
  percentage?: number;
  keepAspectRatio?: boolean;
  quality?: number;
  outputFormat?: string;
  rotation?: number;
  flipHorizontal?: boolean;
  flipVertical?: boolean;
  grayscale?: boolean;
  cropX?: number;
  cropY?: number;
  cropWidth?: number;
  cropHeight?: number;
}

export interface ImageResponse {
  success: boolean;
  message: string;
  originalFileName?: string;
  originalWidth?: number;
  originalHeight?: number;
  originalSizeBytes?: number;
  processedFileName?: string;
  processedWidth?: number;
  processedHeight?: number;
  processedSizeBytes?: number;
  compressionRatio?: number;
  downloadUrl?: string;
  errorDetails?: string;
}

export type ImageFormat = 'png' | 'jpg' | 'jpeg' | 'webp' | 'gif' | 'bmp';

export interface ProcessingOptions {
  resize: boolean;
  crop: boolean;
  rotate: boolean;
  flip: boolean;
  filters: boolean;
}
