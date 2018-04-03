package com.gdwz.energy.web.controller.manager.seo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdwz.core.commons.ServicesFactory;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.cache.CacheManager;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.seo.entity.SeoConfig;
import com.gdwz.energy.seo.sercice.ISeoConfigService;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * @包名称：com.gdwz.energy.web.controller.manager.seo
 * @作者：YQ
 * @创建日期：2014/9/26
 */
@Controller
@RequestMapping(value = "/manager/seo/config/", description = "内容管理/SEO管理")
public class SeoConfigController extends CrudController<SeoConfig, String> {

    @Autowired
    private ISeoConfigService seoConfigService;

    @Override
    protected IGeneralService<SeoConfig> getDefaultService() {
        return this.seoConfigService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/seo/config/";
    }

    /**
     * AJAX 验证页面URL是否被配置
     *
     * @param id
     * @param url
     * @return
     */
    @RequestMapping(value = "checkDataExist", description = "AJAX 验证同一时间，同一商品分类是否有报价")
    @ResponseBody
    public ResponseEntity<String> checkDataExist(@RequestParam(value = "id", required = false) String id,  @RequestParam(value = "url", required = true) String url) {
        JsonObj obj = new JsonObj();
        if (StringUtils.isEmpty(url)) {
            obj.setSuccess(false);
            obj.setMessage("页面URL不能为空");
        } else {
            try {
                if (seoConfigService.checkDataExist(id, url)) {
                    obj.setSuccess(false);
                    obj.setMessage("此页面URL已经配置过SEO");
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

	@Override
	protected void afterSave(SeoConfig entity, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		//刷新seoConfig缓冲
		ServicesFactory.getBean(CacheManager.class).reloadSeoConfigToCache();
	}

	@Override
	protected void afterDelete(String id, HttpServletRequest request,
			HttpServletResponse response) {
		//刷新seoConfig缓冲
		ServicesFactory.getBean(CacheManager.class).reloadSeoConfigToCache();
	}

}
