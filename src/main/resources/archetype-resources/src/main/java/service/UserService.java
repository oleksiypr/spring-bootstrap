#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.List;

import ${package}.domain.User;

public interface UserService {
	void save(User u);
	User find(int id);
	List<User>findAll();
}
