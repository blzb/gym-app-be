package com.blzb.data.repository;

import com.blzb.data.dbo.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/equipos", collectionResourceRel = "equipos")
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
}