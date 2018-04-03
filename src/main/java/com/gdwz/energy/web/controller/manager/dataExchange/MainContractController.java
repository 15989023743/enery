package com.gdwz.energy.web.controller.manager.dataExchange;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.dataExchange.entity.MainContract;
import com.gdwz.energy.dataExchange.service.IMainContractService;
import com.gdwz.energy.web.controller.manager.dataExchange.json.JsonObj;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @包名称：com.gdwz.energy.web.controller.manager.dataExchange
 * @作者：YQ
 * @创建日期：2014/9/22
 */
@Controller
@RequestMapping(value = "/manager/dataExchange/mainContract/", description = "数据汇管理/主力合约价格管理")
public class MainContractController extends CrudController<MainContract, String> {

    @Autowired
    private IMainContractService mainContractService;

    @Override
    protected IGeneralService<MainContract> getDefaultService() {
        return mainContractService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/dataExchange/mainContract/";
    }


    /**
     * AJAX 验证同一时间，同一商品分类是否有报价
     *
     * @param dateTime
     * @return
     */
    @RequestMapping(value = "checkDataExist", description = "AJAX 验证同一时间是否有报价")
    @ResponseBody
    public ResponseEntity<String> checkDataExist(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "dateTime", required = true) String dateTime) {
        JsonObj obj = new JsonObj();
        if (StringUtils.isEmpty(dateTime)) {
            obj.setSuccess(false);
            obj.setMessage("价格时间不能为空");
        } else {
            try {
                if (mainContractService.checkDataExist(id, dateTime)) {
                    obj.setSuccess(false);
                    obj.setMessage("此主力合约相同时段存在报价");
                } else {
                    obj.setSuccess(true);
                    obj.setMessage("");
                }
            } catch (Exception e) {
                obj.setSuccess(false);
                obj.setMessage("服务器异常");
            }
        }
        String returnStr = jsonMapper.toJson(obj);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
        return new ResponseEntity<String>(returnStr, responseHeaders, HttpStatus.OK);
    }

}
