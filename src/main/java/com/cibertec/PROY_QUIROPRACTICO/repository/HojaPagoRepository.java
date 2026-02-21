package com.cibertec.PROY_QUIROPRACTICO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.PROY_QUIROPRACTICO.model.HojaPago;

@Repository
public interface HojaPagoRepository extends JpaRepository<HojaPago, Integer> {
    
}