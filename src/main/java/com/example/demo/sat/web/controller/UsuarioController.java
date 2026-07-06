package com.example.demo.sat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.sat.domain.Usuario;
import com.example.demo.sat.repository.UsuarioRepository;
import com.example.demo.sat.sevice.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}
	
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
}
