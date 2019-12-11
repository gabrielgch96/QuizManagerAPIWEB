package fr.epita.quiz.test.services;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quiz.datamodel.Option;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.OptionDAO;
import fr.epita.quiz.services.QuestionService;
import fr.epita.quiz.services.QuestionDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestQuestionOptions {
	
	@Inject
	QuestionDAO questionDAO;
	
	@Inject
	OptionDAO optionDAO;
	
	@Inject
	QuestionService questchoiceservice;
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	public void testCreate() {
		//given
		Question question = new Question("How much is 2x2 ?", null);
		Option option = new Option("4",true);
		Option option2 = new Option("8",false);
		Option option3 = new Option("5",false);
		Option[] options = {option, option2, option3};
		//when
		
		questchoiceservice.createQuestionWithChoices(question, options);
		
		//then
		Assert.assertNotNull(questionDAO.getById(question.getId(), Question.class));
		for(Option opt : options) {
			Assert.assertNotNull(optionDAO.getById(opt.getId(), Option.class));
		}
		
	}
	
	@Test
	@Transactional  //cannot delete without it...how to fix in DAO??
	public void testDelete() {
		
		//given
		Question question = new Question("How much is 2x2 ?", null);
		Option option = new Option("4",true);
		Option option2 = new Option("8",false);
		Option option3 = new Option("5",false);
		Option[] options = {option, option2, option3};
		
		
		questchoiceservice.createQuestionWithChoices(question, options);
		Integer id = question.getId();		
		for(Option op:options)
			System.out.println(op.getText());
		//When
		
		questchoiceservice.deleteQuestion(question);
		
		//Then
		option.setQuestion(question);
		List<Option> retrieved = optionDAO.search(option);
		if(retrieved != null)
			for(Option op:retrieved)
				System.out.println(op.getText());
		Assert.assertTrue(retrieved.isEmpty() );
		
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
