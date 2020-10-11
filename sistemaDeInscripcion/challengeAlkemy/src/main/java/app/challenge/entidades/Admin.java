package app.challenge.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long dni;
    private String passwordNumerico;

    public Admin() {
    }

    public Admin(long id, long dni, String passwordNumerico) {
        this.id = id;
        this.dni = dni;
        this.passwordNumerico = passwordNumerico;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getPasswordNumerico() {
        return passwordNumerico;
    }

    public void setPasswordNumerico(String passwordNumerico) {
        this.passwordNumerico = passwordNumerico;
    }
}
