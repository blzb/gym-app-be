package com.blzb.controller;

import com.blzb.data.dbo.Materia;
import com.blzb.data.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by apimentel on 5/9/17.
 */
@Controller
public class MateriasController {
    @Autowired
    MateriaRepository materiaRepository;
    @RequestMapping("materias")
    public String home(Model model) {
        model.addAttribute("materias", materiaRepository.findAll());
        return "materias/list";
    }
}
