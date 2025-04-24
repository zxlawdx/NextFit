package dbsystem;

import java.sql.*;

public class DatabaseConnector {

    private Connection conexao;

    // Caminho fixo do banco
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/database/database_system.db";

    // Conecta ao banco
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

    // Desconecta do banco
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

    // Cria a tabela de usuários, se não existir
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
            System.out.println("📦 Tabela 'usuarios' criada ou já existente.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de usuários: " + e.getMessage());
        }
    }

    // Insere um novo usuário, se o e-mail ainda não estiver cadastrado
    public boolean registrarUsuario(String nome, String email, String senha) {
        if (emailExiste(email)) {
            return false;
        }

        String insertSql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
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

    // Verifica se o e-mail já está na tabela
    private boolean emailExiste(String email) {
        String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";

        try (PreparedStatement stmt = this.conexao.prepareStatement(checkEmailSql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar e-mail: " + e.getMessage());
            return true; // Por segurança, assume que existe se der erro
        }
    }

    // Retorna a conexão atual
    public Connection getConexao() {
        return this.conexao;
    }

    // Conexão para chamadas estáticas simples
    public static Connection getConnection() {
        DatabaseConnector connector = new DatabaseConnector();
        return connector.conectar() ? connector.getConexao() : null;
    }
}
