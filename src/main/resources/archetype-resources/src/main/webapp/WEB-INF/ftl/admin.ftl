<!DOCTYPE html>
<html>
<head>
	<title>Admin page</title>
</head>

<body>
	<h1>Welcome to the Admin Page</h1>
	<p>Admin can see all the users:</p>
	
	<table>
	<#list users as u>
		<tr>
			<td>${u.id}</td>
			<td>${u.name}</td>
			<td>${u.address}</td>
		</tr>
	</#list>
	</table>
	
	<a href="logout">Logout</a>
</body>
</html>