<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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