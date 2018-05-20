
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentableRepository;
import domain.Comment;
import domain.Commentable;

@Service
@Transactional
public class CommentableService {

	// Managed repository
	@Autowired
	private CommentableRepository	commentableRepository;


	// Constructors
	public CommentableService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Commentable findOne(final int commentableId) {

		Commentable result = null;
		result = this.commentableRepository.findOne(commentableId);
		return result;
	}

	public Collection<Commentable> findAll() {

		Collection<Commentable> result = null;
		result = this.commentableRepository.findAll();
		return result;
	}

	public Commentable save(final Commentable commentable) {

		Commentable result = null;

		result = this.commentableRepository.save(commentable);

		return result;
	}

	// Ancillary methods

	public Commentable findCommentableByCommentId(final int commentId) {

		final Commentable commentable = this.commentableRepository.findCommentableByCommentId(commentId);
		return commentable;
	}

	public Collection<Comment> findCommentsByCommentableId(final int commentableId) {
		Collection<Comment> result = null;
		result = this.commentableRepository.findCommentsByCommentableId(commentableId);
		return result;
	}

}
