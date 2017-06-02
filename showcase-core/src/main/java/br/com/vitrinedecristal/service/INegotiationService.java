package br.com.vitrinedecristal.service;

import java.util.List;

import br.com.vitrinedecristal.dao.INegotiationDAO;
import br.com.vitrinedecristal.enums.NegotiationStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Negotiation;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.NegotiationVO;

/**
 * Define os contratos de lógica de negócio da entidade {@link Negotiation}.
 */
public interface INegotiationService extends IBaseService<Long, Negotiation, INegotiationDAO> {

	/**
	 * Obtém informações da negociação
	 * 
	 * @param id id da negociação
	 * @return informações da negociação
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	NegotiationVO getNegotiation(Long id) throws BusinessException;

	/**
	 * Cria a negociação
	 * 
	 * @param negotiationVO informações para o cadastro da negociação
	 * @return negociação criada
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	NegotiationVO createNegotiation(NegotiationVO negotiationVO) throws BusinessException;

	/**
	 * Atualiza as informações da negociação
	 * 
	 * @param negotiationVO informações para a alteração da negociacao
	 * @return negociação alterada
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	NegotiationVO updateNegotiation(NegotiationVO negotiationVO) throws BusinessException;

	/**
	 * Altera o status da negociação
	 * 
	 * @param id id da negociação a ser alterado o status
	 * @param status novo status da negociação
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	void updateStatus(Long id, NegotiationStatusEnum status) throws BusinessException;

	/**
	 * Lista as negociações do usuário
	 * 
	 * @param userId id do usuário
	 * @return lista de negociações do usuário
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<NegotiationVO> listNegotiationByUser(Long userId) throws BusinessException;

	/**
	 * Lista as negociações de venda do usuário
	 * 
	 * @param userId id do usuário
	 * @return lista de negociações do usuário
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<NegotiationVO> listNegotiationByUserSeller(Long userId) throws BusinessException;

	/**
	 * Lista as negociações do produto
	 * 
	 * @param productId id do produto
	 * @return lista de negociações do produto
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<NegotiationVO> listNegotiationByProduct(Long productId) throws BusinessException;

}