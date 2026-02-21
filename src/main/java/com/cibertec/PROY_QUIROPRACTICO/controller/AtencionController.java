package com.cibertec.PROY_QUIROPRACTICO.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cibertec.PROY_QUIROPRACTICO.repository.CitaRepository;

@Controller
@RequestMapping("/gestionatencion")
public class AtencionController {

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        
        model.addAttribute("listaCitas", citaRepository.findByEstado("PENDIENTE")); 
        return "AtencionCitas"; 
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("dni") String dni, Model model) {
        model.addAttribute("dniBusqueda", dni);
       
        model.addAttribute("listaCitas", citaRepository.findByDniPacienteAndEstado(dni, "PENDIENTE"));
        return "AtencionCitas";
    }

    @PostMapping("/marcar")
    public String marcarAsistencia(@RequestParam("id_cita") int idCita, 
                                   @RequestParam(value="dni_actual", required=false) String dni,
                                   @RequestParam(value="diagnostico", required=false) String notas) {
        
        citaRepository.marcarAsistencia(idCita, notas != null && !notas.isEmpty() ? notas : "Atención completada");
        
       
        if (dni != null && !dni.isEmpty()) {
            return "redirect:/gestionatencion/buscar?dni=" + dni;
        }
        return "redirect:/gestionatencion/listar";
    }
    @GetMapping("/historial")
    public String mostrarHistorial(Model model) {
        // Obtenemos solo los pacientes que ya pasaron por el ajuste quiropráctico
        model.addAttribute("listaAtendidos", citaRepository.findByEstado("ATENDIDO"));
        return "HistorialAtencion"; // Nombre del nuevo archivo HTML
    }
}