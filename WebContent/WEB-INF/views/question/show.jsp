<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<table>
 <tr>
  <td>Text:</td>
  <td>${question.text}</td>
 </tr>
 <tr>
  <td>Category:</td>
  <td>${question.questionCategory.name}</td>
 </tr>
 <tr>
  <td>Points:</td>
  <td>${question.points}</td>
 </tr>
 <tr>
  <td>Photo 1:</td>
  <td>${question.photo1}</td>
 </tr>
 <tr>
  <td>Photo 2:</td>
  <td>${question.photo2}</td>
 </tr>
 <tr>
  <td>Explanation:</td>
  <td>${question.explanation}</td>
 </tr>
</table>

<div class="botButtons">
  <a href="<c:url value="/question/edit/${question.id}" />">Edit</a> 
</div>