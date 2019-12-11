package fr.epita.quiz.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.services.ExamService;
import fr.epita.quiz.wrapper.ExamQuestionWrapper;

@Path("/exams/")
public class ExamResource {
	
	@Inject
	ExamDAO examdao;
	
	@Inject
	ExamService examservice;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createExam(@RequestBody ExamQuestionWrapper wrapper) throws URISyntaxException {
		
		Exam exam = wrapper.getExam();
		List<Question> questions = wrapper.getExam_questions();
		// create a question
		examdao.create(exam);
		examservice.addQuestionsExam(exam, questions.toArray(new Question[questions.size()]));
		return Response.created(new URI("topics/" + String.valueOf(exam.getId()))).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteTopic(@PathParam("id") int id) throws URISyntaxException {
		Exam exam = examdao.getById(id, Exam.class);
		//examdao.delete(exam);
		examservice.deleteExam(exam);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamById(@DefaultValue("0") @PathParam("id") int id) {
		Exam exam = null;
		if(id != 0) {
			exam = examdao.getById(id, Exam.class);
		}
		//Exam exam = examservice.getExamWithQuestions(id);
		return Response.ok(exam).build();
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateExam(ExamQuestionWrapper wrapper) {
		Exam exam = wrapper.getExam();
		List<Question> questions = wrapper.getExam_questions();
		examservice.updateExamQuestions(exam, questions.toArray(new Question[questions.size()]));
		return Response.ok().build();
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchExam(@DefaultValue("") @QueryParam("title") String title) {
		List<Exam> searchList = examdao.search(new Exam(title));
		return Response.ok(searchList).build();
	}

}
