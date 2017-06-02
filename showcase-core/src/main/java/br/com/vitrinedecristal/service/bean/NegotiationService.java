package br.com.vitrinedecristal.service.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vitrinedecristal.dao.INegotiationDAO;
import br.com.vitrinedecristal.enums.NegotiationStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.model.Negotiation;
import br.com.vitrinedecristal.service.INegotiationService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.NegotiationVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link Negotiation}
 */
public class NegotiationService extends BaseService<Long, Negotiation, INegotiationDAO> implements INegotiationService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(NegotiationService.class);

	public NegotiationService(INegotiationDAO negotiationDAO) {
		super(negotiationDAO);
	}

	@Override
	public NegotiationVO getNegotiation(Long id) throws BusinessException {
		logger.info("Obtendo negociação pelo id: " + id);

		Negotiation negotiation = super.findByPrimaryKey(id);
		if (negotiation == null) {
			throw new EntityNotFoundException("Não foi encontrado negociação com o id informado.");
		}

		return ParserUtil.parse(negotiation, NegotiationVO.class);
	}

	@Override
	@Transactional
	public NegotiationVO createNegotiation(NegotiationVO negotiationVO) throws BusinessException {
		logger.info("Criando negociação: " + negotiationVO);

		if (negotiationVO == null) {
			throw new IllegalArgumentException("A entidade negociação não pode ser nula.");
		}

		if (negotiationVO.getUsuario() == null || negotiationVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário da negociação não pode ser nulo ou conter id nulo.");
		}

		if (negotiationVO.getProduto() == null || negotiationVO.getProduto().getId() == null) {
			throw new IllegalArgumentException("O produto da negociação não pode ser nulo ou conter id nulo.");
		}

		// if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !negotiationVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
		// throw new InvalidPermissionException();
		// }

		if (negotiationVO.getTipoNegociacao() == null) {
			throw new BusinessException("O campo tipo da negociação é obrigatório");
		}

		Negotiation negotiation = ParserUtil.parse(negotiationVO, Negotiation.class);
		negotiation.setStatus(NegotiationStatusEnum.IN_PROGRESS);
		negotiation.setDtAtualizacao(new Date());

		Negotiation storedNegotiation = super.save(negotiation);
		logger.info("Negociação criada com sucesso!");

		return ParserUtil.parse(storedNegotiation, NegotiationVO.class);
	}

	@Override
	@Transactional
	public NegotiationVO updateNegotiation(NegotiationVO negotiationVO) throws BusinessException {
		logger.info("Atualizando mensagem: " + negotiationVO);

		if (negotiationVO == null) {
			throw new IllegalArgumentException("A entidade negociação não pode ser nula.");
		}

		if (negotiationVO.getUsuario() == null || negotiationVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário da negociação não pode ser nulo ou conter id nulo.");
		}

		if (negotiationVO.getProduto() == null || negotiationVO.getProduto().getId() == null) {
			throw new IllegalArgumentException("O produto da negociação não pode ser nulo ou conter id nulo.");
		}

		// if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !negotiationVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
		// throw new InvalidPermissionException();
		// }

		Negotiation storedNegotiation = getDAO().findByPrimaryKey(negotiationVO.getId());
		if (storedNegotiation == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhuma negociação com o id informado.");
		}

		if (negotiationVO.getTipoNegociacao() != null) {
			storedNegotiation.setTipoNegociacao(negotiationVO.getTipoNegociacao());
		}

		if (StringUtils.isNotBlank(negotiationVO.getObservacao())) {
			storedNegotiation.setObservacao(negotiationVO.getObservacao());
		}

		if (negotiationVO.getValorPgto() != null) {
			storedNegotiation.setValorPgto(negotiationVO.getValorPgto());
		}

		if (negotiationVO.getStatus() != null) {
			storedNegotiation.setStatus(negotiationVO.getStatus());
		}

		storedNegotiation.setDtAtualizacao(new Date());
		storedNegotiation = super.save(storedNegotiation);
		logger.info("Negociação atualizada com sucesso!");

		return ParserUtil.parse(storedNegotiation, NegotiationVO.class);
	}

	@Override
	@Transactional
	public void updateStatus(Long id, NegotiationStatusEnum status) throws BusinessException {
		if (id == null) {
			throw new IllegalArgumentException("O id da negociação não pode ser nulo.");
		}

		if (status == null) {
			throw new IllegalArgumentException("O novo status da negociação não pode ser nulo.");
		}

		logger.info("Alterando status da negociação [" + id + "] para " + status);
		Negotiation storedNegotiation = getDAO().findByPrimaryKey(id);
		if (storedNegotiation == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhuma mensagem para o id informado.");
		}

		// if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !storedNegotiation.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
		// throw new InvalidPermissionException();
		// }

		storedNegotiation.setStatus(status);
		storedNegotiation.setDtAtualizacao(new Date());
		super.save(storedNegotiation);

		logger.info("Status da negociação atualizado com sucesso!");
	}

	@Override
	public List<NegotiationVO> listNegotiationByUser(Long userId) throws BusinessException {
		logger.info("Listando as negociações do usuário: " + userId);
		List<Negotiation> listNegotiation = getDAO().findByUser(userId, Arrays.asList(NegotiationStatusEnum.values()));
		logger.info("Negociações listadas com sucesso!");

		return ParserUtil.parse(listNegotiation, NegotiationVO.class);
	}

	@Override
	public List<NegotiationVO> listNegotiationByUserSeller(Long userId) throws BusinessException {
		logger.info("Listando as negociações de venda do usuário: " + userId);
		List<Negotiation> listNegotiation = getDAO().findByUserSeller(userId, Arrays.asList(NegotiationStatusEnum.values()));
		logger.info("Negociações de venda listadas com sucesso!");

		return ParserUtil.parse(listNegotiation, NegotiationVO.class);
	}

	@Override
	public List<NegotiationVO> listNegotiationByProduct(Long productId) throws BusinessException {
		logger.info("Listando as negociações do produto: " + productId);
		List<Negotiation> listNegotiation = getDAO().findByProduct(productId, Arrays.asList(NegotiationStatusEnum.values()));
		logger.info("Mensagens listadas com sucesso!");

		return ParserUtil.parse(listNegotiation, NegotiationVO.class);
	}

}
