<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<title>�ޱ����ĵ�</title>
<script language="javascript">
<!--

//ͣ�á�����
function logout(userName){
   if (confirmDel("ȷ��Ҫע����"+userName+"����?")){
	 var action = "${ctx}/center/rbac/onLineUser/logout?userName="+userName;
	 document.location.href = action;
   }
}
$(document).ready(function() {
	$("#message").fadeOut(3000);
});

-->
</script>
</head>

<body>
<h1>Active Users</h1>  
<ul>  
  <c:forEach items="${activeUsers}" var="uinfo">  
    <li><strong><a href="#" onclick="logout('${uinfo.key.username}')">${uinfo.key.username}</a></strong>   
    / Last Active: <strong>${uinfo.value}</strong></li>  
  </c:forEach>  
</ul>
</body>
</html>
