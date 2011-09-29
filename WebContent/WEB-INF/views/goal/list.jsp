<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div class="topButtons"> 
	  <a class="create" href="<c:url value="/goal/create" />">New Goal</a> 
</div>

<div id="goalResults">
	<div class="list">
		<table class="summary">
			<thead>
				<tr>
					<th>Name</th>										
					<th>Description</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="goal" items="${goalList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
						<td><a href="<c:url value="/goal/show/${goal.id}" />">${goal.name}</a>						
						<td>${goal.description}</td>
						<td><a href="<c:url value="/goal/edit/${goal.id}" />">Edit</a></td>
					</tr>
				</c:forEach>
				<c:if test="${empty goalList}">
					<tr>
						<td colspan="999">No goals found</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
