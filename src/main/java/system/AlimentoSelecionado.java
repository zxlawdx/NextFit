// --- Classe AlimentoSelecionado.java ---
package system;

import dbsystem.Alimentos;
import dbsystem.AlimentosDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import dbsystem.Alimentos;

public class AlimentoSelecionado {
    private Alimentos alimento;
    private double gramas;

    public AlimentoSelecionado(Alimentos alimento, double gramas) {
        this.alimento = alimento;
        this.gramas = gramas;
    }

    public Alimentos getAlimento() {
        return alimento;
    }

    public double getGramas() {
        return gramas;
    }

    public double getProteinas() {
        return alimento.getProteinas() * gramas / 100.0;
    }

    public double getCarboidratos() {
        return alimento.getCarboidratos() * gramas / 100.0;
    }

    public double getGorduras() {
        return alimento.getGorduras() * gramas / 100.0;
    }

    public double getKcal() {
        return alimento.getKcal() * gramas / 100.0;
    }
}
