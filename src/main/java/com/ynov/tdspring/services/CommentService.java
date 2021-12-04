package com.ynov.tdspring.services; 

import com.ynov.tdspring.entities.*;
import com.ynov.tdspring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository; 

	@Autowired
    private EventService eventService;

	// --------------------- // 

	public Comment create(Comment comment) {
		Event event = new Event();
		this.eventService.create(
			event.EVENT_PROJECT, 
			comment.getAuthor(),
			event.EVENT_ACTION_CREATE,
			comment
		);

		return commentRepository.save(comment);
	}

	public Comment update(Comment comment) throws Exception {
		Event event = new Event();
		this.eventService.create(
			event.EVENT_PROJECT, 
			comment.getAuthor(),
			event.EVENT_ACTION_UPDATE,
			comment
		);
		
		return commentRepository.save(comment); 
	}

	public List<Comment> getAllComments() {
		return commentRepository.findAll();
	}

	public Comment getCommentByCommentId(UUID id) {
		return commentRepository.findById(id).orElse(null);
	}

	public Comment addLikeToComment(Comment comment, User user) {
		if (comment != null) {
			List<User> likes = comment.getLikes();

			if (user != null) {
				likes.add(user);
				comment.setLikes(likes);
			}

			commentRepository.save(comment);
		}

		return comment;
	}

	public Comment deleteLikeForComment(Comment comment, User user) {
		if (comment != null) {
			List<User> likes = comment.getLikes();

			if (user != null) {
				likes.remove(user);
				comment.setLikes(likes);
			}

			commentRepository.save(comment);
		}

		Event event = new Event();
        this.eventService.create(event.EVENT_USER, user, event.EVENT_ACTION_DELETE, comment);

        return comment;
	}
}