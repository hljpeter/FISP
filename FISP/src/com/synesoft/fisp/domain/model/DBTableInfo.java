/**
 * 
 */
package com.synesoft.fisp.domain.model;

/**
 * 数据库表信息
 * @date 2013-11-15
 * @author yyw
 *
 */
public class DBTableInfo {

	// 表空间
	private String tableSpaceName;
	// 表名
	private String tableName;
	// 表描述
	private String tableComments;
	// 列名
	private String colName;
	// 列类型
	private String colType;
	// 列长度
	private String colLen;
	// 是否可空
	private String nullable;
	// 列描述
	private String colComments;
	// owner
	private String owner;
	// 表名开头数组
	private String[] tableNameStarts;
	// 表空间
	private String[] tablespaceNames;
	// 查询类型
	private String type;
	// 排除的表名
	private String tableNameExclude;
	// 排除的表名
	private String[] tableNameExcludes;
	// owner
	private String[] owners;
	
	/**
	 * @return the owners
	 */
	public String[] getOwners() {
		return owners;
	}
	/**
	 * @param owners the owners to set
	 */
	public void setOwners(String[] owners) {
		this.owners = owners;
	}
	/**
	 * @return the tableNameExcludes
	 */
	public String[] getTableNameExcludes() {
		return tableNameExcludes;
	}
	/**
	 * @param tableNameExcludes the tableNameExcludes to set
	 */
	public void setTableNameExcludes(String[] tableNameExcludes) {
		this.tableNameExcludes = tableNameExcludes;
	}
	/**
	 * @return the tableNameExclude
	 */
	public String getTableNameExclude() {
		return tableNameExclude;
	}
	/**
	 * @param tableNameExclude the tableNameExclude to set
	 */
	public void setTableNameExclude(String tableNameExclude) {
		this.tableNameExclude = tableNameExclude;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the tablespaceNames
	 */
	public String[] getTablespaceNames() {
		return tablespaceNames;
	}
	/**
	 * @param tablespaceNames the tablespaceNames to set
	 */
	public void setTablespaceNames(String[] tablespaceNames) {
		this.tablespaceNames = tablespaceNames;
	}
	/**
	 * @return the tableSpaceName
	 */
	public String getTableSpaceName() {
		return tableSpaceName;
	}
	/**
	 * @param tableSpaceName the tableSpaceName to set
	 */
	public void setTableSpaceName(String tableSpaceName) {
		this.tableSpaceName = tableSpaceName;
	}
	/**
	 * @return the tableNameStarts
	 */
	public String[] getTableNameStarts() {
		return tableNameStarts;
	}
	/**
	 * @param tableNameStarts the tableNameStarts to set
	 */
	public void setTableNameStarts(String[] tableNameStarts) {
		this.tableNameStarts = tableNameStarts;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the tableComments
	 */
	public String getTableComments() {
		return tableComments;
	}
	/**
	 * @param tableComments the tableComments to set
	 */
	public void setTableComments(String tableComments) {
		this.tableComments = tableComments;
	}
	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}
	/**
	 * @param colName the colName to set
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}
	/**
	 * @return the colType
	 */
	public String getColType() {
		return colType;
	}
	/**
	 * @param colType the colType to set
	 */
	public void setColType(String colType) {
		this.colType = colType;
	}
	/**
	 * @return the colLen
	 */
	public String getColLen() {
		return colLen;
	}
	/**
	 * @param colLen the colLen to set
	 */
	public void setColLen(String colLen) {
		this.colLen = colLen;
	}
	/**
	 * @return the nullable
	 */
	public String getNullable() {
		return nullable;
	}
	/**
	 * @param nullable the nullable to set
	 */
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	/**
	 * @return the colComments
	 */
	public String getColComments() {
		return colComments;
	}
	/**
	 * @param colComments the colComments to set
	 */
	public void setColComments(String colComments) {
		this.colComments = colComments;
	}
	
	
}
