package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.JdbcDao;
import com.revature.beans.User;

public class LoginServlet extends DefaultServlet {
	private Logger log = Logger.getRootLogger();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(request.getSession().getAttribute("user")==null) {
			response.setStatus(403);
			return;
		}
		
//		ObjectMapper om = new ObjectMapper();
//		String json = om.writeValueAsString(users);
//		response.getWriter().write(json);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// using JSON
		String json = request.getReader().lines().reduce((acc, cur) -> acc + cur).get();
		log.trace("json " + json);
		ObjectMapper om = new ObjectMapper();
		User credentials = (User) om.readValue(json, User.class);
		log.trace(credentials);
		JdbcDao dao = new JdbcDao();
		User u = dao.getUser(credentials.getUsername(), credentials.getPassword());

		if (u != null) {
			HttpSession sess = request.getSession();
			sess.setAttribute("user", u);
			String respJson = om.writeValueAsString(u);
			response.getWriter().write(respJson);
		} else {
			response.setStatus(401);
		}
	}
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    super.service(req, resp);
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	    resp.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
	    resp.addHeader("Access-Control-Allow-Credentials", "true");
	    resp.setContentType("application/json");
	}
}