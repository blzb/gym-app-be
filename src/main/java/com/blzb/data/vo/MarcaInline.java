package com.blzb.data.vo;

import com.blzb.data.dbo.Actividad;
import com.blzb.data.dbo.Marca;
import com.blzb.data.dbo.Persona;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by apimentel on 5/11/17.
 */
@Projection(name = "marcaInline", types = {Marca.class})
public interface MarcaInline {
    Persona getPersona();

    int getDuracion();

    Date getFecha();

    int getCalorias();

    Actividad getActividad();

}
