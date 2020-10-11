package app.challenge.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import app.challenge.entidades.*;


import java.util.List;

@RepositoryRestResource
public interface MateriaRepository extends JpaRepository<Materia, Long> {
      Materia findByNombre(String nombre);
      Materia findById(long id);
      void deleteById(long id);

}

