package com.java.employeePaySlip;

import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EmployeeDaoImp implements EmployeeDAO{

	static List<Employee> employeeList;
	static
	{
		employeeList = new ArrayList<Employee>();
	}

	public int generateEmployeeId()
	{
		int empId = 0;
		int count = employeeList.size();
		if(count == 0)
		{
			return 1;
		}
		else
		{
			empId = employeeList.get(employeeList.size()-1).getEmpId();
			count++;
		}
		empId = count;
		return empId;
	}

	@Override
	public String addEmployeeDao(Employee employee) {
		//	        employee = new Employee();
		
		int id = generateEmployeeId();
		employee.setEmpId(id);
		employee.setHra(employee.getSalary()*0.03);
		employee.setDa(employee.getSalary()*0.12);
		employee.setTa(employee.getSalary()*0.01);
		employee.setTax(employee.getSalary()*0.21);
		employee.setPf(employee.getSalary()*0.23);
//		takeHome = employee.getGross() - employee.getTax() - employee.getPf();
//		employee.setTakeHome(takeHome);
		employee.setGross(employee.getSalary()+employee.getHra()+employee.getDa()+employee.getTa());
		employeeList.add(employee);

		return "Employee added successfully";
	}

	@Override
	public List<Employee> showEmployee() {


		return employeeList;
	}
	public Employee searchEmployee(int empId)
	{
		Employee employee1 = null;
		for(Employee employee : employeeList)
		{
			if(empId == employee.getEmpId())
			{
				employee1 = employee;
				break;
			}
		}

		return employee1;
	}

	@Override
	public String writeEmployeeFileDao() throws IOException {
		FileOutputStream fOut = new FileOutputStream("D:/Files/EmployPaySlip.txt");
		ObjectOutputStream outObj = new ObjectOutputStream(fOut);
		outObj.writeObject(employeeList);
		outObj.close();
		fOut.close();
		
		
		return "File created successfully...";
	}

	@Override
	public String readEmployeeFileDao() throws IOException, ClassNotFoundException {
		FileInputStream fIn = new FileInputStream("D:/Files/EmployPaySlip.txt");
		ObjectInputStream objOut = new ObjectInputStream(fIn);
		employeeList = (List<Employee>)objOut.readObject();
		fIn.close();
		objOut.close();
		return "File read Successfull...";
	}



}
