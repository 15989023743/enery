package com.gdwz.energy.web.controller.manager.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.bank.entity.TCssAccount;
import com.gdwz.energy.bank.service.ITCssAccountService;

@Controller
@RequestMapping(value="/manager/bank/tCssAccount/",description="用户账户管理")
public class TCssAccountController extends CrudController<TCssAccount, String> {

	@Autowired
	private ITCssAccountService itCssAccountService;
	
	@Override
	protected IGeneralService<TCssAccount> getDefaultService() {
		// TODO Auto-generated method stub
		return this.itCssAccountService;
	}

	@Override
	protected String getViewPath() {
		// TODO Auto-generated method stub
		return "/manager/bank/tCssAccount/";
	}

}
