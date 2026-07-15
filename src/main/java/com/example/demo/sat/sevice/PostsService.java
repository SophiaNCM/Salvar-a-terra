package com.example.demo.sat.sevice;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.sat.domain.Postagem;
import com.example.demo.sat.domain.Usuario;
import com.example.demo.sat.repository.PostsRepository;

import jakarta.validation.Valid;


@Service
public class PostsService{
	@Autowired
	private PostsRepository repository;
	public void save(Postagem posts) {
		repository.save(posts);
	 }
	 @Transactional
	public void edit(Postagem posts) {
		repository.save(posts);
	 }
	
}
