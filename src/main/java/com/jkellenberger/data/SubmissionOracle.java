package com.jkellenberger.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jkellenberger.beans.Employee;
import com.jkellenberger.beans.Submission;
import com.jkellenberger.utils.ConnectionUtil;
import com.jkellenberger.utils.LogUtil;

public class SubmissionOracle implements SubmissionDAO {
	private static Logger log = Logger.getLogger(SubmissionOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy  HH:mm");

	@Override
	public int addSubmission(Submission submission) {
		int key = 0;
		log.trace("Adding new submission to the database.");
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			//String sql = "select id, f_name, l_name, title, login, pw, super, from employee where login=? AND pw=?";
			String sql = "insert into submission(name, locationSite, gradingType, workJust, typeId, amount, datetime, submitter, firstManager, secondManager, benCo, info, timeMissed, id)"
					+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			
			pstm.setString(1, submission.getName());
			pstm.setString(2, submission.getLocation());
			pstm.setInt(3, submission.getGradingTypeId());
			pstm.setString(4, submission.getWorkJustification());
			pstm.setInt(5, submission.getTypeId());
			pstm.setFloat(6, submission.getAmount());
			log.trace("Date: " + submission.getDateTimeStr());
			pstm.setString(7, submission.getDateTimeStr());
			pstm.setInt(8, submission.getSubmitterId());
			pstm.setInt(9, submission.getFirstManagerId());
			pstm.setInt(10, submission.getSecondManagerId());
			pstm.setInt(11, submission.getBenCoId());
			pstm.setString(12, "Great day for submission");
			pstm.setInt(13, submission.getTimeMissed());
			pstm.setInt(14, 1);
			log.trace(submission.getName());
//			ResultSet rs = pstm.executeUpdate();
			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next()) {
				log.trace("Submission created");
				key = rs.getInt(1);
				submission.setId(key);
				conn.commit();
			} else {
				log.trace("Submission not created!");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, SubmissionOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				LogUtil.logException(e1, SubmissionOracle.class);
			}
		}
		return key;
	}

	@Override
	public List<Submission> getSubmissionsBySubmitter(int id) {
		List<Submission> submissions = new LinkedList<Submission>();
		log.trace("Retrieving submissions from database belonging to this submitter.");
		try (Connection conn = cu.getConnection()) {
			//String sql = "select id, f_name, l_name, title, login, pw, super, from employee where login=? AND pw=?";

			String sql = "" +
					"select sub.id subId, sub.name subName, sub.typeid subTypeId, sub.amount subAmount, sub.datetime subDT,"
					+" sub.submitter subSub, sub.firstmanager subFirstMan, sub.secondmanager subSecondMan, sub.benco subBen,"
					+" sub.status subSta, sub.info subInfo, dept.name deptName, subtypet.name subTName, subtypet.returnpercent subTReturnPerc,"
					+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title, e1.rtotal e1Rtotal,"
					+" sub.locationSite subLocS, sub.gradingType subGradeT, sub.workJust subWorkJust, sub.timeMissed subTimeM,"
					+" e2.id e2Id, e2.f_name e2Fn, e2.l_name e2Ln, e2.title e2Title,"
					+" e3.id e3Id, e3.f_name e3Fn, e3.l_name e3Ln, e3.title e3Title,"
					+" e4.id e4Id, e4.f_name e4Fn, e4.l_name e4Ln, e4.title e4Title"
					+" from (((((submission sub join employee e1 on (sub.submitter = e1.id) join deptartment dept on (e1.dept = dept.id))"
					+" Left Outer Join employee e2 on (sub.firstmanager = e2.id))Left Outer Join employee e3 on (sub.secondmanager = e3.id))"
					+" Left Outer Join employee e4 on (sub.benco = e4.id)) Left Outer Join submissiontypes subTypeT on (sub.typeid = subTypeT.id))"
					+" where sub.submitter=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			
			ResultSet rs = pstm.executeQuery();
			Submission submission = null;
			Employee temp = null;
			while(rs.next()) {
				submission = new Submission();
				submission.setId(rs.getInt("subId"));
				submission.setName(rs.getString("subName"));
				submission.setTypeId(rs.getInt("subTypeId"));
				submission.setType(rs.getString("subTName"));
				submission.setReimbPercent(rs.getInt("subTReturnPerc"));
				submission.setDateTimeStr(rs.getString("subDT"));
				submission.setStatus(rs.getString("subSta"));
				submission.setLocation(rs.getString("subLocS"));
				submission.setGradingTypeId(rs.getInt("subGradeT"));
				submission.setWorkJustification(rs.getString("subWorkJust"));
				submission.setTimeMissed(rs.getInt("subTimeM"));
				submission.setInfo(rs.getString("subInfo"));
				submission.setSubmitterId(rs.getInt("subSub"));
				submission.setFirstManagerId(rs.getInt("subFirstMan"));
				submission.setSecondManagerId(rs.getInt("subSecondMan"));
				submission.setBenCoId(rs.getInt("subBen"));
				
				try {//An example of converting the input string into a DT
					submission.setDateTimeDt((LocalDateTime.parse(submission.getDateTimeStr(), format)));
				    log.trace("Converting Date Time");
				}
				catch (DateTimeParseException exc) {
					log.trace("Converting string into LocalDateTime failed.");
				}
				
				if(submission.getSubmitterId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission creator");
					temp = new Employee();
					temp.setId(rs.getInt("e1Id"));
					temp.setFirstName(rs.getString("e1Fn"));
					temp.setLastName(rs.getString("e1Ln"));
					temp.setTitle(rs.getString("e1Title"));
					temp.setrTotal(rs.getFloat("e1Rtotal"));
					temp.setDept(rs.getString("deptName"));
					
					submission.setSubmitter(temp);
					temp = null;
				}
				
				if(submission.getFirstManagerId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission manager");
					temp = new Employee();
					temp.setId(rs.getInt("e2Id"));
					temp.setFirstName(rs.getString("e2Fn"));
					temp.setLastName(rs.getString("e2Ln"));
					temp.setTitle(rs.getString("e2Title"));
					
					submission.setFirstManager(temp);
					temp = null;
				}
				
				if(submission.getSecondManagerId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission Dept. Head");
					temp = new Employee();
					temp.setId(rs.getInt("e3Id"));
					temp.setFirstName(rs.getString("e3Fn"));
					temp.setLastName(rs.getString("e3Ln"));
					temp.setTitle(rs.getString("e3Title"));
					
					submission.setSecondManager(temp);
					temp = null;
				}
				
				if(submission.getBenCoId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission BenCo");
					temp = new Employee();
					temp.setId(rs.getInt("e4Id"));
					temp.setFirstName(rs.getString("e4Fn"));
					temp.setLastName(rs.getString("e4Ln"));
					temp.setTitle(rs.getString("e4Title"));
					
					submission.setSecondManager(temp);
					temp = null;
				}
				
				submissions.add(submission);

				log.trace("Adding submission to LinkedList");
			}
		} catch(Exception e) {
			LogUtil.logException(e, EmployeeOracle.class);
		}
		return submissions;
	}

	@Override
	public List<Submission> getSubmissionsByManager(int id) {
		List<Submission> submissions = new LinkedList<Submission>();
		log.trace("Retrieving submissions from database, with given employee as manager.");
		try (Connection conn = cu.getConnection()) {
			//String sql = "select id, f_name, l_name, title, login, pw, super, from employee where login=? AND pw=?";

			String sql = "" +
					"select sub.id subId, sub.name subName, sub.typeid subTypeId, sub.amount subAmount, sub.datetime subDT,"
					+" sub.submitter subSub, sub.firstmanager subFirstMan, sub.secondmanager subSecondMan, sub.benco subBen,"
					+" sub.status subSta, sub.info subInfo, dept.name deptName, subtypet.name subTName, subtypet.returnpercent subTReturnPerc,"
					+" sub.locationSite subLocS, sub.gradingType subGradeT, sub.workJust subWorkJust, sub.timeMissed subTimeM,"
					+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title, e1.rtotal e1Rtotal,"
					+" e2.id e2Id, e2.f_name e2Fn, e2.l_name e2Ln, e2.title e2Title,"
					+" e3.id e3Id, e3.f_name e3Fn, e3.l_name e3Ln, e3.title e3Title,"
					+" e4.id e4Id, e4.f_name e4Fn, e4.l_name e4Ln, e4.title e4Title"
					+" from (((((submission sub join employee e1 on (sub.submitter = e1.id) join deptartment dept on (e1.dept = dept.id))"
					+" Left Outer Join employee e2 on (sub.firstmanager = e2.id))Left Outer Join employee e3 on (sub.secondmanager = e3.id))"
					+" Left Outer Join employee e4 on (sub.benco = e4.id)) Left Outer Join submissiontypes subTypeT on (sub.typeid = subTypeT.id))"
					+" where (sub.firstmanager=?) OR (sub.secondmanager=?) OR (sub.benco=?)";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			pstm.setInt(2, id);
			pstm.setInt(3, id);
			
			ResultSet rs = pstm.executeQuery();
			Submission submission = null;
			Employee temp = null;
			while(rs.next()) {
				log.trace("Found Submission:");
				submission = new Submission();
				submission.setId(rs.getInt("subId"));
				submission.setName(rs.getString("subName"));
				submission.setTypeId(rs.getInt("subTypeId"));
				submission.setType(rs.getString("subTName"));
				submission.setReimbPercent(rs.getInt("subTReturnPerc"));
				submission.setDateTimeStr(rs.getString("subDT"));
				submission.setStatus(rs.getString("subSta"));
				submission.setLocation(rs.getString("subLocS"));
				submission.setGradingTypeId(rs.getInt("subGradeT"));
				submission.setWorkJustification(rs.getString("subWorkJust"));
				submission.setTimeMissed(rs.getInt("subTimeM"));
				submission.setInfo(rs.getString("subInfo"));
				submission.setSubmitterId(rs.getInt("subSub"));
				submission.setFirstManagerId(rs.getInt("subFirstMan"));
				submission.setSecondManagerId(rs.getInt("subSecondMan"));
				submission.setBenCoId(rs.getInt("subBen"));
				
				
				try {//An example of converting the input string into a DT
					submission.setDateTimeDt((LocalDateTime.parse(submission.getDateTimeStr(), format)));
				    log.trace("Converting Date Time");
				}
				catch (DateTimeParseException exc) {
					log.trace("Converting string into LocalDateTime failed.");
				}
				
				if(submission.getSubmitterId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission creator");
					temp = new Employee();
					temp.setId(rs.getInt("e1Id"));
					temp.setFirstName(rs.getString("e1Fn"));
					temp.setLastName(rs.getString("e1Ln"));
					temp.setTitle(rs.getString("e1Title"));
					temp.setrTotal(rs.getFloat("e1Rtotal"));
					temp.setDept(rs.getString("deptName"));
					
					submission.setSubmitter(temp);
					temp = null;
				}
				
				if(submission.getFirstManagerId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission manager");
					temp = new Employee();
					temp.setId(rs.getInt("e2Id"));
					temp.setFirstName(rs.getString("e2Fn"));
					temp.setLastName(rs.getString("e2Ln"));
					temp.setTitle(rs.getString("e2Title"));
					
					submission.setFirstManager(temp);
					temp = null;
				}
				
				if(submission.getSecondManagerId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission Dept. Head");
					temp = new Employee();
					temp.setId(rs.getInt("e3Id"));
					temp.setFirstName(rs.getString("e3Fn"));
					temp.setLastName(rs.getString("e3Ln"));
					temp.setTitle(rs.getString("e3Title"));
					
					submission.setSecondManager(temp);
					temp = null;
				}
				
				if(submission.getBenCoId() > 0) {
					//+" e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,"
					log.trace("Adding submission BenCo");
					temp = new Employee();
					temp.setId(rs.getInt("e4Id"));
					temp.setFirstName(rs.getString("e4Fn"));
					temp.setLastName(rs.getString("e4Ln"));
					temp.setTitle(rs.getString("e4Title"));
					
					submission.setSecondManager(temp);
					temp = null;
				}
				
				submissions.add(submission);

				log.trace("Adding submission to LinkedList");
			}
		} catch(Exception e) {
			LogUtil.logException(e, EmployeeOracle.class);
		}
		return submissions;
	}

	@Override
	public void updateSubmission(Submission submission) {
		// TODO Auto-generated method stub
		
	}

}
