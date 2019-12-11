package fr.epita.quiz.test.services;
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

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.User;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestQuestion {
	
	@Inject
	QuestionDAO questionDAO;
	
	@Inject
	UserDAO userdao;
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	@Transactional
	public void testCreateUser() {
		//given
		User user = new User(null,null,"email@gmail.com", "gabo", null);
		
		//QuestionDAO questionDAO = new QuestionDAO();
		//when
		userdao.create(user);
		List<User> ret = userdao.search(user);
		
		Query searchQuery = em.createQuery("select q from User q where q.email='email@gmail.com'");
		List<Question> retrieved = searchQuery.getResultList();
		
		//then
		System.out.println(ret.size());
		System.out.println("Query: "+retrieved.size());
		Assert.assertNotNull(userdao.getById(user.getId(), User.class));
		
	}
	
	@Test
	public void testCreate() {
		//given
		Question question = new Question("What is Dependency Injection ?", null);
		
		//QuestionDAO questionDAO = new QuestionDAO();
		//when
		questionDAO.create(question);
		
		//then
		Assert.assertNotNull(questionDAO.getById(question.getId(), Question.class));
		
	}
	
	@Test
	@Transactional  //cannot delete without it...how to fix in DAO??
	public void testDelete() {
		
		//Given
		Question question = new Question("What is Dependency Injection?", null);
		questionDAO.create(question);
		//When
		
		questionDAO.delete(question);
		
		//Then
		Question retrievedQuestion = questionDAO.getById(question.getId(), Question.class);
		Assert.assertNull(retrievedQuestion);
		
	}
	
	
	@Test
	public void testUpdate() {
		
		Question question = new Question("What is Dependency Injection?", null);
		questionDAO.create(question);
		
		String newText = "What does Spring do in Java?";
		question.setContent(newText);
		questionDAO.update(question);
		
		Question retrieved = questionDAO.getById(question.getId(), Question.class);
		Assert.assertEquals(newText, retrieved.getContent());
		
	}
	
	@Test
	public void testSearch() {
		
		Question question = new Question("What is Dependency Injection?", null);
		questionDAO.create(question);
		
		List<Question> retrievedQuestions = questionDAO.search(new Question("Dependency",null));
		
		Assert.assertNotNull(retrievedQuestions);
		
	}

}
