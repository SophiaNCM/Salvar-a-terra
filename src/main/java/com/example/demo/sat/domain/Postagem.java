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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	public String getConteudo() {
		return Conteudo;
	}

	public void setConteudo(String conteudo) {
		Conteudo = conteudo;
	}

	public LocalDateTime getPostData() {
		return PostData;
	}

	public void setPostData(LocalDateTime postData) {
		PostData = postData;
	}

	public String getTags() {
		return Tags;
	}

	public void setTags(String tags) {
		Tags = tags;
	}

	public int getLikes() {
		return Likes;
	}

	public void setLikes(int likes) {
		Likes = likes;
	}

	public String getImgURL() {
		return ImgURL;
	}

	public void setImgURL(String imgURL) {
		ImgURL = imgURL;
	}

	public Usuario getUsuarioId() {
		return UsuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		UsuarioId = usuarioId;
	}

	public List<Comentario> getComentario() {
		return Comentario;
	}

	public void setComentario(List<Comentario> comentario) {
		Comentario = comentario;
	}

	@Override
	public String toString() {
		return "Postagem [Id=" + Id + ", Titulo=" + Titulo + ", Conteudo=" + Conteudo + ", PostData=" + PostData
				+ ", Tags=" + Tags + ", Likes=" + Likes + ", ImgURL=" + ImgURL + ", UsuarioId=" + UsuarioId
				+ ", Comentario=" + Comentario + "]";
	}
	
	
}
