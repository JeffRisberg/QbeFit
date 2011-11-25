<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="<c:url value="/resources/javascript/highcharts.js"/>"></script>

<style>
 p { font-size: 12px; margin-bottom: 7px; }
</style>

<div id="container" style="height: 120px; margin: 10px;"></div>

<script tyle="text/javascript">
var renderer;
$(document).ready(function() {   
  renderer = new Highcharts.Renderer(
    $('#container')[0],
    400,
    300
  );

  renderer.rect(120, 130, 100, 100, 5)
        .attr({
            'stroke-width': 2,
            stroke: 'red',
            fill: 'yellow',
            zIndex: 3
        })
        .add();
        
  renderer.rect(240, 130, 100, 100, 5)
        .attr({
            'stroke-width': 5,
            stroke: '#ddd',   
            fill: 'blue',         
            zIndex: 3
        })       
        .add();
  renderer.image('http://localhost:8080/QbeFit/resources/images/logo.png', 350, 130, 300, 100)
        .add();
});
</script>
