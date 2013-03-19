<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
	</head>

	<body>
		<div>
			<#if RequestParameters.login_error?exists>
				<span class="error-message">Wrong login or password!</span>
			</#if>
	
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
