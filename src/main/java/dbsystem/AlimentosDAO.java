package dbsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.AlimentoSelecionado;

public class AlimentosDAO {

    public static ObservableList<Alimentos> carregarAlimentos() {
        ObservableList<Alimentos> listaAlimentos = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Alimento")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                double proteinas = rs.getDouble("proteinas_por_100g");
                double carboidratos = rs.getDouble("carboidratos_por_100g");
                double gorduras = rs.getDouble("gorduras_por_100g");
                double kcal = rs.getDouble("calorias_por_100g");

                Alimentos a = new Alimentos(id, nome, tipo, proteinas, carboidratos, gorduras, kcal);
                listaAlimentos.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAlimentos;
    }

    public static void salvarAlimento(Alimentos novoAlimento) {
        String sql = "INSERT INTO Alimento (nome, tipo, proteinas_por_100g, carboidratos_por_100g, gorduras_por_100g, calorias_por_100g) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, novoAlimento.getNome());
            pstmt.setString(2, novoAlimento.getTipo());
            pstmt.setDouble(3, novoAlimento.getProteinas());
            pstmt.setDouble(4, novoAlimento.getCarboidratos());
            pstmt.setDouble(5, novoAlimento.getGorduras());
            pstmt.setDouble(6, novoAlimento.getKcal());

            pstmt.executeUpdate();
            System.out.println("Alimento salvo com sucesso no banco de dados.");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar alimento no banco:");
            e.printStackTrace();
        }
    }


    public static void registrarRefeicao(User usuarioLogado, String nomeRefeicao, List<AlimentoSelecionado> alimentosConsumidos) {
        String sqlRefeicao = "INSERT INTO Refeicao (usuario_id, nome, data_hora) VALUES (?, ?, datetime('now'))";
        String sqlGetId = "SELECT last_insert_rowid()";
        String sqlRefeicaoAlimento = "INSERT INTO RefeicaoAlimento (refeicao_id, alimento_id, quantidade_gramas) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            int refeicaoId;

            // Inserir a refeição
            try (PreparedStatement stmt = conn.prepareStatement(sqlRefeicao)) {
                stmt.setInt(1, usuarioLogado.getId());
                stmt.setString(2, nomeRefeicao);
                stmt.executeUpdate();
            }

            // Obter o último ID inserido
            try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlGetId)) {
                if (rs.next()) {
                    refeicaoId = rs.getInt(1);
                } else {
                    conn.rollback();
                    throw new SQLException("Falha ao obter ID da refeição.");
                }
            }

            // Inserir os alimentos consumidos
            try (PreparedStatement stmt = conn.prepareStatement(sqlRefeicaoAlimento)) {
                for (AlimentoSelecionado alimento : alimentosConsumidos) {
                    stmt.setInt(1, refeicaoId);
                    stmt.setInt(2, alimento.getAlimento().getId());
                    stmt.setDouble(3, alimento.getGramas());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
            System.out.println("✅ Refeição registrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
