package com.gdwz.energy.web.controller.manager.bank;

import com.gdwz.common.dict.domain.DictZDY;
import com.gdwz.common.dict.service.IDictZDYService;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.bank.entity.TCssBank;
import com.gdwz.energy.bank.service.ITCssBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lemon on 14-8-19.
 */
@Controller
@RequestMapping(value="/manager/bank/tCssBank/",description="银行信息管理")
public class TCssBankController extends CrudController<TCssBank,String> {

    @Autowired
    private ITCssBankService tCssBankService;

    @Autowired
    private IDictZDYService dictZDYService;

    @Override
    protected IGeneralService<TCssBank> getDefaultService() {
        return this.tCssBankService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/bank/tCssBank/";
    }

/*    @Override
    protected void onCreate(TCssBank entity, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        DictZDY d= dictZDYService.findUniqueByProperty("ZDLB", "ZDY_FUND_FLOW");
        String fundFlow=d.getDM();
        int a=Integer.parseInt(fundFlow);
        if(a!=0){
            a+=1;
        }
        String flow="";
        for(int i = 6;i>fundFlow.length();i--){
            flow+="0";
        }

        flow+=a;
        flow.substring(flow.length()-6);
        d.setDM(Integer.toString(a));
        d.setMC(flow);
        dictZDYService.save(d);
        modelAndView.addObject("flow",flow);

    }*/
}
