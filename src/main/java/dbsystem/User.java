package dbsystem;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String fotoPerfil;
    private int idade;
    private double peso;
    private double altura;

    // Construtores, getters e setters

    public User(){

    }
    
    public User(int id, String username, String email, String password, int idade, double peso, double altura) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
    }

    // Getters
    public int getIdade() { return idade; }
    public double getPeso() { return peso; }
    public double getAltura() { return altura; }

    // Setters
    public void setIdade(int idade) { this.idade = idade; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setAltura(double altura) { this.altura = altura; }


    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

}
