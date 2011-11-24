<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.tagline { font-size: 30px; margin-bottom: 6px; }
.subtagline { font-size: 19px; margin-bottom: 4px; }
</style>

<div style="margin: 20px 0px;">
  <div class="tagline">
  QbeFit is <b>your</b> fitness solution.
  </div>
  <div class="subtagline">
  We offer ${numActivities} exercises that you can do from your desk or cubicle at lunchtime.
  </div>
</div>

<img src="<c:url value="/resources/images/exerciseAtDesk.jpg" />" 
   style="width: 500px; margin-left: 150px;">
<br>
<br>
<a href="<c:url value="/quiz" />">Try it out!</a>

