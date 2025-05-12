import json
from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
import subprocess

app = FastAPI()
ollama_url = "http://ollama:11434"

@app.post("/chat")
async def chat(request: Request):
    data = await request.json()
    prompt = data.get("prompt")

    # Serializa o corpo corretamente
    payload = {
        "model": "llama3",
        "prompt": prompt
    }
    
    # Para depuração: log do payload gerado
    print(f"Payload gerado: {json.dumps(payload)}")

    # Executa o subprocesso com curl
    result = subprocess.run(
        [
            "curl", "-s", "-X", "POST", f"{ollama_url}/api/generate",
            "-H", "Content-Type: application/json",
            "-d", json.dumps(payload)
        ],
        capture_output=True,
        text=True,
        encoding="utf-8"
    )

    # Verifica se o subprocesso foi bem-sucedido
    if result.returncode != 0:
        return JSONResponse(status_code=500, content={"error": "Erro ao chamar a IA", "details": result.stderr})

    # Depuração da resposta bruta
    print(f"Resposta bruta da IA: {result.stdout}")

    # Variável para acumular a resposta
    resposta_final = ""

    # Divide a resposta bruta em várias partes, caso tenha vindo fragmentada
    try:
        for line in result.stdout.strip().splitlines():
            parsed_response = json.loads(line.strip())
            print(f"Resposta JSON recebida: {parsed_response}")

            # Concatenar as partes de resposta
            resposta_final += parsed_response.get("response", "")

            # Verificar se a resposta está completa
            if parsed_response.get("done", False):
                break

    except json.JSONDecodeError:
        print(f"Erro ao analisar a resposta JSON: {result.stdout}")
        return JSONResponse(status_code=500, content={"error": "Erro ao processar resposta JSON da IA"})

    # Verifica se a resposta final está vazia
    if not resposta_final:
        return JSONResponse(status_code=500, content={"error": "Nenhuma resposta válida recebida da IA"})

    return JSONResponse(content={"response": resposta_final})
