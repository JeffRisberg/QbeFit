<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>  
	<title>
	   <tiles:insertAttribute name="title" ignore="true" />
	</title>
	<link rel="stylesheet" href="<c:url value="/resources/styles/main.css" />" type="text/css" media="screen" />
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/redmond/jquery-ui-1.8.11.custom.css" />" />   
  <script type="text/javascript" src="<c:url value="/resources/javascript/jquery-1.5.2.min.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/jquery-ui-1.8.11.custom.min.js" />"></script>
</head>
<body>
<div id="header">
  <tiles:insertAttribute name="header" />
</div>
<div id="content">
  <tiles:insertAttribute name="body" />
</div>
<div style="clear:both"></div>
<div id="footer">
  <tiles:insertAttribute name="footer" />
</div>
</body>
</html>