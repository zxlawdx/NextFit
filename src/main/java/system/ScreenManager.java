package system;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {
    private static final String LoginXmlPath = "/system/layouts/LoginScreen.fxml";
    private static final String RegisterXmlPath = "/system/layouts/RegisterScreen.fxml";
    private static final String DashbpardXmlPath = "/system/layouts/ManagerFoodScreen.fxml";
    private static final String PerfilXmlPath =  "/system/layouts/PerfilScreen.fxml";
    private static final String ChatXmlPath = "/system/layouts/ChatScreen.fxml";
    private static final String RefeicaoXmlPath = "/system/layouts/FoodScreen.fxml";
    private static final String AdicionarAlimentoPath = "/system/layouts/NewFoodScreen.fxml";

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

    public static String getDashbpardxmlpath() {
        return DashbpardXmlPath;
    }

    public static String getPerfilxmlpath() {
        return PerfilXmlPath;
    }
    
    public static String getChatxmlpath() {
        return ChatXmlPath;
    }

    public static String getRefeicaoxmlpath() {
        return RefeicaoXmlPath;
    }

    public static String getAdicionaralimentopath() {
        return AdicionarAlimentoPath;
    }
}

