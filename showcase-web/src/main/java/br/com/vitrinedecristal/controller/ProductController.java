package br.com.vitrinedecristal.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import br.com.vitrinedecristal.dto.ImageDTO;
import br.com.vitrinedecristal.enums.ProductStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.service.IImageService;
import br.com.vitrinedecristal.service.IProductService;
import br.com.vitrinedecristal.swagger.ApiException;
import br.com.vitrinedecristal.swagger.ApiExceptionResponse;
import br.com.vitrinedecristal.swagger.AuthorizationException;
import br.com.vitrinedecristal.swagger.BadRequestException;
import br.com.vitrinedecristal.swagger.EmptyRequestBodyException;
import br.com.vitrinedecristal.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/product")
@Api(value = "/product")
public class ProductController extends SpringBeanAutowiringSupport {

	@Autowired
	private IProductService productService;

	@Autowired
	private IImageService imageService;

	@Context
	private ServletContext context;

	// private static final String UPLOAD_FOLDER = "C:/blanc/jboss-as-7.1.1.Final/standalone/deployments/showcase-web.war/assets/upload";
	private static final String UPLOAD_FOLDER = "C:/blanc/workspace/showcase/showcase-web/src/main/webapp/assets/upload/";

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Informações do produto", notes = "Obtém informações do produto através do ID.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public ProductVO get(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		if (id == null) {
			throw new EmptyRequestBodyException();
		}

		return this.productService.getProduct(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cria um produto", notes = "Cria um novo produto para o usuário.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public ProductVO create(ProductVO productVO) throws ApiException, BusinessException {
		if (productVO == null) {
			throw new EmptyRequestBodyException();
		}

		productVO.setId(null);
		return this.productService.createProduct(productVO);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualiza um produto", notes = "Atualiza as informações do produto.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public ProductVO update(@PathParam("id") Long id, ProductVO productVO) throws ApiException, BusinessException {
		if (productVO == null) {
			throw new EmptyRequestBodyException();
		}

		productVO.setId(id);
		return this.productService.updateProduct(productVO);
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Remove um produto", notes = "Altera o status do produto informado para INATIVO.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public void remove(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		this.productService.updateStatus(id, ProductStatusEnum.INACTIVE);
	}

	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista os produtos do usuário", notes = "Lista os produtos cadastrados do usuário.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<ProductVO> list(@ApiParam @PathParam("userId") Long userId) throws ApiException, BusinessException {
		if (userId == null) {
			throw new EmptyRequestBodyException();
		}

		return this.productService.listProductByUser(userId);
	}

	@POST
	@Path("{id}/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "Upload de imagem", notes = "Faz o upload das imagens do produto.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	// public void uploadImage(@ApiParam @PathParam("id") Long id, @FormDataParam("file") InputStream uploadedStream) throws BusinessException, IOException {
	public void uploadImage(@ApiParam @PathParam("id") Long id, MultipartFormDataInput multipart) throws BusinessException, IOException {
		String inputNameFileIndex = "file_id";
		Integer fileIndex = multipart.getFormDataPart(inputNameFileIndex, Integer.class, null);

		String inputNameFileContent = "file";
		Map<String, List<InputPart>> uploadForm = multipart.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get(inputNameFileContent);

		this.createDirectory(id);

		for (InputPart inputPart : inputParts) {
			// Retrieve headers, read the Content-Disposition header to obtain the original name of the file
			@SuppressWarnings("unused")
			MultivaluedMap<String, String> header = inputPart.getHeaders();

			String filename = id + File.separator + new Date().getTime() + ".jpg";

			// Handle the body of that part with an InputStream
			InputStream uploadedStream = inputPart.getBody(InputStream.class, null);
			this.writeFile(uploadedStream, filename);

			this.imageService.createImage(id, filename);
		}
	}

	@DELETE
	@Path("/{id}/image")
	@ApiOperation(value = "Remove a imagem", notes = "Remove a imagem do produto.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 404, message = EntityNotFoundException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public void removeImage(@ApiParam @PathParam("id") Long id) throws BusinessException {
		this.imageService.removeImage(id);
	}

	@GET
	@Path("/{id}/image")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lista as imagens do produto", notes = "Lista as imagens cadastradas do produto.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	public List<ImageDTO> listImages(@ApiParam @PathParam("id") Long id) throws ApiException, BusinessException {
		if (id == null) {
			throw new EmptyRequestBodyException();
		}

		return this.imageService.listImagesByProduct(id);
	}

	private void createDirectory(Long productId) {
		File directory = new File(UPLOAD_FOLDER + productId);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	private void writeFile(InputStream uploadedStream, String filename) throws IOException, BusinessException {
		File file = new File(UPLOAD_FOLDER + filename);
		FileOutputStream fop = new FileOutputStream(file);

		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = uploadedStream.read(bytes)) != -1) {
			fop.write(bytes, 0, read);
		}

		fop.flush();
		fop.close();
	}

}