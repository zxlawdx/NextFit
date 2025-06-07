package system;

import dbsystem.Alimentos;
import dbsystem.AlimentosDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class RefeicaoScreen implements AllMetodhs {

    @FXML private Button CriarRefeicao;
    @FXML private Button adicionarAlimento;
    @FXML private Button filtrarButton;
    @FXML private Button salvarButton;
    @FXML private TextField nomeAlimento;
    @FXML private TextField tipoAlimento;
    @FXML private TextField maxKcal;
    @FXML private ComboBox<String> tipoRefeicao;
    @FXML private TableView<Alimentos> tableView;
    @FXML private TableColumn<Alimentos, Integer> idColumn;
    @FXML private TableColumn<Alimentos, String> nomeColumn;
    @FXML private TableColumn<Alimentos, String> tipoColumn;
    @FXML private TableColumn<Alimentos, Double> proteinasColumn;
    @FXML private TableColumn<Alimentos, Double> carboidratosColumn;
    @FXML private TableColumn<Alimentos, Double> gordurasColumn;
    @FXML private TableColumn<Alimentos, Double> kcalColumn;

    private FilteredList<Alimentos> alimentosFiltrados;
    private ObservableList<Alimentos> alimentos = FXCollections.observableArrayList();
    private ObservableList<AlimentoSelecionado> alimentosSelecionados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        alimentos.setAll(AlimentosDAO.carregarAlimentos());
        alimentosFiltrados = new FilteredList<>(alimentos, p -> true);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
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
        filtrarButton.setOnAction(e -> aplicarFiltro());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setOnMouseClicked(e -> {
            ObservableList<Alimentos> selecionados = tableView.getSelectionModel().getSelectedItems();
            for (Alimentos a : selecionados) {
                if (alimentosSelecionados.stream().anyMatch(sel -> sel.getAlimento().equals(a))) {
                    mostrarAlerta("Aviso", "O alimento \"" + a.getNome() + "\" já foi adicionado.");
                    continue;
                }

                TextInputDialog dialog = new TextInputDialog("100");
                dialog.setTitle("Quantidade");
                dialog.setHeaderText("Digite a quantidade em gramas para: " + a.getNome());
                dialog.setContentText("Gramas:");

                dialog.showAndWait().ifPresent(input -> {
                    try {
                        double gramas = Double.parseDouble(input);
                        if (gramas <= 0) throw new NumberFormatException();
                        alimentosSelecionados.add(new AlimentoSelecionado(a, gramas));
                    } catch (NumberFormatException ex) {
                        mostrarAlerta("Erro", "Quantidade inválida.");
                    }
                });
            }
        });
    }

    private void aplicarFiltro() {
        String nomeFiltro = nomeAlimento.getText().toLowerCase().trim();
        String tipoFiltro = tipoAlimento.getText().toLowerCase().trim();
        String maxKcalTexto = maxKcal.getText().trim();
        Double maxKcalValor = null;

        try {
            if (!maxKcalTexto.isEmpty()) {
                maxKcalValor = Double.parseDouble(maxKcalTexto);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Filtro", "Valor máximo de Kcal inválido.");
            return;
        }

        Double finalMaxKcalValor = maxKcalValor;
        alimentosFiltrados.setPredicate(alimento -> {
            boolean nomeMatch = nomeFiltro.isEmpty() || alimento.getNome().toLowerCase().contains(nomeFiltro);
            boolean tipoMatch = tipoFiltro.isEmpty() || alimento.getTipo().toLowerCase().contains(tipoFiltro);
            boolean kcalMatch = (finalMaxKcalValor == null) || alimento.getKcal() <= finalMaxKcalValor;
            return nomeMatch && tipoMatch && kcalMatch;
        });
    }

    @FXML
    private void criarRefeicao() {
        if (alimentosSelecionados.isEmpty()) {
            mostrarAlerta("Nenhum alimento selecionado!", "Adicione ao menos um alimento para criar a refeição.");
            return;
        }

        String tipo = tipoRefeicao.getValue();
        double totalProteinas = 0, totalCarboidratos = 0, totalGorduras = 0, totalKcal = 0;

        System.out.println("Refeição: " + tipo);
        for (AlimentoSelecionado sel : alimentosSelecionados) {
            Alimentos a = sel.getAlimento();
            double g = sel.getGramas();

            System.out.printf("- %s (%.0fg): %.1fP %.1fC %.1fG %.1fkcal%n",
                a.getNome(), g, sel.getProteinas(), sel.getCarboidratos(), sel.getGorduras(), sel.getKcal());

            totalProteinas += sel.getProteinas();
            totalCarboidratos += sel.getCarboidratos();
            totalGorduras += sel.getGorduras();
            totalKcal += sel.getKcal();
        }

        mostrarAlerta("Refeição criada!",
            "Tipo: " + tipo +
            "\nTotal: " +
            String.format("\n%.1fg proteínas\n%.1fg carboidratos\n%.1fg gorduras\n%.1f kcal",
                totalProteinas, totalCarboidratos, totalGorduras, totalKcal));
        AlimentosDAO.registrarRefeicao(Session.getCurrentUser(), tipo, alimentosSelecionados);
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

    @FXML
    private void adicionarAlimento(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenManager.getAdicionaralimentopath()));
            DialogPane dialogPane = loader.load();

            AddFoodinTable controller = loader.getController();
            controller.configurarDialog(dialogPane);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Adicionar Alimento");

            dialog.showAndWait();

            alimentos.setAll(AlimentosDAO.carregarAlimentos());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir a tela de adicionar alimento.");
        }
    }
}
