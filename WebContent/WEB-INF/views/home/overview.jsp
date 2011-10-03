<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
  .select { background: white;  padding: 5px; font-size: 15px }
  table { border: 0px solid white; font-face: "Serif" }
  .button { background: #cccccc; padding: 4px; margin:3px; }
</style>
<div class="welcomeMsg">
 <div style="float:right">
 <c:forEach var="userBadge" items="${userBadges}">
   <img src="<c:url value="/resources/images${userBadge.badge.icon}"/>" width="48" height="48"
        title="${userBadge.badge.description}"/>
 </c:forEach>
 </div>
 Welcome, ${userName}
 <div style="clear:both"></div>
</div>
<div>
 <div style="background: red; float:left; width: 48px; height: 48px;">
   <img src="<c:url value="/resources/images${level.icon}"/>" width="48" height="48"
        title="${level.description}"/>
 </div>
 <div class="statusArea" style="margin-bottom: 10px; margin-left: 50px; padding-bottom: 0px">
   <div style="float:right;">
     <a class="button" href="<c:url value="/level" />">Show Levels</a>
   </div>
   You are ${level.article} ${level.name}, with ${points} points.
   <div style="clear:both"></div>
 </div>
 <div style="clear:both"></div>
</div>

<span class="button">
<a href="<c:url value="/history/activity" />">Activity History</a>
</span>
<span class="button">
<a href="<c:url value="/history/workout" />">Workout History</a>
</span>

<div class='section' style="border-left: 15px solid #661133; padding-left: 5px; margin-bottom: 10px">
<div style="margin-top: 10px">
	<table> 
  <tr>
   <th>&nbsp;</th>
   <c:forEach var="column" items="${trackSummaryColumnList}" varStatus="rowCounter"> 
    <th>${column.label}</th>
   </c:forEach>
   <th>&nbsp;</th>
  </tr>
  <c:forEach var="row" items="${trackSummaryRowList}" varStatus="rowCounter">   
    <tr>
      <td><a href="<c:url value="/home/activity/${row.activityId}" />">${row.name}</a></td>
      <c:forEach var="flag" items="${row.flags}" varStatus="colCounter">
        <td>
        	<c:if test="${flag}"><img height="15" width="20" src="<c:url value="/resources/images/YES.png" />" /></c:if>
        	<c:if test="${!flag}"><img height="15" width="20" src="<c:url value="/resources/images/NO.png" />" /></c:if>
        </td>
      </c:forEach>
	   <th><a href="#" onclick="javascript:doneIt(${row.userActivityId});">Done</a></th>
    </tr>
  </c:forEach>
  </table>
</div>
<div class="subsection" class="select">
	<a href="<c:url value="/activity/select" />">Select Activities</a>
</div>
</div>

<div class='section' style="border-left: 15px solid #221188; padding-left: 5px; margin-bottom: 10px">
<div style="margin-top: 10px">
  <table> 
    <tr>
      <th>&nbsp</th>
      <th>&nbsp</th>
    </tr>
  <c:forEach var="row" items="${goalList}" varStatus="rowCounter">   
    <tr>
      <td>${row.name}</td>  
      <td>${row.description}</td>      
    </tr>
  </c:forEach>
  </table>
</div>
<div class="subsection" class="select">
  <a href="<c:url value="/goal/select" />">Select Goals</a>
</div>
</div>
<script type="text/javascript"> 
  function doneIt(userActivityId) { 
    $.ajax({
      url: '<c:url value="/home/doneIt" />/'+userActivityId,      
      success: function(data) {        
       location.reload(true);
      }
    });
  }
</script>