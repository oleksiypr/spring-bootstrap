<#import "/spring.ftl" as spring />

<#assign titleString>User</#assign>
<#assign customScript>
	<script src="<@spring.url "/resources/js/user.js"/>"></script>
</#assign>

<#assign content>
<h1>User:</h1>     	
<div id="editResult"></div>
     	
<input id="userId" type="hidden" size="40" value="${user.id}"/><br/>
<input id="userName" class="input-block-level" type="text" size="40" value="${user.name}"/><br/>
<input id="userAddress" class="input-block-level" type="text" size="40" value="${user.address}"/>
<a class="btn btn-primary btn-large" id="editUser">Edit</a><br/>
</#assign>

<#include "wrapper.ftl" />        

