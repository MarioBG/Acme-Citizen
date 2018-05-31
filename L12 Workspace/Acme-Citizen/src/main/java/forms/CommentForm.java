
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

public class CommentForm {

	private int		id;
	private Integer	parentCommentId;
	private int		actorId;
	private int		commentableId;
	private Date	moment;
	private String	text;
	private String	picture;


	public CommentForm() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Integer getParentCommentId() {
		return this.parentCommentId;
	}

	public void setParentCommentId(final Integer parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(final int actorId) {
		this.actorId = actorId;
	}

	public int getCommentableId() {
		return this.commentableId;
	}

	public void setCommentableId(final int commentableId) {
		this.commentableId = commentableId;
	}

	@NotNull
	@Past
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

}
