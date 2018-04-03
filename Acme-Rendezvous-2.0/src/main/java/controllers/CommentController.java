package controllers;

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

import services.CommentService;
import services.RendezvousService;
import domain.Comment;
import domain.Petition;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService commentService;

	 @Autowired
	 private RendezvousService rendezvousService;

	 
	// Constructors ---------------------------------------------------------

	public CommentController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required=false) Integer rendezvousId) {
		ModelAndView result;

		Collection<Comment> comment;
		comment = this.commentService.findAll();
		
		Petition rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		if(rendezvousId!=null){
			comment = rendezvous.getComments();
		}

		result = new ModelAndView("comment/list");

		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/list.do");

		return result;
	}
	
	@RequestMapping(value = "/listReplies", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int commentId) {
		ModelAndView result;
		
		Comment comment = new Comment();
		comment = this.commentService.findOne(commentId);
		
		Collection<Comment> comments;
		comments = comment.getReplies();

		result = new ModelAndView("comment/list");

		result.addObject("comment", comments);
		result.addObject("requestURI", "comment/listReplies.do");

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayComment(@RequestParam int commentId) {
		ModelAndView result;
		
		Comment comment;
		comment = this.commentService.findOne(commentId);
		
		result = new ModelAndView("comment/display");

		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/display.do");

		return result;
	}
	
	@RequestMapping(value = "/rendezvous/list", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int rendezvousId) {
		ModelAndView result;
		Petition rendezvous;
		
		Collection<Comment> comment;
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		comment = rendezvous.getComments();

		result = new ModelAndView("comment/list");

		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/rendezvous/list.do");

		return result;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment = new Comment();

		comment = commentService.findOne(commentId);
		Assert.notNull(comment);
		result = this.createEditModelAndView(comment);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView create(@Valid final Comment comment,
			final BindingResult binding) {
		ModelAndView result;
		
		Assert.notNull(comment);
		try{
			this.commentService.delete(comment);
			result = new ModelAndView("redirect:list.do");
		}catch (final Throwable oops) {
			result = this
					.createEditModelAndView(comment, "comment.commit.error");
		}
		
		return result;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/rendezvous/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Comment c;

		c = this.commentService.create();
		result = this.createEditModelAndView(c);
		return result;
	}
			

			// Edition ----------------------------------------------------------------

			//Save
			@RequestMapping(value = "rendezvous/edit", method = RequestMethod.POST, params = "save")
			public ModelAndView save(Comment comment, final BindingResult binding) {
				ModelAndView result;

				comment = this.commentService.save(comment);
				if (binding.hasErrors())
					result = this.createEditModelAndView(comment);
				else
					try {
						this.commentService.save(comment);
						result = new ModelAndView("redirect:list.do" );
					} catch (final Throwable oops) {
						result = this.createEditModelAndView(comment, "comment.commit.error");
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
		Comment commentParent= new Comment();
		
		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("comment", commentParent);
		
		result.addObject("message", message);

		return result;
	}
	
//	protected ModelAndView createEditModelAndView(final CommentForm comment) {
//		ModelAndView result;
//
//		result = this.createEditModelAndView(comment, null);
//
//		return result;
//	}
//
//	protected ModelAndView createEditModelAndView(final CommentForm comment,
//			final String message) {
//		ModelAndView result;
//
//		result = new ModelAndView("comment/edit");
//		result.addObject("commentForm", comment);
//		result.addObject("message", message);
//
//		return result;
//	}
}
