package br.com.vitrinedecristal.service.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vitrinedecristal.dao.IMessageDAO;
import br.com.vitrinedecristal.enums.MessageStatusEnum;
import br.com.vitrinedecristal.enums.RoleEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.exception.InvalidPermissionException;
import br.com.vitrinedecristal.model.Message;
import br.com.vitrinedecristal.security.util.AuthenticationUtils;
import br.com.vitrinedecristal.service.IMessageService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.MessageVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link Message}
 */
public class MessageService extends BaseService<Long, Message, IMessageDAO> implements IMessageService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MessageService.class);

	public MessageService(IMessageDAO messageDAO) {
		super(messageDAO);
	}

	@Override
	public MessageVO getMessage(Long id) throws BusinessException {
		logger.info("Obtendo mensagem pelo id: " + id);

		Message message = super.findByPrimaryKey(id);
		if (message == null) {
			throw new EntityNotFoundException("Não foi encontrado mensagem com o id informado.");
		}

		return ParserUtil.parse(message, MessageVO.class);
	}

	@Override
	@Transactional
	public MessageVO createMessage(MessageVO messageVO) throws BusinessException {
		logger.info("Criando mensagem: " + messageVO);

		if (messageVO == null) {
			throw new IllegalArgumentException("A entidade mensagem não pode ser nula.");
		}

		if (messageVO.getUsuario() == null || messageVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário da mensagem não pode ser nulo ou conter id nulo.");
		}

		if (messageVO.getProduto() == null || messageVO.getProduto().getId() == null) {
			throw new IllegalArgumentException("O produto da mensagem não pode ser nulo ou conter id nulo.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !messageVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		if (StringUtils.isBlank(messageVO.getTexto())) {
			throw new BusinessException("O campo texto é obrigatório");
		}

		Message message = ParserUtil.parse(messageVO, Message.class);
		message.setStatus(MessageStatusEnum.ACTIVE);
		message.setDtAtualizacao(new Date());

		Message storedMessage = super.save(message);
		logger.info("Mensagem criada com sucesso!");

		return ParserUtil.parse(storedMessage, MessageVO.class);
	}

	@Override
	@Transactional
	public MessageVO updateMessage(MessageVO messageVO) throws BusinessException {
		logger.info("Atualizando mensagem: " + messageVO);

		if (messageVO == null) {
			throw new IllegalArgumentException("A entidade mensagem não pode ser nula.");
		}

		if (messageVO.getUsuario() == null || messageVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário da mensagem não pode ser nulo ou conter id nulo.");
		}

		if (messageVO.getProduto() == null || messageVO.getProduto().getId() == null) {
			throw new IllegalArgumentException("O produto da mensagem não pode ser nulo ou conter id nulo.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !messageVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		Message storedMessage = getDAO().findByPrimaryKey(messageVO.getId());
		if (storedMessage == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhuma mensagem com o id informado.");
		}

		if (StringUtils.isNotBlank(messageVO.getTexto())) {
			storedMessage.setTexto(messageVO.getTexto());
		}

		if (messageVO.getStatus() != null) {
			if (!messageVO.getStatus().equals(MessageStatusEnum.INACTIVE)) {
				storedMessage.setStatus(messageVO.getStatus());
			} else if (messageVO.getStatus().equals(MessageStatusEnum.INACTIVE) && AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
				storedMessage.setStatus(messageVO.getStatus());
			}
		}

		storedMessage.setDtAtualizacao(new Date());
		storedMessage = super.save(storedMessage);
		logger.info("Mensagem atualizada com sucesso!");

		return ParserUtil.parse(storedMessage, MessageVO.class);
	}

	@Override
	@Transactional
	public void updateStatus(Long id, MessageStatusEnum status) throws BusinessException {
		if (id == null) {
			throw new IllegalArgumentException("O id da mensagem não pode ser nulo.");
		}

		if (status == null) {
			throw new IllegalArgumentException("O novo status da mensagem não pode ser nulo.");
		}

		logger.info("Alterando status da mensagem [" + id + "] para " + status);
		Message storedMessage = getDAO().findByPrimaryKey(id);
		if (storedMessage == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhuma mensagem para o id informado.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !storedMessage.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		storedMessage.setStatus(status);
		storedMessage.setDtAtualizacao(new Date());
		super.save(storedMessage);

		logger.info("Status da mensagem atualizado com sucesso!");
	}

	@Override
	public List<MessageVO> listMessageByUser(Long userId) throws BusinessException {
		logger.info("Listando as mensagens do usuário: " + userId);
		List<Message> listMessage = getDAO().findByUser(userId, Arrays.asList(MessageStatusEnum.ACTIVE));
		logger.info("Mensagens listadas com sucesso!");

		return ParserUtil.parse(listMessage, MessageVO.class);
	}

	@Override
	public List<MessageVO> listMessageByProduct(Long productId) throws BusinessException {
		logger.info("Listando as mensagens do produto: " + productId);
		List<Message> listMessage = getDAO().findByProduct(productId, Arrays.asList(MessageStatusEnum.ACTIVE));
		logger.info("Mensagens listadas com sucesso!");

		return ParserUtil.parse(listMessage, MessageVO.class);
	}

}
