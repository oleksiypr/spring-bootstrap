#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
	</head>

	<body>
		<div class="${symbol_dollar}{empty param.login_error ? 'login-form' : 'login-error-form'}">
			<c:if test="${symbol_dollar}{not empty param.login_error}">
				<span class="error-message">Wrong login or password!</span>
			</c:if>
	
			<form name="f" action="j_spring_security_check" method="POST">
				<table style="width:100%" border="0">
					<tr>
						<td colspan="2" style="text-align:center">
							<h1>Please, enter your login and password</h1>
						</td>
					</tr>				
					<tr>
						<td>Login:</td>
						<td style="text-align:right"><input type='text' name='j_username' size="15"/></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td style="text-align:right"><input type='password' name='j_password' size="15"></td>
					</tr>
					<tr>
						<td></td>
						<td style="text-align:right"><input type="submit" value="OK"></td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
