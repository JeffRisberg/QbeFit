<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<p align="center">
<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div style="width:600px; background-color:#fff;">
<img src="https://img.skitch.com/20110910-89brthpuc3s98sahnkr7r4dxje.jpg" style="width: 550px; margin: 10px 0px 10px 23px">
<div style="margin: 5px 0px;">
  Sign up for desk-ercises delivered daily to your inbox.
</div>

<c:url var="saveUrl" value="/user/registerUpdate"/>
<form:form method="post" action="${saveUrl}">

<table style="width:500px; margin-left:45px; margin-bottom:15px;" class="reg">
  <tr>
    <td>First&nbsp;Name: </td>
    <td><form:input path="firstName" class="reg_input" /></td>
  </tr>
  <tr>
    <td>Last&nbsp;Name: </td>
    <td><form:input path="lastName" class="reg_input" /></td>
  </tr> 
  <tr>
    <td>Email&nbsp;Address: </td>
    <td><form:input path="email" class="reg_input" /></td>
  </tr>
  <tr>
    <td>Password: </td>
    <td><form:password path="password" class="reg_input" /></td>
  </tr>
  <tr>
    <td>Confirm&nbsp;Password: </td>
    <td><form:password path="confirmPassword" class="reg_input" /></td>
  </tr>
  <tr>
    <td></td>
    <td><input type="submit" value="OK, let's get started!" class="button"></td>
  </tr>
</table>
</form:form>
</div>