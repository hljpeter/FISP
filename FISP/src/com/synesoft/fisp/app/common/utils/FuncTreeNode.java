package com.synesoft.fisp.app.common.utils;

import java.util.ArrayList;
import java.util.List;

public class FuncTreeNode {

	private String text = "";
	
	private boolean leaf = true;
	
	private String value = "";
	
	private String id = "";
	
	private boolean allowDrag = true;
	
	private boolean allowDrop = true;
	
	private boolean draggable = true;
	
	private boolean allowChildren = true;
	
	private String desc = "";
	
	private int orderNo = 0;
	
	private String qtip = "";
	
	private boolean checked = Boolean.FALSE;
	
	private boolean expanded = Boolean.TRUE;
	
	//复选框树
	private String roleId = "";
	
	//子节点
	private List<FuncTreeNode> children = new ArrayList<FuncTreeNode>();
	
	public FuncTreeNode(){
		
	}
	
	public FuncTreeNode(String text, String value, String id, boolean leaf, boolean checked){
		this.text = text;
		this.value = value;
		this.id = id;
		this.leaf =leaf;
		this.checked = checked;
	}

	
	/**
	 * @return the expanded
	 */
	public boolean getExpanded() {
		return expanded;
	}

	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	/**
	 * @return the children
	 */
	public List<FuncTreeNode> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<FuncTreeNode> children) {
		this.children = children;
	}

	/**
	 * @return the checked
	 */
	public boolean getChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isAllowDrag() {
		return allowDrag;
	}

	public void setAllowDrag(boolean allowDrag) {
		this.allowDrag = allowDrag;
	}

	public boolean isAllowDrop() {
		return allowDrop;
	}

	public void setAllowDrop(boolean allowDrop) {
		this.allowDrop = allowDrop;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public boolean isAllowChildren() {
		return allowChildren;
	}

	public void setAllowChildren(boolean allowChildren) {
		this.allowChildren = allowChildren;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


}
