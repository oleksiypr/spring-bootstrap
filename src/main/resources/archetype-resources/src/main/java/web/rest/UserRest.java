#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ${package}.domain.User;
import ${package}.service.UserService;


@Component
@Path("/user")
public class UserRest {
	@Autowired
	private UserService userService;
 
	@POST 
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(User user) { 
		userService.save(user);
		return Response.status(200).entity("User upadted").build();
	} 
}
