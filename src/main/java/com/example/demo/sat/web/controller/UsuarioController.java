package com.example.demo.sat.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.sat.domain.Postagem;
import com.example.demo.sat.domain.Usuario;
import com.example.demo.sat.repository.PostsRepository;
import com.example.demo.sat.repository.UsuarioRepository;
import com.example.demo.sat.sevice.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	public Usuario usuario;
	
	public Postagem postagem;
	@Autowired
	public PostsRepository postsRepository;
	
	@Value("${app.upload-dir}")
	private String uploadDir;
	@GetMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}
//====================================================Configurações da pagina de registro=========================================================
	@PostMapping("/save")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result){
		try {
		service.save(usuario);
		} catch(DataIntegrityViolationException ex) {
			result.reject("email","Ops.. Este e-mail já existe na base de dados");
			return "registro";
		}
		return "login";
	}
//==================================================================================================================================================
//================================================Adicionando a pagina de perfil====================================================================
	@GetMapping("/perfil/{id}")
	public String perfil(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario,Model model) {
	    Usuario perfil = usuarioRepository.findById(id).orElseThrow();
	    List<Postagem> posts = postsRepository.findByUsuarioId(perfil);
	    boolean proprioPerfil = usuario != null && usuario.getId().equals(perfil.getId());
	    model.addAttribute("usuarioLogado", usuario);          // usuário logado
	    model.addAttribute("perfil", perfil);            // perfil sendo visualizado
	    model.addAttribute("posts", posts);
	    model.addAttribute("proprioPerfil", proprioPerfil);
		return "perfil";
	}
	
//==================================================================================================================================================

//================================================Adicionando a pagina de editar perfil=============================================================

	@GetMapping("/editPerfil")
	public String editPerfil(@AuthenticationPrincipal Usuario usuario,Model model) {
		System.out.println("Nome do usuario" + usuario.getUsuarioNome());
		List<Postagem> posts = postsRepository.findByUsuarioId(usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("posts", posts);
		return "editar-perfil";
		
	}
//==================================================================================================================================================

//====================================================Configurações da pagina de editar perfil======================================================

	@PostMapping("/editPerfil/save")
	public String editPerfil( @RequestParam("imgUsuario") MultipartFile foto,
            @Valid Usuario usuarioForm, BindingResult result) throws IOException {
		//Precisamos coletar o authentication do perfil por causa do security e com isso coletamos o email
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 //==============================================================================================
		 
		    String email = auth.getName();
		    //Procurando o email no banco de dados
		    Usuario usuario = usuarioRepository.findByEmail(email);
		    //======================================================
		    
		    // Atualiza somente se o nome foi informado
		    if (usuarioForm.getUsuarioNome() != null &&
		        !usuarioForm.getUsuarioNome().trim().isEmpty()) {
		        usuario.setUsuarioNome(usuarioForm.getUsuarioNome());
		    }
		    //==========================================================
		    
		    // Atualiza somente se a descrição foi informada
		    if (usuarioForm.getDescricao() != null &&
		        !usuarioForm.getDescricao().trim().isEmpty()) {
		        usuario.setDescricao(usuarioForm.getDescricao());
		    }
		    //====================================================
		    
		    // Atualiza somente se uma nova foto foi enviada, como a imagem não é um link e sim um update do computador para o site, precisamos transformar em um link
		    if (foto != null && !foto.isEmpty()) {
		        String nomeOriginal = foto.getOriginalFilename();
		        String extensao = "";
		        if (nomeOriginal != null && nomeOriginal.contains(".")) {
		            extensao = nomeOriginal.substring(
		                    nomeOriginal.lastIndexOf(".")
		            );
		        }
		        String nomeArquivo = UUID.randomUUID() + extensao;
		        Path caminho = Paths.get(uploadDir, "perfis");
		        Files.createDirectories(caminho);
		        Files.copy(
		                foto.getInputStream(),
		                caminho.resolve(nomeArquivo),
		                StandardCopyOption.REPLACE_EXISTING
		        );
		        usuario.setImgUsuario("/uploads/perfis/" + nomeArquivo);
		    }
		    //==========================================================================================================================================================
		    
		    //confirmando a edição
		    service.edit(usuario);
		    //====================
		    
		    // Atualiza o usuário da sessão
		    Authentication novaAuth = new UsernamePasswordAuthenticationToken(usuario, auth.getCredentials(), usuario.getAuthorities());

		    SecurityContextHolder.getContext().setAuthentication(novaAuth);
		    //=============================================================================================================================
		    
		    //redirecionando para o perfil do usuario logado 
		    return "redirect:/usuario/perfil/" + usuario.getId();
		    //=======================================================
	}
}

