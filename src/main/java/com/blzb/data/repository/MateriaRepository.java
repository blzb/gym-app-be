package com.blzb.data.repository;

import com.blzb.data.dbo.Actividad;
import com.blzb.data.dbo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/materias", collectionResourceRel = "materias")
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByCarreraOrderBySemestreAsc(String carrera);
}