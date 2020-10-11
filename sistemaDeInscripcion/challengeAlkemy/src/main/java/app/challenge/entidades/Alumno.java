package app.challenge.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long dni;
    private String pass;

    @ManyToMany
    private List<Materia> materias;

    public Alumno() {
        materias = new ArrayList<>();
    }

    public Alumno(long id, long dni, String pass) {
        this.id = id;
        this.dni = dni;
        this.pass = pass;
        this.materias = new ArrayList<>();
    }

    public void agregarMateriaACursar(Materia materia){
        this.materias.add(materia);
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}
