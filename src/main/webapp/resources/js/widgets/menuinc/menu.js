// JavaScript Document
function chengstate(menuid,save)
{											//切换节点的开放/关闭
	menuobj	= eval("item"+menuid);
	obj		= eval("pr"+menuid); 
	
	if(menuobj.style.display == '')
	{
		menuobj.style.display	= 'none';
	}else{
		menuobj.style.display	= '';
	}//end if

    //增加此段的目的是当打开某一个一级目录树的节点时,其他的一级目录树节点则收合起来.
	//首先判别menuid的长度,目前已经用的一级目录树的值从1-10.
	//从第一个节点开始判别当前打开的节点是否是该节点或子节点,如是则只展开当前节点,其他节点收合.
	if (menuid.length < 3)
	{
	for (i=1;i<20;i++ )
		{   
			j=menuid.length;
			str=""+i.toString();
			if (menuid.substr(0,j)!=str){
				//判别对象是否存在
				if (objExists(str))
				{
					otherobj=eval("item"+str);
					otherobj.style.display = 'none';
					otherpr=eval("pr"+str);
					otherpr.className="mlevel11";
				}
			}
		}
	}

	switch (obj.className)
	{
		case "mlevel11":
			obj.className	= "mlevel12";
			break;
		case "mlevel12":
			obj.className	= "mlevel11";
			break;
		case "mlevel13":
			obj.className	= "mlevel14";
			break;
		case "mlevel14":
			obj.className	= "mlevel13";
			break;
		

		case "mlevel21":
			obj.className	= "mlevel22";
			break;
		case "mlevel22":
			obj.className	= "mlevel21";
			break;
		case "mlevel23":
			obj.className	= "mlevel24";
			break;
		case "mlevel24":
			obj.className	= "mlevel23";
			break;

		case "mlevel31":
			obj.className	= "mlevel32";
			break;
		case "mlevel32":
			obj.className	= "mlevel31";
			break;
		case "mlevel33":
			obj.className	= "mlevel34";
			break;
		case "mlevel34":
			obj.className	= "mlevel33";
			break;

		case "mlevel41":
			obj.className	= "mlevel42";
			break;
		case "mlevel42":
			obj.className	= "mlevel41";
			break;
		case "mlevel43":
			obj.className	= "mlevel44";
			break;
		case "mlevel44":
			obj.className	= "mlevel43";
			break;

		case "mlevel51":
			obj.className	= "mlevel52";
			break;
		case "mlevel52":
			obj.className	= "mlevel51";
			break;
		case "mlevel53":
			obj.className	= "mlevel54";
			break;
		case "mlevel54":
			obj.className	= "mlevel53";
			break;


	}//end switch
	//if(save!=false)
	//{
		//setupcookie(menuid);			//保存状态
	//}//end if
}//end funciton chengstaut


function initialize()
{											//取得cookie  设置节点的缩放,,初始化菜单状态
	var menu	= new Array();
	var menustr	= new String();
	
	if(checkCookieExist("menu"))
	{									//判断是否是是否已经保存过cookie
		menustr		= getCookie("menu");
		if(menustr.length>0)
		{								//判断长度是否合法
			menu	= menustr.split(",");
			for(i=0;i<menu.length;i++)
			{
				if(objExists(menu[i]))			
				{						//验证对象是否存在
					chengstate(menu[i],false);
				}//end if
			}//end for
			objExists(99);
		}//end if
	}//end if
}//end funciton setupstate

function objExists(objid)
{											//验证对象是否存在
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
