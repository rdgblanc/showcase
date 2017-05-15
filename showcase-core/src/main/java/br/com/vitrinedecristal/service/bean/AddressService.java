package br.com.vitrinedecristal.service.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vitrinedecristal.dao.IAddressDAO;
import br.com.vitrinedecristal.enums.AddressStatusEnum;
import br.com.vitrinedecristal.enums.RoleEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.exception.InvalidPermissionException;
import br.com.vitrinedecristal.model.Address;
import br.com.vitrinedecristal.security.util.AuthenticationUtils;
import br.com.vitrinedecristal.service.IAddressService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.AddressVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link Address}
 */
public class AddressService extends BaseService<Long, Address, IAddressDAO> implements IAddressService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AddressService.class);

	public AddressService(IAddressDAO addressDAO) {
		super(addressDAO);
	}

	@Override
	public AddressVO getAddress(Long id) throws BusinessException {
		logger.info("Obtendo endereço pelo id: " + id);

		Address address = super.findByPrimaryKey(id);
		if (address == null) {
			throw new EntityNotFoundException("Não foi encontrado endereço com o id informado.");
		}

		return ParserUtil.parse(address, AddressVO.class);
	}

	@Override
	@Transactional
	public AddressVO createAddress(AddressVO addressVO) throws BusinessException {
		if (addressVO == null) {
			throw new IllegalArgumentException("A entidade endereço não pode ser nula.");
		}

		if (StringUtils.isBlank(addressVO.getCep())) {
			throw new BusinessException("O campo CEP é obrigatório");
		}

		Address address = ParserUtil.parse(addressVO, Address.class);
		address.setStatus(AddressStatusEnum.ACTIVE);
		address.setDtAtualizacao(new Date());

		logger.info("Criando endereço: " + address);
		Address storedAddress = super.save(address);
		logger.info("Endereço criado com sucesso!");

		return ParserUtil.parse(storedAddress, AddressVO.class);
	}

	@Override
	@Transactional
	public AddressVO updateAddress(AddressVO addressVO) throws BusinessException {
		logger.info("Atualizando endereço: " + addressVO);

		if (addressVO == null) {
			throw new IllegalArgumentException("A entidade endereço não pode ser nula.");
		}

		if (addressVO.getUsuario() == null || addressVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário do endereço não pode ser nulo ou conter id nulo.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !addressVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		Address storedAddress = getDAO().findByPrimaryKey(addressVO.getId());
		if (storedAddress == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhum endereço com o id informado.");
		}

		if (StringUtils.isNotBlank(addressVO.getDescricao())) {
			storedAddress.setDescricao(addressVO.getDescricao());
		}

		if (StringUtils.isNotBlank(addressVO.getCep())) {
			storedAddress.setCep(addressVO.getCep());
		}

		if (StringUtils.isNotBlank(addressVO.getEndereco())) {
			storedAddress.setEndereco(addressVO.getEndereco());
		}

		if (addressVO.getNumero() != null) {
			storedAddress.setNumero(addressVO.getNumero());
		}

		if (addressVO.getComplemento() != null) {
			storedAddress.setComplemento(addressVO.getComplemento());
		}

		if (StringUtils.isNotBlank(addressVO.getBairro())) {
			storedAddress.setBairro(addressVO.getBairro());
		}

		if (StringUtils.isNotBlank(addressVO.getCidade())) {
			storedAddress.setCidade(addressVO.getCidade());
		}

		if (StringUtils.isNotBlank(addressVO.getEstado())) {
			storedAddress.setEstado(addressVO.getEstado());
		}

		storedAddress.setDtAtualizacao(new Date());
		storedAddress = super.save(storedAddress);
		logger.info("Endereço atualizado com sucesso!");

		return ParserUtil.parse(storedAddress, AddressVO.class);
	}

	@Override
	@Transactional
	public void updateStatus(Long id, AddressStatusEnum status) throws BusinessException {
		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
			throw new InvalidPermissionException();
		}

		if (id == null) {
			throw new IllegalArgumentException("O id do endereço não pode ser nulo.");
		}

		if (status == null) {
			throw new IllegalArgumentException("O novo status do endereço não pode ser nulo.");
		}

		logger.info("Alterando status do endereço [" + id + "] para " + status);
		Address storedAddress = getDAO().findByPrimaryKey(id);
		if (storedAddress == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhum endereço para o id informado.");
		}

		storedAddress.setStatus(status);
		storedAddress.setDtAtualizacao(new Date());
		super.save(storedAddress);

		logger.info("Status do endereço atualizado com sucesso!");
	}

	@Override
	public List<AddressVO> listAddressByUser(Long userId) throws BusinessException {
		logger.info("Listando os endereços do usuário: " + userId);
		List<Address> listAddress = getDAO().findByUser(userId, Arrays.asList(AddressStatusEnum.ACTIVE));
		logger.info("Endereços listados com sucesso!");

		return ParserUtil.parse(listAddress, AddressVO.class);
	}
}
