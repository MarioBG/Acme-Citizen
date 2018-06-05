
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentableRepository;
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

	public Commentable findByCommentableId(final int commentableId) {

		Assert.isTrue(commentableId != 0);

		final Commentable result = this.commentableRepository.findByCommentableId(commentableId);
		return result;
	}

	public Commentable findCommentableByCommentId(final int commentId) {

		Assert.isTrue(commentId != 0);

		final Commentable commentable = this.commentableRepository.findCommentableByCommentId(commentId);
		return commentable;
	}

}
