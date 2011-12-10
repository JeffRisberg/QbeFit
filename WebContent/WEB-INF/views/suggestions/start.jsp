<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div id="top" style="width: 850px; margin: 0px auto; text-align: center; 
                     background-image: url(/EcoTracker/resources/images/bubbles.png); background-repeat: repeat-x;">
  <div style="padding: 30px; font-size: 25pt; font-family: calibri; color: #3c79a7">
    Suggested Milestones & Best Practices
  </div>
    
  <c:url var="saveUrl" value="/suggestions/done"/>
  <form:form method="post" action="${saveUrl}">
  
    <div id="Left" style="width: 425px; background: #EDEDEE; float: left;">
      
      <div id="Left-Top" style="height: 30px; width: 425px; background: #EDEDEE; margin:0px auto; 
          text-align: bottom; font-family: verdana; font-size: 10pt; padding-top: 20px; "> 
          <b>Suggestions and Milestones:</b>
      </div>         
                  
      <div id="Suggestions" style="width: 425px; background: #DCDCDC;
              text-align: bottom; font-family: verdana; font-size: 8pt;">
                
          <table class="summary">
            <thead>
              <tr>
               <th>&nbsp;</th>
               <th>Name</th> 
               <th>Category</th>                            
              </tr>
            </thead>
            <tbody>
              <c:forEach var="question" items="${questionList}" varStatus="rowCounter">          
                <tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
                  <td>
                    <c:if test="${selectedList[rowCounter.count-1]}">
                      <input type="checkbox" name="question_${question.id}" checked />
                    </c:if>
                    <c:if test="${!selectedList[rowCounter.count-1]}">
                      <input type="checkbox" name="question_${question.id}" />
                    </c:if>                  
                  </td>
                  <td>${question.text}</td> 
                  <td>${question.questionCategory.name}</td>                                       
                </tr>
              </c:forEach>
              <c:if test="${empty questionList}">
                <tr>
                  <td colspan="999">No questions found</td>
                </tr>
              </c:if>
            </tbody>
          </table>       
      </div>  
    </div>    
          
    <div id="Right" style="width: 425px; background: none; float: right; ">
            
      <div id="Right-Top" style="height: 70px; width: 425px; background: none; "></div>
                  
      <div id="bestpractices" style="height: 80px; width: 225px; text-align: bottom; font-family: verdana; 
                font-size: 10pt; padding-left: 50px; "> 
        <img src="<c:url value="/resources/images/BestMethods.png"/>" alt="Title Ball"/>
      </div>        
    </div>         
    <div style="clear:both"></div>
        
    <div id="Bottom" style="height: 55px; width: 305px; margin-top: 10px">
      <input class="save" type="submit" value="Back to Bubblespace" 
         style="background: gray; border: 0px solid gray; font-size: 18pt;"/>
    </div>
  
  </form:form>
</div>