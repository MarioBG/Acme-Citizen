
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WelcomeMessage;

@Repository
public interface WelcomeMessageRepository extends JpaRepository<WelcomeMessage, Integer> {

	@Query("select wm from WelcomeMessage wm where wm.languageCode=?1")
	public WelcomeMessage getWelcomeMessageByLocale(String locale);

}
