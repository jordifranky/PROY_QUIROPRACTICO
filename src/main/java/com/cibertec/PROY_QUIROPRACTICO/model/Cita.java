package com.cibertec.PROY_QUIROPRACTICO.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_consulta") // <--- ¡AQUÍ ESTÁ LA MAGIA! Apunta a la tabla correcta
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta") // La llave primaria en BD es id_consulta
    private Integer idCita;

    @Column(name = "dni_paciente")
    private String dniPaciente; // Coincide con name="dniPaciente" en tu HTML

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha; // Coincide con name="fecha" en tu HTML

    @Column(name = "hora")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hora; // Coincide con name="hora" en tu HTML

    @Column(name = "motivo")
    private String motivo;  // Coincide con name="motivo" en tu HTML

    @Column(name = "estado")
    private String estado;
    
    @Column(name = "diagnostico")
    private String diagnostico; // Este se queda vacío al principio

    public Cita() {
        // Constructor vacío necesario
    }

    // --- GETTERS Y SETTERS ---

    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public String getDniPaciente() { return dniPaciente; }
    public void setDniPaciente(String dniPaciente) { this.dniPaciente = dniPaciente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
}