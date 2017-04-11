package br.com.vitrinedecristal.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.vitrinedecristal.dao.IUserDAO;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.security.credential.UserCredentials;

@Service("userDetailsService")
public class UserDetailsServiceBean implements UserDetailsService {

	@Autowired
	private IUserDAO userDAO;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isBlank(username)) {
			throw new BadCredentialsException("Bad credentials");
		}

		User user = this.userDAO.findByEmail(username, null);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

		return new UserCredentials(user.getId(), user.getEmail(), user.getSenha(), authorities);
	}

}