/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.constants;

/**
 * @file SQLMap.java
 * @author Jon
 * @date 2013-3-17
 * @description TODO
 * @tag 1.0.0
 * 
 */
public class SQLMap {

	/** #########################Common Sqlmap Id######################### **/
	// select by primary key
	public static final String SELECT_BYKEY = "selectByPrimaryKey";

	// select by primary key
	public static final String SELECT_KEY = "selectByKey";

	// select by primary key value from form
	public static final String SELECT_BYKEY_FORM = "selectByPrimaryKeyForm";

	//
	public static final String SELECT_ORGID_LIST = "selectOrgIdList";
	public static final String SELECT_USERROLE_LIST = "selectUserRoleList";

	// delete by primary key
	public static final String DELETE_BYKEY = "deleteByPrimaryKey";
	// insert save
	public static final String INSERT = "insert";
	// get system id sequence number
	public static final String IDSEQUENCE = "selectSequenceNumber";

	// update selective by primary key
	public static final String UPDATE_BYKEY = "updateByPrimaryKey";

	// update selective by primary key
	public static final String UPDATE_BYKEY_SELECTIVE = "updateByPrimaryKeySelective";
	// update by condition
	public static final String UPDATE_BY_CONDITION_SELECTIVE = "updateByCondtionSelective";
	// select list
	public static final String SELECT_LIST = "selectList";
	// select counts
	public static final String SELECT_COUNTS = "selectCounts";
	
	//select del
	public static final String SELECT_QUERYDEL="selectQueryDel";
	
	public static final String UPDATE_CURRENCY ="updateCurrency";
	
	public static final String DELETE_CUR ="deleteCur";
	
	public static final String INSERT_CUR="insertCur";
	// select map
	public static final String SELECT_MAP = "selectMap";
	// select list
	public static final String SELECT_AUTH_LIST = "selectAuthList";
	// select counts
	public static final String SELECT_AUTH_COUNTS = "selectAuthCounts";

	// select list
	public static final String SELECT_QUERY_LIST = "selectQueryList";
	// select counts
	public static final String SELECT_QUERY_COUNTS = "selectQueryCounts";

	public static final String SELECT_ORG_CNTS = "selectCountsByOrgId";

	public static final String SELECT_ROLE_CNTS = "selectCountsByRoleId";

	public static final String SELECT_LIST_FORM = "selectListForm";

	public static final String SELECT_COUNTS_FORM = "selectCountsForm";
	/** #########################Common Sqlmap Id######################### **/

	// select EXP_FILE_BY_SEQ
	public static final String SELECT_BY_BATCH_NO = "selectByBatchNo";

	// select MAX_LOG_ID
	public static final String SELECT_MAX_LOG_ID = "selectMaxLogId";
	// select BY FILE_ID_WITH_EXPORT
	public static final String SELECT_BY_FILE_ID_WITH_EXPORT = "selectByFileIdWithExport";

	// select BY FILE_ID_WITH_UNEXPORT
	public static final String SELECT_BY_FILE_ID_WITH_UNEXPORT = "selectByFileIdWithUnExport";

	public static final String SELECTDTFILECFGBYTID = "selectDtFieldCfgBytid";

	public static final String SELECT_BY_IMP_DEFKEY = "selectByKey";
	
	public static final String DELETE_BY_MPP_ID = "deleteByMppId";
	
	public static final String SELECT_COUNTS_BY_BIZKEYS = "selectCountsByBizKeys";
	
	public static final String SELECT_COUNTS_BY_BIZINFO = "selectCountsByBizInfo";
	
	public static final String SELECT_MOTHEDRETURNTYPE = "selectMothedReturnType";
	
	public static final String DELETE_BY_EXP_ID = "deleteByExpId";
	
	public static final String SELECT_LIST_BY_TABLEID = "selectListByTableId";
	
	public static final String SELECT_LIST_BY_FILENAME = "selectListByFileName";
	
	public static final String SELECT_AUTHORITY_COUNTS = "selectAuthorityCounts";
	
	public static final String SELECT_AUTHORITY_LIST = "selectAuthorityList";
	
	public static final String SELECT_0001_LIST = "select0001List";
	

	/** #########################TIPS_SYSSTA Sqlmap Id######################### **/

	/** #########################TIPS_SYSSTA Sqlmap Id######################### **/
	// select by ROLE_ID
	public static final String SELECT_BY_ROLE_ID = "selectByRoleId";
	// select by user
	public static final String SELECT_BY_USER = "selectByUser";
	// 
	public static final String SELECT_FIRST_MENUS = "selectFirstMenus";
	
	public static final String SELECT_ALL_MENUS = "selectAllMenus";
	
	public static final String SELECT_ALL_MENUS_NO_ORG = "selectAllMenusNoOrg";
	// 
	public static final String SELECT_SECOND_MENUS = "selectSecondMenus";
	// 
	public static final String SELECT_THIRD_MENUS = "selectThirdMenus";
	// 
	public static final String SELECT_ALL_FIR_MENUS = "selectAllFirMenus";
	// 
	public static final String SELECT_MENUS_BY_SUPID = "selectMenusBySupId";
	
	/*******BEGIN 系统参数表 **********************************************************************************/
	
	public static final String QUERY_SYS_PARAM_COUNT_CHECK = "querySysParamCountCheck";
	
	/*******END 系统参数表  **********************************************************************************/
	
	/** DP_EXPR_PARAVAL表删除无效数据 */
	public static final String DeleteByUseFlag = "deleteByUseFlag";
	/** DP_EXPR_PARAVAL根据useFlag更新数据 */
	public static final String UpdateByUseFlag = "updateByUseFlag";
	/** DP_EXPR_PARAVAL根据useFlag查询数据  **/
	public static final String SelectByUseFlag = "selectByUseFlag";

	/** 查询不在List中的信息 */
	public static final String SELECT_NOT_IN_LIST = "selectNotInList";
	/** 查询在List中的信息 */
	public static final String SELECT_IN_LIST = "selectInList";
	/** 查询正式表和临时表合并信息 */
	public static final String SELECT_MERGE_LIST = "selectMergeList";
	
	/**查询角色不绑定机构的资源**/
	public static final String SELECT_ROLE_NO_ORG ="selectByRoleNoOrg";
	
	public static final String SELECT_LIST_PAGE_ALL ="selectListByPageAll";
	
	public static final String SELECT_LIST_PAGE_CHECK ="selectListByPageCheck";
	
	public static final String SELECT_LIST_FOR_PAGE ="selectListForPage";
	
}
