Ext.define('extjs.redrum.TimeRange', {
	extend : 'Ext.grid.Panel',
	id : 'timeRange',
	store : Ext.create('Ext.data.Store', {
		model : 'User',
		fields : [{
			name : 'id',
			type : 'int'
		}, {
			name : 'clazz',
			type : 'string'
		}, {
			name : 'startTime',
			type : 'string'
		}, {
			name : 'endTime',
			type : 'string'
		}, {
			name : 'stores',
			type : 'string'
		}, {
			name : 'count',
			type : 'int'
		}, {
			name : 'maxCount',
			type : 'int'
		} ],
		proxy : {
			type : 'ajax',
			url : baseUrl + 'weibo/queryTime',
			reader : {
				type : 'json',
				root : 'datas'
			}
		},
		autoLoad : true
	}),

	plugins : [ new Ext.grid.plugin.CellEditing({
		clicksToEdit : 2
	}) ],
	selModel : new Ext.selection.CheckboxModel({
		listeners : {
			selectionchange : function(selectionModel, selected, options) {
				// Bunch of code to update store
			}
		}
	}),
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			text : 'Add',
			handler : function() {
				this.up('#timeRange').store.add({});
			}
		}, {
			text : 'Delete',
			handler : function() {
				var grid = this.up('#timeRange');
				var selections = grid.getSelectionModel().getSelection();
				Ext.each(selections, function(item) {
					grid.store.remove(item);
					if (item.data.id == 0)
						return;
					Ext.Ajax.request({
						url : baseUrl + 'weibo/delete',
						params : item.data,
						success : function(response) {
							// alert('success');
							// process
							// server
							// response
							// here
						}
					});
				});
			}
		}, {
			text : 'Apply',
			handler : function() {
				var records = this.up('#timeRange').store.getModifiedRecords();

				Ext.each(records, function(item) {
					item.commit();
					if(0==item.data.id)
						item.data.id=null;
					item.data.clazz='com.redrum.webapp.weibo.entity.TimeRangeEntity';
					Ext.Ajax.request({
						url : baseUrl + 'weibo/save',
						params : item.data,
						success : function(response) {
							// alert('success');
							// process
							// server
							// response
							// here
						}
					});
				});
			}
		} ]
	} ],
	columns : [ {
		text : 'Id',
		width : 50,
		dataIndex : 'id'
	}, {
		text : 'startTime',
		dataIndex : 'startTime',
		editor : {
			allowBlank : false
		},
		flex : 1
	}, {
		text : 'endTime',
		dataIndex : 'endTime',
		editor : {
			allowBlank : false
		},
		flex : 1
	}, {
		text : 'stores',
		dataIndex : 'stores',
		editor : {
			allowBlank : false
		},
		flex : 1
	//		xtype: 'datecolumn',
	}, {
		text : 'maxCount',
		dataIndex : 'maxCount',
		editor : {
			allowBlank : false
		},
		flex : 1
	} ]
});