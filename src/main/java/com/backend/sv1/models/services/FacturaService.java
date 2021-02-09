package com.backend.sv1.models.services;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.backend.sv1.models.Factura;
import com.backend.sv1.pojos.ProductosBajosInventario;
import com.backend.sv1.pojos.ProductosInventario;
import com.backend.sv1.pojos.ProductosVentas;

import org.springframework.data.domain.Page;



public interface FacturaService {
	
	public List<Factura> findAll();

    public Factura save(Factura factura);

    public void delete(Long id);
    
    public Factura findById(Long id);
    
    public List<ProductosVentas> findProductoByFecha(String desde, String hasta);
    
    public List<ProductosBajosInventario> findProductosBajosEnInventario();
    
    public List<ProductosInventario> findProductosInventario();
    
    public List<ProductosInventario> findProductosInventarioPorCategoria(String categoria);
    
    public List<ProductosVentas> findProductosByFechaUsuario(String desde, String hasta, String username);
    
    public Page<Factura> findAll(Pageable pageable);
    
    public Page<Factura> findByUsuarioUsernameAndFecha(Pageable pageable,String username,String fecha);
    
    public Page<Factura> findByFecha(Pageable pageable,String fecha);
    
    
    
}
