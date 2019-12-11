package fr.epita.quiz.services;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Option;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Topic;

@Repository
public class QuestionService {
	
	private static final Logger LOGGER = LogManager.getLogger(); 

	@Inject
	TopicDAO topicdao;

	@Inject
	QuestionDAO questiondao;

	@Inject
	OptionDAO optiondao;

	public void createQuestion(Question question) throws NoSuchElementException {
		Topic questionTopic = null;
		List<Topic> topic_search = topicdao.search(question.getTopic());
		for (Topic topic : topic_search) {
			if (topic.getName().equals(question.getTopic().getName())) {
				questionTopic = topic;
				break;
			}
		}
		if (questionTopic != null) {
			question.setTopic(questionTopic);
			questiondao.create(question);
			LOGGER.info("question created with id: "+ question.getId());
		} else
			throw new NoSuchElementException();
	}

	public void createQuestionWithChoices(Question question, Option... options) {

		try {
			createQuestion(question);
			for (Option option : options) {
				option.setQuestion(question);
				optiondao.create(option);
				LOGGER.info("option created with id: "+ option.getId());
			}
		} catch (NoSuchElementException e) {
			LOGGER.error(e);
		}
	}

	public void updateQuestionOptions(Question question, Option... options) {
		// get options saved in database
		Option criteria = new Option();
		criteria.setQuestion(question);
		List<Option> before = optiondao.search(criteria);
		// transform updated options array to list for easier lookup
		List<Option> updated = Arrays.asList(options);

		for (Option optionNew : updated) {
			if(optionNew.getId() == 0){
				Option temp = new Option(optionNew.getText(), optionNew.getIs_right());
				temp.setQuestion(question);
				optiondao.create(temp);
				LOGGER.info("option created with id: "+ temp.getId());
				continue;
			}
			for (Option optionOld : before) {
				if (optionNew.getId().equals(optionOld.getId())) {
					optionOld.setText(optionNew.getText());
					optionOld.setIs_right(optionNew.getIs_right());
					optiondao.update(optionOld);
					LOGGER.info("option updated with id: "+ optionOld.getId());
					break;
				}
			}
		}
		removeDeletedOptions(updated, before);
	}
	
	private void removeDeletedOptions(List<Option> newOptions, List<Option> oldOptions) {
		int flag;
		for(Option old : oldOptions) {
			flag =0;
			for(Option newOne: newOptions) {
				if(old.getId() == newOne.getId()) {
					flag =1;
					break;
				}
			}
			if(flag == 0) {
				optiondao.delete(old);
				LOGGER.info("option deleted with id: "+ old.getId());
			}
		}
	}

	public void deleteQuestion(Question question) {

		Option criteria = new Option();
		criteria.setQuestion(question);
		List<Option> questionOptions = optiondao.search(criteria);
		for (Option option : questionOptions) {
			optiondao.delete(option);
			LOGGER.info("option deleted with id: "+ option.getId()+ "belonged to question wId: "+question.getId());
		}
		questiondao.delete(question);
		LOGGER.info("question deleted with id: "+ question.getId());
	}


}
