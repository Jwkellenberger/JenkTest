package com.jkellenberger.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jkellenberger.beans.Employee;
//import com.jkellenberger.data.EmployeeOracle;

public class BasePageDelegate implements FrontControllerDelegate{

	private Logger log = Logger.getLogger(BasePageDelegate.class);
//	private EmployeeOracle eo = new EmployeeOracle();
	//private CustomerService customer = new CustomerServiceOracle();
	//private EmployeeService employee = new EmployeeServiceOracle();
//	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by root delegate");
		HttpSession session = req.getSession();
		switch (req.getMethod()) {
		case "GET":
//			resp.getWriter().write("attempt to reach base page");
			req.getRequestDispatcher("/static/root.html").forward(req, resp);
//			resp.getWriter().write("attempt to reach base page");
			break;
		case "POST":
			// testing post
			resp.getWriter().write("No post functionality to this page");
			break;
		case "PUT":
			// testing put
			resp.getWriter().write("No put functionality to this page");
			break;
		case "DELETE":
			// testing delete
			session.invalidate();
			resp.getWriter().write("No Delete functionality to this page");
			break;
		default:
			resp.getWriter().write("No functionality for request method: " + req.getMethod());
			break;
		}
	}

}
