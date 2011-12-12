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
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/start/jquery-ui-1.8.16.custom.css" />" />   
  <script type="text/javascript" src="<c:url value="/resources/javascript/jquery-1.6.4.min.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/jquery-ui-1.8.16.custom.min.js" />"></script>
  <meta name="viewport" content="user-scalable=0, width=device-width, maximum-scale=1.0">
</head>
<body>
<div id="header">
  <tiles:insertAttribute name="header" />
</div>
<div class="panel-875-top"></div>
<div id="content" class="panel-875-mid">
  <tiles:insertAttribute name="body" />
</div>
<div class="panel-875-bot"></div>
<div style="clear:both"></div>
<div id="footer">
  <tiles:insertAttribute name="footer" />
</div>
</body>
</html>
