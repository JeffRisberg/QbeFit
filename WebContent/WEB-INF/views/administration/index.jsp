<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
  .list { margin: 15px 0px; }
</style>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="administration">
  <div class="list">
    <table class="summary">
      <thead>
        <tr>
          <th>Name</th>                                  
          <th>Url</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="adminPanel" items="${adminPanelList}" varStatus="rowCounter">          
          <tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
            <td>${adminPanel.name}</td>            
            <td>${adminPanel.url}</td>                                 
          </tr>
        </c:forEach>       
      </tbody>
    </table>
  </div>
</div>
