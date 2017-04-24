package br.com.vitrinedecristal.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurator extends WebSecurityConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(SecurityConfigurator.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/index.html**").hasAuthority(RoleEnum.ROLE_USER.toString());
		http.authorizeRequests().antMatchers("/**").permitAll();
		// http.formLogin().loginPage("/login.jsp").permitAll();
		http.logout().permitAll();
		http.csrf().disable();

		logger.debug("Spring Security config applied");
	}

}