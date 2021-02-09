package com.backend.sv1.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.sv1.models.Producto;
import com.backend.sv1.models.services.ProductoService;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/productos")
public class ProductoController {
	@Autowired
	private ProductoService productoService;

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Producto newProducto = null;
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			newProducto = productoService.save(producto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error  en la inserccion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Producto insertado con exito");
		response.put("producto", newProducto);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Producto producto = null;
		Map<String, Object> response = new HashMap<>();

		try {
			producto = productoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error  en la consulta de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (producto == null) {
			response.put("mensaje", "El producto ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	//SIRVE TAMBIEN PARA EL CODIGO DE BARRAS EXISTENTE
	@GetMapping("/codigo/{codigo}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public Producto getByCodigoBarras(@PathVariable String codigo) {
		return productoService.findProductoByCodigoBarras(codigo);
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public List<Producto> getProductos() {
		return productoService.findAll();
	}
	
	@GetMapping("/movilapp")
	public List<Producto> getProductosMovilApp() {
		return productoService.findAll();
	}
	
	@GetMapping("/filtrar/{termino}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public List<Producto> getProductos(@PathVariable String termino){
    	return productoService.findByNombreOrCodigoBarras(termino);
    }
	
	@GetMapping("/categoria/{nombre}")
	public List<Producto> getProductosByCategoria(@PathVariable String nombre) {
		return productoService.findProductoByCategoriaNombre(nombre);
	}
	

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> update(@Valid @RequestBody Producto producto, BindingResult result,
			@PathVariable Long id) {
		Producto pro = productoService.findById(id);

		Producto productoUpdate = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (pro == null) {
			response.put("mensaje", "Error:no se pudo editar, el proente ID: ".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			pro.setCodigo_barras(producto.getCodigo_barras());
			pro.setNombre(producto.getNombre());
			pro.setDescripcion(producto.getDescripcion());
			pro.setPrecio(producto.getPrecio());
			pro.setCantidad_maxima(producto.getCantidad_maxima());
			pro.setCantidad_minima(producto.getCantidad_minima());
			pro.setPrecio_compra(producto.getPrecio_compra());
			pro.setCategoria(producto.getCategoria());

			productoUpdate = productoService.save(pro);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el proente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Producto actualizado con exito");
		response.put("producto", productoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	@PutMapping("/codigo/{codigo}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> updateCantidad(@Valid @RequestBody Producto producto, BindingResult result,
			@PathVariable String codigo) {
		Producto pro = productoService.findProductoByCodigoBarras(codigo);
		

		Producto productoUpdate = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (pro == null) {
			response.put("mensaje", "Error:no se pudo editar, el proente ID: ".concat(codigo.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			
			pro.setCantidad_maxima(producto.getCantidad_maxima()+pro.getCantidad_maxima());
			pro.setCategoria(producto.getCategoria());
			

			productoUpdate = productoService.save(pro);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el proente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Producto actualizado con exito");
		response.put("producto", productoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/editar-con-foto/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateconfoto(@Valid Producto producto, BindingResult result, @PathVariable Long id,
			@RequestParam MultipartFile archivo) {
		Producto pro = productoService.findById(id);

		Producto productoUpdate = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (pro == null) {
			response.put("mensaje", "Error:no se pudo editar, el proente ID: ".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			pro.setCodigo_barras(producto.getCodigo_barras());
			pro.setNombre(producto.getNombre());
			pro.setDescripcion(producto.getDescripcion());
			pro.setPrecio(producto.getPrecio());
			pro.setCantidad_maxima(producto.getCantidad_maxima());
			pro.setCantidad_minima(producto.getCantidad_minima());
			pro.setPrecio_compra(producto.getPrecio_compra());
			pro.setCategoria(producto.getCategoria());
			if (!archivo.isEmpty()) {
				try {
					pro.setFoto(archivo.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			productoUpdate = productoService.save(pro);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Producto actualizado con exito");
		response.put("producto", productoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			productoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el producto en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Producto eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/crear-con-foto")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> createconfoto(@Valid Producto producto, BindingResult result,
			@RequestParam MultipartFile archivo) {
		Map<String, Object> response = new HashMap<>();
		Producto newProducto = null;

		if (!archivo.isEmpty()) {
			try {
				producto.setFoto(archivo.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			newProducto = productoService.save(producto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error  en la inserccion en la base de datos con foto");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Producto insertado con exito con exito");
		response.put("producto", newProducto);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id) {
		Producto producto = productoService.findById(id);

		if (producto == null || producto.getFoto() == null) {
			return ResponseEntity.notFound().build();
		}
		Resource imagen = new ByteArrayResource(producto.getFoto());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
	}
	
	@GetMapping("/page/{page}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public Page<Producto> getProductos(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 10);
		return productoService.findAll(pageable);
	}
	

}
