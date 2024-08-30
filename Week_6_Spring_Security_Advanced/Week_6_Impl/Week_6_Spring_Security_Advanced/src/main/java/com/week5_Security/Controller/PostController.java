package com.week5_Security.Controller;

import com.week5_Security.Dto.PostDto;
import com.week5_Security.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public List<PostDto> getAllPost(){
		return postService.getAllPosts();
	}

	@GetMapping("/{postId}")
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN') AND hasAuthority('POST_VIEW')")
	@PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
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
