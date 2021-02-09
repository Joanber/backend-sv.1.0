/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.sv1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



/**
 *
 * @author Andy
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50,unique = true)
	private String codigo_barras;

	@Column(nullable = false, length = 300)
	private String nombre;

	@Column(nullable = true, length = 300)
	private String descripcion;

	@Column(nullable = false,precision = 10, scale = 2 )
	private Double precio;
	
	@Column(nullable = false,precision = 10, scale = 2 )
	private Double precio_compra;

	@Column(nullable = false,precision = 10, scale = 2 )
	private Double cantidad_maxima;

	@Column(nullable = false)
	private Double cantidad_minima;

	@JsonIgnore
	private byte[] foto;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Categoria categoria;

	public Producto() {
	}
	public Integer getFotoHashCode(){
        return (this.foto!=null)? this.foto.hashCode():null;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo_barras() {
		return codigo_barras;
	}

	public void setCodigo_barras(String codigo_barras) {
		this.codigo_barras = codigo_barras.toUpperCase();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}
	
	public Double getPrecio_compra() {
		return precio_compra;
	}
	public void setPrecio_compra(Double precio_compra) {
		this.precio_compra = precio_compra;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Double getCantidad_maxima() {
		return cantidad_maxima;
	}
	public double getCantidad_minima() {
		return cantidad_minima;
	}

	public void setCantidad_minima(double cantidad_minima) {
		this.cantidad_minima = cantidad_minima;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public void setCantidad_maxima(Double cantidad_maxima) {
		this.cantidad_maxima = cantidad_maxima;
	}

	public void setCantidad_minima(Double cantidad_minima) {
		this.cantidad_minima = cantidad_minima;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
