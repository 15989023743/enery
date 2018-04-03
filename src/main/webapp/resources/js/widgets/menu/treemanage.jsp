<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="cn.misd.database.DbOperate"%>
<%@ page import="java.util.List" %>
<%@ page import="cn.misd.webapp.manage.model.*"%>
<%@ page import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>目录树</title>
<link href="menu.css" rel="stylesheet" type="text/css">
<script src="menu.js" type="text/javascript"></script>
<script language="JavaScript">
function window_onload(){
	initialize();
}//
var judge=0;
function expandall(o){
	if (judge==0){
		closeAll();
		judge=1;
		o.src='icon-closeall.gif';
		o.alt='全部折叠';
	}
	else{
		openAll();
		judge=0;
		o.src='icon-expandall.gif';
		o.alt='全部展开';
	}
}
function exitsys(){
    var ask=confirm("你确定退出管理系统吗？\n\n点“确定”继续，点“取消”返回");
    if(ask){
        top.location.href="#";
    }
}
</SCRIPT>

</head>
<body bgcolor="#FFFFFF" text="#000000" onselectstart="return false;"  oncontextmenu="return false;" onLoad="window_onload();">
<table id=control width="100%" border="0" cellspacing="0" cellpadding="0" class="borderon">
  <tr>
    <td height="20" style="padding-top:3px"><nobr>&nbsp;目录树</nobr></td>
    <td width="20" align="center" valign="top"><img src="icon-expandall.gif" width="16" height="15" class="button" onclick="expandall(this)" vspace="2" alt="全部展开"></td>
  </tr>
</table>
<table border=0>
  <tr>
    <td>
	<script language="javascript" type="text/javascript">
	// treemenu 的参数意义依次为：资源目录，如"treemenu/image/";树名称；树图标；连接（可不填）；目标窗口（可不填，如果连接为空，此项无效）
	objTree	= new treemenu("","高校物资供应管理系统","usericon.gif","#","");
	// add_item 的参数意义依次为：该项编号，不能重复；父id，所属的上一层的编号；该项显示的内容；折叠时的图标；展开时的图标；网址或命令；指向的窗口; 点击事件
	<%
	String sql = "from " + FunctionManage.class.getName()+" as fm order by fm.id";
	List list =new DbOperate().getQuery(sql);
	FunctionManage fm = null ;
	for (Iterator it = list.iterator(); it.hasNext();) {
		fm = (FunctionManage) it.next();
		String im = "";
		if(fm.getFatherId().equals("0"))
			im = "parenticon.gif";
		out.println("add_item("+fm.getId()+","+fm.getFatherId()+",'"+fm.getName()+"','"+im+"','','','');");
	}
	%>
	// menu 的参数意义为：所要显示的树的父id；
	// 该函数返回树的html代码，需要由 write 函数输出。
	document.write(menu(0));
	//print_arr();
	</script>

    </td>
    <td>
<%
String category="",father_id="",depth="",name="";
category = request.getParameter("category");
if(category==null)
	category="";
father_id = request.getParameter("father_id");
if(father_id==null)
	father_id="";
depth = request.getParameter("depth");
if(depth==null)
	depth="";
name = (String)request.getParameter("functionname");
if(name==null)
	name="";
if( (name!=null) && !(name.equals("")) ){
	DbOperate dp = new DbOperate();
	String sql1= "from " + FunctionManage.class.getName()+" as fm where fm.father_id="+Long.parseLong(father_id)+" and fm.name=\'"+name+"\'";
	List list1 = dp.getQuery(sql1);
	if(list1.isEmpty()){
		FunctionManage fm1;
		if(category.equals("模块"))
			fm1 = new FunctionManage();
		else
			fm1 = new FunctionManage();
		fm1.setFatherId(father_id);
		fm1.setDepth(Long.parseLong(depth));
		fm1.setName(name);
		dp.saveOrUpdate(fm1);
		out.print("父ID:"+father_id+"\n\n");
		out.print("深度:"+depth+"\n\n");
		out.print("功能:"+name+"\n\n");
		out.print("增加成功!");
		response.sendRedirect("treemanage.jsp");
	}
	else{
		out.print("增加失败!已经存在!");
	}
}
%>
		<form action="" method="post" name="form">
		<ul>
		<li>种类:<input name="category" type="text" /></li>
		<li>父ID:<input name="father_id" type="text" /></li>
		<li>深度:<input name="depth" type="text" /></li>
		<li>功能:<input name="functionname" type="text" /></li>
		<li><input name="go" type="submit" value="增加"/></li>
		</ul>
		</form>
    </td>
  </tr>
</table>
</body>
</html>