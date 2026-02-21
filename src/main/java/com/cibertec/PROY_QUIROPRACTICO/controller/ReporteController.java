package com.cibertec.PROY_QUIROPRACTICO.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.PROY_QUIROPRACTICO.repository.HojaPagoRepository;
import com.cibertec.PROY_QUIROPRACTICO.repository.DistritoRepository; // Necesario para el gráfico
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/gestionreporte")
public class ReporteController {

    @Autowired
    private HojaPagoRepository hojaPagoRepository;
    
    @Autowired
    private DistritoRepository distritoRepository;

    @GetMapping
    public String index(@RequestParam(name = "vista", required = false) String vista, 
                        Model model, HttpSession session) {
        
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        // 1. Cargamos siempre la lista de ventas (para el historial o el reporte de ingresos)
        model.addAttribute("listaVentas", hojaPagoRepository.findAll());

        // 2. Cargamos los datos del gráfico para evitar el error de "listaEstadistica"
        // Asegúrate de tener el método findAll() en tu DistritoRepository
        model.addAttribute("listaEstadistica", distritoRepository.findAll());

        return "Reportes"; 
    }
}