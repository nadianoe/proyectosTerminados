package app.challenge.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nombre;
    private String horarioInicio;
    private String horarioFinal;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Profesor profesor;

    @ManyToMany
    private List<Alumno> alumnos;
    private int cupoMaximo;

    public Materia() {
        alumnos = new ArrayList<>();
    }

    public Materia(long id, String nombre, String horarioInicio, String horarioFinal, Profesor profesor, int cupoMaximo) {
        this.id = id;
        this.nombre = nombre;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.profesor = profesor;
        this.cupoMaximo = cupoMaximo;
    }

    public void agregarAlumno(Alumno alumno){
        this.alumnos.add(alumno);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
