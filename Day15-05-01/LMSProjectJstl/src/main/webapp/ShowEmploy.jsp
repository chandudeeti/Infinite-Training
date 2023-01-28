<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="employeeDao"
		class="com.java.LMSProjectJstl.EmployDaoImp" />
	<c:set var="employeeList" value="${employeeDao.showEmployDao()}" />
	<table border="3" align="center">
		<tr>
			<th>EmpID</th>
			<th>Name</th>
			<th>mailId</th>
			<th>mobNo</th>
			<th>joinDte</th>
			<th>dept</th>
			<th>manager</th>
			<th>leaveAvail</th>
			<td>Details</td>
		</tr>
		<c:forEach var="employ" items="${employeeList}">
			<tr>
				<td>${employ.empId}</td>
				<td>${employ.name}</td>
				<td>${employ.mailId}</td>
				<td>${employ.mobNo}</td>
				<td>${employ.joinDte}</td>
				<td>${employ.dept}</td>
				<td>${employ.manager}</td>
				<td>${employ.leaveAvail}</td>
				<td><a href="ShowDetails.jsp?empId=${employ.empId}&empid=${employ.manager}">Show Details</a>
				<td><a href="ApplyLeave.jsp?empId=${employ.empId}">Apply Leave</a> </td>
				
				<%-- <td><a href="ShowLeaves.jsp?empId=${employ.empId}">Leave History</a> </td>--%>
				 
				
			
			</tr>
			
		</c:forEach>
		</table>
