<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div class="topButtons"> 
	  <a class="create" href="<c:url value="/activity/create" />">New Activity</a> 
</div>

<div id="activityResults">
	<div class="list">
		<table class="summary">
			<thead>
				<tr>
					<th>Name</th>		
					<th>Category</th>
					<th>Difficulty</th>								
					<th>Description</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="activity" items="${activityList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
						<td><a href="<c:url value="/activity/show/${activity.id}" />">${activity.name}</a>
						<td>${activity.activityCategory.name}
						<td>${activity.difficulty}</td>						
						<td>${activity.description}</td>
						<td><a href="<c:url value="/activity/edit/${activity.id}" />">Edit</a></td>
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
