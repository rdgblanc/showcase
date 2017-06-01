package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.enums.NegotiationStatusEnum;
import br.com.vitrinedecristal.model.Negotiation;

/**
 * Define os contratos de acesso a dados da entidade {@link Negotiation}.
 */
public interface INegotiationDAO extends IBaseDAO<Long, Negotiation> {

	List<Negotiation> findByUser(Long userId, List<NegotiationStatusEnum> status);

	List<Negotiation> findByProduct(Long productId, List<NegotiationStatusEnum> status);

}