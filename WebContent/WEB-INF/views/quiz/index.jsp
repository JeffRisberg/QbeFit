<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
  .list { margin: 15px 0px; }
</style>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<c:url var="saveUrl" value="/quiz/indexUpdate"/>
<form:form method="post" action="${saveUrl}">
  <table>    
    <tbody>
      <c:forEach var="question" items="${questionList}" varStatus="rowCounter">
        <tr>          
          <td>${rowCounter.count}. ${question.text}</td>
                    
          <c:if test="${question.questionType == 'InputString'}">       
            <td><input type="text"/></td>
          </c:if>
          <c:if test="${question.questionType == 'InputNumber'}">           
            <td><input type="text"/></td>
          </c:if>     
          <c:if test="${question.questionType == 'Checkbox'}">        
            <td><input type="checkbox" name="ans" value="ans"></td>
          </c:if>                                                                   
        </tr> 
      </c:forEach>
          
      <c:if test="${empty questionList}">
        <tr>
          <td colspan="999">No questions found, please select more goals.</td>
        </tr>
      </c:if>
    </tbody>    
  </table>
    
  <div style="padding: 15px; text-align: bottom;">
    <input type="Submit" name="submit" Value="Submit">
  </div> 

 </form:form> 