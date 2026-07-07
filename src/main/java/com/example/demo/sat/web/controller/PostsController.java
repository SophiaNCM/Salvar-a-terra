package com.example.demo.sat.web.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.sat.domain.Usuario;
import com.example.demo.sat.sevice.UsuarioService;

@Controller
@RequestMapping("/posts")
public class PostsController {
	
	public Usuario usuario;
	private UsuarioService usuarioService;
	@GetMapping("/listar")
	public String PostsListar(@AuthenticationPrincipal Usuario usuario,
	        Model model) {
//=========================================================Adicionando a foto na tela principal=====================================================
	  
		model.addAttribute("usuario", usuario);
		return "posts";
	}
	
	@GetMapping("/CriarPost")
	public String CriarPost(){
		return "criar-post";
	}


}
