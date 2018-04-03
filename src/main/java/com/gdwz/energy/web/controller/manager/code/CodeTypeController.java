package com.gdwz.energy.web.controller.manager.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.gdwz.common.member.entity.MemberDept;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.code.entity.CodeType;
import com.gdwz.energy.code.service.ICodeTypeService;

/**
 * 编码类型管理Controller
 * @author cyh
 * Urls:
 */
@Controller
@RequestMapping(value="/manager/code/codeType/",description="编码类型管理")
public class CodeTypeController extends CrudController<CodeType,String>{

	@Autowired
	private ICodeTypeService iCodeTypeService;
	
	@Override
	protected IGeneralService<CodeType> getDefaultService() {
		// TODO Auto-generated method stub
		return this.iCodeTypeService;
	}

	@Override
	protected String getViewPath() {
		// TODO Auto-generated method stub
		return "manager/code/codeType/";
	}
	
    /**
     * AJAX 编码类型名称是否唯一
     * @param orgCode
     * @param code
     * @return
     */
    @RequestMapping(value="checkName",description="编码类型名称是否唯一")
    @ResponseBody
    public String checkCode(@RequestParam("orgCode") String orgCode, @RequestParam("code") String code) {
        if(code.equals(orgCode)){
            return "true";
        }
        CodeType codeType = new CodeType();
        codeType.setName(code);
        if (iCodeTypeService.isUnique(codeType, "name")){
            return "true";
        }
        return "false";
    }

}
