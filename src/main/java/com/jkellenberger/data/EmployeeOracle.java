package com.jkellenberger.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.jkellenberger.beans.Employee;
import com.jkellenberger.utils.ConnectionUtil;
import com.jkellenberger.utils.LogUtil;

public class EmployeeOracle implements EmployeeDAO{
	private static Logger log = Logger.getLogger(EmployeeOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public int addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Employee getEmployee(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Employee getEmployee(Employee employee) {
		log.trace("Retrieving user from database.");
		try (Connection conn = cu.getConnection()) {
			//String sql = "select id, f_name, l_name, title, login, pw, super, from employee where login=? AND pw=?";
			String sql = "select e1.id loginId, e1.f_name loginFn, e1.l_name loginLn, e1.title loginTitle, e1.login loginLogin, e1.super loginSuper, e1.dept loginDept, e1.rtotal loginRtotal, dept.name DeptName, dept.head DeptHead, e2.id bossId, e2.f_name bossFn, e2.l_name bossLn, e2.title bossTitle, e2.login bossLogin, e2.super bossSuper, e2.dept bossDept, e2.rtotal bossRtotal, e3.id headId, e3.f_name headFn, e3.l_name headLn, e3.title headTitle, e3.login headLogin, e3.super headSuper, e3.dept headDept, e3.rtotal headRtotal from ((employee e1 left join employee e2 on (e1.super = e2.id)) join deptartment dept on (e1.dept = dept.id)) join employee e3 on (dept.head=e3.id) where e1.login =? AND e1.pw=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, employee.getLogin());
			pstm.setString(2, employee.getPw());
			log.trace(employee.getLogin()+ " " + employee.getPw());
			ResultSet rs = pstm.executeQuery();
			employee = null;
			Employee manager = null;
			Employee deptHead = null;
			if(rs.next()) {
				log.trace("Found Employee:");
				employee = new Employee();
				employee.setId(rs.getInt("loginId"));
				employee.setFirstName(rs.getString("loginFn"));
				employee.setLastName(rs.getString("loginLn"));
				employee.setTitle(rs.getString("loginTitle"));
				employee.setrTotal(rs.getFloat("loginRtotal"));
				employee.setDept(rs.getString("DeptName"));
				
				if(employee.getId() > 0 && rs.getInt("bossId") >0 && employee.getId() != rs.getInt("bossId")) {
					log.trace("Found Manager:");
					manager = new Employee();
					manager.setId(rs.getInt("bossId"));
					manager.setFirstName(rs.getString("bossFn"));
					manager.setLastName(rs.getString("bossLn"));
					manager.setTitle(rs.getString("bossTitle"));
					manager.setrTotal(rs.getFloat("bossRtotal"));
					manager.setDept(rs.getString("DeptName"));
					
					log.trace("Adding Manager: ");
					employee.setManagerObj(manager);
					//e2.id bossId, e2.f_name bossFn, e2.l_name bossLn, e2.title bossTitle, e2.login bossLogin, e2.super bossSuper, e2.dept bossDept, e2.rtotal bossRtotal, e3.i
				}
				
				if(employee.getId() > 0 && rs.getInt("headId") >0 && employee.getId() != rs.getInt("DeptHead")) {
					log.trace("Found Dept Head:");
					deptHead = new Employee();
					deptHead.setId(rs.getInt("headId"));
					deptHead.setFirstName(rs.getString("headFn"));
					deptHead.setLastName(rs.getString("headLn"));
					deptHead.setTitle(rs.getString("headTitle"));
					deptHead.setrTotal(rs.getFloat("headRtotal"));
					deptHead.setDept(rs.getString("DeptName"));
					
					log.trace("Adding Dept. Head: ");
					employee.setHeadObj(deptHead);
				}//e3.id headId, e3.f_name headFn, e3.l_name headLn, e3.title headTitle, e3.login headLogin, e3.super headSuper, e3.dept headDept, e2.rtotal bossRtotal
				
				//SubmissionOracle so = new SubmissionOracle();
				//log.trace(so.getSubmissionsByManager(4));
				//log.trace(so.getSubmissionsBySubmitter(5));
				
				log.trace(employee);
			}
		} catch(Exception e) {
			LogUtil.logException(e, EmployeeOracle.class);
		}
		return employee;
	}
	
	/*
--    id number(10) primary key,
----    f_name varchar2(20) default "" not null,
----    l_name varchar2(20) default "" not null,
--    title varchar2(20) not null check(length(login)>=2),
--    login varchar2(20) unique not null check(length(login)>=2),
--    pw varchar2(20) not null check(length(password)>=2),
--    super number(10),
--    dept number(10),
--    rTotal number(6) default 750,
	 * */
	
//	@Override
//	public int addUser(User user) {
//		int key = 0;
//		log.trace("adding user to the database: "+user);
//		Connection conn = cu.getConnection();
//		try {
//			conn.setAutoCommit(false);
//			String sql = "insert into User_t(login, password"
//					+ ") values (?,?)";
//			String[] keys = {"id"};
//			PreparedStatement pstm = conn.prepareStatement(sql, keys);
//			pstm.setString(1, user.getUsername());
//			pstm.setString(2, user.getPassword());
//			
//			pstm.executeUpdate();
//			ResultSet rs = pstm.getGeneratedKeys();
//			if(rs.next()) {
//				log.trace("User created");
////				key = rs.getInt(1);
////				user.setId(key);
//				key=1;
//				conn.commit();
//			} else {
//				log.trace("User not created!");
//				conn.rollback();
//			}
//		} catch (SQLException e) {
//			LogUtil.rollback(e, conn, UserOracle.class);
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				LogUtil.logException(e1, UserOracle.class);
//			}
//		}
//		return key;
//	}
//	
//	@Override
//	public User getUser(User user) {
//		log.trace("Retrieving user from database.");
//		try (Connection conn = cu.getConnection()) {
//			String sql = "select id, login, password, status from User_t where login=? AND password=?";
//			
//			PreparedStatement pstm = conn.prepareStatement(sql);
//			pstm.setString(1, user.getUsername());
//			pstm.setString(2, user.getPassword());
//			ResultSet rs = pstm.executeQuery();
//			user = null;
//			if (rs.next()) {
//				log.trace("user found!");
//				user = new User();
//				user.setId(rs.getInt("id"));
//				user.setStatus(rs.getInt("status"));
//				user.setUsername(rs.getString("login"));
//				user.setPassword(rs.getString("password"));
//				user.setCars(new LinkedList<Car>());
//				user.setOffers(new LinkedList<Offer>());
//				user.setPayments(new LinkedList<Payment>());
//			}
//		} catch (Exception e) {
//			LogUtil.logException(e, UserOracle.class);
//		}
//		return user;
//	}
//	
//	public Boolean usernameInDataBase(String username) {
//		Boolean bool = false;
//		log.trace("Checking if user is within the database.");
//		try (Connection conn = cu.getConnection()) {
//			String sql = "select id from User_t where login=?";
//			
//			PreparedStatement pstm = conn.prepareStatement(sql);
//			pstm.setString(1, username);
//			ResultSet rs = pstm.executeQuery();
//			if (rs.next()) {
//				log.trace("Username within the database!");
//				if(rs.getInt("id")>0) bool = true;
//			}
//		} catch (Exception e) {
//			LogUtil.logException(e, UserOracle.class);
//		}
//		return bool;
//	}
//
//	@Override
//	public List<User> getUsers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void deleteUser(User employee) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateUser(User employee) {
//		// TODO Auto-generated method stub
//		
//	}
	////////////////////////////////////////////////////////////
	

	@Override
	public Boolean loginInDataBase(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		
	}

}
