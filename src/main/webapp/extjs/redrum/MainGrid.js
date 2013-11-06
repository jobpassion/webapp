Ext.define('extjs.redrum.MainGrid', {
	extend : 'Ext.grid.Panel',
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
			Ext.create('Ext.window.Window', {
				title : 'Detail',
				width : 600,
				layout : 'fit',
				items : Ext.create('Ext.form.Panel', {
					bodyPadding : 5,
					width : '100%',
					url : 'save-form.php',
					layout : 'anchor',
					defaults : {
						anchor : '100%',
						border : false
					},
					border : false,
					defaultType : 'textfield',
					items : [ Ext.create('Ext.panel.Panel', {
						layout : {
							type : 'hbox',
							align : 'middle'
						},
						border : false,
						items : [ {
							xtype : 'label',
							forId : 'myFieldId',
							text : '亚马逊',
							width : '15%',
							margin : '0 0 0 10'
						}, {
							xtype : 'textfield',
							width : '60%',
							name : 'title',
							value : record.data.title,
							allowBlank : false
						}, {
							xtype : 'label',
							forId : 'myFieldId',
							text : '2013-03-01',
							width : '20%',
							margin : '0 0 0 20'
						} ]
					}), {
						xtype : 'textfield',
						width : '100%',
						name : 'url',
						value : record.data.url,
						margin : '20 10 0 10',
						allowBlank : false
					}, {
						width : '100%',
						xtype : 'textareafield',
						name : 'content',
						value : record.data.content,
						height : 250,
						margin : '20 10 0 10',
					} ],
					buttons : [ {
						text : '排队',
						handler : function() {
							var vs = this.up('form').getValues(false, true);
							for ( var i in vs) {
								record.set(i, vs[i]);
							}
							record.commit();
							vs.id = record.data.id;
							vs.clazz = record.data.clazz;
							Ext.Ajax.request({
							    url: baseUrl + 'weibo/save',
							    params: vs,
							    success: function(response){
							        alert('success');
							        // process server response here
							    }
							});
							this.up('window').close();
						}
					}, {
						text : '确定',
						formBind : true, //only enabled once the form is valid
						disabled : true,
						handler : function() {
							var vs = this.up('form').getValues(false, true);
							for ( var i in vs) {
								record.set(i, vs[i]);
							}
							record.commit();
							vs.id = record.data.id;
							vs.clazz = record.data.clazz;
							vs.immediately = true;
							Ext.Ajax.request({
							    url: baseUrl + 'weibo/save',
							    params: vs,
							    success: function(response){
							        alert('success');
							        // process server response here
							    }
							});
							this.up('window').close();
						}
					} ]
				})
			}).show();
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