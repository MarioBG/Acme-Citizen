
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import domain.Citizen;
import domain.Comment;
import domain.Commentable;
import domain.GovernmentAgent;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;


	// Constructors ---------------------------------------------------------

	public CommentController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer commentableId, @RequestParam(required = false) final Integer commentId) {

		ModelAndView result;
		Collection<Comment> comments = new ArrayList<Comment>();
		Commentable commentable = null;
		Comment parentComment = null;

		if (commentableId != null) {
			comments = this.commentService.findByCommentableId(commentableId);
			commentable = this.commentableService.findOne(commentableId);
		} else if (commentId != null) {
			parentComment = this.commentService.findOne(commentId);
			comments = parentComment.getReplies();
			commentable = parentComment.getCommentable();
		}

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("commentable", commentable);
		result.addObject("parentComment", parentComment);
		result.addObject("requestURI", "comment/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId) {

		ModelAndView result;
		Comment comment;
		String role = "";

		comment = this.commentService.findOne(commentId);
		if (comment.getActor() instanceof Citizen)
			role = "citizen";
		else if (comment.getActor() instanceof GovernmentAgent)
			role = "governmentAgent";

		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		result.addObject("role", role);

		return result;
	}
	// Editing ---------------------------------------------------------------

	//	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(final int chirpId) {
	//		ModelAndView result;
	//		final Chirp chirp = this.chirpService.findOne(chirpId);
	//
	//		result = this.createEditModelAndView(chirp);
	//
	//		return result;
	//	}
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

}
