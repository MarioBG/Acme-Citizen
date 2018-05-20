
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "actor_id, commentable_id")
})
public class Comment extends DomainEntity {

	// Constructors

	public Comment() {
		super();
	}


	// Attributes

	private Date	moment;
	private String	text;
	private String	picture;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}


	// Relationships

	private Commentable			commentable;
	private Actor				actor;
	private Comment				parentComment;
	private Collection<Comment>	replies;


	@Valid
	@ManyToOne(optional = false)
	public Commentable getCommentable() {
		return this.commentable;
	}

	public void setCommentable(final Commentable commentable) {
		this.commentable = commentable;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@Valid
	@ManyToOne(optional = true)
	public Comment getParentComment() {
		return this.parentComment;
	}

	public void setParentComment(final Comment parentComment) {
		this.parentComment = parentComment;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "parentComment")
	public Collection<Comment> getReplies() {
		return this.replies;
	}

	public void setReplies(final Collection<Comment> replies) {
		this.replies = replies;
	}

}
