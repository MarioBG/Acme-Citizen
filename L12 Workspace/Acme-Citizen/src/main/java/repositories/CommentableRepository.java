
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Commentable;

@Repository
public interface CommentableRepository extends JpaRepository<Commentable, Integer> {

	@Query("select c from Commentable c where c.id = ?1")
	Commentable findByCommentableId(int commentableId);

	@Query("select c from Commentable c join c.comments com where com.id = ?1")
	Commentable findCommentableByCommentId(int commentId);

}
