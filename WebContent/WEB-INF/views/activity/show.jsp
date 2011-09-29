<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<table>
 <tr>
  <td>Name:</td>
  <td>${activity.name}</td>
 </tr>
 <tr>
  <td>Category:</td>
  <td>${activity.activityCategory.name}</td>
 </tr>
 <tr>
  <td>Difficulty:</td>
  <td>${activity.difficulty}</td>
 </tr>
 <tr>
  <td>Photo 1:</td>
  <td>${activity.photo1}</td>
 </tr>
 <tr>
  <td>Photo 2:</td>
  <td>${activity.photo2}</td>
 </tr>
 <tr>
  <td>Description:</td>
  <td>${activity.description}</td>
 </tr>
</table>

<div class="botButtons">
  <a href="<c:url value="/activity/edit/${activity.id}" />">Edit</a> 
</div>