#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.dao.UserDAO;
import ${package}.domain.User;


@Transactional
@Service
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
}
