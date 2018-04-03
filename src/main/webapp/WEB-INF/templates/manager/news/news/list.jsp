<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新闻信息管理</title>
<!-- 共通head区域标签 begin-->
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<!-- 共通head区域标签 end-->
<!-- 外部CSS样式表以及JS引入 begin-->
<link rel="stylesheet" type="text/css" href="../../../theme/1/style.css" />
<script src="${ctx}/js/table_mvc.js" type="text/javascript"></script>
<!-- 页面JS函数编写 begin -->
<script language="javascript">
<!--
	function actionEvent(methodname,status){
		var form = document.forms[0];
		if(validateIsSelect(form.all, form.newsids)){
			form.action="${ctx}/center/news/news/"+methodname;
			form.status.value=status;
			//form.method.value=methodname;
			form.submit();
		}else{
			alert("请选择要操作的记录");
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

-->
</script>

</head>

<body class="bodycolor" style="margin-top: 2">
<form id="ListForm" name="ListForm" action="${ctx}/center/news/news/" method="post" >
<input type="hidden" id="entityid" name="id" value="" />
<input type="hidden" id="status" name="status" value="" />
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
                <td width="35%"><b><quick:navigation name="新闻信息管理" /></b></td>
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
		    <td nowrap class="TableContent" width="12%" align="center">新闻名称</td>
		    <td nowrap class="TableData" width="38%" ><input type="text" name="filter_LIKE_name" value="${param['filter_LIKE_name']}"  class="SmallInput" style="width:97%" maxlength="30" size="30"/></td> 
		    <td nowrap class="TableContent" width="12%" align="center">新闻频道</td>
		    <td nowrap class="TableData" width="38%">
		    <input type="text" name="filter_LIKE_newsChannel.name" value="${param['filter_LIKE_newsChannel.name']}"  class="SmallInput" style="width:97%" maxlength="30" size="30"/>
		    </td> 
		   </tr>
		   <tr>
			<td align="center" class="TableContent" colspan="6">
				<input type="button" class="SmallButton" name="b_act_query" onclick="search()" value="查&nbsp;&nbsp;询" style="width:60" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="SmallButton" name="b_act_clear" onclick="reload()" value="重&nbsp;&nbsp;置" style="width:60" />
				<acl:method action="create">
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="SmallButton" name="b_act_new" onclick="create()" value="新增新闻" style="width:60" />
				</acl:method>
			</td>
		   </tr>		   		   
		</table>
			<!--过滤条件结束-->
		<br/>
        <table style="width:100%" align="center" class="TableBlock1" onmousemove="changeto()" onmouseout="changeback()">
          <tr align="center">
            <td width="3%" class="TableHeader1">序号</td>
            <td width="3%" class="TableHeader1"><a href="#" onclick="sort('id')">新闻ID</a></td>
            <td width="6%" class="TableHeader1" colspan="1">操作</td>
            <td width="20%" class="TableHeader1"><a href="#" onclick="sort('name')">新闻名称</a></td>
            <td width="10%" class="TableHeader1"><a href="#" onclick="sort('newsChannel.name')">所属频道</a></td>
            <td width="5%" class="TableHeader1"><a href="#" onclick="sort('createdate')">发布日期</a></td>
            <td width="5%" class="TableHeader1"><a href="#" onclick="sort('clickcount')">人气指数</a></td>
            <td width="5%" class="TableHeader1"></td>
            <td width="5%" class="TableHeader1"><a href="#" onclick="sort('commend')">推荐</a></td>
 			<td width="5%" class="TableHeader1"></td>
          </tr>
          <!-- 			-->
		  <c:forEach items="${page.result}" var="e" varStatus="s">
          <tr>
            <td class="TableData" align="center">${s.index+page.first}</td>
			<td class="TableData" align="center"><input type="checkbox" name="newsids" value="${e.id}" /></td>
            <td class="TableData" align="center">
            <acl:method action="update">
            <a href="#" onclick="update('${e.id}')">修改</a>
            </acl:method>
            <acl:method action="delete">
            、<a href="#" onclick="del('${e.id}')">删除</a>
            </acl:method>
            </td>
            <td class="TableData">${e.name}</a></td>
            <td class="TableData">${e.newsChannel.name}</td>
            <td class="TableData">${e.createdate}</td>
            <td class="TableData">${e.clickcount}</td>
            <td class="TableData"><c:choose><c:when test="${e.visible==true}">已发布</c:when><c:otherwise>未发布</c:otherwise></c:choose></td>
            <td class="TableData"><c:choose><c:when test="${e.commend==true}">推荐</c:when><c:otherwise>--</c:otherwise></c:choose></td>
            <td class="TableData"><div align="center"><a href="${ctx}/center/news/news/comment?newsid=${e.id}">评论管理</a></div></td>
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
             onclick="javascript:allselect(this, this.form.newsids)"/>全选</td>
              <td width="85%">
              <acl:method action="visible">
              <input name="visible" type="button"
              <c:if test="${fn:length(page.result)<1}">disabled="disabled"</c:if>
               class="SmallButton"  onClick="javascript:actionEvent('visible',true)" value=" 播出 "/> 
              &nbsp;&nbsp;<input name="disable" type="button" class="SmallButton" 
              <c:if test="${fn:length(page.result)<1}">disabled="disabled"</c:if>
              onClick="javascript:actionEvent('visible',false)" value=" 停播 "/> 
              </acl:method>
              <acl:method action="commend">
              &nbsp;&nbsp;<input name="commend" type="button" class="SmallButton" 
              <c:if test="${fn:length(page.result)<1}">disabled="disabled"</c:if>
              onClick="javascript:actionEvent('commend',true)" value=" 推 荐 "/> 
              &nbsp;&nbsp;<input name="uncommend" type="button" class="SmallButton" 
				<c:if test="${fn:length(page.result)<1}">disabled="disabled"</c:if>
				onClick="javascript:actionEvent('commend',false)" value=" 不推荐 "/>
			  </acl:method>
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