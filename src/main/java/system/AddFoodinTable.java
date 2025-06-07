package system;

import dbsystem.Alimentos;
import dbsystem.AlimentosDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class AddFoodinTable {
    @FXML private TextField nomeField;
    @FXML private TextField carboidratosField;
    @FXML private TextField gordurasField;
    @FXML private TextField proteinasField;
    @FXML private TextField kcalField;
    @FXML private ComboBox<String> tipoComboBox;
    @FXML private DialogPane dialogPane;

    /**
     * Método que é chamado automaticamente quando o botão "Salvar" do DialogPane é acionado.
     */
        public boolean salvarAlimento() {
            if (tipoComboBox.getValue() == null || 
                nomeField.getText().isEmpty() ||
                carboidratosField.getText().isEmpty() || 
                gordurasField.getText().isEmpty() ||
                proteinasField.getText().isEmpty() || 
                kcalField.getText().isEmpty()) {
                System.out.println("Preencha todos os campos.");
                return false;
            }

            try {
                String tipo = tipoComboBox.getValue();
                String nome = nomeField.getText().trim();
                double carboidratos = Double.parseDouble(carboidratosField.getText());
                double gorduras = Double.parseDouble(gordurasField.getText());
                double proteinas = Double.parseDouble(proteinasField.getText());
                double kcal = Double.parseDouble(kcalField.getText());

                Alimentos novoAlimento = new Alimentos(0, nome, tipo, proteinas, carboidratos, gorduras, kcal);

                AlimentosDAO.salvarAlimento(novoAlimento);

                System.out.println("Alimento adicionado com sucesso: " + nome);
                return true;

            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter valores: " + e.getMessage());
                return false;
            }
    }

    /**
     * Lógica para ser chamada de fora quando a tela for carregada e o botão "Salvar" for pressionado.
     * Essa abordagem depende do uso correto do `Dialog<ButtonType>` na tela que invoca esse pane.
     */
    public void configurarDialog(DialogPane pane) {
        this.dialogPane = pane;

        pane.lookupButton(ButtonType.OK).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            salvarAlimento();
        });
    }
}
