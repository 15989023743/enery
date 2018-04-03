<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gdwz.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="author" content="MISD HUASOFT">
	<meta name="keywords" content="《世纪桥》--软件开发平台">
	<meta http-equiv="description" content="技术支持商">
    <link rel="stylesheet" type="text/css" href="${ctx}/theme/1/style.css">
    <script type='text/javascript' src='${ctx}/dwr/interface/DESPlus.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/interface/guardService.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script> 
    <script language="javascript" src="${ctx}/js/jquery.js"></script>
	<script language="javascript" src="${ctx}/js/commons.js"></script>
	<script language="javascript" src="${ctx}/js/misd.js"></script>

	<script language="javascript" src="${ctx}/js/MisdGrid.js"></script>
	<script language="javascript" src="${ctx}/js/MisdDateCheckout.js"></script>
	<!-- 
	<script language="javascript" src="${ctx}/js/15idto18.js"></script>
	<script language="javascript" src="${ctx}/js/Checkgmsfhm.js"></script>
	 -->
	<script language="javascript" src="${ctx}/js/MisdOpenShow.js"></script>
	<script language="javascript" src="${ctx}/js/MisdClickFmButton.js"></script>

  </head>
<body >
<%
/*
String prjname=request.getParameter("prjname");
String flowid=request.getParameter("flowid");
String pname=request.getParameter("pname");
String pvalue=request.getParameter("pvalue").replaceAll("%20","  ");//当动态条件有空格需要转换
//out.println("<script>alert('"+prjname+"=="+flowid+"=="+pname+"=="+pvalue+"')</script>");
//String yhm = "root",yhmm="cls";
*/
%>
<SCRIPT type=text/javascript>
window.onload = function() {
	DESPlus.doDecrypt("${webmisdpassword}",function(data) {
		Login('<%=SpringSecurityUtils.getCurrentUserName().toUpperCase().equals("ROLEANONYMOUS")?"GUEST":SpringSecurityUtils.getCurrentUserName().toUpperCase()%>',data); 
		ExecuteMisdFrm("${prjname}","${flowid}","${pname}","${pvalue}");
	});
}
</SCRIPT>
<script language="javascript">
<!-- 
function Login (Username,Password)
{
	var vsuccss=form1.vsuccss.value;
	if(vsuccss=="0"){
		bSuccess = MISD1.Logon(Username,Username,Password); //MISD登录方法
		if(bSuccess==false){
			alert("请求错误!!");
			form1.vsuccss.value="0";
		}
		else{
			bSuccess1 = MISD1.SetMisdSize(1,1);//设计MISD打开表单比例
			form1.vsuccss.value="1";
		}
  	}
}
-->
</script>
<script language="vbscript">
sub ExecuteMisdFrm(prjname,flowid,pn,pk)
	If MISD1.GetCurrentMode() = 1 or MISD1.GetCurrentMode() = 2 Then
		dim pname(10),pvalue(10)
		arr=split(pn,",")
		for n=0 to ubound(arr)
		pname(n)=arr(n)
		next
		arr1=split(pk,",")
		for n=0 to ubound(arr1)
		pvalue(n)=arr1(n)
		next
		Call MISD1.ExecuteFmButton2(prjname,flowid,pname,pvalue)
		'Call MISD1.SetButtonName("关  闭", "返  回")
	End If
form1.flowids.value=flowid
form1.prjnames.value=prjname
end sub
</script>
<script language="javascript"><!--
//循环替换字符串的字符
function strReplace(destStr,oldStr,newStr)
  {
    var foundPos=0;
	var tmpstr = "";
	if(destStr==null)   return "";
    tmpStr = destStr;
    foundPos = tmpStr.indexOf(oldStr);
    while (foundPos>=0)
    {
      tmpStr = tmpStr.substring(0,foundPos) + newStr + tmpStr.substring(foundPos + oldStr.length,tmpStr.length);
      foundPos = tmpStr.indexOf(oldStr,foundPos+newStr.length);
    }
    return tmpStr;
  }

function acceptData(antenna,id){
	if(id!=null && id!="" && old_tid!=id){
		if("${pname}"=="TID"){
			ExecuteMisdFrm("${prjname}","${flowid}","${pname}",id);
			old_tid = id;
		}
		else if(itd_nInputID != null && tid_right=="3") {
			MISD1.SetInputFocuse(itd_nInputID);
			MISD1.SetInputRight(itd_nInputID,3);
			MISD1.SetInputValue(itd_nInputID,id);
			MISD1.SetInputRight(itd_nInputID,2);
			top.topbar.rfidWebView1.enter();
			old_tid = id;
		}
		else if("${pname}"=="GUARD"){
			guardService.writeGuardOfUser("${prjname}","${pvalue}",id,"<%=SpringSecurityUtils.getCurrentUserName().toUpperCase()%>",function(data){
				if(data.toLowerCase()=="access"){
					ExecuteMisdFrm("${prjname}","${flowid}","${pname}","${pvalue}");
					//window.setTimeout("",100);
				}
				ExecuteMisdFrm("${prjname}","${flowid}","${pname}","${pvalue}");
				old_tid = id;
			});
		}
		else if(guard_table != null && guard_table !="" ){
			guardService.writeGuard(guard_table,id,function(data){
				ExecuteMisdFrm("${prjname}","${prjname}","${flowid}","${pname}","${pvalue}");
				old_tid = id;
			});
		}
		/*
		else if("${prjname}"=="CRM" && "${flowid}"=="13" ){
			guardService.saveGuard(id,function(data){
				ExecuteMisdFrm("${prjname}","${flowid}","${pname}","${pvalue}");
				old_tid = id;
			});
		}*/
	}
}
var old_tid;
var itd_nInputID;
var tid_right;
var guard_table;
function initFm(){
	//var inputCount = MISD1.GetInputCount();
	//alert(inputCount);
	for(i=0;i<=1000;i++){
		var strInputMane = MISD1.GetFieldNameByInput(i);
		if(strInputMane=="GUARD"){
			guard_table = MISD1.getInputValue(i);
			old_tid="";
			return;
		}
		if(strInputMane=="TID"){
			itd_nInputID = i;
			tid_right = MISD1.GetInputRight(itd_nInputID);
			old_tid="";
			return;
		}
	}
}

--></script>
<script language="javascript" for="MISD1" event="InputOnDblClk(nInputID)" > //处理点击文本框
strID= MISD1.GetInputType(nInputID);
if(strID==5){
	var olddate=MISD1.GetInputValue(nInputID);
	var paramArr = new Array();
    olddate=strReplace(olddate,'/','-');
	paramArr[0] = olddate;
	paramArr[1] = true;

	var _h = 310;
	var datetime = window.showModalDialog('${ctx}/js/calendar/calendars.htm',paramArr,"dialogWidth:238px;dialogHeight:"+_h+"px;help:no;status:no;resizable:no;");
	if(datetime=="cancel"){
	}else{
        //datetime=strReplace(datetime,"-","/");
		MISD1.SetInputValue(nInputID,datetime);
	}
}
</script>

<script language="javascript" for="MISD1" event="ClickFmButton(strButtonName)" > 
//MisdClickFmButton(strButtonName);//  处理是否查询出结果,如没有则打印提示信息 lwg 20090117
</script>

<script language="javascript" for="MISD1" event="IuputLostFocus(nInputID)" > //输入框失去焦点时执行
//DateCheckout(nInputID);//数据校验
</script>
<script language="javascript" for="MISD1" event="ClickFmButtonBefore(strButtonName)" > //点击按钮
var flowid=form1.flowids.value;  
var prjname=form1.prjnames.value;  
//alert(strButtonName);
ButDictionary(strButtonName);
if(strButtonName=="关闭浏览器/64266"){
top.opener=null;
top.close();
}
if (strButtonName=="关闭表/57602"){
	var height = window.document.body.scrollHeight;
	//alert(height);
	if(window.parent != null){
		var frame = window.parent.document.getElementById("iFrameInfo");
		if(frame != null){
			frame.height = height;
		}
	}
}
</script>
<script language="javascript" for="MISD1" event="GridCellFocus(PrjN,nFlowd,nRow,nCol,nGrid,sValue)" > //处理电子表格
form1.nGrids.value=nGrid; //取到电子表格对象ID存储 lwg
form1.nRows.value=nRow;
form1.nCols.value=nCol;
//MisdGrid();//进入 MISD电子表格事件资源  lwg 2008-12-18
</script>
<script language="javascript" for="MISD1" event="EnterForm()" > //弹出页面中的表单自动适应页面大小
var height = window.document.body.scrollHeight;
if(window.parent != null){
	var frame = window.parent.document.getElementById("iFrameInfo");
	if(frame != null){
		frame.height = height;
	}
}
initFm();
</script>
<div style="z-index:-1" align=center>
<%@ include file="misd_obj.jsp" %>
</div>
<SCRIPT Language="JavaScript" FOR=window EVENT=onunload>
try{
	var vsuccss1=form1.vsuccss.value;
	if(vsuccss1=="1"){
		MISD1.Logoff();
	}
}catch(e){}
</SCRIPT>
<form name="form1" >
	<input type="hidden" name="vsuccss" value="0">
	<input type="hidden" name="flowids" value="">
	<input type="hidden" name="prjnames" value="">
	<input type="hidden" name="nGrids" value="">
	<input type="hidden" name="nRows" value="">
	<input type="hidden" name="nCols" value="">
</form>
<!-- 
<input type="button" name="test" value="test" onclick="MISD1.LoadCache('ROOT','cls')" />
 -->
</body>
</html>
