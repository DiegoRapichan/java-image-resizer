import React, { useState, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import axios from "axios";
import { motion, AnimatePresence } from "framer-motion";
import { ImageResponse } from "../types";

// ⚠️ Ajuste para o endpoint completo da API
const API_URL = import.meta.env.VITE_API_URL; // deve ser: https://java-image-resizer.onrender.com/api/images

export default function ImageResizer() {
  const [originalFile, setOriginalFile] = useState<File | null>(null);
  const [originalPreview, setOriginalPreview] = useState<string>("");
  const [processedPreview, setProcessedPreview] = useState<string>("");
  const [response, setResponse] = useState<ImageResponse | null>(null);
  const [loading, setLoading] = useState(false);

  const [width, setWidth] = useState<number>(800);
  const [height, setHeight] = useState<number>(600);
  const [quality, setQuality] = useState<number>(85);
  const [keepRatio, setKeepRatio] = useState(true);
  const [rotation, setRotation] = useState<number>(0);
  const [grayscale, setGrayscale] = useState(false);
  const [format, setFormat] = useState<string>("jpg");

  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      setOriginalFile(file);
      setOriginalPreview(URL.createObjectURL(file));
      setProcessedPreview("");
      setResponse(null);

      const img = new Image();
      img.onload = () => {
        setWidth(img.width);
        setHeight(img.height);
      };
      img.src = URL.createObjectURL(file);
    }
  }, []);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: { "image/*": [".png", ".jpg", ".jpeg", ".webp", ".gif", ".bmp"] },
    maxFiles: 1,
  });

  const processImage = async () => {
    if (!originalFile) return;

    setLoading(true);
    const formData = new FormData();
    formData.append("file", originalFile);
    formData.append("width", width.toString());
    formData.append("height", height.toString());
    formData.append("keepAspectRatio", keepRatio.toString());
    formData.append("quality", quality.toString());
    formData.append("outputFormat", format);
    if (rotation !== 0) formData.append("rotation", rotation.toString());
    if (grayscale) formData.append("grayscale", "true");

    try {
      // ⚠️ URL ajustada
      const res = await axios.post<ImageResponse>(
        `${API_URL}/process`,
        formData,
      );
      setResponse(res.data);

      if (res.data.success && res.data.downloadUrl) {
        const imageRes = await axios.get(
          `${API_URL}${res.data.downloadUrl.replace("/api/images", "")}`,
          {
            responseType: "blob",
          },
        );
        setProcessedPreview(URL.createObjectURL(imageRes.data));
      }
    } catch (error) {
      console.error("Error:", error);
      alert("Erro ao processar imagem");
    } finally {
      setLoading(false);
    }
  };

  const downloadImage = () => {
    if (response?.downloadUrl) {
      window.open(
        `${API_URL}${response.downloadUrl.replace("/api/images", "")}`,
        "_blank",
      );
    }
  };

  const formatBytes = (bytes: number) => {
    if (bytes === 0) return "0 Bytes";
    const k = 1024;
    const sizes = ["Bytes", "KB", "MB"];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round((bytes / Math.pow(k, i)) * 100) / 100 + " " + sizes[i];
  };

  // ✅ O resto do componente continua igual (UI, drag & drop, previews, controles, botões)
  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-blue-50 to-pink-50 p-8">
      {/* ...restante do JSX igual... */}
    </div>
  );
}
