<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.tagline {
    font-family:georgia;
    font-weight:heavy;
    font-size:38px;
    color:#009933;
    }
.reg {
    font-size: 18px;
    font-family:helvetica;
    }
.reg_input {
    font-size: 18px;
    margin-left: 20px;
    padding-left: 10px;
    margin-bottom:10px;
    height: 40px;
    width: 340px;
    font-family:helvetica;
    }
input { 
    -webkit-border-radius: 5px; 
    -moz-border-radius: 5px; 
    border-radius: 5px; 
    }
.button {
    padding: 10px 10px;
    margin-left:19px;
    margin-bottom:20px;
    display: inline;
    background: #009933 url(button.png) repeat-x bottom;
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
.button.red { background-color: #e50000; }
.button.purple { background-color: #9400bf; }
.button.green { background-color: #58aa00; }
.button.orange { background-color: #ff9c00; }
.button.blue { background-color: #2c6da0; }
.button.black { background-color: #333; }
.button.white { background-color: #fff; color: #000; text-shadow: 1px 1px #fff; }
.button.small { font-size: 75%; padding: 3px 7px; }
.button.small:hover { background-position: 0 -50px; }
.button.small:active { padding: 4px 7px 2px; background-position: 0 top; }
.button.large { font-size: 125%; padding: 7px 12px; }
.button.large:hover { background-position: 0 -35px; }
.button.large:active { padding: 8px 12px 6px; background-position: 0 top; }
    }
</style>

<p align="center">
<c:if test="${flashMessage != null}">
  <div class="message">${flashMessage}</div>
</c:if>

<div style="width:600px; background-color:#fff;"><br>
<img src="https://img.skitch.com/20110910-89brthpuc3s98sahnkr7r4dxje.jpg" style="width:550px; margin-left:23px;"><br><br>
<span class="tagline" style="line-height:48px; margin-left:45px;">Sign up for desk-ercises<br> &nbsp; &nbsp; &nbsp;delivered daily to your inbox.</span><br>
<br>

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
<br>
</p>

