package com.gdwz.energy.code.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.code.dao.IWasteMatchingDao;
import com.gdwz.energy.code.entity.CodeItem;
import com.gdwz.energy.code.entity.WasteMatching;
import com.gdwz.energy.code.service.ICodeItemService;
import com.gdwz.energy.code.service.ICodeService;
import com.gdwz.energy.code.service.IWasteMatchingService;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： WasteMatchingServiceImpl extends GeneralServiceImpl
 * @类描述： 编码业务功能类
 * @当前版本 1.0
 * @修改备注：
 */
@SuppressWarnings("unchecked")
@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class WasteMatchingServiceImpl extends GeneralServiceImpl<WasteMatching,IWasteMatchingDao>
        implements IWasteMatchingService {


    @Resource
    private ICodeItemService codeItemService;
    
    @Resource
    private ICodeService codeService;

    private ArrayList codeItemValue;// 存储编码项

    private Queue<Object> queue;

    private static Object lock = new Object();


    private void toQueue(String[] array) {
        queue = new LinkedList();
        for (int i = 0; i < array.length; i++) {
            queue.offer(array[i]);
        }
    }

    public String getCode(String code, String[] vars) {
        this.toQueue(vars);
        return this.getCode(code);
    }

    /**
     * 获得编码
     *
     * @param code
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public String getCode(String code) {
        synchronized (lock) {
            StringBuffer sb = new StringBuffer();
            try {
                List list = codeItemService.getCodeItemsByCodeName(code);
                if (list == null) {
                    return null;
                }

                codeItemValue = new ArrayList();
                CodeItem codeItemObj = null;
                for (int i = 0; i < list.size(); i++) {
                    codeItemObj = (CodeItem) list.get(i);
                    Object type = codeItemObj.getType();
                    Object value = codeItemObj.getValue();
                    Object format = codeItemObj.getFormat();
                    Object length = codeItemObj.getLength();
                    Object codeItemId = codeItemObj.getCode().getId();
                    Object codeId = codeItemObj.getId();
                    if (codeItemId == null || codeId == null || type == null) {
                        return null;
                    }
                    if (value == null) {
                        value = "";
                    }
                    if (format == null) {
                        format = "";
                    }
                    if (length == null) {
                        length = "";
                    }

                    String codeItem = this.createCodeItem(type.toString(),
                            value.toString(), format.toString(),
                            length.toString(), codeItemId.toString(),
                            codeId.toString());
                    if (codeItem == null || codeItem.equals("")) {
                        return null;
                    }
                    codeItemValue.add(codeItem);
                    sb.append(codeItem);
                }
                return sb.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 创建编码项
     *
     * @param type
     * @param value
     * @param formart
     * @param length
     * @return
     */
    private String createCodeItem(String type, String value, String format,
                                  String length, String codeItemId, String codeId) {
        int iType = Integer.parseInt(type);
        switch (iType) {
            case 0:// 流水号
                if (value.equals("")) {
                    value = "0";
                }
                if (!format.equals("") && isResetWaste(codeId, format)) {
                    value = "0";
                }
                String waste = this.createWaste(Integer.parseInt(value),
                        Integer.parseInt(length));
                if (this.updateWaste(waste, codeId) == 1) {
                    if (this.updateCurrentDate(codeItemId) != 1) {
                        return null;
                    }
                    return waste;
                } else {
                    return null;
                }
            case 1:// 常量
                return value;
            case 2:// 时间
                return this.createDate(format);
            case 3:// 变量
                return this.createVar();
            case 4:// 动态流水号
                if (value.equals("")) {
                    return null;
                }
                String key = this.getWasteMatchingKey(value);
                if (!format.equals("") && isResetWaste(codeItemId, format)) {
                    value = "0";
                } else {
                    value = this.getWasteMatchingValue(codeItemId, key);
                }
                String activeWaste = this.createWaste(Integer.parseInt(value),
                        Integer.parseInt(length));
                if (this.updateWasteMatchingValue(codeItemId, key, activeWaste,
                        value) == 1) {
                    if (this.updateCurrentDate(codeItemId) != 1) {
                        return null;
                    }
                    return activeWaste;
                } else {
                    return null;
                }
        }
        return null;

    }

    /**
     * 获取活动流水对应关键字,以","号分隔
     *
     * @param ciValue
     * @return
     */
    private String getWasteMatchingKey(String ciValue) {
        String[] orders = ciValue.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orders.length; i++) {
            int order = Integer.parseInt(orders[i]);
            String value = this.codeItemValue.get(order).toString();
            sb.append(",").append(value);
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * 获取动态流水号
     *
     * @param codeId
     * @param key
     * @return
     * @throws com.psjc.CustomException
     */
    private String getWasteMatchingValue(String codeItemId, String key) {

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("codeitemid", codeItemId);
        param.put("key", key);
        param.put("deleteStatus", false);
        WasteMatching wasteMatching = getDefaultDAO().findUniqueByProperty(param);

        if (wasteMatching == null) {
            return "0";
        } else {
            return wasteMatching.getValue();
        }
    }

    /**
     * 更新流水号
     *
     * @param codeItemId
     * @param key
     * @param waste
     * @return
     * @throws com.psjc.CustomException
     */
    private int updateWasteMatchingValue(String codeItemId, String key,
                                         String waste, String oldValue) {

        int row = 0;
        WasteMatching wm = new WasteMatching();
        if (Integer.parseInt(waste) == 1) {
            wm.setCodeitemid(codeItemId);
            wm.setKey(key);
            wm.setValue(waste);
            wm.setDeleteStatus(false);
            wm.setAddTime(new Date());
            wm.setAddUserId("0");
            WasteMatching wasteMatching = getDefaultDAO().save(wm);
            if (wasteMatching != null) {
                row = 1;
            }
        } else {

            Map<String,Object> param = new HashMap<String,Object>();
            param.put("codeitemid", codeItemId);
            param.put("key", key);
            param.put("deleteStatus", false);
            param.put("value", oldValue);
            WasteMatching wasteMatching = this.getDefaultDAO().findUniqueByProperty(param);
            wasteMatching.setValue(waste);
//			sb.append("update t_sys_waste_matching set waste_value='")
//					.append(waste).append("' where code_item_id='")
//					.append(codeItemId).append("' and Key='").append(key)
//					.append("' and delete_status=0 and waste_value='")
//					.append(oldValue).append("'");
//			row = wasteMatchingDao.executeNativeSQL(sb.toString());
            wasteMatching = this.getDefaultDAO().save(wasteMatching);
            if (wasteMatching != null) {
                row = 1;
            }
        }

        return row;
    }

    /**
     * 创建格式化日期
     *
     * @param formart
     * @return
     */
    private String createDate(String formart) {
        StringBuffer sb = new StringBuffer();
        sb.append("select to_char(sysdate,'").append(formart)
                .append("') formartDate from dual");

        List list = getDefaultDAO().nativeQuery(sb.toString());

        if (list == null) {
            return null;
        }
        return list.get(0).toString();
    }

    /**
     * 创建变量值
     *
     * @return
     */
    private String createVar() {
        Object obj = this.queue.poll();
        if (obj == null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    /**
     * 创建流水号
     *
     * @param value
     * @param length
     * @return
     */
    private String createWaste(int value, int length) {
        int newValue = value + 1;
        String waste = String.format("%0" + String.valueOf(length) + "d", newValue);
        int wasteLen = waste.length();
        if (wasteLen > length) { //字符长度超出length，则截取字符串的后length个字符，列表length=3，生成的字符串=1000，则截取后=000
            waste = waste.substring(wasteLen - length, wasteLen);
        }
        return waste;
    }

    /**
     * 更新流水号
     *
     * @param waste
     * @param id
     * @return
     */
    private int updateWaste(String waste, String id) {
        return codeItemService.updateItemValue(id, waste);
    }

    /**
     * 更新当前时间
     *
     * @param codeId
     * @return
     */
    private int updateCurrentDate(String codeId) {
        return codeService.updateCurrentDate(codeId);
    }

    /**
     * 重置流水号
     *
     * @param codeId
     * @return
     */
    private boolean isResetWaste(String codeId, String format) {
        StringBuffer sb = new StringBuffer();
        long result = codeService.countByWaste(codeId, format);

        if (result == 1) {
            return false;
        } else {
            return true;
        }
    }

}
