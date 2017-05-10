package com.blzb.data.repository;

import com.blzb.data.dbo.Actividad;
import com.blzb.data.dbo.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/marcas", collectionResourceRel = "marcas")
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}