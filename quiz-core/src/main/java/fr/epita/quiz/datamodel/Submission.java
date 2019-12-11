package fr.epita.quiz.datamodel;

import java.sql.Date;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SUBMISSION")
public class Submission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SUB_ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="U_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="EX_ID")
	private Exam exam;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "submission")
	//@JoinColumn(name = "ANS_ID")
	private Set<Answer> answers = new HashSet<Answer>();;
	
	@Column(name = "SCORE")
	private double score;
	
	@Column(name= "DATE")
	private LocalDate date;

	public Submission() {}
	
	public Submission(User user, Exam exam) {
		super();
		this.user = user;
		this.exam = exam;
		this.score = 0;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}
	
	public void removeAnswer(Answer answer) {
		this.answers.remove(answer);
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
	

}
