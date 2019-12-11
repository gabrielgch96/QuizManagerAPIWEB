package fr.epita.quiz.datamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ANSWER")
public class Answer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ANS_ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="U_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="Q_ID")
	private Question question;
	/*
	@ManyToOne
	@JoinColumn(name="EX_ID")
	private Exam exam;
	*/
	@ManyToOne
	@JoinColumn(name = "SUB_ID")
	private Submission submission;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
		name="ANSWEROPTION",
		joinColumns = {@JoinColumn(name="ANS_ID", referencedColumnName = "ANS_ID")},
		inverseJoinColumns = {@JoinColumn(name="OPTION_ID", referencedColumnName = "OPTION_ID")})
	private Set<Option> selectedOptions = new HashSet<Option>();;
	
	public Answer() {}

	public Answer(User user, Question question) {//, Submission submission) {
		super();
		this.user = user;
		this.question = question;
		//this.exam = exam;
		//this.submission = submission;
	}

	public void addOption(Option option) {
		this.selectedOptions.add(option);
		//option.getAnswers().add(this);
	}
	
	public void removeOption(Option option) {
		this.selectedOptions.remove(option);
		//option.getAnswers().remove(this);
	}
	
	public Set<Option> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(Set<Option> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
/*
	public User getUser() {
		return user;
	}
*/
	public void setUser(User user) {
		this.user = user;
	}
/*
	public Question getQuestion() {
		return question;
	}
*/
	public void setQuestion(Question question) {
		this.question = question;
	}
/*
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}*/
/*
	public Submission getSubmission() {
		return submission;
	}
*/
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	

}
