package app.challenge.repositorios;

import app.challenge.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdminRepository extends JpaRepository<Admin, Long>{
    boolean existsByDni(long dni);
    Admin findByDni(long dni);
    Admin findById(long id);
}

