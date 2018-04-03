var MainContractChart = function(width){
    var width = width;
    var mainContractChart;
    var loadData = function (){
        var obj = getParams();
        if( obj.itemId != "" ){
            //查询数据
            $.ajax({
                type: 'POST',
                url: ctx+'/portal/data/mainContract/chart.htm',
                async: false,
                data: obj,
                cache: false,
                dataType: 'json',
                success: function(json){
                    if( null != json ){
                        //加载图表
                        assemblyChartData(json);
                    }
                }
            });
        }
    };
    var assemblyChartData = function(json){
        var name = "指数";
        var option = PriceTrendChartUtil.getOption();
        PriceTrendChartUtil.setDataZoom(option,false);
        PriceTrendChartUtil.setToolbox(option,"");
        var x = parseInt(width)-92
        var legend = { data:[name],x:x, y:26 };
        var x2 = 32;
        var grid = { x:40,y:15,x2:x2,y2:30 };
        PriceTrendChartUtil.setLegend(option,legend);
        PriceTrendChartUtil.setGrid(option,grid);
        option.yAxis[0].axisLabel = { formatter: '{value} '}
        option.xAxis[0].data = PriceTrendChartUtil.getXaxisData(json,"priceDate","date");
        option.series = PriceTrendChartUtil.getSeriesData(json,name,"priceDate","priceStr","date");
        PriceTrendChartUtil.setAxisLabel(option,0,"date","yyyy-MM");
        PriceTrendChartUtil.setSymbol(option,0,"none");
        PriceTrendChartUtil.setSmooth(option,0,true);
        PriceTrendChartUtil.calculateXWidth(option,json,"priceStr");
        mainContractChart.setOption(option, true);
    };
    var getParams = function(){
        var type = 12;
        var title = "主力合约走势"
        var obj = {};
        obj.type = type;
        jQuery("#mainContractChartTitle").text(title);
        return obj;
    };
    require([ 'echarts',
            'echarts/chart/line',
            'echarts/chart/bar' ],
        function (ec) {
            mainContractChart = ec.init(document.getElementById('mainContractChart'));
            loadData();
        });
}