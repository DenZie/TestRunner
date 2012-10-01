Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '../ux');

Ext.require([
    'Ext.tip.QuickTipManager',
    'Ext.container.Viewport',
    'Ext.layout.*',
    'Ext.form.Panel',
    'Ext.form.Label',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.tree.*',
    'Ext.selection.*',
    'Ext.tab.Panel',
    'Ext.ux.layout.Center'  
]);

Ext.onReady(function(){
	
    var layoutExamples = [];
    
    Ext.Object.each(getCombinationLayouts(), function(name, example){
        layoutExamples.push(example);
    });
    
    var contentPanel = {
            id: 'content-panel',
            region: 'center', // this is what makes this panel into a region within the containing layout
            layout: 'card',
            margins: '2 5 5 0',
            activeItem: 0,
            border: false,
            items: [layoutExamples]
       };
	
	Ext.create('Ext.Viewport', {
        layout: 'border',
        title: 'TestRunner',
        items: [contentPanel],
        renderTo: Ext.getBody()
    });
	Ext.getCmp('content-panel').layout.setActiveItem("abs-form-panel");
});