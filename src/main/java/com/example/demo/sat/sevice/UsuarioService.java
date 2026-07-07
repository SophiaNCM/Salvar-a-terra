package com.example.demo.sat.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.sat.domain.Usuario;
import com.example.demo.sat.repository.UsuarioRepository;

import jakarta.validation.Valid;



@Service
public class UsuarioService implements UserDetailsService{
	
//================================================================Tratando o login ========================================================================	
	@Autowired
	private UsuarioRepository repository;
	
	@Transactional(readOnly = true)
	public Usuario buscarPorEmail(String Email) {
		return repository.findByEmail(Email);
	}

	@Override @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {

	    System.out.println("Email recebido: [" + Email + "]");

	    Usuario usuario = buscarPorEmail(Email);

	    System.out.println("Usuario encontrado: " + usuario);

	    if (usuario == null) {
	        throw new UsernameNotFoundException("Usuário não encontrado");
	    }
		return usuario;
		}
	
	 @Transactional
	public void save(@Valid Usuario usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setPassword(crypt);
		repository.save(usuario);
	}
	 @Transactional
	public void edit(@Valid Usuario usuario) {
		repository.save(usuario);
	 }
}

