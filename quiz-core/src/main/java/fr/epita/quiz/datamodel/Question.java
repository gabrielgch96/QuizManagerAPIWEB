package fr.epita.quiz.datamodel;

import java.util.Set;

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
@Table(name="QUESTION")
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Q_ID")
	private Integer id;
	
	@Column(name="Q_CONTENT")
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="T_ID", referencedColumnName = "T_ID")
	private Topic topic;
	
	
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private Set<Option> options;	
	/*
	@ManyToMany(mappedBy = "examQuestions", fetch = FetchType.EAGER)
	private Set<Exam> exams;// = new HashSet<Exam>();
	*/
	public Question() {}
	
	public Question(String content, Topic topic) {
		super();
		this.content = content;
		this.topic = topic;
	}
	
	
	/*
	public Set<Exam> getExams() {
		return this.exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}*/
	
	public Integer getId() {
		return id;
	}
	
	public Set<Option> getOptions() {
		return options;
	}

	public void setOptions(Set<Option> options) {
		this.options = options;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
