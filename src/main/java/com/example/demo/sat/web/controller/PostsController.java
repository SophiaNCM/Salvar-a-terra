package com.example.demo.sat.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.sat.domain.Comentario;
import com.example.demo.sat.domain.Postagem;
import com.example.demo.sat.domain.Usuario;
import com.example.demo.sat.repository.ComentarioRepository;
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
	
	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	
	@GetMapping("/listar")
	public String PostsListar(@AuthenticationPrincipal Usuario usuario,
	        Model model) {
//=========================================================Adicionando Postagem=====================================================
		model.addAttribute("posts", postsRepository.findAll());
		model.addAttribute("usuario", usuario);
		return "posts";
	}
//==================================================================================================================================
	
//=========================================================comentar Postagem========================================================
	@PostMapping("/post/{postId}/comentario")
	public String PostsComentar(
			@PathVariable("postId") Long postId,
	        @RequestParam(value = "imgComentario", required = false) MultipartFile imagem,
	        @AuthenticationPrincipal Usuario usuario,
	        @ModelAttribute Comentario comentario
	) throws IOException {
		//Buscando o id do post que ser comentado
	    Postagem post = postsRepository.findById(postId).orElseThrow();
	    //================================================================
	    //Inserindo a img
	    if (imagem != null && !imagem.isEmpty()) {
	        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
	        Path caminho = Paths.get("src/main/resources/static/img/comentario/");
	        Files.createDirectories(caminho);
	        Files.copy(
	                imagem.getInputStream(),
	                caminho.resolve(nomeArquivo),
	                StandardCopyOption.REPLACE_EXISTING
	        );
	        comentario.setImgURL("/img/comentario/" + nomeArquivo);
	    }
	    //===================================================================
	    //Inserindo o dados que o usuario não tem permissão
	    comentario.setUsuarioId(usuario);
	    comentario.setPostagemId(post);
	    comentario.setPostData(LocalDate.now());
	    //=================================================================
	    comentarioRepository.save(comentario);
	    // redirecionando para a pagina do post
	    return "redirect:/posts/postIndividual/" + postId;
	}
//==================================================================================================================================
	
//==========================================Comando para acessar a pagina de criar Postagem=========================================
	
	@GetMapping("/CriarPost")
	public String CriarPost(Model model) {

	    model.addAttribute("postagem", new Postagem());
	    return "criar-post";
	}
//==================================================================================================================================
	
//====================================================Comando para inserir um Postagem==============================================
	
	
	@PostMapping("/CriarPost/save")
	public String CriarPostSave(
	        @RequestParam("imagem") MultipartFile imagem,
	        @AuthenticationPrincipal Usuario usuario,
	        @Valid @ModelAttribute Postagem postagem,
	        BindingResult result
	) throws IOException {

//=====================================Codigo para o usuario recuperar uma imagem de seu computador ===============================

	    // Verifica se existe imagem enviada
	    if (imagem != null && !imagem.isEmpty()) {
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

	    }
//==================================================================================================================================

//==============================================Dados que o usuario não insere=======================================================

	    // Dados preenchidos automaticamente
	    postagem.setUsuarioId(usuario);
	    postagem.setPostData(LocalDate.now());
	    postagem.setLikes(0);
//==================================================================================================================================

//===================================================Conferindo se tem erro=========================================================


	    if (result.hasErrors()) {
	        result.getAllErrors().forEach(System.out::println);
	        return "criar-post";
	    }
//==================================================================================================================================

//==========================================================Salvando================================================================

	    postsService.save(postagem);

	    return "redirect:/posts/listar";
	}	
//==================================================================================================================================
//==========================================================Post Individual=========================================================
		@GetMapping("/postIndividual/{id}")
		public String postIndividual(@PathVariable("id") Long id, ModelMap model){
			model.addAttribute("post", postsRepository.findById(id).orElse(null));
			
			return "postUnico";
			}
		
//==========================================================ADD LIKES ==================================================================
		@PostMapping("/like/{id}")
		public ResponseEntity<?> adicionarLikes(@PathVariable("id") Long id){
			postsRepository.updateSomarLikes(id);
			int likes = postsRepository.findLikesById(id);
			return ResponseEntity.ok(likes);
		}
//======================================================================================================================================
//========================Metodo de deletar o post pelo id(os comentarios desse post são deletados juntos)==============================
		@GetMapping("/excluir/{id}")
		public String ExcluirPost(@PathVariable("id") Long id) {
			comentarioRepository.deleteByPostagemId(id);
			postsRepository.deleteById(id);
			return "redirect:/usuario/editPerfil";
		}
//=======================================================================================================================================
//=============================Acessando a pagina de edição(mesma pagina usada para criar post)==========================================
		@GetMapping("/editar/{id}")
		public String preEditar(@PathVariable("id") Long id, ModelMap model) {
			model.addAttribute("postagem", postsRepository.findById(id).orElseThrow());

			return "criar-post";
		}
//=======================================================================================================================================
//====================================================Metodo de editar post escolhido ===================================================
		@PostMapping("/editar")
		public String editar(@Valid Postagem postagem,BindingResult result , RedirectAttributes attr,  
				@RequestParam("imagem") MultipartFile imagem) throws IOException{
			//Capturando o post escolhid0
				Postagem postagemEdit = postsRepository.findById(postagem.getId()).orElseThrow();
			//===================================================================================
		    // Verifica se existe imagem enviada
		    if (imagem != null && !imagem.isEmpty()) {
		        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
		        Path caminho = Paths.get("src/main/resources/static/img/posts/");
		        Files.createDirectories(caminho);
		        Files.copy(
		            imagem.getInputStream(),
		            caminho.resolve(nomeArquivo),
		            StandardCopyOption.REPLACE_EXISTING
		        );
		        // Salva apenas o caminho da imagem na entidade
		        postagemEdit.setImgURL("/img/posts/" + nomeArquivo);

		    //===============================================================================
		    // Verifidando erro
		    }
			if(result.hasErrors()) {
				return "criar-post";
			}
			//===============================================================================
			//Informando os dados que podem ser mudados
			postagemEdit.setTitulo(postagem.getTitulo());
			postagemEdit.setConteudo(postagem.getConteudo());
			postagemEdit.setTags(postagem.getTags());
			postsService.edit(postagemEdit);
			//================================================================================
			//Informando que a edição foi um sucesso
			attr.addFlashAttribute("success", "Post editado com sucesso.");
			return "redirect:/usuario/editPerfil";
			//================================================================================
		}	
//=============================================================Metodo de busca=============================================================
		@GetMapping("/buscar/post")
		public String getPorNome(@RequestParam("Pesquisa") String pesquisa,@AuthenticationPrincipal Usuario usuario, ModelMap model) {		
			model.addAttribute("posts", postsRepository.buscarPorTituloOuConteudo(pesquisa));
			model.addAttribute("usuario", usuario);
			return "posts";
		}
//==========================================================================================================================================

}


