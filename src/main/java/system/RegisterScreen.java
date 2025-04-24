package system;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    @FXML private Button buttonBackToLogin;
    @FXML private Button registerUserButton;
    private Connection connection;

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
    
        DatabaseConnector connector = new DatabaseConnector();
    
        if (!connector.conectar()) {
            System.out.println("Erro ao conectar com o banco de dados.");
            return;
        }
    
        try {
            boolean sucesso = connector.registrarUsuario(user, email, password);
            if (sucesso) {
                System.out.println("✅ Conta criada com sucesso!");
                // Redirecionar, se quiser
                ScreenManager.trocarTela(event, ScreenManager.getLoginxmlpath());
            } else {
                System.out.println("❌ Não foi possível criar a conta. E-mail já cadastrado.");
            }
        } finally {
            connector.desconectar(); // Fecha a conexão
        }
    }
    

}
