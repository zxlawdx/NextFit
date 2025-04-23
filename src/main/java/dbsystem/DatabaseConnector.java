package dbsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    private Connection conexao;

    /**
     * Conecta ao banco de dados. Se já estiver conectado, desconecta primeiro.
     */
    public boolean conectar() {
        try {
            // Se já estiver conectado, desconecta primeiro
            if (this.conexao != null && !this.conexao.isClosed()) {
                System.out.println("Já estava conectado. Desconectando...");
                this.conexao.close();
            }

            // Cria nova conexão
            String url = "jdbc:sqlite:src/main/database/database_system.db";
            this.conexao = DriverManager.getConnection(url);

            // Evita erro de 'database is locked'
            Statement stmt = this.conexao.createStatement();
            stmt.execute("PRAGMA busy_timeout = 5000");
            stmt.close();

            System.out.println("Conectou com sucesso!");
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
    }

    public boolean desconectar() {
        try {
            if (this.conexao != null && !this.conexao.isClosed()) {
                this.conexao.close();
                this.conexao = null;
                System.out.println("Desconectou com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao desconectar: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Criar um Statement pronto para uso
     */
    public Statement criarStatement() {
        try {
            return this.conexao.createStatement();
        } catch (SQLException e) {
            System.err.println("Erro ao criar statement: " + e.getMessage());
            return null;
        }
    }

    public void inserirUsuario(String nome, String email, String senha) {
        // Verifica se o e-mail já existe na tabela
        String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = getConexao().prepareStatement(checkEmailSql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Erro: O e-mail " + email + " já está cadastrado.");
                return;  // E-mail já existe, não insere
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar e-mail: " + e.getMessage());
            return;
        }

        // Caso o e-mail não exista, insere o usuário
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = getConexao().prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.executeUpdate();
            System.out.println("✅ Usuário inserido.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }


    /**
     * Retorna a conexão ativa. Útil para outras classes.
     */
    public Connection getConexao() {
        return this.conexao;
    }

    /**
     * Retorno padrão para manter compatibilidade com chamadas estáticas
     */
    public static Connection getConnection() {
        DatabaseConnector instance = new DatabaseConnector();
        if (instance.conectar()) {
            return instance.getConexao();
        } else {
            System.err.println("❌ Não foi possível obter conexão.");
            return null;
        }
    }

    /**
     * Criação da tabela de usuários
     */
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
