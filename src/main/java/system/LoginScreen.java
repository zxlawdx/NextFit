package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbsystem.DatabaseConnector;
import dbsystem.User;
import javafx.event.ActionEvent;
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
    @FXML private Button logonButton;
   

    private Connection connection;

    @FXML
    private void initialize() {
        connection = DatabaseConnector.getConnection();
    }

    @FXML
    private void signin(ActionEvent event){
        String email = emailField.getText();
        String password = passwordField.getText();

        // if (email.isEmpty() || password.isEmpty()) {
        //     statusLabel.setText("❗ Preencha todos os campos.");
        //     return;
        // }

        if (validarLogin(email, password)) {
            statusLabel.setText("✅ Login bem-sucedido!");
            System.out.println("Usuário autenticado!");
            ScreenManager.trocarTela(event, ScreenManager.getDashbpardxmlpath());
        } else {
            statusLabel.setText("❌ Login inválido.");
            System.out.println("Falha no login.");
        }
    }

    @FXML
    private void signup(ActionEvent event){
        ScreenManager.trocarTela(event, ScreenManager.getRegisterxmlpath());
    }

    private boolean validarLogin(String email, String senha) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("nome"));
                user.setPassword(rs.getString("senha"));
                user.setIdade(rs.getInt("idade"));
                user.setPeso(rs.getDouble("peso"));
                user.setAltura(rs.getDouble("altura"));
                user.setFotoPerfil(rs.getString("fotoPerfil"));

                Session.setCurrentUser(user);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
        }

        return false;
    }
}
