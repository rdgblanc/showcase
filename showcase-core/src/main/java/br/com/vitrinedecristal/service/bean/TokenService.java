package br.com.vitrinedecristal.service.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vitrinedecristal.application.ApplicationBeanFactory;
import br.com.vitrinedecristal.dao.ITokenDAO;
import br.com.vitrinedecristal.enums.TokenEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.log.TrackingLogger;
import br.com.vitrinedecristal.model.Token;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.service.ITokenService;
import br.com.vitrinedecristal.service.IUserService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.UserVO;

/**
 * Serviço para gerenciamento de tokens de ativações dos usuários.
 */
public class TokenService extends BaseService<Long, Token, ITokenDAO> implements ITokenService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = TrackingLogger.getLogger(TokenService.class);

	private static final int EXPIRATION_TIME_FORGOT_PASSWORD = 3600; // Segundos em que o token de recuperação de senha leva para expirar

	public TokenService(ITokenDAO tokenDAO) {
		super(tokenDAO);
	}

	@Override
	public Token createTokenForPasswordRecovery(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Usuário deve ser informado");
		}

		logger.info("Criando token de recuperação de senha para o usuário: " + user.getId());

		Calendar expirationTime = GregorianCalendar.getInstance();
		expirationTime.add(Calendar.SECOND, EXPIRATION_TIME_FORGOT_PASSWORD);

		Token token = new Token();
		token.setUsuario(user);
		token.setTipo(TokenEnum.FORGOT_PASSWORD);
		token.setUsado(false);
		token.setDtExpiracao(expirationTime.getTime());

		getDAO().save(token);
		logger.info("Token criado com sucesso!");

		return token;
	}

	@Override
	public Token validatePasswordForgotToken(String tokenHash, Long userId) throws BusinessException {
		if (StringUtils.isBlank(tokenHash)) {
			throw new IllegalArgumentException("O Token Hash deve ser informado");
		}

		logger.info("Ativando token de esquecimento de senha através do tokenHash [ " + tokenHash + " ] e userId [ " + userId + " ]");
		Token token;

		try {
			token = getDAO().findByHash(tokenHash);
		} catch (NoResultException e) {
			throw new BusinessException("Código de validação inválido");
		}

		if (token == null) {
			throw new BusinessException("Código de validação não encontrado");
		}

		if (!token.getTipo().equals(TokenEnum.FORGOT_PASSWORD)) {
			throw new BusinessException("O tipo informado no token é inválido");
		}

		if (token.getDtExpiracao().before(new Date())) {
			throw new BusinessException("O código de validação informado está expirado");
		}

		if (userId != null) {
			if (token.getUsuario().getId().equals(userId)) {
				IUserService userBO = ApplicationBeanFactory.getBean(IUserService.class);
				try {
					userBO.updateUser(ParserUtil.parse(token.getUsuario(), UserVO.class));
				} catch (Exception e) {
					logger.warn("Erro ao atualizar usuário", e);
				}

				token.setDtAtivacao(new Date());
				Token newToken = getDAO().save(token);

				logger.info("Ativação do token realizada com sucesso!");
				return newToken;

			} else {
				throw new IllegalArgumentException("O código de validação informado não é valido.");
			}
		} else {
			throw new IllegalArgumentException("O código do usuario informado não é um valor valido.");
		}
	}

	@Override
	public Token findByHash(String tokenHash) {
		return getDAO().findByHash(tokenHash);
	}

}