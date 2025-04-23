package system;

import dbsystem.DatabaseConnector;
import dbsystem.CreateTablesManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Cria a instância do DatabaseConnector
        DatabaseConnector db = new DatabaseConnector();

        // Conecta ao banco de dados antes de criar tabelas ou inserir dados
        if (db.conectar()) {
            // Criar as tabelas se a conexão for bem-sucedida
            db.criarTabelaUsuarios();
            db.inserirUsuario("Law", "law@example.com", "senha123");

            // Criar as tabelas extras
            CreateTablesManager tables = new CreateTablesManager(db.getConexao());
            tables.criarTabelasExtras();
        } else {
            System.err.println("Erro ao conectar ao banco de dados!");
            return;
        }

        // Carrega a interface FXML
        Parent root = FXMLLoader.load(getClass().getResource("/system/mainLayout.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/system/main.css").toExternalForm());

        // Configurações da janela
        stage.setTitle("NextFit");
        stage.setScene(scene);
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
