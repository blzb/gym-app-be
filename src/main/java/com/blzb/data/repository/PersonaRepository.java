package com.blzb.data.repository;

import com.blzb.data.dbo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/personas", collectionResourceRel = "personas")
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Persona findByCorreoAndPassword(@Param("correo") String correo, @Param("password") String password);
}