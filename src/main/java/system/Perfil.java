package system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbsystem.DatabaseConnector;
import dbsystem.User;

public class Perfil implements AllMetodhs {

    @FXML private Button sairBtn;
    @FXML private ImageView profileImage;
    @FXML private Button editProfileBtn;
    @FXML private Label user_info;
    @FXML private Label emailLabel;
    @FXML private Label idadeLabel;
    @FXML private Label pesoLabel;
    @FXML private Label alturaLabel;
    
    @Override
    public void back(ActionEvent event) {
        ScreenManager.trocarTela(event, ScreenManager.getDashbpardxmlpath());
    }

    @FXML
    public void initialize() {
        profileImage.setFitWidth(200);
        profileImage.setFitHeight(200);
        profileImage.setPreserveRatio(true);


        User user = Session.getCurrentUser();
        if (user != null) {
            if (user.getFotoPerfil() != null) {
                File imageFile = new File(user.getFotoPerfil());
                if (imageFile.exists()) {
                    profileImage.setImage(new Image(imageFile.toURI().toString()));
                }
            } else {
                File defaultImageFile = new File("src/main/icon/iconCircle.png");
                if (defaultImageFile.exists()) {
                    profileImage.setImage(new Image(defaultImageFile.toURI().toString()));
                }
            }

            // Atualizar as informações do usuário
            user_info.setText(user.getUsername());
            emailLabel.setText("Email: " + user.getEmail());
            idadeLabel.setText("Idade: " + user.getIdade());
            pesoLabel.setText("Peso: " + user.getPeso() + " kg");
            alturaLabel.setText("Altura: " + user.getAltura() + " m");
        }

        profileImage.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            double radius = Math.min(newBounds.getWidth(), newBounds.getHeight()) / 2;
            Circle clip = new Circle(newBounds.getWidth() / 2, newBounds.getHeight() / 2, radius);
            profileImage.setClip(clip);
        });
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma nova foto de perfil");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) editProfileBtn.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            profileImage.setImage(image);

            // Atualizar o caminho no banco de dados
            User user = Session.getCurrentUser();
            if (user != null) {
                try (Connection conn = DatabaseConnector.getConnection()) {
                    String sql = "UPDATE usuarios SET fotoPerfil = ? WHERE email = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, file.getAbsolutePath());
                    stmt.setString(2, user.getEmail());
                    stmt.executeUpdate();
                    
                    user.setFotoPerfil(file.getAbsolutePath()); // Atualiza na memória também
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
