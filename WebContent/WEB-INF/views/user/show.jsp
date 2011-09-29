<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<table>
 <tr>
  <td>Email:</td>
  <td>${user.email}</td>
 </tr>
 <tr>
  <td>First Name:</td>
  <td>${user.firstName}</td>
 </tr>
 <tr>
  <td>Last Name:</td>
  <td>${user.lastName}</td>
 </tr>
 <tr>
  <td>Points:</td>
  <td>${user.points}</td>
 </tr>
 <tr>
  <td>Level:</td>
  <td>${user.level.name}</td>
 </tr>
 <tr>
  <td>Login Count:</td>
  <td>${user.loginCount}</td>
 </tr>
 <tr>
  <td>Last Logged In:</td>
  <td>${user.lastLoggedIn}</td>
 </tr>
</table>

<div class="botButtons">
  <a href="<c:url value="/user/edit/${user.id}" />">Edit</a> 
</div>
