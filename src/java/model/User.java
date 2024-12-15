package model;

/**
 *
 * @author kavin
 */
public class User {
    private int id; // Primary key
    private String name;
    private String email;
    private String pnumber;
    private String password; 

    // Constructor
    public User(String name, String email, String pnumber, String password) {
        this.name = name;
        this.email = email;
        this.pnumber = pnumber;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}