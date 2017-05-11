package com.blzb.data.repository;

import com.blzb.data.dbo.Calificacion;
import com.blzb.data.dbo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/calificaciones", collectionResourceRel = "calificaciones")
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByMateria(Materia materia);
}