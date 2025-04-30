from fastapi import FastAPI, Request
import subprocess
from fastapi.responses import JSONResponse

app = FastAPI()

@app.post("/chat")
async def chat(request: Request):
    # Recebe o JSON enviado pelo cliente (com o 'prompt')
    data = await request.json()
    prompt = data.get("prompt")

    # Chama o Ollama com o modelo local 'mistral'
    result = subprocess.run(
        ["ollama", "run", "mistral", prompt],
        capture_output=True,
        text=True,
        encoding="utf-8"  # garante leitura UTF-8 do subprocesso
    )

    # Retorna a resposta com codificação UTF-8 explícita
    response_data = {
        "response": result.stdout.strip()
    }
    return JSONResponse(
        content=response_data,
        media_type="application/json; charset=utf-8"  # CORREÇÃO aqui
    )
