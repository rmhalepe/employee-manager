//code by Rachel Halepeska
package com.cognixia.jump;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public class EmployeeManagementSystem {
//class that creates and modifies a text file and csv file using the Emplyee class to keep track of employees
//modifications are done to the text file which holds Emplyee data
//The csv file is readable (spreadsheet) and is updated at the beginning and end with data from txt file
	public static void main(String[] args) {
		File file = new File("resources/employees.txt");
		File csv = new File("resources/eData.csv");
		//add exists first
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			if(!csv.exists()) {
			csv.createNewFile();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		//first step: read txt file and store in CSV file
		readAll(file); 
		List<String> empAsString = convertToStringList(file);
		if(empAsString != null) {
			writeCSV(csv,empAsString);
		}
		readCSV(csv);
		//list of emplyees for example
		List<Emplyee> employees = new ArrayList<Emplyee>();
		employees.add(new Emplyee("Chad", "Developer"));
		employees.add(new Emplyee("Carol", "Sales"));
		employees.add(new Emplyee("Bob", "Developer"));
		employees.add(new Emplyee("Mark", "Management"));
		employees.add(new Emplyee("Rebecca", "Management"));

		writeObjects(file, employees);
		readAll(file);
		addEmp(file, new Emplyee("Anthony", "Engineering"));
		readAll(file);
		listDepartment(file, "Developer");
		deleteEmp(file, 0);
		readAll(file);
		modifyDepartment(file, 10, "CEO");
		modifyDepartment(file, 1, "CEO");
		modifyName(file, 2, "Eliza");
		readAll(file);
		
		//last step: put changes to CSV list
		empAsString = convertToStringList(file);
		writeCSV(csv,empAsString);
		readCSV(csv);
		
	}
	
	public static void writeObjects(File file, List<Emplyee> employees) {
		//method that overwrites any previous employees in the file
		//replaces with new employee list
		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file))) {
			writer.writeObject(employees);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void addEmp(File file, Emplyee emp) {
		//method that adds to list of employees in file
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			
			@SuppressWarnings("unchecked")
			 List<Emplyee> empList = (List<Emplyee>) reader.readObject();
			empList.add(emp);
			writeObjects(file, empList);

		}catch(EOFException e) {
			// (no list on file) create new list to add to file
			System.out.println("File is currently empty; Created new list and added one employee");
			List <Emplyee> empList = new ArrayList<Emplyee>();
			empList.add(emp);
			writeObjects(file, empList);
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void deleteEmp(File file, int id) {
		//removes the employee with given id from the file
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			
			@SuppressWarnings("unchecked")
			 List<Emplyee> empList = (List<Emplyee>) reader.readObject();
			empList.removeIf(element -> element.getId() == id);
			writeObjects(file, empList);
			
		}catch(EOFException e) {
			//this means the file has no list (it reached the end of the file)
			System.out.println("File is Empty");
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void modifyDepartment(File file, int id, String department) {
		//modifies the department of the employee with given id
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			
			@SuppressWarnings("unchecked")
			 List<Emplyee> empList = (List<Emplyee>) reader.readObject();
			Emplyee toModify = empList.stream()
					.filter( empl -> empl.getId() == id )
					.findAny()  		//there will only ever be one employee with a given id
					.get();
			toModify.setDepartment(department);
			deleteEmp(file, id);
			addEmp(file, toModify);
	
		}catch(EOFException e) {
			//this means the file has no list (it reached the end of the file)
			System.out.println("File is Empty");
		}catch(NoSuchElementException e) {
			//this means the filter did not find an employee with the given id in the file 
			System.out.println("Employee with id "+id+ " does not exist.");
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void modifyName(File file, int id, String name) {
		//modifies the department and name of the employee with given id
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			
			@SuppressWarnings("unchecked")
			 List<Emplyee> empList = (List<Emplyee>) reader.readObject();
			Emplyee toModify = empList.stream()
					.filter( empl -> empl.getId() == id )
					.findAny()  		//there will only ever be one employee with a given id
					.get();
			toModify.setName(name);
			deleteEmp(file, id);
			addEmp(file, toModify);
			
		}catch(EOFException e) {
			//this means the file has no list (it reached the end of the file)
			System.out.println("File is Empty");
		}catch(NoSuchElementException e) {
			//this means the filter did not find an employee with the given id in the file 
			System.out.println("Employee with id "+id+ " does not exist.");
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void readAll(File file) {
		//prints the current list of employees on file, sorted by department (alphabetically)
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			
			System.out.println("Full Employee List: ");
			@SuppressWarnings("unchecked")
			List<Emplyee> empList = (List<Emplyee>) reader.readObject();
			empList.stream()
			.sorted(Comparator.comparing(Emplyee::getDepartment))
			.forEach(System.out::println);
			
		}catch(EOFException e) {
			//this means the file has no list (it reached the end of the file)
			System.out.println("File is Empty");
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void listDepartment(File file, String department) {
		//prints all the employees in a certain department
	
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			
			System.out.println("Employees in "+department+ " department:");
			@SuppressWarnings("unchecked")
			List<Emplyee> empList = (List<Emplyee>) reader.readObject();
			empList.stream()
			.filter(empl -> empl.getDepartment().equals(department))
			.forEach(System.out::println);
								
		}catch(EOFException e) {
			//this means the file has no list (it reached the end of the file)
			System.out.println("File is Empty");
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static List<String> convertToStringList(File file){
		//reads the Employee list in file and returns as a string list
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			
			@SuppressWarnings("unchecked")
			List<Emplyee> empList = (List<Emplyee>) reader.readObject();
			List<String> toReturn = new ArrayList<String>();
			for(Emplyee emp: empList) {
				//format for csv
				toReturn.add(emp.getName()+", "+ emp.getDepartment()+", " +emp.getId());
			}
			return toReturn;

		}catch(EOFException e) {
			//this means the file has no list (it reached the end of the file)
			System.out.println("File is Empty");
			return null;
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}	
	}
	public static void readCSV(File csv) {
		try {
		FileReader read = new FileReader(csv);
		BufferedReader csvRead = new BufferedReader(read);
		System.out.println("CSV file: ");
		String row;
		while ((row = csvRead.readLine()) != null) {
		  System.out.println(row);
		}
		csvRead.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeCSV(File csv, List<String> toWrite) {
		try {
			PrintWriter printer = new PrintWriter(csv);
			printer.append("Name");
			printer.append(", ");
			printer.append("Department");
			printer.append(", ");
			printer.append("ID");
			printer.append("\n");
			for(String emp: toWrite) {
				printer.append(emp);
				printer.append("\n");
			}
			printer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
