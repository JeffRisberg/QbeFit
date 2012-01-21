<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div class="topButtons"> 
	  <a class="create" href="<c:url value="/question/create" />">New Question</a> 
</div>

<div id="questionResults">
	<div class="list">
		<table class="summary">
			<thead>
				<tr>
					<th>Text</th>		
					<th>Category</th>
					<th>Points</th>								
					<th>Explanation</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="question" items="${questionList}" varStatus="rowCounter">				   
					<tr class="${rowCounter.count % 2 == 0 ? 'even' : 'odd'}">
						<td><a href="<c:url value="/question/show/${question.id}" />">${question.text}</a>
						<td>${question.questionCategory.name}</td>
						<td>${question.points}</td>   						
						<td>${question.explanation}</td>
						<td><a href="<c:url value="/question/edit/${question.id}" />">Edit</a></td>
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
