FROM python:3.10-slim

# Evita interações durante instalações de pacotes (útil em imagens menores como slim)
ENV DEBIAN_FRONTEND=noninteractive

# Instala o curl e outras dependências necessárias
RUN apt-get update && apt-get install -y curl

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas os arquivos necessários para instalar as dependências
COPY requirements.txt .

# Instala as dependências
RUN pip install --no-cache-dir -r requirements.txt

# Copia o restante do código
COPY . .



# Comando padrão para iniciar o servidor
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
