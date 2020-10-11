package app.challenge.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import app.challenge.entidades.*;

import java.util.List;


@RepositoryRestResource
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    List<Alumno> findAllByDni(long dni);
    boolean existsByDni(long dni);
    Alumno findByDni(long dni);
    Alumno findById(long id);
}