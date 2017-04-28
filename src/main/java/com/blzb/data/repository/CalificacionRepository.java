package com.blzb.data.repository;

import com.blzb.data.dbo.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/calificaciones", collectionResourceRel = "calificaciones")
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
}