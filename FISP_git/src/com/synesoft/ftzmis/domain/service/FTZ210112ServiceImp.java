package com.synesoft.ftzmis.domain.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZ210101Repository;
import com.synesoft.ftzmis.domain.repository.FTZ210112Repository;

@Service
public class FTZ210112ServiceImp implements FTZ210112Service {

	

	@Resource
	protected FTZ210112Repository ftz210112Repos;
}
