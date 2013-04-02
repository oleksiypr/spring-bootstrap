<#import "/spring.ftl" as spring />
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><#if titleString?exists>${titleString!}</#if></title>
<link rel="stylesheet" type="text/css" href="<@spring.url "/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet" type="text/css" href="<@spring.url "/resources/css/style.css"/>" />

<script src="http://code.jquery.com/jquery.js"></script>
<script src="<@spring.url "/resources/js/bootstrap.min.js"/>"> </script>

<#if customScript?exists>${customScript}</#if>
</head>

<body>
	${pageInnards}
</body>
</html>