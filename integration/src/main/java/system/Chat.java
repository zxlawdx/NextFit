package system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import com.google.gson.*;

public class Chat {

    @FXML private TextArea txtPergunta;
    @FXML private TextArea txtResposta;

    @FXML
    private void onEnviarClick() {
        String pergunta = txtPergunta.getText();

        new Thread(() -> {
            try {
                // Envia a mensagem e recebe a resposta JSON
                String respostaJson = enviarMensagem(pergunta);
                
                // Processa o JSON para extrair a resposta desejada
                String resposta = parsearResposta(respostaJson);
                
                // Atualiza a UI com a resposta obtida
                Platform.runLater(() -> txtResposta.setText(resposta));
            } catch (IOException e) {
                // Em caso de erro, exibe a mensagem de erro
                Platform.runLater(() -> txtResposta.setText("Erro ao conectar ao servidor: " + e.getMessage()));
            }
        }).start();
    }

    // Método para enviar a mensagem para a API FastAPI
    private String enviarMensagem(String pergunta) throws IOException {
        // Cria o corpo da requisição em formato JSON
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("prompt", pergunta);

        // URL do servidor FastAPI
        String url = "http://fastapi:8000/chat";

        // Configura a conexão HTTP
        HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
        conexao.setRequestMethod("POST");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);

        // Envia os dados no corpo da requisição
        try (OutputStream os = conexao.getOutputStream()) {
            byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Verifica o código de status da resposta
        int statusCode = conexao.getResponseCode();
        if (statusCode != 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = br.readLine()) != null) {
                    resposta.append(linha).append("\n");
                }
                throw new IOException("Erro ao conectar à API: " + statusCode + " - " + resposta.toString());
            }
        }

        // Lê a resposta do servidor
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                resposta.append(linha).append("\n"); // Preserva a estrutura NDJSON
            }
            return resposta.toString();
        }
    }

    
    private String parsearResposta(String jsonND) {
        StringBuilder respostaCompleta = new StringBuilder();
    
        try (BufferedReader reader = new BufferedReader(new StringReader(jsonND))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;
    
                JsonElement jsonElement = JsonParser.parseString(linha);
                if (!jsonElement.isJsonObject()) continue;
    
                JsonObject obj = jsonElement.getAsJsonObject();
    
                if (obj.has("response")) {
                    respostaCompleta.append(obj.get("response").getAsString());
                }
    
                if (obj.has("done") && obj.get("done").getAsBoolean()) {
                    break;
                }
            }
        } catch (Exception e) {
            return "Erro ao processar resposta da IA.";
        }
    
        return respostaCompleta.toString().trim();
    }
    

}