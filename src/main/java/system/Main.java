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
    public void start(Stage stage) {
        // Cria a instância do DatabaseConnector
        DatabaseConnector db = new DatabaseConnector();

        try {
            // Conecta ao banco de dados
            if (db.conectar()) {
                // Criar a tabela de usuários
                db.criarTabelaUsuarios();

                // Registrar um usuário (apenas para exemplo)
                boolean usuarioCriado = db.registrarUsuario("Law", "law@example.com", "senha123");
                if (usuarioCriado) {
                    System.out.println("✅ Usuário criado com sucesso.");
                } else {
                    System.out.println("❌ O e-mail já está em uso ou ocorreu um erro.");
                }

                // Criar as tabelas extras
                CreateTablesManager tables = new CreateTablesManager(db.getConexao());
                tables.criarTabelasExtras();
            } else {
                System.err.println("❌ Erro ao conectar ao banco de dados.");
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

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao inicializar a aplicação: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
