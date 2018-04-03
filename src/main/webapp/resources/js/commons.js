jQuery.ajaxSetup({cache:false});
var basePath,ctx=getBasepath();// 应用程序的根路径 */
function getBasepath() {
	if(top.ctx){
		basePath = top.ctx;
		return top.ctx;
	}
	var basepath= document.location.pathname.substring(document.location.pathname.indexOf('/')+1) ;
	basepath= basepath.substring(0,basepath.indexOf('/')) ;
	return "/"+basepath+"";
}

	/*
	 * function confirmDel( message ){ if( message == null ){ message =
	 * "您确定要删除一条记录吗？"; } if( window.confirm( message ) ){ $.blockUI({ message: '<p style="font-size: 16px;font-weight: bold;">正在执行，请稍候...</p>'
	 * }); return true; }else{ return false; } }
	 */
	
	
	
	/***************************************************************************
	 * FUNCTION: dictTreeOnClick Discription: 字典树弹出共用对话框
	 **************************************************************************/ 
	function dictTreeOnClick(clickFieldId,clickFieldMcId,dictName,condition)
	{
		
		var records = new Array();
		records["clickField"]=clickFieldId;
	 	records["mcField"]=clickFieldMcId;
	 	// records["typeid"]=document.getElementById("typeid").value;
		var url=ctx+"/manager/common/dict/dialog/intoTree.htm?treetype=001&dictName="+dictName+"&condition="+condition;
		if (url.indexOf(" ")>=0){
			url=encodeURI(url);
			url=encodeURI(url);
		}
		winOpen(url,records,430,700);
	} 

	// 多选树中的"代码选择"按钮按下时
	function dictMultiSelectOnClick(clickFieldId,clickFieldMcId,dictName,condition){
		alert(clickFieldId);
		var records = new Array();
		records["clickField"]=clickFieldId;
	 	records["mcField"]=clickFieldMcId;

		// var value=document.all(dictField).value;
		// 初始化时的KEY值
		// records["initKey"]=value;
		var url=ctx+"/manager/common/dict/dialog/intoTree.htm?treetype=002&dictName="+dictName+"&condition="+condition;
		openMultiSelectModalDialog(url,records,350,700);
	}
	
	/***************************************************************************
	 * FUNCTION: dictListOnClick Discription: 字典列表弹出共用对话框
	 **************************************************************************/ 
	function dictListOnClick(clickFieldId,clickFieldMcId,dictName,condition)
	{
		var records = new Array();
		records["clickField"]=clickFieldId;
	 	records["mcField"]=clickFieldMcId;
	 	// records["typeid"]=document.getElementById("typeid").value;
	 	// alert(records["typeid"]);
	 	var url=ctx+"/manager/common/dict/dialog/dictlistmain.htm?dictName="+dictName+"&condition="+condition+"&otherCondition=";
		if (url.indexOf(" ")>=0){
			url=encodeURI(url);
			url=encodeURI(url);
			url=encodeURI(url);	
		}
		openDictListModalDialog(url,records,700,505);
	} 

		/**
		 * 功能 字典和通用字典列表控件弹出窗口 输入参数 URL URL地址 arg 参数 width 页面宽度 height 页面高度 返回
		 * 选择好的人员信息或字典选择的值
		 */
		function openDictListModalDialog(URL,arg,width, height) {
			var returnValue=window.showModalDialog(URL,arg,"dialogWidth="+width+"px;dialogHeight="+height+"px;help:no;scroll=yes;status=0;");
			if(returnValue==null)
			  {
			 	return false;
			  }
			  var clickField=returnValue["clickField"];
			  var clickvalue=returnValue["value"];
			  var mcvalue=returnValue["mcvalue"];
			  var mcField=returnValue["mcField"];
			  document.getElementById(mcField).value=mcvalue;
			  document.getElementById(clickField).value=clickvalue;
			  var relateOption = document.getElementById(mcField).getAttribute("relateOption");
			  eval(relateOption);
			  // document.all(mcField).value=mcvalue;
			  // document.all(clickField).value=clickvalue;
		}
		
		/**
		 * 功能 字典和人员控件弹出窗口 输入参数 URL URL地址 arg 参数 width 页面宽度 height 页面高度 返回
		 * 选择好的人员信息或字典选择的值
		 */
		function openDictTreeModalDialog(URL,arg,width, height) {
			var returnValue=window.open(URL,arg,"dialogWidth="+width+"px;dialogHeight="+height+"px;help:no;scroll=yes;status=0;");
			if(returnValue==null)
			  {
			 		 return false;
			  }
			
			  var clickField=returnValue["clickField"];
			  var clickvalue=returnValue["value"];
			  var mcField=returnValue["mcField"];
			  var mcvalue=returnValue["mcvalue"];
			  document.getElementById(mcField).value=mcvalue;
			  document.getElementById(clickField).value=clickvalue;
			  // document.all(mcField).value=mcvalue;
			  // document.all(clickField).value=clickvalue;
		}
		
		/**
		 * 功能 多选字典弹出 输入参数 URL URL地址 arg 参数 width 页面宽度 height 页面高度 返回 多选字典中所选择的值
		 */
		function openMultiSelectModalDialog(URL,arg,width, height) {
			var returnValue=window.showModalDialog(URL,arg,"dialogWidth="+width+"px;dialogHeight="+height+"px;help:no;scroll=yes;status=0;");
				if(returnValue != null && returnValue != undefined){
				/*
				 * //KEY var codeKey=returnValue["codeKey"]; //中文名 var
				 * codeValue=returnValue["codeValue"]; //返回值 var
				 * valueObj=returnValue["valueObj"]; //清空页面相关项赋值
				 * document.all(codeValue).value="";
				 * document.all(codeKey).value=""; //为页面相关项赋值
				 * document.all(codeValue).value = valueObj.value;
				 * document.all(codeKey).value = valueObj.key;
				 */
				
				  var clickField=returnValue["clickField"];
				  var clickvalue=returnValue["value"];
				  var mcField=returnValue["mcField"];
				  var mcvalue=returnValue["mcvalue"];
				  document.getElementById(mcField).value=mcvalue;
				  document.getElementById(clickField).value=clickvalue;
				
				
			}
		}
		
		/**
		 * 功能 选择其它字典的弹出窗口 输入参数 URL URL地址 arg 参数 width 页面宽度 height 页面高度 返回
		 * 输入的其它字典值
		 */
		function openInputOtherDictValueModalDialog(URL,arg,width, height) {
			var returnValue=window.showModalDialog(URL,arg,"dialogWidth="+width+"px;dialogHeight="+height+"px;help:no;scroll=yes;status=0;");
			if(returnValue==null)
			  {
			 		 return false;
			  }
			  var selectId=returnValue["selectId"];
			  var key=returnValue["key"];
			  var value=returnValue["value"];
			  var list=document.all(selectId);
			  var size=returnValue["size"];
			  var isEdit=returnValue["isEdit"];
			  var nameField=returnValue["nameField"];
			  var cerrKey=returnValue["cerrKey"];
			  var len=list.length;
			  person_Array[selectId]=new personInfo(key,value);
			  for(var i=0;i<len;i++){
			  // 首先遍历下拉框
			  	if(list.options[i].value==key){
			  		list.options[i].selected=true;
			  		  if(nameField!="" && cerrKey!=list.options[i].value){
			  		  	  list.fireEvent("onchange");
					  }
			  		if(isEdit=="true"){
			  		// 可编辑时
			  		var inputCopy=selectId+"_personSelect"
			  		document.all(inputCopy).value=list.options[i].text;
			  		}
			  		return;
			  	}
			  }
			  // 下拉框的长度没有变化时，说明是第一次进行选择
			  if(size==len){
			  list.options[len]=new Option(value,key);
			  list.options[len].selected=true;
			  list.fireEvent("onchange");
			  	if(isEdit=="true"){
			  		// 可编辑时
			  		var inputCopy=selectId+"_personSelect"
			  		document.all(inputCopy).value=list.options[len].text;
			  		}
			  }
			  else{
			  	  // 已选择过，需要覆盖上一个选项
			  	 list.options[len-1]=new Option(value,key);
			  	 list.options[len-1].selected=true;
			  	 list.fireEvent("onchange");
			  	 	if(isEdit=="true"){
			  		// 可编辑时
			  		var inputCopy=selectId+"_personSelect"
			  		document.all(inputCopy).value=list.options[len-1].text;
			  		}
			  }
		}

		/***********************************************************************
		 * FUNCTION: newWindow PARAMETERS: doc -> Document to open in the new
		 * window hite -> Height of the new window wide -> Width of the new
		 * window bars -> 1-Scroll bars = YES 0-Scroll Bars = NO resize ->
		 * 1-Resizable = YES 0-Resizable = NO CALLS: NONE RETURNS: New window
		 * instance
		 **********************************************************************/
		function newWindow (doc, hite, wide, bars, resize) {
		    var winNew="_blank";
		    var opt="toolbar=0,location=no,left=50,top=50,directories=0,status=0,menubar=0,";
		    opt+=("scrollbars="+bars+",");
		    opt+=("resizable="+resize+",");
		    opt+=("width="+wide+",");
		    opt+=("height="+hite);
		    winHandle=window.open(doc,winNew,opt);
		    return;
		}
		
		function winOpen (strURL,strName,width,height)
		{
		    theWindow = window.open (strURL,strName,"width="+width+" height="+height+" scrollbars=yes left="+(1024-width)/2+" top="+(768-height)/2);	
		    if (theWindow.opener == null) theWindow.opener = window;
		    if (window.focus) theWindow.focus();
		}
		
		// 验证邮件
		function verifyEmailAddress(strEmail){
		  var myReg = /^[_a-zA-Z0-9_-_._-]+@([_a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,3}$/;
		  return myReg.test(strEmail);
		}
		/***********************************************************************
		 * *** 判断是否为日期数据 例子:itIsDate("2009-10-7" , "-") *****
		 **********************************************************************/
		function itIsDate(DateString , Dilimeter) 
		{ 
		  if (DateString==null) return false; 
		  if (Dilimeter=='' || Dilimeter==null) 
		   Dilimeter = '-'; 
		  var tempy=''; 
		  var tempm=''; 
		  var tempd=''; 
		  var tempArray; 
		  if (DateString.length<8 && DateString.length>10) 
		    return false;    
		  tempArray = DateString.split(Dilimeter); 
		  if (tempArray.length!=3) 
		   return false; 
		  if (tempArray[0].length==4) 
		  { 
		   tempy = tempArray[0]; 
		   tempd = tempArray[2]; 
		  } 
		  else 
		  { 
		   tempy = tempArray[2]; 
		   tempd = tempArray[1]; 
		  } 
		  tempm = tempArray[1]; 
		  var tDateString = tempy + '/'+tempm + '/'+tempd+' 8:0:0';// 加八小时是因为我们处于东八区
		  var tempDate = new Date(tDateString); 
		  if (isNaN(tempDate)) 
		   return false; 
		 if (((tempDate.getUTCFullYear()).toString()==tempy) && (tempDate.getMonth()==parseInt(tempm)-1) && (tempDate.getDate()==parseInt(tempd))) 
		  { 
		   return true; 
		  } 
		  else 
		  { 
		   return false; 
		  } 
		} 

		/***********************************************************************
		 * *** 求字符串的字节长度 *****
		 **********************************************************************/
		function byteLength(paraString) 
		{
		 var strValue =new String(paraString);
		 var strLength = strValue.length;
		 var numLength =0;
		  for (globle_i =0 ; globle_i<strLength;globle_i++){
		    var ASCIIValue =strValue.charCodeAt(globle_i);
		    if ( ASCIIValue > 0 && ASCIIValue < 127 )  
		      numLength = numLength + 1 
		    else
		     numLength = numLength + 2
		  }
		  return numLength;
		}

		/***********************************************************************
		 * *** 去除空格 *****
		 **********************************************************************/
		function trim(stringToTrim) {
			return stringToTrim.replace(/^\s+|\s+$/g,"");
		}
			
		function ltrim(stringToTrim) {
			return stringToTrim.replace(/^\s+/,"");
		}
				
		function rtrim(stringToTrim) {
			return stringToTrim.replace(/\s+$/,"");
		}

		String.prototype.trim = function() {return this.replace(/^\s+|\s+$/g,"");}
		String.prototype.ltrim = function() {return this.replace(/^\s+/,"");}
		String.prototype.rtrim = function() {return this.replace(/\s+$/,"");}
		
		/***********************************************************************
		 * *** 复选框的全选与取消 *****
		 **********************************************************************/
		function CheckAll(form){
			var length = form.itemId.length;
			var tocheck = form.chkall.checked;
			if (length)
				for (var i=0; i<length; i++){ 
					if (form.itemId[i].disabled != true){
						form.itemId[i].checked = tocheck;
					} 			
				}
			else {
				if (form.itemId.disabled !=true){
					form.itemId.checked = tocheck;
				}
			}
		}
		
		/***********************************************************************
		 * *** 转化字符串 *****
		 **********************************************************************/
		function conversion_code(paraString)
		{
			strResult = "";
			j=0;
			for (i=0;i<paraString.length;i++){ 
				Char = String1.charAt(i);
				if (Char=="'"){
				    strResult = strResult + paraString.substring(j,i)+"\\"+"\'";
				    j=i+1;
				 } 
			return strResult;
			}
		}

		/***********************************************************************
		 * *** 数字输入控制处理 *****
		 **********************************************************************/
		function InputIntNumberCheck(){
			// 为支持IE 或 Netscape
			var theEvent=window.event || arguments.callee.caller.arguments[0]; 
			var elm ;
			var ver = navigator.appVersion;
			if (ver.indexOf("MSIE") != -1){  // IE
				if ( !((theEvent.keyCode >=48)&&(theEvent.keyCode<=57))){
					theEvent.keyCode=0;
				}
			}else{ // Netscape
				if ( !((theEvent.which >=48)&&(theEvent.which<=57))){
					theEvent.stopPropagation();
					theEvent.preventDefault();
				}
			}
			//
		}

		/***********************************************************************
		 * *** 有小数点数字输入控制处理 *****
		 **********************************************************************/
		function InputLongNumberCheck(){
			if ( !((window.event.keyCode >=48)&&(window.event.keyCode<=57) || window.event.keyCode ==46)){
				window.event.keyCode=0;
			}

			var theEvent=window.event || arguments.callee.caller.arguments[0]; 
			var elm ;
			var ver = navigator.appVersion;
			if (ver.indexOf("MSIE") != -1){  // IE
				if (!((theEvent.keyCode>=48)&&(theEvent.keyCode<=57) || theEvent.keyCode ==46)){
					theEvent.keyCode=0;
				}
			}else{ // Netscape
				if ( !((theEvent.which >=48)&&(theEvent.which<=57) || theEvent.which ==46)){
					theEvent.stopPropagation();
					theEvent.preventDefault();
				}
			}
		}
		
		/***********************************************************************
		 * *** 获取cookie内容 *****
		 **********************************************************************/
		function getCookie( name ){
			var nameOfCookie = name + "=";
			var x = 0;
			while ( x <= document.cookie.length ){
				var y = (x+nameOfCookie.length);
				if ( document.cookie.substring( x, y ) == nameOfCookie ) {
					if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
					endOfCookie = document.cookie.length;
					return unescape( document.cookie.substring( y, endOfCookie ) );
				}
				x = document.cookie.indexOf( " ", x ) + 1;	
				if ( x == 0 ) break;
			}
			return "";
		}

		/***********************************************************************
		 * *** 设置cookie内容、过期时间 *****
		 **********************************************************************/
		function setCookie( name, value, expiredays ) {
			var todayDate = new Date(); 
			todayDate.setDate( todayDate.getDate() + expiredays ); 
			document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" 
		} 
		/***********************************************************************
		 * *** 检查输入字符 ***** '// islegality：输入的字符是否为给定的字符 '//返回值：bool
		 **********************************************************************/
		function islegality(checkstrpass){
		var checkokpass="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
		for (i=0; i<checkstrpass.length; i++) {
		       ch=checkstrpass.charAt(i);
		       for (j=0;j<checkokpass.length; j++){
		        if (ch==checkokpass.charAt(j))
		        break;
		        }
		      if (j==checkokpass.length){
			  return false; // 函有特别字符时返回false
		      break;
		        }
		  }
		   return true;
		}
		/**
		 * 检查输入是否中文
		 */
		function ck_chinese(value_) {
		  return escape(value_).indexOf("%u")!=-1 
		}
		
		
		/**
		 * 功能 替换指定位置的字符 输入参数 strObj 要操作的字符串 replacetext 替换的字符 返回 替换后的字符串
		 */
		function replacePos(strObj, pos, replacetext){
			if(strObj!=null&&replacetext!=null&&strObj.length>=replacetext.length){
			   var str = strObj.substr(0, pos-1) + replacetext + strObj.substring(pos, strObj.length);
			   return str;
		   }
		}
		

/**
 * 提示信息显示3秒隐藏
 */
$(document).ready(function() {
	$("#message").fadeOut(3000);
});

/**
 * 点击“查询”按钮执行
 * 
 * @returns
 */
function search() {
	$("#pageSize").attr("name","pageSize");
	$("#pageNo").val('1');
	$("#ListForm").submit();
}

/**
 * 点击“重围”按钮执行
 * 
 * @returns
 */
function reload(){
	document.location.href = $('#ListForm').attr("action");
}

/**
 * 点击“取消”按钮执行
 * 
 * @returns
 */
function doreturn(){
	var form = document.forms["inputForm"];
	form.setAttribute("class","");
	form.action = getInputFormAction()+"return.htm";
	form.submit();
}

/**
 * 跳转到指定页码(分页)
 * 
 * @param pageNo
 * @returns
 */
function jumpPage(pageNo){
	$("#pageSize").attr("name","pageSize");
	$("#pageNo").val(pageNo);
	$("#ListForm").submit();
}

/**
 * 指定字段排序
 * 
 * @param orderBy
 * @param defaultOrder
 * @returns
 */
function sort(orderBy,defaultOrder){
	if($("#orderBy").val()==orderBy){
		if($("#order").val()==""){
			$("#order").val(defaultOrder==null?'asc':defaultOrder);
		}
		else if($("#order").val()=="desc"){
			$("#order").val("asc");
		}
		else if($("#order").val()=="asc"){
			$("#order").val("desc");
		}
	}
	else{
		$("#orderBy").val(orderBy);
		$("#order").val(defaultOrder==null?'asc':defaultOrder);
	}
	$("#pageSize").attr("name","pageSize");
	$("#ListForm").submit();
}

function getPageRequest(){
	var pageRequest = "page.pageRequest="+$("#pageNo").val()+"|"+$("input[name=page.pageSize]").val()+"|"+$("#orderBy").val()+"|"+$("#order").val();
	return pageRequest;
}

/**
 * 点击“新增”按钮执行
 * 
 * @returns
 */
function create(){
	$("#entityid").val("");
	$('#ListForm').attr("action",getListFormAction()+'create.htm');
	$("#ListForm").submit();
}

/**
 * 点击“修改”按钮或者链接执行
 * 
 * @param id
 * @returns
 */
function update(id){
	 command(id,'update');
}

/**
 * 点击“删除”按钮或者链接执行
 * 
 * @param id
 * @returns
 */
function del(id){
	command(id,'delete','确认要删除该记录吗?');
}

/**
 * 点击“查看”按钮或者链接执行
 * 
 * @param id
 * @returns
 */
function view(id){
	command(id,'view');
}

/**
 * 执行指定请求方法
 * 
 * @param id
 *            操作行数据ID
 * @param method
 *            请求方法
 * @param msg
 *            需确认提示信息
 * @returns
 */
function command(id, method, msg){
	if(msg!=null){
		if(msg!=null&&!confirm('\n'+ msg)){
			return;
		}
	}
	 if(id!=null) $("#entityid").val(id);// document.getElementById('entityid').value=id;
	 else $("#entityid").val("");// document.getElementById('entityid').value="";
	 
	 $('#ListForm').attr("action",getListFormAction() + method + ".htm");
	 $("#ListForm").submit();
}
/*
 * function actionConfirm(msg, method){ if(confirm('\n'+ msg)){
 * if(method.indexOf(".action")==-1) method +=".action";
 * $('#ListForm').attr("action",getListFormAction() + method);
 * $("#ListForm").submit(); } }
 */

/**
 * 获取管理列表表单action路径
 * 
 * @returns
 */
function getListFormAction(){
	return getAction("ListForm");
}

/**
 * 获取录入表单action路径
 * 
 * @returns
 */
function getInputFormAction(){
	 return getAction("inputForm");
}

/**
 * 获取指定表单action路径
 * 
 * @param formid
 *            表单ID
 * @returns
 */
function getAction(formid){
	 var action_value = $('#'+formid).attr('action');
	 if(action_value!=null&&action_value.lastIndexOf('/')>-1){
		 action_value = action_value.substring(0,action_value.lastIndexOf('/')+1);
	 }
	 return action_value;
}

/**
 * 禁止指定表单所有按钮执行
 * 
 * @param formid
 *            表单ID
 * @returns
 */
function hiddenButton(formid){
  var obj = $(formid);
  var count = obj.elements.length;
  for(var i=0;i<count;i++){
	   with(obj.elements[i]){
			var _type = getAttribute("type");
			// if(typeof(_dataType) == "object" || typeof(this[_dataType]) ==
			// "undefined") continue;
			if(_type=="button" && getAttribute("id")!=""){
				// alert(_type);
				$(getAttribute("id")).disabled = true;
			}
	   }
  }
}

/** ------------ table 光标定位颜色显示 -----------------* */
var  highlightcolor='#eaf4ff';
// 此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var  clickcolor='#C2DFFF';
function  changeto(){
	/*
	 * source = window.event?event.srcElement:event.target; if
	 * (source.tagName=="TR"||source.tagName=="TABLE") return;
	 * while(source.tagName!="TD") source=source.parentElement;
	 * source=source.parentElement; cs = source.children; //alert(cs.length); if
	 * (cs[1].style.backgroundColor!=highlightcolor&&source.id!="nc"&&cs[1].style.backgroundColor!=clickcolor){
	 * for(i=0;i<cs.length;i++){ cs[i].style.backgroundColor=highlightcolor; } }
	 */
}

function  changeback(){
	/*
	 * if
	 * (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
	 * return if
	 * (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor){
	 * //source.style.backgroundColor=originalcolor for(i=0;i<cs.length;i++){
	 * cs[i].style.backgroundColor=""; } }
	 */
}


jQuery(function(){
	tableTrStyleChange();
});


function tableTrStyleChange(){
	jQuery(".TableBlock1[onmousemove='changeto()'] tr").hover(function(){
		if(jQuery(this).find(".TableData").length>0){
			var color=jQuery(this).find(".TableData").css("background-color");
			color=color.indexOf("rgb")>=0? RGBToHex(color) : color;
			if(color.toLowerCase()==clickcolor.toLowerCase()){
				return;
			}
			jQuery(this).find(".TableData").css("background-color",highlightcolor);
		}
	},function(){
		if(jQuery(this).find(".TableData").length>0){
			var color=jQuery(this).find(".TableData").css("background-color");
			color=color.indexOf("rgb")>=0? RGBToHex(color) : color;
			if(color.toLowerCase()==clickcolor.toLowerCase()){
				return;
			}
			jQuery(this).find(".TableData").css("background-color","white");
		}
	});
	
	jQuery(".TableBlock1[onmousemove='changeto()'] tr").click(function(){
		if(jQuery(this).find(".TableData").length>0){
			jQuery(".TableBlock1 tr").find(".TableData").css("background-color","white");
			jQuery(this).find(".TableData").css("background-color",clickcolor);
		}
	});
}


function RGBToHex(rgb){
    
    var regexp = /^rgb\(([0-9]{0,3})\,\s([0-9]{0,3})\,\s([0-9]{0,3})\)/g;
    var re = rgb.replace(regexp, "$1 $2 $3").split(" ");// 利用正则表达式去掉多余的部分
    var hexColor = "#";
    var hex = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];
    for (var i = 0; i < 3; i++) {
        var r = null;
        var c = re[i];
        var hexAr = [];
        while (c > 16) {
            r = c % 16;
            c = (c / 16) >> 0;
            hexAr.push(hex[r]);
        }
        hexAr.push(hex[c]);
        hexColor += hexAr.reverse().join('');
    }
    return hexColor;
}

function subNum(bsub,consub){
	bsub=bsub.replace("px","");
	return parseInt(consub)-parseInt(bsub);
}

// 动态布局控制---开始
function changeWidth(left,right){
	var bdwidth=jQuery(left).parent().width();
	/*alert(bdwidth);
	var lml = jQuery(left).css("margin-left");
	if(lml){
		lml=lml.replace("px","");
		bdwidth=parseInt(bdwidth)-parseInt(lml);
	}
	var lmr = jQuery(left).css("margin-right");
	if(lmr){
		lml=lml.replace("px","");
		bdwidth=parseInt(bdwidth)-parseInt(lmr);
	}
	var rml = jQuery(right).css("margin-left");
	if(rml){
		lml=lml.replace("px","");
		bdwidth=parseInt(bdwidth)-parseInt(rml);
	}
	var rmr = jQuery(right).css("margin-right");
	if(rmr){
		lml=lml.replace("px","");
		bdwidth=parseInt(bdwidth)-parseInt(rmr);
	}
	 alert(bdwidth+" "+jQuery(left).width()+" "+bdwidth*0.02);*/
	jQuery(right).width(bdwidth-jQuery(left).width()-bdwidth*0.02);
}

function synBond(res,tar,dir){
	if(dir&&dir==1){
		jQuery(tar).height(jQuery(res).height());
	}else{
		jQuery(tar).width(jQuery(res).width());
	}
		
}



function layoutChangeInit(conf){
	if(conf&&conf.leftDiv&&conf.rightDiv&&conf.frameCon){
		changeWidth(conf.leftDiv,conf.rightDiv);
		window.onresize = function (){
			changeWidth(conf.leftDiv,conf.rightDiv);
		};
		      	
    	jQuery(conf.frameCon).load(function(){
    		var height=window.frames[jQuery(this).attr("name")].document.body.scrollHeight+10;
    		jQuery(this).css("height",height+"px");
    		synBond(conf.rightDiv,conf.leftDiv,1);
		});
	}
}

String.prototype.EndWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 }

 String.prototype.StartWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 }

 
 //在json中递归查找url
function findCurrentPageInMenuJson(list,url,clunm,isexpandAll){
	var findValue="";
	if(!url.StartWith("/")){
		url="/"+url;
	}
	var ext = url.substring(url.lastIndexOf('/'));
	for(var i=0;list&&i<list.length;i++){
		if(list[i].nodeType=="M"&&list[i].hasChildren){
			var find=findCurrentPageInMenuJson(list[i].children,url,clunm,isexpandAll);
			if(isexpandAll){
				list[i].isexpand=true;
				if(find){
					findValue=find;
				}
			}else{
				if(find){
					list[i].isexpand=true;
					return find;
				}
			}
		}
		
		if(list[i].url&&!list[i].url.StartWith("/")){
			list[i].url="/"+list[i].url;
		}
		
		if(url!="/ "){
			if(list[i].nodeType=="F"&&(url==list[i].url||url.StartWith(list[i].url))){
				if(clunm){
					return list[i][clunm];
				}else{
					return list[i].id;
				}
			}else if(list[i].nodeType=="F"&&ext){
				url=url.replace(ext,"");
				var url2=list[i].url;
				var ext2 = url2.substring(url2.lastIndexOf('/'));
				url2=url2.replace(ext2,"");
				if(url==url2||url.StartWith(url2)){
					if(clunm){
						findValue = list[i][clunm];
					}else{
						findValue = list[i].id;
					}
				}
			}
		}
	}
	return findValue;
}




//设置为主页 
function SetHome(obj, vrl) {
	try {
		obj.style.behavior = 'url(#default#homepage)';
		obj.setHomePage(vrl);
	} catch (e) {
		if (window.netscape) {
			try {
				netscape.security.PrivilegeManager
						.enablePrivilege("UniversalXPConnect");
			} catch (e) {
				alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
			}
			var prefs = Components.classes['@mozilla.org/preferences-service;1']
					.getService(Components.interfaces.nsIPrefBranch);
			prefs.setCharPref('browser.startup.homepage', vrl);
		} else {
			alert("您的浏览器不支持，请按照下面步骤操作：1.打开浏览器设置。2.点击设置网页。3.输入：" + vrl
					+ "点击确定。");
		}
	}
}
// 加入收藏 兼容360和IE6 
function shoucang(sTitle, sURL) {
	try {
		window.external.addFavorite(sURL, sTitle);
	} catch (e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		} catch (e) {
			alert("加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}


function pageMasterTitle(selectId,name){
	if(selectId){
		jQuery(".memberlogo").html(selectId);
	}else if(name=="login"){
		jQuery(".memberlogo").html("登录");
	}else if(name=="register"){
		jQuery(".memberlogo").html("会员注册");
	}else if(name=="message"){
		jQuery(".memberlogo").html("信息提示");
	}else{
		jQuery(".memberlogo").html("会员中心");
	}
}

