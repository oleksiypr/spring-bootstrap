<!DOCTYPE html>

<head>
<script src="http://code.jquery.com/jquery.js"></script>
<script src = "../resources/user.js" type="text/javascript"></script>
</head> 

<html>
    <head>
        <title>User</title>
    </head>
    
    <body>
        <h1>User:</h1>     	
     	<div></div>
     	
     	<input id="userId" type="hidden" size="40" value="${user.id}"/><br/>
     	<input id="userName" type="text" size="40" value="${user.name}"/><br/>
     	<input id="userAddress" type="text" size="40" value="${user.address}"/><br/>
		<button>Edit</button><br/>
		
        <a href="../logout">Logout</a>        
    </body>
</html>
