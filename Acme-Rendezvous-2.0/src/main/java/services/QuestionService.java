//
// package services;
//
// import java.util.Collection;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.util.Assert;
//
// import repositories.QuestionRepository;
// import security.UserAccountService;
// import domain.Answer;
// import domain.Question;
// import domain.Rendezvous;
//
// @Service
// @Transactional
// public class QuestionService {
//
// // Managed repository
//
// @Autowired
// private QuestionRepository questionRepository;
//
// // Supporting services
//
// @Autowired
// private AnswerService answerService;
//
// @Autowired
// private UserAccountService userAccountService;
//
// @Autowired
// private UserService userService;
//
//
// // @Autowired
// // private RendezvousesService rendezvousesService;
// //
// // Constructors
//
// public QuestionService() {
// super();
// }
//
// // Simple CRUD methods
//
// public Question create(final Rendezvous r) {
// final Question res = new Question();
//
// final Question a = new Question();
//
// a.setRendezvous(r);
//
// return res;
// }
//
// public Collection<Question> findAll() {
// Collection<Question> res;
// res = this.questionRepository.findAll();
// Assert.notNull(res);
// return res;
// }
//
// public Question findOne(final int questionId) {
// Assert.isTrue(questionId != 0);
// Question res;
// res = this.questionRepository.findOne(questionId);
// Assert.notNull(res);
// return res;
// }
//
// public Question save(final Question question) {
// Assert.notNull(question);
// Assert.isTrue(this.questionRepository.findByUserId(question.getRendezvous().getOrganiser().getId()) != null);
// Question res;
// res = this.questionRepository.save(question);
// return res;
// }
// public void delete(final Question question) {
// Assert.notNull(question);
// Assert.isTrue(question.getId() != 0);
// Assert.isTrue(this.questionRepository.exists(question.getId()));
// for (final Answer a : this.answerService.findByQuestionId(question.getId()))
// this.answerService.delete(a);
// this.questionRepository.delete(question);
// }
//
// // Other business methods
//
// public Collection<Question> findByRendezvousId(final Integer rendezvousId) {
// return this.questionRepository.findByRendezvousId(rendezvousId);
// }
//
//}
