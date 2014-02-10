<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="top" style="width: 790px; background: #EDEDEE; margin: 0px auto; font-size: 16pt; padding: 5px;"> 
	<b>${questionCategory.name}</b>
</div>
					
<div id="title" style="width: 790px; background: #EDEDEE; margin: 0px auto; font-size: 8pt; padding: 5px; " >
	${questionCategory.name} Questions:	
</div>

<c:url var="saveUrl" value="/quiz/update"/>
<form:form method="post" action="${saveUrl}">
  <table>	   
		<tbody>
			<c:forEach var="question" items="${questionList}" varStatus="rowCounter">
				<tr>					
					<td>${rowCounter.count}. ${question.text}</td>
										
					<c:if test="${question.questionType == 'String'}">					
						<td><input type="text"/></td>
					</c:if>
					<c:if test="${question.questionType == 'Number'}">					
						<td><input type="text"/></td>
					</c:if>
					<c:if test="${question.questionType == 'Range'}">         
            <td><input type="text"/></td>
          </c:if>			
					<c:if test="${question.questionType == 'Boolean'}">						
						<td><input type="checkbox" name="ans" value="ans"></td>
					</c:if>																																		
				</tr>	
			</c:forEach>
					
			<c:if test="${empty questionList}">
				<tr>
					<td colspan="999">No questions found</td>
				</tr>
			</c:if>
		</tbody>		
  	</table>

	<div id="Bottom" style="height: 50px; width: 275px; text-align: center; ">
		<input class="save" type="submit" value="Submit"/>
	</div>

 </form:form> 

