/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.sv1.models.services.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.sv1.models.Persona;
import com.backend.sv1.models.repositories.PersonaRepository;
import com.backend.sv1.models.services.PersonaService;

/**
 *
 * @author Andy
 */
@Service
public class PersonaServiceImp implements PersonaService{
    
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        return personaRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona findById(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

	@Override
	@Transactional(readOnly = true)
	public List<Persona> findAllByOrderByIdAsc() {
		return personaRepository.findAllByOrderByIdAsc();
	}

	@Override
	public List<Persona> findByNombreOrApellidoIgnoreCase(String nombre) {
		return personaRepository.findByNombreOrApellidoIgnoreCase(nombre);
	}

	@Override
	public Page<Persona> findAll(Pageable pageable) {
		return personaRepository.findAll(pageable);
	}

	@Override
	public Persona findByCedula(String cedula) {
		return personaRepository.findByCedula(cedula);
	}
    
}
