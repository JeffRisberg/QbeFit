<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<p style="padding: 4px">Comparison Screen - this will display a bubble on the left, indicating current user, and a bubble on the right,
indicating the selected user for comparison.

<p style="padding: 4px">Each bubble will have child bubbles to indicate the given user's score in each of the 9 or 10 different question 
categories.
