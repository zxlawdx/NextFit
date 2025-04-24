package system;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;           // <-- IMPORTANTE
import javafx.scene.Parent;
import javafx.scene.Scene;         // <-- IMPORTANTE
import javafx.stage.Stage;

public class ScreenManager {
    private static final String LoginXmlPath = "/system/mainLayout.fxml";
    private static final String RegisterXmlPath = "/system/RegisterScreen.fxml";

    public static void trocarTela(ActionEvent event, final String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLoginxmlpath() {
        return LoginXmlPath;
    }

    public static String getRegisterxmlpath() {
        return RegisterXmlPath;
    }
  
}

