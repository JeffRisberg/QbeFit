<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
  .select { background: white;  padding: 5px; font-size: 15px }
  table { border: 0px solid white; font-face: "Serif" }
  .button { background: #cccccc; padding: 4px; margin:3px; }
  .grid-top { height: 32px; background: url(/QbeFit/resources/images/skin/bgr-overview-top.png) }
  .grid-middle { height: 30px; background: url(/QbeFit/resources/images/skin/bgr-overview-mid.png) }
  .grid-bottom { height: 20px; background: url(/QbeFit/resources/images/skin/bgr-overview-bot.png) }
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
   You are ${level.article} ${level.name}, with ${points} points
   <div style="clear:both"></div>
 </div>
 <div style="clear:both"></div>
</div>

<span class="button">
<a href="<c:url value="/history/activity" />">Activity Chart</a>
</span>
<span class="button">
<a href="<c:url value="/history/workout" />">Workout Chart</a>
</span>

<div style="margin-top: 10px; margin-left: 12px; width: 775px;">	
  <div class="grid-top">
   <div style="float:left; width:200px">&nbsp;</div>
   <c:forEach var="column" items="${trackSummaryColumnList}" varStatus="rowCounter"> 
    <div style="padding: 10px 0px 0px 0px; float:left; width:85px">${column.label}</div>
   </c:forEach>
   <div style="float:left; width:65px">&nbsp;</div>
  </div>
  <div style="clear:both"></div>
  <c:forEach var="row" items="${trackSummaryRowList}" varStatus="rowCounter">   
    <div class="grid-middle">
      <div style="float:left; width:200px;">
        <a style="padding: 4px 4px 0px 12px; display:block;" href="<c:url value="/home/activity/${row.activityId}" />">${row.name}</a>
      </div>
      <c:forEach var="flag" items="${row.flags}" varStatus="colCounter">
        <div style="float:left; width:75px; padding: 7px 0px 0px 10px;">
        	<c:if test="${flag}"><img height="15" width="20" src="<c:url value="/resources/images/YES.png" />" /></c:if>
        	<c:if test="${!flag}"><img height="15" width="20" src="<c:url value="/resources/images/NO.png" />" /></c:if>
        </div>
      </c:forEach>
	    <div style="float:left; width:55px; padding: 5px 0px 0px 10px;">
	      <a href="#" onclick="javascript:doneIt(${row.userActivityId});">Done</a>
	    </div>
	    <div style="clear:both; height:40px;"></div>
    </div>
  </c:forEach>
  <div class="grid-bottom"></div>  
</div>
<div class="subsection" class="select">
	<a href="<c:url value="/activity/select" />">Select Your Activities</a>
</div>

<div style="margin-top: 10px; font-size: 12px">
  <table> 
    <tr>
      <th style="font-size: 16px">Goals:</th>
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
  <a href="<c:url value="/goal/select" />">Select Your Goals</a>
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