
package controllers.governmentAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping("/comment/governmentAgent")
public class CommentGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService	commentService;


	// Constructors ---------------------------------------------------------

	public CommentGovernmentAgentController() {
		super();
	}

	// Delete  ----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int commentId) {

		ModelAndView res = null;
		final Comment comment = this.commentService.findOne(commentId);

		try {
			this.commentService.delete(comment);
			if (comment.getParentComment() != null)
				res = new ModelAndView("redirect:../list.do?commentId=" + comment.getParentComment().getId());
			else if (comment.getParentComment() == null)
				res = new ModelAndView("redirect:../list.do?commentableId=" + comment.getCommentable().getId());
		} catch (final Throwable oops) {
			if (comment.getParentComment() != null)
				res = new ModelAndView("redirect:../list.do?commentId=" + comment.getParentComment().getId());
			else if (comment.getParentComment() == null)
				res = new ModelAndView("redirect:../list.do?commentableId=" + comment.getCommentable().getId());
		}

		return res;
	}
}
