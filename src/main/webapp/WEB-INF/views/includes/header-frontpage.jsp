<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="header">
	<div id="header-topbar">
	    <div id="header-menu">
			<%@ include file="/WEB-INF/views/includes/search.jsp" %>
			<%@ include file="/WEB-INF/views/includes/navigation.jsp" %>
		</div>
	</div>
	<div id="header-splash">
		<img id="logo-splash" src="<c:url value="/images/logo-splash.png"/>" />
	</div>
</div>