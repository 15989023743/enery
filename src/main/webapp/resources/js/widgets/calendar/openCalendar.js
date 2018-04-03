	function getBasepath() {
		var basepath= document.location.pathname.substring(document.location.pathname.indexOf('/')+1) ;
		 basepath= basepath.substring(0,basepath.indexOf('/')) ;
		 return "/"+basepath+"";
	}
function showCalendarYmd(dateId){
	var date = document.getElementById(dateId);
	var paramArr = new Array();
	paramArr[0] = date.value;
	paramArr[1] = true;
	paramArr[2] = false;
	
	var _w = 200;
	var _h = 262;
	var retVal = window.showModalDialog(getBasepath()+'/js/calendar/calendars.htm',paramArr,"dialogWidth:"+_w+"px;dialogHeight:"+_h+"px;help:no;status:no;resizable:no;");
	if(retVal=="cancel"){
	}else{
		date.value = retVal;
	}

}

function showCalendarYmdHM(dateId){
	var date = document.getElementById(dateId);
	var paramArr = new Array();
	paramArr[0] = date.value;
	paramArr[1] = false;
	paramArr[2] = false;
	
	var _w = 200;
	var _h = 289;
	var retVal = window.showModalDialog(getBasepath()+'/js/calendar/calendars.htm',paramArr,"dialogWidth:"+_w+"px;dialogHeight:"+_h+"px;help:no;status:no;resizable:no;");
	if(retVal=="cancel"){
	}else{
		date.value = retVal;
	}

}

function showCalendarYmdHMS(dateId){
	var date = document.getElementById(dateId);
	var paramArr = new Array();
	paramArr[0] = date.value;
	paramArr[1] = false;
	paramArr[2] = true;

	var _w = 200;
	var _h = 289;
	var retVal = window.showModalDialog(getBasepath()+'/js/calendar/calendars.htm',paramArr,"dialogWidth:"+_w+"px;dialogHeight:"+_h+"px;help:no;status:no;resizable:no;");
	if(retVal=="cancel"){
	}else{
		date.value = retVal;
	}

}

function showCalendarYmdHM1(){
	var date = window.event.srcElement;
	var paramArr = new Array();
	paramArr[0] = date.value;
	paramArr[1] = false;
	
	var _h = 336;
	var retVal = window.showModalDialog(getBasepath()+'/js/calendar/calendars.htm',paramArr,"dialogWidth:238px;dialogHeight:"+_h+"px;help:no;status:no;resizable:no;");
	if(retVal=="cancel"){
	}else{
		date.value = retVal;
	}

}


function showCalendarYmd1(){
	var date = window.event.srcElement;
	var paramArr = new Array();
	paramArr[0] = date.value;
	paramArr[1] = true;
	
	var _h = 310;
	var retVal = window.showModalDialog(getBasepath()+'/js/calendar/calendars.htm',paramArr,"dialogWidth:238px;dialogHeight:"+_h+"px;help:no;status:no;resizable:no;");
	if(retVal=="cancel"){
	}else{
		date.value = retVal;
	}

}