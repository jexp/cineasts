<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="org.neo4j.movies.domain.User"--%>
<html>
<head><title>Profile</title></head>
<body>
<h3>Hi, ${user.name},</h3>

<div>You can update your preferences and details here.<br/>
    Have fun.

    <c:set var="friends" value="${user.friends}"/>
    <c:choose>
        <c:when test="${not empty friends}">
            <h4>Your friends,</h4>
            <ul>
                <c:forEach items="${friends}" var="friend">
                    <li><a href="/user/${friend.login}"><c:out value="${friend.name}"/></a></li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <h3>Add some Friends</h3>
            <div>Here are some recommendationss:</div>
        </c:otherwise>
    </c:choose>
    <c:set var="ratings" value="${user.ratings}"/>
    <c:choose>
        <c:when test="${not empty ratings}">
            <h4>Your rated Movies,</h4>
            <ul>
                <c:forEach items="${ratings}" var="rating">
                    <c:set var="movie" value="${rating.movie}"/>
                    <li><a href="/movies/${movie.id}"><c:out value="${movie.title}"/> (${movie.year}) ${rating.stars} Stars - &quot;${rating.comment}&quot;</a></li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <h3>Recommended Movies</h3>
            <div>Here are some movie recommendations for you:</div>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>