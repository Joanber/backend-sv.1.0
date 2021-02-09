/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

import com.backend.sv1.models.Persona;
import com.backend.sv1.models.services.PersonaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<?> create(@Valid @RequestBody Persona persona, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Persona newPersona = null;
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            newPersona = personaService.save(persona);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error  en la inserccion en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Persona insertada con exito con exito");
        response.put("persona", newPersona);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Persona persona = null;
        Map<String, Object> response = new HashMap<>();

        try {
            persona = personaService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error  en la consulta de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (persona == null) {
            response.put("mensaje", "La persona ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Persona>(persona, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public List<Persona> getPersonas() {
        return personaService.findAll();
    }
    
    @GetMapping("/filtrar/{termino}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public List<Persona> getPersonas(@PathVariable String termino) {
        return personaService.findByNombreOrApellidoIgnoreCase(termino);
    }
    
    @GetMapping("/page/{page}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public Page<Persona> getPersonas(@PathVariable Integer page){
    	Pageable pageable = PageRequest.of(page, 10);
    	return personaService.findAll(pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<?> update(@Valid @RequestBody Persona persona, BindingResult result, @PathVariable Long id) {
        Persona per = personaService.findById(id);

        Persona personaUpdate = null;

        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (per == null) {
            response.put("mensaje", "Error:no se pudo editar, el perente ID: ".concat(id.toString())
                    .concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            per.setNombre(persona.getNombre());
            per.setApellido(persona.getApellido());
            per.setEmail(persona.getEmail());
            per.setCedula(persona.getCedula());
            per.setFecha(persona.getFecha());
            per.setTelefono(persona.getTelefono());
            per.setDireccion(persona.getDireccion());

            personaUpdate = personaService.save(per);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cliente actualizado con exito");
        response.put("persona", personaUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/editar-con-foto/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<?> updateconfoto(@Valid Persona persona, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile archivo) {
        Persona per = personaService.findById(id);

        Persona personaUpdate = null;

        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (per == null) {
            response.put("mensaje", "Error:no se pudo editar, la persona ID: ".concat(id.toString())
                    .concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            per.setNombre(persona.getNombre());
            per.setApellido(persona.getApellido());
            per.setEmail(persona.getEmail());
            per.setCedula(persona.getCedula());
            per.setFecha(persona.getFecha());
            per.setTelefono(persona.getTelefono());
            per.setDireccion(persona.getDireccion());
            if (!archivo.isEmpty()) {
                try {
                    per.setFoto(archivo.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            personaUpdate = personaService.save(per);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Persona actualizada con exito");
        response.put("persona", personaUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            personaService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la persona en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Persona eliminada con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/crear-con-foto")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<?> createconfoto(@Valid Persona persona, BindingResult result, @RequestParam MultipartFile archivo) {
        Map<String, Object> response = new HashMap<>();
        Persona newPersona = null;
        
        if (!archivo.isEmpty()) {
            try {
                persona.setFoto(archivo.getBytes());
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
            newPersona = personaService.save(persona);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error  en la inserccion en la base de datos con foto");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Persona insertada con exito con exito");
        response.put("persona", newPersona);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id){
    	
        Persona persona = personaService.findById(id);
        
        if (persona==null || persona.getFoto()== null) {
            return ResponseEntity.notFound().build();
        }
        Resource imagen = new ByteArrayResource(persona.getFoto());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imagen);
    }
    
    @GetMapping("/existe-cedula-persona/{cedula}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public Persona getCedulaExiste(@PathVariable String cedula) {
        return personaService.findByCedula(cedula);
    }

}
