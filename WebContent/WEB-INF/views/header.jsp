<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div style="float: left;">
<a href="<c:url value="/home" />">
  <img src="/QbeFit/resources/images/logo180x64.png" width="180" height="64" border="0" />
</a>
</div>
<div style="float:right">
<div class="panel-475-top"></div>
<div class="panel-475-mid">
<sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')">
<div style="float: right; font-type: verdana; font-size: 12px; margin-right: 5px; margin-left: 5px;">
   <button id="logout" onClick="window.location='<c:url value="/j_spring_security_logout"/>';">
   Logout
   </button>
</div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
<div style="font-type: verdana; float: right; font-size: 12px; margin-left: 5px;">
   <button id="admin" onClick="window.location='<c:url value="/adminHome" />';">
   Admin
   </button>
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
   <input type="text" name="j_username" size="18" />
   Password
   <input type="password" name="j_password" size="18" />
   <input type="submit" value="Login" class="button">
  </form:form>
  <div style="padding: 5px 0px;">
   <button id="registerAndSaveMyscores" style="font-size: 11px"
     onClick="window.location='<c:url value="/register" />'">
     Register and Save my Scores
   </button>
  </div>
 </div>
</sec:authorize>

<div style="clear:both"></div>
</div>
<div class="panel-475-bot"></div>
</div>

<div style="font-style: italic; font-size: 14px;">
 No sweat, high impact, exercises that you can do at your desk.
</div>

<script type="text/javascript">  
  //Apply jQuery/jQueryUI behavior to elements on this page
  $(document).ready(function() {  
    $('#logout').button();
    $('#admin').button();
    $('#registerAndSaveMyscores').button();
   });  
</script>
