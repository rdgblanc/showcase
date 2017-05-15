package br.com.vitrinedecristal.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import br.com.vitrinedecristal.dao.IAddressDAO;
import br.com.vitrinedecristal.enums.AddressStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Address;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.AddressVO;

/**
 * Define os contratos de lógica de negócio da entidade {@link Address}.
 */
public interface IAddressService extends IBaseService<Long, Address, IAddressDAO> {

	/**
	 * Obtém informações do endereço
	 * 
	 * @param id id do endereço
	 * @return informações do endereço
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	AddressVO getAddress(Long id) throws BusinessException;

	/**
	 * Cria o endereço
	 * 
	 * @param addressVO informações para o cadastro do endereço
	 * @return endereço criado
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	AddressVO createAddress(AddressVO addressVO) throws BusinessException;

	/**
	 * Atualiza as informações do endereço
	 * 
	 * @param addressVO informações para a alteração do endereço
	 * @return endereço alterado
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	AddressVO updateAddress(AddressVO addressVO) throws BusinessException;

	/**
	 * Altera o status do endereço
	 * 
	 * @param id id do endereço a ser alterado o status
	 * @param status novo status do endereço
	 * @throws BusinessException
	 */
	@Secured(ROLE_ADMIN)
	void updateStatus(Long id, AddressStatusEnum status) throws BusinessException;

	/**
	 * Lista os endereços do usuários
	 * 
	 * @param userId id do usuário
	 * @return lista de endereços do usuário
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	List<AddressVO> listAddressByUser(Long userId) throws BusinessException;
}
