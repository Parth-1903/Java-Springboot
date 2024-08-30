package com.week5_Security.Service;

import com.week5_Security.Dto.PostDto;
import com.week5_Security.entities.PostEntity;
import com.week5_Security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

	private final PostService postService;

	public boolean isOwnerOfPost(Long postId){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PostDto post = postService.getPostById(postId);
		return post.getAuthor().getId().equals(user.getId());
	}

}
