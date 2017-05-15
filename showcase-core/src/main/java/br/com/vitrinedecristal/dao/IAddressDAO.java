package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.enums.AddressStatusEnum;
import br.com.vitrinedecristal.model.Address;

/**
 * Define os contratos de acesso a dados da entidade {@link Address}.
 */
public interface IAddressDAO extends IBaseDAO<Long, Address> {

	List<Address> findByUser(Long userId, List<AddressStatusEnum> status);

}