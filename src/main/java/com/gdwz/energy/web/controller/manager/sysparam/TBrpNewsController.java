package com.gdwz.energy.web.controller.manager.sysparam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.sysparam.entity.TBrpNews;
import com.gdwz.energy.sysparam.service.ITBrpNewsService;


/**
 * @author Gary
 *
 */
@Controller
@RequestMapping(value="/manager/sysParam/tBrpNews/",description="系统参数管理")
public class TBrpNewsController extends CrudController<TBrpNews,String> {

    @Autowired
    private ITBrpNewsService tBrpNewsService;

  
    @Override
    protected IGeneralService<TBrpNews> getDefaultService() {
        return this.tBrpNewsService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/sysparam/tBrpNews/";
    }


}
