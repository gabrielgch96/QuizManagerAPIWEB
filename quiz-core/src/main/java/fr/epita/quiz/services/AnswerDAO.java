package fr.epita.quiz.services;

import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Answer;

@Repository
public class AnswerDAO extends DAO<Answer>{

	@Override
	protected String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fillParametersMap(Map<String, Object> map, Answer t) {
		// TODO Auto-generated method stub
		
	}

}
