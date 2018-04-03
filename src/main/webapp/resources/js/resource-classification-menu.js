/**
 * @desc 资源分类菜单特效处理，需要jQuery的支持
 * @author yanlun
 * @version 1.0
 * @date 2014/10/09
 **/


/**
* Jquery扩展，:当前元素是否是被筛选元素的子元素或者本身
**/
isChildAndSelfOf = function(thisObj,b){
	return (jQuery(thisObj).closest(b).length > 0);
};

/**
*Jquery扩展，判断:当前元素是否是被筛选元素的子元素 
**/
isChildOf = function(thisObj,b){
	return (jQuery(thisObj).parents(b).length > 0);
};

var currentPath = document.location.href, basicPath, ctx=getBasicPath();// 应用程序的基本路径
function getBasicPath() {
	if(top.ctx){
		basicPath = top.ctx;
		return top.ctx;
	}
	var pathname= document.location.pathname;
	basicPath= pathname.substring(pathname.indexOf('/')+1);
	basicPath= "/"+basicPath.substring(0,basicPath.indexOf('/'));
	return basicPath;
}

/**
*判断当前页面是否为首页
**/
function isWelcomePage(){
	if(typeof(pageConfig) != "undefined"){
		//需要引入js_commons.html
		var isHome = pageConfig.navId == "home"?true:false;
		return isHome;
	}else{
		if((basicPath == currentPath || basicPath+"index.htm" == currentPath) ||
				(basicPath+"/" == currentPath || basicPath+"/index.htm" == currentPath)){
			return true;
		}
		return false;
	}
}

/**
 * 分类详情的背景图切换效果，默认的navleft.jpg
 */

function classificatioBackgroundSetting(){
	var DEFAULT_BACKIMG = "navleft.jpg";
	var DEFAULT_BACKIMG_ARRAY =  ["navleft.jpg","navleft_hg.jpg","navleft_mt.jpg","navleft_sl.jpg","navleft_sy.jpg"];
	var DEFAULT_CLASSIFICATION_ARRAY = ["天然气","化工","煤炭","塑料","石油"];
	jQuery(".hh").each(function(i,n){
		var thisObj = jQuery(this);
		var currentClassificationName = thisObj.attr("classification-name");
		var pos = jQuery.inArray(currentClassificationName, DEFAULT_CLASSIFICATION_ARRAY);
		if(pos != -1){
			thisObj.find(".nn").attr("style","background:url("+ctx+"/resources/style/portal/images/header/"+DEFAULT_BACKIMG_ARRAY[pos]+") no-repeat;");
		}
	});
}

function resourceClassificationMenuInit(){
	//资源分类展开和关闭处理
	var classificationObj = jQuery("#resourceClassificationUl");
	var resourceExpendOrCloseObj = jQuery("#resourceExpendOrClose");
	var defaultHeight = classificationObj.height();
	var baseHeight = classificationObj.children("li").eq(0).outerHeight();
	
	var resourceClassificationNumber = jQuery("#resourceClassificationUl li").length;//资源分类大类个数，判断是否启用展开合并功能
	var expendOrCloseMinLimit = Math.ceil((defaultHeight - 10)/baseHeight);
	var resourceClassificationUlMaxHeight = resourceClassificationNumber * baseHeight;//初始化ul的高度（资源分类大类个数*基本高度60）
	
	var ulBorderWidth = parseInt(classificationObj.css('border-left-width'))||2;//默认为2px
	var menuCloseOffset = resourceExpendOrCloseObj.height()-ulBorderWidth;//下拉上拉按钮的偏移量计算，用于资源菜单关闭设置top高度
	
	//除主页外资源分类菜单的hover事件处理
	var classificationAllObj = jQuery("#resourceClassificationAll");
	var classificationDivObj = jQuery("#resourceClassificationDiv");
	
	/**
	 * 判断是否开启展开合并功能
	 */
	function needOpenFunctionOfExpendOrClose(){
		return resourceClassificationNumber > expendOrCloseMinLimit;
	}
	
	if(!isWelcomePage()){//除首页外资源菜单的特殊处理-默认合并
		classificationDivObj.attr("style","display:none;");
		classificationAllObj.removeClass("resouce-classification-expend").addClass("resouce-classification-close");//默认资源分类菜单是展开的
		
		classificationAllObj.hover(function(){
			classificationAllObj.removeClass("resouce-classification-close").addClass("resouce-classification-expend");
			classificationDivObj.stop(true,true).slideDown();
		},function(){
			classificationAllObj.removeClass("resouce-classification-expend").addClass("resouce-classification-close");
			if(needOpenFunctionOfExpendOrClose()){resourceMenuClose(resourceExpendOrCloseObj);}
			classificationDivObj.stop(true,true).slideUp("fast");
		});
	}else{
		//资源分类hover事件的预处理
		classificationDivObj.attr("style","display:block;");
		classificationAllObj.removeClass("resouce-classification-close").addClass("resouce-classification-expend");//默认资源分类菜单是关闭的
	}
	
	//分类hover事件
	jQuery(".hh").hover(function(){
		jQuery(this).children("div").stop(true,true).show();
	},function(){
		jQuery(this).children("div").stop(true,true).hide();
	});
	
	classificatioBackgroundSetting();
	
	//top高度计算
	jQuery(".hh").each(function(i,n){
		if((i+1)>expendOrCloseMinLimit){
			jQuery(this).attr("style","top:"+i*baseHeight+"px;").addClass("display-none");
		}else{
			jQuery(this).attr("style","top:"+i*baseHeight+"px;");
		}
	});
	
	/**
	 * 资源菜单关闭
	 */
	function resourceMenuClose(jqueryObj){
		//关闭多余的.hh属性display:none;
		jQuery(".hh").each(function(i,n){
			if((i+1) > expendOrCloseMinLimit){
				jQuery(this).removeClass("display-block").addClass("display-none");
			}
		});
		jqueryObj.removeClass("resource-expend").addClass("resource-close");
		classificationObj.animate({height:defaultHeight+'px'});
		resourceExpendOrCloseObj.animate({top:(defaultHeight - menuCloseOffset)+'px'});
	}
	
	/**
	 * 资源菜单展开
	 */
	function resourceMenuExpend(jqueryObj){
		jqueryObj.removeClass("resource-close").addClass("resource-expend");
		classificationObj.animate({height : resourceClassificationUlMaxHeight+'px'});
		resourceExpendOrCloseObj.animate({top : (resourceClassificationUlMaxHeight)+'px'});
		//开启所有的.hh属性display:block;
		jQuery(".hh").each(function(){
			jQuery(this).removeClass("display-none").addClass("display-block");
		});
	}
	
	//判断是否应该启用展开合并功能
	if(needOpenFunctionOfExpendOrClose()){
		resourceExpendOrCloseObj.bind('click',function(){
			var thisObj = jQuery(this);
			if(thisObj.attr("class") == "resource-close"){
				resourceMenuExpend(thisObj);
			}else{
				resourceMenuClose(thisObj);
			}
		});
		jQuery(document).bind("click",function(event){
			if (isChildOf(event.target,"#resourceClassificationDiv") == false){
				resourceMenuClose(resourceExpendOrCloseObj);
			}
		});
	}else{
		if(!isWelcomePage()){
			classificationObj.attr("style","height: "+resourceClassificationUlMaxHeight+"px;");
		}
		resourceExpendOrCloseObj.removeClass("resource-close");//取消展开和合并class
	}
}

/**
 * 未带参数classIds的链接选定判断，默认判别方法
 */
function hoverNavgationDefault(){
	var navigationHref;
	if(isWelcomePage()){//判定是否为首页
		jQuery("#nav_index").addClass("selected");//首页特定ID为nav_index
		return false;
	}else{
		jQuery(".navigationUl li").each(function(){
			thisObj = jQuery(this);
			navigationHref = thisObj.children("a").eq(0).attr("href");
			if(navigationHref.indexOf(currentPath) != -1){
				thisObj.addClass("selected");//添加选定状态
				return false;
			}
		});
	}
}

(function($){
	$.getUrlParam = function(name)
	{
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
})(jQuery);

function hoverNavgation(){
	var classIds = jQuery.getUrlParam("classIds");
	var classId = jQuery.getUrlParam("classId");
	
  	if(classIds != null){
  		var classIdsArray = classIds.split("-");
  		jQuery('#'+classIdsArray[0]).addClass("selected");
  	}else if(classId != null)
  		jQuery('#'+classId).addClass("selected");
  	else{
  		hoverNavgationDefault();//执行默认的判别方法
  	}
}

/**
 * 资源分类菜单小类点击事件绑定
 */
function resourceSelectItemGoodsClickBind()
{
	jQuery(".nn li a").bind("click",function(event){
		 var thisObj = jQuery(this);
		 var pId = thisObj.attr("pId");
		 var cId = thisObj.attr("cId");
		 var itemGoodsName = thisObj.attr("itemGoodsName");
		 
		 if(pId != null || itemGoodsName != null){
			/* jQuery("#resourceItemGoodsName").val(itemGoodsName);
			 jQuery("#resourceParentids").val(pId);
			 jQuery("#resourceSelectItemGoodsName").val(cId);*/
			 //jQuery("#resouce-classification-form").submit();
			 window.open(ctx+"/spot/index.htm?selectItemGoodsName="+cId+"&filter_EQ_itemGoodsName="+itemGoodsName+"&parentids="+pId);
		 }
		 event.stopPropagation();
	 });
}

/**
 * 资源分类菜单大类点击事件绑定
 */
function resourceSelectChildClickBind(){
	 jQuery(".hh").bind("click",function(){
		 var thisObj = jQuery(this);
		 var pId = thisObj.attr("pId");
		 var itemGoodsNames = thisObj.attr("itemGoodsNames");
		 
		 if(pId != null){
			/* jQuery("#resourceItemGoodsNames").val(itemGoodsNames);
			 jQuery("#resourceParentids").val(pId);
			 //清除子项数据
			 jQuery("#resourceItemGoodsName").val("");
			 jQuery("#resourceSelectItemGoodsName").val("");
			 
			 jQuery("#resouce-classification-form").submit();*/
			 window.open(ctx+"/spot/index.htm?filter_LIKER_itemGoodsNames="+itemGoodsNames+"&parentids="+pId);
		 }
	 });

}

jQuery(document).ready(function(){
	//hoverNavgation();
	
	resourceSelectItemGoodsClickBind();
	resourceSelectChildClickBind();
	
	resourceClassificationMenuInit();
});
