package com.cognixia.jump;
import java.io.Serializable;

public class Worker implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6874270883806235797L;
	private static final String companyName = "Cognixia";
	private String name;
	private String department;
	
	public Worker(String name, String department) {
		super();
		this.name = name;
		this.department = department;
	}

	public static String getCompanyname() {
		return companyName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Worker [name=" + name + ", department=" + department + "]";
	}
	
}
