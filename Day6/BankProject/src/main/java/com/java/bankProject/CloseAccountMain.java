package com.java.bankProject;

import java.sql.SQLException;
import java.util.Scanner;

public class CloseAccountMain 
{

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		Bank bank = new Bank();
		int accountNo;
		System.out.println("Enter AccountNo..");
		accountNo = sc.nextInt();
		BankDAO dao= new BankDaoImpl();
		try 
		{
			System.out.println(dao.closeAccount(accountNo));
			System.out.println(bank.getStatus());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		sc.close();
	}
}