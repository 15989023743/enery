package com.gdwz.energy.web.controller.manager.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.code.entity.Code;
import com.gdwz.energy.code.service.ICodeService;

/**
 * 编码管理Controller
 * @author cyh
 * Urls:
 */
@Controller
@RequestMapping(value="/manager/code/code/",description="编码管理")
public class CodeController extends CrudController<Code,String>{

	@Autowired
	private ICodeService iCodeService;
	
	@Override
	protected IGeneralService<Code> getDefaultService() {
		// TODO Auto-generated method stub
		return this.iCodeService;
	}

	@Override
	protected String getViewPath() {
		// TODO Auto-generated method stub
		return "manager/code/code/";
	}

}
