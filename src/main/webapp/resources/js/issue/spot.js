	/*点击品种时触发事件*/
function selectChild(name,id){
	jQuery("#pageNo").val(1);
        if(id!=null){
            jQuery(".dis").attr("style","display:none;");
            jQuery("#parentId_"+id).removeAttr("style");
            jQuery("#selectParentId").val(id);
            //父类点击查询
            jQuery("#itemGoodsNames").val(name);
            //记录父类的id
            jQuery("#parentids").val(id);
            //点击全部时触发事件
            if(id==0||name==''){
                jQuery("#itemGoodsNames").val('');
                jQuery("#parentids").val('');
                jQuery("#selectItemGoodsName").val('');
                jQuery("#itemGoodsName").val('');
            }
            /* 清空子选项开始*/
            jQuery("#selectItemGoodsName").val("");
            jQuery("#itemGoodsName").val("");
             /* 清空子选项结束*/
            jQuery("#pageNo").attr("value",1);
            jQuery("#spotForm").submit();
        }
    }
	
	//选择品名触发事件
	function selectItemGoodsName(itemGoodsName,pId,cId)
	{
		if(pId!=null||itemGoodsName!=null){
			jQuery("#itemGoodsName").val(itemGoodsName);
			jQuery("#parentids").val(pId);
			jQuery("#selectItemGoodsName").val(cId);
			jQuery("#pageNo").attr("value",1);
	        jQuery("#spotForm").submit();
		}
	}