/*
 * package controllers.user;
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
 * import services.QuestionService;
 * import services.RendezvousService;
 * import services.UserService;
 * import controllers.AbstractController;
 * import domain.Answer;
 * import domain.Question;
 * import domain.Rendezvous;
 * import domain.User;
 * 
 * @Controller
 * 
 * @RequestMapping("/question/user")
 * public class QuestionUserController extends AbstractController {
 * 
 * // Services —----------------------------------------------------
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
 * // Constructors —------------------------------------------------
 * 
 * public QuestionUserController() {
 * super();
 * }
 * 
 * // Listing —---------------------------------------------------—
 * 
 * @RequestMapping(value = "/list", method = RequestMethod.GET)
 * public ModelAndView list(@RequestParam(required = true) final Integer rendezvousId) {
 * 
 * final Collection<Question> questions = new ArrayList<Question>();
 * 
 * final User principal = this.userService.findByPrincipal();
 * final Rendezvous r = this.rendezvousService.findOne(rendezvousId);
 * Assert.notNull(r);
 * questions.addAll(this.questionService.findByRendezvousId(rendezvousId));
 * 
 * final Boolean isPrincipal = principal.equals(r.getOrganiser());
 * 
 * final ModelAndView result = new ModelAndView("question/list");
 * result.addObject("questions", questions);
 * result.addObject("isPrincipal", isPrincipal);
 * result.addObject("requestURI", "question/user/list.do");
 * 
 * return result;
 * }
 * 
 * @RequestMapping(value = "/create", method = RequestMethod.GET)
 * public ModelAndView create(@RequestParam final int rendezvousId) {
 * final ModelAndView res;
 * Question q;
 * Rendezvous r;
 * 
 * r = this.rendezvousService.findOne(rendezvousId);
 * Assert.notNull(r);
 * q = this.questionService.create(r);
 * 
 * res = this.createEditModelAndView(q);
 * 
 * return res;
 * }
 * 
 * @RequestMapping(value = "/edit", method = RequestMethod.GET)
 * public ModelAndView edit(@RequestParam final int questionId) {
 * ModelAndView result;
 * Question question;
 * Rendezvous rendezvous;
 * Collection<Answer> answers;
 * 
 * question = this.questionService.findOne(questionId);
 * Assert.notNull(question);
 * rendezvous = this.rendezvousService.findOne(question.getRendezvous().getId());
 * answers = question.getAnswers();
 * result = this.createEditModelAndView(question);
 * result.addObject("rendezvous", rendezvous);
 * result.addObject("answers", answers);
 * 
 * return result;
 * }
 * 
 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
 * public ModelAndView save(@Valid final Question question, final BindingResult binding) {
 * ModelAndView res;
 * final int id = question.getRendezvous().getId();
 * 
 * if (binding.hasErrors())
 * res = this.createEditModelAndView(question);
 * else
 * try {
 * this.questionService.save(question);
 * res = new ModelAndView("redirect:/question/user/list.do?rendezvousId=" + id);
 * } catch (final Throwable oops) {
 * res = this.createEditModelAndView(question, "question.commit.error");
 * }
 * 
 * return res;
 * }
 * 
 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
 * public ModelAndView delete(@Valid final Question question, final BindingResult binding) {
 * ModelAndView result;
 * 
 * Assert.notNull(question);
 * try {
 * this.questionService.delete(question);
 * result = new ModelAndView("redirect:/question/user/list.do?rendezvousId=" + question.getRendezvous().getId());
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
 * result = new ModelAndView("question/edit");
 * 
 * result.addObject("question", question);
 * result.addObject("message", message);
 * 
 * return result;
 * }
 * }
 */
