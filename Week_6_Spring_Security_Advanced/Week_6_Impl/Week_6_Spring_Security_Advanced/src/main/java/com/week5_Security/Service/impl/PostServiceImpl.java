package com.week5_Security.Service.impl;

import com.week5_Security.Dto.PostDto;
import com.week5_Security.Exception.ResourceNotFoundException;
import com.week5_Security.Repository.PostRepository;
import com.week5_Security.Service.PostService;
import com.week5_Security.entities.PostEntity;
import com.week5_Security.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PostEntity postEntity = modelMapper.map(inputPost,PostEntity.class);
		postEntity.setAuthor(user);
		return modelMapper.map(postRepository.save(postEntity), PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto inputPost, Long postId) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("User {user}",user);
		PostEntity olderPost = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with this ID: "+postId));
		inputPost.setId(postId);
		modelMapper.map(inputPost,olderPost);
		return modelMapper.map(postRepository.save(olderPost), PostDto.class);
	}
}