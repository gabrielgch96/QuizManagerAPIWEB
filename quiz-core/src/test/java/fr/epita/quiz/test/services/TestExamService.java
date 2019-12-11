package fr.epita.quiz.test.services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Topic;
import fr.epita.quiz.datamodel.User;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.services.ExamService;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.TopicDAO;
import fr.epita.quiz.services.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestExamService {
	
	@Inject 
	ExamDAO examdao;
	
	@Inject
	QuestionDAO questiondao;
	
	@Inject
	UserDAO userdao;
	
	@Inject
	ExamService examserv;
	
	@Inject
	TopicDAO topicdao;
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	@Transactional
	public void testCreate() {
		Exam exam = new Exam("Java Principles");
		examdao.create(exam);
		Topic topic = new Topic("Java");
		topicdao.create(topic);
		Question question = new Question("How much is 2x2 ?", topic);
		Question question2 = new Question("How much is 2x3 ?", topic);
		Question question3 = new Question("How much is 2x4 ?", topic);
		Question question4 = new Question("How much is 2x5 ?", topic);
		List<Question> questions = Arrays.asList(question, question2, question3, question4);
		questiondao.create(question);
		questiondao.create(question2);
		questiondao.create(question3);
		questiondao.create(question4);
		
		examserv.addQuestionsExam(exam, question, question2, question3, question4);
		//Query searchQuery = em.createQuery("FROM Question q JOIN FETCH q.exams eq WHERE eq.id="+exam.getId());
		Query searchQuery = em.createQuery("select q from Question q join fetch q.exams eq where eq.id="+exam.getId());
		List<Question> retrieved = searchQuery.getResultList();
		for(Question item: retrieved) {
			System.out.println("Id: "+ item.getId()+"--"+item.getContent());
		}
		System.out.println("In exam: ");
		for(Question item: exam.getQuestions()) {
			System.out.println("Id: "+ item.getId()+"--"+item.getContent());
		}
		Assert.assertEquals(questions,retrieved);
		
	}
	
	@Test
	@Transactional
	public void testUpdateExamQuestions() {
		Exam exam = new Exam("Java Principles");
		Topic topic = new Topic("Java");
		topicdao.create(topic);
		Question question = new Question("How much is 2x2 ?", topic);
		Question question2 = new Question("How much is 2x3 ?", topic);
		Question question3 = new Question("How much is 2x4 ?", topic);
		Question question4 = new Question("How much is 2x5 ?", topic);
		List<Question> questions = new LinkedList<Question>(Arrays.asList(question, question2, question3, question4));
		examdao.create(exam);
		for(Question q:questions)
			questiondao.create(q);
		examserv.addQuestionsExam(exam, question, question2, question3, question4);
		
		Question newQuestion = new Question("How much is 20-1?", null);
		questiondao.create(newQuestion);
		questions.remove(question4);
		questions.add(newQuestion);
		Question[] questionsArray = questions.toArray(new Question[questions.size()]);  
		examserv.updateExamQuestions(exam, questionsArray);
		
		Query searchQuery = em.createQuery("select q from Question q join fetch q.exams eq where eq.id="+exam.getId());
		List<Question> retrieved = searchQuery.getResultList();
		Assert.assertTrue(retrieved.contains(newQuestion));
		Assert.assertFalse(retrieved.contains(question4));
	}
	
	@Test
	@Transactional
	public void testDeleteExam() {
		//given
		User user = new User("Gabriel", "Garces", "testing@gmail.com", "abcd", true);
		Exam exam = new Exam("Java Principles");
		Question question = new Question("How much is 2x2 ?", null);
		Question question2 = new Question("How much is 2x3 ?", null);
		Question question3 = new Question("How much is 2x4 ?", null);
		Question question4 = new Question("How much is 2x5 ?", null);
		List<Question> questions = Arrays.asList(question, question2, question3, question4);
		List<Question> retrieved = new ArrayList<Question>();
		userdao.create(user);
		examdao.create(exam);
		for(Question q:questions)
			questiondao.create(q);
		examserv.addQuestionsExam(exam, question, question2, question3, question4);
		//when
		examserv.deleteExam(exam);
		
		//then
		for(Question q : questions)
			retrieved.add(questiondao.getById(q.getId(), Question.class));
		
		Assert.assertEquals(questions, retrieved);
		Assert.assertEquals(0, exam.getQuestions().size());
		//System.out.println(retrieved.get(0).getExams().size());
		Assert.assertNull(examdao.getById(exam.getId(), Exam.class));
		
	}

}
