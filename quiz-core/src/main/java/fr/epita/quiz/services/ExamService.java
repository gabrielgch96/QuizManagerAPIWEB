package fr.epita.quiz.services;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Question;

@Repository
public class ExamService {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Inject
	ExamDAO examdao;
	
	@Inject
	QuestionDAO questiondao;
	
	@Inject
	TopicDAO topicdao;
	
	@Inject
	OptionDAO optiondao;	
	
	public void addQuestionsExam(Exam exam, Question...questions) {
		for(Question question : questions) {
			exam.addQuestion(question);
			//questiondao.update(question);
		}
		examdao.update(exam);
		LOGGER.info("Updated exam id="+exam.getId());
	}
	
	public void deleteExam(Exam exam) {
		//Exam old = examdao.getById(exam.getId(), Exam.class);
		for(Iterator<Question> iter=exam.getQuestions().iterator(); iter.hasNext();){
			//iter.next().getExams().remove(exam);
			iter.next();
			iter.remove();
		}
		examdao.update(exam);
		examdao.delete(exam);
		LOGGER.info("Deleted exam with id: " + exam.getId());
	}
	
	public void updateExamQuestions(Exam exam, Question... questions) {
		Exam oldExam = examdao.getById(exam.getId(), Exam.class);
		Set<Question> before = oldExam.getQuestions();
		List<Question> questionList = Arrays.asList(questions);
		for (Iterator<Question> iter = before.iterator(); iter.hasNext();) {
			Question question = iter.next();
			if (!questionList.contains(question)) {
				//question.getExams().remove(exam);
				iter.remove();
			}
		}
		for (Question question : questions) {
			if (!before.contains(question))
				exam.addQuestion(question);
		}
		examdao.update(exam);
		LOGGER.info("Updated exam with id="+exam.getId());
	}

}
