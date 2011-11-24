<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div style="float: left;">
<a href="<c:url value="/home" />">
  <img src="/QbeFit/resources/images/logo.png" border="0" />
</a>
</div>

<sec:authorize access="isAuthenticated()">
<div style="float: right; font-size: 16px; background: #cccccc; padding: 5px;">
   <a href="<c:url value="/j_spring_security_logout" />">Logout</a>
</div>
</sec:authorize>

<c:if test="${not empty offerLogin}">
<div class="login">
<form:form>
 User
 <input type="text" name="user" />
 Password
 <input type="text" name="password" />
 <input type="submit" value="Login" class="button">
 </form:form>
</div>
</c:if>

<div style="margin-top: 30px; font-style: italic; font-size: 14px;">
 No sweat, high impact, exercises that you can do at your desk.
</div>
<div style="clear:both"></div>
