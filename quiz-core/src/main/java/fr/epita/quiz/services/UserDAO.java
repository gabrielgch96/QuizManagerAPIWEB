package fr.epita.quiz.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.User;

@Repository
public class UserDAO extends DAO<User>{
	
	private static String str_validate = "select q from User q where q.email=:pEmail and q.password=:pPwd";
	private static final Logger LOGGER = LogManager.getLogger(); 
	
	public List<User> validateUser(User user) {
		Query searchQuery = em.createQuery(str_validate);
		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("pEmail", user.getEmail());
		parameters.put("pPwd", user.getPassword());
		parameters.forEach((k, v) -> searchQuery.setParameter(k, v));
		LOGGER.info("user authenticate: "+ user.getEmail()+" with pwd: "+ user.getPassword());
		return (List<User>) searchQuery.getResultList();
	}

	@Override
	protected void fillParametersMap(Map<String, Object> map, User user) {
		map.put("pFirst", "%"+user.getFirst_name()+"%");
		map.put("pLast", "%"+user.getLast_name()+"%");
		
	}

	@Override
	protected String getQueryString() {
		return "SELECT q FROM User q WHERE q.first_name LIKE :pFirst OR q.last_name LIKE :pLast";
	}

}
