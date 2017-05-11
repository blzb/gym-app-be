package com.blzb.data.repository;

import com.blzb.data.dbo.Estado;
import com.blzb.data.dbo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/estados", collectionResourceRel = "estados")
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Estado findTopByPersonaOrderByFechaDesc(Persona persona);
}