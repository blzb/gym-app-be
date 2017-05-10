package com.blzb.controller;

import com.blzb.data.dbo.Persona;
import com.blzb.data.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by apimentel on 4/24/17.
 */
@Controller
public class AppController {
    @Autowired
    PersonaRepository personaRepository;

    @RequestMapping("")
    public String home(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("userId") != null) {
            return "home";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginGET(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("userId") != null) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("userId") != null) {
            httpServletRequest.getSession().invalidate();
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPOST( Model model, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("password");
        System.out.println(email);
        System.out.println(password);
        Persona person = personaRepository.findByCorreoAndPassword(email, password);
        if (person != null) {
            httpServletRequest.getSession().setAttribute("userId", person.getId());
            httpServletRequest.getSession().setAttribute("user", person);
            httpServletRequest.getSession().setAttribute("rol", "estudiante");
            return "home";
        } else if ("admin@ipn.mx".equalsIgnoreCase(email) && "admin".equalsIgnoreCase(password)) {
            Persona admin = new Persona();
            admin.setNombre("Administrador");
            admin.setApellido("Gimnasio");
            httpServletRequest.getSession().setAttribute("userId", -1);
            httpServletRequest.getSession().setAttribute("user", admin);
            httpServletRequest.getSession().setAttribute("rol", "admin");
            return "home";
        } else {
            model.addAttribute("mensaje", "Usuario no Encontrado");
            return "login";
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String registerGET() {
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerPOST(Persona persona, HttpServletRequest httpServletRequest) {
        persona = personaRepository.save(persona);
        httpServletRequest.getSession().setAttribute("userId", persona.getId());
        httpServletRequest.getSession().setAttribute("user", persona);
        return "home";
    }

}