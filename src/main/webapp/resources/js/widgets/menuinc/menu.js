// JavaScript Document
function chengstate(menuid,save)
{											//�л��ڵ�Ŀ���/�ر�
	menuobj	= eval("item"+menuid);
	obj		= eval("pr"+menuid); 
	
	if(menuobj.style.display == '')
	{
		menuobj.style.display	= 'none';
	}else{
		menuobj.style.display	= '';
	}//end if

    //���Ӵ˶ε�Ŀ���ǵ���ĳһ��һ��Ŀ¼���Ľڵ�ʱ,������һ��Ŀ¼���ڵ����պ�����.
	//�����б�menuid�ĳ���,Ŀǰ�Ѿ��õ�һ��Ŀ¼����ֵ��1-10.
	//�ӵ�һ���ڵ㿪ʼ�б�ǰ�򿪵Ľڵ��Ƿ��Ǹýڵ���ӽڵ�,������ֻչ����ǰ�ڵ�,�����ڵ��պ�.
	if (menuid.length < 3)
	{
	for (i=1;i<20;i++ )
		{   
			j=menuid.length;
			str=""+i.toString();
			if (menuid.substr(0,j)!=str){
				//�б�����Ƿ����
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
		//setupcookie(menuid);			//����״̬
	//}//end if
}//end funciton chengstaut


function initialize()
{											//ȡ��cookie  ���ýڵ������,,��ʼ���˵�״̬
	var menu	= new Array();
	var menustr	= new String();
	
	if(checkCookieExist("menu"))
	{									//�ж��Ƿ����Ƿ��Ѿ������cookie
		menustr		= getCookie("menu");
		if(menustr.length>0)
		{								//�жϳ����Ƿ�Ϸ�
			menu	= menustr.split(",");
			for(i=0;i<menu.length;i++)
			{
				if(objExists(menu[i]))			
				{						//��֤�����Ƿ����
					chengstate(menu[i],false);
				}//end if
			}//end for
			objExists(99);
		}//end if
	}//end if
}//end funciton setupstate

function objExists(objid)
{											//��֤�����Ƿ����
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
