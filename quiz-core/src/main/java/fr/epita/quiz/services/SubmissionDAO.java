package fr.epita.quiz.services;

import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Submission;

@Repository
public class SubmissionDAO extends DAO<Submission>{

	@Override
	protected String getQueryString() {
		return "from Submission sub where sub.user = :pUser";
	}

	@Override
	protected void fillParametersMap(Map<String, Object> map, Submission submission) {
		System.out.println("user searching for:"+ submission.getUser().getId());
		map.put("pUser", submission.getUser());
	}

}
