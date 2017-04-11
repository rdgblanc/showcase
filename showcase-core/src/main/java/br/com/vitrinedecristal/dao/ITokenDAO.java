package br.com.vitrinedecristal.dao;

import br.com.vitrinedecristal.model.Token;

public interface ITokenDAO extends IBaseDAO<Long, Token> {

	Token findByHash(String tokenHash);

}
