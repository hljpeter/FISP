
Ext.BLANK_IMAGE_URL = contextPath+'/resources/vendor/extjs/resources/images/default/s.gif';
Ext.ns('Syn.RoleMgr');

Ext.onReady(function() {
	var menuId = document.getElementById("menuId").value ;
	var editable = document.getElementById("editable").value;
	var cfgRole = {
		curRoleId :menuId,
		editable  :editable
	};
	var ui = new Syn.RoleMgr.RoleFuncUi(cfgRole);
	new Ext.Panel({
		renderTo : 'funcUI',
		width : 350,
		height : 450,
		layout : 'fit',
		border : false,
		items : ui
	});
	
});

/** 创建角色和功能维护界面UI **/
Syn.RoleMgr.RoleFuncUi = function(config) {
	Ext.apply(this, config);
	
	//当前选择的角色
	this.curRoleId = config.curRoleId;
	this.editable  = config.editable;
	/** 功能信息列表 **/
	this.loaderFuncTree = new Ext.tree.TreeLoader({
    	dataUrl : contextPath+'/sm00/01/search?menuId='+this.curRoleId,
    	requestMethod : 'POST',
    	listeners : {
			load : function(obj,node,response){
				var subnodes = node.childNodes;
				for(var s in subnodes){
					if(typeof subnodes[s] == 'object'){
						var snode = subnodes[s];
						var attr = snode.attributes;
						if(attr.checked){
							node.attributes.checked = true;
							node.ui.checkbox.checked = true;
						}
					}
				}
				//node.expand(true,true);
				if(config.editable=="false"){
					$("#funcUI").find("input[type='checkbox']").attr("disabled",true);
				}
			}
		}
    });
	
	/** 功能树 **/
	this.funcTree = new Ext.tree.TreePanel({
			id : 'funcTree',
            xtype: 'treepanel',
            iconCls : 'icon-plugin',
            flex: 1,
            region: 'center',
            border: false,
            autoScroll : true,
            margins: '',
            style: '',
            root: new Ext.tree.AsyncTreeNode({
				id : 'ALL_ROOT',
				text : "功能权限",
				checked: false,
		        draggable: false,
		        expanded : true
            }),
            loader: this.loaderFuncTree
        });

	//功能UI
	this.funcpanel = 
	{
        xtype: 'panel',
        region: 'center',
        layout: 'border',
        margins: '1px',
        items: [
            this.funcTree
        ]
    };
	
	//最外层的容器定义,包装roletree和功能UI
	Syn.RoleMgr.RoleFuncUi.superclass.constructor.call(this, {
	    layout : 'border',
	    items : [this.funcpanel]
	});
	
	//根据当前节点选中父节点及其子节点
	Ext.getCmp('funcTree').on('checkchange', function(node, checked) {
		selParent(node, checked);            
		selChild(node, checked);
	});
};

//这个方法是选择父节点,自动选中所有的子节点
function selParent(node, checked) {             
	//checked ? node.expand() : node.collapse();             
	if (node.hasChildNodes()) {                 
		node.eachChild(function(child) {                    
			child.attributes.checked = checked;                    
			var cb = child.ui.checkbox;                    
			if (cb)                    
			{                        
				cb.checked = checked;                    
			}                    
			selParent(child, checked);                
		});            
	}        
}        
//这个方法是选择子节点,自动选中父节点的父节点        
function selChild(node, checked) {            
	if (checked) {   
		//node.expand();                
		var parentNode = node.parentNode;                
		if (parentNode != undefined) {                   
			parentNode.attributes.checked = checked;                    
			var cb = parentNode.ui.checkbox;                    
			if (cb)                     
			{                        
				cb.checked = checked;                    
			}                    
			selChild(parentNode, checked);                
		}            
	}else{
		var flag = false;
        if(node == null){
            return;
        }
        var parentNode = node.parentNode;
        if(parentNode != undefined){
            if(parentNode.hasChildNodes()){
                parentNode.eachChild(function(child){
                    var cd = child.attributes.checked;
                    if(cd == true){
                        flag = true;
                    }
                });
            }
            if(!flag){
            	parentNode.attributes.checked = checked;
            	var cb = parentNode.ui.checkbox;
            	if(cb){
            		cb.checked = checked;
            		selChild(parentNode, checked);
            	}
            }
        }
	}        
}


/** 角色和功能维护界面，继承自Panel组件 **/
Ext.extend(Syn.RoleMgr.RoleFuncUi, Ext.Panel, {
	
	

});


