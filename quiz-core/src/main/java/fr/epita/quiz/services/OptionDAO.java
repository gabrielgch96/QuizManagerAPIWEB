package fr.epita.quiz.services;

import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Option;
import fr.epita.quiz.datamodel.Question;
import net.bytebuddy.asm.Advice.This;

@Repository
public class OptionDAO extends DAO<Option>{
	
	private Question question;

	@Override
	protected String getQueryString() {
		
		return "from Option opt where opt.question = :pQuestion";
	}

	@Override
	protected void fillParametersMap(Map<String, Object> map, Option t) {
		map.put("pQuestion", this.question);
		
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	

}
