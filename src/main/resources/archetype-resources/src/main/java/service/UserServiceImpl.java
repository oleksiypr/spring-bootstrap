#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.dao.UserDAO;
import ${package}.domain.User;


@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDao;

	@Override
	public void save(User u) {
		userDao.save(u);
	}
	
	@Override
	public User find(int id) {
		return userDao.find(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
}