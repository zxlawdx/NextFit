package dbsystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTablesManager {

    private final Connection conexao;

    public CreateTablesManager(Connection conexao) {
        this.conexao = conexao;
    }

    public void criarTabelasExtras() {
        try (Statement stmt = conexao.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS alimentos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    tipo TEXT NOT NULL,
                    proteinas REAL,
                    carboidratos REAL,
                    gorduras REAL,
                    kcal REAL
                );
            """;
            stmt.execute(sql);
            System.out.println("✅ Tabela de alimentos criada ou já existente.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public void criarTabelaUsuarios() {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                senha TEXT NOT NULL
            );
        """;

        try (Statement stmt = this.conexao.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Tabela de usuários criada ou já existente.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de usuários: " + e.getMessage());
        }
    }
}
