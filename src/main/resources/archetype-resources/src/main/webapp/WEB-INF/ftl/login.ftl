<#import "/spring.ftl" as spring />
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
<head>
<title>Login</title>

<link rel="stylesheet" type="text/css" href="<@spring.url "/resources/css/bootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<@spring.url "/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet" type="text/css" href="<@spring.url "/resources/css/style.css"/>" />

<style type="text/css">
	body {
    	padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
	}
</style>

<script src="http://code.jquery.com/jquery.js"></script>
<script src="<@spring.url "/resources/js/bootstrap.min.js"/>"> </script>

<body>
	<div class="container">
		<form class="form-signin" name="f" action="j_spring_security_check" method="POST">
			<h2 class="form-signin-heading">Please sign in</h2>
		    <#if RequestParameters.login_error?exists>
				<span class="error-message">Wrong login or password!</span>
			</#if>
		    <input name='j_username' type="text" placeholder="Login" class="input-block-level">
		    <input name='j_password' type="password" placeholder="Password" class="input-block-level">
			<button type="submit" class="btn btn-large btn-primary">Sign in</button>
		</form>
	</div>
</body>
</html>