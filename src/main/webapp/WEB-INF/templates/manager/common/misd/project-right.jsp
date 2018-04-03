<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>角色信息登记</title>
<!-- 共通head区域标签 begin-->
<%@ include file="/WEB-INF/views/public/meta.jsp"%>
<!-- 共通head区域标签 end-->
<!-- 外部CSS样式表以及JS引入 begin-->
<link rel="stylesheet" type="text/css" href="${ctx}/theme/1/style.css" />
<link rel="stylesheet" type="text/css" href="../../../theme/1/style.css" />
	<link href="${ctx}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
	<link href="${ctx}/widgets/menuinc/css1.css" rel="stylesheet" type="text/css" />

<style type="text/css">
<!--
A:link {
	FONT-SIZE: 9pt; COLOR: #333333; TEXT-DECORATION: none
}
A:unknown {
	FONT-SIZE: 9pt; COLOR: #fff000; TEXT-DECORATION: none
}
A:visited {
	FONT-SIZE: 9pt; COLOR: #333333; TEXT-DECORATION: none
}
A:hover {
	FONT-SIZE: 9pt; COLOR: #ff0000; TEXT-DECORATION: none
}
HR {
	COLOR: #009900
}
A:hover {
	
}
-->
</style>
	<script><!--
		
   function selectDeptAll(obj){
     //var str = document.form1.roletext.innerHTML;
  //$('#'+obj.value+' input').attr("checkec",obj.checked);
	//alert($('#'+obj.value+' input').attr('checked'));
	if(obj.checked)
		selectRightAll(obj.value,2);
	else
		selectRightAll(obj.value,0);
		
	var external_inputs = document.getElementById(obj.value);
	var inputs = external_inputs.getElementsByTagName('input');
	if (inputs.length+""=="undefined"){
		inputs.checked = obj.checked;
	}
	else{
		for (var i=0;i < inputs.length;i++) {
		    var input = inputs.item(i);
		    input.checked =obj.checked;
		}
	}
    return;
   }
   
   function selectRightAll(obj,rightcode){
     //var str = document.form1.roletext.innerHTML;
  //$('#'+obj.value+' input').attr("checkec",obj.checked);
	//alert($('#'+obj.value+' input').attr('checked'));
	
	var external_inputs = document.getElementById(obj);
	var selects = external_inputs.getElementsByTagName('select');
	if (selects.length+""=="undefined"){
		return;
	}
	for (var i=0;i < selects.length;i++) {
		var select = selects.item(i);
		select.selectedIndex=rightcode;
		//var colls = select.options;
		//colls.
		//for(var j=0;j < colls.length;j++){
		//	alert(colls(j).value);
		//}
		//for(var j=0;i < select.length;j++){
		//	alert(select.options[j])
		//}
	}
/*	if (inputs.length+""=="undefined"){
		inputs.checked = obj.checked;
	}
	else{
		for (var i=0;i < inputs.length;i++) {
		    var input = inputs.item(i);
		    input.checked =obj.checked;
		}
	}*/
    return;
   }
   
   //在选择每个菜单前的checkbox的操作
   function selectAllOpe(obj){
   	  alert(obj);
      var obj2 = eval("document.inputForm." + obj.value);
	  obj2.checked = obj.checked;
   }
   
   function showGrid(obj1){
   var menucount = document.getElementById('menucount').value;
   if(menucount==null) 
   	  menucount=0;
   for(j=1;j<=menucount;j++){
   	str1="MENU"+j;
     if (obj1==str1){
	    var i;
	 	for(i=1;i<=menucount;i++){
			var obj2 = eval("document.all.chk" + i);
			var obj3 = eval("document.all.menulist" + i);
			if(i==j){
			obj2.style.display = 'block';
			obj3.style.display = 'block';
			}
			else{
			obj2.style.display = 'none';
			obj3.style.display = 'none';
			}
		}
     }
	 }
     return;
   }
   
   function selectButton(obj){
      var x = obj.name.substring(0,3);
      var obj2 = eval("document.inputForm." + x);
      //if (!obj.checked) obj2.checked = false;
      
      var str = document.form1.roletext.innerHTML;
      if (obj.checked){
         if (str.indexOf(obj.value)>=0){
         
         }else{
           document.form1.roletext.innerHTML = str + "," + obj.value;
         }
      }else{
         if (str.indexOf(obj.value)>=0){
           document.form1.roletext.innerHTML = str.replace(obj.value,"");
           document.form1.roletext.innerHTML = document.form1.roletext.innerHTML.replace(",,",",");
         }else{
         
         }
      }
   }
	--></script>
</head>

<body class="bodycolor" style="margin-top: 2">
<form id="inputForm" action="project!saveRight.action" method="post">
<input type="hidden" name="page.pageRequest" value="${page.pageRequest}" />
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
		                <td width="35%"><b><quick:navigation name="权限信息设置" /></b></td>
		                <td width="55%" align="right">
			                <input type="submit" class="SmallButton" name="b_act_subit"   style="width:60"  value="提&nbsp;&nbsp;交" />&nbsp;&nbsp;
			                <input type="button" class="SmallButton" name="b_act_close"    style="width:60"  value="取&nbsp;&nbsp;消" onclick="goreturn()" />
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
					<td width="10%" align="right" class="TableContent">角色名称</td>
					<td width="40%" align="left" class="TableData" ><input readonly="readonly" type="text" name="groupName" value="${groupName}" class="SmallInput"  style="width:230px"  maxlength="70" /></td>			                       
					<td width="10%" align="right" class="TableContent">工程名称</td>
					<td width="40%" align="left" class="TableData" ><input readonly="readonly" type="text" name="projectName" value="${projectName}" class="SmallInput"  style="width:230px"  maxlength="100" /></td>			                       
				</tr>
		      </table>
		  </fieldset>
		  <br/>
		  <fieldset ><legend>&nbsp;权限信息&nbsp;</legend>
			 <table style="width:100%;" align="center">
			    <tr>
					<td style="width:100%;" colspan="4">
					<div style="word-break:break-all;width:100%; overflow:auto;">
					${rightMenu}
					</div>
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