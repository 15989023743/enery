<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<!-- 共通head区域标签 begin-->
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<!-- 共通head区域标签 end-->
<!-- 外部CSS样式表以及JS引入 begin-->
<link rel="stylesheet" type="text/css" href="../../../theme/1/style.css" />
<script src="${ctx}/js/table_mvc.js" type="text/javascript"></script>
<!-- 页面JS函数编写 begin -->
<script language="javascript">
<!--

//停用、激活
function logout(userName){
   if (confirmDel("确认要注销［"+userName+"］吗?")){
	 var action = "${ctx}/center/rbac/onLineUser/logout?userName="+userName+"&page.pageRequest=${page.pageRequest}";
	 document.location.href = action;
   }
}
$(document).ready(function() {
	$("#message").fadeOut(3000);
});

-->
</script>

</head>

<body class="bodycolor" style="margin-top: 2">
<form id="ListForm" name="ListForm" action="${ctx}/center/rbac/onLineUser/" method="post" >
<input type="hidden" id="entityid" name="id" value="" />
<input type="hidden" id="userName" name="userName" value="" />
<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
<input type="hidden" id="orderBy" name="orderBy" value="${page.orderBy}" />
<input type="hidden" id="order" name="order" value="${page.order}" />
<quick:currentAndReturnPageUrl />
<c:if test="${not empty message}">
	<div id="message" style="color:#ff0000;" align="center">${message}</div>
</c:if>
 <table style="width:98%" align="center" cellspacing="0" cellpadding="0">
<!--表格顶部开始-->
 <tr>
    <td height="30">
      <!--圆角表格头开始-->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableRound">
      <tr>
        <td width="12" height="30" class="TableHeadLeft"></td>
        <td class="TableHeader">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="small">
              <tr>
                <td width="3%"><div align="center"><img src="${ctx}/images/tb.gif" width="16" height="16" /></div></td>
                <td width="35%"><b><quick:navigation name="在线用户查询列表" /></b></td>
                <td width="55%" align="right">&nbsp;&nbsp;</td>
              </tr>
            </table>
			</td>
          </tr>
        </table>
		</td>
        <td width="16" class="TableHeadRight"></td>
      </tr>
    </table>
	 <!--圆角表格头结束-->
	</td>
 </tr>
<!--表格顶部开始-->

 <tr>
   <td height="18">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableRound">
      <tr>
        <td width="8" class="TableLeft"></td>
        <td>
        <!--业务表格区域开始-->
        	<!--过滤条件开始-->
        <table style="width:100%" align="center" class="TableBlock1" >
		   <tr>
			<td class="TableHeader1" align="center" colspan="6">查询条件</td>
		   </tr>
		   <tr>
		    <td nowrap class="TableContent" width="12%" align="center">登录名</td>
		    <td nowrap class="TableData" width="38%" ><input type="text" name="filter_LIKE_userName" value="${param['filter_LIKE_userName']}"  class="SmallInput" style="width:97%" maxlength="30" size="30"/></td> 
		    <td nowrap class="TableContent" align="center" width="12%">姓名或Email</td>
		    <td nowrap class="TableData" colspan="3"><input type="text" name="filter_LIKE_trueName__email" value="${param['filter_LIKE_trueName__email']}"  class="SmallInput" style="width:97%" maxlength="30" size="30"/></td> 
		   </tr>
		   <tr>
			<td align="center" class="TableContent" colspan="6">
				<input type="button" class="SmallButton" name="b_act_query" onclick="search()" value="查&nbsp;&nbsp;询" style="width:60" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="SmallButton" name="b_act_clear" onclick="reload()" value="重&nbsp;&nbsp;置" style="width:60" />&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		   </tr>		   		   
		</table>
			<!--过滤条件结束-->
		<br/>
        <table style="width:100%" align="center" class="TableBlock1" onmousemove="changeto()" onmouseout="changeback()">
          <tr align="center">
            <td width="3%" class="TableHeader1">序号</td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('userName')">用户名</a></td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('trueName')">姓名</a></td>
            <td width="15%" class="TableHeader1">角色</td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('dept.name')">所属部门</a></td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('tel')">联系电话</a></td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('lastLoginIp')">最后登录IP</a></td>
            <td width="5%" class="TableHeader1"><a href="#" onclick="sort('loginTimes')">登录次数</a></td>
            <td width="3%" class="TableHeader1"><a href="#" onclick="sort('status')">状态</a></td>
            <td width="9%" class="TableHeader1" colspan="2">操作</td>
          </tr>
          <!-- 			-->
		  <c:forEach items="${page.result}" var="entity" varStatus="s">
          <tr>
            <td class="TableData" align="center">${s.index+page.first}</td>
            <td class="TableData">${entity.userName}</td>
            <td class="TableData">${entity.trueName}</td>
            <td class="TableData">${entity.roleNames}</td>
            <td class="TableData">${entity.dept.name}</td>
            <td class="TableData">${entity.tel}</td>
            <td class="TableData">${entity.lastLoginIp}</td>
            <td class="TableData">${entity.loginTimes}</td>
            <td class="TableData"><c:choose><c:when test="${entity.status==true}">生效</c:when><c:otherwise>失效</c:otherwise></c:choose></td>
            <td class="TableData" align="center">
            <acl:method action="logout">
            <a href="#" onclick="logout('${entity.userName}')">注销</a>
            </acl:method>
            </td>
            <td class="TableData" align="center">
            <a href="#" onclick="info('${entity.id}')">留言</a>
            </td>
          </tr>
          </c:forEach>
        </table>
		<!--业务表格区域结束-->
      </td>
        <td width="8" class="TableRight"></td>
	   </tr>
	   </table>
	</td>
 </tr>
 
<!--表格底部开始-->
 <tr>
    <td height="35">
    <!--圆角表格头开始-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableRound">
      <tr>
        <td width="12" height="35" class="TableFooterLeft"></td>
        <td class="TableFooter">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="small">
          <tr>
            <td>&nbsp;&nbsp;共有${page.totalCount}条记录，当前第 ${page.pageNo}/${page.totalPages}页</td>
            <td>${page.pageBar}</td>
          </tr>
        </table>
        </td>
        <td width="16" class="TableFooterRight"></td>
      </tr>     
    </table>
    <!--圆角表格头结束-->
    </td>
 </tr>
<!--表格底部结束-->    

 </table>
</form>
</body>
</html>