<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.incra.domain.enums.QuestionType" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="top" style="width: 790px; background: #EDEDEE; margin: 0px auto; font-size: 16pt; padding: 5px;"> 
  <b>Leaderboard</b>
</div>

<div style="border: 1px solid gray; width: 300px">
 <c:forEach var="leaderboardEntry" items="${leaderboardEntries}" >
   <div style="margin: 5px;">
      <div style="float:left">
        <a href="<c:url value="/comparison/${leaderboardEntry.user.id}" />">${leaderboardEntry.user.firstName} ${leaderboardEntry.user.lastName}</a>
      </div>
      <div style="float:right">
        ${leaderboardEntry.score}
      </div>
      <div style="clear:both"></div>
   </div>
 </c:forEach>
</div>