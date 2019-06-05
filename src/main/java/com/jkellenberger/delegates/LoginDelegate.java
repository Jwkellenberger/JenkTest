package com.jkellenberger.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkellenberger.beans.Employee;
import com.jkellenberger.data.EmployeeOracle;

public class LoginDelegate implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(LoginDelegate.class);
	private EmployeeOracle eo = new EmployeeOracle();
	//private CustomerService customer = new CustomerServiceOracle();
	//private EmployeeService employee = new EmployeeServiceOracle();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by login delegate");
		HttpSession session = req.getSession();
		switch (req.getMethod()) {
		case "GET":
			resp.getWriter().write("attempt to login");
			//checkLogin(req, resp);
			break;
		case "POST":
			// logging in
			Employee emp = (Employee) session.getAttribute("loggedEmployee");
//			Customer customer = (Customer) session.getAttribute("loggedCustomer");
			if (emp != null) {
				respondWithUser(resp, emp);
			} else {
				log.trace(LoginDelegate.class + ": No Current session for given user");
				checkLogin(req, resp);
			}
			break;
		case "DELETE":
			// logging out
			session.invalidate();
			// sever an id with a session and response says to delete cookie
			log.trace("User logged out");
			break;
		default:
			break;
		}
	}

	private void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.trace("Logging in!");
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("loggedEmployee");
		Employee check = new Employee();
		if (employee != null) {
			respondWithUser(resp, employee);
		} else {

			// Need to see if we are an employee. Then we need to see if we are a customer.
			// Then we need to store that information in the session object.
			String username = req.getParameter("user");
			String password = req.getParameter("pass");
			log.trace(("Login: " +username + "  Pw:" + password));
			check.setLogin(username);
			check.setPw(password);
			employee = eo.getEmployee(check);
			
			if (employee != null) {
				log.trace("employee being added to session");
				session.setAttribute("loggedEmployee", employee);
			}
			if (employee == null) {
				log.trace("employee not found in Oracle");
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No user found with those credentials");
			} else {
				respondWithUser(resp, employee);
			}
		}
	}

	private void respondWithUser(HttpServletResponse resp, Employee emp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		String e = om.writeValueAsString(emp);
		StringBuilder sb = new StringBuilder("{\"employee\":");
//		sb.append(", \"employee\":");
		sb.append(e);
		sb.append("}");
		log.trace("Writing JSON to Browser: " + e);
		resp.getWriter().write(sb.toString());
	}

}
