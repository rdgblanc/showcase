package br.com.vitrinedecristal.service.bean;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import br.com.vitrinedecristal.dao.IUserLoginDAO;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.model.UserLogin;
import br.com.vitrinedecristal.service.IUserLoginService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.RequestUtil;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link UserLogin}
 */
public class UserLoginService extends BaseService<Long, UserLogin, IUserLoginDAO> implements IUserLoginService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UserLoginService.class);

	public UserLoginService(IUserLoginDAO userLoginDAO) {
		super(userLoginDAO);
	}

	@Override
	@Transactional
	public void saveLastAccess(User user) {
		logger.info("Atualizando último acesso do usuário: " + user.getId());

		UserLogin userLogin = new UserLogin();
		userLogin.setIp(RequestUtil.getInstance().getClientIp());
		userLogin.setDtInsercao(new Date());
		userLogin.setUser(user);
		super.save(userLogin);

		logger.info("Atualização de acesso realizada com sucesso!");
	}

}
