<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div class="topButtons"> 
	  <a class="create" href="<c:url value="/challenge/create" />">New Challenge</a> 
</div>

<div id="challengeResults">
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
				<c:forEach var="challenge" items="${challengeList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
						<td><a href="<c:url value="/challenge/show/${challenge.id}" />">${challenge.name}</a>						
						<td>${challenge.description}</td>
						<td><a href="<c:url value="/challenge/edit/${challenge.id}" />">Edit</a></td>
					</tr>
				</c:forEach>
				<c:if test="${empty challengeList}">
					<tr>
						<td colspan="999">No challenges found</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
