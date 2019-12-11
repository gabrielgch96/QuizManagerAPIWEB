package fr.epita.quiz.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Question;

@Repository
public class QuestionDAO extends DAO<Question> {
	
	private static String query_getQuestions = "select q from Question q join fetch q.exams eq where eq.id=:pId";
	private String getIdsQuery = "SELECT q.id FROM Question q";
	private String randomQuery = "SELECT q FROM Question q WHERE q.id =:random";
	private static int LIMIT = 10;

	@Override
	protected String getQueryString() {
		return "from Question q where q.content like :pContent ";//and q.topic = :pTopic";
	}

	@Override
	protected void fillParametersMap(Map<String,Object> map, Question question) {
		map.put("pContent", "%" + question.getContent() +"%");
		//map.put("pTopic", question.getTopic());
	}
	
	public List<Question> getQuestions(Integer id) {
		Query searchQuery = em.createQuery(query_getQuestions);
		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("pId", id);
		parameters.forEach((k, v) -> searchQuery.setParameter(k, v));

		return (List<Question>) searchQuery.getResultList();
	}
	
	public List<Question> randomQuestions(){
		int[] selected ;
		Query idsAvailable = em.createQuery(getIdsQuery);
		List<Integer> available = idsAvailable.getResultList();
		int max = LIMIT;
		int count = available.size();
		if(count >=10)
			selected = new int[LIMIT];
		else {
			selected = new int[count];
			max = count;
		}
		for(int i =0; i < max; i++) {
			Collections.shuffle(available);
			selected[i] = available.get(0).intValue();
			available.remove(0);
		}
		List<Question> results = new LinkedList<Question>();
		for(int id: selected) {
			results.add(getById(id, Question.class));
		}
		return results;
	}

}
