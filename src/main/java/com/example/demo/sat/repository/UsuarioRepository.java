package com.example.demo.sat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.sat.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	@Query("select u from Usuario u where u.Email like :Email")
	Usuario findByEmail(@Param("Email") String Email);
	

}
