<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglibs.jsp"%>
<%@ include file="common.jsp" %>
<HTML>
<HEAD>
<TITLE>在线用户信息 </TITLE>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/js/xmlhttp.js" type="text/javascript"></script>
<style type="text/css">
A:link{TEXT-DECORATION:none ;Color:#000000},A:active{TEXT-DECORATION:none ;Color:#000000},A:visited{TEXT-DECORATION:none ;Color:#000000}A:hover{TEXT-DECORATION: underline;Color:#4455aa}

A:link {text-decoration: none; color: #000099; }
A:visited {text-decoration: none; color: #000099}
A:active {text-decoration: none; color:black}
A:hover {text-decoration: underline; color: red}

input {
	BORDER-BOTTOM: #017861 1px solid; BORDER-LEFT: #017861 1px solid; BORDER-RIGHT: #017861 1px solid; BORDER-TOP: #017861 1px solid; FONT-SIZE: 12px; HEIGHT: 20px
}

BODY{
FONT-SIZE: 12px;COLOR: #000000;FONT-FAMILY:  宋体; scrollbar-face-color: #DEE3E7;scrollbar-highlight-color: #FFFFFF;scrollbar-shadow-color: #DEE3E7;scrollbar-3dlight-color: #D1D7DC;scrollbar-arrow-color:  #006699;scrollbar-track-color: #EFEFEF;scrollbar-darkshadow-color: #98AAB1;
}
TD{
font-family: 宋体;font-size: 12px;line-height: 15px;
}
th
{
background-color: #6487DC;color: white;font-size: 12px;font-weight:bold;
}
td.TableTitle2
{
background-color: #E4E8EF;
}
td.TableBody1
{
background-color: #C7D8FA;
}
td.TableBody2
{
background-color: #E4E8EF;
}
td.TableBody3
{
background-color: #FFFFFF;
}
td.TopDarkNav
{
}
td.TopLighNav
{
}
td.TopLighNav1
{
}
td.TopLighNav2
{
background-color:#FFFFFF
}
.tableBorder1
{
width:75%;border: 1px; background-color: #6595D6;
}
.tableBorder2
{
width:75%;border: 1px #DEDEDE solid; background-color: #EFEFEF;
}

#TableTitleLink A:link, #TableTitleLink A:visited, #TableTitleLink A:active {	COLOR: #FFFFFF;	TEXT-DECORATION: none;}#TableTitleLink A:hover {	COLOR: #FFFFFF;	TEXT-DECORATION: underline;} input, select, textarea{
font-family:Tahoma,Verdana,宋体; font-size: 12px; line-height: 15px;}
}
.normalTextSmall 
{ 
    font-size : 11px;
    color : #000000; 
    font-family: Verdana, Arial, Helvetica, sans-serif;
}
.tableBorder3
{
width:100%;border: 1px; background-color: #6595D6;
}
.tableBorder3
{
width:100%;border: 1px; background-color: #6595D6;
}
</style>
</HEAD>

<script language="javascript">
var ip = "<%=v_ip+":"+v_port%>";
var webname = "<%=v_webname%>";
function GetResult() 
{ 
	var oBao = getXMLHttpRequest();
	oBao.open("post","http://"+ip+"/misd/PrjReonloadAction.do?function=userList",false); 
	oBao.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	oBao.send();
	var str = unescape(oBao.responseText)
	
	var arraystr = str.split("||");
	//document.write("<html>");
	//document.write("<head>");
	//document.write("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
	//document.write("</head>");
	document.write("<table cellpadding=3 cellspacing=1 align=center class=tableborder1 style=\"width:80%\">");
	document.write("<tr height=30><th valign=middle colspan=2 align=left>&nbsp;现在的应用服务器是："+ip+"</th><th valign=middle  align=center><input type='button' value=' 刷 新 ' onclick=prjRefresh() style=\"cursor:hand\"></th></tr>");
	document.write("<tr height=24 ><td valign=middle class=tablebody2 align=center width=5%><b>序号</b></td><td valign=middle class=tablebody2 align=center width=40%><b>用户名</b></td><td valign=middle class=tablebody2 align=center  width=55%><b>IP</b></td></tr>");
	for(var i=0;i<arraystr.length-1;i++){
		var arraystr1  = arraystr[i].split("^");
		document.write("<tr height=24 ><td valign=middle class=tablebody2 align=center>"+(i+1)+"</td><td valign=middle class=tablebody2 align=left>"+arraystr1[1]+"</td><td valign=middle class=tablebody2 align=center>"+arraystr1[0]+"</td></tr>");
	}
	document.write("</table>");
} 
</script>
<BODY>
<script language="javascript">
GetResult();
</script>

</BODY>
<script language=javascript>
<!--
//预处理(主要因为js中的confirm只有“确定”和“取消”两个按钮名称，现在用js中执行脚本语言的方法执行vbs的msgbox功能函数)
/*@cc_on @*/
//判断系统和jscript版本
if (@_win32 && @_jscript_version>=5){
	window.confirm = function(str)  //Author: meizz 重定义confirm函数
	{
		str=str.replace(/\'/g, "'&chr(39)&'").replace(/\r\n|\n|\r/g, "'&VBCrLf&'");//
		execScript("n = msgbox('"+ str +"',36, '提示消息确认窗口')", "vbscript");//执行VB函数
		//return(n);
		if (n==6) {
			return true;
		}
		else {
			return false;
		}
	}
} 
 -->
</script>
<script language="javascript">
function delprj(str) 
{   
	if (confirm("真的要删除该工程吗？"))
	{ 
		var oBao = getXMLHttpRequest();
		oBao.open("post","http://"+ip+"/"+webname+"/PrjReonloadAction.do?function=delPrj&prjname="+str,false); 
		oBao.setRequestHeader("Content-Type", "text/html; charset=utf-8")
		oBao.send();
		//var str = unescape(oBao.responseText)
		window.location.reload();
	}
} 
function prjRefresh() 
{   
	window.location.reload();
} 
</script>
</HTML>
