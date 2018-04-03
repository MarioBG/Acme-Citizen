package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
	
	@Query("select a from Announcement a where a.rendezvous.id =?1")
	Collection<Announcement> findAnnouncementsByRendezvous(int rendezvousId);

	@Query("select ann from Rendezvous r join r.attendants a join r.announcements ann where a.id=?1 order by ann.moment desc")
	Collection<Announcement> findAnnouncementsByAttendants(int rendezvousId);

	@Query("select a from User u join u.organisedRendezvouses r join r.announcements a where u.id=?1")
	Collection<Announcement> announcementsByUser(int userId);

}
