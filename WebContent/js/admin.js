Ext.require(['Ext.grid.*','Ext.tree.*','Ext.data.*','Ext.window.MessageBox','Ext.panel.Panel', 'Ext.layout.container.Table']);

Ext.onReady(function() {
/************Utility methods *******************/

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


	/************History *******************/
    Ext.define('history', {
        extend: 'Ext.data.Model',
        fields: ['name', 'clscount', 'mtscount']
    });

    var testJarsStore = Ext.create('Ext.data.JsonStore', {
        model: 'history',
        proxy: {
            type: 'ajax',
            url: 'testJars.jsp',
            reader: {
                type: 'json',
                root: 'testjars'
            }
        },
        autoLoad: true
    });

    var testJarsGrid = Ext.create('Ext.grid.Panel', {
        width:400,
        height:400,
        collapsible:false,
        direction: 'DSC',
        title:'QTF - Build History',
        renderTo: 'testJarsDiv',
        store: testJarsStore,
        multiSelect: true,
        viewConfig: {
            emptyText: 'No Test Jars'
        },
        columns: [{
            text: 'Jar Name',
            flex: 30,
            dataIndex: 'name'
        },{
            text: '# Classes',
            flex: 10,
            renderer: name,
            dataIndex: 'clscount'
        },{
            text: '# Methods',
            flex: 10,
            renderer: name,
            dataIndex: 'mtscount'
        }],
 	   dockedItems: [{
		   xtype: 'toolbar',
		   dock: 'top',
		   items: {
 		   		xtype: 'filefield',
 		   		buttonOnly: true,
 	            buttonText: 'Add Test Jar',
 	            buttonConfig: {
 		   			iconCls: 'icon-add'
 	            },
 	           listeners: {
 	               'change': function(fb, v){
 	                  alert(fb.getValue());
 	               }
 	           }
 		   		
//		   		text: 'Add Test Jar'
//		   		handler: function(){
//		   			Ext.MessageBox.prompt('suiteName', 'Suite Name Please! :', saveSuite);
//	   			}
            }
        }]
        
    });
    
    var fibasic = Ext.create('Ext.form.field.File', {
        renderTo: 'filebrowser',
        width: 400,
        hideLabel: true
    });
 

});
