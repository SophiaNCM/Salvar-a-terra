package com.example.demo.sat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.sat.domain.Postagem;
import com.example.demo.sat.domain.Usuario;

public interface PostsRepository extends JpaRepository<Postagem, Long>{
	 
	 //Buscando o usuario
		@Query("SELECT p FROM Postagem p WHERE p.UsuarioId = :UsuarioId")
		List<Postagem> findByUsuarioId(Usuario UsuarioId);
		
		
	//Atualizar a contagem de likes
		@Transactional(readOnly= false)
		@Modifying
		@Query("update Postagem p set p.Likes = p.Likes + 1 where p.Id = :Id")
		void updateSomarLikes(@Param("Id") Long Id);
		
		//Informa a quantidade de likes
		@Query("select p.Likes from Postagem p where p.Id = :Id")
		int findLikesById(@Param("Id") Long Id);
		
		@Query("Select p from Postagem p where p.Titulo like %:Pesquisa% or p.Conteudo like %:Pesquisa% or p.Tags like %:Pesquisa%")
		List<Postagem> buscarPorTituloOuConteudo(@Param("Pesquisa") String Pesquisa);
		
}
