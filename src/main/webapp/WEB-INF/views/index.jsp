<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Cineasts.net</title></head>
<body>
<div style="float:left;">
<img src="<c:url value="/images/cineasts.png"/>" width="50%"/>
</div>
<div style="float:right;">
<form action="/movies" method="get">
    <input type="text" name="q"/>
</form>
</div>
<h2>Cineasts.net</h2>

</body>
</html>