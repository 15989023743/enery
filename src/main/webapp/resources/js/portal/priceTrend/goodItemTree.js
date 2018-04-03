/**
 * 根据给定的参数构造商品分类树。
 * 如：new goodsItemTree({treeBoxId: 'treeBox', ctxPath: ctx,  usechk: false, onClickFn: function(event, treeId, treeNode, clickFlag){}});
 *
 * @param config.treeBoxId {string} zTree构造时的UL元素的ID [必须]
 * @param config.ctxPath {string} 远程加载树时URL的相对路径 [必须]
 * @param config.usechk {boolean} 是否使用checkBox [非必须]
 * @param config.onClickFn {function} 点击节点时的回调函数 [非必须]
 */
var goodsItemTree = function(config){
    //private variable.
    var _v_config = config || {};
    var _v_treeBoxId = config.treeBoxId || '';
    var _v_ctxPath = config.ctxPath || '';
    var _v_usechk = config.usechk || false;
    var _v_onClickFn = config.onClickFn || false;
    var _v_treeUrl = _v_ctxPath+'/portal/goods/goodsItem/load.htm';
    var _m_onClickFn = _v_onClickFn ? _v_onClickFn : function(){};
    var _action = config.action || "";
    var _data = config.data || null;
    var _v_treeObj = _m_init();
    //private method.
    //start _m_init;
    function _m_init(){
        //base config
        var _config =
            {
                method: "POST",
                action:_action,
                url: _v_treeUrl,
                ctx: _v_ctxPath,
                showcheck: _v_usechk, //是否显示选择
                onnodeclick: _m_onClickFn
            };
        _config.data = eval(_data);
        return $("#"+_v_treeBoxId).treeview(_config);
    }//End _m_init;
    //return
    return _v_treeObj;
}