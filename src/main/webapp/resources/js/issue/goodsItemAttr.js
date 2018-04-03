var GOODSITEM_NAMES_MAP={};
var GOODSITEM_LEAF_MAP={};
var PROFITLOSS_SCALE_MAP={};
var WEB_PATH="";
var attrvaluesJson=[];
var attrvaluesMap={};
var attrvalueStrMap={};
var attrvalueNameMap={};

var messageUIConf='<p style="font-size: 16px;font-weight: bold;">正在执行，请稍候...</p>';

var tempFunc=null;

var tempFuncs=[];

var ids=[];

var temIntex=0;

function goodsItemAttrInit(){
	GOODSITEM_NAMES_MAP={};
	GOODSITEM_LEAF_MAP={};
	PROFITLOSS_SCALE_MAP={};
	WEB_PATH="";
	attrvaluesJson=[];
	attrvaluesMap={};
	attrvalueStrMap={};
	attrvalueNameMap={};
	tempFunc=null;

	tempFuncs=[];

	ids=[];

	temIntex=0;
}


function initToUpdate(vchar,spchar,context,selectCallback,normalSelectCallbakc){
	/*post:function(url, data, callBack, type, element){
		jQuery.ajax({
			  type: "post",
			  url: url,
			  dataType: type,
			  data: data,
			  success: callBack,
			  timeout: 60000,
			  beforeSend: function(XMLHttpRequest){
				try{
					if(element)
						jQuery.blockUI({message:messageUIConf});
				}catch(ex){}
			  },
			  complete: function (XMLHttpRequest, textStatus) {
				try{
					if(element)
						jQuery.unblockUI();
				}catch(ex){}
			  }
		});
	}*/
	for(var i=0;i<attrvaluesJson.length&&attrvaluesJson!=null&&attrvaluesJson.length>0;i++){
		attrvaluesMap[attrvaluesJson[i].attrId]=attrvaluesJson[i].valueId;
		attrvalueStrMap[attrvaluesJson[i].attrId]=attrvaluesJson[i].attrValue;
	}
	var func=function(ids){
		goodsItemInterlock("","",ids,context,selectCallback,normalSelectCallbakc);
	}
	getItemsIdByName(vchar,spchar,func);
	
	//jQuery("#goodsItem_").val(ids[temIntex++]);
}


function findIndexInArray(array,value){
	for(var i=0;i<array.length;i++){
		if(array[i]==value){
			return i;
		}
	}
	return -1;
}


/**
 * 商品类目选择器连动方法
 * @param pid 父级id
 * @param preve 当前id
 * @param context 操作上下文
 * @param selectCallback 选择品名时执行的回调 
 */
function goodsItemInterlock(pid,preve,defaults,context,selectCallback,normalSelectCallbakc){
	var idPre="";
	var callback=function(result){
		if(preve){
				cascadeFlatRemove(jQuery("#"+preve+" + select").attr("id"),"select");
				idPre=preve;
		}else{
			idPre="goodsItem";
		}
	
		if(result.length>0){
			idPre=idPre+"_"+pid;
			var select=jQuery("<select size='3'></select>");
			context.append(select);
			select.attr("id",idPre);
			select.addClass("checkList");
			select.addClass("required");
			select.change(function(){
				if(!GOODSITEM_LEAF_MAP[this.value]){
					goodsItemInterlock(this.value,idPre,null,context,selectCallback);
					if(normalSelectCallbakc){
						normalSelectCallbakc();
					}
				}else if(selectCallback){
					selectCallback(jQuery(this));
				}
			});
			for(var i=0;i<result.length;i++){
				GOODSITEM_NAMES_MAP[result[i].id]=result[i].names;
				GOODSITEM_LEAF_MAP[result[i].id]=result[i].leaf;
				PROFITLOSS_SCALE_MAP[result[i].id]=parseFloat("0"+result[i].profitLossScale)/100;
				var option=jQuery("<option></option>");
				option.append(result[i].name);
				option.attr("value",result[i].id);
				select.append(option);
				if(defaults&&findIndexInArray(defaults,result[i].id)>=0){
					option.attr("selected","selected");
					if(!GOODSITEM_LEAF_MAP[result[i].id]){
						goodsItemInterlock(result[i].id,idPre,defaults,context,selectCallback);
					}else if(selectCallback){
						selectCallback(jQuery("#"+idPre));
					}
				}
			}
    	}
	}
	getGoodsItems(pid,callback);
}



/**
 * 级联删除类目选择器
 * @param current 当前控件
 * @param comp 控件类型
 */
function cascadeFlatRemove(current,comp){
	if(jQuery("#"+current).length>0){
		var next=jQuery("#"+current+" + "+comp);
		var nextId=next?next.attr("id"):null;
		if(nextId){
			cascadeFlatRemove(nextId,comp);
		}
		jQuery("#"+current).remove();
	}
}

/**
 * 商品属性连动
 * @param goodsId 商品id
 * @param context 操作上下文
 */
function goodsAttrsInterlock(goodsId,context){
	var namePre="ATTRS_";
	var callback=function(result){
		if(result.length>0){
			context.empty();
			for(var i=0;i<result.length;i++){
				if(i%2==0&&i!=0){
					context.append("<br/>");
				}
				var name=namePre+result[i].id;
				var id=result[i].id;
				var html=jQuery("<span style='margin-right:15px;min-width:200px;'></span>");
				context.append(html);
				html.append(result[i].name+":");
				switch (result[i].inputType) {
					case '1':
						var input=jQuery("<input type='text' />");
						input.attr("name","valuefor_"+name);
						input.attr("id",name).addClass("required");
						html.append(input);
						input.val(attrvalueStrMap[id]);
						
						var hdinput=jQuery("<input type='hidden' />");
						hdinput.attr("name",name);
						html.append(hdinput);
						break;
					case '2':
						var select=jQuery("<select></select>");
						html.append(select);
						select.attr("name",name);
						select.attr("id",name).addClass("required");
						
						var valuesPut=function(sel,values){
							if(values){
								var option=jQuery("<option value=''></option>");
								sel.append(option);
								for(var j=0;j<values.length;j++){
									option=jQuery("<option></option>");
									sel.append(option);
									option.append(values[j].name);
									option.attr("value",values[j].id);
									var thisvalue=attrvaluesMap[parseInt(sel.attr("name").replace("ATTRS_",""))];
									if(thisvalue==values[j].id){
										//alert(thisvalue+"  "+values[j].id)
										option.attr("selected","selected");
										jQuery("#valuefor_"+sel.attr("name")).val(values[j].name);
									}
								}
							}
						}
						var valueForSelect=jQuery("<input type='hidden' />");
						html.append(valueForSelect);
						valueForSelect.attr("name","valuefor_"+name);
						valueForSelect.attr("id","valuefor_"+name);
						getGoodsAttrValues(goodsId,result[i].id,select,valuesPut);
						select.change(function(){
							var obj=jQuery("#valuefor_"+this.id);
							obj.val(jQuery(this).find("option:selected").text());
						});
						break;
					case '3':
						var mcinput=jQuery("<input type='text' />");
						mcinput.attr("name","valuefor_"+name);
						mcinput.attr("readonly","readonly");
						mcinput.attr("id","mc"+name).addClass("required");
						html.append(mcinput);
						mcinput.val(attrvalueStrMap[id]);
						mcinput.attr("onclick","dictListOnClick('"+name.replace(".","")+"','mc"+name+"','"+result[i].dictName+"','');");
						
						var inputButton=jQuery("<input type='button' />");
						inputButton.attr("name",name.replace(".","")+"Button");
						inputButton.attr("readonly","readonly");
						inputButton.attr("id",name.replace(".","")+"Button").addClass("required");
						inputButton.val("…");
						html.append(inputButton);
						inputButton.attr("onclick","dictListOnClick('"+name.replace(".","")+"','mc"+name+"','"+result[i].dictName+"','');");
						
						var input=jQuery("<input type='hidden' />");
						input.attr("name",name.replace(".",""));
						input.attr("readonly","readonly");
						input.attr("id",name.replace(".","")).addClass("required");
						html.append(input);
						input.val(attrvaluesMap[id]);
						
						var hdinput=jQuery("<input type='hidden' />");
						hdinput.attr("name",name);
						html.append(hdinput);
						break;
					case '4':
						var mcinput=jQuery("<input type='text' />");
						mcinput.attr("name","valuefor_"+name);
						mcinput.attr("readonly","readonly");
						mcinput.attr("id","mc"+name).addClass("required");
						html.append(mcinput);
						mcinput.val(attrvalueStrMap[id]);
						mcinput.attr("onclick","dictTreeOnClick('"+name.replace(".","")+"','mc'"+name+",'"+result[i].dictName+"','');");
						//dictTreeOnClick(name.replace(".",""),"mc"+name,result[i].dictName,"");
						var inputButton=jQuery("<input type='button' />");
						inputButton.attr("name",name.replace(".","")+"Button");
						inputButton.attr("readonly","readonly");
						inputButton.attr("id",name.replace(".","")+"Button").addClass("required");
						inputButton.val("…");
						html.append(inputButton);
						inputButton.attr("onclick","dictTreeOnClick('"+name.replace(".","")+"','mc'"+name+",'"+result[i].dictName+"','');");
						
						var input=jQuery("<input type='hidden' />");
						input.attr("name",name.replace(".",""));
						input.attr("readonly","readonly");
						input.attr("id",name.replace(".","")).addClass("required");
						html.append(input);
						input.val(attrvaluesMap[id]);
						
						var hdinput=jQuery("<input type='hidden' />");
						hdinput.attr("name",name);
						html.append(hdinput);
						break;
					case '5':
						var input=jQuery("<input type='text' />");
						input.attr("name","valuefor_"+name);
						input.attr("readonly","readonly");
						input.attr("id",name).addClass("required");
						html.append(input);
						input.val(attrvalueStrMap[id]);
						input.click(function(){
							WdatePicker({dateFmt:'yyyy/MM/dd'});
						});
						
						var hdinput=jQuery("<input type='hidden' />");
						hdinput.attr("name",name);
						html.append(hdinput);
						break;
					case '6':
						var input=jQuery("<input type='text' />");
						input.attr("name","valuefor_"+name);
						input.attr("readonly","readonly");
						input.attr("id",name).addClass("required");;
						html.append(input);
						input.val(attrvalueStrMap[id]);
						input.click(function(){
							WdatePicker({dateFmt:'yyyy/MM/dd HH:mm'});
						});
						
						var hdinput=jQuery("<input type='hidden' />");
						hdinput.attr("name",name);
						html.append(hdinput);
						break;
					case '7':
						var input=jQuery("<input type='text' />");
						input.attr("name","valuefor_"+name);
						input.attr("readonly","readonly");
						input.attr("id",name).addClass("required");;
						html.append(input);
						input.val(attrvalueStrMap[id]);
						input.click(function(){
							WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'});
						});
						
						var hdinput=jQuery("<input type='hidden' />");
						hdinput.attr("name",name);
						html.append(hdinput);
						break;
					default:
						break;
				}
			}
		}
	}
	getGoodsAttrs(goodsId,callback);
}


/**
 * 获取商品类目
 * @param pid 父级id
 * @param callback 回调方法
 */
function getGoodsItems(pid,callback){
	jQuery.blockUI({message:messageUIConf});
	jQuery.post(WEB_PATH+"/portal/goods/goodsItem/goodsItemJsonByPid.htm",{"pid":pid},function(data){
		if(callback){
			callback(data);
			jQuery.unblockUI();
		}
	},"json");
}

/**
 * 获取商品属性
 * @param goodsId 商品id
 * @param callback 回调方法
 */
function getGoodsAttrs(goodsId,callback){
	jQuery.blockUI({message:messageUIConf});
	jQuery.post(WEB_PATH+"/portal/goods/goodsAttr/attrJsonByGoodsId.htm",{"goodsId":goodsId},function(data){
		if(callback){
			callback(data);
			jQuery.unblockUI();
		}
	},"json");
}

/**
 * 获取商品属性值
 * @param goodsId 商品id
 * @param attrId 属性id
 * @param callback 回调方法
 */
function getGoodsAttrValues(goodsId,attrId,sel,callback){
	jQuery.blockUI({message:messageUIConf});
	jQuery.post(WEB_PATH+"/portal/goods/goodsAttrValues/attrValuesJson.htm",{"itemId":goodsId,"attrId":attrId},function(data){
		if(callback){
			callback(sel,data);
			jQuery.unblockUI();
		}
	},"json");
}

/**
 * 
 * @param name
 * @param callback
 */
function getItemsIdByName(names,split,callback){
	jQuery.blockUI({message:messageUIConf});
	jQuery.post(WEB_PATH+"/portal/goods/goodsItem/goodsItemsIdByName.htm",{"names":names,"split":split},function(data){
		if(callback){
			callback(data);
			jQuery.unblockUI();
		}
	},"json");
}

 