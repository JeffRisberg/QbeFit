<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.tagline {
    font-weight: heavy;
    font-size: 30px;
    color: #0099dd;   
}

.activityDescription { 
    margin-bottom: 6px; 
    font-size: 13px; 
}

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
     
<p align="center">
<div style="width: 800px;">
<div style="margin: 10px 0px 14px 0px">
<span class="tagline">Your QbeFit exercise is: ${activity.name}</span>
</div>
<div class="activityDescription">${activity.description}</div>
<div class="photos" style="height: 600px;">
 <c:if test="${not empty activity.photo1}">   
     <img src="<c:url value="/resources/images/${activity.photo1}.jpg" />"/>  
 </c:if>
 <c:if test="${not empty activity.photo2}">  
     <img src="<c:url value="/resources/images/${activity.photo2}.jpg" />"/>  
 </c:if>
</div>
</div>
</p>