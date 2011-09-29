<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<ul>
<c:forEach var="menuItem" items="${menuItems}"> 
    <c:if test="${menuItem == currentMenuItem}">
    <li class="current"><a href="<c:url value="${menuItem.url}" />">${menuItem.label}</a></li>    
    </c:if>
    <c:if test="${menuItem != currentMenuItem}">
    <li><a href="<c:url value="${menuItem.url}" />">${menuItem.label}</a></li>    
    </c:if>
</c:forEach>
</ul>
