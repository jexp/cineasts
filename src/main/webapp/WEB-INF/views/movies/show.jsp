<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${not empty movie}">
      <div class="span-5">
        <div class="profile-header">
          <div class="profile-image"><img src="<c:url value="/images/movie-placeholder.png" />" /></div>
          <div class="profile-header-details">          
            <h2>${movie.title} (${movie.year})</h2>
            <ul class="rating">
              <!-- Add a loop here -->
              <li class="active"></li>
              <li class="active"></li>
              <li class="active"></li>
              <li class="disabled"></li>
              <li class="disabled"></li>
            </ul>
          </div>
          <div class="break"></div>
        </div>

        <div class="span-half">
          <h3>Movie facts</h3>

          <table>
            <tr>
              <th>Status</th>
              <td>Released</td>
            </tr>
            <tr>
              <th>Runtime</th>
              <td>2h 12m</td>
            </tr>
            <tr>
              <th>Etc</th>
              <td>etc</td>
            </tr>
          </table>
        </div>

        <div class="span-half last">
          <h3>Trailers</h3>

          <ul>
            <li><a href="#">Some trailer</a></li>
          </ul>
        </div>

        <div class="span-half">
          <h3>Release info</h3>

          <ul>
            <li>
              <h4>USA</h4>
              <table>
                <tr>
                  <th>Released</th>
                  <td>1994-06-23</td>
                </tr>
                <tr>
                  <th>Runtime</th>
                  <td>2h 12m</td>
                </tr>
                <tr>
                  <th>Etc</th>
                  <td>etc</td>
                </tr>
              </table>
            </li>
          </ul>
        </div>

      </div>
      <div class="span-7 last">
        <div class="movie-content-outer">
          <div class="movie-content">
            <h2>Overview</h2>
            <p>Lorem ipsum dolor sit amet.</p>

            <h2>Cast</h2>
            <c:if test="${not empty movie.roles}">
              <ul class="actors-list">
                <c:forEach items="${movie.roles}" var="role">
                    <li>
                        <a class="actor-image" href="<c:url value="/actors/${role.actor.id}" />"><img src="<c:url value="/images/profile-placeholder-small.png" />" /></a>
                        <a href="<c:url value="/actors/${role.actor.id}" />"><c:out value="${role.actor.name}" /> as <c:out value="${role.name}" /></a>
                    </li>
                </c:forEach>
              </ul>
              <div class="break"></div>
            </c:if>
            
            <h2>Reviews</h2>
            <c:if test="${not empty movie.ratings}">
              <ul>
                <c:forEach items="${movie.ratings}" var="rating">
                  <li>
                    <h4>${rating.stars} stars by <a href="<c:url value="/user/${rating.user.login}" />">${rating.user.name}</a></h4>
                    <p>${rating.comment}</p>
                  </li>
                </c:forEach>
              </ul>
            </c:if>
            
            <c:if test="${not empty user}">
              <form method="post" action="<c:url value="/movies/${movie.id}" />">
                  <ul class="rating-input">
                    <lh>My rating</lh>
                    <li>
                      <input id="rating-one" type="radio" name="rated" value="1" />
                      <label class="button-label" for="rating-one">1</label>
                    </li>
                    <li>
                      <input id="rating-two" type="radio" name="rated" value="2" />
                      <label class="button-label" for="rating-two">2</label>
                    </li>
                    <li>
                      <input id="rating-three" type="radio" name="rated" value="3" selected="selected" />
                      <label class="button-label" for="rating-three">3</label>
                    </li>
                    <li>
                      <input id="rating-four" type="radio" name="rated" value="4" />
                      <label class="button-label" for="rating-four">4</label>
                    </li>
                    <li>
                      <input id="rating-five" type="radio" name="rated" value="5" />
                      <label class="button-label" for="rating-five">5</label>
                    </li>
                  </ul>
                  <textarea rows="3" cols="60" name="comment"></textarea>
                  <div class="break"></div>
                  <input type="submit" value="Submit review" />
              </form>
            </c:if>
          </div>
        </div>
      </div>
    </c:when>
    <c:otherwise>
        <h2>No movie found</h2>
        <p>The movie you were looking for could not be found.</p>
    </c:otherwise>
</c:choose>
