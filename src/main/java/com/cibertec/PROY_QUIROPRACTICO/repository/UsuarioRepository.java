package com.cibertec.PROY_QUIROPRACTICO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.PROY_QUIROPRACTICO.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Al heredar de JpaRepository, ya tienes listos:
    // .findAll(), .save(), .deleteById(), .findById()
    
    // Este método lo usaremos para el login que vimos antes
    Usuario findByEmailAndClave(String email, String clave);
}