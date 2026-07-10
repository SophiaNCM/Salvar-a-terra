package com.example.demo.sat.domain;

import java.io.Serializable;
import java.time.LocalDate;
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
		private LocalDate PostData;
		
		
		@Column(name = "ImgURL", nullable = true)
		private String ImgURL;
		
		@ManyToOne
		@JoinColumn(name="Usuario_fk")
		private Usuario UsuarioId;
		
		@ManyToOne
		@JoinColumn(name = "Postagem_fk")
		private Postagem PostagemId;

		public Long getId() {
			return Id;
		}

		public void setId(Long id) {
			Id = id;
		}

		public String getConteudo() {
			return Conteudo;
		}

		public void setConteudo(String conteudo) {
			Conteudo = conteudo;
		}

		public LocalDate getPostData() {
			return PostData;
		}

		public void setPostData(LocalDate postData) {
			PostData = postData;
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

		public Postagem getPostagemId() {
			return PostagemId;
		}

		public void setPostagemId(Postagem postagemId) {
			PostagemId = postagemId;
		}

		@Override
		public String toString() {
			return "Comentario [Id=" + Id + ", Conteudo=" + Conteudo + ", PostData=" + PostData + ", ImgURL=" + ImgURL
					+ ", UsuarioId=" + UsuarioId + ", PostagemId=" + PostagemId + "]";
		}
		
		
}
