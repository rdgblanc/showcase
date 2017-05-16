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

import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.service.ICategoryService;
import br.com.vitrinedecristal.swagger.ApiException;
import br.com.vitrinedecristal.swagger.ApiExceptionResponse;
import br.com.vitrinedecristal.swagger.AuthorizationException;
import br.com.vitrinedecristal.swagger.BadRequestException;
import br.com.vitrinedecristal.swagger.EmptyRequestBodyException;
import br.com.vitrinedecristal.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/category")
@Api(value = "/category")
public class CategoryController extends SpringBeanAutowiringSupport {

	@Autowired
	private ICategoryService categoryService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista as categorias", notes = "Lista as categorias cadastradas.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<CategoryVO> list() throws ApiException, BusinessException {
		return this.categoryService.listCategories();
	}

	@GET
	@Path("/subCategories/{categoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista as sub-categorias", notes = "Lista as sub-categorias da categoria informada.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<CategoryVO> listSubCategories(@ApiParam @PathParam("categoryId") Long categoryId) throws ApiException, BusinessException {
		if (categoryId == null) {
			throw new EmptyRequestBodyException();
		}

		return this.categoryService.listSubCategories(categoryId);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Informações da categoria", notes = "Obtém informações da categoria através do ID.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public CategoryVO get(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		if (id == null) {
			throw new EmptyRequestBodyException();
		}

		return this.categoryService.getCategory(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cria uma categoria", notes = "Cria uma nova categoria.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public CategoryVO create(CategoryVO categoryVO) throws ApiException, BusinessException {
		if (categoryVO == null) {
			throw new EmptyRequestBodyException();
		}

		categoryVO.setId(null);
		return this.categoryService.createCategory(categoryVO);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualiza uma categoria", notes = "Atualiza as informações da categoria.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public CategoryVO update(@PathParam("id") Long id, CategoryVO categoryVO) throws ApiException, BusinessException {
		if (categoryVO == null) {
			throw new EmptyRequestBodyException();
		}

		categoryVO.setId(id);
		return this.categoryService.updateCategory(categoryVO);
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Remove uma categoria", notes = "Remove a categoria informada através do id.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public void remove(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		this.categoryService.removeCategory(id);
	}

}