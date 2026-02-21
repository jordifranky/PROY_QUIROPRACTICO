package com.cibertec.PROY_QUIROPRACTICO.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_historia_clinica")
public class HistoriaClinica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_historia")
	private Integer idHistoria;

	@Column(name = "motivo_consulta")
	private String motivoConsulta;

	@Column(name = "zonas_dolor")
	private String zonasDolor;

	@Column(name = "antecedente_trauma")
	private String antecedenteTrauma;

	@Column(name = "antecedente_cirugia")
	private String antecedenteCirugia;

	@Column(name = "escala_dolor_inicial")
	private Integer escalaDolor;

	@Column(name = "tiene_asma")
	private Boolean tieneAsma;

	@Column(name = "tiene_diabetes")
	private Boolean tieneDiabetes;

	@Column(name = "tiene_alergias")
	private Boolean tieneAlergias;

	@Column(name = "observaciones")
	private String observaciones;

	// RELACIÓN INVERSA: Esta historia pertenece a un Paciente
	@OneToOne
	@JoinColumn(name = "dni_paciente") // FK en la tabla historia
	private Paciente paciente;

	// Getters y Setters
	public Integer getIdHistoria() { return idHistoria; }
	public void setIdHistoria(Integer idHistoria) { this.idHistoria = idHistoria; }
	public String getMotivoConsulta() { return motivoConsulta; }
	public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }
	public String getZonasDolor() { return zonasDolor; }
	public void setZonasDolor(String zonasDolor) { this.zonasDolor = zonasDolor; }
	public String getAntecedenteTrauma() { return antecedenteTrauma; }
	public void setAntecedenteTrauma(String antecedenteTrauma) { this.antecedenteTrauma = antecedenteTrauma; }
	public String getAntecedenteCirugia() { return antecedenteCirugia; }
	public void setAntecedenteCirugia(String antecedenteCirugia) { this.antecedenteCirugia = antecedenteCirugia; }
	public Integer getEscalaDolor() { return escalaDolor; }
	public void setEscalaDolor(Integer escalaDolor) { this.escalaDolor = escalaDolor; }
	public Boolean getTieneAsma() { return tieneAsma; }
	public void setTieneAsma(Boolean tieneAsma) { this.tieneAsma = tieneAsma; }
	public Boolean getTieneDiabetes() { return tieneDiabetes; }
	public void setTieneDiabetes(Boolean tieneDiabetes) { this.tieneDiabetes = tieneDiabetes; }
	public Boolean getTieneAlergias() { return tieneAlergias; }
	public void setTieneAlergias(Boolean tieneAlergias) { this.tieneAlergias = tieneAlergias; }
	public String getObservaciones() { return observaciones; }
	public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
	public Paciente getPaciente() { return paciente; }
	public void setPaciente(Paciente paciente) { this.paciente = paciente; }
}