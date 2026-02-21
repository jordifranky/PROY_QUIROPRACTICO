package com.cibertec.PROY_QUIROPRACTICO.controller;

import java.util.List; // Import necesario para la lista
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.PROY_QUIROPRACTICO.model.Usuario;
import com.cibertec.PROY_QUIROPRACTICO.repository.UsuarioRepository;

@Controller
@RequestMapping("/gestionusuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar usuarios (reemplaza doGet accion=listar)
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Usuario> lista = usuarioRepository.findAll(); // Ya no dará error por el extends
        model.addAttribute("listaUsuarios", lista);
        return "MantUsuario"; // Busca MantUsuario.jsp
    }

    // Registrar o Actualizar (reemplaza doPost)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuario") Usuario u, RedirectAttributes redirectAttributes) {
        try {
            // save() registra si el ID es 0/null y actualiza si el ID ya existe
            usuarioRepository.save(u);
            redirectAttributes.addFlashAttribute("mensaje", "¡Usuario guardado correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al procesar el usuario.");
        }
        return "redirect:/gestionusuario/listar";
    }

    // Eliminar usuario (reemplaza doGet accion=eliminar)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            usuarioRepository.deleteById(id); // Ya no dará error por el extends
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el usuario.");
        }
        return "redirect:/gestionusuario/listar";
    }
}