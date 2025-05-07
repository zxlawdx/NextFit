FROM openjdk:17-slim

# Instala dependências necessárias para JavaFX (GUI)
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgtk-3-0 \
    libxss1 \
    libxxf86vm1 \
    libgl1 \
 && apt-get clean

# Define o diretório de trabalho no container
WORKDIR /app

# Copia todos os arquivos do projeto para dentro do container
COPY . .

# Dá permissão de execução ao script Gradle wrapper
RUN chmod +x ./gradlew

# Executa o app com Gradle

CMD ["./gradlew", "build"]
