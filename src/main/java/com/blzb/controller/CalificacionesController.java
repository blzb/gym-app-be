package com.blzb.controller;

import com.blzb.data.dbo.Calificacion;
import com.blzb.data.dbo.Materia;
import com.blzb.data.dbo.Persona;
import com.blzb.data.repository.CalificacionRepository;
import com.blzb.data.repository.MateriaRepository;
import com.blzb.data.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by apimentel on 5/9/17.
 */
@Controller
@RequestMapping("calificaciones")
public class CalificacionesController {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    CalificacionRepository calificacionRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String get(Model model, HttpSession httpSession) {
        Long userId = Long.parseLong(httpSession.getAttribute("userId").toString());
        Persona persona = personaRepository.getOne(userId);
        model.addAttribute("materias", materiaRepository.findByCarreraOrderBySemestreAsc(persona.getCarrera()));
        model.addAttribute("persona", persona);
        return "calificacion/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String home(@PathVariable Long id, Model model, HttpSession httpSession) {
        Long userId = Long.parseLong(httpSession.getAttribute("userId").toString());
        Persona persona = personaRepository.getOne(userId);
        Materia materia = materiaRepository.getOne(id);
        List<Calificacion> calificacionList = calificacionRepository.findByMateria(materia);
        model.addAttribute("calificaciones", calificacionList);
        model.addAttribute("persona", persona);
        model.addAttribute("materia", materia);
        model.addAttribute("today", simpleDateFormat.format(new Date()));
        return "calificacion/calificaciones";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public String save(@PathVariable Long id, Model model, HttpServletRequest request) throws ParseException {
        Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
        Persona persona = personaRepository.getOne(userId);
        Materia materia = materiaRepository.getOne(id);
        String valor = request.getParameter("valor");
        String fecha = request.getParameter("fecha");
        String tipo = request.getParameter("tipo");

        Calificacion calificacion = new Calificacion();
        calificacion.setValor(Float.parseFloat(valor));
        calificacion.setMateria(materia);
        calificacion.setPersona(persona);
        calificacion.setTipo(tipo);
        if (fecha != null) {
            calificacion.setFecha(simpleDateFormat.parse(fecha));
        } else {
            calificacion.setFecha(new Date());
        }
        calificacionRepository.save(calificacion);
        return "redirect:/calificaciones/"+id;
    }
}
