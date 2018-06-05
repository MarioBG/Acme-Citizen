
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import controllers.AbstractController;
import domain.Comment;
import domain.Commentable;
import forms.CommentForm;

@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;


	// Constructors ---------------------------------------------------------

	public CommentActorController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Comment> comments;

		comments = this.commentService.findByPrincipal();

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("commentable", null);
		result.addObject("parentComment", null);
		result.addObject("requestURI", "comment/actor/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) final Integer commentableId, @RequestParam(required = false) final Integer commentId) {

		ModelAndView result;
		Comment comment = null;

		if (commentId != null) {
			final Comment parentComment = this.commentService.findOne(commentId);
			final Commentable commentable = parentComment.getCommentable();
			comment = this.commentService.create(commentable.getId(), parentComment.getId());
		} else if (commentableId != null)
			comment = this.commentService.create(commentableId, null);

		final CommentForm commentForm = this.commentService.construct(comment);

		result = this.createEditModelAndView(commentForm);
		return result;
	}

	// Edition -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int commentId) {

		final ModelAndView result;

		final Comment comment = this.commentService.findOne(commentId);
		Assert.isTrue(comment.getActor().equals(this.actorService.findByPrincipal()));
		final CommentForm commentForm = this.commentService.construct(comment);

		result = this.createEditModelAndView(commentForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CommentForm commentForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm);
		else
			try {
				final Comment comment = this.commentService.reconstruct(commentForm, binding);
				this.commentService.save(comment);
				if (commentForm.getParentCommentId() != null)
					result = new ModelAndView("redirect:../list.do?commentId=" + commentForm.getParentCommentId());
				else
					result = new ModelAndView("redirect:../list.do?commentableId=" + commentForm.getCommentableId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final CommentForm commentForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm);
		else
			try {
				final Comment comment = this.commentService.reconstruct(commentForm, binding);
				this.commentService.delete(comment);
				if (commentForm.getParentCommentId() != null)
					result = new ModelAndView("redirect:../list.do?commentId=" + commentForm.getParentCommentId());
				else
					result = new ModelAndView("redirect:../list.do?commentableId=" + commentForm.getCommentableId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error");
			}
		return result;
	}

	//
	//	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editSave(@Valid final Chirp c, final BindingResult binding) {
	//		ModelAndView res;
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(c, "user.params.error");
	//		else
	//			try {
	//				this.chirpService.save(c);
	//				res = new ModelAndView("redirect:/user/chirp/list.do");
	//			} catch (final Throwable oops) {
	//				res = this.createEditModelAndView(c, "user.commit.error");
	//			}
	//		System.out.println(binding);
	//
	//		return res;
	//	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CommentForm commentForm) {

		ModelAndView result;

		result = this.createEditModelAndView(commentForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CommentForm commentForm, final String messageCode) {

		ModelAndView result;

		if (commentForm.getId() == 0)
			result = new ModelAndView("comment/create");
		else
			result = new ModelAndView("comment/edit");
		result.addObject("commentForm", commentForm);
		result.addObject("message", messageCode);

		return result;
	}
}
