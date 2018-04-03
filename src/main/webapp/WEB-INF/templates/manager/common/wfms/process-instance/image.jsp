<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
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
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/js/return.js"></script>
</head>
  <body>
  <div style='position:absolute; width:611px;height:22px;z-index:1;left:0px; top:0px;' >
  <form id="inputForm" action="${ctx}/center/common/wfms/processInstance/image" method="post" >
  <quick:backPageUrl />
  <b><quick:navigation name="流程跟踪" /></b>
  <input type="button" class="SmallButton" name="b_act_close"    style="width:60"  value="返&nbsp;&nbsp;回" onClick="doreturn()" />
  </form>
  </div>
  <workflow:traceProcess piid="${piid}"/>
  </body>
</html>
