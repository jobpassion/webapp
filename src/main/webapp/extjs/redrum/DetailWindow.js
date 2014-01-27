Ext.define(
    'extjs.redrum.DetailWindow', {
    	extend:'Ext.window.Window',
    	record:null,
    	store:null,
        initComponent: function () {
        	var record = this.record;
            Ext.apply(
                this, {
                    title: 'Detail',
                    width: 600,
                    layout: 'fit',
                    items: Ext.create(
                        'Ext.form.Panel', {
                            bodyPadding: 5,
                            width: '100%',
                            url: 'save-form.php',
                            layout: 'anchor',
                            defaults: {
                                anchor: '100%',
                                border: false
                            },
                            border: false,
                            defaultType: 'textfield',
                            items: [
                                Ext.create(
                                    'Ext.panel.Panel', {
                                        layout: {
                                            type: 'hbox',
                                            align: 'middle'
                                        },
                                        border: false,
                                        items: [{
                                            xtype: 'label',
                                            forId: 'myFieldId',
                                            text: '亚马逊',
                                            width: '15%',
                                            margin: '0 0 0 10'
                                        }, {
                                            xtype: 'textfield',
                                            width: '60%',
                                            name: 'title',
                                            listeners: {
                                                change: function (
                                                    t,
                                                    newValue) {
                                                    Ext
                                                        .getCmp(
                                                            'textCount')
                                                        .setText(
                                                            '还可以输入' + (130 - countCharacters(newValue + ' ' + Ext
                                                                .getCmp(
                                                                    'weiboContent')
                                                                .getValue()) * 0.5) + '个字')
                                                }
                                            },
                                            id: 'weiboTitle',
                                            value: record.data.title,
                                            allowBlank: false
                                        }, {
                                            xtype: 'label',
                                            forId: 'myFieldId',
                                            text: '2013-03-01',
                                            width: '20%',
                                            margin: '0 0 0 20'
                                        }]
                                    }), {
                                    xtype: 'textfield',
                                    width: '100%',
                                    name: 'url',
                                    value: record.data.url,
                                    margin: '20 10 0 10',
                                    allowBlank: false
                                }, {
                                    width: '100%',
                                    xtype: 'textareafield',
                                    id: 'weiboContent',
                                    name: 'content',
                                    listeners: {
                                        change: function (
                                            t,
                                            newValue) {
                                            Ext
                                                .getCmp(
                                                    'textCount')
                                                .setText(
                                                    '还可以输入' + (130 - countCharacters(Ext
                                                        .getCmp(
                                                            'weiboTitle')
                                                        .getValue() + ' ' + newValue) * 0.5) + '个字')
                                        }
                                    },
                                    value: record.data.content,
                                    height: 250,
                                    margin: '20 10 0 10',
                                }, {
                                    xtype: 'label',
                                    text: '字数提示',
                                    id: 'textCount',
                                    margin: '0 0 0 10'
                                }
                            ],
                            buttons: [{
                                text: '排队',
                                handler: function () {
                                    var vs = this
                                        .up(
                                            'form')
                                        .getValues(
                                            false,
                                            true);
                                    for (var i in vs) {
                                        record
                                            .set(
                                                i,
                                                vs[i]);
                                    }
                                    record
                                        .commit();
                                    vs.id = record.data.id;
                                    vs.clazz = record.data.clazz;
                                    Ext.Ajax
                                        .request({
                                            url: baseUrl + 'weibo/save',
                                            params: vs,
                                            success: function (
                                                response) {
                                                alert('success');
                                                // process server response here
                                            }
                                        });
                                    this.up('window').store.reload();
                                    this
                                        .up(
                                            'window')
                                        .close();
                                }
                            }, {
                                text: '确定',
                                formBind: true, //only enabled once the form is valid
                                disabled: true,
                                handler: function () {
                                    var vs = this
                                        .up(
                                            'form')
                                        .getValues(
                                            false,
                                            true);
                                    for (var i in vs) {
                                        record
                                            .set(
                                                i,
                                                vs[i]);
                                    }
                                    record
                                        .commit();
                                    vs.id = record.data.id;
                                    vs.clazz = record.data.clazz;
                                    vs.immediately = true;
                                    Ext.Ajax
                                        .request({
                                            url: baseUrl + 'weibo/save',
                                            params: vs,
                                            success: function (
                                                response) {
                                                alert('success');
                                                // process server response here
                                            }
                                        });
                                    this.up('window').store.reload();
                                    this
                                        .up(
                                            'window')
                                        .close();
                                }
                            }]
                        })
                });
            this.callParent(arguments);  
        }
    });

function countCharacters(str) {
    var totalCount = 0;
    for (var i = 0; i < str.length; i++) {
        var c = str.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
            totalCount++;
        } else {
            totalCount += 2;
        }
    }
    // alert(totalCount);
    return totalCount;
}