<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>世纪桥日志查看</title>
<!-- 共通head区域标签 begin-->
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<!-- 共通head区域标签 end-->
<!-- 外部CSS样式表以及JS引入 begin-->
<link rel="stylesheet" type="text/css" href="../../../theme/1/style.css" />
</head>
<body class="bodycolor" style="margin-top: 2">
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
		                <td width="35%"><b>世纪桥平台日志</b></td>
		                <td width="55%" align="right">
			                <input type="button" class="SmallButton" name="b_act_close"    style="width:60"  value="返&nbsp;&nbsp;回" onclick="history.back()" />
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
		  <fieldset ><legend>&nbsp;${title}&nbsp;</legend>
			 <table style="width:100%" align="center" class="TableBlock" >
			    <tr>
			    <td><textarea rows="22" cols="" class="SmallInput" style="width: 100%">${logMessage}</textarea> </td>
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
</body>
</html>