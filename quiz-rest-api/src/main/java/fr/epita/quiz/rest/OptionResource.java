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

import fr.epita.quiz.datamodel.Option;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.OptionDAO;

@Path("/options/")
public class OptionResource {
	
	@Inject
	OptionDAO optiondao;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOption(@RequestBody Option option) throws URISyntaxException {
		// create a question
		optiondao.create(option);
		return Response.created(new URI("options/" + String.valueOf(option.getId()))).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteOption(@PathParam("id") int id) throws URISyntaxException{
		Option option = optiondao.getById(id, Option.class);
		optiondao.delete(option);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOptionById(@PathParam("id") int id) {
		Option option= optiondao.getById(id, Option.class);
		return Response.ok(option).build();
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOption(@RequestBody Option option) {
		optiondao.update(option);

		return Response.ok().build();
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchQuestions(@DefaultValue("0") @QueryParam("qId") int qId) {
		Question criteria = new Question();
		criteria.setId(qId);
		Option option_criteria = new Option();
		option_criteria.setQuestion(criteria);
		optiondao.setQuestion(criteria);
		List<Option> searchList = optiondao.search(option_criteria);
		return Response.ok(searchList).build();
	}

}
