package com.backend.sv1.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.sv1.models.repositories.UsuarioRepository;
import com.backend.sv1.security.jwt.JwtUtils;
import com.backend.sv1.security.payload.pojos.LoginRequest;
import com.backend.sv1.security.payload.pojos.UserJwt;
import com.backend.sv1.security.services.UserDetailsImpl;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
         
    	  Authentication authentication = authenticate(
    				loginRequest.getUsername(), loginRequest.getPassword());

    			SecurityContextHolder.getContext().setAuthentication(authentication);
    			String jwt = jwtUtils.generateJwtToken(authentication);
    			
    			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
    			List<String> roles = userDetails.getAuthorities().stream()
    					.map(item -> item.getAuthority())
    					.collect(Collectors.toList());

    			return ResponseEntity.ok(new UserJwt(jwt, 
    					 userDetails.getId(), 
    					 userDetails.getUsername(), 
    					 roles,userDetails.getId_persona()));
	
	  
	}
	private Authentication authenticate(String username, String password) throws Exception {
        try {
        	 return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User diseabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentianls", e);
        }
    }


}
