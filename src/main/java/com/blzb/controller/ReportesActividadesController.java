package com.blzb.controller;

import com.blzb.data.dbo.Actividad;
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

import java.util.*;

/**
 * Created by apimentel on 5/9/17.
 */
@Controller
@RequestMapping("reportes/actividades")
public class ReportesActividadesController {
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("actividades", actividadRepository.findAll());
        return "reportes/personas/list";
    }

    @RequestMapping("{id}")
    public String personaReport(@PathVariable("id") Long id, Model model) throws JsonProcessingException {
        Actividad actividad = actividadRepository.getOne(id);
        List<Marca> marcas = marcaRepository.findByActividad(actividad);
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("pieValues", objectMapper.writeValueAsString(getTotals(marcas)));
        model.addAttribute("caloriasValues", objectMapper.writeValueAsString(getCalorias(marcas)));
        model.addAttribute("timeValues", objectMapper.writeValueAsString(getTimeSeries(marcas)));
        model.addAttribute("timeLabels", objectMapper.writeValueAsString(actividadesTimeSeries(marcas)));
        model.addAttribute("marcas", marcas);
        return "reportes/personas/report";
    }

    private Set<String> actividadesTimeSeries(List<Marca> marcas) {
        Set<String> labels = new LinkedHashSet<>();
        for (Marca marca : marcas) {
            labels.add(marca.getActividad().getNombre());
        }
        return labels;
    }

    private List<Map> getTimeSeries(List<Marca> marcas) {
        Map<Date, Map<String, Integer>> totals = new HashMap<>();
        for (Marca marca : marcas) {
            if (!totals.containsKey(marca.getFecha())) {
                totals.put(marca.getFecha(), new HashMap<>());
            }

            if (!totals.get(marca.getFecha()).containsKey(marca.getActividad().getNombre())) {
                totals.get(marca.getFecha()).put(marca.getActividad().getNombre(), 0);
            }
            totals.get(marca.getFecha()).put(
                    marca.getActividad().getNombre(), totals.get(marca.getFecha()).get(
                            marca.getActividad().getNombre()) + marca.getDuracion());
        }
        List<Map> jsonValues = new ArrayList<>();
        for (Map.Entry<Date, Map<String, Integer>> entry : totals.entrySet()) {
            Map item = new HashMap();
            item.put("y", entry.getKey());
            for (Map.Entry<String, Integer> actividad : entry.getValue().entrySet()) {
                item.put(actividad.getKey(), actividad.getValue());
            }
            jsonValues.add(item);
        }
        return jsonValues;

    }

    private List<Map> getTotals(List<Marca> marcas) {
        Map<String, Integer> totals = new HashMap<>();
        for (Marca marca : marcas) {
            if (!totals.containsKey(marca.getActividad().getNombre())) {
                totals.put(marca.getActividad().getNombre(), 0);
            }
            totals.put(
                    marca.getActividad().getNombre(),
                    totals.get(marca.getActividad().getNombre()) + marca.getDuracion()
            );
        }
        List<Map> jsonValues = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : totals.entrySet()) {
            Map item = new HashMap();
            item.put("label", entry.getKey());
            item.put("value", entry.getValue());
            jsonValues.add(item);
        }
        return jsonValues;
    }

    private List<Map> getCalorias(List<Marca> marcas) {
        Map<String, Integer> totals = new HashMap<>();
        for (Marca marca : marcas) {
            if (!totals.containsKey(marca.getActividad().getNombre())) {
                totals.put(marca.getActividad().getNombre(), 0);
            }
            totals.put(
                    marca.getActividad().getNombre(),
                    totals.get(marca.getActividad().getNombre()) + marca.getCalorias()
            );
        }
        List<Map> jsonValues = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : totals.entrySet()) {
            Map item = new HashMap();
            item.put("label", entry.getKey());
            item.put("value", entry.getValue());
            jsonValues.add(item);
        }
        return jsonValues;
    }
}
