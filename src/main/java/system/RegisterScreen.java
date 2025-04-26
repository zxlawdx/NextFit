package system;

import java.io.IOException;
import dbsystem.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class RegisterScreen {

    @FXML private TextField emailField;
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private TextField idadeField;
    @FXML private TextField pesoField;
    @FXML private TextField alturaField;
    @FXML private Button buttonBackToLogin;
    @FXML private Button registerUserButton;

    private DatabaseConnector connector = new DatabaseConnector();

    public void handleCreateAccount(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(RegisterScreen.class.getResource("/system/RegisterScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToLogin(ActionEvent event){
        ScreenManager.trocarTela(event, ScreenManager.getLoginxmlpath());
    }

    public void createAccount(ActionEvent event) {
        String email = emailField.getText();
        String user = userField.getText();
        String password = passwordField.getText();
        String idadeText = idadeField.getText();
        String pesoText = pesoField.getText();
        String alturaText = alturaField.getText();

        // Verifica se os campos obrigatórios foram preenchidos
        if (email.isEmpty() || user.isEmpty() || password.isEmpty() || idadeText.isEmpty() || pesoText.isEmpty() || alturaText.isEmpty()) {
            System.out.println("❌ Preencha todos os campos!");
            return;
        }

        int idade;
        double peso;
        double altura;

        try {
            idade = Integer.parseInt(idadeText);
            peso = Double.parseDouble(pesoText);
            altura = Double.parseDouble(alturaText);
        } catch (NumberFormatException e) {
            System.out.println("❌ Idade, peso e altura precisam ser números válidos!");
            return;
        }

        if (!connector.conectar()) {
            System.out.println("Erro ao conectar com o banco de dados.");
            return;
        }

        try {
            boolean sucesso = connector.registrarUsuario(user, email, password, idade, peso, altura);
            if (sucesso) {
                System.out.println("✅ Conta criada com sucesso!");
                ScreenManager.trocarTela(event, ScreenManager.getLoginxmlpath());
            } else {
                System.out.println("❌ Não foi possível criar a conta. E-mail já cadastrado.");
            }
        } finally {
            connector.desconectar(); // Fecha a conexão
        }
    }
}
