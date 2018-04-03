<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="cn.misd.webapp.manage.model.*"%>
<%@ page import="cn.misd.database.DbOperate"%>
<%@ page import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base onmouseover="window.status;return true" />
<title>目录树</title>
<link  href="../css/tree.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
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

function doChoice(strUrl){
     //var tempID='';
     //var obj = parent.mainFrame.viewid;
     //parent.mainFrame.showHideView();
     //alert(obj);
     //parent.mainFrame.document.getElementById("viewid").style.display='none';
     //parent.mainFrame.document.getElementById("misdViewid").style.display='block';
	 parent.view.window.location.href=strUrl;
	 top.mainFrame.showHideView()
}
</SCRIPT>

</head>
<body bgcolor="#F0F0F0" text="#000000" onselectstart="return false;"  oncontextmenu="return false;" onLoad="window_onload();">
<div id="tree">
	<div id="menu">
	<script language="javascript" type="text/javascript">
	// treemenu 的参数意义依次为：资源目录，如"treemenu/image/";树名称；树图标；连接（可不填）；目标窗口（可不填，如果连接为空，此项无效）
	objTree	= new treemenu("","物资采购管理系统","usericon.gif","#","");
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
		if(fm.getCategory().equals("M"))
			out.println("add_item("+fm.getId()+","+fm.getFatherId()+",'"+fm.getName()+"','"+im+"','','','view');");
		else{
			if(fm.getType().equals("MISD"))
				out.println("add_item("+fm.getId()+","+fm.getFatherId()+",'"+fm.getName()+"','"+im+"','','#','','return parent.MISD."+fm.getExecuteObject()+"');");
			else
				out.println("add_item("+fm.getId()+","+fm.getFatherId()+",'"+fm.getName()+"','"+im+"','','#','','doChoice(\"../"+fm.getExecuteObject()+"\")');");
		}
	}
	%>
	// menu 的参数意义为：所要显示的树的父id；
	// 该函数返回树的html代码，需要由 write 函数输出。
	document.write(menu(0));
	//print_arr();
	</script>
	</div>
</div>
</body>
</html>