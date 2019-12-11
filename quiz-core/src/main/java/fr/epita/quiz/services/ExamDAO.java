package fr.epita.quiz.services;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Exam;

@Repository
public class ExamDAO extends DAO<Exam>{

	@Override
	protected String getQueryString() {
		return "from Exam ex where ex.title like :pTitle";
	}

	@Override
	protected void fillParametersMap(Map<String, Object> map, Exam exam) {
		map.put("pTitle", "%" + exam.getTitle() +"%");
	}
	

}
