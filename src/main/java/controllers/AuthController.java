package controllers;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import ServiceImpl.AuthServiceImpl;
import Services.AuthService;
import models.User;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "database_system")
	private DataSource dataSource;
	private AuthService authService;

	public AuthController() {
		super();
	}

	@Override
	public void init() throws ServletException {// initialization method
		super.init();
		this.authService = new AuthServiceImpl(dataSource);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("get action : " + action);
		if (action == null) {
			action = "sign-in";
			System.out.println("get action : " + action);
		}
		switch (action) {
		case "sign-in":
			signIn(request, response);
			break;
		case "sign-up":
			signUp(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		default:
			break;
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("userId");
			removeCookie(request, response, "userId");
			response.sendRedirect("login.jsp");
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
	}

	private void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
				break;
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			if (name == null || email == null || password == null) {
				request.getSession().setAttribute("error", "Name , Email and password are required");
				response.sendRedirect("register.jsp");
				return;
			}
			boolean result = authService.signUp(new User(name, email, password));
			if (result) {
				User savedUser = authService.getUser(email);
				request.getSession().setAttribute("userId", savedUser.getId());
				Cookie cookie = new Cookie("userId", savedUser.getId() + "");
				cookie.setMaxAge(60 * 60);
				response.addCookie(cookie);
				response.sendRedirect("UserController");
				return;
			} else {
				request.getSession().setAttribute("error", "Invalid Name or email or password");
				response.sendRedirect("register.jsp");
				return;
			}
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.toString());
			response.sendRedirect("register.jsp");
		}
	}

	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			if (email == null || password == null) {
				request.getSession().setAttribute("error", "Email and password are required");
				response.sendRedirect("login.jsp");
				return;
			}
			boolean result = authService.signIn(new User(email, password));
			if (result) {
				User savedUser = authService.getUser(email);
				request.getSession().setAttribute("userId", savedUser.getId());
				Cookie cookie = new Cookie("userId", savedUser.getId() + "");
				cookie.setMaxAge(60 * 60);
				response.addCookie(cookie);
				response.sendRedirect("UserController");
				return;
			} else {
				request.getSession().setAttribute("error", "Invalid email or password");
				response.sendRedirect("login.jsp");
				return;
			}
		} catch (Exception e) {
			request.getSession().setAttribute("error", e.toString());
			response.sendRedirect("login.jsp");
		}
	}

}
