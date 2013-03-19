#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service(value = "authUserDetailService")
public class AuthUserDetailService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AuthUserDetailService.class);


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		String login = null;
		String password = null;
		
		logger.debug("Started loading user by name: " + username);
		
		if (username.equals("admin")) {
			login = "admin";
			password = "admin";
			
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));			
		}
		
		if (username.equals("user")) {
			login = "user";
			password = "user";
			
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		
		logger.debug("User " + username + ": " + login + ", " + password);

		return new User(login, password, true, true, true, true, list);
	}
}
