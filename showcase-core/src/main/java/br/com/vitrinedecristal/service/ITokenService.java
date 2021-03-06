package br.com.vitrinedecristal.service;

import br.com.vitrinedecristal.dao.ITokenDAO;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Token;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.UserVO;

public interface ITokenService extends IBaseService<Long, Token, ITokenDAO> {

	Token createWelcomeToken(User user);

	Token validateWelcomeToken(String tokenHash) throws BusinessException;

	Token createTokenForPasswordRecovery(User user);

	Token validatePasswordForgotToken(String tokenUid, Long userId) throws BusinessException;

	/**
	 * Consulta um token pelo hash
	 * 
	 * @param tokenHash Hash do token
	 * @return {@link Token} encontrado
	 */
	Token findByHash(String tokenHash);

}
