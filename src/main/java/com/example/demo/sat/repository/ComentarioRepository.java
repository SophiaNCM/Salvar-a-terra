package com.example.demo.sat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.sat.domain.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
	@Transactional
	@Modifying
	@Query("DELETE FROM Comentario c WHERE c.PostagemId.Id = :Id")
	void deleteByPostagemId(@Param("Id") Long Id);
}
