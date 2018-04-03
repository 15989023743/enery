<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="cn.misd.webapp.manage.model.*"%>
<%@ page import="cn.misd.database.DbOperate"%>
<%@ page import="java.util.*"%>
<script LANGUAGE="JavaScript" ></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="gb2312">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="robots" content="all" />
<meta name="author" content="cjh" />
<meta name="Copyright" content="MISD联盟2005" />
<meta name="description" content="MISD 2005 By Design Mode V1.0" />
<meta name="keywords" content="世纪桥 , MISD , MIS , 信息管理系统 , 信息管理系统开发 , MISD 2005 By Design Mode V1.0" />
<link rel="icon" href="../images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon" />
<link  href="../css/tree.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
<link href="menu.css" rel="stylesheet" type="text/css">
<script LANGUAGE="JavaScript" src="../scripts/tree.js"></script>
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
<title>目录树</title>
<base onmouseover="window.status='您好,这是系统功能按钮!';return true;">
</head>
<body onselectstart="return false;"  oncontextmenu="return false;" onLoad="window_onload();">
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
	// add_item 的参数意义依次为：该项编号，不能重复；父id，所属的上一层的编号；该项显示的内容；折叠时的图标；展开时的图标；网址或命令；指向的窗口
	<%
	String sql = "from " + FunctionManage.class.getName()+" as fm order by fm.id";
	List list =new DbOperate().getQuery(sql);
	FunctionManage fm = null ;
	for (Iterator it = list.iterator(); it.hasNext();) {
		fm = (FunctionManage) it.next();
		String im = "";
		if(fm.getFatherId().equals("0"))
			im = "parenticon.gif";
		out.println("add_item("+fm.getId()+","+fm.getFatherId()+",'"+fm.getName()+"','"+im+"','','return parent.MISD."+fm.getExecuteObject()+"','');");
	}
	%>
	// menu 的参数意义为：所要显示的树的父id；
	// 该函数返回树的html代码，需要由 write 函数输出。
	document.write(menu(0));
	//print_arr();
	</script>

    </td>
  </tr>
</table>
</body>
</html>
