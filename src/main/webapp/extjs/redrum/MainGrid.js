Ext.define('extjs.redrum.MainGrid', {
	extend : 'Ext.grid.Panel',
    initComponent: function () {
        Ext.apply(this,{
	store : Ext.create('Ext.data.Store', {
		model : 'User',
		fields : [ {
			name : 'id',
			type : 'string'
		}, {
			name : 'title',
			type : 'string'
		}, {
			name : 'url',
			type : 'string'
		}, {
			name : 'content',
			type : 'string'
		}, {
			name : 'createDate',
			type : 'int'
		}, {
			name : 'source',
			type : 'string'
		}, {
			name : 'storeType',
			type : 'string'
		}, {
			name : 'lastModifyDate',
			type : 'string'
		}, {
			name : 'imgUrl',
			type : 'string'
		}, {
			name : 'clazz',
			type : 'string'
		} ],
		proxy : {
			type : 'ajax',
			url : baseUrl + 'weibo/query',
			reader : {
				type : 'json',
				root : 'datas'
			}
		},
		autoLoad : true
	}),

	plugins : [ new Ext.grid.plugin.CellEditing({
		clicksToEdit : 1
	}) ],
	listeners : {
		itemdblclick : function(_this, record, item) {
			Ext.create('extjs.redrum.DetailWindow', {record:record,store:_this.store}).show();
		}
	},
	selModel:new Ext.selection.CheckboxModel( {
        listeners:{
            selectionchange: function(selectionModel, selected, options){
                // Bunch of code to update store
            }
        }
    }),
	columns : [{
		text : 'Id',
		width : 50,
		dataIndex : 'id'
	}, {
		text : 'Title',
		dataIndex : 'title',
		editor : {
			allowBlank : false
		},
		flex : 1
	}, {
		text : 'content',
		dataIndex : 'content',
		flex : 1
	}, {
		text : 'createDate',
		dataIndex : 'createDate',
		renderer : renderDate,
		width : 100,
	//		xtype: 'datecolumn',
	}, {
		text : 'storeType',
		width : 80,
		dataIndex : 'storeType',
		width : 100,
	}, {
		text : 'Url',
		editor : {
			allowBlank : false
		},
		dataIndex : 'url'
	} ]
        });
        this.callParent(arguments);
        var _this = this;
        var runner = new Ext.util.TaskRunner();
        var task = runner.start({
            run: function(){
            	_this.store.reload();
            	console.log('reload data store');
            },
            interval: 30000
        });
    }
});

