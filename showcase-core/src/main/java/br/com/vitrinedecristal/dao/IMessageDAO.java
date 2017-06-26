package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.enums.MessageStatusEnum;
import br.com.vitrinedecristal.model.Message;

/**
 * Define os contratos de acesso a dados da entidade {@link Message}.
 */
public interface IMessageDAO extends IBaseDAO<Long, Message> {

	List<Message> findByUser(Long userId, List<MessageStatusEnum> status);

	List<Message> findByNegotiation(Long negotiationId, List<MessageStatusEnum> status);

}