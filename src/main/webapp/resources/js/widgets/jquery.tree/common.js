try { document.execCommand("BackgroundImageCache", false, true); } catch (e) { }
function getiev() {
    var userAgent = window.navigator.userAgent.toLowerCase();
    $.browser.msie8 = $.browser.msie && /msie 8\.0/i.test(userAgent);
    $.browser.msie7 = $.browser.msie && /msie 7\.0/i.test(userAgent);
    $.browser.msie6 = !$.browser.msie8 && !$.browser.msie7 && $.browser.msie && /msie 6\.0/i.test(userAgent);
    var v;
    if ($.browser.msie8) {
        v = 8;
    }
    else if ($.browser.msie7) {
        v = 7;
    }
    else if ($.browser.msie6) {
        v = 6;
    }
    else { v = -1; }
    return v;
}
$(document).ready(function() {
    var v = getiev()
    if (v > 0) {
        $(document.body).addClass("ie ie" + v);
    }

    $("#treeview").css("width","100%");
    $("#treeview_left").css({height:"auto","min-height":"437px"});
    $("#treeview_right").css({width:"100%",height:"auto","min-height":"437px",float:"left","margin-left":"8px"});
    $("#rightid").css({width:"100%",height:"auto","min-height":"437px"});
    try{
    	layoutChangeInit({"frameCon":"#rightid","leftDiv":"#treeview_left","rightDiv":"#treeview_right"});
    }catch(e){
    	//alert("0");
    }
    
});
var userAgent = window.navigator.userAgent.toLowerCase();
$.browser.msie8 = $.browser.msie && /msie 8\.0/i.test(userAgent);
$.browser.msie7 = $.browser.msie && /msie 7\.0/i.test(userAgent);
$.browser.msie6 = !$.browser.msie8 && !$.browser.msie7 && $.browser.msie && /msie 6\.0/i.test(userAgent);

function view(item){
	$('#rightid').attr('src',$("#tree").getConfig().action+'view.htm?id='+item.value);
};
function reflash(t){
 	var item = $("#tree").getItem(t);
 	if(item.hasChildren&&item.complete)//只刷新有子节点及已经加载的节点
	 	$("#tree").reflash($("#tree").getItem(t).value);
};
function update(t){
	var item = $("#tree").getItem(t);
		$('#rightid').attr('src',$("#tree").getConfig().action+'update.htm?parentId='+item.parentId+'&id='+item.value);	
};
function remove(t){
	if(confirm("你确定删除该节点下面所有数据吗？\n\n点“确定”继续，点“取消”返回")){
		var item = $("#tree").getItem(t);
			$.ajax({
            type: "POST",
            url: $("#tree").getConfig().action+"delete.htm?id="+item.value+"&parentId="+item.parentId,
            data: "",
            async: true,
            dataType: "text/javascript",
            success: function(data){
            	if(data){
            		updatereflash(item.parentId);
            	}
			;},
            error: function(e) { alert("error occur!"); }
        });
	}
}
function add(t){
		var item = $("#tree").getItem(t);
			$('#rightid').attr('src',$("#tree").getConfig().action+'create.htm?parentId='+item.value);
};
function sort(t){
 	var item = $("#tree").getItem(t);
 	if(item.hasChildren)//只刷新有子节点及已经加载的节点
 		$('#rightid').attr('src',$("#tree").getConfig().action+'sort.htm?parentId='+item.id+'&xh=0');
};

function updatereflash(parentId){
	if(parentId==""||parentId=="0"||parentId=="-1"){
		initTree();
	}else{
		$("#tree").reflash(""+parentId);
	}
};