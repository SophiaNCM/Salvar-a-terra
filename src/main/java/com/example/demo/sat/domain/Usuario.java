package com.example.demo.sat.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@SuppressWarnings("serial")
@Entity
@Table(name="Usuario")
public class Usuario implements  UserDetails {
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
	
	@Lob
	@Column(name = "ImgUsuario", nullable = true, length = 1000000)
	private String ImgUsuario;
	
	@PastOrPresent(message = "A data de nascimento não pode ser futura")
	@Column(name = "DtNascimento", nullable = true)
	private LocalDate DtNascimento;
	
	@Column(name = "Descricao", nullable = true)
	private String Descricao;
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }




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

	public String getPassword() {
		return Senha;
	}

	public void setPassword(String senha) {
		Senha = senha;
	}

	public String getUsername() {
		return Email;
	}

	public void setUsername(String email) {
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
	
	public LocalDate getDtNascimento() {
		return DtNascimento;
	}
	public void setDtNascimento(LocalDate dtNascimento) {
		DtNascimento = dtNascimento;
	}
	
	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	@Override
	public String toString() {
		return "Usuario [Id=" + Id + ", UsuarioNome=" + UsuarioNome + ", Senha=" + Senha + ", Email=" + Email
				+ ", ImgUsuario=" + ImgUsuario + ", DtNascimento=" + DtNascimento + ", Descricao=" + Descricao +  "]";
	}




	
	
}
