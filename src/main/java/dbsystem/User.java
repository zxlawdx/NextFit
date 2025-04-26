package dbsystem;

public class User {
    private String email;
    private String username;
    private double meter;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }
    public void setMeter(double meter) {
        this.meter = meter;
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
    public double getMeter() {
        return meter;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    
}
