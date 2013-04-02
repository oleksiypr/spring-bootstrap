<#import "/spring.ftl" as spring />
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />

<#assign titleString>Index</#assign>

<#assign content>
<h1>Non secured page</h1>
<p>Welcome!</p> 
</#assign>

<#include "wrapper.ftl" />