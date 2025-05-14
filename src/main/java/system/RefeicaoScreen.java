package system;

import dbsystem.Alimentos;
import dbsystem.AlimentosDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RefeicaoScreen {

    @FXML private Button CriarRefeicao;
    @FXML private TextField nomeAlimento;
    @FXML private TableView<Alimentos> tableView; // <- Esse precisa estar definido no FXML
    @FXML private TableColumn<Alimentos, String> nomeColumn;
    @FXML private TableColumn<Alimentos, String> tipoColumn;
    @FXML private TableColumn<Alimentos, Double> proteinasColumn;
    @FXML private TableColumn<Alimentos, Double> carboidratosColumn;
    @FXML private TableColumn<Alimentos, Double> gordurasColumn;
    @FXML private TableColumn<Alimentos, Double> kcalColumn;

    private FilteredList<Alimentos> alimentosFiltrados;

    @FXML
    public void initialize() {
        // Carrega dados do banco
        ObservableList<Alimentos> alimentos = AlimentosDAO.carregarAlimentos();
        alimentosFiltrados = new FilteredList<>(alimentos, p -> true);

        // Configura colunas da tabela
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        proteinasColumn.setCellValueFactory(new PropertyValueFactory<>("proteinas"));
        carboidratosColumn.setCellValueFactory(new PropertyValueFactory<>("carboidratos"));
        gordurasColumn.setCellValueFactory(new PropertyValueFactory<>("gorduras"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        // Aplica lista filtrada na tabela
        tableView.setItems(alimentosFiltrados);

        // Adiciona listener para campo de pesquisa
        nomeAlimento.textProperty().addListener((obs, antigoValor, novoValor) -> {
            alimentosFiltrados.setPredicate(alimento -> {
                if (novoValor == null || novoValor.isEmpty()) {
                    return true;
                }
                String filtro = novoValor.toLowerCase();
                return alimento.getNome().toLowerCase().contains(filtro);
            });
        });
    }

    @FXML
    private void BuscarAlimento() {
        // Não precisa fazer nada aqui porque o filtro já está vinculado ao campo de texto
    }
}
