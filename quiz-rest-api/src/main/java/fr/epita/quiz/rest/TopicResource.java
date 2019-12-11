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

import fr.epita.quiz.datamodel.Topic;
import fr.epita.quiz.services.TopicDAO;

@Path("/topics/")
public class TopicResource {

	@Inject
	TopicDAO topicdao;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTopic(@RequestBody Topic topic) throws URISyntaxException {
		// create a question
		topicdao.create(topic);
		return Response.created(new URI("topics/" + String.valueOf(topic.getId()))).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteTopic(@PathParam("id") int id) throws URISyntaxException {
		Topic topic = topicdao.getById(id, Topic.class);
		topicdao.delete(topic);
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopicById(@PathParam("id") int id) {
		Topic topic = topicdao.getById(id, Topic.class);
		return Response.ok(topic).build();
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTopic(Topic topic) {
		topicdao.update(topic);

		return Response.ok().build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchTopics(@DefaultValue("") @QueryParam("topic_name") String topic_name) {
		List<Topic> searchList = topicdao.search(new Topic(topic_name));
		return Response.ok(searchList).build();
	}

}
