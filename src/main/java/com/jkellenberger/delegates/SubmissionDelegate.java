package com.jkellenberger.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkellenberger.beans.Employee;
import com.jkellenberger.beans.Submission;
import com.jkellenberger.data.SubmissionOracle;

public class SubmissionDelegate implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(SubmissionDelegate.class);
	private SubmissionOracle so = new SubmissionOracle();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by Submission delegate");
		HttpSession session = req.getSession();
		switch (req.getMethod()) {
		case "GET":
			resp.getWriter().write("attempt to get Submissions");
			//checkLogin(req, resp);
			break;
		case "POST":
			// create new submission
			//Employee emp = (Employee) session.getAttribute("loggedEmployee");
			createNewSubmission(req,resp);
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

	private void createNewSubmission(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.trace("Attempting to Create Session");
//		let sendStr = "subSub="+send.subSub+"&subFirstMan="+send["subFirstMan"]+"&subHead="+send["subHead"]
//				+"&subName="+send["subName"]+"&subLocation="+send["subLocation"]+"&subCost="+send["subCost"]
//				+"&subJustification="+send["subJustification"]+"&subMissedHours="+["subMissedHours"]+"&subType="+send["subType"]
//				+"&subDate="+send["subDate"];
		if (req.getParameter("subSub").length() == 0) {
			log.trace("Failed Submission: no submitter id");
		} else {
			Submission submission = new Submission();
			submission.setSubmitterId(Integer.parseInt(req.getParameter("subSub")));
			if(req.getParameter("subFirstMan").length() > 0)
				submission.setFirstManagerId(Integer.parseInt(req.getParameter("subFirstMan")));
			if(req.getParameter("subHead").length() > 0)
				submission.setSecondManagerId(Integer.parseInt(req.getParameter("subHead")));
			if(req.getParameter("subName").length() > 0)
				submission.setName(req.getParameter("subName"));
			if(req.getParameter("subLocation").length() > 0)
				submission.setLocation(req.getParameter("subLocation"));
			log.trace("subDate: " + req.getParameter("subDate"));

			if(req.getParameter("subDate").length() > 0)
				submission.setDateTimeStr(req.getParameter("subDate"));
			//////////////////////////////
			submission.setBenCoId(1);
			//////////////////////////////
			if(req.getParameter("subType").length() > 0) {
				submission.setTypeId(Integer.parseInt(req.getParameter("subType")));
				float val = Float.parseFloat(req.getParameter("subCost"));
				val = calculateReimbursementFromCost(val, submission.getTypeId());
				submission.setAmount(val);
			}
			if(req.getParameter("subJustification").length() > 0)
				submission.setWorkJustification(req.getParameter("subJustification"));
			if(req.getParameter("subMissedHours").length() > 0)
				submission.setTimeMissed(Integer.parseInt((String)req.getParameter("subMissedHours")));
			submission.setGradingTypeId(1);
			log.trace(submission);
			
			int check = so.addSubmission(submission);
			check=1;
			if (check > 0) {
				log.trace("Submission success!");
				resp.getWriter().write("Submission success!");
			} else {
				log.trace("Submission failed");
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error in adding to Oracle");
			}
		}
	}
	
	private float calculateReimbursementFromCost(float val, int type){
		switch(type) {
			case 1:
				return val * 0.8f;
			case 2:
				return val * 0.6f;
			case 3:
				return val * 0.75f;
			case 4:
				return val;
			case 5:
				return val * 0.9f;
			case 6:
				return val * 0.3f;
			default:
				return 0.0f;
		}
	}

}

