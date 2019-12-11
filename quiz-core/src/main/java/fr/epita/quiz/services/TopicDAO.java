package fr.epita.quiz.services;

import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Topic;

@Repository
public class TopicDAO extends DAO<Topic>{

	@Override
	protected String getQueryString() {
		return "from Topic t where t.name like :pName";
	}

	@Override
	protected void fillParametersMap(Map<String, Object> map, Topic topic) {
		map.put("pName", "%" + topic.getName() +"%");
		
	}

}
