package com.cognixia.jump;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

	public static void main(String[] args) {

		List<Emplyee> employees = new ArrayList<Emplyee>();
		employees.add(new Emplyee("Chad", "Developer"));
		employees.add(new Emplyee("Carol", "Sales"));
		employees.add(new Emplyee("Mark", "Management"));
		for(Emplyee s: employees) {
			System.out.println(s);
		}
	}

}
