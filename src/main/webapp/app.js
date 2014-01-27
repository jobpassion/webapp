function renderDate(val) {
    return Ext.util.Format.dateRenderer('Y-m-d')(new Date(val));
}

Ext
    .require(
        ['Ext.grid.*', 'Ext.data.*', 'Ext.Editor',
            'Ext.selection.CheckboxModel'
        ],
        function () {
            window.edit = function (id) {
                var editor = new Ext.Editor({
                    autoSize: {
                        width: 'boundEl', // The width will be
                        // determined by the width
                        // of the boundEl, the
                        // height from the editor
                        // (21)
                        height: 'boundEl' // The width will be
                        // determined by the width
                        // of the boundEl, the
                        // height from the editor
                        // (21)
                    },
                    updateEl: true, // update the innerHTML of the
                    // bound element when editing
                    // completes
                    field: {
                        xtype: 'textareafield'
                    }
                });
                editor.startEdit(Ext.get('tpl' + id));

            };

            //        var runner = new Ext.util.TaskRunner();
            //        var task = runner.start({
            //            run: function(){
            //                Ext.Ajax.request({
            //                    url: baseUrl + 'weibo/check',
            //                    success: function (response) {
            //                        // alert('success');
            //                        // process server response here
            //                    	if(null != Ext.getCmp('checkWeibo')){
            //                    		Ext.getCmp('checkWeibo').setText("check Weibo:" + response.responseText);
            //                    	}
            //                    }
            //                });
            //            },
            //            interval: 30000
            //        });

            var app = Ext
                .application({
                    name: 'HelloExt',
                    theme: 'ext-theme-neptune',
                    launch: function () {
                        var mainGrid = Ext.create(
                            "extjs.redrum.MainGrid", {
                                region: 'center'
                            });

                        Ext
                            .create(
                                'Ext.container.Viewport', {
                                    layout: 'border',
                                    items: [{
                                        xtype: 'panel',
                                        layout: 'border',
                                        region: 'center',
                                        items: [
                                            mainGrid, {
                                                xtype: 'panel',
                                                region: 'north',
                                                layout: 'absolute',
                                                items: [
                                                    /*{
																				xtype: 'button',
																				text: 'check weibo',
																				id: 'checkWeibo',
																				x: 100,
																				margin: '30, 30, 0, 0',
																				handler: function () {
																				Ext.create(
																				    'Ext.window.Window', {
																				        width: 600,
																				        title:'Set Cookies',
																				        layout: 'fit',
																				        items: [

																				            Ext.create(
																				                'Ext.form.Panel', {
																				                    bodyPadding: 5,
																				                    width: '100%',

																				                    url: baseUrl + 'weibo/setCookie',
																				                    layout: 'anchor',
																				                    defaults: {
																				                        anchor: '100%'
																				                    },

																				                    // The
																				                    // fields
																				                    defaultType: 'textfield',
																				                    items: [{
																				                        fieldLabel: '',
																				                        xtype:'textarea',
																				                        name: 'cookie',
																				                        allowBlank: false
																				                    }],

																				                    buttons: [{
																				                        text: 'Submit',
																				                        formBind: true, // only
																				                        disabled: true,
																				                        handler: function () {
																				                        	var _this = this;
																				                            var form = this
																				                                .up(
																				                                    'form')
																				                                .getForm();
																				                            if (form
																				                                .isValid()) {
																				                                form
																				                                    .submit({
																				                                    	headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
																				                                        success: function (
																				                                            form,
																				                                            action) {
																				                                            _this.up('window').close();
																				                                        },
																				                                        failure: function (
																				                                            form,
																				                                            action) {
																				                                            _this.up('window').close();
																				                                        }
																				                                    });
																				                            }
																				                        }
																				                    }]
																				                })
																				        ]
																				    })
																				    .show();
																				}
																				}*/
                                                    Ext
                                                    .create(
                                                        'extjs.redrum.TimeRange', {
                                                            width: '100%',
                                                            height: '100%'
                                                        })
                                                ],
                                                height: 200
                                            }, {
                                                xtype: 'panel',
                                                region: 'south',
                                                layout: 'column',
                                                height: 100,
                                                items: [{
                                                    html: '&nbsp;',
                                                    columnWidth: .5,
                                                    border: false
                                                }, {
                                                    xtype: 'button',
                                                    text: '排队',
                                                    margin: '30, 30, 0, 0',
                                                    handler: function () {
                                                        var selections = mainGrid
                                                            .getSelectionModel()
                                                            .getSelection();
                                                        Ext
                                                            .each(
                                                                selections,
                                                                function (
                                                                    item) {
                                                                    item
                                                                        .commit();
                                                                    Ext.Ajax
                                                                        .request({
                                                                            url: baseUrl + 'weibo/save',
                                                                            params: item.data,
                                                                            success: function (
                                                                                response) {
                                                                                // alert('success');
                                                                                // process
                                                                                // server
                                                                                // response
                                                                                // here
                                                                            }
                                                                        });
                                                                });
                                                        mainGrid
                                                            .getSelectionModel()
                                                            .deselectAll();
                                                    },
                                                    columnWidth: .15

                                                }, {
                                                    xtype: 'button',
                                                    text: '发送',
                                                    margin: '30, 30, 0, 0',

                                                    handler: function () {
                                                        var selections = mainGrid
                                                            .getSelectionModel()
                                                            .getSelection();
                                                        Ext
                                                            .each(
                                                                selections,
                                                                function (
                                                                    item) {
                                                                    item
                                                                        .commit();
                                                                    item.data.immediately = true;
                                                                    Ext.Ajax
                                                                        .request({
                                                                            url: baseUrl + 'weibo/save',
                                                                            params: item.data,
                                                                            success: function (
                                                                                response) {
                                                                                //alert('success');
                                                                                // process server response here
                                                                            }
                                                                        });
                                                                });
                                                        mainGrid
                                                            .getSelectionModel()
                                                            .deselectAll();
                                                    },
                                                    columnWidth: .15

                                                }, {
                                                    xtype: 'button',
                                                    text: '删除',
                                                    margin: '30, 30, 0, 0',
                                                    handler: function () {
                                                        var selections = mainGrid
                                                            .getSelectionModel()
                                                            .getSelection();
                                                        Ext
                                                            .each(
                                                                selections,
                                                                function (
                                                                    item) {
                                                                    //item.commit();
                                                                    //item.immediately = true;
                                                                    mainGrid.store
                                                                        .remove(item);
                                                                    Ext.Ajax
                                                                        .request({
                                                                            url: baseUrl + 'weibo/delete',
                                                                            params: item.data,
                                                                            success: function (
                                                                                response) {
                                                                                //alert('success');
                                                                                // process server response here
                                                                            }
                                                                        });
                                                                });
                                                        mainGrid
                                                            .getSelectionModel()
                                                            .deselectAll();
                                                    },
                                                    columnWidth: .15

                                                }]
                                            }
                                        ]
                                    }, {
                                        xtype: 'panel',
                                        title: '',
                                        region: 'west',
                                        layout: 'border',
                                        items: [{
                                            xtype: 'panel',
                                            region: 'south',
                                            items: [{
                                                xtype: 'button',
                                                handler: function () {
                                                	var record = mainGrid.store.add({'clazz':'com.redrum.webapp.weibo.WeiboMsg'});
                                        			Ext.create('extjs.redrum.DetailWindow', {record:record[0], store:mainGrid.store}).show();
                                                },
                                                text : '写微博'
                                            }]
                                        },{
                                            xtype: 'panel',
                                            region: 'center'
                                        }],
                                        width: 60
                                    }, {
                                        xtype: 'panel',
                                        title: 'ttttttttt',
                                        region: 'east',
                                        width: 150
                                    }]
                                });
                    }
                });

        });