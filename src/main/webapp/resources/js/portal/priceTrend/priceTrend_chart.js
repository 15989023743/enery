/**
 * 时间对象的格式化
 */
Date.prototype.format = function(format){
    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    }

    if (/(y+)/.test(format)){
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4- RegExp.$1.length));
    }

    for (var k in o){
        if (new RegExp("(" + k + ")").test(format)){
            format = format.replace(RegExp.$1, RegExp.$1.length == 1? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
//使用Echarts时，导入需要的库文件
require.config({
    packages: [
        {
            name: 'echarts',
            location: ctx+'/resources/js/widgets/chart/echarts/src',
            main: 'echarts'
        },
        {
            name: 'zrender',
            location:  ctx+'/resources/js/widgets/chart/zrender/src',
            main: 'zrender'
        }
    ]
});
/**
 * Echarts帮助类
 */
var PriceTrendChartUtil = {
    /**
     * 获得公共的图表配置对象
     * @param {string} title 图表标题
     */
    getOption: function(title){
        return {
            title : {
                text: title || ''
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:[]
            },
            toolbox: {
                show : true,
                feature : {
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            dataZoom:{
                show:true
            },
            xAxis : [
                {
                    axisLabel:{},
                    type : 'category',
                    boundaryGap : false,
                    data : []
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    scale: true,
                    splitArea:{
                        show:false
                    }
                }
            ],
            series : []
        };
    }/** End getOption */
    ,setDataZoom : function(option,flag){
        option.dataZoom = {
            show:flag
        };
        return option;
    }/** End setDataZoom */
    ,wrapTitle : function(option,len){
        var title = option.title.text||"";
        if(title.replace(/[^\x00-\xff]/g,"xx").length <= len){
            option.title.text = title;
            return option;
        }
        var newTitle = "";
        var l = 0;
        var schar = "";
        for(var i=0;schar=title.charAt(i);i++){
            newTitle +=schar;
            l += (schar.match(/[^\x00-\xff]/)!=null?2:1) ;
            if( l >= len){
                newTitle += "\n";
                l = 0;
            }
        }
        option.title.text = newTitle;
        return option;
    }/** End wrapTitle */
    ,calculateYHeight : function(option,len){
        var title = option.title.text||"";
        if(title.replace(/[^\x00-\xff]/g,"xx").length <= len){
            return option;
        }
        var num = 0;
        var l = 0;
        var schar = "";
        for(var i=0;schar=title.charAt(i);i++){
            l += (schar.match(/[^\x00-\xff]/)!=null?2:1) ;
            if( l >= len){
                num++;
                l = 0;
            }
        }
        if( option.grid ){
            option.grid.y = num*25+10;
            return option;
        }else{
            return this.setGrid(option,{y:num*25+10});
        }
    }/** End calculateYHeight */
    ,calculateXWidth : function(option,json,yfield){
        var maxData = 0.0 ;
        for( var index in json ){
            for ( var item in json[index]){
                if( item == yfield){
                    maxData = parseFloat(Math.max(json[index][item],maxData)).toFixed(2);
                }
            }
        }
        var width = this.getStrWidth(maxData);
        if( width > 40 ){
            if( option.grid ){
                option.grid.x = width + 10 ;
                return option;
            }else{
                return this.setGrid(option,{ x:width+10 });
            }
        }
        return option
    }/** End calculateXWidth */
    ,getStrWidth : function(str,fontSize){
        var span = document.getElementById("__getwidth");
        if (span == null) {
            span = document.createElement("span");
            span.id = "__getwidth";
            document.body.appendChild(span);
            span.style.visibility = "hidden";
            span.style.whiteSpace = "nowrap";
        }
        span.innerHTML = str
        span.style.fontSize = fontSize || 12 + "px";
        return span.offsetWidth;
    }/** End getStrWidth */

    ,setSubTitle:function(option,title){
        option.title.subtext = title || ""
        return option;
    }/** End setSubTitle */

    ,setSplitArea : function(option,flag){
        option.yAxis.splitArea = {
            show:flag
        }
        return option;
    }/** End setSplitArea */
    ,setToolbox : function(option,toolbox){
        option.toolbox = toolbox;
        return option;
    }/** End setToolbox */

    ,setLegend  : function(option,legend){
        option.legend = legend;
        return option;
    }/** End setLegend */

    ,setGrid  : function(option,grid){
        option.grid = grid;
        return option;
    }/** End setGrid */
    ,setSymbol: function(option,index,symbol){
        option.series[index].symbol = symbol;
        return option;
    }/** End setSymbol */
    ,setSmooth: function(option,index,smooth){
        option.series[index].smooth = smooth;
        return option;
    }/** End setSmooth */
    /**
     * 获得图例数据
     * @param {array} jsonArray 二维数组
     */
    ,getLegendData: function(jsonArray){
        var _arr = [];
        for (var item in jsonArray){
            _arr.push(jsonArray[item]);
        }
        return _arr;
    }/** End getLegendData */

    /**
     * 获得x坐标数据
     * @param json      数据
     * @param axis      x轴显示字段
     * @param type      类型 date 或者 string
     * @returns {Array}
     */
    ,getXaxisData: function(json,axis,type){
        var axisData = [] ;
        for( var index in json ){
            for ( var item in json[index]){
                if( item == axis ){
                    if ( type.toLowerCase() == "date" ){
                        var newDt = new Date(json[index][item]);
                        axisData.push(newDt.format("yyyy-MM-dd"));
                    }else {
                        axisData.push(json[index][item]);
                    }
                }
            }
        }
        return axisData;
    }/** End getXaxisData */

    ,setAxisLabel : function(option,index,type,format){
        format = format || "yyyy-MM-dd";
        type = type || "string";
        index = index || 0;
        option.xAxis[index].axisLabel.formatter = function(value){
            if( type == "date"){
                var newDt = new Date(value.replace(/-/g,"\/"));
                return newDt.format(format);
            }else{
                return value;
            }
        }
        return option;
    }/** End setAxisLabel */

    /**
     * 获得图表数据
     * @param json      数据
     * @param ename     series名称
     * @param xfield    x轴显示字段
     * @param yfield    Y轴显示字段
     * @param type      类型 date 或者 string
     * @returns {Array}
     */
    ,getSeriesData: function(json,ename,xfield,yfield,type){
        var total = 0;
        //构造图表数据
        var returnArr = [];
        var data = [];
        for( var index in json ){
            var name = "";
            var value = ""
            for ( var item in json[index]){
                if( item == xfield){
                    if ( type.toLowerCase() == "date" ){
                        var newDt = new Date(json[index][item]);
                        name = newDt.format("yyyy-MM-dd")
                    }else {
                        name = json[index][item];
                    }

                }
                if( item == yfield){
                    value = json[index][item];
                }
            }
            if ( "" != name && "" != value ){
                data.push({value:value,name:name});
            }
        }
        //图表数据
        var _eobj = {
            name:ename,
            type:'line',
            data: data
        };
        returnArr.push( _eobj );
        return returnArr;
    }/** End getSeriesData */
};
