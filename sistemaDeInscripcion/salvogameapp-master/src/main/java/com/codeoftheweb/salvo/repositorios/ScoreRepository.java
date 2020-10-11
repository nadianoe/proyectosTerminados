package com.codeoftheweb.salvo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.codeoftheweb.salvo.entidades.*;

@RepositoryRestResource
public interface ScoreRepository extends JpaRepository<Score, Long> {
	
      Score findById(long id);
}

