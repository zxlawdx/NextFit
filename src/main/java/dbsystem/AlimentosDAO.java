package dbsystem;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlimentosDAO {

    public static ObservableList<Alimentos> carregarAlimentos() {
        ObservableList<Alimentos> listaAlimentos = FXCollections.observableArrayList();
    
        try (Connection conn = DatabaseConnector.getConnection()) {  // <- aqui está correto agora
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM alimentos");
    
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                double proteinas = rs.getDouble("proteinas");
                double carboidratos = rs.getDouble("carboidratos");
                double gorduras = rs.getDouble("gorduras");
                double kcal = rs.getDouble("kcal");
    
                Alimentos a = new Alimentos(id, nome, tipo, proteinas, carboidratos, gorduras, kcal);
                listaAlimentos.add(a);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return listaAlimentos;
    }
    

    
}
