package com.gdwz.energy.web.controller.manager.dataExchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.dataExchange.entity.DynamicFutures;
import com.gdwz.energy.dataExchange.service.IDynamicFuturesService;
import com.gdwz.energy.web.controller.manager.dataExchange.json.JsonObj;

/**
 * @包名称：com.gdwz.energy.web.controller.portal.dataExchange
 * @作者：YQ
 * @创建日期：2014/9/24
 */
@Controller
@RequestMapping(value = "/manager/dataExchange/dynamicFutures/", description = "数据汇管理/期货动态管理")
public class DynamicFuturesController extends CrudController<DynamicFutures, String>{

	@Autowired
	private IDynamicFuturesService dynamicFuturesService;
	
	@Override
	protected IGeneralService<DynamicFutures> getDefaultService() {
		return this.dynamicFuturesService;
	}

	@Override
	protected String getViewPath() {
		return "/manager/dataExchange/dynamicFutures/";
	}
	
	
	/**
	 * 保存实体(save)回调函数，在执行保存之前调用用。可以进行数据校验
	 * @param entity
	 * @param modelAndView
	 * @param request
	 * @param response
	 * @param errors
	 */
	 protected void beforeSave(DynamicFutures entity,ModelAndView modelAndView,HttpServletRequest request, HttpServletResponse response,BindException errors){
		 if (entity!=null && StringUtils.isEmpty(entity.getId())){
			 entity.setGoodsItemId(entity.getGoodsItem().getId());
		 }
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
                if (dynamicFuturesService.checkDataExist(id, dateTime)) {
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
