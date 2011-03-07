<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:out value="${movie}"/>
<c:choose>
    <c:when test="${not empty movie}">
        <h2>${movie.title} (${movie.stars} Stars)</h2>

        <c:if test="${not empty user}">
        <div>
            <form method="post" action="/movies/${movie.id}">
                <div>
                <input type="image" src="/images/star.png" name="rated" value="1"/>
                <input type="image" src="/images/star.png" name="rated" value="2"/>
                <input type="image" src="/images/star.png" name="rated" value="3"/>
                <input type="image" src="/images/star.png" name="rated" value="4"/>
                <input type="image" src="/images/star.png" name="rated" value="5"/>
                </div>
                <textarea rows="3" cols="60" name="comment"></textarea>
            </form>
        </div>
        </c:if>
        <c:if test="${not empty movie.roles}">
        <ul>
        <c:forEach items="${movie.roles}" var="role">
            <li>
                <a href="/actors/${role.actor.id}"><c:out value="${role.actor.name}" /> as <c:out value="${role.name}" /></a><br/>
            </li>
        </c:forEach>
        </ul>
        </c:if>
        <c:if test="${not empty movie.ratings}">
        <dl>
        <c:forEach items="${movie.ratings}" var="rating">
            <dt>${rating.stars} Stars by ${rating.user.name}</dt>
            <dd>Comment: ${rating.comment}</dd>
        </c:forEach>
        </dl>
        </c:if>
    </c:when>
    <c:otherwise>
        No Movie with id ${id} found!
    </c:otherwise>
</c:choose>