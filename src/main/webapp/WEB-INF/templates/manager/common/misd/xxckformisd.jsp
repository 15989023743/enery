<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>

<%
	if(request.getSession().getValue("currentUser") != null){
		request.setCharacterEncoding("UTF-8");
		
		// 取得传递参数--分类名称(flmc);参数值(pvalue);标题名称(v_title)
		String flmc = request.getParameter("flmc");
		String pvalue = request.getParameter("pvalue");
		String v_title = request.getParameter("v_title");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			String sql = "select t.mc, t.prjname, t.seflowid, t.sepname,t.pvalue from jwzhpt_pz.t_pz_sys_misdprj t where t.flmc = '"+ flmc +"' and t.xgbs in (1, 2, 5) order by t.px";
			//conn = connJwzhpt();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
%>
<html>
<head>
<title><%=v_title%></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="MISD,详细信息,查看">
<meta http-equiv="description" content="MISD详细信息">

<link rel="stylesheet" type="text/css" href="../theme/1/style.css">
<link rel="stylesheet" type="text/css" href="../theme/1/submenu.css">


<script Language="JavaScript">
//-------- 卡片点击链接相关页面 -------
function submenulink(menuid,args)
{
  var targetelement;
  targetelement=document.getElementById(menuid);
  targetelement.className="active";
  
  var links=document.getElementsByTagName("A");
  for (i=0; i<links.length; i++)
  {
	  temp_srcelement=links[i];
	  if (temp_srcelement.id!=menuid)
	  {
		  if (temp_srcelement.className=="active")
		  {
		     temp_srcelement.className="";
			 break;
		  }    
	  }
  }
  
  // 显示目的MISD信息
  var misdarray = args.split("_");
    
  var misdprjname = misdarray[0];
  var misdflowid = misdarray[1];
  var misdpname = misdarray[2];
  var misdpvalue = misdarray[3];

  var url = "";
  if((misdprjname == "PZRKGLSYRK")&&(misdpvalue != "undefined")){
  	url = "misd.jsp?prjname="+misdprjname+"&flowid="+misdflowid+"&pname="+misdpname+"&pvalue=<%=pvalue%>,"+misdpvalue;
  }
  else{
  	url = "misd.jsp?prjname="+misdprjname+"&flowid="+misdflowid+"&pname="+misdpname+"&pvalue=<%=pvalue%>";
  }

  document.getElementById("iFrameInfo").setAttribute("src",url);
}

// 关闭页面
 function closeWindow(){
 	window.close();
 }
</script>
</head>

<body>
<div id="tabs1">
    <ul>   
<% 
 	String prjname = "";
 	String flowid = "";
 	String pname = "";
 	String extpvalue = "";
 	for(int i = 1; rs.next(); i++){
 		if( i == 1){
 			prjname = rs.getString("prjname");
 			flowid = rs.getString("seflowid");
 			pname = rs.getString("sepname");
 			
 			if(rs.getString("pvalue") != null){
 				extpvalue = rs.getString("pvalue");
 				out.println("<li><a href='#' id='menu"+i+"' onclick=\"submenulink(this.id,'"+rs.getString("prjname")+"_"+rs.getString("seflowid")+"_"+rs.getString("sepname")+"_"+rs.getString("pvalue")+"');\" class='active'><span>"+rs.getString("mc")+"</span></a></li>");
 			}
 			else{
 				out.println("<li><a href='#' id='menu"+i+"' onclick=\"submenulink(this.id,'"+rs.getString("prjname")+"_"+rs.getString("seflowid")+"_"+rs.getString("sepname")+"');\" class='active'><span>"+rs.getString("mc")+"</span></a></li>");
 			}
 		}
 		else{
 			if(rs.getString("pvalue") != null){
 				extpvalue = rs.getString("pvalue");
 				out.println("<li><a href='#' id='menu"+i+"' onclick=\"submenulink(this.id,'"+rs.getString("prjname")+"_"+rs.getString("seflowid")+"_"+rs.getString("sepname")+"_"+rs.getString("pvalue")+"');\"><span>"+rs.getString("mc")+"</span></a></li>");
 			}
 			else{
 				out.println("<li><a href='#' id='menu"+i+"' onclick=\"submenulink(this.id,'"+rs.getString("prjname")+"_"+rs.getString("seflowid")+"_"+rs.getString("sepname")+"');\"><span>"+rs.getString("mc")+"</span></a></li>");
 			}
 		}
 	}
 %>
	</ul>
	<div align="right">
	<input type="button" class="SmallButton02" value="关闭窗口" onclick="closeWindow();"/>&nbsp;
	</div>
</div>
<table style="width:100%" align=center cellspacing="0" cellpadding="0">
<tr>
<td height="18">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableRound">
    <tr>
    <td width="8" class="TableLeft"></td>
    <td>
		<table style="width:100%" align=center align=center class="TableBlock1" >
		<tr>
		<td>
		<IFRAME src="misd.jsp?prjname=<%=prjname%>&flowid=<%=flowid%>&pname=<%=pname%>&pvalue=<%=pvalue%><%if(extpvalue != "")out.println(","+extpvalue);%>"
		 frameBorder=0 marginwidth=0 marginheight=0 id="iFrameInfo" scrolling="no"
		 width="100%"  onload="this.height=iFrameInfo.document.body.scrollHeight">
		</IFRAME>
		</td>
		</tr>
		</table>
     </td>
     <td width="8" class="TableRight"></td>
	 </tr>
	 </table>
</td>
</tr>
<tr>
<td height="35">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableRound">
    <tr>
    <td width="12" height="35" class="TableFooterLeft"></td>
    <td class="TableFooter">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="small">
        <tr>
        <td>&nbsp;</td>
        <td>
			<table border="0" align="right" cellpadding="0" cellspacing="0" class="small">
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
</td>
</tr>
</table>
</body>
</html>
<%
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
				//rs1.close();
			}
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}
%>