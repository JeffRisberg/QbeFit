<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="organizationTypeResults">
	<div class="list">
		<table class="summary">
			<thead>
				<tr>
					<th>Name</th>										
					<th>Description</th>				
				</tr>
			</thead>
			<tbody>
				<c:forEach var="organizationType" items="${organizationTypeList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
						<td><a href="<c:url value="/organizationType/show/${organizationType.id}" />">${organizationType.name}</a>						
						<td>${organizationType.description}</td>					
					</tr>
				</c:forEach>
				<c:if test="${empty organizationTypeList}">
					<tr>
						<td colspan="999">No organizationTypes found</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
