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

import com.backend.sv1.models.Categoria;
import com.backend.sv1.models.services.CategoriaService;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/categorias")
public class CategoriaController {
	@Autowired
	private CategoriaService categoriaService;

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> create(@Valid @RequestBody Categoria categoria, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Categoria newCategoria = null;
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			newCategoria = categoriaService.save(categoria);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error  en la inserccion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Categoria insertada  con exito");
		response.put("categoria", newCategoria);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Categoria categoria = null;
		Map<String, Object> response = new HashMap<>();

		try {
			categoria = categoriaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error  en la consulta de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (categoria == null) {
			response.put("mensaje", "La categoria ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public List<Categoria> getCategorias() {
		return categoriaService.findAll();
	}
	@GetMapping("/movilapp")
	public List<Categoria> getCategoriasMovilApp() {
		return categoriaService.findAll();
	}


	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria, BindingResult result,
			@PathVariable Long id) {
		Categoria cat = categoriaService.findById(id);

		Categoria categoriaUpdate = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (cat == null) {
			response.put("mensaje", "Error:no se pudo editar, la categoria ID: ".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			cat.setNombre(categoria.getNombre());
			categoriaUpdate = categoriaService.save(cat);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Categoria actualizada con exito");
		response.put("categoria", categoriaUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			categoriaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la categoria en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Categoria eliminada con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/crear-con-foto")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> createconfoto(@Valid  Categoria categoria, BindingResult result,@RequestParam MultipartFile archivo) {
		Map<String, Object> response = new HashMap<>();
		Categoria newCategoria = null;
		if (!archivo.isEmpty()) {
            try {
                categoria.setFoto(archivo.getBytes());
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
			newCategoria = categoriaService.save(categoria);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error  en la inserccion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Categoria insertada  con exito");
		response.put("categoria", newCategoria);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	@PutMapping("/editar-con-foto/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	public ResponseEntity<?> updatecomfoto(@Valid Categoria categoria, BindingResult result,
			@PathVariable Long id,@RequestParam MultipartFile archivo) {
		Categoria cat = categoriaService.findById(id);

		Categoria categoriaUpdate = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (cat == null) {
			response.put("mensaje", "Error:no se pudo editar, la categoria ID: ".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			cat.setNombre(categoria.getNombre());
			 if (!archivo.isEmpty()) {
	                try {
	                    cat.setFoto(archivo.getBytes());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
			categoriaUpdate = categoriaService.save(cat);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Categoria actualizada con exito");
		response.put("categoria", categoriaUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	 @GetMapping("/img/{id}")
	    public ResponseEntity<?> verFoto(@PathVariable Long id){
	        Categoria categoria = categoriaService.findById(id);
	        
	        if (categoria==null || categoria.getFoto()== null) {
	            return ResponseEntity.notFound().build();
	        }
	        Resource imagen = new ByteArrayResource(categoria.getFoto());
	        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
	    }
	 
	 @GetMapping("/page/{page}")
	 @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	 public Page<Categoria> getCategorias(@PathVariable Integer page){
		 Pageable pageable = PageRequest.of(page, 10);
		 return categoriaService.findAll(pageable);
	 }
	 //PARA UN NOMBRE DE CATEGORIA EXISTENTE
	 @GetMapping("/existe-nombre-categoria/{nombre}")
	 @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	 public Categoria getNombreExiste(@PathVariable String nombre) {
		 return categoriaService.findByNombre(nombre);
	 }
	 
	 //FILTRAR LISTA POR TERMINO
	 @GetMapping("/filtrar/{termino}")
	 @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	 public List<Categoria> getCategoriasByTermino(@PathVariable String termino){
		 return categoriaService.findCategotriasByNombre(termino);
	 }

}
