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
	
	<c:if test="${param.empId!=null}">
	<c:set var="empId" value="${param.empId}"/>
	<jsp:useBean id="lDao" class="com.java.LMSProjectJstl.LevHistoryDaoImp"/>
	<c:set var="lev" value="${lDao.searchLeaveHistory(empId) }"/>
	
	<table border="3" align="center">
		<tr>
 			<th>LeaveId</th>
 			<th>EmpId</th>
			<th>Leave Toatl Days</th>
			<th>Manager Comments</th>
			<th>Leave Start Date</th>
			<th>Leave End Date</th>
			<th>Leave Type</th>
			<th>Leave Status</th>
			<th>Leave Reason</th>
			
			
		</tr>
		<c:forEach var="lev" items="${lev}">
			<tr>
				<td>${lev.leaveId}</td>
				<td>${lev.empId}</td>
 				<td>${lev.noOfDays}</td>
				<td>${lev.levMngCmts}</td>
				<td>${lev.stDate}</td>
				<td>${lev.endDate}</td>
				<td>${lev.leaveTyp}</td>
				<td>${lev.status}</td>				
				<td>${lev.levReason}</td>				
			</tr>
			
				</c:forEach>
		</c:if>
		
		
		</table>
		</body>
</html>
