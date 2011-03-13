<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Results for &quot;${query}&quot;</h2>

<c:choose>
  <c:when test="${not empty movies}">
    <ul class="search-results">
      <c:forEach items="${movies}" var="movie">
        <li>
          <a class="thumbnail" href="<c:url value="/movies/${movie.id}" />"><img src="<c:url value="/images/movie-placeholder.png" />" /></a>
          <div class="search-result-details">
            <h3><a href="/movies/${movie.id}"><c:out value="${movie.title}" /></a></h3>
            <p><c:out value="${movie.description}" escapeXml="true" /></p>
          </div>
          <ul class="rating">
            <!-- Add a loop here -->
            <c:set var="stars" value="${movie.stars}"/>
            <li class="${stars > 0 ? "active" : "disabled"}"></li>
            <li class="${stars > 1 ? "active" : "disabled"}"></li>
            <li class="${stars > 2 ? "active" : "disabled"}"></li>
            <li class="${stars > 3 ? "active" : "disabled"}"></li>
            <li class="${stars > 4 ? "active" : "disabled"}"></li>
          </ul>
          <div class="break"></div>
        </li>
      </c:forEach>
    </ul>
  </c:when>
  <c:otherwise>
    <h2>No movies found for query &quot;${query}&quot;.</h2>
  </c:otherwise>
</c:choose>
