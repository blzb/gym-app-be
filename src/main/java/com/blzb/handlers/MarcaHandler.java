package com.blzb.handlers;

import com.blzb.data.dbo.Estado;
import com.blzb.data.dbo.Marca;
import com.blzb.data.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by apimentel on 5/10/17.
 */
@RepositoryEventHandler
@Component
public class MarcaHandler {

    @Autowired
    EstadoRepository estadoRepository;

    @HandleBeforeCreate
    public void handlerMarcaSave(Marca marca) {
        Estado estado = estadoRepository.findTopByPersonaOrderByFechaDesc(marca.getPersona());
        if (estado != null && marca.getActividad() != null) {
            Double kcal = new Double(marca.getActividad().getMets() * 0.0175 * estado.getPeso() * marca.getDuracion());
            marca.setCalorias(kcal.intValue());
        }
    }
}
