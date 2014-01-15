package com.synesoft.fisp.domain.repository.sm;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.dataproc.jdbc.JdbcOperation;
import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.sm.model.Sm_Date_QryForm;
import com.synesoft.fisp.domain.component.PageHandler;
@Repository
public class HolidayMainRepositoryImp implements HolidayMainRepository {

	@Resource
	private PageHandler<Sm_Date_QryForm> pageH;
	@Resource
	private JdbcOperation jdbcOper;
	
	@Override
	public Page<Sm_Date_QryForm> queryInputList(Pageable pageable,
			Sm_Date_QryForm smDateForm) {
		// TODO Auto-generated method stub
		return pageH.getPage(Table.BAT_DATE_INFO, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, smDateForm, pageable);
	}

	@Override
	public List<Map<String, Object>> queryDetailList(String dtlYear) {
		StringBuffer sql= new StringBuffer();
		sql.append("select bat_date from bat_date_info where workday='N' and substr(bat_date,0,4) = '");
		sql.append(dtlYear);
		sql.append("' order by bat_date ");	
		
		return jdbcOper.queryForList(sql.toString());
	}

	@Override
	public void update(String addHolidays, String removeHolidays) {
		if(addHolidays != null && !"".equals(addHolidays)){
			String sql2 = "update bat_date_info set workday = 'N' where bat_date in("+addHolidays+")";
			jdbcOper.update(sql2);
		}
		if(removeHolidays != null && !"".equals(removeHolidays)){
			String sql1 = "update bat_date_info set workday = 'Y' where bat_date in("+removeHolidays+")";
			jdbcOper.update(sql1);
		}	
		
	}

	@Override
	public void update2(String strday, String endday, String holiflg) {		
		String sql = "update bat_date_info set workday = '"+holiflg+"' where bat_date >='"+strday+"' and bat_date <= '"+endday+"'";
		jdbcOper.update(sql);			
	}
	
}
