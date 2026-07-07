package com.example.demo.sat.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.sat.domain.Usuario;

@Controller
public class HomeController {

	//Abrir pagina login
	@GetMapping("/")
	public String login() {
		return "login";
	}

	@GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Credenciais inválidas!");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados");
		return "login";
	}
}
