package com.example.demo.sat.domain;

import java.io.Serializable;
import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="Comentario")
public class Comentario implements Serializable{
		@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	
		private Long Id;
		
		
		@NotNull(message= "O post precisa de um conteudo")
		@Column(name = "Conteudo", nullable = false)
		private String Conteudo;
		
		
		@Column(name = "PostData", nullable = false)
		private LocalDateTime PostData;
		
		
		@Column(name = "ImgURL", nullable = true)
		private String ImgURL;
		
		@ManyToOne
		@JoinColumn(name="Usuario_fk")
		private Usuario UsuarioId;
		
		@ManyToOne
		@JoinColumn(name = "Postagem_fk")
		private Postagem PostagemId;
		
		
}
