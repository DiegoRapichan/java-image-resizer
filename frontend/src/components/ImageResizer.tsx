import React, { useState, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import axios from "axios";
import { motion, AnimatePresence } from "framer-motion";
import { ImageResponse } from "../types";

const API_URL = import.meta.env.VITE_API_URL;

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

  // Drag & Drop
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

  // Processar imagem
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
      const res = await axios.post<ImageResponse>(
        `${API_URL}/process`,
        formData,
      );

      setResponse(res.data);

      // ✅ Corrigido: não duplicar /api/images
      if (res.data.success && res.data.downloadUrl) {
        const fullUrl = res.data.downloadUrl.startsWith("http")
          ? res.data.downloadUrl
          : `${API_URL}${res.data.downloadUrl}`;
        setProcessedPreview(fullUrl);
      }
    } catch (error) {
      console.error("Erro ao processar imagem:", error);
      alert("Erro ao processar imagem");
    } finally {
      setLoading(false);
    }
  };

  const downloadImage = () => {
    if (response?.downloadUrl) {
      const fullUrl = response.downloadUrl.startsWith("http")
        ? response.downloadUrl
        : `${API_URL}${response.downloadUrl}`;
      window.open(fullUrl, "_blank");
    }
  };

  const formatBytes = (bytes: number) => {
    if (bytes === 0) return "0 Bytes";
    const k = 1024;
    const sizes = ["Bytes", "KB", "MB"];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round((bytes / Math.pow(k, i)) * 100) / 100 + " " + sizes[i];
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-blue-50 to-pink-50 p-8">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          className="text-center mb-12"
        >
          <h1 className="text-6xl font-bold mb-4 bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">
            📷 Image Resizer Pro
          </h1>
          <p className="text-xl text-gray-600">
            Redimensione, otimize e transforme suas imagens
          </p>
        </motion.div>

        {/* Upload Area */}
        <motion.div
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.1 }}
        >
          <div
            {...getRootProps()}
            className={`border-4 border-dashed rounded-2xl p-12 text-center cursor-pointer transition-all duration-300 mb-8 ${
              isDragActive
                ? "border-purple-500 bg-purple-50 scale-105"
                : "border-gray-300 hover:border-purple-400 hover:bg-white"
            }`}
          >
            <input {...getInputProps()} />
            <div className="text-6xl mb-4">🖼️</div>
            {isDragActive ? (
              <p className="text-2xl font-semibold text-purple-600">
                Solte a imagem aqui!
              </p>
            ) : (
              <>
                <p className="text-xl font-semibold mb-2">
                  Arraste e solte uma imagem
                </p>
                <p className="text-gray-500">ou clique para selecionar</p>
              </>
            )}
          </div>
        </motion.div>

        {originalFile && (
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            {/* Configurações */}
            <motion.div
              initial={{ opacity: 0, x: -50 }}
              animate={{ opacity: 1, x: 0 }}
              className="bg-white rounded-2xl shadow-2xl p-8"
            >
              <h2 className="text-2xl font-bold mb-6 text-gray-800">
                ⚙️ Configurações
              </h2>
              <div className="space-y-6">
                {/* Dimensões */}
                <div>
                  <label className="block text-sm font-medium mb-2">
                    Largura: {width}px
                  </label>
                  <input
                    type="range"
                    min="100"
                    max="4000"
                    value={width}
                    onChange={(e) => setWidth(Number(e.target.value))}
                    className="w-full h-2 bg-gradient-to-r from-purple-200 to-pink-200 rounded-lg appearance-none cursor-pointer"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">
                    Altura: {height}px
                  </label>
                  <input
                    type="range"
                    min="100"
                    max="4000"
                    value={height}
                    onChange={(e) => setHeight(Number(e.target.value))}
                    className="w-full h-2 bg-gradient-to-r from-purple-200 to-pink-200 rounded-lg appearance-none cursor-pointer"
                  />
                </div>

                {/* Manter proporção */}
                <div className="flex items-center gap-3">
                  <input
                    type="checkbox"
                    checked={keepRatio}
                    onChange={(e) => setKeepRatio(e.target.checked)}
                    className="w-5 h-5 text-purple-600 rounded"
                  />
                  <label className="text-sm font-medium">
                    Manter proporção
                  </label>
                </div>

                {/* Qualidade */}
                <div>
                  <label className="block text-sm font-medium mb-2">
                    Qualidade: {quality}%
                  </label>
                  <input
                    type="range"
                    min="1"
                    max="100"
                    value={quality}
                    onChange={(e) => setQuality(Number(e.target.value))}
                    className="w-full h-2 bg-gradient-to-r from-green-200 to-blue-200 rounded-lg appearance-none cursor-pointer"
                  />
                </div>

                {/* Formato */}
                <div>
                  <label className="block text-sm font-medium mb-2">
                    Formato de Saída
                  </label>
                  <select
                    value={format}
                    onChange={(e) => setFormat(e.target.value)}
                    className="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-purple-500 focus:outline-none"
                  >
                    <option value="jpg">JPEG</option>
                    <option value="png">PNG</option>
                    <option value="webp">WebP</option>
                    <option value="gif">GIF</option>
                  </select>
                </div>

                {/* Rotação */}
                <div>
                  <label className="block text-sm font-medium mb-2">
                    Rotação
                  </label>
                  <div className="flex gap-2">
                    {[0, 90, 180, 270].map((deg) => (
                      <button
                        key={deg}
                        onClick={() => setRotation(deg)}
                        className={`flex-1 py-2 rounded-lg font-medium transition-all ${
                          rotation === deg
                            ? "bg-purple-600 text-white shadow-lg scale-105"
                            : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                        }`}
                      >
                        {deg}°
                      </button>
                    ))}
                  </div>
                </div>

                {/* Filtro */}
                <div className="flex items-center gap-3">
                  <input
                    type="checkbox"
                    checked={grayscale}
                    onChange={(e) => setGrayscale(e.target.checked)}
                    className="w-5 h-5 text-purple-600 rounded"
                  />
                  <label className="text-sm font-medium">Preto e Branco</label>
                </div>

                {/* Botão Processar */}
                <button
                  onClick={processImage}
                  disabled={loading}
                  className="w-full py-4 bg-gradient-to-r from-purple-600 to-pink-600 text-white font-bold rounded-xl shadow-lg hover:shadow-xl transform hover:scale-105 transition-all disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none"
                >
                  {loading ? (
                    <span className="flex items-center justify-center gap-2">
                      <span className="animate-spin">⚙️</span> Processando...
                    </span>
                  ) : (
                    "🚀 Processar Imagem"
                  )}
                </button>
              </div>
            </motion.div>

            {/* Preview */}
            <motion.div
              initial={{ opacity: 0, x: 50 }}
              animate={{ opacity: 1, x: 0 }}
              className="space-y-6"
            >
              {/* Original */}
              <div className="bg-white rounded-2xl shadow-2xl p-6">
                <h3 className="text-lg font-bold mb-4 text-gray-800">
                  📸 Original
                </h3>
                <div className="relative aspect-video bg-gray-100 rounded-xl overflow-hidden">
                  {originalPreview && (
                    <img
                      src={originalPreview}
                      alt="Original"
                      className="w-full h-full object-contain"
                    />
                  )}
                </div>
                {response && (
                  <div className="mt-4 text-sm text-gray-600 space-y-1">
                    <p>
                      📐 {response.originalWidth} x {response.originalHeight}px
                    </p>
                    <p>💾 {formatBytes(response.originalSizeBytes || 0)}</p>
                  </div>
                )}
              </div>

              {/* Processada */}
              <AnimatePresence>
                {processedPreview && response?.success && (
                  <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    exit={{ opacity: 0, y: -20 }}
                    className="bg-white rounded-2xl shadow-2xl p-6"
                  >
                    <h3 className="text-lg font-bold mb-4 text-green-600">
                      ✅ Processada
                    </h3>
                    <div className="relative aspect-video bg-gray-100 rounded-xl overflow-hidden">
                      <img
                        src={processedPreview}
                        alt="Processada"
                        className="w-full h-full object-contain"
                      />
                    </div>
                    <div className="mt-4 space-y-2">
                      <div className="text-sm text-gray-600 space-y-1">
                        <p>
                          📐 {response.processedWidth} x{" "}
                          {response.processedHeight}px
                        </p>
                        <p>
                          💾 {formatBytes(response.processedSizeBytes || 0)}
                        </p>
                        <p className="font-bold text-green-600">
                          💰 Economia: {response.compressionRatio?.toFixed(1)}%
                        </p>
                      </div>
                      <button
                        onClick={downloadImage}
                        className="w-full mt-4 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white font-bold rounded-xl shadow-lg hover:shadow-xl transform hover:scale-105 transition-all"
                      >
                        📥 Baixar Imagem
                      </button>
                    </div>
                  </motion.div>
                )}
              </AnimatePresence>
            </motion.div>
          </div>
        )}
      </div>
    </div>
  );
}
