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

import fr.epita.quiz.datamodel.User;
import fr.epita.quiz.services.UserDAO;

@Path("/users/")
public class UserResource{
	
	@Inject
	UserDAO userdao;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(@RequestBody User user) throws URISyntaxException {
		// create a question
		userdao.create(user);
		return Response.created(new URI("users/" + String.valueOf(user.getId()))).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") int id) throws URISyntaxException{
		User user = userdao.getById(id, User.class);
		userdao.delete(user);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") int id) {
		User user = userdao.getById(id, User.class);
		return Response.ok(user).build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(User user) {
		List<User> searchList = userdao.validateUser(user);
		if(searchList.size() == 1)
		{
			searchList.get(0).setPassword("");
			return Response.ok(searchList.get(0)).build();
		}
		return Response.ok(null).build();
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {
		userdao.update(user);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUsers(
			@DefaultValue("") @QueryParam("ufirst") String ufirst, 
			@DefaultValue("") @QueryParam("ulast") String ulast,
			@DefaultValue("") @QueryParam("uemail") String email,
			@DefaultValue("") @QueryParam("upassword") String password,
			@DefaultValue("false") @QueryParam("uadmin") Boolean admin) {
		List<User> searchList = userdao.search(new User(ufirst, ulast, email,password, admin));
		return Response.ok(searchList).build();
	}

}
