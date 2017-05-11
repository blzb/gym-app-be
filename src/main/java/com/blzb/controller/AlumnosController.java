package com.blzb.controller;

import com.blzb.data.repository.MateriaRepository;
import com.blzb.data.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by apimentel on 5/9/17.
 */
@Controller
public class AlumnosController {
    @Autowired
    PersonaRepository personaRepository;
    @RequestMapping("alumnos")
    public String home(Model model) {
        model.addAttribute("alumnos", personaRepository.findAll());
        return "alumnos/list";
    }
}
