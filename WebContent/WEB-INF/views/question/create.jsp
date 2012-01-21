<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<c:url var="saveUrl" value="/question/save"/>
<form:form method="post" action="${saveUrl}">
  <form:hidden path="id" />
  <table>
  <tr>
   <td>Text:</td>
   <td><form:input path="text" size="40"/></td>
  </tr>
  <tr>
   <td>Type:</td>
   <td>
    <form:select path="questionCategory">
      <form:options items="${questionCategoryList}" itemValue="id" itemLabel="name" />
    </form:select>
   </td>
  </tr>
  <tr>
   <td>Points:</td>
   <td><form:input path="points" size="40"/></td>
   <td><form:errors path="points" cssClass="error" /></td>
  </tr>
  <tr>
   <td>Photo 1:</td>
   <td><form:input path="photo1" size="40"/></td>
   <td><form:errors path="photo1" cssClass="error" /></td>
  </tr>
  <tr>
   <td>Photo 2:</td>
   <td><form:input path="photo2" size="40"/></td>
   <td><form:errors path="photo2" cssClass="error" /></td>
  </tr>
  <tr>
   <td>Explanation:</td>
   <td><form:textarea path="explanation:" cols="70" rows="3" /></td>
  </tr>
  </table>
 
  <div class="botButtons">
    <input class="save" type="submit" value="Save"/>
  </div>
</form:form>
