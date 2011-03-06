<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<h2>Movies</h2>

<c:if test="${not empty movies}">
<dl class="listings">
<c:forEach items="${movies}" var="movie">
	<dt>
		<a href="/movies/${movie.id}"><c:out value="${movie.title}" /></a><br/>
	</dt>
	<dd>
		<c:out value="${movie.description}" escapeXml="true" />
	</dd>	
</c:forEach>
</dl>
</c:if>
