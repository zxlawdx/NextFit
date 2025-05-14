package system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController implements AllMetodhs{

    @FXML private Button btnNovaRefeicao;
    @FXML private Button btnHistorico;
    @FXML private Button btnPerfil;
    @FXML private Button btnSair;
    @FXML private Button btnChat;

    @FXML
    public void perfil(ActionEvent event){
        ScreenManager.trocarTela(event, ScreenManager.getPerfilxmlpath());
    }

    @FXML
    public void chat(ActionEvent event){
        ScreenManager.trocarTela(event, ScreenManager.getChatxmlpath());
    }

    @Override
    public void back(ActionEvent event) {
        ScreenManager.trocarTela(event, ScreenManager.getLoginxmlpath());
    }

    @FXML
    public void novaRefeicao(ActionEvent event){
        ScreenManager.trocarTela(event, ScreenManager.getRefeicaoxmlpath());
    }
    
}
