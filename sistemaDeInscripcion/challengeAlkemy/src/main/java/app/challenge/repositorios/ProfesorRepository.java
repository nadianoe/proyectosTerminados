package app.challenge.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import app.challenge.entidades.*;

@RepositoryRestResource
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Profesor findByDni(long dni);
    Profesor findById(long id);
    void deleteById(long id);
}