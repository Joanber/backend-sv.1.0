package com.backend.sv1.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.backend.sv1.models.Rol;
import com.backend.sv1.models.Usuario;


public interface UsuarioService {

	public List<Usuario> findAll();

	public Usuario save(Usuario usuario);

	public void delete(Long id);

	public Usuario findById(Long id);

	public List<Rol> findAllRoles();

	public List<Usuario> findAllByOrderByIdAsc();
	
	public List<Usuario> findByUsernameOrNombrePersona(String termino);
	
	public Usuario findByUsername(String username);
	
	public Page<Usuario> findAll(Pageable pageable);
	
	public void actulizarPassword(String password,Long id);

}
