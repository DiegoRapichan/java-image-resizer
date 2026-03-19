# 📷 Image Resizer & Optimizer Pro

Sistema completo para redimensionamento, otimização e transformação de imagens. Spring Boot + React + Processamento Avançado.

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.2-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.3-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![Tailwind](https://img.shields.io/badge/Tailwind-3.4-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)](https://tailwindcss.com/)

**🌐 [Demo ao Vivo](https://java-image-resizer.vercel.app/)** | **⚡ [Quick Start](QUICK_START.md)** 


---

## 📋 Sobre o Projeto

API REST profissional para processamento de imagens com interface web moderna e interativa. Desenvolvido com foco em performance, qualidade e experiência do usuário.

**Principais características:**

- 🎨 **Interface Premium** - React com Drag & Drop, sliders interativos e preview em tempo real
- ⚡ **Processamento Rápido** - Thumbnailator + imgscalr para máxima qualidade
- 🔄 **Múltiplas Operações** - Resize, crop, rotate, flip, filtros e mais
- 📊 **Estatísticas Detalhadas** - Economia de espaço, comparação antes/depois
- 🎯 **API REST Completa** - Swagger UI para testes
- 💎 **Código Limpo** - SOLID, design patterns, documentação completa

---

## ✨ Funcionalidades

### 🖼️ Operações de Imagem

| Operação              | Descrição                         | Status |
| --------------------- | --------------------------------- | ------ |
| **Redimensionar**     | Por dimensões (px) ou porcentagem | ✅     |
| **Manter Proporção**  | Aspect ratio automático           | ✅     |
| **Qualidade JPEG**    | Controle 1-100%                   | ✅     |
| **Converter Formato** | PNG, JPG, WebP, GIF, BMP          | ✅     |
| **Rotação**           | 90°, 180°, 270°                   | ✅     |
| **Espelhar**          | Horizontal e Vertical             | ✅     |
| **Preto e Branco**    | Conversão grayscale               | ✅     |
| **Recorte (Crop)**    | Coordenadas personalizadas        | ✅     |

### 🎨 Interface Moderna (FIRULAS!)

- ✅ **Drag & Drop** - Arraste imagens diretamente
- ✅ **Sliders Interativos** - Ajuste dimensões em tempo real
- ✅ **Preview Lado a Lado** - Compare original vs processada
- ✅ **Animações Suaves** - Framer Motion para transições
- ✅ **Estatísticas Visuais** - Economia de espaço em %
- ✅ **Design Gradient** - Interface moderna e atrativa
- ✅ **Responsivo** - Funciona em desktop, tablet e mobile

### 📊 Recursos Técnicos

- ✅ **API REST** - Documentação Swagger completa
- ✅ **Processamento Batch** - Múltiplas imagens (futuro)
- ✅ **Formatos Modernos** - Suporte WebP
- ✅ **Alta Qualidade** - Algoritmos profissionais
- ✅ **Tratamento de Erros** - Mensagens claras
- ✅ **CORS Configurado** - Pronto para produção

---

## 🚀 Tecnologias

### Backend

| Tecnologia            | Versão | Uso                                       |
| --------------------- | ------ | ----------------------------------------- |
| **Java**              | 17     | Linguagem de programação                  |
| **Spring Boot**       | 3.2.2  | Framework backend                         |
| **Thumbnailator**     | 0.4.20 | Processamento de imagens (fácil)          |
| **imgscalr**          | 4.2    | Alta qualidade (algoritmos profissionais) |
| **WebP ImageIO**      | 0.1.6  | Suporte WebP                              |
| **SpringDoc OpenAPI** | 2.3.0  | Documentação Swagger                      |
| **Lombok**            | -      | Redução de boilerplate                    |

### Frontend

| Tecnologia         | Versão | Uso                       |
| ------------------ | ------ | ------------------------- |
| **React**          | 18     | Biblioteca UI             |
| **TypeScript**     | 5.3    | Type safety               |
| **Vite**           | 5.0    | Build tool moderna        |
| **Tailwind CSS**   | 3.4    | Estilização utility-first |
| **Framer Motion**  | 11.0   | Animações suaves          |
| **React Dropzone** | 14.2   | Drag & drop de arquivos   |
| **Axios**          | 1.6    | Cliente HTTP              |

---

## 🏗️ Arquitetura

### Fluxo de Processamento

```
┌─────────────────────────────────────────┐
│         Frontend React (Port 3000)      │
│   ├── Drag & Drop                       │
│   ├── Sliders Interativos               │
│   ├── Preview Lado a Lado               │
│   └── Framer Motion Animations          │
└──────────────┬──────────────────────────┘
               │ HTTP REST
               ▼
┌─────────────────────────────────────────┐
│      Backend Spring Boot (Port 8080)    │
│   ├── ImageController (REST API)        │
│   ├── ImageProcessingService            │
│   │   ├── Resize (Thumbnailator)        │
│   │   ├── Crop (SubImage)               │
│   │   ├── Rotate (imgscalr)             │
│   │   ├── Flip (imgscalr)               │
│   │   └── Grayscale (Graphics2D)        │
│   └── Exception Handlers                │
└─────────────────────────────────────────┘
               │
               ▼
        ┌──────────────┐
        │   Arquivo    │
        │  Otimizado   │
        └──────────────┘
```

### Princípios de Design

- **Single Responsibility** - Cada método faz uma transformação
- **Open/Closed** - Fácil adicionar novos filtros
- **Separation of Concerns** - Controller → Service → Utils
- **DRY** - Reutilização de código
- **Clean Code** - Nomes descritivos, métodos pequenos

---

## 🔧 Instalação e Execução

### Pré-requisitos

```bash
# Verificar versões
java -version    # Java 17+
node -v          # Node.js 18+
npm -v           # npm 9+
```

### Quick Start

#### 1️⃣ Clone o repositório

```bash
git clone https://github.com/DiegoRapichan/image-resizer.git
cd image-resizer
```

#### 2️⃣ Backend (Terminal 1)

```bash
cd backend
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```

✅ Backend: `http://localhost:8080`  
📚 Swagger: `http://localhost:8080/swagger-ui.html`

#### 3️⃣ Frontend (Terminal 2)

```bash
cd frontend
npm install
npm run dev
```

✅ Frontend: `http://localhost:3000`

---

## 📚 Endpoints da API

### Base URL

```
http://localhost:8080/api/images
```

### Endpoints Disponíveis

#### 🔄 Processar Imagem

```http
POST /process
Content-Type: multipart/form-data

Parameters:
- file: Arquivo de imagem (max 20MB)
- width: Largura em pixels (opcional)
- height: Altura em pixels (opcional)
- percentage: Porcentagem de redimensionamento (opcional)
- keepAspectRatio: Manter proporção (default: true)
- quality: Qualidade JPEG 1-100 (default: 85)
- outputFormat: png, jpg, webp, gif (opcional)
- rotation: 90, 180, 270 (opcional)
- flipHorizontal: true/false (default: false)
- flipVertical: true/false (default: false)
- grayscale: true/false (default: false)
- cropX, cropY, cropWidth, cropHeight (opcional)
```

**Response:**

```json
{
  "success": true,
  "message": "Image processed successfully",
  "originalFileName": "photo.jpg",
  "originalWidth": 4000,
  "originalHeight": 3000,
  "originalSizeBytes": 5242880,
  "processedFileName": "photo_a1b2c3d4.jpg",
  "processedWidth": 800,
  "processedHeight": 600,
  "processedSizeBytes": 245760,
  "compressionRatio": 95.3,
  "downloadUrl": "/api/images/download/photo_a1b2c3d4.jpg"
}
```

#### ⬇️ Download

```http
GET /download/{fileName}
```

#### 🏥 Health Check

```http
GET /health
```

### 📖 Documentação Interativa

Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 💡 Exemplos de Uso

### Exemplo 1: Redimensionar para 800x600

**Input:** `photo.jpg` (4000x3000, 5MB)

**Request:**

```bash
curl -X POST http://localhost:8080/api/images/process \
  -F "file=@photo.jpg" \
  -F "width=800" \
  -F "height=600" \
  -F "keepAspectRatio=true" \
  -F "quality=85"
```

**Output:** `photo_abc123.jpg` (800x600, 850KB)  
**Economia:** 83%

### Exemplo 2: Converter para WebP + Grayscale

**Request:**

```bash
curl -X POST http://localhost:8080/api/images/process \
  -F "file=@photo.jpg" \
  -F "outputFormat=webp" \
  -F "grayscale=true" \
  -F "quality=90"
```

**Output:** `photo_xyz789.webp` (P&B, alta qualidade)

### Exemplo 3: Rotação + Redimensionar 50%

**Request:**

```bash
curl -X POST http://localhost:8080/api/images/process \
  -F "file=@photo.jpg" \
  -F "percentage=50" \
  -F "rotation=90"
```

---

## 📁 Estrutura do Projeto

```
image-resizer/
│
├── backend/                          # 🔧 Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/imageresizer/
│   │   │   │   ├── config/           # CORS, Swagger
│   │   │   │   ├── controller/       # REST Controllers
│   │   │   │   ├── service/          # Lógica de processamento
│   │   │   │   ├── model/            # DTOs e Enums
│   │   │   │   ├── exception/        # Exception Handlers
│   │   │   │   └── ImageResizerApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── input/                        # Imagens de exemplo
│   ├── output/                       # Imagens processadas
│   ├── pom.xml
│   └── README.md
│
├── frontend/                         # 🎨 Frontend React
│   ├── src/
│   │   ├── components/
│   │   │   └── ImageResizer.tsx      # Componente principal
│   │   ├── types/
│   │   │   └── index.ts              # TypeScript types
│   │   ├── App.tsx
│   │   ├── main.tsx
│   │   └── index.css
│   ├── package.json
│   ├── vite.config.ts
│   ├── tailwind.config.js
│   └── README.md
│
├── README.md                         # 📖 Este arquivo
├── DOCUMENTATION.md                  # 📚 Documentação técnica
└── QUICK_START.md                   # ⚡ Guia rápido
```

---

## 🎨 Screenshots

- **Página Principal**  
  ![Página Principal](/screenshots/image-resize-home.PNG)

- **Upload de Imagem**  
  ![Upload de Imagem](/screenshots/image-resize-upload.PNG)

- **Resultado Processado**  
  ![Resultado Processado](/screenshots/image-resize-result.PNG)

---

## 🧪 Testes

### Backend

```bash
cd backend
./mvnw test
```

### Frontend

```bash
cd frontend
npm test
```

---

## 🚀 Deploy

### Backend - Railway/Heroku

```bash
# Railway
railway up

# Heroku
heroku create image-resizer-api
git push heroku main
```

### Frontend - Vercel/Netlify

```bash
# Vercel
cd frontend
npm run build
vercel --prod

```

---

## 🎯 Roadmap

### Próximas Features

- [ ] Batch processing (múltiplas imagens)
- [ ] Filtros avançados (blur, sharpen, sepia)
- [ ] Marca d'água automática
- [ ] Histórico de processamento
- [ ] Compressão inteligente por IA
- [ ] API de metadados EXIF
- [ ] Conversão de formatos RAW
- [ ] Integração com serviços de nuvem

---

## 🤝 Contribuindo

Contribuições são bem-vindas!

1. Fork o projeto
2. Crie uma branch: `git checkout -b feature/NovaFeature`
3. Commit: `git commit -m 'Adiciona NovaFeature'`
4. Push: `git push origin feature/NovaFeature`
5. Abra um Pull Request

---

## 👨‍💻 Autor

**Diego Colombari Rapichan**

Desenvolvedor Full Stack especializado em Java/Spring Boot e React.

- 🌐 GitHub: [@DiegoRapichan](https://github.com/DiegoRapichan)
- 💼 LinkedIn: [Diego Rapichan](https://linkedin.com/in/diego-rapichan)
- 📧 Email: direrapichan@gmail.com
- 📍 Localização: Apucarana, PR - Brasil

### 🎓 Formação

- **Bacharelado** em Sistemas de Informação
- **Pós-graduação** em Desenvolvimento OO com Java
- **Pós-graduação** em Desenvolvimento de Aplicações Web (em andamento)
- **Pós-graduação** em IA e Machine Learning (em andamento)

### 💼 Outros Projetos

- 🔄 [**File Converter API**](https://github.com/DiegoRapichan/java-file-converter) - Java + Spring + Angular  
  Sistema de conversão entre 7 formatos (CSV, JSON, XML, Excel, PDF)
- 🏭 [**Autoflex Inventory System**](https://github.com/DiegoRapichan/autoflex-inventory-system) - Spring + React + PostgreSQL  
  Sistema de gestão de estoque com sugestão inteligente de produção
- 📝 [**Task Manager API**](https://github.com/DiegoRapichan/task-manager-api) - Node.js + Express  
  API REST para gerenciamento de tarefas com JWT

---

## 📜 Licença

Este projeto está sob a licença MIT.

```
MIT License

Copyright (c) 2026 Diego Colombari Rapichan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files...
```

---

## 🙏 Agradecimentos

- **Thumbnailator Team** - Biblioteca excelente para processamento
- **imgscalr Team** - Algoritmos de alta qualidade
- **Spring Boot Team** - Framework incrível
- **React Team** - UI library moderna
- **Tailwind CSS** - Utility-first CSS framework
- **Framer Motion** - Animações suaves

---

## 📞 Suporte

Encontrou um bug? Tem uma sugestão?

- 🐛 [Abra uma issue](https://github.com/DiegoRapichan/java-image-resizer/issues)
- 💬 [Inicie uma discussão](https://github.com/DiegoRapichan/java-image-resizer/discussions)
- 📧 Email: direrapichan@gmail.com

---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela!**

**Desenvolvido por [Diego Rapichan](https://github.com/DiegoRapichan)**

[⬆ Voltar ao topo](#-image-resizer--optimizer-pro)

</div>
