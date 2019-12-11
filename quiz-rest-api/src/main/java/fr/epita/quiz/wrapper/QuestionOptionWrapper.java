package fr.epita.quiz.wrapper;

import java.util.List;

import fr.epita.quiz.datamodel.Option;
import fr.epita.quiz.datamodel.Question;

public class QuestionOptionWrapper {
	
	Question question;
	List<Option> options;
	
	public QuestionOptionWrapper() {}
	
	public QuestionOptionWrapper(Question question, List<Option> options) {
		super();
		this.question = question;
		this.options = options;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public List<Option> getOptions() {
		return options;
	}
	
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	

}
