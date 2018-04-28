package com.revature.bankapp.servlets;

import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.bankapp.pojos.UserAccount;
import com.revature.bankapp.services.UserService;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static UserService userService = new UserService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getInputStream() != null) {
			ObjectMapper mapper = new ObjectMapper();
			UserAccount user = mapper.readValue(req.getInputStream(), UserAccount.class);
			
			user = userService.createUser(user);
			
			if (user != null) {
				user.setPassword("");
				HttpSession session = req.getSession();
				session.setAttribute("user", user);
			}
			
			PrintWriter out = resp.getWriter();
			out.write(mapper.writeValueAsString(user));
		}
	}

}
