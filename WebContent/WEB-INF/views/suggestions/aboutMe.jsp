<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="top" style="width: 700px; margin: 0px auto;">
	<div style="font-size: 15px;">		
	 Tell us a little about yourself and your goals:		
	</div>
	
	<c:url var="saveUrl" value="/suggestions/aboutMeUpdate"/>
  <form:form method="post" action="${saveUrl}">
		<div style="background: none; margin: 7px auto; padding: 7px auto;">
		  Type: 
			<form:select path="organizationType">
        <form:options items="${organizationTypeList}" itemValue="id" itemLabel="name" />
      </form:select>   
		</div>	
    <table class="summary">
      <thead>
        <tr>
          <th>Select Goal</th>
          <th>Name</th>                   
          <th>Description</th>      
        </tr>
      </thead>
      <tbody>
        <c:forEach var="goal" items="${goalList}" varStatus="rowCounter">          
          <tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
            <td>
              <c:if test="${selectedList[rowCounter.count-1]}">
                <input type="checkbox" name="goal_${goal.id}" checked />
              </c:if>
              <c:if test="${!selectedList[rowCounter.count-1]}">
                <input type="checkbox" name="goal_${goal.id}" />
              </c:if>
            </td>   
            <td>${goal.name}</td>       
            <td>${goal.description}</td>                      
          </tr>
        </c:forEach>      
      </tbody>
    </table> 
    <div style="padding: 15px; text-align: bottom;">
          <input type="Submit" NAME="submit" Value="Let's get started!">
    </div>  
	</form:form>
</div>
