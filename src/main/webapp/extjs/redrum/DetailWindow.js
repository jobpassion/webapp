Ext.create('extjs.redrum.DetailWindow', {
	extend : 'Ext.window.Window',
	title : 'Detail',
	width : 600,
	layout : 'fit',
	items : Ext.create('Ext.form.Panel', {
		bodyPadding : 5,
		width : '100%',

		// The form will submit an AJAX request to this URL when submitted
		url : 'save-form.php',

		// Fields will be arranged vertically, stretched to full width
		layout : 'anchor',
		defaults : {
			anchor : '100%',
			border : false
		},

		// The fields
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

		// Reset and Submit buttons
		buttons : [ {
			text : '排队',
			handler : function() {
				//    	    	        	function tmpSave(){
				//    	    	        		Ext.apply(record.data, this.up('form').getValues(false,true));
				//    	    	        	}
				var vs = this.up('form').getValues(false, true);
				for ( var i in vs) {
					record.set(i, vs[i]);
				}
				//    	    	        		record.modified={content:'afdsa'};
				//    	    	        		record.commit();
				//    	    	        	tmpSave();
				this.up('window').close();
			}
		}, {
			text : '确定',
			formBind : true, //only enabled once the form is valid
			disabled : true,
			handler : function() {

				this.up('window').close();
			}
		} ]
	})
})