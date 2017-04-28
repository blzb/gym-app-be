package com.blzb.data.repository;

import com.blzb.data.dbo.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by apimentel on 4/23/17.
 */
@RepositoryRestResource(path = "/reservaciones", collectionResourceRel = "reservaciones")
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
}