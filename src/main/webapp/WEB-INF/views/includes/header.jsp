<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="float:left;">
    <a href="/">
    <img src="<c:url value="/images/cineasts.png"/>" width="50%"/>
    </a>
</div>
<!--%@ include file="/WEB-INF/views/includes/login.jsp" %-->
<div style="float:right;">
<%@ include file="/WEB-INF/views/includes/user.jsp" %>
<%@ include file="/WEB-INF/views/includes/search.jsp" %>
</div>
<div style="clear:both;"></div>
