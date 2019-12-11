package fr.epita.quiz.wrapper;

import java.util.List;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Question;

public class ExamQuestionWrapper {
	
	Exam exam;
	List<Question> exam_questions;
	
	public ExamQuestionWrapper() { }
	
	public ExamQuestionWrapper(Exam exam, List<Question> exam_questions) {
		super();
		this.exam = exam;
		this.exam_questions = exam_questions;
	}

	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public List<Question> getExam_questions() {
		return exam_questions;
	}
	public void setExam_questions(List<Question> exam_questions) {
		this.exam_questions = exam_questions;
	}
	
	

}
