package dbsystem;

public class Alimentos {
    private int id;
    private String nome;
    private String tipo;
    private double proteinas;
    private double carboidratos;
    private double gorduras;
    private double kcal;

    public Alimentos(int id, String nome, String tipo, double proteinas, double carboidratos, double gorduras, double kcal){
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.proteinas =  proteinas;
        this.carboidratos = carboidratos;
        this.gorduras = gorduras;
        this.kcal = kcal;
    }

    public double getCarboidratos() {
        return carboidratos;
    }


    public double getGorduras() {
        return gorduras;
    }


    public double getKcal() {
        return kcal;
    }


    public String getNome() {
        return nome;
    }


    public double getProteinas() {
        return proteinas;
    }


    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public void setCarboidratos(double carboidratos) {
        this.carboidratos = carboidratos;
    }


    public void setGorduras(double gorduras) {
        this.gorduras = gorduras;
    }


    public void setKcal(double kcal) {
        this.kcal = kcal;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
