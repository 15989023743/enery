package com.gdwz.energy.goods.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

import com.gdwz.core.commons.ServicesFactory;
import com.gdwz.core.web.view.velocity.directive.AbstractDirective;
import com.gdwz.energy.goods.service.IGoodsItemAttrValueService;

/**
 * 判断商品属性值是否关联的指令
 * @author shuangfeng
 *
 */
public class ItemAttrValueSelectDirective extends AbstractDirective {
	/**
	 * 类目ID
	 */
    private Long itemId;

    
    /**
     * 属性ID
     */
    private Long attrId;

    
    /**
     * 属性值ID
     */
    private String valuesId;
    
    private String bodyText;
    
    private IGoodsItemAttrValueService goodsItemAttrValueService;
    
    
    
    @Override
	protected void doInit(InternalContextAdapter internalContext,
			ViewToolContext context, Writer writer, Node node) {
    	if ( node.jjtGetNumChildren() != 4 )
        {
            throw new VelocityException("#itemAttrValueSelect(): argument missing at  "
            		+"#itemAttrValueSelect(itemId,attrId,valuesId)"
                 + Log.formatFileString(this));
        }
		goodsItemAttrValueService=ServicesFactory.getBean(IGoodsItemAttrValueService.class);
		itemId = Long.parseLong(((SimpleNode) node.jjtGetChild(0)).value(internalContext).toString());
		attrId = Long.parseLong(((SimpleNode) node.jjtGetChild(1)).value(internalContext).toString());
		valuesId = ((SimpleNode) node.jjtGetChild(2)).value(internalContext).toString();
		bodyText = node.jjtGetChild(3).literal();
	}

	@Override
	protected boolean doRender(InternalContextAdapter internalContext,
			ViewToolContext context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException,
			MethodInvocationException {
		if(this.itemId!=null&&this.attrId!=null&&this.valuesId!=null){
			Long count=goodsItemAttrValueService.getCountByAllKeys(itemId, attrId, valuesId);
			if(count==null||count==0){
				context.getVelocityEngine().evaluate(context, writer, "itemAttrValueSelect", bodyText);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return "itemAttrValueSelect";
	}

	@Override
	public int getType() {
		return BLOCK;
	}

}
