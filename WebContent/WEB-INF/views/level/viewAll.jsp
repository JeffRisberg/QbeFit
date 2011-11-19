<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
  .list { margin: 15px 0px; }
</style>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="levelResults">
  <div class="list">
    <table class="summary">
      <thead>
        <tr>
          <th>Level Name</th>
          <th>Points Required</th>                            
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="level" items="${levelList}" varStatus="rowCounter">          
          <tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
            <td>${level.name}</td>
            <td>${level.points}</td>
            <td>${level.description}</td>                                 
          </tr>
        </c:forEach>
        <c:if test="${empty levelList}">
          <tr>
            <td colspan="999">No levels found</td>
          </tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>

<p style="font-size: 14px; margin-bottom: 8px;">
You earn points based on the difficulty points of each activity that you complete each day, with a bonus of 20 points just for joining QbeFit!
</p>