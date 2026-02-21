package com.cibertec.PROY_QUIROPRACTICO.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.PROY_QUIROPRACTICO.model.HojaPago;
import com.cibertec.PROY_QUIROPRACTICO.model.Usuario;
import com.cibertec.PROY_QUIROPRACTICO.repository.HojaPagoRepository;
import com.cibertec.PROY_QUIROPRACTICO.repository.PacienteRepository;
import com.cibertec.PROY_QUIROPRACTICO.repository.TratamientoRepository;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/gestionventa")
public class VentaController {

    @Autowired
    private HojaPagoRepository hojaPagoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    // doGet -> accion=nueva
    @GetMapping("/nueva")
    public String nuevaVenta(Model model) {
        model.addAttribute("listaPacientes", pacienteRepository.findAll());
        model.addAttribute("listaTratamientos", tratamientoRepository.findAll());
        return "NuevaVenta"; // NuevaVenta.jsp
    }

 // doPost -> accion=registrar
    @PostMapping("/registrar")
    public String registrarVenta(@RequestParam("dniPaciente") String dniPaciente, 
                                @RequestParam("totalSesiones") int totalSesiones, 
                                @RequestParam("costoTotal") double costoTotal,    
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
            if (u == null) return "redirect:/login";

            HojaPago hp = new HojaPago();
            hp.setDniPaciente(dniPaciente);
            hp.setIdUsuario(u.getIdUsuario());
            hp.setTotalSesiones(totalSesiones);
            hp.setCostoTotal(costoTotal);

            hojaPagoRepository.save(hp);
            
            
            redirectAttributes.addAttribute("exito", "1");
            
         return "redirect:/gestionreporte?vista=historial"; 
            
        } catch (Exception e) {
            e.printStackTrace();
            // Si hay un error, nos quedamos en la página de Nueva Venta para que el usuario intente de nuevo
            redirectAttributes.addAttribute("error", "1"); 
            return "redirect:/gestionventa/nueva";
        }
    }

    @GetMapping("/exportarPDF")
    public void exportarPDF(@RequestParam("nro_hp") Integer nroHp, HttpServletResponse response) {
        try {
            HojaPago venta = hojaPagoRepository.findById(nroHp).orElse(null);
            if (venta == null) return;

            // 1. CARGAMOS EL REPORTE
            InputStream reporteStream = new ClassPathResource("reportes/boleta.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);

            // 2. CONFIGURAMOS PARÁMETROS
            Map<String, Object> parametros = new HashMap<>();
            
            // Carga del logo desde la carpeta estática
            try {
                InputStream logoStream = new ClassPathResource("static/img/Logo-McLellan.png").getInputStream();
                parametros.put("logoPath", logoStream); 
            } catch (Exception e) {
                parametros.put("logoPath", null); // Si falla el logo, que el PDF siga adelante
            }

            parametros.put("nroBoleta", String.format("BO-%06d", venta.getNroHp()));
            parametros.put("nombrePaciente", venta.getDniPaciente());
            parametros.put("descripcion", "Tratamiento Quiropráctico McLellan");
            parametros.put("cantidad", venta.getTotalSesiones());
            
            double unitario = venta.getCostoTotal() / (venta.getTotalSesiones() > 0 ? venta.getTotalSesiones() : 1);
            parametros.put("precioUnitario", unitario);
            parametros.put("totalPagar", venta.getCostoTotal());

            // 3. GENERAR PDF (DataSource de 1 fila)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource(1));

            // 4. RESPUESTA AL NAVEGADOR
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=Boleta_" + nroHp + ".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            System.err.println("ERROR EN PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}