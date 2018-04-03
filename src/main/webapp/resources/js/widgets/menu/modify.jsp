<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta name="generator" content="HTML Tidy for Windows (vers 1st July 2003), see www.w3.org" />

  <title>用户注册</title>
</head>
<body>
<font color="red"><html:errors/></font>
<html:form action="" focus="username""> 
	<ul>
		<li>*用 户 名：<html:text property="username" maxlength="10" />
			<font color="red"><html:errors property="username"/></font></li>
		<li>*密　　码：<html:password property="password" maxlength="20"/>
			<font color="red"><html:errors property="password"/></font></li>
		<li>*真实姓名：<html:text property="moniker" maxlength="20"/>
			<font color="red"><html:errors property="moniker"/></font></li>
		<li>性　　别：<html:select property="sex">
							<html:option value="男">男</html:option>
							<html:option value="女">女</html:option>
					 </html:select></li>
		<li>电子邮箱：<html:text property="email" maxlength="100"/></li>
		<li>主　　页：<html:text property="homepage" maxlength="100"/></li>
		<li>固定电话：<html:text property="phone" maxlength="30"/></li>
		<li>移动电话：<html:text property="mobile_telephone" maxlength="30"/></li>
		<li>职　　务：<html:text property="work" maxlength="50"/></li>
		<li>详细地址：<html:text property="address" maxlength="100"/></li>
		<li>邮政编码：<html:text property="postalcode" maxlength="6"/></li>
		<li>自我简介：<html:textarea property="presentation" rows="2" cols="20"/></li>
		<li><html:submit>保存</html:submit><html:reset>重填</html:reset></li>
	</ul>
</html:form>
</BODY>
</HTML>