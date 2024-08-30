package com.week4.services.impl;

import com.week4.dto.PostDto;
import com.week4.entities.PostEntity;
import com.week4.exception.ResourceNotFoundException;
import com.week4.repositories.PostRepository;
import com.week4.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<PostDto> getAllPosts() {
		return postRepository
				.findAll()
				.stream()
				.map(postEntity -> modelMapper.map(postEntity,PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto getPostById(Long id) {
		PostEntity postEntity = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with this ID: "+id));
		return modelMapper.map(postEntity,PostDto.class);
	}

	@Override
	public PostDto createdNewPost(PostDto inputPost) {
		PostEntity postEntity = modelMapper.map(inputPost,PostEntity.class);
		return modelMapper.map(postRepository.save(postEntity), PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto inputPost, Long postId) {
		PostEntity olderPost = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with this ID: "+postId));
		inputPost.setId(postId);
		modelMapper.map(inputPost,olderPost);
		return modelMapper.map(postRepository.save(olderPost), PostDto.class);
	}
}
