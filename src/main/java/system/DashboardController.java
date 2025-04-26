package system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController implements AllMetodhs{

    @FXML private Button btnNovaRefeicao;
    @FXML private Button btnHistorico;
    @FXML private Button btnPerfil;
    @FXML private Button btnSair;

    public void perfil(ActionEvent event){
        ScreenManager.trocarTela(event, ScreenManager.getPerfilxmlpath());
    }

    // @Override
    // public void init() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'init'");
    // }

    @Override
    public void back(ActionEvent event) {
        ScreenManager.trocarTela(event, ScreenManager.getLoginxmlpath());
    }
    
}
