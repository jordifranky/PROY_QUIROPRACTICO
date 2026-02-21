package com.cibertec.PROY_QUIROPRACTICO.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.PROY_QUIROPRACTICO.model.Usuario;
import com.cibertec.PROY_QUIROPRACTICO.repository.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

   
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    // doGet -> Mostrar login
    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/index";
        }
        return "Login"; 
    }

    // doPost -> Procesar el ingreso
    @PostMapping("/login")
    public String login(@RequestParam("email") String email, 
                        @RequestParam("clave") String clave, 
                        HttpSession session, 
                        Model model) {
        
        Usuario vendedor = usuarioRepository.findByEmailAndClave(email, clave);

        if (vendedor != null) {
            session.setAttribute("usuarioLogueado", vendedor);
            return "redirect:/index"; 
        } else {
            model.addAttribute("mensajeError", "Usuario o contraseña incorrectos");
            return "Login";
        }
    }

    // doGet -> Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuarioLogueado"); // 
        session.invalidate(); 
        return "redirect:/login?logout"; // 
    }
}