package com.backend.sv1.security.jwt;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.backend.sv1.models.Usuario;
import com.backend.sv1.models.repositories.UsuarioRepository;
import com.backend.sv1.security.services.UserDetailsImpl;





public class UserDetailsServiceImpl implements UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
			
	@Autowired
	private UsuarioRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user=userRepository.findByUsername(username);
		if (user==null) {
			logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException(String.format("Usuario No encontrado con username: ", username));
		}
		else {
			//return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
			return UserDetailsImpl.build(user);	
		}
		
	}
}
