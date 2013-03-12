#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import ${package}.config.TestConfig;
import ${package}.domain.User;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class UserDAOImplTest {
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Test
	public void testSave() {
		String name = "Test001";
		String address = "Address001";
		
		User user = new User();
		user.setId(null);
		user.setName(name);
		user.setAddress(address);
		
		userDao.save(user);
		sessionFactory.getCurrentSession().flush();
		
		Assert.notNull(user.getId());
		Assert.notNull(user.getName());
		Assert.notNull(user.getAddress());
		
		User persisted = (User) sessionFactory.getCurrentSession().get(User.class, user.getId());
		
		Assert.notNull(persisted);
		
		Assert.isTrue(user.getName().equals(persisted.getName()));	
		Assert.isTrue(user.getAddress().equals(persisted.getAddress()));		
	}
	
	
	@Test
	public void testFind() {
		String name = "Test001";
		String address = "Address001";
		
		User persisted = new User();
		persisted.setId(null);
		persisted.setName(name);
		persisted.setAddress(address);
		
		sessionFactory.getCurrentSession().saveOrUpdate(persisted);
		sessionFactory.getCurrentSession().flush();
		
		User user = userDao.find(persisted.getId());
		
		Assert.notNull(user);
		Assert.notNull(user.getId());
		Assert.notNull(user.getAddress());
		
		Assert.isTrue(user.getName().equals(persisted.getName()));	
		Assert.isTrue(user.getAddress().equals(persisted.getAddress()));	
	}
}
