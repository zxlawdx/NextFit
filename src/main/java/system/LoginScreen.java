package system;

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginScreen {

    @FXML private Label welcomeLabel;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label statusLabel;
    @FXML private Button logonButton;
    private Connection connection;

    @FXML
    private void initialize() {
        connection = DatabaseConnector.getConnection(); // obtém conexão


        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (validarLogin(email, password)) {
                statusLabel.setText("✅ Login bem-sucedido!");
                System.out.println("Usuário autenticado!");
                ScreenManager.trocarTela(event, ScreenManager.getDashbpardxmlpath());
                // aqui você pode abrir uma nova tela, etc.
            } else {
                statusLabel.setText("❌ Login inválido.");
                System.out.println("Falha no login.");
            }
        });

        logonButton.setOnAction(event -> {
            ScreenManager.trocarTela(event, ScreenManager.getRegisterxmlpath());
        });
        
    }


    private boolean validarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // retorna true se encontrou o usuário

        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return false;
        }
    }

}
