package fr.epita.quiz.test.services;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Topic;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.QuestionService;
import fr.epita.quiz.services.TopicDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestQuestionTopic {
	
	@Inject
	TopicDAO topicDAO;
	
	@Inject 
	QuestionDAO questionDAO;
	
	@Inject 
	QuestionService questionservice;
	
	@Test
	//@Transactional
	public void testCreate() {
		Topic topic = new Topic("Java");
		topicDAO.create(topic);
		Question question = new Question("What is Java", topic);
		try {
			questionservice.createQuestion(question);
		}catch(NoSuchElementException nsee) {
		
		}
		//Assert.assertNotNull(topicDAO.search(topic));
		Assert.assertNotNull(question);
	}
	
	@Test
	public void testSearchAllTopics() {
		Topic topic = new Topic("Java");
		topicDAO.create(topic);
		Topic topic2 = new Topic("Maven");
		topicDAO.create(topic2);
		topic2.setName("");
		List<Topic> topics = topicDAO.search(topic2);
		
		for(Topic t : topics)
			System.out.println(t.getId()+"-"+t.getName()+"\n");
		
		Assert.assertNotNull(topics);
	}
	
	@Test
	public void testSearchAllQuestionsByTopic() {
		Topic topic = new Topic("Java");
		topicDAO.create(topic);
		Question question = new Question("What is Java?", topic);
		questionDAO.create(question);
		question = new Question("How do you compile Java?", topic);
		questionDAO.create(question);
		question.setContent("");
		List<Question> retrieved = questionDAO.search(question);
		
		for(Question t : retrieved)
			System.out.println(t.getId()+"-"+t.getContent()+"\n");
		
		Assert.assertNotNull(retrieved);
	}

}
