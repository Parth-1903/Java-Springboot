package com.week4.controller;

import com.week4.dto.PostDto;
import com.week4.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping
	public List<PostDto> getAllPost(){
		return postService.getAllPosts();
	}

	@GetMapping("/{postId}")
	public PostDto getPostById(@PathVariable("postId") Long postId){
		return postService.getPostById(postId);
	}

	@PostMapping
	public PostDto createPost(@RequestBody PostDto postDto){
		return postService.createdNewPost(postDto);
	}


	@PutMapping("/{postId}")
	public PostDto updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Long postId){
		return postService.updatePost(postDto,postId);
	}
}
