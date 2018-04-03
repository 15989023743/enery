var backWindowMask;
var backWindowDiv;
var backWindowForm;
function BACK_WINDOW(comp){
	var top=jQuery(window).height()/2-375/2;
    var left=jQuery(window).width()/2-365/2;
    var b1=false;
    var b2=false;
    var b3=false;
    if(!backWindowMask){
        b1=true;
        backWindowMask=jQuery("<div style='position: fixed;width: 100%;height: 100%;background-color: #000000;opacity: 0.5;filter: alpha(opacity=30);top: 0px;left: 0px;z-index: 99997;display: none;'></div>");
    }
    if(!backWindowDiv){
        b2=true;
        backWindowDiv=jQuery("<div style='position: fixed;width: 375px;height: 365px;background-color: #f9fff7;opacity: 0.5;filter: alpha(opacity=30);z-index: 99998;display: none;'></div>");
        backWindowDiv.css({top:top+"px",left:left+"px"});
    }
    
    if(!backWindowForm){
        b3=true;
        backWindowForm=jQuery("<div style='position: fixed;width: 365px;height: 355px;background-color: #f9fff7;z-index: 99999;display: none;'></div>");
        backWindowForm.css({top:(top+5)+"px",left:(left+5)+"px"});
        backWindowForm.append(comp);
        var img=jQuery("<img style='position: absolute;top: 10px;right: 10px;cursor: pointer;' src='"+getContextPath()+"/resources/style/common/images/close2.jpg' />");
        backWindowForm.append(img);
    }
    
    if(b1&&b2&&b3){
        jQuery('body').append(backWindowMask);
        jQuery('body').append(backWindowDiv);
        jQuery('body').append(backWindowForm);
        img.click(function(){
            close_login_form();
        });
        img.hover(function(){
            this.src=getContextPath()+"/resources/images/close.jpg";
        },function(){
            this.src=getContextPath()+"/resources/images/close2.jpg";
        });
    }
    backWindowMask.fadeIn("normal",function(){
        backWindowDiv.show("normal",function(){
            backWindowForm.fadeIn("slow");
        });
    });
}


function close_login_form(){
    backWindowForm.fadeOut("normal",function(){
        backWindowDiv.hide("fast",function(){
            backWindowMask.fadeOut("slow");
        });
    });
}

function getContextPath() {
    var contextPath = document.location.pathname;
    var index =contextPath.substr(1).indexOf("/");
    contextPath = contextPath.substr(0,index+1);
    delete index;
    return window.location.protocol+"//"+window.location.host+contextPath;
//	  return window.location.protocol+"//"+window.location.host;
}