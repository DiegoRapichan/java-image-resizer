# 🚀 GUIA RÁPIDO - Image Resizer Pro

## ⚡ Quick Start (5 minutos)

### Pré-requisitos
- ✅ Java 17+ ([Download](https://adoptium.net/))
- ✅ Node.js 18+ e npm ([Download](https://nodejs.org/))

---

## 📦 Instalação

### 1. Clonar/Extrair
```bash
# Se baixou compactado
tar -xzf image-resizer.tar.gz
cd image-resizer

# Se clonou do Git
git clone https://github.com/DiegoRapichan/image-resizer.git
cd image-resizer
```

### 2. Backend (Terminal 1)
```bash
cd backend
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```

✅ **Backend rodando:** http://localhost:8080  
📚 **Swagger UI:** http://localhost:8080/swagger-ui.html

### 3. Frontend (Terminal 2)
```bash
cd frontend
npm install
npm run dev
```

✅ **Frontend rodando:** http://localhost:3000

---

## ✨ Testando a Aplicação

### Via Interface Web (RECOMENDADO)

1. Abra http://localhost:3000
2. Arraste uma imagem para a área de upload
3. Ajuste os sliders (largura, altura, qualidade)
4. Escolha opções (formato, rotação, filtros)
5. Clique em "🚀 Processar Imagem"
6. Veja o antes/depois lado a lado
7. Clique em "📥 Baixar Imagem"

### Via Swagger UI

1. Abra http://localhost:8080/swagger-ui.html
2. Expanda `POST /api/images/process`
3. Clique em "Try it out"
4. Faça upload de uma imagem
5. Preencha os parâmetros
6. Execute e veja o resultado

### Via cURL

```bash
# Redimensionar para 800x600
curl -X POST http://localhost:8080/api/images/process \
  -F "file=@sua-imagem.jpg" \
  -F "width=800" \
  -F "height=600" \
  -F "quality=85"
```

---

## 🎨 Recursos da Interface

### Drag & Drop
- Arraste imagens diretamente para o navegador
- Suporta PNG, JPG, WebP, GIF, BMP

### Sliders Interativos
- **Largura:** 100px - 4000px
- **Altura:** 100px - 4000px
- **Qualidade:** 1% - 100%

### Transformações
- ✅ Rotação: 0°, 90°, 180°, 270°
- ✅ Formato: JPG, PNG, WebP, GIF
- ✅ Filtro: Preto e Branco
- ✅ Manter Proporção

### Preview Comparativo
- Original vs Processada lado a lado
- Estatísticas em tempo real
- Economia de espaço em %

---

## 📁 Estrutura de Pastas

```
image-resizer/
├── backend/              # API Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── frontend/             # Interface React
│   ├── src/
│   ├── package.json
│   └── README.md
└── README.md             # Documentação principal
```

---

## 🐛 Problemas Comuns

### Backend não inicia
```bash
# Verificar Java
java -version  # Deve ser 17+

# Limpar e reinstalar
cd backend
./mvnw clean install -U
```

### Frontend não inicia
```bash
# Reinstalar dependências
cd frontend
rm -rf node_modules
npm install

# Verificar Node
node -v  # Deve ser 18+
```

### CORS Error
- Certifique-se que o backend está em `localhost:8080`
- E frontend em `localhost:3000`

### Imagem não processa
- Verifique se o arquivo é uma imagem válida
- Tamanho máximo: 20MB
- Formatos suportados: PNG, JPG, WebP, GIF, BMP

---

## 📚 Próximos Passos

1. **Explore a interface**
   - Teste diferentes configurações
   - Experimente os filtros
   - Compare os resultados

2. **Teste a API via Swagger**
   - Experimente diferentes parâmetros
   - Veja os schemas de request/response

3. **Leia a documentação completa**
   - [README.md](README.md) - Visão geral
   - [DOCUMENTATION.md](DOCUMENTATION.md) - Detalhes técnicos

4. **Customize**
   - Adicione novos filtros
   - Personalize a interface
   - Implemente batch processing

---

## 💡 Dicas

- Use qualidade 85% para ótimo balanço tamanho/qualidade
- WebP geralmente é 25-35% menor que JPG
- Manter proporção evita distorções
- Grayscale reduz significativamente o tamanho

---

## 🆘 Suporte

Problemas? 
- 📧 direrapichan@gmail.com
- 🐛 [GitHub Issues](https://github.com/DiegoRapichan/image-resizer/issues)

---

**Desenvolvido com ❤️ por Diego Rapichan**

**Bom uso! 🚀**
