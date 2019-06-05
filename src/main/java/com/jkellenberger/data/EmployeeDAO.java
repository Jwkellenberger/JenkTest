package com.jkellenberger.data;

import java.util.List;

import com.jkellenberger.beans.Employee;

public interface EmployeeDAO {
	/**
	 * Inserts an User into the database.
	 * 
	 * @param User the User object to be inserted
	 */
	public int addEmployee(Employee employee);
	
	/**
	 * returns a user object from the database
	 * 
	 * @param emp the user objected holding the id of the employee to be retrieved
	 * @return the Employee from the database that matches the id,
	 * an empty object if no Employee with said id exists.
	 */
	public Employee getEmployee(int id);
	public Employee getEmployee(Employee employee);
	
	/**
	 * returns a bool is username is from the database
	 * 
	 * @param emp the user objected holding the id of the employee to be retrieved
	 * @return the Employee from the database that matches the id,
	 * an empty object if no Employee with said id exists.
	 */
	public Boolean loginInDataBase(String login);
	
	/**
	 * returns a list of all Employees in database
	 * 
	 * @return list of Employees in database
	 */
	public List<Employee> getEmployees();
	
	/**
	 * deletes a Employee from the database
	 * 
	 * @param Employee the Employee to be deleted
	 */
	public void deleteEmployee(Employee employee);
	
	/**
	 * updates a Employee in the database
	 * 
	 * @param Employee the Employee to be updated
	 */
	public void updateEmployee(Employee employee);
}
