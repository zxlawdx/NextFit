package system;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.*;

public class LocalAIClient {

    private static final String API_URL = "http://localhost:11434/api/generate"; // Ollama local
    private static final String MODEL_NAME = "mistral"; // Modelo leve para IA local

    public static String enviarMensagem(String mensagemUsuario) throws IOException {
        // Monta JSON manualmente
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("model", MODEL_NAME);
        jsonRequest.addProperty("prompt", mensagemUsuario);
        jsonRequest.addProperty("stream", false); // stream false para facilitar leitura no Java

        String jsonInput = new Gson().toJson(jsonRequest);

        // Conexão HTTP
        URL url = new URL(API_URL);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("POST");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);

        try (OutputStream os = conexao.getOutputStream()) {
            os.write(jsonInput.getBytes("utf-8"));
        }

        int statusCode = conexao.getResponseCode();
        if (statusCode != 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder erro = new StringBuilder();
                String linha;
                while ((linha = br.readLine()) != null) {
                    erro.append(linha.trim());
                }
                throw new IOException("Erro na API local: " + statusCode + " - " + erro.toString());
            }
        }

        // Processar resposta JSON
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                resposta.append(linha.trim());
            }

            JsonObject obj = JsonParser.parseString(resposta.toString()).getAsJsonObject();
            if (!obj.has("response")) {
                return "Erro: resposta inesperada da IA.";
            }
            return obj.get("response").getAsString();
        }
    }
}
