<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<table style="width:400px;">
 <tr>
  <td>Name:</td>
  <td>${organizationType.name}</td>
 </tr>
 <tr>
  <td>Description:</td>
  <td>${organizationType.description}</td>
 </tr>
</table>

<div style="font-size: 14px; padding: 5px; margin-top: 10px;">
Applicable questions:
</div>
<table>
 <c:forEach var="organizationTypeActivity" items="${organizationType.activities}">
  <tr>
   <td>${organizationTypeActivity.activity.activityCategory.name}</td>
   <td>${organizationTypeActivity.activity.name}</td>
  </tr>  
 </c:forEach>
</table>

