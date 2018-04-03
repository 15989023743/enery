<%@ page contentType="text/html;charset=utf-8"
language="java" 
import="java.util.List" 
import="cn.misd.database.DbOperate" 
import="cn.misd.webapp.manage.model.*" 
errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统功能管理</title>
</head>
<%
String father_id="",depth="",name="";
father_id = request.getParameter("father_id");
if(father_id==null)
	father_id="";
depth = request.getParameter("depth");
if(depth==null)
	depth="";
name = (String)request.getParameter("functionname");
if(name==null)
	name="";
if( (father_id!=null) && !(father_id.equals("")) ){
	DbOperate dp = new DbOperate();
	String sql1= "from " + FunctionManage.class.getName()+" as fm where fm.father_id="+Long.parseLong(father_id)+" and fm.functionname=\'"+name+"\'";
	List list1 = dp.getQuery(sql1);
	if(list1.isEmpty()){
		FunctionManage fm1 = new FunctionManage();
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
<body>
<form action="" method="post" name="form">
<ul>
<li>父ID:<input name="father_id" type="text" /></li>
<li>深度:<input name="depth" type="text" /></li>
<li>功能:<input name="functionname" type="text" /></li>
<li><input name="go" type="submit" value="增加"/></li>
</ul>
</form>
</body>
</html>
