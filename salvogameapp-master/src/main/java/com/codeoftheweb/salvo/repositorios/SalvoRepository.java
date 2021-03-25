package com.codeoftheweb.salvo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.codeoftheweb.salvo.entidades.*;

@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo, Long> {
	
      Salvo findById(long id);
}

