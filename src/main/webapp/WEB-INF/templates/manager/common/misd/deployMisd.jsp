<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>文件上传</title>
<!-- 共通head区域标签 begin-->
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<!-- 共通head区域标签 end-->
<!-- 外部CSS样式表以及JS引入 begin-->
<link rel="stylesheet" type="text/css" href="../../../../theme/1/style.css" />
<!-- 页面JS函数编写 begin -->
	<script>
	$(function(){
	    $("input[name=file]").focus().addClass("required");
	});
	</script>
</head>

<body class="bodycolor" style="margin-top: 2px">
<form id="inputForm" action="${ctx}/center/common/misd/misd!deploy.action" method="post" enctype="multipart/form-data" class="validate">
<div style="color:#ff0000;" align="center">
<s:actionmessage theme="mytheme"/>
</div>
 <table style="width:600px;" align="center" cellspacing="0" cellpadding="0">
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
		                <td width="35%"><b>世纪桥工程部署</b></td>
		                <td width="55%" align="right">
			                <input type="submit" class="SmallButton" name="b_act_subit"   style="width:60"  value="提&nbsp;&nbsp;交" />
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
					<td width="17%" align="left" class="TableContent">文件:</td>
					<td width="83%" align="left" class="TableData" ><s:file name="file" cssClass="SmallInput" cssStyle="width:80%;" label="选择部署的文件"/></td>			                       
				</tr>
			    <tr>
					<td width="17%" align="left" class="TableContent">说明:</td>
					<td width="83%" align="left" class="TableData" >只允许部署zip压缩的文件</td>			                       
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