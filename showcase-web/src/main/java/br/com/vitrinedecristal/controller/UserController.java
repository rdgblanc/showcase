package br.com.vitrinedecristal.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import br.com.vitrinedecristal.dto.LoginDTO;
import br.com.vitrinedecristal.dto.UserDTO;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.service.IUserService;
import br.com.vitrinedecristal.swagger.ApiException;
import br.com.vitrinedecristal.swagger.ApiExceptionResponse;
import br.com.vitrinedecristal.swagger.AuthorizationException;
import br.com.vitrinedecristal.swagger.BadRequestException;
import br.com.vitrinedecristal.swagger.EmptyRequestBodyException;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

@Path("/user")
@Api(value = "/user")
public class UserController extends SpringBeanAutowiringSupport {

	@Autowired
	private IUserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List users", notes = "List every user login information.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<UserDTO> list() throws ApiException, BusinessException {
		return this.userService.listUsers();
	}

	@GET
	@Path("/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get user information", notes = "Get user information based on the uid (@self for logged user).")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public UserDTO get(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		if (id == null) {
			throw new EmptyRequestBodyException();
		}

		return ParserUtil.getVO(this.userService.findByPrimaryKey(id), UserDTO.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create an user", notes = "Create a new user.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public UserDTO create(UserVO userVO) throws ApiException, BusinessException {
		if (userVO == null) {
			throw new EmptyRequestBodyException();
		}

		userVO.setId(null);
		return this.userService.createUser(userVO);
	}

	@PUT
	@Path("/{uid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update an user", notes = "Update the informed user.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public UserDTO update(@PathParam("id") Long id, UserVO userVO) throws ApiException, BusinessException, NotFoundException {
		if (userVO == null) {
			throw new EmptyRequestBodyException();
		}

		userVO.setId(id);
		return this.userService.updateUser(userVO);
	}

	@DELETE
	@Path("/{uid}")
	@ApiOperation(value = "Remove the informed user", notes = "Remove the informed user.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public void remove(@PathParam("id") Long id) throws ApiException {
		User user = new User();
		user.setId(id);
		this.userService.remove(user);
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Validate login credentials", notes = "Validate login information and stores the credentials in the session.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public UserDTO login(@ApiParam LoginDTO loginDetails) throws ApiException {
		if (loginDetails == null) {
			throw new EmptyRequestBodyException();
		}

		return this.userService.login(loginDetails);
	}

}