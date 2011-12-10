<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
.photos img {  
    top:  0; 
    left: 0; 
} 
#slideshow img { display: none }
#slideshow img.first { display: block }
</style>

<script type="text/javascript"
      src="<c:url value="/resources/javascript/jquery.cycle.lite.js" />"></script>
     
<script type="text/javascript">
  $(document).ready(function(){
    $('.photos').cycle({ 
    fx:    'fade', 
    speed:  2000 
    });
  }); 
</script>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div style="font-size: 16px; padding: 4px;">
Suggested Question ${index} of ${total}: ${question.name}
</div>
<div style="font-size: 14px; padding: 4px;">
${question.description}
</div>

Add this question?
<div class="botButtons">
  <c:if test="${index == 1}">
   <a href="<c:url value="/suggestions/back" />"><span style="color:gray">Back</span></a>
  </c:if>
   <c:if test="${index > 1}">
  <a href="<c:url value="/suggestions/back" />"><span style="color:black">Back</span></a>
  </c:if>
  <a href="<c:url value="/suggestions/yes" />">Yes</a> 
  <a href="<c:url value="/suggestions/no" />">No</a>
  <a href="<c:url value="/suggestions/done" />">Done</a>  
</div>

<div class="photos" style="height: 600px;">
 <c:if test="${not empty question.photo1}">   
     <img src="<c:url value="/resources/images/${question.photo1}.jpg" />"/>  
 </c:if>
 <c:if test="${not empty question.photo2}">  
     <img src="<c:url value="/resources/images/${question.photo2}.jpg" />"/>  
 </c:if>
</div>