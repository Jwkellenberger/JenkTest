package com.jkellenberger.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.jkellenberger.delegates.FrontControllerDelegate;


public class FrontController extends DefaultServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(FrontController.class);
	private RequestDispatcher rd = new RequestDispatcher();
	
	private void process(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {
		log.trace(req.getRequestURI() + " " + req.getMethod());
		if(req.getRequestURI().substring(req.getContextPath().length())
				.startsWith("/static")) {
			log.trace("This is static content!");
			super.doGet(req, resp);
		} else {
			log.trace("non-static content");
			FrontControllerDelegate fcd = rd.dispatch(req,resp);
			fcd.process(req,resp);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		process(req,resp);
//		resp.getWriter().write("We're here!");
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
}
