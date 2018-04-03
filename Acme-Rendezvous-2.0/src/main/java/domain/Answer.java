/*
 * package domain;
 * 
 * import javax.persistence.Access;
 * import javax.persistence.AccessType;
 * import javax.persistence.Entity;
 * import javax.persistence.Index;
 * import javax.persistence.ManyToOne;
 * import javax.persistence.Table;
 * import javax.validation.Valid;
 * 
 * import org.hibernate.validator.constraints.NotBlank;
 * import org.hibernate.validator.constraints.SafeHtml;
 * 
 * @Entity
 * 
 * @Access(AccessType.PROPERTY)
 * 
 * @Table(indexes = {@Index(columnList = "question_Id,user_Id") })
 * public class Answer extends DomainEntity {
 * 
 * private String content;
 * 
 * 
 * @NotBlank
 * 
 * @SafeHtml
 * public String getContent() {
 * return this.content;
 * }
 * 
 * public void setContent(final String title) {
 * this.content = title;
 * }
 * 
 * 
 * //Relationships
 * 
 * private Question question;
 * private User user;
 * 
 * 
 * @Valid
 * 
 * @ManyToOne(optional=true)
 * public Question getQuestion() {
 * return this.question;
 * }
 * 
 * public void setQuestion(final Question question) {
 * this.question = question;
 * }
 * 
 * @Valid
 * 
 * @ManyToOne(optional= true)
 * public User getUser() {
 * return this.user;
 * }
 * 
 * public void setUser(final User user) {
 * this.user = user;
 * }
 * 
 * }
 */
