<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="">
	<c:if test="${param.empId!=null && param.empid!=null}">
		<c:set var="empId" value="${param.empId }" />
		<c:set var="empid" value="${param.empid }" />
		<jsp:useBean id="bDao" class="com.java.LMSProjectJstl.EmployDaoImp" />
		<c:set var="employ" value="${bDao.SearchEmploy(empId) }" />
		<c:set var="employee" value="${bDao.SearchEmploy(empid) }" />
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

			</tr>
			<tr>
				<td>${employ.empId}</td>
				<td>${employ.name}</td>
				<td>${employ.mailId}</td>
				<td>${employ.mobNo}</td>
				<td>${employ.joinDte}</td>
				<td>${employ.dept}</td>
				<td>${employ.manager}</td>
				<td>${employ.leaveAvail}</td>
			</tr>
		</table>
		<center>
		&nbsp;
		&nbsp;
		&nbsp;
		<h3>EMPLOYEE MANAGER</h3>
		&nbsp;
		&nbsp;
		&nbsp;
		</center>
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
			</tr>

			<tr>
				<td>${employee.empId}</td>
				<td>${employee.name}</td>
				<td>${employee.mailId}</td>
				<td>${employee.mobNo}</td>
				<td>${employee.joinDte}</td>
				<td>${employee.dept}</td>
				<td>${employee.manager}</td>
				<td>${employee.leaveAvail}</td>
			</tr>
		</table>
	</c:if>

	<center>
	<h4>Applied Leaves</h4>
	<jsp:include page="ShowLeaves.jsp"></jsp:include>
	<h4>Pending Leaves</h4>
	<jsp:include page="Pending.jsp"></jsp:include>
	</center>
	</form>
</body>
</html>
