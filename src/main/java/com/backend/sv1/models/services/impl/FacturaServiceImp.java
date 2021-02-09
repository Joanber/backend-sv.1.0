package com.backend.sv1.models.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.sv1.models.Factura;
import com.backend.sv1.models.repositories.FacturaRepository;
import com.backend.sv1.models.services.FacturaService;
import com.backend.sv1.pojos.ProductosBajosInventario;
import com.backend.sv1.pojos.ProductosInventario;
import com.backend.sv1.pojos.ProductosVentas;




@Service
public class FacturaServiceImp implements FacturaService {
	
	@Autowired
	private FacturaRepository facturaRepository;

	@Override
	public List<Factura> findAll() {
		return facturaRepository.findAll();
	}

	@Override
	public Factura save(Factura factura) {
		return facturaRepository.save(factura);
	}

	@Override
	public void delete(Long id) {
		facturaRepository.deleteById(id);
	}

	@Override
	public Factura findById(Long id) {
		return facturaRepository.findById(id).orElse(null);
	}
	@Override
	public List<ProductosVentas> findProductoByFecha(String desde, String hasta) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date desdee = null;
		try {
			desdee = formater.parse(desde.concat(" 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    Date hastaa = null;
		try {
			hastaa = formater.parse(hasta.concat(" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return facturaRepository.findProductosByFecha(desdee, hastaa);
	}

	@Override
	public List<ProductosBajosInventario> findProductosBajosEnInventario() {
		return facturaRepository.findProductosBajosEnInventario();
	}

	@Override
	public List<ProductosInventario> findProductosInventario() {
		return facturaRepository.findProductosInventario();
	}

	@Override
	public List<ProductosInventario> findProductosInventarioPorCategoria(String categoria) {
		return facturaRepository.findProductosInventarioPorCategoria(categoria);
	}

	@Override
	public List<ProductosVentas> findProductosByFechaUsuario(String desde, String hasta, String username) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date desdee = null;
		try {
			desdee = formater.parse(desde.concat(" 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    Date hastaa = null;
		try {
			hastaa = formater.parse(hasta.concat(" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return facturaRepository.findProductosByFechaUsuario(desdee, hastaa, username);
	}

	@Override
	public Page<Factura> findAll(Pageable pageable) {
		return facturaRepository.findAll(pageable);
	}	

	@Override
	public Page<Factura> findByUsuarioUsernameAndFecha(Pageable pageable, String username, String fecha) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	    Date fecha1 = null;
		try {
			fecha1 = formater.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return facturaRepository.findByUsuarioUsernameAndFecha(pageable, username, fecha1) ;
	}

	@Override
	public Page<Factura> findByFecha(Pageable pageable, String fecha) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	    Date fecha1 = null;
		try {
			fecha1 = formater.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return facturaRepository.findByFecha(pageable, fecha1);
	}

	


	

}
