package system;

import dbsystem.Alimentos;
import dbsystem.AlimentosDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RefeicaoScreen implements AllMetodhs {

    @FXML private Button CriarRefeicao;
    @FXML private Button adicionarAlimento;
    @FXML private TextField nomeAlimento;
    @FXML private TextField tipoAlimento;
    @FXML private ComboBox<String> tipoRefeicao;
    @FXML private TableView<Alimentos> tableView;
    @FXML private TableColumn<Alimentos, String> nomeColumn;
    @FXML private TableColumn<Alimentos, String> tipoColumn;
    @FXML private TableColumn<Alimentos, Double> proteinasColumn;
    @FXML private TableColumn<Alimentos, Double> carboidratosColumn;
    @FXML private TableColumn<Alimentos, Double> gordurasColumn;
    @FXML private TableColumn<Alimentos, Double> kcalColumn;

    private FilteredList<Alimentos> alimentosFiltrados;
    private ObservableList<Alimentos> alimentosSelecionados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ObservableList<Alimentos> alimentos = AlimentosDAO.carregarAlimentos();
        alimentosFiltrados = new FilteredList<>(alimentos, p -> true);

        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        proteinasColumn.setCellValueFactory(new PropertyValueFactory<>("proteinas"));
        carboidratosColumn.setCellValueFactory(new PropertyValueFactory<>("carboidratos"));
        gordurasColumn.setCellValueFactory(new PropertyValueFactory<>("gorduras"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        tableView.setItems(alimentosFiltrados);
        tipoRefeicao.setItems(FXCollections.observableArrayList("Café da manhã", "Almoço", "Jantar", "Lanche", "Personalizada"));
        tipoRefeicao.setValue("Almoço");

        nomeAlimento.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltro());
        tipoAlimento.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltro());
    }

    private void aplicarFiltro() {
        String nomeFiltro = nomeAlimento.getText().toLowerCase().trim();
        String tipoFiltro = tipoAlimento.getText().toLowerCase().trim();

        alimentosFiltrados.setPredicate(alimento -> {
            boolean nomeMatch = nomeFiltro.isEmpty() || alimento.getNome().toLowerCase().contains(nomeFiltro);
            boolean tipoMatch = tipoFiltro.isEmpty() || alimento.getTipo().toLowerCase().contains(tipoFiltro);
            return nomeMatch && tipoMatch;
        });
    }

    @FXML
    private void adicionarAlimento() {
        Alimentos selecionado = tableView.getSelectionModel().getSelectedItem();
        if (selecionado != null && !alimentosSelecionados.contains(selecionado)) {
            alimentosSelecionados.add(selecionado);
            // Você pode atualizar uma tabela auxiliar ou imprimir no console por enquanto:
            System.out.println("Alimento adicionado: " + selecionado.getNome());
        }
    }

    @FXML
    private void criarRefeicao() {
        if (alimentosSelecionados.isEmpty()) {
            mostrarAlerta("Nenhum alimento selecionado!", "Adicione ao menos um alimento para criar a refeição.");
            return;
        }

        String tipo = tipoRefeicao.getValue();
        System.out.println("Criando refeição: " + tipo);
        alimentosSelecionados.forEach(a -> System.out.println("- " + a.getNome()));

        mostrarAlerta("Refeição criada!", "Refeição do tipo '" + tipo + "' criada com " + alimentosSelecionados.size() + " alimentos.");
        alimentosSelecionados.clear();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

    @Override
    public void back(ActionEvent event) {
        ScreenManager.trocarTela(event, ScreenManager.getDashbpardxmlpath());
    }
}
