package com.gdwz.energy.web.controller.portal.dataExchange;

import com.gdwz.core.commons.EntityHelper;
import com.gdwz.core.entity.BaseDict;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.PropertyFilter;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.energy.dataExchange.entity.DynamicFutures;
import com.gdwz.energy.dataExchange.entity.PriceTrend;
import com.gdwz.energy.dataExchange.service.IDynamicFuturesService;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.sys.session.IDict;
import com.gdwz.utils.Reflections;
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

/**
 * @包名称：com.gdwz.energy.web.controller.portal.dataExchange
 * @作者：YQ
 * @创建日期：2014/10/21
 */
@Controller
@RequestMapping(value = "/portal/data/dynamicFutures/", description = "门户_期货数据管理")
public class DynamicFuturesPortalController extends SimpleController {
    @Autowired
    private IDynamicFuturesService dynamicFuturesService;

    @Autowired
    private IDict dict;

    protected String getViewPath() {
        return "/portal/dataExchange/dynamicFutures/";
    }


    @RequestMapping(value = "portal",description = "门户_期货数据走势整体概况页面（主力合约）",aclLevel = EAclLevel.WHITE)
    public String portal_overview(@RequestParam(value = "width",defaultValue = "337")Integer width,@RequestParam(value = "height",defaultValue = "150")Integer height,ModelMap map){
        map.put("width",width);
        map.put("height",height);
        return getViewPath()+"portal_overview";
    }

    @RequestMapping(value = "chart", description = "AJAX 返回图形数据",aclLevel = EAclLevel.WHITE)
    @ResponseBody
    public ResponseEntity<String> chart(HttpServletRequest request,Long itemId,Integer type) {
        List<DynamicFutures> resultList = new ArrayList<DynamicFutures>();
        boolean isAll = false;
        Date date = null;
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
        filters.addFilter("goodsItem.id", itemId, PropertyFilter.MatchType.EQ);
        filters.setOrderBy("priceDate");
        filters.setOrder("asc");

        resultList = dynamicFuturesService.findByFilters(filters);
        if( null != resultList && resultList.size() > 0 ){
            buildDict(resultList.get(0));;
        }
        for (DynamicFutures dynamicFutures : resultList) {
            dynamicFutures.setPriceStr(dynamicFutures.getPrice()!=null?dynamicFutures.getPrice().toString():"");
            if(null!=dynamicFutures.getGoodsItem()){
                GoodsItem item = new GoodsItem();
                item.setId(dynamicFutures.getGoodsItem().getId());
                item.setName(dynamicFutures.getGoodsItem().getName());
                dynamicFutures.setGoodsItem(item);
            }
        }
        String returnStr = jsonMapper.toJson(resultList);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
        return new ResponseEntity<String>(returnStr, responseHeaders, HttpStatus.OK);
    }

    private void buildDict(DynamicFutures mainContract) {
        Map<String,String> dictnames = EntityHelper.getEntityDictName(mainContract.getClass());
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
                    for (String fieldName : fieldNames) {
                        if (Reflections.getAccessibleField(mainContract.getClass(), fieldName + "mc") != null) {
                            Object dmvalue = Reflections.getFieldValue(mainContract, fieldName);
                            String mcvalue = (String) dictmap.get(String.valueOf(dmvalue));
                            if (mcvalue != null) {
                                Reflections.setFieldValue(mainContract, fieldName + "mc", mcvalue);
                            }
                        }
                    }
                }
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
