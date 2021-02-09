package com.backend.sv1.models.repositories;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.sv1.models.Producto;



@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	public List<Producto> findAllByOrderByIdAsc();
	
	@Query("select p from Producto p join fetch p.categoria c where c.nombre=?1 order by p.id")
	public List<Producto> findProductoByCategoriaNombre(String nombre);
	
	@Query("select p from Producto p  where  p.nombre like %?1% or p.codigo_barras like %?1%")
	public List<Producto> findByNombreOrCodigoBarras(String termino);
	
	@Query("select p from Producto p  where   p.codigo_barras=?1")
	public Producto findProductoByCodigoBarras(String codigo);
	
	@Query("select p from Producto p  order by id desc")
	public Page<Producto> findAll(Pageable pageable);
	


}
