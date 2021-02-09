package com.backend.sv1.pojos;

import java.util.Date;

public interface ProductosVentas {
	
	public String getCodigo_barras();

	public String getNombre();

	public int getCantidad();

	public double getPrecio();

	public String getNombre_categoria();
	
	public Date  getFecha();
	
	public String getDatos_persona();
	
	public double getPrecio_compra();
	
	public Long getId();
	
}
