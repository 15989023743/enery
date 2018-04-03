package com.gdwz.energy.goods.directive;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.tools.view.ViewToolContext;

import com.gdwz.core.commons.ServicesFactory;
import com.gdwz.core.web.view.velocity.directive.AbstractDirective;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.energy.goods.service.IGoodsItemService;

/**
 * 
 * 创建时间：2014年9月28日 上午11:29:44
 * 项目名称：gdwz-energy-web
 * @author yanlun
 * @version 1.0
 * 文件名称：ResourceClassificationInitDirective.java
 * 类说明：公共模块-资源分类数据初始化
 */
public class ResourceClassificationInitDirective extends AbstractDirective {    
    /*
     * 指令内部的标签内容节点
     */
    private Node body;
    
    private IGoodsItemService goodsItemsService;
    
    @Override
	protected void doInit(InternalContextAdapter internalContext,
			ViewToolContext context, Writer writer, Node node) {
    	if ( node.jjtGetNumChildren() != 1 )
        {
            throw new VelocityException("#resourceClassificationInit(): body missing at  "
            		+"#resourceClassificationInit()"
                 + Log.formatFileString(this));
        }
    	goodsItemsService=ServicesFactory.getBean(IGoodsItemService.class);
    	body = node.jjtGetChild(0);
	}

	@Override
	protected boolean doRender(InternalContextAdapter internalContext,
			ViewToolContext context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException,
			MethodInvocationException {
		List<GoodsItem> resourceClassificationIsDepth0 = this.goodsItemsService.queryGoodsItemByDepth(0);
        
		Map<Long,List<GoodsItem>> itemMapsMap=new HashMap<Long,List<GoodsItem>>();
        for(GoodsItem item:resourceClassificationIsDepth0){
        	itemMapsMap.put(item.getId(),goodsItemsService.getEnabledGoodsByParentId(item.getId(), true));
        }
        
        
		if(resourceClassificationIsDepth0!=null && resourceClassificationIsDepth0.size()>0){
			StringWriter sw = new StringWriter();
			internalContext.localPut("resourceClassificationIsDepth0", resourceClassificationIsDepth0);
			internalContext.localPut("itemMapsMap", itemMapsMap);
			body.render(internalContext, sw);
	        writer.write(sw.toString());
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String getName() {
		return "resourceClassificationInit";
	}

	@Override
	public int getType() {
		return BLOCK;
	}
}
