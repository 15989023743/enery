<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作流任务管理</title>
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="../../../../../theme/1/style.css" />
<script src="${ctx}/js/table_mvc.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/calendar/openCalendar.js"></script>
<!-- 页面JS函数编写 begin -->
<script language="javascript">
<!--

-->
</script>

</head>

<body class="bodycolor" style="margin-top: 2px">
<form id="ListForm" name="ListForm" action="${ctx}/center/common/wfms/workflow/" method="post" >
<input type="hidden" id="entityid" name="id" value="" />
<input type="hidden" id="flid" name="flvalue" value="1" />
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
                <td width="35%"><b><quick:navigation name="待办消息" /></b></td>
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
		    <td nowrap class="TableContent" width="12%" align="center">流程名称</td>
		    <td nowrap class="TableData" width="38%" ><input type="text" name="wc.pdname" value="${param['wc.pdname']}"  class="SmallInput" style="width:96%" maxlength="30" size="30"/></td> 
		    <td nowrap class="TableContent" align="center" width="12%">任务名称</td>
		    <td nowrap class="TableData" colspan="3"><input type="text" name="wc.tname" value="${param['wc.tname']}"  class="SmallInput" style="width:95%" maxlength="30" size="30"/></td> 
		   </tr>
		   <tr>
		    <td nowrap class="TableContent" width="12%" align="center">创建时间</td>
		    <td nowrap class="TableData" width="38%" ><input readonly="readonly" type="text" onclick="showCalendarYmdHMS('startTime')"  id="startTime" name="wc.startTime" value="${param['wc.startTime']}" class="SmallInput"  style="width:120px;" />至<input readonly="readonly" type="text" onclick="showCalendarYmdHMS('endTime')"  id="endTime" name="wc.endTime" value="${param['wc.endTime']}" class="SmallInput"  style="width:120px;" /></td> 
		    <td nowrap class="TableContent" align="center" width="12%">状态</td>
		    <td nowrap class="TableData" colspan="3">
					<quick:dictselect  
						name="wc.status" 
						value="wc.status"
						dictname="ZDY_TASK_STATUS"  
						emptyOption="true" 
						cssStyle="width:230px" 
						cssClass="smallselect"/>
			</td> 
		   </tr>
			<td align="center" class="TableContent" colspan="6">
				<input type="button" class="SmallButton" name="b_act_query" onclick="search()" value="查&nbsp;&nbsp;询" style="width:60" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="SmallButton" name="b_act_clear" onclick="reload()" value="重&nbsp;&nbsp;置" style="width:60" />
			</td>
		   </tr>		   		   
		</table>
			<!--过滤条件结束-->
		<br/>
        <table style="width:100%" align="center" class="TableBlock1" onmousemove="changeto()" onmouseout="changeback()">
          <tr align="center">
            <td width="4%" class="TableHeader1">序号</td>
			<td width="10%" class="TableHeader1">流程名称</td>
            <td width="12%" class="TableHeader1"><a href="#" onclick="sort('name')">任务名称</a></td>
            <td width="15%" class="TableHeader1"><a href="#" onclick="sort('description')">说明</a></td>
			<td width="6%" class="TableHeader1"><a href="#" onclick="sort('actorId')">参与者</a></td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('create')">创建时间</a></td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('start')">启动时间</a></td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('end')">结束时间</a></td>
            <td width="5%" class="TableHeader1">操作</td>
          </tr>
          <c:forEach items="${page.result}" var="e" varStatus="s">
          <tr>
            <td class="TableData" align="center">${s.index+page.first}</td>
			<td class="TableData">${e.processDefinitionName}</td>
            <td class="TableData">${e.name}</td>
			<td class="TableData">${e.description}</td>
            <td class="TableData">${e.actorId}</td>
            <td class="TableData"><quick:date name="e.create" format="yyyy年MM月dd日 HH时mm分ss秒" /></td>
            <td class="TableData"><quick:date name="e.start" format="yyyy年MM月dd日 HH时mm分ss秒" /></td>
            <td class="TableData"><quick:date name="e.end" format="yyyy年MM月dd日 HH时mm分ss秒" /></td>
            <td class="TableData" align="center">
			<c:if test="${e.end==null}">
			<a href="#" onclick="command('${e.id}','openTask')">办理</a>
			</c:if>
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