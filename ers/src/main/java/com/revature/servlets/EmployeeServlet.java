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
import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public class EmployeeServlet extends DefaultServlet {
	private Logger log = Logger.getRootLogger();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(request.getSession().getAttribute("user")==null) {
			response.setStatus(403);
			return;
		}
		JdbcDao dao = new JdbcDao();
		List<Reimbursement> rList = new ArrayList<>();
		rList = dao.getByUser((User) request.getSession().getAttribute("user"));
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(rList);
		log.trace(json);
		response.getWriter().write(json);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(request.getSession().getAttribute("user")==null) {
			response.setStatus(403);
			return;
		}
		// using JSON
		String json = request.getReader().lines().reduce((acc, cur) -> acc + cur).get();
		log.trace("json " + json);
		ObjectMapper om = new ObjectMapper();
		Reimbursement toSubmit = (Reimbursement) om.readValue(json, Reimbursement.class);
		log.trace(toSubmit);
		JdbcDao dao = new JdbcDao();
		boolean succ = dao.saveReimbursement(toSubmit, (User) request.getSession().getAttribute("user"));
		if(!succ) {
			response.setStatus(501);
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