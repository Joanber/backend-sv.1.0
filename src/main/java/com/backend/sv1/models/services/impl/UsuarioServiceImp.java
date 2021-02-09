package com.backend.sv1.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.sv1.models.Rol;
import com.backend.sv1.models.Usuario;
import com.backend.sv1.models.repositories.UsuarioRepository;
import com.backend.sv1.models.services.UsuarioService;



@Service
public class UsuarioServiceImp implements UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return usuarioRepository.findAllByOrderByIdAsc();
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public List<Rol> findAllRoles() {
		return usuarioRepository.findAllRoles();
	}

	@Override
	public List<Usuario> findAllByOrderByIdAsc() {
		return usuarioRepository.findAllByOrderByIdAsc();
	}

	@Override
	public List<Usuario> findByUsernameOrNombrePersona(String termino) {
		return usuarioRepository.findByUsernameOrNombrePersona(termino);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	@Override
	public void actulizarPassword(String password, Long id) {
		usuarioRepository.actulizarPassword(password, id);
		
	}

}
