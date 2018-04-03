package com.gdwz.energy.web.controller.manager.dataExchange;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.dataExchange.entity.LatestTrend;
import com.gdwz.energy.dataExchange.service.ILatestTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @包名称：com.gdwz.energy.web.controller.manager.dataExchange
 * @作者：YQ
 * @创建日期：2014/9/22
 */
@Controller
@RequestMapping(value = "/manager/dataExchange/latestTrend/", description = "数据汇管理/最新成交动态")
public class LatestTrendController extends CrudController<LatestTrend, String> {

    @Autowired
    private ILatestTrendService latestTrendService;

    @Override
    protected IGeneralService<LatestTrend> getDefaultService() {
        return latestTrendService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/dataExchange/latestTrend/";
    }
}
