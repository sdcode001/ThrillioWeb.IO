package com.sd.thrillio.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.sd.thrillio.constants.UserType;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.managers.UserManager;


public class UserDao {
	
	public Collection<User> getUsers() {
			Collection<User> users = new ArrayList<>();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			//try-with-resource => conn & stmt will be colsed
			//Connection string => <protocol>:<sub-protocol>:<data-source-details>
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?useSSL=false", "root", "DeY@733129");
				Statement stmt = conn.createStatement();){
				
				String query = "select * from User";
				
				ResultSet rs = stmt.executeQuery(query);
				
		    	while (rs.next()) {
		    		long id = rs.getLong("id");
		    		String email = rs.getString("email");
		    		String password = rs.getString("password");
		    		String first_name = rs.getString("first_name");
		    		String last_name = rs.getString("last_name");
		    		int gender_id = rs.getInt("gender_id");
		    		int user_type_id = rs.getInt("user_type_id");
		    		String user_type = UserType.values()[user_type_id].getValue();
		    		Timestamp created_date = rs.getTimestamp("created_date");
		    			
		    		users.add(UserManager.getInstance().createUser(id, email, password, first_name, last_name, gender_id, user_type));
		    	}
	    	
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			return users;
	}

	
	public User getUser(long userId) {
		User user = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//try-with-resource => conn & stmt will be colsed
		//Connection string => <protocol>:<sub-protocol>:<data-source-details>
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?useSSL=false", "root", "DeY@733129");
			Statement stmt = conn.createStatement();){
			
			String query = "select * from User where id = "+userId;
			
			ResultSet rs = stmt.executeQuery(query);
			
	    	while (rs.next()) {
	    		long id = rs.getLong("id");
	    		String email = rs.getString("email");
	    		String password = rs.getString("password");
	    		String first_name = rs.getString("first_name");
	    		String last_name = rs.getString("last_name");
	    		int gender_id = rs.getInt("gender_id");
	    		int user_type_id = rs.getInt("user_type_id");
	    		String user_type = UserType.values()[user_type_id].getValue();
	    		Timestamp created_date = rs.getTimestamp("created_date");
	    			
	    		user = UserManager.getInstance().createUser(id, email, password, first_name, last_name, gender_id, user_type);
	    	}
    	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

}
