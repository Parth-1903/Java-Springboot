package com.week5_Security.Controller;

import com.week5_Security.Dto.PostDto;
import com.week5_Security.Service.PostService;
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
