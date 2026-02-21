package com.cibertec.PROY_QUIROPRACTICO.model;

import jakarta.persistence.*;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tb_paciente")
public class Paciente {

	@Id
	@Column(name = "dni", length = 8)
	private String dni;

	@Column(name = "nombres", nullable = false)
	private String nombres;

	@Column(name = "apellidos", nullable = false)
	private String apellidos;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Para que Spring entienda la fecha del HTML
	@Column(name = "fecha_nac")
	private Date fechaNac;

	@Column(name = "sexo", length = 1)
	private String sexo;

	@Column(name = "estado_civil", length = 1)
	private String estadoCivil;

	@Column(name = "ocupacion")
	private String ocupacion;
	
	@Column(name = "num_hijos")
	private Integer numHijos;
	
	@Column(name = "telefono")
	private String telefono;

	@Column(name = "email")
	private String email;

	@Column(name = "direccion")
	private String direccion;

	@ManyToOne
	@JoinColumn(name = "cod_distrito") 
	private Distrito distrito;

	// --- NUEVA RELACIÓN: 1 a 1 con Historia Clínica ---
	// "cascade = CascadeType.ALL" significa: Si guardo Paciente, guarda también su Historia.
	@OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
	private HistoriaClinica historiaClinica;

	// Método auxiliar para asignar la historia (vinculación bidireccional)
	public void asignarHistoria(HistoriaClinica historia) {
		this.historiaClinica = historia;
		historia.setPaciente(this); // Vincula el paciente a la historia
	}
	@Column(name = "zonas_dolor")
	private String zonasDolor; // Debe ser String para guardar la unión de los checkboxes

	public void setZonasDolor(String zonasDolor) {
	    this.zonasDolor = zonasDolor;
	}

	public String getZonasDolor() {
	    return zonasDolor;
	}
	public Paciente() {}

	// GETTERS Y SETTERS
	public String getDni() { return dni; }
	public void setDni(String dni) { this.dni = dni; }
	public String getNombres() { return nombres; }
	public void setNombres(String nombres) { this.nombres = nombres; }
	public String getApellidos() { return apellidos; }
	public void setApellidos(String apellidos) { this.apellidos = apellidos; }
	public Date getFechaNac() { return fechaNac; }
	public void setFechaNac(Date fechaNac) { this.fechaNac = fechaNac; }
	public String getSexo() { return sexo; }
	public void setSexo(String sexo) { this.sexo = sexo; }
	public String getEstadoCivil() { return estadoCivil; }
	public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
	public String getOcupacion() { return ocupacion; }
	public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }
	public Integer getNumHijos() { return numHijos; }
	public void setNumHijos(Integer numHijos) { this.numHijos = numHijos; }
	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getDireccion() { return direccion; }
	public void setDireccion(String direccion) { this.direccion = direccion; }
	public Distrito getDistrito() { return distrito; }
	public void setDistrito(Distrito distrito) { this.distrito = distrito; }
	
	public HistoriaClinica getHistoriaClinica() { return historiaClinica; }
	public void setHistoriaClinica(HistoriaClinica historiaClinica) { this.historiaClinica = historiaClinica; }
}