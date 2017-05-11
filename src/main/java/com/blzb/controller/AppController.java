package com.blzb.controller;

import com.blzb.data.dbo.Estado;
import com.blzb.data.dbo.Persona;
import com.blzb.data.repository.EstadoRepository;
import com.blzb.data.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by apimentel on 4/24/17.
 */
@Controller
public class AppController {
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    EstadoRepository estadoRepository;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

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
    public String loginPOST(Model model, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getParameter("boleta");
        String password = httpServletRequest.getParameter("password");
        System.out.println(email);
        System.out.println(password);
        Persona person = personaRepository.findByBoletaAndPassword(email, password);
        if (person != null) {
            httpServletRequest.getSession().setAttribute("userId", person.getId());
            httpServletRequest.getSession().setAttribute("user", person);
            httpServletRequest.getSession().setAttribute("rol", "estudiante");
            return "home";
        } else if ("admin".equalsIgnoreCase(email) && "admin".equalsIgnoreCase(password)) {
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
    public String registerPOST(HttpServletRequest httpServletRequest) throws ParseException {
        String nombre = httpServletRequest.getParameter("nombre");
        String apellido = httpServletRequest.getParameter("apellido");
        String sexo = httpServletRequest.getParameter("sexo");
        String fechaNacimiento = httpServletRequest.getParameter("fechaNacimiento");
        String boleta = httpServletRequest.getParameter("boleta");
        String password = httpServletRequest.getParameter("password");
        String peso = httpServletRequest.getParameter("peso");
        String estatura = httpServletRequest.getParameter("estatura");
        String carrera = httpServletRequest.getParameter("carrera");
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setBoleta(boleta);
        persona.setSexo(sexo);
        persona.setCarrera(carrera);
        persona.setFechaNacimiento(simpleDateFormat.parse(fechaNacimiento));
        persona.setPassword(password);
        persona = personaRepository.save(persona);
        Estado estado = new Estado();
        estado.setEstatura(Float.parseFloat(estatura));
        estado.setPeso(Float.parseFloat(peso));
        estado.setPersona(persona);
        estado = estadoRepository.save(estado);
        httpServletRequest.getSession().setAttribute("userId", persona.getId());
        httpServletRequest.getSession().setAttribute("user", persona);
        return "redirect:/";
    }

}