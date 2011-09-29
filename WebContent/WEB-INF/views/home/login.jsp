<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.tagline {
    font-family:georgia;
    font-weight:heavy;
    font-size:30px;
    color:#0099dd;
    }
.subtagline { font-size: 19px; margin-bottom: 4px; }
.error {   
    width: 495px; 
    margin-left: 45px;
    color: red;
    background: #dddddd;
    font-size: 12px;
    padding: 6px;
}
.reg {
    font-size: 16px;
    font-family:helvetica;
    }
.reg_input {
    font-size: 16px;
    margin-left: 20px;
    padding-left: 10px;
    margin-bottom:10px;
    height: 25px;
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
    background: #0099dd;
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

<div style="line-height:48px; margin-left:45px;">
<span class="tagline" style=>Welcome back! Please log in:</span>
</div>

<c:if test="${isError}">
<div class="error">
The username or password you entered were not found.  Please try again.
</div>
</c:if>
<c:url var="saveUrl" value="/j_spring_security_check"/>
<form method="post" action="${saveUrl}">

<table style="width:500px; margin-left:45px; margin-bottom:15px;" class="reg">
  <tr>
   <td>Email&nbsp;Address:</td>
   <td><input type="text" name="j_username" class="reg_input" /></td>
  </tr>

  <tr>
   <td>Password:</td>
   <td><input type="password" name="j_password" class="reg_input" /></td>
  </tr>
    
  <tr>
    <td></td>
    <td><input type="submit" value="I'm ready!" class="button" /></td>
  </tr>
</table>
</form>
