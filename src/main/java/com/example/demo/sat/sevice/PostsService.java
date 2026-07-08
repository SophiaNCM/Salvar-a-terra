package com.example.demo.sat.sevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.sat.domain.Postagem;
import com.example.demo.sat.repository.PostsRepository;


@Service
public class PostsService{
	@Autowired
	private PostsRepository repository;
	public void save(Postagem posts) {
		repository.save(posts);
	 }
}
