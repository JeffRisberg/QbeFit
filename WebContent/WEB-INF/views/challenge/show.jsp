<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<table>
 <tr>
  <td>Name:</td>
  <td>${challenge.name}</td>
 </tr>
 <tr>
  <td>Description:</td>
  <td>${challenge.description}</td>
 </tr>
</table>

<div class="botButtons">
  <a href="<c:url value="/challenge/edit/${challenge.id}" />">Edit</a> 
</div>
