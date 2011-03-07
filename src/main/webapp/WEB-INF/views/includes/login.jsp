<div style="float:left">
<div id="login-error">${error}</div>

<form action="/j_spring_security_check" method="post" >
<label for="j_username">Login:</label> <input id="j_username" name="j_username" type="text"
        <c:if test="${not empty param.login_error}">value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"</c:if>
        />
<label for="j_password">Password:</label> <input id="j_password" name="j_password" type="password" />
<input type="checkbox" name="_spring_security_remember_me"/> Remember me
<input  type="submit" value="Login"/>
</form>
</div>