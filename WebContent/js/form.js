Ext.require(['Ext.grid.*','Ext.tree.*','Ext.data.*','Ext.window.MessageBox','Ext.panel.Panel', 'Ext.layout.container.Table']);

Ext.namespace('qtf');
qtf.reloadHistory = function () {
	store.load();
};

var store = Ext.create('Ext.data.JsonStore', {
    model: 'ImageModel',
    proxy: {
        type: 'ajax',
        url: 'history.jsp',
        reader: {
            type: 'json',
            root: 'builds'
        }
    }
});

qtf.reloadHistory = function () {
	store.load();
};

Ext.onReady(function() {
/************Utility methods *******************/
	function getMethodList(){
		var methods = new Array();
		var i=0;
		var records = tree.getChecked();
		Ext.Array.each(records, function(rec){
			methods[i++] = rec.parentNode.get('text') + "::" + rec.get('text');
		});
		return methods;
	};

	function saveSuite(btn, text){
		if(typeof text != "undefined") {
			if(validateSuiteName(text)) {
				Ext.Ajax.request({
					url: 'saveSuite.jsp',
		    	    params: {suiteName: text, "Mlist" : getMethodList()},
		    	    success: function(response){
		    	    	clearSuiteMap();
	        			suiteStore.load();
		    	    }
		    	});
    		} else {
    			alert("Name Already exists! Pick your next chioice :) ");
    		}
    	} else {alert("You are Crazy!");}
    };

	function validateSuiteName(suiteName){
		var suiteData = suiteStore.data;
		var status =true;
		suiteData.each(function(rec) {
			var nm = rec.data.name;
			if (suiteName == nm) {
				status = false;
				return false;
			}
		} );
		return status;
	}

    function clearSuiteMap() {
    	tree.collapseAll();
    	var records = tree.getChecked();
		Ext.Array.each(records, function(rec){
			rec.set("checked", false);
		});
    }

/************Suite Tree *******************/
	var tree = Ext.create('Ext.tree.Panel', {
		id: 'suiteTree',
		store: Ext.create('Ext.data.TreeStore', {
			proxy: {
				type: 'ajax',
				url: 'SuiteMap.jsp'
	   		},
	   		sorters: [{
	   			property: 'text',
	   			direction: 'ASC'}, {
	   			property: 'text',
	   			direction: 'ASC'
	   		}]
	   }),
	   rootVisible: false,
	   useArrows: true,
	   frame: true,
	   title: 'QTF - Test Suite Map',
	   renderTo: 'tree-div',
	   width: 500,
	   height: 400,
	   dockedItems: [{
		   xtype: 'toolbar',
		   dock: 'bottom',
		   items: {
		   		text: 'Save Suite',
		   		handler: function(){
		   			Ext.MessageBox.prompt('suiteName', 'Suite Name Please! :', saveSuite);
	   			}
            }
        }]
    });

	/************History *******************/
    Ext.define('history', {
        extend: 'Ext.data.Model',
        fields: ['id', 'result', {name:'duration', type: 'float'}, 'name']
    });

    var historyStore = Ext.create('Ext.data.JsonStore', {
        model: 'history',
        proxy: {
            type: 'ajax',
            url: 'history.jsp',
            reader: {
                type: 'json',
                root: 'builds'
            }
        },
        autoLoad: true
    });

    function result(val) {
        if (val == true) {
            return '<span style="color:green;">PASS</span>';
        } else {
            return '<span style="color:red;">FAIL</span>';
        }
        return val;
    };

    function name(val) {
    	var patt = /(\d+$)/i;
    	var folder = val.match(patt);
    	var param ="";
    	if(folder!=undefined) {
    		param = "?test=" + folder[0];
	    }
	    val = "<a href='showResult.jsp" + param +"'>"+ val +"</a>";
        return val;
    };

    var history = Ext.create('Ext.grid.Panel', {
        width:400,
        height:400,
        collapsible:false,
        direction: 'DSC',
        title:'QTF - Build History',
        renderTo: 'historyDiv',
        store: historyStore,
        multiSelect: true,
        viewConfig: {
            emptyText: 'No build History'
        },

        columns: [{
            text: 'Id',
            flex: 3,
            dataIndex: 'id'
        },{
            text: 'Test Name',
            flex: 50,
            renderer: name,
            dataIndex: 'name'
        },{
            text: 'Result',
            flex: 35,
            renderer: result,
            dataIndex: 'result'
        },{
            text: 'Duration',
            dataIndex: 'duration',
            flex: 15
    }]
    });
    
//    history.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
//        if (selectedRecord.length) {
//            var root = tree.getRootNode();
//            var methods = selectedRecord[0].data.methods;
//    		Ext.Array.each(methods, function(m){
//    			var fulldata = m.split("::");
//    			var cNode = root.findChild( "text", fulldata[0], true );
//    			cNode.expand();
//    			var mNode = cNode.findChild( "text", fulldata[1], true );
//    			mNode.set("checked", true);
//    		});
//        }
//    });

    /************Saved Suites *******************/
    Ext.define('suite', {
        extend: 'Ext.data.Model',
        fields: ['name', 'created', 'methods']
    });

	var suiteStore = new Ext.data.Store({
	    model: 'suite',
	    proxy: {
	        type: 'ajax',
	        url: 'saveSuite.jsp',
	        reader: {
	            type: 'json',
	            root: 'suites'
	        }
	    },
        autoLoad: true
	});

	var suites = Ext.create('Ext.grid.Panel', {
        width:300,
        height:400,
        collapsible:false,
        direction: 'DSC',
        title:'QTF - Saved Suites ',
        renderTo: 'suiteDiv',
        store: suiteStore,
        multiSelect: true,
        viewConfig: {
            emptyText: 'No Suite List'
        },
        columns: [{
            text: 'Suite Name',
            flex: 3,
            dataIndex: 'name'
        }]
    });

    suites.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
    	clearSuiteMap();
        if (selectedRecord.length) {
            var root = tree.getRootNode();
            var methods = selectedRecord[0].data.methods;
    		Ext.Array.each(methods, function(m){
    			var fulldata = m.split("::");
    			var cNode = root.findChild( "text", fulldata[0], true );
    			cNode.expand();
    			var mNode = cNode.findChild( "text", fulldata[1], true );
    			mNode.set("checked", true);
    		});
        }
    });

	/************Form *******************/
    var form = Ext.create('Ext.form.Panel', {
          border: true,
          fieldDefaults: {
              labelWidth: 75
          },
          url: 'start.jsp',
          defaultType: 'textfield',
          bodyPadding: 5,
          items: [{
              width:          200,
              xtype:          'combo',
              mode:           'local',
              value:          'test',
              triggerAction:  'all',
              forceSelection: true,
              editable:       false,
              fieldLabel:     'Environment',
              name:           'env',
              displayField:   'name',
              valueField:     'value',
              queryMode: 'local',
              store:          Ext.create('Ext.data.Store', {
                  fields : ['name', 'value'],
                  data   : [
                      {name : 'test',   value: 'test'},
                      {name : 'uat',  value: 'uat'},
                      {name : 'prod',  value: 'prod'},
                      {name : 'dev', value: 'dev'}
                  ]
              })
          },{
              width:          200,
              xtype:          'combo',
              mode:           'local',
              value:          'firefox',
              triggerAction:  'all',
              forceSelection: true,
              editable:       false,
              fieldLabel:     'Browser',
              name:           'driver',
              displayField:   'name',
              valueField:     'value',
              queryMode: 'local',
              store:          Ext.create('Ext.data.Store', {
                  fields : ['name', 'value'],
                  data   : [
                      {name : 'firefox',   value: 'firefox'},
                      {name : 'ie',  value: 'ie'},
                      {name : 'chrome', value: 'chrome'}
                  ]
              })
          },{
              width:          200,
              xtype:          'combo',
              mode:           'local',
              value:          'false',
              triggerAction:  'all',
              forceSelection: true,
              editable:       false,
              fieldLabel:     'Proxy ',
              name:           'proxy',
              displayField:   'name',
              valueField:     'value',
              queryMode: 'local',
              store:          Ext.create('Ext.data.Store', {
                  fields : ['name', 'value'],
                  data   : [
                      {name : 'Enabled',   value: 'true'},
                      {name : 'Disabled',  value: 'false'}
                  ]
              })
          }]
      });
    /************Form Window *******************/
    var win = Ext.create('Ext.window.Window', {
    	id: 'myPanel',
    	closable:false,
    	title: 'Select Run Parameters ',
    	width: 250,
    	height:160,
    	minWidth: 250,
    	minHeight: 160,
    	layout: 'fit',
    	plain: true,
    	items: form,
    	buttons: [{
    		text: 'Run Test',
    		handler:function() {
    		form.submit({
			  url : 'start.jsp',
			  params : {"Mlist" : getMethodList(), "run": true},
			  success : function(form, action) {
			  },
			  failure : function(form, action) {
			  }
			});
      		showProgress();
      		tree.store.load();
      		Ext.getBody().unmask();
      		win.hide();
          }
      },{
          text: 'Run Later',
          handler:function() {
      		Ext.getBody().unmask();
      		win.hide();
          }
      }]
  });
	/************************ Buttons *******************/
    Ext.create('Ext.button.Button', {
        text: 'Start',
        renderTo: 'startBtn',
        handler: function() {
    		var records = tree.getChecked(),
			names = [];
			if(records.length < 1) {
				Ext.MessageBox.show({
		            title: ' No Tests Selected! ',
		            buttons: Ext.Msg.OK,
		            msg: ' Are you crazy? No Tests selected for run. ' ,
		            icon: Ext.MessageBox.WARNING
				});
			} else {
	    		Ext.getBody().mask();
				win.show();
			}
    	}
    });

    Ext.create('Ext.button.Button', {
        text: 'Stop',
        renderTo: 'stopBtn',
        handler: function() {
	    	Ext.Ajax.request({
				url: 'stop.jsp',
				success: function(r) {
	    			qtf.reloadHistory();
			 	}
		 	});
    	}
    });
    win.hide();
});
