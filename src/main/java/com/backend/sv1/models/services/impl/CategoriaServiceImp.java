package com.backend.sv1.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.sv1.models.Categoria;
import com.backend.sv1.models.repositories.CategoriaRepository;
import com.backend.sv1.models.services.CategoriaService;


@Service
public class CategoriaServiceImp implements CategoriaService{
	  
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findAllByOrderByIdAsc() {
		return categoriaRepository.findAllByOrderByIdAsc();
	}

	@Override
	public Categoria findByNombre(String nombre) {
		return categoriaRepository.findByNombre(nombre);
	}

	@Override
	public Page<Categoria> findAll(Pageable pageable) {
		return categoriaRepository.findAll(pageable);
	}

	@Override
	public List<Categoria> findCategotriasByNombre(String termino) {
		return categoriaRepository.findCategotriasByNombre(termino);
	}

}
