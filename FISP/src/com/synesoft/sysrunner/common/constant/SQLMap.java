/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.sysrunner.common.constant;

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
	// delete by primary key
	public static final String DELETE_BYKEY = "deleteByPrimaryKey";
	// insert save
	public static final String INSERT = "insert";
	// update all by primary key
	public static final String UPDATE_BYKEY = "updateByPrimaryKey";
	// update selective by primary key
	public static final String UPDATE_BYKEY_SELECTIVE = "updateByPrimaryKeySelective";
	// select list
	public static final String SELECT_LIST = "selectList";
	// select counts
	public static final String SELECT_COUNTS = "selectCounts";
	/** #########################Common Sqlmap Id######################### **/

	// query
	public static final String BAT_STEP_RTM_COUNTS_LIST = "selectCountList";

	public static final String BAT_STEP_RELY_STEP_RELY_LIST = "selectListForRely";

	public static final String DELETE_BAT_STEP_RELY_BY_STEPID = "deleteByStepId";

	public static final String DELETE_BAT_STEP_RELY_BY_TASKID = "deleteByTaskId";

	public static final String SELECT_BYTASKID_AND_RUNTIMEID = "selectByTaskIdAndRuntimeId";

	public static final String SELECT_LIST_MAX = "selectListForMax";

	public static final String SELECT_LIST_EXCL = "selectListForExcl";

	public static final String DELETE_RELY = "deleteRely";

	public static final String SELECT_QUERY_LIST = "selectQueryList";

	public static final String SELECT_DIM_TYPE_ID = "selectByDimTypeId";

	public static final String SELECT_DIM_ID = "selectByDimId";
	
	public static final String SELECT_LIST_EXCLUDE_THIS_TYPE = "selectListExcludeThisType";

}
