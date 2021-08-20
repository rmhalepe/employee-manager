package com.cognixia.jump;
import java.io.Serializable;

public class Emplyee extends Worker implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2210450860734912264L;
	private static int idCounter = 0;
	private int id;

	
	public Emplyee(String name, String department) {
		super(name, department);
		id = idCounter++;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + super.getName() + ", department=" + super.getDepartment() + "]";
	}
	
	public int getId() {
		return id;
	}
	
}
