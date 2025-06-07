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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import com.google.gson.*;

public class Chat implements AllMetodhs{

    @FXML private ScrollPane scrollPane;
    @FXML private TextArea txtPergunta;
    @FXML private VBox chatContainer;

    
    @FXML
    private void onEnviarClick(){
        String pergunta = txtPergunta.getText();
        txtPergunta.clear();
        Label perguntaLabel = new Label("Você: " + pergunta);
        perguntaLabel.getStyleClass().add("chat-message-label");
        chatContainer.getChildren().add(perguntaLabel);


        new Thread(() -> {
            try {
                // Envia a mensagem e recebe a resposta JSON
                String respostaJson = enviarMensagem(pergunta);
                
                // Processa o JSON para extrair a resposta desejada
                String resposta = parsearResposta(respostaJson);
                
                // Atualiza a UI com a resposta obtida
                Platform.runLater(() -> {
                    // Cria um label para a resposta e adiciona ao chat
                    Label respostaLabel = new Label("IA: " + resposta);
                    respostaLabel.getStyleClass().add("chat-message-label-ai");
                    chatContainer.getChildren().add(respostaLabel);
                });
            } catch (IOException e) {
                // Em caso de erro, exibe a mensagem de erro
                Platform.runLater(() -> {
                    Label erroLabel = new Label("Erro ao conectar ao servidor: " + e.getMessage());
                    chatContainer.getChildren().add(erroLabel);
                });
            }
        }).start();
    }

    private void adicionarMensagem(String texto, boolean usuario) {
        Label label = new Label(texto);
        label.setWrapText(true);
        label.getStyleClass().addAll("mensagem", usuario ? "mensagem-usuario" : "mensagem-resposta");
        chatContainer.getChildren().add(label);
    }

    private void scrollAutomatico() {
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }

    private String enviarMensagem(String pergunta) throws IOException {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("prompt", pergunta);

        String url = "http://fastapi:8000/chat";

        HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
        conexao.setRequestMethod("POST");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);

        try (OutputStream os = conexao.getOutputStream()) {
            byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                resposta.append(linha).append("\n");
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

    @Override
    public void back(ActionEvent event) {
        ScreenManager.trocarTela(event, ScreenManager.getDashbpardxmlpath());
    }
}
