package com.backend.sv1.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.sv1.models.Rol;
import com.backend.sv1.models.Usuario;
import com.backend.sv1.models.services.UsuarioService;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {
	@Autowired
    private UsuarioService usuarioService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Usuario newUsuario = null;
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
        	usuario.setPassword(encoder.encode(usuario.getPassword()));
            newUsuario = usuarioService.save(usuario);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error  en la inserccion en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Usuario insertado  con exito");
        response.put("usuario", newUsuario);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();

        try {
            usuario = usuarioService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error  en la consulta de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (usuario == null) {
            response.put("mensaje", "El usuario ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public List<Usuario> getUsuarios() {
        return usuarioService.findAll();
    }
    
    @GetMapping("/filtrar/{termino}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public List<Usuario> getUsuarios(@PathVariable String termino){
    	return usuarioService.findByUsernameOrNombrePersona(termino);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
        Usuario user = usuarioService.findById(id);

        Usuario usuarioUpdate = null;

        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (user == null) {
            response.put("mensaje", "Error:no se pudo editar, el userente ID: ".concat(id.toString())
                    .concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            user.setUsername(usuario.getUsername());
            user.setActivo(usuario.isActivo());
            user.setPersona(usuario.getPersona());
            user.setRoles(usuario.getRoles());   

            usuarioUpdate = usuarioService.save(user);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cliente actualizado con exito");
        response.put("usuario", usuarioUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar al usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Usuario eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public List<Rol> getRoles() {
        return usuarioService.findAllRoles();
    }
    
    @GetMapping("/existe-username-usuario/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Usuario getUsernameExiste(@PathVariable String username) {
    	return usuarioService.findByUsername(username);
    }
    
    @GetMapping("/page/{page}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public Page<Usuario> getUsuarios(@PathVariable Integer page){
    	Pageable pageable = PageRequest.of(page, 10);
    	return usuarioService.findAll(pageable);
    }
    
   
    
    @GetMapping("/truef/{id}/{passactual}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Boolean passwordCorrecta(@PathVariable Long id,@PathVariable String passactual) {
    	boolean pass=false;
    	Usuario user = usuarioService.findById(id);
    	if (encoder.matches(passactual, user.getPassword())) {
			pass=true;
		}
    	return pass;
    }
    
    @PutMapping("/withpass/{password}/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateWithPassword(@PathVariable String password, @PathVariable Long id) {
    	 Map<String, Object> response = new HashMap<>();
         try {
             usuarioService.actulizarPassword(encoder.encode(password), id);
         } catch (DataAccessException e) {
             response.put("mensaje", "Error al actualizar la contraseña en la base de datos");
             response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
             return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
         }
         response.put("mensaje", "Contraseña actualizada con exito");
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


}
