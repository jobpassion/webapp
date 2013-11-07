function renderDate(val) {
	return Ext.util.Format.dateRenderer('Y-m-d')(new Date(val));
}

Ext.require([ 'Ext.grid.*', 'Ext.data.*', 'Ext.Editor',
		'Ext.selection.CheckboxModel' ], function() {

	window.edit = function(id) {
		var editor = new Ext.Editor({
			autoSize : {
				width : 'boundEl', // The width will be determined by the width of the boundEl, the height from the editor (21)
				height : 'boundEl' // The width will be determined by the width of the boundEl, the height from the editor (21)
			},
			updateEl : true, // update the innerHTML of the bound element when editing completes
			field : {
				xtype : 'textareafield'
			}
		});
		editor.startEdit(Ext.get('tpl' + id));

	};
	var app = Ext.application({
		name : 'HelloExt',
		theme : 'ext-theme-access',
		launch : function() {
		    var mainGrid = Ext.create("extjs.redrum.MainGrid", {region:'center'});
			Ext.create('Ext.container.Viewport', {
				layout : 'border',
				items : [ {
					xtype : 'panel',
					layout:'border',
					region : 'center',
					items : [mainGrid, {
						xtype:'panel',
						region:'north',
						height:100
					}, {
						xtype:'panel',
						region:'south',
						layout:'column',
						height:100,
						items:[{html:'&nbsp;', columnWidth:.5, border:false},
						       {
						    	   xtype:'button',
						    	   text:'排队',
						    	   margin:'30, 30, 0, 0',
        						handler : function() {
        						    var selections = mainGrid.getSelectionModel().getSelection();
        						    Ext.each(selections, function (item) {
                                          item.commit();
                							Ext.Ajax.request({
                							    url: baseUrl + 'weibo/save',
                							    params: item.data,
                							    success: function(response){
                							        //alert('success');
                							        // process server response here
                							    }
                							});
                                        });
                                    mainGrid.getSelectionModel().deselectAll();
        						},
						    	   columnWidth:.15
						    		   
						       },{
						    	   xtype:'button',
						    	   text:'发送',
						    	   margin:'30, 30, 0, 0',
						    	   
        						handler : function() {
        						    var selections = mainGrid.getSelectionModel().getSelection();
        						    Ext.each(selections, function (item) {
                                          item.commit();
                                          item.immediately = true;
                							Ext.Ajax.request({
                							    url: baseUrl + 'weibo/save',
                							    params: item.data,
                							    success: function(response){
                							        //alert('success');
                							        // process server response here
                							    }
                							});
                                        });
                                    mainGrid.getSelectionModel().deselectAll();
        						},
						    	   columnWidth:.15
						    		   
						       },{
						    	   xtype:'button',
						    	   text:'删除',
						    	   margin:'30, 30, 0, 0',
        						handler : function() {
        						    var selections = mainGrid.getSelectionModel().getSelection();
        						    Ext.each(selections, function (item) {
                                          //item.commit();
                                          //item.immediately = true;
                                          mainGrid.store.remove(item);
                							Ext.Ajax.request({
                							    url: baseUrl + 'weibo/delete',
                							    params: item.data,
                							    success: function(response){
                							        //alert('success');
                							        // process server response here
                							    }
                							});
                                        });
                                    mainGrid.getSelectionModel().deselectAll();
        						},
						    	   columnWidth:.15
						    		   
						       }
						       ]
					}]
				}, , {
					xtype : 'panel',
					title : 'ttttttttt',
					region : 'east',
					width : 150
				} ]
			});
		}
	});

});