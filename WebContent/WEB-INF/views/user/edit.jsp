<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
.error {
  color: #ff0000;
}
</style>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<c:url var="saveUrl" value="/user/save"/>
<form:form method="post" action="${saveUrl}">
  <form:hidden path="id" />
  <table>
  <tr>
	 <td>Email:</td>
	 <td><form:input path="email" size="40"/></td>
	 <td><form:errors path="email" cssClass="error" /></td>
	</tr>
  <tr>
   <td>First Name:</td>
   <td><form:input path="firstName" size="40"/></td>
   <td><form:errors path="firstName" cssClass="error" /></td>
  </tr>
   <tr>
   <td>Last Name:</td>
   <td><form:input path="lastName" size="40"/></td>
   <td><form:errors path="lastName" cssClass="error" /></td>
  </tr>
	</table>
	
	<div class="botButtons">
	<input class="save" type="submit" value="Submit"/>
	</div>
</form:form>
