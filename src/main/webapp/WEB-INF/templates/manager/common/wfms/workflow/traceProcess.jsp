<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.gdwz.com/workflow/tags" prefix="workflow" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>流程跟踪</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
  <body>
	<workflow:traceProcess piid="${param.piid}"/>
  </body>
</html>
