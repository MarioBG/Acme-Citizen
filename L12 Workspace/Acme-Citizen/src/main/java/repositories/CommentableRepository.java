
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;
import domain.Commentable;

@Repository
public interface CommentableRepository extends JpaRepository<Commentable, Integer> {

	@Query("select c from Commentable c join c.comments com where com.id = ?1")
	Commentable findCommentableByCommentId(int commentableId);

	@Query("select c.comments from Commentable c where c.id = ?1")
	Collection<Comment> findCommentsByCommentableId(int commentableId);

}
