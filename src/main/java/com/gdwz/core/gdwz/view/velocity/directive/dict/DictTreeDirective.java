package com.gdwz.core.gdwz.view.velocity.directive.dict;

import com.gdwz.common.dict.service.IDictService;
import com.gdwz.core.commons.ServicesFactory;
import com.gdwz.core.entity.BaseDict;
import com.gdwz.core.web.view.velocity.directive.AbstractDirective;
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


public class DictTreeDirective extends AbstractDirective{
	
	 private IDictService dictService;
	  private String id;
	  private String name;
	  private String value;
	  private String dictname;
	  private String condition;
	  private String cssStyle;
	  private String cssClass;
	  private String onchange;

	  public String getName()
	  {
	    return "dictTree";
	  }

	  public int getType()
	  {
	    return 2;
	  }

	  protected void doInit(InternalContextAdapter internalContext, ViewToolContext context, Writer writer, Node node)
	  {
	    if (node.jjtGetNumChildren() != 8)
	    {
	      throw new VelocityException("#dictTree(): argument missing at  #dictTree(id,name,value,dictname,condition,cssClass,cssStyle,onchange)" + 
	        Log.formatFileString(this));
	    }
	    if (this.dictService == null) {
	      this.dictService = ((IDictService)ServicesFactory.getBean("dictService"));
	    }

	    this.id = ((SimpleNode)node.jjtGetChild(0)).value(internalContext).toString();
	    this.name = ((SimpleNode)node.jjtGetChild(1)).value(internalContext).toString();
	    this.value = ((SimpleNode)node.jjtGetChild(2)).value(internalContext).toString();
	    this.dictname = ((SimpleNode)node.jjtGetChild(3)).value(internalContext).toString();
	    this.condition = ((SimpleNode)node.jjtGetChild(4)).value(internalContext).toString();
	    this.cssClass = ((SimpleNode)node.jjtGetChild(5)).value(internalContext).toString();
	    this.cssStyle = ((SimpleNode)node.jjtGetChild(6)).value(internalContext).toString();

	    this.onchange = ((SimpleNode)node.jjtGetChild(7)).value(internalContext).toString();
	  }

	  protected boolean doRender(InternalContextAdapter internalContext, ViewToolContext context, Writer writer, Node node)
	    throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException
	  {
	    try
	    {
	      StringBuffer htmlOut = new StringBuffer();

	      String dictvalue = "";
	      if ((this.value != null) && (!this.value.equals("")) && (!this.value.equalsIgnoreCase("null")))
	      {
	        BaseDict dict = this.dictService.getValueByDictListKey(this.dictname, "", this.value);
	        if (dict != null)
	          dictvalue = dict.getMC();
	      }
	      htmlOut.append("<input type=\"text\"");
	      htmlOut.append(" name=\"");
	      htmlOut.append("mc" + this.name);
	      htmlOut.append("\"");

	      htmlOut.append(" value=\"");
	      htmlOut.append(dictvalue);
	      htmlOut.append("\"");

	      htmlOut.append(" id=\"");
	      htmlOut.append(this.id + "mc");
	      htmlOut.append("\"");

	      if (!this.cssClass.isEmpty()) {
	        htmlOut.append(" class=\"");
	        htmlOut.append(this.cssClass);
	        htmlOut.append("\"");
	      }
	      if (!this.cssStyle.isEmpty()) {
	        htmlOut.append(" style=\"");
	        htmlOut.append(this.cssStyle);
	        htmlOut.append("\"");
	      }

	      StringBuffer cnclick = new StringBuffer();

	      cnclick.append(" onclick='dictTreeOnClick(\"" + this.id + "\",\"" + this.id + "mc\",\"" + this.dictname + "\",\"" + this.condition + "\")");
	      if ((this.onchange != null) && (!this.onchange.equals(""))) {
	        cnclick.append(";" + this.onchange);
	      }
	      cnclick.append("'");
	      htmlOut.append(cnclick.toString());
	      htmlOut.append(" readonly=\"readonly\" title=\"请单击该输入框选择弹出字典输入\"/>\n");

	      htmlOut.append("<input type=\"button\" name=\"" + this.id + "Button\" value=\"...\" class=\"SmallButton\" style=\"width:22px\" \n");
	      htmlOut.append(cnclick.toString());
	      htmlOut.append(" TABINDEX=\"-1\"/>");
	      htmlOut.append("<input type=\"hidden\" name=\"" + this.name + "\" value=\"" + this.value + "\" id=\"" + this.id + "\" />\n");
	      writer.write(htmlOut.toString());
	      return true;
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return false;
	  }

}
