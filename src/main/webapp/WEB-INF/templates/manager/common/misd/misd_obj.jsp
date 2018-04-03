<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<c:if test="${nodeurltype eq '04' or  nodeurltype eq '' }">
<c:set var="v_ip" value="${APP.misdEngineHost}"/>
<c:set var="v_port" value="${APP.misdEngineHostPort}"/>
<c:set var="v_engineName" value="${APP.misdEngineName}"/>
<c:set var="v_releaseName" value="${APP.misdAppPutoutName}"/>
<c:set var="v_version" value="${APP.misdEngineVsesion}"/>
</c:if>
<c:if test="${nodeurltype eq '06'}">
<c:set var="v_ip" value="${APP.misdEngineHost_net}"/>
<c:set var="v_port" value="${APP.misdEngineHostPort_net}"/>
<c:set var="v_engineName" value="${APP.misdEngineName_net}"/>
<c:set var="v_releaseName" value="${APP.misdAppPutoutName_net}"/>
<c:set var="v_version" value="${APP.misdEngineVsesion_net}"/>
</c:if>

<c:if test="${v_ip eq '' or v_ip eq 'localhost' or v_ip eq '127.0.0.1'}">
	<c:set var="v_ip" value="${pageContext.request.serverName}"/>
</c:if>
<c:if test="${v_port eq ''}">
	<c:set var="v_port" value="${pageContext.request.serverPort}"/>
</c:if>

<%
	/*
	String v_ip = GlobalConstants.getMisdEngineHost();
	if(v_ip==null || "".equals(v_ip) || "localhost".equalsIgnoreCase(v_ip) || "127.0.0.1".equals(v_ip))
	  	v_ip = request.getServerName();
	String v_port = GlobalConstants.getMisdEngineHostPort();
	if( v_port==null || "".equals(v_port) )
	 	v_port = String.valueOf(request.getServerPort());

	String v_webname = GlobalConstants.getMisdEngineName();
	String v_releaseName = GlobalConstants.getMisdAppPutoutName();
	String v_version = GlobalConstants.getMisdEngineVsesion();*/
%>

<script language="jscript" src="${ctx}/js/misdobj.js"></script>
<center>
<script language="jscript">
	LoadMISDObject("${nodeurltype}","http://${v_ip}:${v_port}","${v_engineName}","${v_releaseName}","${v_version}");
</script>
</center>