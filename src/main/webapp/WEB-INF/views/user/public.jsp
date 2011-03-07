<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="org.neo4j.movies.domain.User"--%>
<c:set var="name" value="${profiled.name}"/>
<html>
<head><title>Profile Page of ${name}</title></head>
<body>
<h3>This is the Profile Page of ${name}</h3>

<c:choose>
    <c:when test="${isFriend}">
        <p>If you are a friend of ${name}, you can see all the friends:</p>
        <c:set var="friends" value="${profiled.friends}"/>
        <c:choose>
            <c:when test="${not empty friends}">
                <ul>
                    <c:forEach items="${friends}" var="friend">
                        <a href="/user/${friend.login}"><c:out value="${friend.name}"/></a><br/>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                ${name} has no friends.
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        You are not a friend of ${name}
    </c:otherwise>
</c:choose>
</body>
</html>