package com.gdwz.energy.code.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.code.dao.ICodeDao;
import com.gdwz.energy.code.entity.Code;
import com.gdwz.energy.code.service.ICodeService;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： CodeServiceImpl extends GeneralServiceImpl
 * @类描述： 编码业务功能类
 * @当前版本 1.0
 * @修改备注：
 */
@Service
@Transactional
public class CodeServiceImpl extends GeneralServiceImpl<Code,ICodeDao> implements
		ICodeService {


	public List<Code> getAllCodes() {
		return getDefaultDAO().getAllCodes();
	}

	
	/**
     * 更新当前时间
     *
     * @param codeId
     * @return
     */
    public int updateCurrentDate(String codeId) {
        StringBuffer sb = new StringBuffer();
        sb.append("update Code set currdate=")
                .append("to_char(sysdate,'YYYY-MM-DD hh24:mi:ss')")
                .append(" where id='").append(codeId).append("'");
        int row = getDefaultDAO().executeUpdate(sb.toString());
        return row;
    }


	public long countByWaste(String codeId, String format) {
		StringBuffer sb = new StringBuffer();
        sb.append("from Code c where ")
                .append(" to_char(to_date(c.currdate,'YYYY-MM-DD hh24:mi:ss'),'")
                .append(format).append("')=to_char(sysdate,'").append(format)
                .append("') and c.id='").append(codeId).append("'");
        return getDefaultDAO().getCount(sb.toString(), new Object[]{});
	}
	
}
