<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新闻</title>
<!-- 共通head区域标签 begin-->
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<!-- 共通head区域标签 end-->
<!-- 外部CSS样式表以及JS引入 begin-->
	<link rel="stylesheet" type="text/css" href="../../../theme/1/style.css" />
	<script type="text/javascript" src="${ctx}/js/tiny_mce/tiny_mce.js"></script>
<!-- 页面JS函数编写 begin -->
<script language="javascript" type="text/javascript">
tinyMCE.init({
	language : "zh_cn",
	mode : "textareas",
	theme : "advanced",
	//width : "500",
	plugins : "table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,zoom,flash,searchreplace,print,contextmenu",
	theme_advanced_buttons1_add_before : "save,separator",
	theme_advanced_buttons1_add : "fontselect,fontsizeselect",
	theme_advanced_buttons2_add : "separator,insertdate,inserttime,preview,zoom,separator,forecolor,backcolor",
	theme_advanced_buttons2_add_before: "cut,copy,paste,separator,search,replace,separator",
	theme_advanced_buttons3_add_before : "tablecontrols,separator",
	theme_advanced_buttons3_add : "emotions,iespell,flash,advhr,separator,print",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_path_location : "bottom",
	plugin_insertdate_dateFormat : "%Y-%m-%d",
	plugin_insertdate_timeFormat : "%H:%M:%S",
	extended_valid_elements : "a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]",
	external_link_list_url : "example_data/example_link_list.js",
	external_image_list_url : "example_data/example_image_list.js",
	flash_external_list_url : "example_data/example_flash_list.js"
});

	$(function(){
	    //$("#newsChannelid").addClass("required");
		$("input[name=name]").focus().addClass("required");
		$("textarea[name=content]").attr("minlength",10);
	});
</script>
</head>

<body class="bodycolor" style="margin-top: 2">
<form id="inputForm" action="${ctx}/center/news/news/save" method="post" class="validate">
<input type="hidden" name="id" value="${entity.id}" />
<quick:backPageUrl />
 <table style="width:100%" align="center" cellspacing="0" cellpadding="0">
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
		                <td width="35%"><b><quick:navigation name="新闻" /><c:choose><c:when test="${entity.id==null}">创建 </c:when><c:otherwise>修改</c:otherwise></c:choose></b></td>
		                <td width="55%" align="right">
			                <input type="submit" class="SmallButton" name="b_act_subit"   style="width:60"  value="提&nbsp;&nbsp;交" />&nbsp;&nbsp;
			                <input type="button" class="SmallButton" name="b_act_close"    style="width:60"  value="取&nbsp;&nbsp;消" onclick="doreturn()" />
		                </td>
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

<!--业务表格区域开始-->
 <tr>
   <td height="18">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableRound">
      <tr>
        <td width="8" class="TableLeft"></td>
        <td class="TableLine1">
        <table width="100%" border="0" cellspacing="0" cellpadding="3" class="TableRound">
        <tr>
        <td>
		  <fieldset ><legend>&nbsp;基本信息&nbsp;</legend>
			 <table style="width:100%" align="center" class="TableBlock" >
			    <tr>
					<td width="10%" align="right" class="TableContent">新闻名称</td>
					<td width="90%" align="left" class="TableData" ><input type="text" name="name" value="${entity.name}" class="SmallInput"  style="width:90%"  maxlength="40" /></td>			                       
				</tr>
			    <tr>
					<td align="right" class="TableContent">新闻频道</td>
					<td align="left" class="TableData" >
					<quick:dicttree 
						id="newsChannelid"
						name="newsChannel.id" 
						value="entity.newsChannel.id"
						dictname="NewsChannel" 
						condition=" visible=1 "
						cssClass="SmallInput required" 
						cssStyle="width:230px" 
						maxlength="30"/>
					</td>			                       
				</tr>
			    <tr>
					<td align="right" class="TableContent">新闻内容</td>
					<td align="left" class="TableData" >
					<textarea rows="23" cols="80" name="content" style="width:90%;" class="SmallInput required">${entity.content}</textarea>
					</td>
				</tr>
		      </table>
		  </fieldset>

	    </td>
	    </tr> 
	   </table>
	   </td>
	   <td width="8" class="TableRight"></td>
      </tr> 
   </table>
   </td>
 </tr>
<!--业务表格区域结束-->

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
	            <td>&nbsp;&nbsp;</td>
	            <td>
		            <table border="0" align="right" cellpadding="0" cellspacing="0">
		                <tr>
		                  <td width="40">&nbsp;</td>
		                  <td width="45">&nbsp;</td>
		                  <td width="45">&nbsp;</td>
		                  <td width="40">&nbsp;</td>
		                  <td width="100">&nbsp;</td>
		                  <td width="40">&nbsp;</td>
		                </tr>
		            </table>
	            </td>
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