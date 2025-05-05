package system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    @FXML private Label txtResposta;  // Agora está correto com Label

    @FXML
    private void onEnviarClick() {
        String pergunta = txtPergunta.getText();

        new Thread(() -> {
            try {
                String respostaJson = enviarMensagem(pergunta);
                String resposta = parsearResposta(respostaJson);
                Platform.runLater(() -> txtResposta.setText(resposta));  // Label usa setText normalmente
            } catch (IOException e) {
                Platform.runLater(() -> txtResposta.setText("Erro ao conectar ao servidor: " + e.getMessage()));
            }
        }).start();
    }

    private String enviarMensagem(String pergunta) throws IOException {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("prompt", pergunta);

        String url = "http://localhost:8000/chat";

        HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
        conexao.setRequestMethod("POST");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);
        try (OutputStream os = conexao.getOutputStream()) {
            byte[] input = jsonRequest.toString().getBytes("UTF-8");
            os.write(input, 0, input.length);
        }

        int statusCode = conexao.getResponseCode();
        if (statusCode != 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = br.readLine()) != null) {
                    resposta.append(linha.trim());
                }
                throw new IOException("Erro ao conectar à API: " + statusCode + " - " + resposta.toString());
            }
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                resposta.append(linha.trim());
            }
            return resposta.toString();
        }
    }

    private String parsearResposta(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        return obj.get("response").getAsString();
    }
}
