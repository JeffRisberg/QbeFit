<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.tagline { font-size: 30px; margin-bottom: 6px; }
.subtagline { font-size: 19px; margin-bottom: 4px; }
.reg { 
   width: 500px; 
   margin-left: 45px; 
   margin-bottom: 15px;
   font-size: 16px; 
   font-family: helvetica;
}
.reg td {
   text-align: right;
} 
.reg_input {
    font-size: 12px;
    margin-left: 10px;
    padding-left: 5px;
    margin-bottom: 5px;
    height: 20px;
    width: 190px;      
}
.reg_input.wide {
    width: 554px;
}
input { 
    -webkit-border-radius: 5px; 
    -moz-border-radius: 5px; 
    border-radius: 5px; 
}
.label {
    padding-top: 12px;
}
.button {
    padding: 10px 10px;
    margin-left:19px;
    margin-bottom:20px;
    display: inline;
    background: #009933;
    border: none;
    color: #fff;
    cursor: pointer;
    font-weight: bold;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    text-shadow: 1px 1px #666;
    font-size:18px;
    font-family:helvetica;
}
.button:hover {
    background-position: 0 -48px;
}
.button:active {
    background-position: 0 top;
    position: relative;
    top: 1px;
    padding: 6px 10px 4px;
}
</style>

<c:url var="saveUrl" value="/user/register"/>
<form:form method="post" action="${saveUrl}">
<form:hidden path="points" />

<table style="width:500px; margin-left:45px; margin-bottom:15px;" class="reg">
  <tr>
    <td class="label">First&nbsp;Name:</td>
    <td><form:input path="firstName" class="reg_input" />
    <form:errors path="firstName" cssClass="error" /></td>
    <td class="label">Last&nbsp;Name:</td>
    <td><form:input path="lastName" class="reg_input" />
    <form:errors path="lastName" cssClass="error" /></td>
  </tr> 
  <tr>
    <td class="label">Email&nbsp;Address:</td>
    <td colspan="99"><form:input path="email" class="reg_input wide" size="50" />
    <form:errors path="email" cssClass="error" /></td>
  </tr>
  <tr>
    <td class="label">Password:</td>
    <td><form:password path="password" class="reg_input" />
    <form:errors path="password" cssClass="error" /></td>
    <td class="label">Confirm&nbsp;Password:</td>
    <td><form:password path="confirmPassword" class="reg_input" />
    <form:errors path="confirmPassword" cssClass="error" /></td>
  </tr>
  <tr>
    <td></td>
    <td><input type="submit" value="OK, let's get started!" class="button"></td>
  </tr>
</table>
</form:form>
