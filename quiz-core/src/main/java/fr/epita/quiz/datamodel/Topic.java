package fr.epita.quiz.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TOPIC")
public class Topic {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="T_ID")
	private Integer id;
	
	@Column(name="T_NAME", unique = true)
	private String name;
	
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Question> questions;
	*/
	public Topic() {}

	public Topic(String name) {
		super();
		this.name = name;
	}
/*
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
