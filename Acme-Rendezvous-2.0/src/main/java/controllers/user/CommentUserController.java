package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Comment;
import domain.Petition;
import domain.Citizen;
import forms.CommentForm;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private RendezvousService rendezvousService;

	// Constructors ---------------------------------------------------------

	public CommentUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/listReplies", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int commentId) {
		ModelAndView result;

		Comment comment = new Comment();
		comment = this.commentService.findOne(commentId);

		Collection<Comment> comments;
		comments = comment.getReplies();

		result = new ModelAndView("comment/list");

		result.addObject("comment", comments);
		result.addObject("requestURI", "comment/user/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Comment c;
		c = this.commentService.create();
		
		CommentForm cForm = this.commentService.construct(c);
		result = this.createEditModelAndViewForm(cForm);
		return result;
	}

	@RequestMapping(value = "/editReplies", method = RequestMethod.GET)
	public ModelAndView createReplies(@RequestParam int commentId) {
		ModelAndView result;
		Comment c;
		Comment commentParent;
		commentParent = this.commentService.findOne(commentId);

		c = this.commentService.create();
		
		c.setCommentParent(commentParent);
		c.setRendezvous(commentParent.getRendezvous());
		
		CommentForm cForm = this.commentService.construct(c);

		result = this.createEditModelAndViewReplyForm(cForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(CommentForm commentForm, final BindingResult binding) {
		ModelAndView result;
		int rendezvousId = 0;

		if (binding.hasErrors())
			result = this.createEditModelAndViewForm(commentForm,
					"comment.params.error");
		else
			try {
				Comment comment = this.commentService.reconstruct(commentForm, binding);
				this.commentService.save(comment);
				rendezvousId = comment.getRendezvous().getId();
				result = new ModelAndView(
						"redirect:/comment/rendezvous/list.do?rendezvousId="
								+ rendezvousId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewForm(commentForm,
						"comment.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/editReplies", method = RequestMethod.POST, params = "saveReply")
	public ModelAndView saveReply(CommentForm commentForm, final BindingResult binding) {
		ModelAndView result;
		int rendezvousId = 0;

		// Collection<Comment> replies = new ArrayList<Comment>();
		// Comment commentParent;
		//
		// commentParent = comment.getCommentParent();
		// replies = commentParent.getReplies();
		// replies.add(comment);
		//
		if (binding.hasErrors())
			result = this.createEditModelAndViewReplyForm(commentForm,
					"comment.params.error");
		else
			try {
				Comment comment= this.commentService.reconstruct(commentForm, binding);
				this.commentService.save(comment);
				rendezvousId = comment.getRendezvous().getId();
				result = new ModelAndView(
						"redirect:/comment/rendezvous/list.do?rendezvousId="
								+ rendezvousId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewReplyForm(commentForm,
						"comment.commit.error");
			}
		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment,
			final String message) {
		ModelAndView result;
		Citizen u = new Citizen();
		Collection<Petition> rendezvous;

		u = this.userService.findByPrincipal();
		rendezvous = this.rendezvousService.findByAttendantId(u.getId());

		result = new ModelAndView("comment/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("comment", comment);
		result.addObject("message", message);
		result.addObject("requestURI", "comment/user/edit.do");

		return result;
	}

	protected ModelAndView createEditModelAndViewReply(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndViewReply(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewReply(final Comment comment,
			final String message) {
		ModelAndView result;
		// Rendezvous rendezvous;
		// Comment commentParent;
		//
		// rendezvous = comment.getRendezvous();
		// commentParent = comment.getCommentParent();

		Collection<Petition> rendezvous = new ArrayList<Petition>();
		rendezvous.add(comment.getRendezvous());
		Collection<Comment> comments = new ArrayList<Comment>();
		comments.add(comment.getCommentParent());

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("rendezvous", rendezvous);
		result.addObject("commentParent", comments);
		result.addObject("message", message);
		result.addObject("requestURI", "comment/user/editReplies.do");

		return result;
	}

	protected ModelAndView createEditModelAndViewForm(final CommentForm comment) {
		ModelAndView result;

		result = this.createEditModelAndViewForm(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewForm(
			final CommentForm commentForm, final String message) {

		//this.commentService.checkPrincipalForm(commentForm);

		ModelAndView result;
		Citizen u = new Citizen();
		Collection<Petition> rendezvous;

		u = this.userService.findByPrincipal();
		rendezvous = this.rendezvousService.findByAttendantId(u.getId());

		result = new ModelAndView("comment/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("commentForm", commentForm);
		result.addObject("message", message);
		result.addObject("requestURI", "comment/user/edit.do");

		return result;
	}
	
	protected ModelAndView createEditModelAndViewReplyForm(final CommentForm commentForm) {
		ModelAndView result;

		result = this.createEditModelAndViewReplyForm(commentForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewReplyForm(final CommentForm commentForm,
			final String message) {
		ModelAndView result;
		// Rendezvous rendezvous;
		// Comment commentParent;
		//
		// rendezvous = comment.getRendezvous();
		// commentParent = comment.getCommentParent();
		

		Collection<Petition> rendezvous = new ArrayList<Petition>();
		rendezvous.add(commentForm.getRendezvous());
		Collection<Comment> comments = new ArrayList<Comment>();
		comments.add(commentForm.getCommentParent());

		result = new ModelAndView("comment/edit");
		result.addObject("commentForm", commentForm);
		result.addObject("rendezvous", rendezvous);
		result.addObject("commentParent", comments);
		result.addObject("message", message);
		result.addObject("requestURI", "comment/user/editReplies.do");

		return result;
	}

}
