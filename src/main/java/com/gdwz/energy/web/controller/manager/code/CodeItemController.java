package com.gdwz.energy.web.controller.manager.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.code.entity.CodeItem;
import com.gdwz.energy.code.service.ICodeItemService;

/**
 * 编码项管理Controller
 * @author cyh
 * Urls:
 */
@Controller
@RequestMapping(value="/manager/code/codeItem/",description="编码项管理")
public class CodeItemController extends CrudController<CodeItem,String>{

	@Autowired
	private ICodeItemService iCodeItemService;
	
	@Override
	protected IGeneralService<CodeItem> getDefaultService() {
		// TODO Auto-generated method stub
		return this.iCodeItemService;
	}

	@Override
	protected String getViewPath() {
		// TODO Auto-generated method stub
		return "manager/code/codeItem/";
	}

}
