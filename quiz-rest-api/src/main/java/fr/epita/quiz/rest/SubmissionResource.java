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

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Submission;
import fr.epita.quiz.datamodel.User;
import fr.epita.quiz.services.SubmissionDAO;
import fr.epita.quiz.services.SubmissionService;
import fr.epita.quiz.wrapper.SubmissionCreationWrapper;

@Path("/submissions/")
public class SubmissionResource {
	
	@Inject
	SubmissionDAO submissiondao;
	
	@Inject
	SubmissionService submissionService;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSubmission(@RequestBody SubmissionCreationWrapper wrapper) throws URISyntaxException {
		Exam exam = wrapper.getExam();
		User user = wrapper.getUser();
		List<Answer> answers = wrapper.getAnswers();
		Submission submission = submissionService.createSubmission(user, exam, answers.toArray(new Answer[answers.size()]));
		return Response.created(new URI("submission/" +String.valueOf(submission.getId()))).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteSubmission(@PathParam("id") int id) throws URISyntaxException {
		Submission submission= submissiondao.getById(id, Submission.class);
		submissiondao.delete(submission);
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionById(@PathParam("id") int id) {
		Submission submission= submissiondao.getById(id, Submission.class);
		return Response.ok(submission).build();
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateSubmission(Submission submission) {
		//submission.update(submission);
		//not available
		return Response.ok().build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchTopics(@DefaultValue("0") @QueryParam("user_id") int id) {
		User user = new User();
		user.setId(id);
		Submission submission= new Submission();
		submission.setUser(user);
		List<Submission> searchList = submissiondao.search(submission);
		return Response.ok(searchList).build();
	}

}
