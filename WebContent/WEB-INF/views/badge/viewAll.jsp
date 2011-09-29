<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
  .list { margin: 15px 0px; }
</style>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="badgeResults">
  <div class="list">
    <table class="summary">
      <thead>
        <tr>
          <th>Badge Name</th>                                  
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="badge" items="${badgeList}" varStatus="rowCounter">          
          <tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
            <td>${badge.name}</td>            
            <td>${badge.description}</td>                                 
          </tr>
        </c:forEach>
        <c:if test="${empty badgeList}">
          <tr>
            <td colspan="999">No badges found</td>
          </tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>
