<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.incra.domain.enums.EnumFieldType" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<c:url var="saveUrl" value="/leaderboard/selectUpdate"/>
  
<form:form method="post" action="${saveUrl}">
  
  <table>	   
		<tbody>
			<c:forEach var="quiz" items="${quizList}" varStatus="rowCounter">
				<tr>					
					<td>${rowCounter.count}. ${quiz.question}</td>
										
					<c:if test="${quiz.fieldType == 'InputString'}">
						<!-- InputString -->
						<td><input type="text"/></td>
					</c:if>
					<c:if test="${quiz.fieldType == 'InputNumber'}">
						<!--InputNumber -->
						<td><input type="text"/></td>
					</c:if>			
					<c:if test="${quiz.fieldType == 'Checkbox'}">
						<!-- checkbox -->
						<td><input type=checkbox name=ans value=ans></td>
					</c:if>																																		
				</tr>	
			</c:forEach>
					
			<c:if test="${empty quizList}">
				<tr>
					<td colspan="999">No entries found</td>
				</tr>
			</c:if>
		</tbody>		
  	</table>
	<table>
  		<tbody>			
			<tr><td>
				Maintenance Piece:  Engaging with this sector will trigger a reminder email each month to check responses (in case there is loss of a collection service, or people are not using it) and if this page is not revisited within 3 days after the reminder email, the bubble will begin to sink down 5% per day.
				</td>
			</tr>			
		</tbody>
	</table>

 
	<div id="Bottom" style="height: 50px; width: 275px; background: grey; margin: 20px auto; text-align: center; ">
		<input class="save" type="submit" value="Bubble this!"
			     style="background:grey; border: 0px solid black; font-size: 18pt;"/>
	</div>

 </form:form> 

