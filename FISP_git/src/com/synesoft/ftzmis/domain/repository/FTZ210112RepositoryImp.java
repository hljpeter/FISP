package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
@Repository
public class FTZ210112RepositoryImp implements FTZ210112Repository {
	

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<FtzInMsgCtl> pageFtzInMsgCtl;
	
	@Resource
	private PageHandler<FtzInTxnDtl> pageFtzInTxnDtl;
	
}
