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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getUsuarioNome() {
		return UsuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		UsuarioNome = usuarioNome;
	}

	public String getSenha() {
		return Senha;
	}

	public void setSenha(String senha) {
		Senha = senha;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getImgUsuario() {
		return ImgUsuario;
	}

	public void setImgUsuario(String imgUsuario) {
		ImgUsuario = imgUsuario;
	}

	public List<Postagem> getPostagem() {
		return Postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		Postagem = postagem;
	}

	public List<Comentario> getComentario() {
		return Comentario;
	}

	public void setComentario(List<Comentario> comentario) {
		Comentario = comentario;
	}

	@Override
	public String toString() {
		return "Usuario [Id=" + Id + ", UsuarioNome=" + UsuarioNome + ", Senha=" + Senha + ", Email=" + Email
				+ ", ImgUsuario=" + ImgUsuario + ", Postagem=" + Postagem + ", Comentario=" + Comentario + "]";
	}
	
	
}
