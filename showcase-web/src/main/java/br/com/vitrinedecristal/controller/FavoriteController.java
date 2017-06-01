package br.com.vitrinedecristal.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.service.IFavoriteService;
import br.com.vitrinedecristal.swagger.ApiException;
import br.com.vitrinedecristal.swagger.ApiExceptionResponse;
import br.com.vitrinedecristal.swagger.AuthorizationException;
import br.com.vitrinedecristal.swagger.BadRequestException;
import br.com.vitrinedecristal.swagger.EmptyRequestBodyException;
import br.com.vitrinedecristal.vo.FavoriteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/favorite")
@Api(value = "/favorite")
public class FavoriteController extends SpringBeanAutowiringSupport {

	@Autowired
	private IFavoriteService favoriteService;

	// @GET
	// @Produces(MediaType.APPLICATION_JSON)
	// @ApiOperation(value = "Lista os favoritos", notes = "Lista os favoritos cadastrados.")
	// @ApiResponses(value = {
	// @ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
	// @ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	// })
	// public List<FavoriteVO> list() throws ApiException, BusinessException {
	// return this.favoriteService.listFavorites();
	// }

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Informações do favorito", notes = "Obtém informações do favorito através do ID.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public FavoriteVO get(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		if (id == null) {
			throw new EmptyRequestBodyException();
		}

		return this.favoriteService.getFavorite(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cria um favorito", notes = "Cria um novo favorito.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public FavoriteVO create(FavoriteVO favoriteVO) throws ApiException, BusinessException {
		if (favoriteVO == null) {
			throw new EmptyRequestBodyException();
		}

		favoriteVO.setId(null);
		return this.favoriteService.createFavorite(favoriteVO);
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Remove um favorito", notes = "Remove o favorito informado através do id.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public void remove(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		this.favoriteService.removeFavorite(id);
	}

	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista os favoritos do usuário", notes = "Lista os favoritos cadastrados do usuário.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<FavoriteVO> listByUser(@ApiParam @PathParam("userId") Long userId) throws ApiException, BusinessException {
		if (userId == null) {
			throw new EmptyRequestBodyException();
		}

		return this.favoriteService.listFavoriteByUser(userId);
	}

}