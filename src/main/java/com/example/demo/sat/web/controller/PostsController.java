package com.example.demo.sat.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.sat.domain.Postagem;
import com.example.demo.sat.domain.Usuario;
import com.example.demo.sat.repository.PostsRepository;
import com.example.demo.sat.repository.UsuarioRepository;
import com.example.demo.sat.sevice.UsuarioService;

import java.nio.file.Path;
import java.nio.file.Paths;
import jakarta.validation.Valid;

import com.example.demo.sat.sevice.PostsService;

@Controller
@RequestMapping("/posts")
public class PostsController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PostsService postsService;
	
	@GetMapping("/listar")
	public String PostsListar(@AuthenticationPrincipal Usuario usuario,
	        Model model) {
//=========================================================Adicionando Postagem=====================================================
	  
		model.addAttribute("usuario", usuario);
		return "posts";
	}
	
	@GetMapping("/CriarPost")
	public String CriarPost(Model model) {

	    model.addAttribute("postagem", new Postagem());
	    return "criar-post";
	}
	
	@PostMapping("/CriarPost/save")
	public String CriarPostSave(
	        @RequestParam("imagem") MultipartFile imagem,
	        @AuthenticationPrincipal Usuario usuario,
	        @Valid @ModelAttribute Postagem postagem,
	        BindingResult result
	) throws IOException {

	    System.out.println("ENTROU NO MÉTODO");

	    // Verifica se existe imagem enviada
	    if (imagem != null && !imagem.isEmpty()) {

	        System.out.println("2 - Tem foto");

	        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();

	        Path caminho = Paths.get("src/main/resources/static/img/posts/");

	        Files.createDirectories(caminho);

	        Files.copy(
	            imagem.getInputStream(),
	            caminho.resolve(nomeArquivo),
	            StandardCopyOption.REPLACE_EXISTING
	        );

	        // Salva apenas o caminho da imagem na entidade
	        postagem.setImgURL("/img/posts/" + nomeArquivo);

	        System.out.println("3 - Foto salva");
	    }


	    // Dados preenchidos automaticamente
	    postagem.setUsuarioId(usuario);
	    postagem.setPostData(LocalDateTime.now());
	    postagem.setLikes(0);

	    System.out.println("4 - Antes da validação");


	    if (result.hasErrors()) {

	        System.out.println("5 - Tem erros");

	        result.getAllErrors()
	              .forEach(System.out::println);

	        return "criar-post";
	    }


	    System.out.println("6 - Antes do save");

	    postsService.save(postagem);

	    System.out.println("7 - Depois do save");


	    return "redirect:/posts/listar";
	}			
}


