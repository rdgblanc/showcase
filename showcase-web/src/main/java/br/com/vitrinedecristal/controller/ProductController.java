package br.com.vitrinedecristal.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sun.jersey.multipart.FormDataParam;

import br.com.vitrinedecristal.enums.ProductStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
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

	/** The path to the folder where we want to store the uploaded files */
	private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";

	private final String UPLOADED_FILE_PATH = "c:\\";

	@POST
	@Path("upload")
	@Consumes({
			"application/x-www-form-urlencoded",
			"multipart/form-data"
	})
	public void addBlob(@FormDataParam("file") InputStream uploadedInputStream) throws IOException {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			// entityToMerge.setTestBlob(out.toByteArray());
			// super.edit(entityToMerge);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Faz o upload da imagem do Show, retornando o path [relativo] do arquivo no host FTP
	 * 
	 * @param inputStream {@link InputStream} do arquivo da imagem
	 * @return path relativo do arquivo no host FTP
	 * @throws ImageException na ocorrência de erros ao realizar o upload ou o processamento da imagem
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Upload das imagens", notes = "Faz o upload das imagens do produto.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = BadRequestException.MESSAGE, response = ApiExceptionResponse.class),
			@ApiResponse(code = 403, message = AuthorizationException.MESSAGE, response = ApiExceptionResponse.class)
	})
	@Path("/image")
	/*
	 * public Response uploadImage(@FormDataParam("uploadedFile") InputStream inputStream) throws ImageException { String imagePath = getESService().uploadImage(inputStream); return Response.ok(imagePath).build(); }
	 */
	public Response uploadFile(MultipartFormDataInput input) {
		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		String inputName = "input-700";
		List<InputPart> inputParts = uploadForm.get(inputName);

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				// constructs upload file path
				fileName = UPLOADED_FILE_PATH + fileName;

				writeFile(bytes, fileName);

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();
	}

	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data; name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	// save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}

}