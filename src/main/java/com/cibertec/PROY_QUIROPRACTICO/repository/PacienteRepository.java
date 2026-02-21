package com.cibertec.PROY_QUIROPRACTICO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.PROY_QUIROPRACTICO.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, String> {
}