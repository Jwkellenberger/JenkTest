package com.jkellenberger.beans;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private String title;
	private String login;
	private String pw;
	private int managerId;
	private Employee managerObj;
	private Employee headObj;
	private int deptId;
	private String dept;
	private float rTotal;
	
	
	public int getId() {
		return id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public Employee getManagerObj() {
		return managerObj;
	}

	public void setManagerObj(Employee managerObj) {
		this.managerObj = managerObj;
	}

	public Employee getHeadObj() {
		return headObj;
	}

	public void setHeadObj(Employee headObj) {
		this.headObj = headObj;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public float getrTotal() {
		return rTotal;
	}

	public void setrTotal(float rTotal) {
		this.rTotal = rTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + deptId;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + managerId;
		result = prime * result + ((managerObj == null) ? 0 : managerObj.hashCode());
		result = prime * result + ((pw == null) ? 0 : pw.hashCode());
		result = prime * result + Float.floatToIntBits(rTotal);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (deptId != other.deptId)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (managerId != other.managerId)
			return false;
		if (managerObj == null) {
			if (other.managerObj != null)
				return false;
		} else if (!managerObj.equals(other.managerObj))
			return false;
		if (pw == null) {
			if (other.pw != null)
				return false;
		} else if (!pw.equals(other.pw))
			return false;
		if (Float.floatToIntBits(rTotal) != Float.floatToIntBits(other.rTotal))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", title=" + title
				+ ", login=" + login + ", dept=" + dept + ", rTotal=" + rTotal + "]";
	}
	
	
	
//			--create table employee (
//			--    id number(10) primary key,
//			----    f_name varchar2(20) default "" not null,
//			----    l_name varchar2(20) default "" not null,
//			--    title varchar2(20) not null check(length(login)>=2),
//			--    login varchar2(20) unique not null check(length(login)>=2),
//			--    pw varchar2(20) not null check(length(password)>=2),
//			--    super number(10),
//			--    dept number(10),
//			--    rTotal number(6) default 750,
}
