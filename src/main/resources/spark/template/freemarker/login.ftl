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

    <h3>Login</h3>

    <form class="form-horizontal" action="/login" role="form" method="post">

        <div class="form-group">
            <div class="col-sm-10">
                <input type="text" class="form-control" name="username" id="username" placeholder="Benutzername" value="${username!}" />
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-10">
                <input type="password" class="form-control" name="password" placeholder="Passwort">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-default">Login</button>
            </div>
        </div>

	 </form>
</@layout.masterTemplate>