package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;
import domain.Petition;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// pasar al servicio de Rendezvous
	@Query("select u.rsvpdRendezvouses from User u where u = ?1")
	Collection<Petition> findRsvpdRendezvousesByUserId(int attendantsId);
	
	@Query("select c from Comment c where c.rendezvous.id=?1 and c.commentParent=null")
	Collection<Comment> commentsOfThisRendezvouseWithCommentNull(int rendezvouseId);

}
