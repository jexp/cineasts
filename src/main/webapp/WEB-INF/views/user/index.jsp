<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="org.neo4j.movies.domain.User"--%>
<html>
<head><title>Profile</title></head>
<body>
<h3>Hi, ${user.name},</h3>

<p>You can update your preferences and details here.<br/>
    Have fun.
</p>
<c:choose>
<c:when test="${not empty user.friends}">
    <ul>
        <c:forEach items="${user.friends}" var="friend">
            <a href="/user/${friend.login}"><c:out value="${friend.name}"/></a><br/>
        </c:forEach>
    </ul>
</c:when>
<c:otherwise>
    </c:otherwise>
</c:choose>
</body>
</html>