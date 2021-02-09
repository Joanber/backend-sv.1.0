/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.sv1.models.repositories;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.sv1.models.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
	public List<Persona> findAllByOrderByIdAsc();
	
	@Query("select a from Persona a where a.nombre like %?1% or a.apellido like %?1%")
	public List<Persona> findByNombreOrApellidoIgnoreCase(String termino);
	
	@Query("select a from Persona a order by id desc")
	public Page<Persona> findAll(Pageable pageable);
	
	public Persona findByCedula(String cedula);
}
