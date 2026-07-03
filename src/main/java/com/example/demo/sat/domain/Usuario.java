package com.example.demo.sat.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="Usuario")
public class Usuario implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long Id;
	
	@NotNull(message= "O usuario precisa de um nome")
	@Column(name = "Usuario", nullable = false)
	private String UsuarioNome;
	
	@NotNull(message= "O usuario precisa de uma senha")
	@Column(name = "Senha", nullable = false)
	private String Senha;
	
	@NotNull(message= "O usuario precisa de um email")
	@Column(name = "Email", nullable = false)
	private String Email;
	
	@Column(name = "ImgUsuario", nullable = true)
	private String ImgUsuario;
	
	@OneToMany(mappedBy = "UsuarioId")
	private List<Postagem> Postagem;
	
	@OneToMany(mappedBy = "UsuarioId")
	private List<Comentario> Comentario;
	
	
}
