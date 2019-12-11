package fr.epita.quiz.datamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="EXAM")
public class Exam {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EX_ID")
	private Integer id;
	
	@Column(name="EX_TITLE")
	private String title;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="EXAM_QUESTIONS",
		joinColumns = {@JoinColumn(name="EX_ID", referencedColumnName = "EX_ID")},
		inverseJoinColumns = {@JoinColumn(name="Q_ID", referencedColumnName = "Q_ID")})
	private Set<Question> examQuestions = new HashSet<Question>();
	
	public Exam() {}

	public Exam(String title) {
		super();
		this.title = title;
	}
	
	public void addQuestion(Question question) {
		this.examQuestions.add(question);
		//question.getExams().add(this);
	}
	
	public void removeQuestion(Question question) {
		this.examQuestions.remove(question);
		//question.getExams().remove(this);
	}

	public Set<Question> getQuestions() {
		return examQuestions;
	}

	public void setQuestions(Set<Question> questions) {
		this.examQuestions = questions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
