<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="org.neo4j.movies.domain.User"--%>
<html>
  <head>
    <title>Profile</title>
  </head>
  <body>

    <div class="span-5">
      <div class="profile-header">
        <div class="profile-image"><img src="<c:url value="/images/profile-placeholder.png" />" /></div>
        <div class="profile-header-details">          
          <h2>Hi ${user.name}!</h2>
          <p>User description goes here.</p>
        </div>
        <div class="break"></div>
      </div>
      
      <c:choose>
        <c:when test="${not empty user.friends}">
          <h2>Your friends</h2>
          <ul class="friends-list">
            <c:forEach items="${user.friends}" var="friend">
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
          <h2>You don't have any friends yet</h2>
        </c:otherwise>
      </c:choose>
    </div>
    <div class="span-7 last">
      <div class="profile-feed">
        <div class="span-third">
          <h2>Your rated movies</h2>
        </div>
        <div class="span-third last">
          <h2>12</h2>
        </div>
        <ul class="rated-movies-list span-all last">
          <li>
              <h3><a href="<c:url value="/movies/1" />">Forest gump</a></h3>
              <ul class="rating">
                <!-- Add a loop here -->
                <li class="active"></li>
                <li class="active"></li>
                <li class="active"></li>
                <li class="disabled"></li>
                <li class="disabled"></li>
              </ul>
          </li>
          <li>
              <h3><a href="<c:url value="/movies/1" />">Forest gump</a></h3>
              <ul class="rating">
                <!-- Add a loop here -->
                <li class="active"></li>
                <li class="active"></li>
                <li class="active"></li>
                <li class="disabled"></li>
                <li class="disabled"></li>
              </ul>
          </li>
        </ul>
        <div class="break"></div>
      </div>
    </div>
  </body>
</html>
