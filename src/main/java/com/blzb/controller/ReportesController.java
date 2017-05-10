package com.blzb.controller;

import com.blzb.data.dbo.Marca;
import com.blzb.data.dbo.Persona;
import com.blzb.data.repository.ActividadRepository;
import com.blzb.data.repository.MarcaRepository;
import com.blzb.data.repository.PersonaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by apimentel on 5/9/17.
 */
@Controller
@RequestMapping("reportes")
public class ReportesController {
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @RequestMapping("persona")
    public String home(Model model) {
        model.addAttribute("personas", personaRepository.findAll());
        return "reportes/personas/list";
    }

    @RequestMapping("persona/{id}")
    public String personaReport(@PathVariable("id") Long id, Model model) throws JsonProcessingException {
        Persona persona = personaRepository.getOne(id);
        List<Marca> marcas = marcaRepository.findByPersona(persona);
        Map<String, Integer> totals = new HashMap<>();
        for (Marca marca : marcas) {
            if (!totals.containsKey(marca.getActividad().getNombre())) {
                totals.put(marca.getActividad().getNombre(), 0);
            }
            totals.put(marca.getActividad().getNombre(), totals.get(marca.getActividad().getNombre()) + marca.getDuracion());
        }
        List<Map> jsonValues = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : totals.entrySet()) {
            Map item = new HashMap();
            item.put("label", entry.getKey());
            item.put("value", entry.getValue());
            jsonValues.add(item);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonValues);
        model.addAttribute("pieValues", jsonString);
        model.addAttribute("marcas", marcas);
        model.addAttribute("persona", persona);
        return "reportes/personas/report";
    }
}
