// JavaScript Document
/******************************************************************************
*???????????????? for ALL 2.0.3
*??????????????????Javascript??????????????
*??????????menu ??itemExists ?? chengstate ?? setupcookie ?? initialize ?? objExists ?? saveCookie ?? getCookie ?? checkCookieExist ?? deleteCookie 
*??????????
	menu				???????????? ??
	itemExists			????????????childer??
	chengstate			????????????????open/close????
	setupcookie			????????????????Cookie ??
	initialize			??????????????????????Cookie????
	objExists			?????????????????? ??
	saveCookie			??????????Cookie ??
	getCookie			??????????Cookie ??
	checkCookieExist	??????Cookie???????? ??
	deleteCookie		??????Cookie ??
*??    ??????????
*??????????
	QQ:			46163020
	msn:		yuenshui@hotmail.com
	Email:		woaini4651@yahoo.com.cn
	????????:	www.yemaweb.com
	????????????????????????????
*??????????2004??12??28??
*??????????1
*??????????2004??7??14??
*??    ????????????????????????????????????????????????????????????
			????????????????????????????????????????????
			????????????????????????????????????????????????????????????????????
			????????????????????????????????????????????
**********************************************   ?????????? ????????????
2004??12??28??
v2.0.3
??????????????????????????????????????????????????js??????
????????????????????????????????????????????????????????????

2004??12??09??
v2.0.1
????????????????????????bug


2004??10??15?? 
v2.0 
????????????????????
????????????????????????
????????????????????????????
*******************************************************************************/
treedata	= new Array();
path		= "";
function treemenu(tree_path,tree_name,tree_ico,url,target)
{
	path	= tree_path;
	name	= tree_name;
	ico	= tree_ico;
	
	if(url!="" && url!=null)
		//document.write("<div><img src='"+path+ico+"' align=top border=0><a style='font-size:15px;font-weight:bold' href='"+url+"' target='"+target+"'>"+name+"</a></div>");
		document.write("<div><img src='"+path+"/icon-expandall.gif' align=top border=0 width='16' height='15' class='button' onclick='expandall(this)' vspace='2' alt='全部展开'><a style='font-size:15px;font-weight:bold' href='"+url+"' target='"+target+"'>"+name+"</a></div>");
	else
		//document.write("<div style='font-size:15px;font-weight:bold'><img src='"+path+ico+"' align=top border=0>"+name+"</div>");
		document.write("<div style='font-size:15px;font-weight:bold'><img src='"+path+"/icon-expandall.gif' align=top border=0 width='16' height='15' class='button' onclick='expandall(this)' vspace='2' alt='全部展开'>"+name+"</div>");

}//end function treemenu
//                 0 id      1 ??id      2  ????    3  ????   4  ????????    5 ????????     6 ????????  7 ????????
function add_item(tree_id,tree_prarent,tree_name,tree_close_ico,tree_open_ico,tree_url,tree_target,tree_onclick)
{
	if(tree_close_ico=="")
		tree_close_ico="folder.gif";
	if(tree_open_ico=="")
		tree_open_ico="folder-open.gif";
	//                                       0 id      1 ??id      2  ????    3  ????   4  ????????    5 ????????     6 ????????  7 ????????
	treedata[treedata.length]	= new Array(tree_id,tree_prarent,tree_name,tree_url,tree_close_ico,tree_open_ico,tree_target,tree_onclick);
}//end function add_item

function print_arr()
{
	var i;
	var j;
	var n	= treedata.length;
	var m	= treedata[0].length;
	
	for(var i=0; i<n; i++)
	{
		for(j=0; j<m;j++)
		{
			//"  treedata["+i+"]["+j+"]="
			document.write(" "+j+":"+treedata[i][j]);
		}//end for
		
		document.write(" <br>\n");
	}//end for
	
}//end function print_arr

function menu(id)						//??????
{
	var currdata	= new Array();
	var i			= 0;
	var printstr	= "<table border='0' cellspacing='0' cellpadding='0'>\n";
	var listtype	= "";
	var menutype	= "";
	
	for(; i<treedata.length; i++)
	{					//??????????????
		if(treedata[i][1]==id)	currdata[currdata.length] = treedata[i];
	}//end for	
	
	
	for(var i=0; i<currdata.length; i++)
	{					//??????????????????
		if(itemExists(currdata[i][0]))
		{						//????????????????
			if(i==currdata.length-1)
			{			// ????????
				menutype	= "menu3";
				listtype	= "list1";
			}
			else
			{
				menutype	= "menu1";
				listtype	= "list";
			}//end if
			onmouseup	= "chengstate('"+currdata[i][0]+"')";
		}
		else
		{					//??????????
			if(i==currdata.length-1)
			{			// ????????
				menutype	= "file1";
			}
			else
			{
				menutype	= "file";
			}//end if
			onmouseup	= "";
		}//end if

		if(currdata[i][3]!="" && currdata[i][3]!=null)
		{
			menuname	= "<a name='"+currdata[i][0]+"' id='"+currdata[i][0]+"'></a><a id='a"+ currdata[i][0]+"' href='"+currdata[i][3]+"' onclick= 'setColor(\"a"+currdata[i][0]+"\");"+currdata[i][7]+"' target='"+currdata[i][6]+"'>"+currdata[i][2]+"</a>";
		}
		else
		{
			menuname	= currdata[i][2];
		}//end if

		ico			= "<img src='"+path+currdata[i][4]+"' id='ico"+currdata[i][0]+"' align=middle border=0>";
		printstr	+= "<tr><td id='pr"+currdata[i][0]+"' valign=middle class="+menutype+" onMouseUp="+onmouseup+">"+ico+"<span onMouseOver='over_str(this)' valign='bottom' onMouseOut='out_str(this)' class='item'> "+menuname+" </span> </td></tr>\n";
		printstr	+= "<tr id='item"+currdata[i][0]+"' ondblclick='chengstatedbl(\""+currdata[i][0]+"\")' style='display:none'><td class="+listtype+">"+menu(currdata[i][0])+"</td></tr>\n";

	}//end for
	printstr	+= "</table>\n";
	return printstr;
}//end function menu

function itemExists(id)
{
	for(var i=0;i<treedata.length;i++)
	{
		if(treedata[i][1]==id)return true;
	}//end for
	return false;
}//end function itemExists

function closeAll()
{											// ????????????????
	var len	= treedata.length;
	for(var i=0; i<len; i++)
	{					//??????????????????
		obj		= eval("pr"+treedata[i][0]);
		if(obj.className == "menu2" || obj.className == "menu4")
		{						//????????????????
			chengstate(treedata[i][0]);
		}//end if
	}//end for
	
}//end function closeAll

function openAll()
{											// ????????????????
	var len	= treedata.length;
	for(var i=0; i<len; i++)
	{					//??????????????????
		obj		= eval("pr"+treedata[i][0]);
		if(obj.className == "menu1" || obj.className == "menu3")
		{						//????????????????
			chengstate(treedata[i][0]);
		}//end if
	}//end for
}//end function openAll

function over_str(obj)
{
	obj.style.background	= "#EEEEEE";
	obj.style.border		= "1px solid #999999";
}//end function over

function out_str(obj)
{
	obj.style.background	= "";
	obj.style.border		= "1px solid #FFFFFF";
}//end function out

function chengstatedbl(id)
{
	chengstate(id,true);
	event.cancelBubble = true;
}//end function chengstatedbl

function chengstate(menuid,save)
{											//??????????????/????
	//alert("menuid:"+menuid+"save:"+save);
	menuobj	= eval("item"+menuid);
	obj		= eval("pr"+menuid);
	ico		= eval("ico"+menuid);
	var len	= treedata.length;
	for(var i=0; i<len; i++)
	{
		if(treedata[i][0]==menuid)
		{
			break;
		}//end if
	}//end for
	
	if(menuobj.style.display == '')
	{
		menuobj.style.display	= 'none';
		ico.src					= path+treedata[i][4];
	}else{
		menuobj.style.display	= '';
		ico.src					= path+treedata[i][5];
	}//end if
	switch (obj.className)
	{
		case "menu1":
			obj.className	= "menu2";
			break;
		case "menu2":
			obj.className	= "menu1";
			break;
		case "menu3":
			obj.className	= "menu4";
			break;
		case "menu4":
			obj.className	= "menu3";
			break;
	}//end switch
	
	if(save!=false)
	{
		setupcookie(menuid);			//????????
	}//end if
}//end funciton chengstaut

function setupcookie(menuid)
{										//????cookie  ????????????
	var menu	= new Array();
	var menustr	= new String();
	menuOpen	= false;
	if(checkCookieExist("menu"))
	{									//????????????????????????cookie
		menustr		= getCookie("menu");
		//alert(menustr);
		if(menustr.length>0)
		{								//????menu????????????????????????????
			menu	= menustr.split(",");
			for(var i=0;i<menu.length;i++)
			{
				if(menu[i]==menuid)
				{						//????????????????????????????
					menu[i]='';
					menuOpen	= true;
				}//end if
			}//end for
			if(menuOpen==false)menu[i] = menuid;
		}else{
			menu[0]	= menuid;
		}//end if
	}else{
		menu[0]	= menuid;
	}//end if
	menu.sort();
	menustr	= menu.join(",");
	menustr	= menustr.replace(",,",",");
	if(menustr.substr(menustr.length-1,1)==',')menustr = menustr.substr(0,menustr.length-1);		//?????????? ","
	if(menustr.substr(0,1)==',')menustr = menustr.substr(1,menustr.length-1);		//?????????? ","
	saveCookie("menu",menustr,1000);
	//alert(menustr);
	//deleteCookie("menu");
}//end function setupcookie

function initialize()
{											//????cookie  ??????????????,,??????????????
	var menu	= new Array();
	var menustr	= new String();
	
	if(checkCookieExist("menu"))
	{									//????????????????????????cookie
		menustr		= getCookie("menu");
		if(menustr.length>0)
		{								//????????????????
			menu	= menustr.split(",");
			for(var i=0;i<menu.length;i++)
			{
				if(objExists(menu[i]))			
				{						//????????????????
					chengstate(menu[i],false);
				}//end if
			}//end for
			objExists(99);
		}//end if
	}//end if
}//end funciton setupstate

function objExists(objid)
{											//????????????????
	try
	{
		obj = eval("item"+objid);
	}//end try
	catch(obj)
	{
		return false;
	}//end catch
	
	if(typeof(obj)=="object")
	{
		return true;
	}//end if
	return false;
}//end function objExists
//--------------------------------------------------????????????????????????  ????Cookie ????
function saveCookie(name, value, expires, path, domain, secure)
{											// ????Cookie
  var strCookie = name + "=" + value;
  if (expires)
  {											// ????Cookie??????, ??????????
     var curTime = new Date();
     curTime.setTime(curTime.getTime() + expires*24*60*60*1000);
     strCookie += "; expires=" + curTime.toGMTString();
  }//end if
  // Cookie??????
  strCookie +=  (path) ? "; path=" + path : ""; 
  // Cookie??Domain
  strCookie +=  (domain) ? "; domain=" + domain : "";
  // ????????????????,????????????
  strCookie +=  (secure) ? "; secure" : "";
  document.cookie = strCookie;
}//end funciton saveCookie

function getCookie(name)
{											// ????????????????Cookie??, null????Cookie??????
  var strCookies = document.cookie;
  var cookieName = name + "=";  // Cookie????
  var valueBegin, valueEnd, value;
  // ????????????Cookie????
  valueBegin = strCookies.indexOf(cookieName);
  if (valueBegin == -1) return null;  // ??????Cookie
  // ????????????????
  valueEnd = strCookies.indexOf(";", valueBegin);
  if (valueEnd == -1)
      valueEnd = strCookies.length;  // ????????Cookie
  // ????Cookie??
  value = strCookies.substring(valueBegin+cookieName.length,valueEnd);
  return value;
}//end function getCookie

function checkCookieExist(name)
{											// ????Cookie????????
  if (getCookie(name))
      return true;
  else
      return false;
}//end function checkCookieExist

function deleteCookie(name, path, domain)
{											// ????Cookie
  var strCookie;
  // ????Cookie????????
  if (checkCookieExist(name))
  {										    // ????Cookie??????????????
    strCookie = name + "="; 
    strCookie += (path) ? "; path=" + path : "";
    strCookie += (domain) ? "; domain=" + domain : "";
    strCookie += "; expires=Thu, 01-Jan-70 00:00:01 GMT";
    document.cookie = strCookie;
  }//end if
}//end function deleteCookie
var colorid = '0';//上一个ID
function setColor(id){
	if(colorid==id) return;
	if(colorid !='0'){
		document.getElementById(colorid).style.color='#000000';
		//document.getElementById(id).style.background	= "";
		//document.getElementById(id).style.border		= "0px solid #FFFFFF";
		//out_str(document.getElementById('pr'+String(colorid).substring(1)));
	}
	document.getElementById(id).style.color='#FF0000';
	//document.getElementById(id).style.background	= "#EEEEEE";
	//document.getElementById(id).style.border		= "0px solid #999999";
	//over_str(document.getElementById('pr'+String(id).substring(1)));
	colorid = id;
	//alert(colorid);
}