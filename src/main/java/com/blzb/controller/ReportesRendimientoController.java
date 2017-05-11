package com.blzb.controller;

import com.blzb.data.dbo.Calificacion;
import com.blzb.data.dbo.Marca;
import com.blzb.data.dbo.Persona;
import com.blzb.data.repository.ActividadRepository;
import com.blzb.data.repository.CalificacionRepository;
import com.blzb.data.repository.MarcaRepository;
import com.blzb.data.repository.PersonaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by apimentel on 5/9/17.
 */
@Controller
@RequestMapping("reportes/rendimiento")
public class ReportesRendimientoController {
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    CalificacionRepository calificacionRepository;

    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("personas", personaRepository.findAll());
        return "reportes/rendimiento/list";
    }

    @RequestMapping("{id}")
    public String personaReport(@PathVariable("id") Long id, Model model) throws JsonProcessingException {
        Persona persona = personaRepository.getOne(id);
        List<Calificacion> calificacions = calificacionRepository.findByPersona(persona);
        List<Marca> marcas = marcaRepository.findByPersona(persona);
        ObjectMapper objectMapper = new ObjectMapper();
        String values = objectMapper.writeValueAsString(getTimeSeries(marcas, calificacions));
        model.addAttribute("timeValues", values);
        model.addAttribute("calificaciones", calificacions);
        model.addAttribute("persona", persona);
        model.addAttribute("marcas", marcas);
        return "reportes/rendimiento/report";
    }

    private List<Map> getTimeSeries(List<Marca> marcas, List<Calificacion> calificacions) {
        Set<Date> dates = new TreeSet<>();
        Map<Date, Integer> ejercicio = new HashMap<>();
        Map<Date, List<Float>> promedio = new HashMap<>();
        for (Marca marca : marcas) {
            dates.add(marca.getFecha());
            if (!ejercicio.containsKey(marca.getFecha())) {
                ejercicio.put(marca.getFecha(), 0);
            }
            ejercicio.put(marca.getFecha(), ejercicio.get(marca.getFecha()) + marca.getDuracion());
        }
        for (Calificacion calificacion : calificacions) {
            dates.add(calificacion.getFecha());
            if (!promedio.containsKey(calificacion.getFecha())) {
                promedio.put(calificacion.getFecha(), new ArrayList<>());
            }
            promedio.get(calificacion.getFecha()).add(calificacion.getValor());
        }


        List<Map> jsonValues = new ArrayList<>();
        for (Date date : dates) {
            Map item = new HashMap();
            item.put("y", date);
            if (ejercicio.containsKey(date)) {
                item.put("a", ejercicio.get(date));
            } else {
                item.put("a", 0);
            }
            if (promedio.containsKey(date)) {
                item.put("b", promedio.get(date).stream().mapToDouble(i -> i).average().orElse(0)*10);
            } else {
                item.put("b", 0);
            }

            jsonValues.add(item);
        }
        return jsonValues;

    }

}
