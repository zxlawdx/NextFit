package dbsystem;

import java.sql.*;

/**
 * Classe para gerenciamento da conexão com o banco SQLite.
 */
public class DatabaseConnector {

    private Connection conexao;

    // Caminho fixo do banco
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/database/database_system.db";

    /**
     * Conecta ao banco de dados.
     * @return true se conseguiu conectar, false caso contrário
     */
    public boolean conectar() {
        try {
            if (this.conexao != null && !this.conexao.isClosed()) {
                System.out.println("Já estava conectado. Desconectando...");
                this.conexao.close();
            }

            this.conexao = DriverManager.getConnection(DATABASE_URL);

            // Evita erro de "database is locked"
            try (Statement stmt = this.conexao.createStatement()) {
                stmt.execute("PRAGMA busy_timeout = 5000");
            }

            System.out.println("✅ Conectado com sucesso.");
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Desconecta do banco.
     * @return true se desconectou com sucesso
     */
    public boolean desconectar() {
        try {
            if (this.conexao != null && !this.conexao.isClosed()) {
                this.conexao.close();
                this.conexao = null;
                System.out.println("🔌 Desconectado com sucesso.");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao desconectar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cria a tabela de usuários se não existir.
     */

    /**
     * Registra um novo usuário.
     * @param nome nome do usuário
     * @param email email do usuário
     * @param senha senha do usuário
     * @return true se inseriu com sucesso, false se email já existe ou erro
     */
    public boolean registrarUsuario(String nome, String email, String senha) {
        if (emailExiste(email)) {
            return false;
        }

        String insertSql = "INSERT INTO Usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = this.conexao.prepareStatement(insertSql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.executeUpdate();
            System.out.println("✅ Usuário inserido com sucesso.");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica se o email já está cadastrado.
     * @param email email a verificar
     * @return true se email existe, false caso contrário
     */
    private boolean emailExiste(String email) {
        String checkEmailSql = "SELECT COUNT(*) FROM Usuario WHERE email = ?";

        try (PreparedStatement stmt = this.conexao.prepareStatement(checkEmailSql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar e-mail: " + e.getMessage());
            return true; // Por segurança, assume que existe se der erro
        }
    }

    public Connection getConexao() {
        return this.conexao;
    }

    /**
     * Método para obter conexão estática, útil para chamadas simples.
     * @return conexão ativa ou null se falha
     */
    public static Connection getConnection() {
        DatabaseConnector connector = new DatabaseConnector();
        return connector.conectar() ? connector.getConexao() : null;
    }
}

