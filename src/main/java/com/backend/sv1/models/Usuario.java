/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.sv1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Andy
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 30)
	private String username;

	@Column(nullable = false, length = 60)
	private String password;

	@Column(name = "activo")
	private boolean activo;
	
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id",updatable = true),inverseJoinColumns = @JoinColumn(name = "rol_id",updatable = true),uniqueConstraints = {
			@UniqueConstraint(columnNames = { "usuario_id", "rol_id" }) })
	public List<Rol> roles;

	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Persona persona;
	
	@JsonIgnoreProperties(value={"usuario","hibernateLazyInitializer","handler"}, allowSetters=true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private List<Factura> facturas;
	
	public Usuario() {
		this.roles=new ArrayList<>();
	}
	
	public void addRol(Rol rol) {
		this.roles.add(rol);
	}
	
	public void removeRol(Rol rol) {
		this.roles.remove(rol);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<Rol> getRoles() {
		return roles;
	}
	

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario a =(Usuario) obj;
		
		return this.id != null && this.id.equals(a.getId());
	}

}
