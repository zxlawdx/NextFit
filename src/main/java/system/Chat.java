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
                // Substituindo DeepSeekClient por uma implementação com FastAPI e Ollama
                String respostaJson = enviarMensagem(pergunta);
                // Extraímos o texto da resposta do JSON usando o método de parsing
                String resposta = parsearResposta(respostaJson);
                Platform.runLater(() -> txtResposta.setText(resposta));
            } catch (IOException e) {
                Platform.runLater(() -> txtResposta.setText("Erro ao conectar ao servidor: " + e.getMessage()));
            }
        }).start();
    }

    // Método para enviar a pergunta ao servidor FastAPI
    private String enviarMensagem(String pergunta) throws IOException {
        // Preparando a requisição JSON
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("prompt", pergunta);  // Modificando para enviar "prompt"

        // Configuração do URL do servidor FastAPI local
        String url = "http://localhost:8000/chat";  // Alterando para o endereço do seu servidor FastAPI

        // Enviando requisição POST
        HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
        conexao.setRequestMethod("POST");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);
        System.out.println("requisição enviada");
        try (OutputStream os = conexao.getOutputStream()) {
            byte[] input = jsonRequest.toString().getBytes("UTF-8");
            os.write(input, 0, input.length);
        }

        // Recebendo a resposta
        System.out.println("agora vai receber resposta");
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
        System.out.println("agora vai processar a resposta");
        // Processando a resposta do servidor
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                resposta.append(linha.trim());
                System.out.println("a resposta deve sair agora");
            }
            System.out.println(resposta.toString());
            return resposta.toString();
        }
    }

    // Método para parsear a resposta do JSON da API
    private String parsearResposta(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        // Aqui estamos acessando o campo "response" da resposta JSON
        return obj.get("response").getAsString(); // Modificado para acessar "response"
    }
}
