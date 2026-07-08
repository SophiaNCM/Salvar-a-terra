package com.example.demo.sat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.sat.domain.Postagem;
import com.example.demo.sat.domain.Usuario;

public interface PostsRepository extends JpaRepository<Postagem, Long>{
	 
	 
		@Query("SELECT p FROM Postagem p WHERE p.UsuarioId = :UsuarioId")
		List<Postagem> findByUsuarioId(Usuario UsuarioId);
		
}
