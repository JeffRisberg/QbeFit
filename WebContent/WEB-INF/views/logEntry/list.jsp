<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="logEntryResults">
	<div class="list">
		<table class="summary">
			<thead>
				<tr>
				  <th>&nbsp;</th>
				  <th>Date</th>
					<th>User</th>														
					<th>Key</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="logEntry" items="${logEntryList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
						<td><a href="<c:url value="/logEntry/show/${logEntry.id}" />">${logEntry.id}</a></td>
					  <td>${logEntry.dateCreated}</td>
            <td>${logEntry.user.email}</td>
            <td>${logEntry.logEntryKey.name}</td>																
					</tr>
				</c:forEach>
				<c:if test="${empty logEntryList}">
					<tr>
						<td colspan="999">No log entries found</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
