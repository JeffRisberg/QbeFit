<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<c:url var="saveUrl" value="/activity/selectUpdate"/>
<form:form method="post" action="${saveUrl}">
<div id="activityResults">
	<div class="list">
		<table class="summary">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>Name</th>	
					<th>Category</th> 
					<th>Points</th>  									
					<th>Description</th>					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="activity" items="${activityList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
					  <td>
              <c:if test="${selectedList[rowCounter.count-1]}">
                <input type="checkbox" name="activity_${activity.id}" checked />
              </c:if>
              <c:if test="${!selectedList[rowCounter.count-1]}">
                <input type="checkbox" name="activity_${activity.id}" />
              </c:if>
            </td>
						<td>${activity.name}</td>	
						<td>${activity.activityCategory.name}</td>
						<td>${activity.difficulty}</td> 					
						<td>${activity.description}</td>											
					</tr>
				</c:forEach>
				<c:if test="${empty activityList}">
					<tr>
						<td colspan="999">No activities found</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>

<div class="botButtons">
  <input class="save" type="submit" value="Submit"/>
</div>
</form:form>