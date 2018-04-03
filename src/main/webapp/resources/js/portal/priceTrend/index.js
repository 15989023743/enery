jQuery(function(){
    var priceTrendChart;
    require([ 'echarts',
        'echarts/chart/line',
        'echarts/chart/bar' ],
        function (ec) {
            priceTrendChart = ec.init(document.getElementById('priceTrendChart'));
            loadData();
        });
    var loadGoodsItem = function(){
        var actionurl = ctx+"/portal/goods/goodsItem/load.htm";
        jQuery.getJSON(actionurl,null,function(response){
            new goodsItemTree({
                treeBoxId:'tree',
                ctxPath:ctx+'/',
                data:response.treedata,
                onClickFn:goodsItemTreeCallback
            });
        });
    };
    var goodsItemTreeCallback = function(item){
        jQuery("#itemId").val(item.value);
        jQuery("#itemName").val(item.text);
        loadData();
    };

    var loadGoodsItemList = function(){
        var actionurl = ctx+"/portal/goods/goodsItem/loadGoodsItemList.htm";
        jQuery.getJSON(actionurl,null,function(response){
            if( null != response ){
                assemblyUlList(response);
            }
        });
    };

    var goodsItemACallback = function(item){
        jQuery("#itemId").val(item.value);
        jQuery("#itemName").val(item.text);
        jQuery("#parentids").val(item.parentids);
        loadData();
    };

    var  assemblyUlList = function (json) {
        var parentids = jQuery("#parentids").val();
        var itemId = jQuery("#itemId").val();
        var objArray = [];
        if ( null != parentids && parentids != ""){
            objArray = parentids.split("-")
        }
        var add = "navlefticon2.png";
        var plus = "navlefticon1.png"
        var html = "";
        var imgSrc = ctx+"/resources/style/portal/images/navleft/";
        var displayNoneStr = "none";
        var displayBlockStr = "block";
        for( var i in json){
            var li = '<li><ul class="selected3">';
            if ( objArray.length != 0 ){
                if( json[i].id == objArray[0] ){
                    if( json[i].id == itemId ){
                        li += '<li><a style="cursor:pointer;" itemId="'+json[i].id+'" itemName="'+json[i].name+'" itemParentids="'+json[i].parentids+'" title="'+json[i].name+'"><img src="'+imgSrc+plus+'" /><span>'+json[i].name+'</span></a><i class=""></i></li></ul><div style="display: '+displayBlockStr+'">';
                    }else{
                        li += '<li><a style="cursor:pointer;" itemId="'+json[i].id+'" itemName="'+json[i].name+'" itemParentids="'+json[i].parentids+'" title="'+json[i].name+'"><img src="'+imgSrc+plus+'" /><span>'+json[i].name+'</span></a><i class=""></i></li></ul><div style="display: '+displayBlockStr+'">';
                    }
                }else{
                    li += '<li><a style="cursor:pointer;" itemId="'+json[i].id+'" itemName="'+json[i].name+'" itemParentids="'+json[i].parentids+'" title="'+json[i].name+'"><img src="'+imgSrc+add+'" /><span>'+json[i].name+'</span></a><i class=""></i></li></ul><div style="display: '+displayNoneStr+'">';
                }
            }else{
                if( json[i].id == itemId ){
                    li += '<li><a  class="selected4" style="cursor:pointer;" itemId="'+json[i].id+'" itemName="'+json[i].name+'" itemParentids="'+json[i].parentids+'" title="'+json[i].name+'"><img src="'+imgSrc+plus+'" /><span>'+json[i].name+'</span></a><i class=""></i></li></ul><div style="display: '+displayBlockStr+'">';
                }else{
                    li += '<li><a style="cursor:pointer;" itemId="'+json[i].id+'" itemName="'+json[i].name+'" itemParentids="'+json[i].parentids+'" title="'+json[i].name+'"><img src="'+imgSrc+add+'" /><span>'+json[i].name+'</span></a><i class=""></i></li></ul><div style="display: '+displayNoneStr+'">';
                }
            }

            var ul = '<ul class="smallli">';
            for( var x in json[i].childs){
                if(json[i].childs[x].id == itemId){
                    ul += '<li><a class="selected4" style="cursor:pointer;" itemId="'+json[i].childs[x].id+'" itemName="'+json[i].childs[x].name+'" itemParentids="'+json[i].childs[x].parentids+'" title="'+json[i].childs[x].name+'">'+json[i].childs[x].name+'</a><i class="icon_navactive icon_navno"></i></li>';
                }else{
                    ul += '<li><a style="cursor:pointer;" itemId="'+json[i].childs[x].id+'" itemName="'+json[i].childs[x].name+'" itemParentids="'+json[i].childs[x].parentids+'" title="'+json[i].childs[x].name+'">'+json[i].childs[x].name+'</a><i class="icon_navno"></i></li>';
                }
            }
            ul += '</ul>';
            li +=  ul + "</div></li>";
            html += li;
        }
        jQuery("#goodsItemUL").html(html);
        jQuery("#goodsItemUL a").each(function(i,o){

            jQuery(this).click(function(){
                var flag = false ;
                var obj = {};
                if( typeof jQuery(this).children("img").eq(0).attr("src") != "undefined" ){
                    jQuery("#goodsItemUL a").removeClass("selected2");
                    jQuery("#goodsItemUL a").removeClass("selected4");
                    jQuery("#goodsItemUL a").siblings("i").removeClass("icon_navactive")
//                    jQuery(this).siblings("i").addClass("icon_navactive")
                    jQuery(this).addClass("selected4");
//                    jQuery(this).css("color","#ee5807");

                    if ( jQuery(this).children("img").eq(0).attr("src") != imgSrc + plus ){
                        jQuery("#goodsItemUL img").attr("src",imgSrc + add );
                        jQuery("#goodsItemUL div").css("display",displayNoneStr);
                        jQuery(this).children("img").eq(0).attr("src",imgSrc + plus );
                        jQuery(this).parent("li").parent("ul").siblings("div").css("display",displayBlockStr);
                    }else{
                        jQuery(this).children("img").eq(0).attr("src",imgSrc + add );
                        jQuery(this).parent("li").parent("ul").siblings("div").css("display",displayNoneStr);
//                        flag = true;
                    }
                }else{
                    jQuery("#goodsItemUL a").removeClass("selected2");
                    jQuery("#goodsItemUL a").removeClass("selected4")
                    jQuery("#goodsItemUL a").siblings("i").removeClass("icon_navactive")
                    jQuery(this).siblings("i").addClass("icon_navactive");
                    jQuery(this).parent("li").parent("ul").parent("div").prev("ul").children("li").eq(0).children("a").eq(0).addClass("selected4");
                    jQuery(this).addClass("selected2")
//                    jQuery(this).addClass("selected4")
                }
                if ( !flag ){
                    obj.value = jQuery(this).attr("itemId");
                    obj.text = jQuery(this).attr("itemName");
                    obj.parentids = jQuery(this).attr("itemParentids");
                    goodsItemACallback(obj);
                }
            })
        })
    }

    var loadData = function (){
        $("#selectGoodsName").text($("#itemName").val()+"价格指数")
        var obj = getParams();
        if( obj.itemId != "" ){
            //查询数据
            $.ajax({
                type: 'POST',
                url: ctx+'/indices/chart.htm',
                async: false,
                data: obj,
                cache: false,
                dataType: 'json',
                success: function(json){
                    if( null != json ){
                        //加载图表
                        assemblyChartData(json);
                        //加载表格数据
                        assemblyTableData(json);
                    }
                }
            });
        }
    };
    var assemblyChartData = function(json){
        var unit = "";
        if( json ){
            if(typeof json[0]!="undefined" && json[0].unitmc != null){
                unit = "("+json[0].unitmc+")";
            }
        }
        var option = PriceTrendChartUtil.getOption(jQuery("#itemName").val()+'指数趋势图');
        PriceTrendChartUtil.setSubTitle(option,unit);

        option.yAxis[0].axisLabel = { formatter: '{value} '}
        var name = "指数";
        var jsonArray = {legend:name};
        option.legend.data = PriceTrendChartUtil.getLegendData(jsonArray);
        option.xAxis[0].data = PriceTrendChartUtil.getXaxisData(json,"priceDate","date");
        option.series = PriceTrendChartUtil.getSeriesData(json,name,"priceDate","priceStr","date");
        PriceTrendChartUtil.setSymbol(option,0,"none");
        PriceTrendChartUtil.setSmooth(option,0,true);
        PriceTrendChartUtil.wrapTitle(option,34);
        PriceTrendChartUtil.calculateYHeight(option,34);
        PriceTrendChartUtil.calculateXWidth(option,json,"priceStr");
        priceTrendChart.setOption(option, true);
    };
    var assemblyTableData = function(json){
        var displayFields = ["priceStr","exponentPre","exponentPreAdd","exponentYear","exponentYearAdd","priceDate"];
        //清空内容
        $('#data').empty();
        var jsonArray = json ||[];
        //表格内容
        var tbodyHtml = '';
        for(var i=0 ; i<jsonArray.length;i++){
            tbodyHtml += '<tr>';
            tbodyHtml+= "<td>"+jsonArray[i]["goodsItem"]["name"]+"</td>";
            for(var x in displayFields){
                if ( displayFields[x] == "priceDate"){
                    var newDt = new Date(jsonArray[i][displayFields[x]]);
                    jsonArray[i][displayFields[x]] = newDt.format("yyyy-MM-dd");
                }
                tbodyHtml+= "<td>"+jsonArray[i][displayFields[x]]+"</td>";
            }
            tbodyHtml += '</tr>';
        }
        $('#data').append(tbodyHtml);
    };
    var getParams = function(){
        var type ;
        jQuery.each(jQuery("input[name=time]"),function(i,n){
            if(jQuery(this).prop("checked")){
                type = jQuery(this).val();
                var date = new Date();
                $("#etime").val(date.format("yyyy-MM-dd"));
                // 计算值
                switch (type) {
                    case "3" :
                        date = date.setMonth(date.getMonth()-3);
                        break;
                    case "6" :
                        date = date.setMonth(date.getMonth()-6);
                        break;
                    case "12" :
                        date = date.setMonth(date.getMonth()-12);
                        break;
                    case "24" :
                        date = date.setMonth(date.getMonth()-24);
                        break;
                    default :
                        date = null
                        break;
                }
                if( date == null ){
                    $("#stime").val("");
                    $("#etime").val("");
                }else{
                    $("#stime").val(new Date(date).format("yyyy-MM-dd"));
                }
                return;
            }
        });
        var itemId = jQuery("#itemId").val();
        var obj = {};
        obj.type = type;
        obj.itemId = itemId;
        return obj;
    };
    jQuery("input[name=time]").bind('click',function(){
        if($(this).val()==0){
            var re = confirm("数据量比较大，是否继续?");
            if(re == false)
                return false;
        }
        $(this).prop("checked","checked");
        loadData();
    });
    // 加载商品分类树
//    loadGoodsItem();
    loadGoodsItemList();
});