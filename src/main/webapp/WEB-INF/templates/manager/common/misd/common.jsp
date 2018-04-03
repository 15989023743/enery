<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.gdwz.core.web.WebConstants"%>
<%@page import="com.gdwz.common.config.domain.SystemConfig"%>
<%
	SystemConfig systemConfig = (SystemConfig)getServletContext().getAttribute(WebConstants.APPLICATION_CONFIG);
	String v_ip = systemConfig.getMisdEngineHost();
	if(v_ip==null || "".equals(v_ip) || "localhost".equalsIgnoreCase(v_ip) || "127.0.0.1".equals(v_ip))
	  	v_ip = request.getServerName();
	String v_port = systemConfig.getMisdEngineHostPort();
	if( v_port==null || "".equals(v_port) )
	 	v_port = String.valueOf(request.getServerPort());

	String v_webname = systemConfig.getMisdEngineName();
	String v_releaseName = systemConfig.getMisdAppPutoutName();
	String v_version = systemConfig.getMisdEngineVsesion();
	String groupName = systemConfig.getMisdAppRoleName();
%>