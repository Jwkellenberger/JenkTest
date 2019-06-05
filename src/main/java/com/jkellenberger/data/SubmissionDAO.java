package com.jkellenberger.data;

import java.util.List;

import com.jkellenberger.beans.Submission;

public interface SubmissionDAO {
	/**
	 * Inserts an User into the database.
	 * 
	 * @param User the User object to be inserted
	 */
	public int addSubmission(Submission submission);
	
	/**
	 * returns a list of all Employees in database
	 * 
	 * @return list of Employees in database
	 */
	public List<Submission> getSubmissionsBySubmitter(int id);
	
	/**
	 * returns a list of all Employees in database
	 * 
	 * @return list of Employees in database
	 */
	public List<Submission> getSubmissionsByManager(int id);
	
	/**
	 * deletes a Employee from the database
	 * 
	 * @param Employee the Employee to be deleted
	 */
	//public void deleteEmployee(Employee employee);
	
	/**
	 * updates a Employee in the database
	 * 
	 * @param Employee the Employee to be updated
	 */
	public void updateSubmission(Submission submission);

}