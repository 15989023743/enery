package com.gdwz.energy.web.controller.portal.dataExchange;

import com.gdwz.core.commons.EntityHelper;
import com.gdwz.core.entity.BaseDict;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.energy.dataExchange.entity.PriceTrend;
import com.gdwz.energy.dataExchange.service.IPriceTrendService;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.energy.goods.service.IGoodsItemService;
import com.gdwz.rbac.domain.Acl;
import com.gdwz.sys.session.IDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.gdwz.utils.Reflections;

/**
 * @包名称：com.gdwz.energy.web.controller.portal.dataExchange
 * @作者：YQ
 * @创建日期：2014/9/24
 */
@Controller
@RequestMapping(value = "/indices/", description = "门户_价格指数管理")
public class PriceTrendPortalController extends SimpleController{

    @Autowired
    private IPriceTrendService priceTrendService;

    @Autowired
    private IGoodsItemService goodsItemService;

    @Autowired
    private IDict dict;

    protected String getViewPath() {
        return "/portal/dataExchange/priceTrend/";
    }

    @RequestMapping(value = "index",description = "门户_价格指数默认页面",aclLevel = EAclLevel.WHITE)
    public String index(Long itemId,ModelMap map){
        GoodsItem goodsItem = null;
        if( null != itemId ){
            goodsItem = goodsItemService.findById(itemId);

        }else {
            ListPropertyFilter filters = ListPropertyFilter.getInstance();
            filters.addFilter("depth",0, PropertyFilter.MatchType.EQ);
            Page pageParam = new Page<GoodsItem>(1);
            pageParam.setOrderBy("index");
            pageParam.setOrder("asc");
            Page<GoodsItem> page = goodsItemService.findPagesByFilters(pageParam,filters);
            if( null != page.getResult() && page.getResult().size() > 0){
                goodsItem = page.getResult().get(0);
            }
        }
        if( null != goodsItem ){
            map.put("goodsItem",goodsItem);
        }
        return getViewPath()+"index";
    }

    @RequestMapping(value = "portal",description = "门户_价格指数整体概况页面",aclLevel = EAclLevel.WHITE)
    public String portal_overview(@RequestParam(value = "width",defaultValue = "337")Integer width,@RequestParam(value = "height",defaultValue = "150")Integer height,ModelMap map){
        map.put("width",width);
        map.put("height",height);
//        map.put("itemId",itemId);
//        GoodsItem goodsItem = goodsItemService.findById(itemId);
//        map.put("goodsItem",goodsItem);
        return getViewPath()+"portal_overview";
    }

    @RequestMapping(value = "chart", description = "AJAX 返回图形数据",aclLevel = EAclLevel.WHITE)
    @ResponseBody
    public ResponseEntity<String> chart(HttpServletRequest request,Long itemId,Integer type) {
        List<PriceTrend> resultList = new ArrayList<PriceTrend>();
        boolean isAll = false;
        Date date = null;
        if( null != itemId ){
            if (type == 3) {
                date = getMonthStartAndEnd(Calendar.getInstance().getTime(), -3);
            }else if (type == 6) {
                date = getMonthStartAndEnd(Calendar.getInstance().getTime(), -6);
            }else if (type == 12) {
                date = getMonthStartAndEnd(Calendar.getInstance().getTime(), -12);
            }else if (type == 24) {
                date = getMonthStartAndEnd(Calendar.getInstance().getTime(), -24);
            }else if (type == 0) {
                isAll = true;
            }
            ListPropertyFilter filters = ListPropertyFilter.getInstance();
            if( !isAll ){
                if( null != date ){
                    filters.addFilter("priceDate",date, PropertyFilter.MatchType.GE);
                    filters.addFilter("priceDate",Calendar.getInstance().getTime(), PropertyFilter.MatchType.LE);
                }
            }
            filters.addFilter("goodsItem.id",itemId, PropertyFilter.MatchType.EQ);
            filters.setOrderBy("priceDate");
            filters.setOrder("asc");
            publicFilter(request,filters);
            resultList = priceTrendService.findByFilters(filters);
        }
        if( null != resultList && resultList.size() > 0 ){
            buildDict(resultList.get(0));;
        }
        for (PriceTrend priceTrend : resultList) {
            priceTrend.setPriceStr(priceTrend.getPrice()!=null?priceTrend.getPrice().toString():"");
            if(null!=priceTrend.getGoodsItem()){
                GoodsItem item = new GoodsItem();
                item.setId(priceTrend.getGoodsItem().getId());
                item.setName(priceTrend.getGoodsItem().getName());
                priceTrend.setGoodsItem(item);
            }
        }
        String returnStr = jsonMapper.toJson(resultList);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
        return new ResponseEntity<String>(returnStr, responseHeaders, HttpStatus.OK);
    }

    private void buildDict(PriceTrend priceTrend) {
        Map<String,String> dictnames = EntityHelper.getEntityDictName(priceTrend.getClass());
        Map dicts = new HashMap();
        List<BaseDict> list_dicts;
        if ((dictnames != null) && (dictnames.size() > 0)) {
            for (String dictname : dictnames.keySet()) {
                Map dictmap = new HashMap();
                list_dicts = dict.getDictByName(dictname);
                if (list_dicts != null) {
                    for (BaseDict dict : list_dicts) {
                        dictmap.put(dict.getDM(), dict.getMC());
                    }
                    dicts.put(dictname, dictmap);
                }
            }
        }
        if ((dicts != null) && (dicts.size() > 0)) {
            Map dictmap = null;
            String[] fieldNames = (String[])null;
            for (String dictname : dictnames.keySet()) {
                dictmap = (Map)dicts.get(dictname);
                if (dictmap != null) {
                    fieldNames = ((String)dictnames.get(dictname)).split(",");
                    for (String fieldName : fieldNames)
                        if (Reflections.getAccessibleField(priceTrend.getClass(), fieldName + "mc") != null) {
                            Object dmvalue = Reflections.getFieldValue(priceTrend, fieldName);
                            String mcvalue = (String)dictmap.get(String.valueOf(dmvalue));
                            if (mcvalue != null) {
                                Reflections.setFieldValue(priceTrend, fieldName + "mc", mcvalue);
                            }else{
                                Reflections.setFieldValue(priceTrend, fieldName + "mc", null);
                            }
                        }
                }
            }
        }
    }

    private void publicFilter(HttpServletRequest request,ListPropertyFilter filters) {
        Acl acl = (Acl)(request.getAttribute("acl_right")!=null?request.getAttribute("acl_right"):null);
        if(acl!=null && !acl.isSingle()){
            switch(acl.getRightCode().value()){
                case 1:
                    filters.addFilter("djrdm", MemberAuthenticationUtils.getCurrentUserName(request), PropertyFilter.MatchType.EQ);
                    break;
                case 2:
                    filters.addFilter("djdwjgdm", MemberAuthenticationUtils.getDeptId(request),PropertyFilter.MatchType.EQ);
                    break;
                case 3:
                    break;
                case 0:
                default:
                    filters.addFilter("djrdm",-1, PropertyFilter.MatchType.EQ);
                    break;
            }
        }
    }

    // 计算日期的方法 offset -1 上一个月，+1下一个月
    private static Date getMonthStartAndEnd(Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.add(Calendar.MONTH, offset);
        return cal.getTime();
    }

}
