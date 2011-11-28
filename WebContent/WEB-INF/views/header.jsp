<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div style="float: left;">
<a href="<c:url value="/home" />">
  <img src="/QbeFit/resources/images/logo180x64.png" border="0" />
</a>
</div>

<sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')">
<div style="float: right; font-size: 16px; background: #cccccc; padding: 5px;">
   <a href="<c:url value="/j_spring_security_logout" />">Logout</a>
</div>
</sec:authorize>

<sec:authorize access="!(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))">
 <div class="login">
  <c:if test="${isError}">
   <div style="color:red">
    Invalid login, please try again
   </div>
  </c:if>
  <c:url var="loginUrl" value="/j_spring_security_check"/>
  <form:form method="post" action="${loginUrl}">
   User
   <input type="text" name="j_username" />
   Password
   <input type="password" name="j_password" />
   <input type="submit" value="Login" class="button">
  </form:form>
  <div style="padding: 5px 0px;">
   <a href="<c:url value="/user/register" />">Register and Save my Scores</a>
  </div>
 </div>
</sec:authorize>

<div style="clear:both"></div>
<div style="font-style: italic; font-size: 14px;">
 No sweat, high impact, exercises that you can do at your desk.
</div>
