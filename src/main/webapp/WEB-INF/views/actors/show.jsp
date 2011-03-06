<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:out value="${actor}"/>
<c:choose>
    <c:when test="${actor != null}">
        <h2>${actor.name}</h2>
        <c:if test="${not empty actor.roles}">
        <ul>
        <c:forEach items="${actor.roles}" var="role">
            <li>
                <a href="/movies/${role.movie.id}"><c:out value="${role.movie.title}" /> as <c:out value="${role.name}" /> in <c:out value="${role.movie.year}"/></a><br/>
            </li>
        </c:forEach>
        </ul>
        </c:if>
    </c:when>
    <c:otherwise>
        No actor with id ${id} found!
    </c:otherwise>
</c:choose>