package system;

import dbsystem.DatabaseConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Cria a instância do DatabaseConnector
        DatabaseConnector db = new DatabaseConnector();
    
        try {
            if (db.conectar()) {
                System.out.println("conexão bem sucedida");
            } else {
                System.err.println("❌ Erro ao conectar ao banco de dados.");
                return;
            }
    
            // 🟢 Carrega o FXML da tela de login
            Parent root = FXMLLoader.load(getClass().getResource(ScreenManager.getLoginxmlpath()));
            Scene scene = new Scene(root);
    
            // Configura a janela
            stage.setTitle("NextFit");
            stage.setScene(scene);
    
            // Adiciona o ícone do app
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/system/background/iconApp.png")));
            
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.setResizable(false);
            stage.show();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao inicializar a aplicação: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
