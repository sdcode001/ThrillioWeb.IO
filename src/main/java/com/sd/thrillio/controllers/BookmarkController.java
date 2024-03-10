package com.sd.thrillio.controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sd.thrillio.entities.Bookmark;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.managers.BookmarkManager;
import com.sd.thrillio.managers.UserManager;

//Controllers are also Singletons.

@WebServlet(urlPatterns = {"/bookmark", "/bookmark/save", "/bookmark/mybooks", "/bookmark/delete"})
public class BookmarkController extends HttpServlet{
	
	public BookmarkController() {
        // TODO Auto-generated constructor stub
    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long userId = 5;
		RequestDispatcher despatcher = null;
		
		if(request.getServletPath().contains("save")) {
			//save book.
			String bookId = request.getParameter("bid");
			User user = UserManager.getInstance().getUser(userId);
			Bookmark bookmark = BookmarkManager.getInstance().getBook(Long.parseLong(bookId));
			
			BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
			
			Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
			request.setAttribute("books", list);
			despatcher = request.getRequestDispatcher("/mybookmark.jsp");
		}
		else if(request.getServletPath().contains("mybooks")) {
			Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
			request.setAttribute("books", list);
			despatcher = request.getRequestDispatcher("/mybookmark.jsp");
		}
		else if(request.getServletPath().contains("delete")) {
			//delete book
			String bookId = request.getParameter("bid");
			User user = UserManager.getInstance().getUser(userId);
			Bookmark bookmark = BookmarkManager.getInstance().getBook(Long.parseLong(bookId));
			
			BookmarkManager.getInstance().deleteUserBookmark(user, bookmark);
			
			Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
			request.setAttribute("books", list);
			despatcher = request.getRequestDispatcher("/mybookmark.jsp");
			
		}
		else {
			Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(false, userId);
			request.setAttribute("books", list);
			despatcher = request.getRequestDispatcher("/browse.jsp");
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
