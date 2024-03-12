package com.sd.thrillio.managers;

import java.util.Collection;
import java.util.List;
import com.sd.thrillio.daos.UserDao;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.util.StringUtil;

//singletone design pattern is used.
//1) single object is created
//2) golbal point of access

public class UserManager {
	private static UserManager instance = new UserManager();
	private static UserDao dao = new UserDao();
	
	private UserManager(){}
	
	public static UserManager getInstance() {
		return instance;
	}
	
	public User createUser(long id, String email, String password, String firstName, String lastName, int gender, String userType) {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);
		return user;
	}
	
	public Collection<User> getUsers() {
		return dao.getUsers();
	}

	public User getUser(long userId) {
		return dao.getUser(userId);
	}

	public long authenticate(String email, String password) {
		String encryptedPassword = StringUtil.encodePassword(password);
		return dao.authenticate(email, encryptedPassword);
	}

    public int saveNewUser(User user) {
    	String encryptedPassword = StringUtil.encodePassword(user.getPassword());
    	user.setPassword(encryptedPassword);
		return dao.saveNewUser(user);
		
	}
   
}
