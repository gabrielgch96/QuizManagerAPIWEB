package fr.epita.quiz.wrapper;

import java.util.List;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.User;

public class SubmissionCreationWrapper {
	
	User user;
	List<Answer> answers;
	Exam exam;
	
	public SubmissionCreationWrapper() {
		super();
	}
	
	public SubmissionCreationWrapper(List<Answer> answers, Exam exam) {
		super();
		this.answers = answers;
		this.exam = exam;
	}
	
	public SubmissionCreationWrapper(User user, List<Answer> answers, Exam exam) {
		super();
		this.user = user;
		this.answers = answers;
		this.exam = exam;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
	

}
