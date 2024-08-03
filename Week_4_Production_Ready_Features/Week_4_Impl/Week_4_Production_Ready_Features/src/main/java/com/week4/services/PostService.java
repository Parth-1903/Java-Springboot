package com.week4.services;

import com.week4.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {

	List<PostDto> getAllPosts();

	PostDto getPostById(Long id);

	PostDto createdNewPost(PostDto inputPost);

	PostDto updatePost(PostDto postDto, Long postId);
}
