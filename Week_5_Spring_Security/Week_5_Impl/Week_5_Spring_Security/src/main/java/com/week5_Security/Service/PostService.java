package com.week5_Security.Service;

import com.week5_Security.Dto.PostDto;

import java.util.List;

public interface PostService {
	List<PostDto> getAllPosts();

	PostDto getPostById(Long id);

	PostDto createdNewPost(PostDto inputPost);

	PostDto updatePost(PostDto postDto, Long postId);
}
