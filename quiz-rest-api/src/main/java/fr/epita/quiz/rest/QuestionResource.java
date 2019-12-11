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
import fr.epita.quiz.datamodel.Option;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.QuestionService;
import fr.epita.quiz.wrapper.QuestionOptionWrapper;

@Path("/questions/")
public class QuestionResource {

	@Inject
	QuestionDAO questiondao;
	
	@Inject
	QuestionService questionservice;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createQuestion(@RequestBody QuestionOptionWrapper wrapper) throws URISyntaxException {
		// create a question
		//question.setTopic(null);
		//questionservice.createQuestion(question);
		Question question = wrapper.getQuestion();
		List<Option> options = wrapper.getOptions();
		questionservice.createQuestionWithChoices(question, options.toArray(new Option[options.size()]));
		return Response.created(new URI("questions/" + String.valueOf(question.getId()))).build();
		//return Response.ok().build();
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateQuestion(@RequestBody QuestionOptionWrapper wrapper) {
		Question question = wrapper.getQuestion();
		List<Option> options = wrapper.getOptions();
		questiondao.update(question);
		questionservice.updateQuestionOptions(question, options.toArray(new Option[options.size()]));
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuestionById(@PathParam("id") int id) {
		// create a question

		Question question = questiondao.getById(id, Question.class);

		return Response.ok(question).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteQuestion(@PathParam("id") int id) throws URISyntaxException{
		Question question = questiondao.getById(id, Question.class);
		//questiondao.delete(question);
		questionservice.deleteQuestion(question);
		return Response.ok().build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchQuestions(@DefaultValue("") @QueryParam("qContent") String questionContent) {
		// create a question
		List<Question> searchList = questiondao.search(new Question(questionContent, null));
		return Response.ok(searchList).build();
	}
	
	@GET
	@Path("/random")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRandom() {
		List<Question> searchList = questiondao.randomQuestions();
		return Response.ok(searchList).build();
	}
}
