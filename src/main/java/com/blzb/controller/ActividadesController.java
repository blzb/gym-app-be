package com.blzb.controller;

import com.blzb.data.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apimentel on 5/9/17.
 */
@Controller
public class ActividadesController {
    @Autowired
    ActividadRepository actividadRepository;
    @RequestMapping("actividad")
    public String home(Model model) {
        model.addAttribute("actividades", actividadRepository.findAll());
        return "actividad/list";
    }
}
