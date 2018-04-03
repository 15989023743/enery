<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<%@ include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>世纪桥工程管理</title>
<!-- 共通head区域标签 begin-->
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<!-- 共通head区域标签 end-->
<!-- 外部CSS样式表以及JS引入 begin-->
<link rel="stylesheet" type="text/css" href="../../../theme/1/style.css" />
<script src="${ctx}/js/xmlhttp.js" type="text/javascript"></script>
<script src="${ctx}/js/table_mvc.js" type="text/javascript"></script>
<!-- 页面JS函数编写 begin -->
<script language="javascript">
<!--
var ip = "<%=v_ip+":"+v_port%>";
var webname = "<%=v_webname%>";
var groupname = "<%=groupName%>";
var oBao;
var prjname = '${param["prjname"]}';
if(prjname.length>0){
	oBao = getXMLHttpRequest();
	delprj(prjname);
	reloadGroupRight(groupname);
}
function delprj(prjname)
{
	oBao.open("post","http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=delPrj&prjname="+prjname,false); 
	oBao.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	oBao.send();
	//var str = unescape(oBao.responseText);
	//alert(str);
	//window.location.reload();
	//$.getJSON("http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=delPrj&prjname="+prjname,function(data){
		//alert(data.content);
		//$('#mashupContnt').html(data.content);
	//});
	
	//send_request(function(data){},"http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=delPrj&prjname="+prjname,true);
}
function reloadGroupRight(groupname)
{
	oBao.open("post","http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=reloadgroupright&groupname="+groupname,false); 
	oBao.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	oBao.send();
	//var str = unescape(oBao.responseText);
	//alert(str);
	//window.location.reload();
	//$.getJSON("http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=reloadgroupright&groupname="+groupname,function(data){
		//alert(data.content);
		//$('#mashupContnt').html(data.content);
	//});
	//send_request(function(data){},"http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=reloadgroupright&groupname="+groupname,true);
}

function reloadPrj(){
	var form = document.forms[0];
	if(validateIsSelect(form.all, form.projectNames)){
		oBao = getXMLHttpRequest();
	    if(form.projectNames.length){
	    	for(var i=0;i<form.projectNames.length;i++){
	    		if(form.projectNames[i].checked){
	    			form.projectNames[i].checked=false;
					delprj(form.projectNames[i].value);
				}
	    	}
	    }else{
	    	if(form.projectNames.checked){
	    		form.projectNames.checked=false;
				delprj(form.projectNames.value);
			}
	    }
		reloadGroupRight(groupname);
		form.all.checked=false;
		alert('完成重启!');
	}else{
		alert("请选择要重启的工程");
	}
}
	
	function allselect(allobj,items){
	    var state = allobj.checked;
	    if(items.length){
	    	for(var i=0;i<items.length;i++){
	    		if(!items[i].disabled) items[i].checked=state;
	    	}
	    }else{
	    	if(!items.disabled) items.checked=state;
	    }
	}
	/*
	 * 判断是否选择了记录
     */
	function validateIsSelect(allobj,items){
	    var state = allobj.checked;
	    if(items.length){
	    	for(var i=0;i<items.length;i++){
	    		if(items[i].checked) return true;
	    	}
	    }else{
	    	if(items.checked) return true;
	    }
	    return false;
	}
function right(projectName){
	//var action = document.getElementById('ListForm').action+"!input.action?page.pageRequest=${page.pageRequest}";
	//if(id!=null)
	//	action +="&id="+id;
	 if(projectName!=null) $("#projectName").val(projectName);//document.getElementById('entityid').value=id;
	 else $("#projectName").val("");//document.getElementById('entityid').value="";
	 $('#ListForm').attr("action",'${ctx}/center/common/misd/project/right');
	 $("#ListForm").submit();
}

function command(projectName, method, msg){
	if(msg!=null&&!confirm('\n'+ msg)){
		return;
	}
	 if(projectName!=null) $("#projectName").val(projectName);
	 else $("#projectName").val("");
	 $('#ListForm').attr("action","${ctx}/center/common/misd/project/"+ method);
	 $("#ListForm").submit();
}

-->
</script>

</head>

<body class="bodycolor" style="margin-top: 2">
<form id="ListForm" name="ListForm" action="${ctx}/center/common/misd/project/" method="post" >
<input type="hidden" id='groupName' name='groupName' value="<%=groupName%>" >
<input type="hidden" id="projectName" name="projectName" value="" />
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
                <td width="35%"><b><quick:navigation name="工程信息查询列表" /></b></td>
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
		    <td class="TableContent" width="12%" align="center">工程名称</td>
		    <td class="TableData" width="38%" ><input type="text" name="filter_LIKE_projectName" value="${param['filter_LIKE_projectName']}"  class="SmallInput" style="width:97%" maxlength="30" size="30"/></td> 
		    <td class="TableContent" align="center" width="12%">工程路径</td>
		    <td class="TableData" colspan="3"><input type="text" name="filter_LIKE_prjFullName" value="${param['filter_LIKE_prjFullName']}"  class="SmallInput" style="width:97%" maxlength="30" size="30"/></td> 
		   </tr>
		   <tr>
			<td align="center" class="TableContent" colspan="6">
				<input type="button" class="SmallButton" name="b_act_query" onclick="search()" value="查&nbsp;&nbsp;询" style="width:60" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="SmallButton" name="b_act_clear" onclick="reload()" value="重&nbsp;&nbsp;置" style="width:60" />
				<acl:method action="deploy">
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="SmallButton" name="b_act_clear" onclick="command(null,'deploy')" value="部署工程" style="width:60" />
				</acl:method>
			</td>
		   </tr>		   		   
		</table>
			<!--过滤条件结束-->
		<br/>
        <table style="width:100%" align="center" class="TableBlock1" onmousemove="changeto()" onmouseout="changeback()" >
          <tr align="center">
            <td width="3%" class="TableHeader1">选择</td>
            <td width="3%" class="TableHeader1">序号</td>
            <td width="12%" class="TableHeader1"><a href="#" onclick="sort('projectName')">工程名称</a></td>
            <td width="16%" class="TableHeader1" colspan="3">操作</td>
            <td width="60%" class="TableHeader1"><a href="#" onclick="sort('prjFullName')">工程路径</a></td>
          </tr>
          <!-- 			-->
		  <c:forEach items="${page.result}" var="e" varStatus="s">
          <tr>
			<td class="TableData" align="center"><input type="checkbox" name="projectNames" value="${e.projectName}" /></td>
            <td class="TableData" align="center">${s.index+page.first}</td>
            <td class="TableData">${e.projectName}</td>
            <td class="TableData" align="center">
            <acl:method action="right">
            <a href="#" onclick="right('${e.projectName}')" title="点击进入权限设置">设置权限</a>
            </acl:method>
            </td>
            <td class="TableData" align="center">
            <acl:method action="removeRight">
            <a href="#" onclick="command('${projectName}','removeRight','删除后不可恢复，确定清空[${projectName}]工程的权限吗？')" title="点击开始清空权限">清空权限</a>
            </acl:method>
            </td>
            <td class="TableData" align="center">
            <acl:method action="logoutProject">
            <a href="#" onclick="command('${projectName}','logoutProject','确定注销[${projectName}]工程吗？')" title="点击开始注销工程">注销</a>
            </acl:method>
            </td>
            <td class="TableData">${prjFullName}</td>
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
 <tr>
   <td height="18">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableRound">
      <tr>
        <td width="8" class="TableLeft"></td>
        <td>
		<table style="width:100%;border-top: 0px;" align="center" class="TableBlock1" >
          <tr> 
            <td width="8%"><input type="checkbox" name="all" <c:if test="${fn:length(page.result)<1}">disabled="disabled"</c:if>
             onclick="javascript:allselect(this, this.form.projectNames)"/>全选</td>
              <td width="85%">
              <input name="visible" type="button"
              <c:if test="${fn:length(page.result)<1}">disabled="disabled"</c:if>
               class="SmallButton"  onClick="javascript:reloadPrj()" value=" 重 启 " />
              </td>
          </tr>
        </table>
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