package system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Perfil implements AllMetodhs {
    
    @FXML private Button sairBtn;
    @FXML private ImageView profileImage;
    @FXML private Button editProfileBtn;

    @Override
    @FXML
    public void back(ActionEvent event) {
        ScreenManager.trocarTela(event, ScreenManager.getDashbpardxmlpath());
        
    }

    @FXML
    public void initialize() {
        profileImage.setFitWidth(200);   // aumenta o tamanho da imagem
        profileImage.setFitHeight(200);
        profileImage.setPreserveRatio(true); // mantém proporção bonita

        File defaultImageFile = new File("src/main/icon/iconCircle.png");
        if (defaultImageFile.exists()) {
            profileImage.setImage(new Image(defaultImageFile.toURI().toString()));
        }

        // Só depois que o tamanho for definido, aplica o círculo certinho
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
        
        // Filtro para imagens apenas
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) editProfileBtn.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            profileImage.setImage(image);
        }
    }
}
