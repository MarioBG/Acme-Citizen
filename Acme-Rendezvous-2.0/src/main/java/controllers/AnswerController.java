/*
 * package controllers;
 * 
 * import java.util.ArrayList;
 * import java.util.Collection;
 * 
 * import javax.validation.Valid;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Controller;
 * import org.springframework.util.Assert;
 * import org.springframework.validation.BindingResult;
 * import org.springframework.web.bind.annotation.RequestMapping;
 * import org.springframework.web.bind.annotation.RequestMethod;
 * import org.springframework.web.bind.annotation.RequestParam;
 * import org.springframework.web.servlet.ModelAndView;
 * 
 * import services.AnswerService;
 * import services.QuestionService;
 * import services.RendezvousService;
 * import services.UserService;
 * import domain.Answer;
 * import domain.Question;
 * import domain.Rendezvous;
 * 
 * @Controller
 * 
 * @RequestMapping("/answer")
 * public class AnswerController extends AbstractController {
 * 
 * // Services ------------------------------------------------------
 * 
 * @Autowired
 * private AnswerService answerService;
 * 
 * @Autowired
 * private QuestionService questionService;
 * 
 * @Autowired
 * private UserService userService;
 * 
 * @Autowired
 * private RendezvousService rendezvousService;
 * 
 * 
 * // Constructors --------------------------------------------------
 * 
 * public AnswerController() {
 * super();
 * }
 * 
 * // Listing -------------------------------------------------------
 * 
 * @RequestMapping(value = "/user/list", method = RequestMethod.GET)
 * public ModelAndView list(@RequestParam(required = true) final Integer questionId) {
 * 
 * final Collection<Answer> answers = new ArrayList<Answer>();
 * 
 * final Question question = this.questionService.findOne(questionId);
 * Assert.notNull(question);
 * answers.addAll(this.answerService.findByQuestionId(questionId));
 * 
 * final ModelAndView result = new ModelAndView("answer/list");
 * result.addObject("answers", answers);
 * result.addObject("question", question);
 * result.addObject("requestURI", "question/user/list.do");
 * 
 * return result;
 * }
 * 
 * @RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
 * public ModelAndView create(@Valid final Question question, final BindingResult binding) {
 * ModelAndView result;
 * 
 * Assert.notNull(question);
 * try {
 * this.questionService.delete(question);
 * result = new ModelAndView("redirect:list.do");
 * } catch (final Throwable oops) {
 * result = this.createEditModelAndView(question, "comment.commit.error");
 * }
 * 
 * return result;
 * }
 * 
 * //Ancillary methods
 * 
 * protected ModelAndView createEditModelAndView(final Question question) {
 * ModelAndView result;
 * 
 * result = this.createEditModelAndView(question, null);
 * 
 * return result;
 * }
 * 
 * protected ModelAndView createEditModelAndView(final Question question, final String message) {
 * ModelAndView result;
 * final Rendezvous r = question.getRendezvous();
 * result = new ModelAndView("question/edit");
 * 
 * result.addObject("question", question);
 * result.addObject("rendezvous", r);
 * result.addObject("message", message);
 * 
 * return result;
 * }
 * }
 */
