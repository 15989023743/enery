/**
 * @desc 自定的js工具方法，需要jQuery的支持
 * @author yanlun
 * @version 1.0
 * @date 2014/09/23
 **/

/**
 * 
 * 简单的tab切换封装
 * 如果要做成点击后再转到请将<li>中的onmouseover 改成 onclick;
 * 
 **/
function g(o){return document.getElementById(o);}
function HoverLi(tabTotal,currentIndex,tabMark,tabContentMark){
	for(var i=1;i<=tabTotal;i++){
		g(tabMark+'_'+i).className='normaltab';
		g(tabContentMark+'_0'+i).className='undis';
	}
	g(tabContentMark+'_0'+currentIndex).className='dis';
	g(tabMark+'_'+currentIndex).className='hovertab';
}


/**
 * 
 *信息向上滚动控制实现，鼠标hover停止滚动
 *
 **/
function dataRolling(speed){
	var defaultSpeed=30; //数字越大速度越慢
	var tab=document.getElementById("scroll-obj");
	var scrollDetail=document.getElementById("scroll-detail");
	var scrollBackup=document.getElementById("scroll-backup");
	var offset=jQuery(tab).offset();
	var num=parseInt(tab.offsetHeight/scrollDetail.offsetHeight)+1;
	if(typeof(speed) == "undefined"){
		speed = defaultSpeed;
	}
	//备份数据
	for(var i=0;i<num;i++){
	    scrollBackup.innerHTML+=scrollDetail.innerHTML;
	}

	function Marquee(){
	    var offset2=jQuery(scrollBackup).offset();
	    if(offset2.top<=offset.top+9){
	        tab.scrollTop=0;
	    }else{
	        tab.scrollTop++;
	    }
	    if(tab.scrollTop%50==0&&tab.scrollTop!=0){
	        clearInterval(MyMar);
	        bindMyMarPauseInterval();
	    }
	}
	
	var MyMar=setInterval(Marquee,speed);
	tab.onmouseover=function() {clearInterval(MyMar);};
	tab.onmouseout=function() {bindInterval();};
	function bindInterval(){
	    MyMar=setInterval(Marquee,speed);
	    if(MyMarPause){
	        clearInterval(MyMarPause);
	    }
	}
	
	var MyMarPause;
	function bindMyMarPauseInterval(){
	    MyMarPause=setInterval(bindInterval,3000);
	}
}

/**
 * 行业指数图表引入
 */
function getItemInfo(){
	jQuery("#chartsD337").load(ctx+"/indices/portal.htm");
	/*var url = ctx+"/portal/goods/goodsItem/queryGoodsItemList.htm";
	var param = {};
	param["rownum"]=3;
	jQuery.ajax({
        type:'POST',
        url:url,
        data:param,
        dataType:"json",
        success:function(data){
        	var itemInfo = "";
        	if (data!=null && data.length>0){
        		var num = 0;
        		for(var i=0,len=data.length;i<len;i++){
        			num = i+1;
        			if (i==0){
        				itemInfo += '<li id="tb1_'+num+'" class="hovertab" onmouseover="HoverLi('+data.length+','+num+','+'\'tb1\''+','+'\'tbc1\''+');">'+data[i].name+'</li>';
        			}else{
        				itemInfo += '<li id="tb1_'+num+'" class="normaltab" onmouseover="HoverLi('+data.length+','+num+','+'\'tb1\''+','+'\'tbc1\''+');">'+data[i].name+'</li>';
        			}
        		}
        		jQuery("#itemInfo").html(itemInfo);
        		for(var i=0,len=data.length;i<len;i++){
        			num = i+1;
        			jQuery("#tbc1_0"+num).load(ctx+"/portal/data/priceTrend/portal.htm",{itemId:data[i].id});
        		}
        	}
        }
    });*/
}

function getDynamicFutures(){
    jQuery("#mainContractChartDiv").load(ctx+"/portal/data/dynamicFutures/portal.htm");
}