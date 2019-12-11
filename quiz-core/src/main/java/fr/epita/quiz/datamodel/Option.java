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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OPTION")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="OPTION_ID")
	private Integer id;
	
	@Column(name="TEXT")
	private String text;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Q_ID", nullable = false)
	private Question question;
	
	@Column(name="IS_RIGHT")
	Boolean is_right;
	/*
	@ManyToMany(mappedBy = "selectedOptions", targetEntity = Answer.class, fetch = FetchType.EAGER)
	private Set<Answer> answers = new HashSet<Answer>();
	*/
	public Option() {}
	
	public Option(String text, Boolean is_right) {
		super();
		this.text = text;
		this.is_right = is_right;
	}
	/*
	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}*/

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	/*
	public Question getQuestion() {
		return question;
	}*/
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Boolean getIs_right() {
		return is_right;
	}
	public void setIs_right(Boolean is_right) {
		this.is_right = is_right;
	}

}
