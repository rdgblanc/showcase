package br.com.vitrinedecristal.service;

import java.util.List;

import br.com.vitrinedecristal.dao.IMessageDAO;
import br.com.vitrinedecristal.enums.MessageStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Message;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.MessageVO;

/**
 * Define os contratos de lógica de negócio da entidade {@link Message}.
 */
public interface IMessageService extends IBaseService<Long, Message, IMessageDAO> {

	/**
	 * Obtém informações da mensagem
	 * 
	 * @param id id da mensagem
	 * @return informações da mensagem
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	MessageVO getMessage(Long id) throws BusinessException;

	/**
	 * Cria a mensagem
	 * 
	 * @param messageVO informações para o cadastro da mensagem
	 * @return mensagem criada
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	MessageVO createMessage(MessageVO messageVO) throws BusinessException;

	/**
	 * Atualiza as informações da mensagem
	 * 
	 * @param messageVO informações para a alteração da mensagem
	 * @return mensagem alterada
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	MessageVO updateMessage(MessageVO messageVO) throws BusinessException;

	/**
	 * Altera o status da mensagem
	 * 
	 * @param id id da mensagem a ser alterado o status
	 * @param status novo status da mensagem
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	void updateStatus(Long id, MessageStatusEnum status) throws BusinessException;

	/**
	 * Lista as mensagens do usuário
	 * 
	 * @param userId id do usuário
	 * @return lista de mensagens do usuário
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<MessageVO> listMessageByUser(Long userId) throws BusinessException;

	/**
	 * Lista as mensagens do produto
	 * 
	 * @param productId id do produto
	 * @return lista de mensagens do produto
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<MessageVO> listMessageByProduct(Long productId) throws BusinessException;

}