package br.com.vitrinedecristal.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import br.com.vitrinedecristal.enums.NegotiationStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.service.INegotiationService;
import br.com.vitrinedecristal.swagger.ApiException;
import br.com.vitrinedecristal.swagger.ApiExceptionResponse;
import br.com.vitrinedecristal.swagger.AuthorizationException;
import br.com.vitrinedecristal.swagger.BadRequestException;
import br.com.vitrinedecristal.swagger.EmptyRequestBodyException;
import br.com.vitrinedecristal.vo.NegotiationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/negotiation")
@Api(value = "/negotiation")
public class NegotiationController extends SpringBeanAutowiringSupport {

	@Autowired
	private INegotiationService negotiationService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Informações da negociação", notes = "Obtém informações da negociação através do ID.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public NegotiationVO get(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		if (id == null) {
			throw new EmptyRequestBodyException();
		}

		return this.negotiationService.getNegotiation(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cria uma negociação", notes = "Cria uma nova negociação para o usuário.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public NegotiationVO create(NegotiationVO negotiationVO) throws ApiException, BusinessException {
		if (negotiationVO == null) {
			throw new EmptyRequestBodyException();
		}

		negotiationVO.setId(null);
		return this.negotiationService.createNegotiation(negotiationVO);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualiza uma negociação", notes = "Atualiza as informações da negociação.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public NegotiationVO update(@PathParam("id") Long id, NegotiationVO negotiationVO) throws ApiException, BusinessException {
		if (negotiationVO == null) {
			throw new EmptyRequestBodyException();
		}

		negotiationVO.setId(id);
		return this.negotiationService.updateNegotiation(negotiationVO);
	}

	@PUT
	@Path("/{id}/{status}")
	@ApiOperation(value = "Atualiza o status", notes = "Altera o status da negociação.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public void remove(@ApiParam @PathParam("id") Long id, @ApiParam @PathParam("status") NegotiationStatusEnum status) throws ApiException, BusinessException {
		this.negotiationService.updateStatus(id, status);
	}

	@GET
	@Path("/user/order/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista as negociações de comprar do usuário", notes = "Lista as negociações de compra cadastradas do usuário.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<NegotiationVO> listByUser(@ApiParam @PathParam("userId") Long userId) throws ApiException, BusinessException {
		if (userId == null) {
			throw new EmptyRequestBodyException();
		}

		return this.negotiationService.listNegotiationByUser(userId);
	}

	@GET
	@Path("/user/seller/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista as negociações de venda do usuário", notes = "Lista as negociações de venda cadastradas do usuário.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<NegotiationVO> listByUserSeller(@ApiParam @PathParam("userId") Long userId) throws ApiException, BusinessException {
		if (userId == null) {
			throw new EmptyRequestBodyException();
		}

		return this.negotiationService.listNegotiationByUserSeller(userId);
	}

	@GET
	@Path("/product/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista as negociações do produto", notes = "Lista as negociações cadastradas do produto.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<NegotiationVO> listByProduct(@ApiParam @PathParam("productId") Long productId) throws ApiException, BusinessException {
		if (productId == null) {
			throw new EmptyRequestBodyException();
		}

		return this.negotiationService.listNegotiationByProduct(productId);
	}

}