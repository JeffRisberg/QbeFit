<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div>
  <div style="float:left">
   QbeFit was developed at Startup Weekend, September 2011
  </div>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
  <div style="padding-left: 10px; float:right">
   <a href="<c:url value="/activity" />">
    Activities
   </a>
  </div>
  <div style="padding-left: 10px; float:right">
   <a href="<c:url value="/goal" />">
    Goals
   </a>
  </div>
  <div style="padding-left: 10px; float:right">
   <a href="<c:url value="/challenge" />">
    Challenges
   </a>
  </div>
  <div style="padding-left: 10px; float:right">
   <a href="<c:url value="/user" />">
    Users
   </a>
  </div>
  <div style="padding-left: 10px; float:right">
   <a href="<c:url value="/badge" />">
    Badges
   </a>
  </div>
  </sec:authorize>
  <div style="padding-left: 10px; float:right">
   <a href="<c:url value="/home/terms" />">
    Terms
   </a>
  </div>
  <div style="padding-left: 10px; float:right">
   <a href="<c:url value="/home/about" />">
    About Us
   </a>
  </div>
  <div style="clear:both"></div>
</div>
