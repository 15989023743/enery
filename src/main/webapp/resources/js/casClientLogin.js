var constants = {
	loginButt : 'loginButt',
	clientLoginForm : 'loginForm',
	casServerFrame : 'cas_serverFrame',
	loginFormUser : 'username',
	loginFormPassword : 'password',
	serverLoginFormId : 'fm1',
	redirectURLName : 'service',
	//loginURL : 'http://cas.gcp.edu.cn:8089/repository/j_spring_cas_security_check',
	//casServerURL : 'http://219.222.226.107/cas/login',
	casServerLoginUrl:'casServerLoginUrl'
}

function initEvents(e) {
	var loginButtEl = document.getElementById(constants.loginButt);
	if (loginButtEl) {
		registerListener(loginButtEl, 'click', loginCAS);
	}
	var loginFormEl = document.getElementById(constants.clientLoginForm);
	if (loginFormEl) {
		registerListener(loginFormEl, 'keydown', function(e) {
			if (isKeyDownEnter(e)) {
				loginCAS();
			}
		});
	}
}

loginCAS = function() {
	var username = document.getElementById(constants.loginFormUser).value;
	var password = document.getElementById(constants.loginFormPassword).value;
	if(username==''){
		alert('请输入用户名');
		document.getElementById(constants.loginFormUser).focus();
		return;
	}
	if(password==''){
		alert('请输入密码');
		document.getElementById(constants.loginFormPassword).focus();
		return;
	}
	document.getElementById('loginButt').disabled=true;
	var casServerFrameDoc = document.getElementById(constants.casServerFrame).contentWindow.document;
	//casServerFrameDoc.test();
	var cas_username = casServerFrameDoc.getElementById(constants.loginFormUser);
	var cas_password = casServerFrameDoc.getElementById(constants.loginFormPassword);
	cas_username.value = username;
	cas_password.value = password;
	var cas_loginForm = casServerFrameDoc.forms[constants.serverLoginFormId];
	if (cas_loginForm) {
		cas_loginForm.submit();
		//cas_loginForm.submit.click();
	} else {
		alert('cas_loginForm is undefined');
	}
}

function resetWindow() {
	if (top.location !== self.location) {
		top.location = self.location;
	}
}

function loadedIFrame(e) {
	var frameURL = document.getElementById(constants.casServerLoginUrl).value;
	    //frameURL += '?service=';
	    //var redirectURL = getParam(constants.redirectURLName);
	    //redirectURL = redirectURL ? redirectURL : constants.loginURL;
	    //frameURL += redirectURL;
	var cas_serverFrame = document.getElementById(constants.casServerFrame);
	if (!cas_serverFrame)
		return;
	cas_serverFrame.src = frameURL;
	if (cas_serverFrame.attachEvent) {
		cas_serverFrame.attachEvent('onload', function() {
			initEvents(e);
		});
	} else {
		cas_serverFrame.onload = function() {
			initEvents(e);
		}
	}
}

function registerListener(el, eventName, handle) {
	if (window.attachEvent) {
		el.attachEvent('on' + eventName, handle);
	} else if (window.addEventListener) {
		el.addEventListener(eventName, handle, false);
	}
}

function isKeyDownEnter(e) {
	e = e ? e : window.event;
	var _isie = (window.attachEvent) ? true : false;
	var _key = '';
	if (_isie) {
		_key = e.keyCode;
	} else {
		_key = e.which;
	}
	if (_key == 13)
		return true;
	else
		return false;
}

function getParam(name) {
	var queryString = window.location.search;
	var param = queryString.substr(1, queryString.length - 1).split("&");
	for ( var i = 0; i < param.length; i++) {
		var keyValue = param[i].split("=");
		if (keyValue[0] == name)
			return keyValue[1];
	}
	return null;
}
