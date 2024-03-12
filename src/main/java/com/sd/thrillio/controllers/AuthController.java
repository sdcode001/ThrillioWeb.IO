package com.sd.thrillio.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sd.thrillio.managers.BookmarkManager;
import com.sd.thrillio.managers.UserManager;
import com.sd.thrillio.entities.Bookmark;
import com.sd.thrillio.entities.User;
import java.io.IOException;
import java.util.Collection;

/**
 * Servlet implementation class AuthController
 */

@WebServlet(urlPatterns = {"/auth", "/auth/login", "/auth/logout", "/auth/signup", "/auth/register"})
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AuthController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher despatcher = null;
		
		if(request.getServletPath().contains("login")) {
			despatcher = request.getRequestDispatcher("/login.jsp");
		}
		else if(request.getServletPath().contains("logout")) {
			//remove http-session
			request.getSession().invalidate();
			
			despatcher = request.getRequestDispatcher("/login.jsp");
		}
		else if(request.getServletPath().contains("signup")) {
			despatcher = request.getRequestDispatcher("/signup.jsp");
		}
		else if(request.getServletPath().contains("register")) {
			//register user.
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String fname = request.getParameter("first_name");
			String lname = request.getParameter("last_name");
			String gender = request.getParameter("gender");
			String user_type = request.getParameter("usertype");
			
			if(email==null || password==null || fname==null || lname==null || gender==null || user_type==null) {
				despatcher = request.getRequestDispatcher("/signup.jsp");
			}
			else {
				User user = UserManager.getInstance().createUser(-1, email, password, fname, lname, Integer.parseInt(gender), user_type);
				int result = UserManager.getInstance().saveNewUser(user);
				
				if(result>0) {
					long userId = UserManager.getInstance().authenticate(email, password);
					if(userId==-1) {
				    	despatcher = request.getRequestDispatcher("/signup.jsp");
				    }
				    else {
				    	//set http-session.
				    	//generate unique session-id for the first time.
				    	HttpSession session = request.getSession(); 
				    	session.setAttribute("userId", userId);
				    	
				    	//despatcher = request.getRequestDispatcher("bookmark/mybooks");
				    	
				    	 /* As the above servlet redirect is not working(unexpectedly) so we are using this 
				    	    approach but its not good way to redirect */
				    	Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(false, userId);
						request.setAttribute("books", list);
						despatcher = request.getRequestDispatcher("/browse.jsp");
				    }
				}
				else {
					despatcher = request.getRequestDispatcher("/signup.jsp");
				}
			}	
			
		}
		else {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			if(email==null || password==null) {
				despatcher = request.getRequestDispatcher("/login.jsp");
			}
			else {
				long userId = UserManager.getInstance().authenticate(email, password);
				
			    if(userId==-1) {
			    	despatcher = request.getRequestDispatcher("/login.jsp");
			    }
			    else {
			    	//set http-session.
			    	HttpSession session = request.getSession(); //generate unique session-id for the first time.
			    	session.setAttribute("userId", userId);
			    	
			    	despatcher = request.getRequestDispatcher("bookmark/mybooks");
			    }
			}
		}
		
		despatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
