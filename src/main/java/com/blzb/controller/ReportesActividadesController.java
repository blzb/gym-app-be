package com.blzb.controller;

import com.blzb.data.dbo.Actividad;
import com.blzb.data.dbo.Marca;
import com.blzb.data.dbo.Persona;
import com.blzb.data.repository.ActividadRepository;
import com.blzb.data.repository.MarcaRepository;
import com.blzb.data.repository.PersonaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;
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
@RequestMapping("reportes/actividad")
public class ReportesActividadesController {
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @RequestMapping("")
    public String home(Model model) throws JsonProcessingException {
        List<Actividad> actividads = actividadRepository.findAll();
        model.addAttribute("actividades", actividads);
        Map<String, Integer> contadores = new TreeMap<>();
        List<Marca> marcas = marcaRepository.findAll();
        for(Actividad actividad: actividads){
            contadores.put(actividad.getNombre(), 0);
        }
        for (Marca marca : marcas) {
            contadores.put(marca.getActividad().getNombre(), contadores.get(marca.getActividad().getNombre()) + 1);
        }
        List<Map> jsonValues = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : contadores.entrySet()) {
            Map item = new HashMap();
            item.put("y", entry.getKey());
            item.put("totales", entry.getValue());
            jsonValues.add(item);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("contadores", objectMapper.writeValueAsString(jsonValues));
        return "reportes/actividades/list";
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
        model.addAttribute("actividad", actividad);
        return "reportes/actividades/report";
    }

    private Set<String> actividadesTimeSeries(List<Marca> marcas) {
        Set<String> labels = new LinkedHashSet<>();
        labels.add("minutos");
        return labels;
    }

    private List<Map> getTimeSeries(List<Marca> marcas) {
        Map<Date, Integer> totals = new HashMap<>();
        for (Marca marca : marcas) {
            if (!totals.containsKey(marca.getFecha())) {
                totals.put(marca.getFecha(), 0);
            }

            totals.put(marca.getFecha(), totals.get(marca.getFecha()) + marca.getDuracion());
        }
        List<Map> jsonValues = new ArrayList<>();
        for (Map.Entry<Date, Integer> entry : totals.entrySet()) {
            Map item = new HashMap();
            item.put("y", entry.getKey());
            item.put("minutos", entry.getValue());
            jsonValues.add(item);
        }
        return jsonValues;

    }

    private List<Map> getTotals(List<Marca> marcas) {
        Map<String, Integer> totals = new HashMap<>();
        for (Marca marca : marcas) {
            if (!totals.containsKey(marca.getPersona().getNombreCompleto())) {
                totals.put(marca.getPersona().getNombreCompleto(), 0);
            }
            totals.put(
                    marca.getPersona().getNombreCompleto(),
                    totals.get(marca.getPersona().getNombreCompleto()) + marca.getDuracion()
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
            if (!totals.containsKey(marca.getPersona().getNombreCompleto())) {
                totals.put(marca.getPersona().getNombreCompleto(), 0);
            }
            totals.put(
                    marca.getPersona().getNombreCompleto(),
                    totals.get(marca.getPersona().getNombreCompleto()) + marca.getCalorias()
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
