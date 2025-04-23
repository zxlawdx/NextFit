package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbsystem.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScreen {

    @FXML private Label welcomeLabel;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label statusLabel;

    private Connection connection;

    @FXML
    private void initialize() {
        connection = DatabaseConnector.getConnection(); // obtém conexão

        welcomeLabel.setText("Bem-vindo ao NextFit!");

        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (validarLogin(email, password)) {
                statusLabel.setText("✅ Login bem-sucedido!");
                System.out.println("Usuário autenticado!");
                // aqui você pode abrir uma nova tela, etc.
            } else {
                statusLabel.setText("❌ Login inválido.");
                System.out.println("Falha no login.");
            }
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
