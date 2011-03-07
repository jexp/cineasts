<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="org.neo4j.movies.domain.User"--%>
<html>
<head><title>Profile</title></head>
<body>
<h3>Hi, ${user.name},</h3>

<div>You can update your preferences and details here.<br/>
    Have fun.

    <c:choose>
        <c:when test="${not empty user.friends}">
            <h4>Your friends,</h4>
            <ul>
                <c:forEach items="${user.friends}" var="friend">
                    <a href="/user/${friend.login}"><c:out value="${friend.name}"/></a><br/>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <h3>Add some Friends</h3>
            <div>Here are some recommendationss:</div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>