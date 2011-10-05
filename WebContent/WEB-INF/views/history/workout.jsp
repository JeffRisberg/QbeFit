<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="<c:url value="/resources/javascript/highcharts.js"/>"></script>

<div class="statusArea">
 ${userName}: You are ${level.article} ${level.name}, with ${points} points
</div>

<div id="container"></div>

<script tyle="text/javascript">
var chart;
$(document).ready(function() {      
   chart = new Highcharts.Chart({
      chart: {
         renderTo: 'container',
         defaultSeriesType: 'line'
      },
      title: {
         text: 'Exercise Pattern'
      },
      credits: {
        enabled: false
      },         
      xAxis: {
         categories: [
         'May 2011', 'Jun 2011', 'Jul 2011', 'Aug 2011', 'Sep 2011'
         ]
      },
      yAxis: {
         title: {
            text: 'Times'
         }
      },               
      series: [{
         name: 'Total Workouts',
         data: [
         11, 9, 11, 12, 14         
         ]
      }]
   });
});
</script>
