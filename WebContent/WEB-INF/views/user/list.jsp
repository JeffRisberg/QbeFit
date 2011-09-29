<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="userResults">
	<div class="list">
		<table class="summary">
			<thead>
				<tr>
					<th>Email</th>		
					<th>First Name</th>
					<th>Last Name</th>
					<th style="text-align: right">Points</th>	
					<th>Level</th>  
					<th style="text-align: right">Login Count</th> 
					<th>Last Logged In</th> 												
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${userList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
						<td><a href="<c:url value="/user/show/${user.id}" />">${user.email}</a>					
						<td>${user.firstName}</td>						
						<td>${user.lastName}</td>
						<td style="text-align: right">${user.points}</td>
						<td><c:if test="${not empty user.level}">${user.level.name}</c:if></td>
						<td style="text-align: right">${user.loginCount}</td>
						<td>${user.lastLoggedIn}</td>
						<td><a href="<c:url value="/user/edit/${user.id}" />">Edit</a> 
					</tr>
				</c:forEach>
				<c:if test="${empty userList}">
					<tr>
						<td colspan="999">No users found</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
