<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="org.neo4j.movies.domain.User"--%>
<c:set var="name" value="${profiled.name}"/>
<html>
  <head><title>Profile for ${name}</title></head>
  <body>

    <div class="span-5">
      <div class="profile-header">
        <div class="profile-image"><img src="<c:url value="/images/profile-placeholder.png" />" /></div>
        <div class="profile-header-details">          
          <h2>${name}</h2>
          <p>User description goes here.</p>
        </div>
        <div class="break"></div>
      </div>

      <h2>${name}'s friends</h2>
      <c:choose>
        <c:when test="${isFriend}">
          <c:set var="friends" value="${profiled.friends}"/>
          <c:choose>
            <c:when test="${not empty friends}">
              <ul class="friends-list">
                <c:forEach items="${friends}" var="friend">
                  <li>
                    <a class="friend-image" href="<c:url value="/user/${friend.login}" />"><img src="<c:url value="/images/profile-placeholder-small.png" />" /></a>
                    <div class="friend-info">                    
                      <h3><a href="<c:url value="/user/${friend.login}" />"><c:out value="${friend.name}"/></a></h3>
                      <p>Description of friend</p>
                    </div>
                    <div class="break"></div>
                  </li>
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
    </div>
    <div class="span-7 last">
      <div class="profile-feed">
        <div class="span-third">
          <h2>Rated movies</h2>
        </div>
        <div class="span-third last">
          <h2>12</h2>
        </div>
        <ul class="rated-movies-list span-all last">
            <c:set var="ratings" value="${profiled.ratings}"/>
            <c:choose>
                <c:when test="${not empty ratings}">
                        <c:forEach items="${ratings}" var="rating">
                            <c:set var="movie" value="${rating.movie}"/>
          <li>
              <h3><a href="<c:url value="/movies/${movie.id}" />"><c:out value="${movie.title}"/> (${movie.year}) ${rating.stars} Stars - &quot;${rating.comment}&quot;</a></h3>
              <ul class="rating">
                <!-- Add a loop here -->
                <c:set var="stars" value="${rating.stars}"/>
                <li class="${stars > 0 ? "active" : "disabled"}"></li>
                <li class="${stars > 1 ? "active" : "disabled"}"></li>
                <li class="${stars > 2 ? "active" : "disabled"}"></li>
                <li class="${stars > 3 ? "active" : "disabled"}"></li>
                <li class="${stars > 4 ? "active" : "disabled"}"></li>
              </ul>
          </li>
                        </c:forEach>
                </c:when>
                <c:otherwise>
                    ${name} has not rated any movies.
                </c:otherwise>
            </c:choose>
        </ul>
        <div class="break"></div>
      </div>
    </div>
  </body>
</html>
