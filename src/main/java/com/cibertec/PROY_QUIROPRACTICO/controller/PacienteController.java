package com.cibertec.PROY_QUIROPRACTICO.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.PROY_QUIROPRACTICO.model.Paciente;
import com.cibertec.PROY_QUIROPRACTICO.model.Distrito;
import com.cibertec.PROY_QUIROPRACTICO.repository.PacienteRepository;
import com.cibertec.PROY_QUIROPRACTICO.repository.DistritoRepository;

@Controller
@RequestMapping("/gestionpaciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DistritoRepository distritoRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("listaPacientes", pacienteRepository.findAll());
        model.addAttribute("listaDistritos", distritoRepository.findAll());
        // Si no hay un paciente en edición, enviamos uno vacío para el formulario de registro
        if (!model.containsAttribute("pacienteEditar")) {
            model.addAttribute("pacienteEditar", new Paciente());
        }
        return "MantPaciente";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("paciente") Paciente p, 
                          @RequestParam(value = "chk_zona", required = false) String[] zonasSeleccionadas,
                          RedirectAttributes redirectAttributes) {
        try {
            // Lógica para las zonas de dolor (String.join)
        	if (zonasSeleccionadas != null) {
        	    String unidos = String.join(", ", zonasSeleccionadas);
        	    p.setZonasDolor(unidos);
        	}

            pacienteRepository.save(p); // save() registra si es nuevo o actualiza si el ID/DNI existe
            redirectAttributes.addFlashAttribute("mensaje", "¡Operación realizada con éxito!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al procesar los datos.");
        }
        return "redirect:/gestionpaciente/listar";
    }

    @GetMapping("/editar/{dni}")
    public String editar(@PathVariable("dni") String dni, RedirectAttributes redirectAttributes) {
        Paciente p = pacienteRepository.findById(dni).orElse(null);
        redirectAttributes.addFlashAttribute("pacienteEditar", p);
        return "redirect:/gestionpaciente/listar";
    }

    @GetMapping("/eliminar/{dni}")
    public String eliminar(@PathVariable("dni") String dni, RedirectAttributes redirectAttributes) {
        try {
            pacienteRepository.deleteById(dni);
            redirectAttributes.addFlashAttribute("mensaje", "Paciente eliminado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el paciente.");
        }
        return "redirect:/gestionpaciente/listar";
    }
}