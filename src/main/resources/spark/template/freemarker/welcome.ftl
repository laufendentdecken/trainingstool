<#import "masterTemplate.ftl" as layout />

<@layout.masterTemplate title="Sign In">
    <#if message??>
    	<div class="alert alert-success">
    		${message}
    	</div>
    </#if>
    <#if error??>
    	<div class="alert alert-danger">
    		<strong>Error:</strong> ${error}
    	</div>
    </#if>


    <center><h2>Willkommen</h2></center>

    </@layout.masterTemplate>