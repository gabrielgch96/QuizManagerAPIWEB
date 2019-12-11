package fr.epita.quiz.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Option;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Submission;
import fr.epita.quiz.datamodel.User;

@Repository
public class SubmissionService {

	private static final Logger LOGGER = LogManager.getLogger();
	
	@Inject
	Double MAX_SCORE;

	@Inject
	SubmissionDAO submissiondao;

	@Inject
	OptionDAO optiondao;
	
	@Inject
	ExamDAO examdao;

	@Inject
	AnswerDAO answerdao;

	@Inject
	UserDAO userdao;

	public Submission createSubmission(User user, Exam exam, Answer[] answersSubmitted) {

		user = userdao.getById(user.getId(), User.class);
		LocalDate today = LocalDate.now();
		Submission submission = new Submission();
		submission.setExam(exam);
		submission.setDate(today);
		submission.setUser(user);
		submissiondao.create(submission);

		for (Answer answer : answersSubmitted) {
			answer.setSubmission(submission);
			Set<Option> temp = answer.getSelectedOptions();
			answer.setSelectedOptions(new HashSet<Option>());
			answer.setUser(user);
			answerdao.create(answer);
			addOptionsAnswer(answer, temp);
			LOGGER.info("Created answer (id=" + answer.getId() + ") for submission: " + submission.getId());
			submission.addAnswer(answer);
		}
		double score = scoreSubmission(exam, answersSubmitted);
		submission.setScore(score);
		submissiondao.update(submission);

		LOGGER.info("Created submission (id=" + submission.getId() + ") by user:" + user.getId());

		return submission;
	}

	public void addOptionsAnswer(Answer answer, Set<Option> options) {

		for (Option option : options) {
			answer.addOption(option);
		}
		answerdao.update(answer);
	}
	
	private boolean checkIfSelectedOption(Option optionToCheck, Set<Option> selectedOptions) {
		
		for(Option opt: selectedOptions) {
			if(opt.getId() == optionToCheck.getId())
				return true;
		}
		return false;
	}

	private double scoreSubmission(Exam exam, Answer[] answers) {
		
		double exam_score = 0;
		double possiblePointsPerQuestion;
		double optionValue=0;
		
		Set<Option> selectedOptions = new HashSet<Option>();
		for(Answer answer: answers)
			selectedOptions.addAll(answer.getSelectedOptions());
		
		Exam to_score = examdao.getById(exam.getId(), Exam.class);
		Set<Question> questionsExam = to_score.getQuestions();
		possiblePointsPerQuestion = MAX_SCORE/ questionsExam.size();
		
		Set<Option> optionsQuestion;
		for(Question question: questionsExam) {
			optionsQuestion = question.getOptions();
			int correct_count=0;
			int user_chose = 0;
			for(Option option: optionsQuestion) {
				if(option.getIs_right()) {
					correct_count++;
					if(checkIfSelectedOption(option, selectedOptions))
						user_chose++;
				}
			}
			optionValue = possiblePointsPerQuestion / correct_count;
			exam_score += optionValue* user_chose;
		}
		System.out.println("Exam score:"+exam_score);
		return exam_score;

	}

}
