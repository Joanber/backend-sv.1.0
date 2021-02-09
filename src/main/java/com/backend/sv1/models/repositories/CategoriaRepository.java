package com.backend.sv1.models.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.sv1.models.Categoria;




public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	public List<Categoria> findAllByOrderByIdAsc();
	
	public Categoria findByNombre(String nombre);
	
	@Query("select c from Categoria c order by id desc")
	public Page<Categoria> findAll(Pageable pageable);
	
	@Query("select c from Categoria c where c.nombre like %?1%")
	public List<Categoria> findCategotriasByNombre(String termino);

}
