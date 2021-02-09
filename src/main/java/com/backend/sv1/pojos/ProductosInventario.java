package com.backend.sv1.pojos;

public interface ProductosInventario {
	public String getCodigo_barras();

	public String getNombre();

	public double getCantidad_maxima();
	
	public double getCantidad_minima();
	
	public double getPrecio();

	public String getNombre_categoria();

}
