package fr.epita.quiz.services;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public abstract class DAO<T> {

	@PersistenceContext
	EntityManager em;

	@Transactional(Transactional.TxType.REQUIRED)
	public void create(T t) {
		em.persist(t);
		//em.flush();
	}

	@Transactional
	public T getById(Serializable id, Class<T> clazz) {
		return em.find(clazz, id);
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void update(T t) {
		em.merge(t);
		//em.flush();
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void delete(T t) {
		em.remove(em.contains(t) ? t : em.merge(t));
		//em.remove(t);
		//em.flush();
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public List<T> search(T criteria) {

		Query searchQuery = em.createQuery(getQueryString());
		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		fillParametersMap(parameters, criteria);
		
		parameters.forEach((k, v) -> searchQuery.setParameter(k, v));
		
		List<T> results = searchQuery.getResultList();

		return results;
	}

	protected abstract String getQueryString();

	protected abstract void fillParametersMap(Map<String, Object> map, T t);
}
