package com.example.demo.sat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostsController {
	@GetMapping("/listar")
	public String PostsListar(){
		return "posts";
	}
	
	@GetMapping("/CriarPost")
	public String CriarPost(){
		return "criar-post";
	}


}
