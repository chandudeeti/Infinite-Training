package com.java.bankProject;

import java.sql.SQLException;
import java.util.Scanner;

public class SearchAccountMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int accountNo;
		System.out.println("Enter AccountNo  ");
		accountNo = sc.nextInt();
		BankDAO dao= new BankDaoImpl();
		try {
			Bank bank = dao.searchAccount(accountNo);
			if (bank!=null) {
				System.out.println(bank);
			} else {
				System.out.println("AccountNo Not Found...");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
	}
}
