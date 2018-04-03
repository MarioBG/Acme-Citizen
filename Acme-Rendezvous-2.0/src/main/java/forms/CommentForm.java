package forms;

import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import domain.Comment;
import domain.Petition;

public class CommentForm {

	// Constructors

	public CommentForm() {
		super();
	}

	// Attributes

	private String text;
	private String picture;
	
	private int id;
	private Petition rendezvous;
	private Comment commentParent;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(optional = false)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Petition getRendezvous() {
		return rendezvous;
	}

	public void setRendezvous(Petition rendezvous) {
		this.rendezvous = rendezvous;
	}

	@Valid
	@ManyToOne(optional = true)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Comment getCommentParent() {
		return commentParent;
	}

	public void setCommentParent(Comment commentParent) {
		this.commentParent = commentParent;
	}
	
	
}
