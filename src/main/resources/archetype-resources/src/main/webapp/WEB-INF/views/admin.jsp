#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin page</title>
</head>

<body>
	<h1>Welcome to the Admin Page</h1>
	<p>Admin can see all the users:</p>
	
	<table>
	<c:forEach var="u" items="${symbol_dollar}{users}">
		<tr>
			<td>${symbol_dollar}{u.id}</td>
			<td>${symbol_dollar}{u.name}</td>
			<td>${symbol_dollar}{u.address}</td>
		</tr>
	</c:forEach>
	</table>
	
	<a href="j_spring_security_logout">Logout</a>
</body>
</html>