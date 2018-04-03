<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>世纪桥工程部署完毕</title>
<script src="${ctx}/js/xmlhttp.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript">
var prjname = '${prjname}';
if(prjname!=""){
	delprj(prjname);
}
function delprj(prjname)
{
	var ip = "<%=v_ip+":"+v_port%>";
	var webname = "<%=v_webname%>";
	var oBao = getXMLHttpRequest();
	oBao.open("post","http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=delPrj&prjname="+prjname,false); 
	oBao.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	oBao.send();
	//var str = unescape(oBao.responseText);
	//alert(str);
	//window.location.reload();
}
</SCRIPT>
</head>
<body>
<div align="center">
<table>
<tr>
	<td height="40"></td>
</tr>
<tr>
	<td align="center">
		<table width="405" border="0" cellpadding="0" cellspacing="0" align="center">
		  <tr>
			<td height="212" align="center" valign="middle" bgcolor="#95CBFD"><table width="295" border="0" cellpadding="0" bgcolor="#FFFFFF">
			  <tr>
				<td width="388" align="center" bgcolor="#C2E1FE"><table width="100" border="0" cellpadding="0" cellspacing="0">
					<tr>
					  <td>&nbsp;</td>
					</tr>
				  </table>
					<table width="373" border="0" cellpadding="0" cellspacing="10" bgcolor="#FFFFFF">
					  <tr>
						<td width="353" height="60" align="left" valign="bottom" class="font12">
							<p><c:out value="${message}" escapeXml="false"/></p>
							工程名称：${prjname}<p/>
							文件名: <s:property value="fileFileName"/><br/>
							文件类型：<s:property value="fileContentType"/><br/>
							文件描述：<s:property value="description"/>
						</td>
					  </tr>
					  <tr>
						<td height="80" align="center" valign="middle"><font size="2"><span class="content">
						 <input type="button" name="sure" value="确 定"
						 onclick="javascript:window.location.href='deployMisd.action'" >
						</span></font></td>
					  </tr>
					</table>
					<table width="100" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						<td>&nbsp;</td>
					  </tr>
				  </table></td>
			  </tr>
			</table></td>
		  </tr>
		</table>
	</td>
</tr>
<tr>
	<td height="40"></td>
</tr>
</table>
</div>
</body>
</html>