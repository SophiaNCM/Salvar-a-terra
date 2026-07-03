package com.example.demo.sat.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
//Informação basicas de uma tabela que será incluida no banco de dados

@SuppressWarnings("serial")
@Entity
@Table(name="Postagem")
public class Postagem implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long Id;
	
	@NotNull(message= "O post precisa de um titulo")
	@Column(name = "Titulo", nullable = false)
	private String Titulo;
	
	@NotNull(message= "O post precisa de um conteudo")
	@Column(name = "Conteudo", nullable = false)
	private String Conteudo;
	
	
	@Column(name = "PostData", nullable = false)
	private LocalDateTime PostData;
	
	@Column(name = "Tags", nullable = true)
	private String Tags;
	
	@Column(name = "Likes", nullable = true)
	private int Likes;
	
	@Column(name = "ImgURL", nullable = true)
	private String ImgURL;
	
	@ManyToOne
	@JoinColumn(name="Usuario_fk")
	private Usuario UsuarioId;
	
	@OneToMany(mappedBy = "PostagemId")
	private List<Comentario> Comentario;
	
	
}
