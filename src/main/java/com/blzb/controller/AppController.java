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
    public String home(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getSession().getAttribute("userId") != null){
            return "home";
        }else{
            return "redirect:/login";
        }
    }
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginGET(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getSession().getAttribute("userId") != null){
            return "redirect:/";
        }else{
            return "login";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getSession().getAttribute("userId") != null){
            httpServletRequest.getSession().invalidate();
            return "redirect:/";
        }else{
            return "login";
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPOST(Map<String, String> values, Model model, HttpServletRequest httpServletRequest){
        String email = values.get("email");
        String password = values.get("password");
        Persona person = personaRepository.findByCorreoAndPassword(email, password);
        if(person != null){
            httpServletRequest.getSession().setAttribute("userId", person.getId());
            httpServletRequest.getSession().setAttribute("user", person);
            return "home";
        }else{
            model.addAttribute("mensaje", "Usuario no Encontrado");
            return "login";
        }

    }
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String registerGET(){
        return "register";
    }
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerPOST(Persona persona){
        personaRepository.save(persona);
        return "home";
    }

}