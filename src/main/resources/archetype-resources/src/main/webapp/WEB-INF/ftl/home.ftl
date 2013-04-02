<#import "/spring.ftl" as spring />

<#assign titleString>Home</#assign>

<#assign content>
<h1>Welcome to the Home Page</h1>
<p>New User Persisted! Please check User page.</p> 
<p>${user}</p>
</#assign>

<#include "wrapper.ftl" />