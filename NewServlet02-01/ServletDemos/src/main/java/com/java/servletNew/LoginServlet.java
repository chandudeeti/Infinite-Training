package com.java.servletNew;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user, pwd;
		user=request.getParameter("userName");
		pwd=request.getParameter("password");
		PrintWriter out = response.getWriter();
		if(user.equals("Infinite") && pwd.equals("Infinite"))
		{
			RequestDispatcher disp = request.getRequestDispatcher("Menu.html");
			disp.forward(request, response);
			out.println("Correct credentials");
		}
		else
		{
			RequestDispatcher disp = request.getRequestDispatcher("Login.html");
			disp.include(request, response);
			out.println("Incorrect Credentials");
		}
		
	}

}
