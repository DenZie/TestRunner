<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html>
<head>
	<title>Packageizer</title>
 <style type="text/css">
        @import "http://o.aolcdn.com/dojo/1.2.0/dijit/themes/tundra/tundra.css";
        @import "http://o.aolcdn.com/dojo/1.2.0/dojo/resources/dojo.css"
    </style>
    <script type="text/javascript" src="http://o.aolcdn.com/dojo/1.2.0/dojo/dojo.xd.js" 
        djConfig="parseOnLoad: true"></script>
	<script type="text/javascript" src="/pack/js/CU/dojo.js"></script>
 	<style type="text/css">
	body {
		font-size: 12px;
		font-family: Arial;
	}

	h1,h2 {
		margin: 0;
	}
	
	h2 {
		border-bottom: 4px solid black;
	}
	
	.dojo {
		display: none;
	}

	.file {
		background: cyan;
		padding: 10px;
		margin: 10px;
	}

	#treeContainer {
		width: 50%;
		float: left;
		margin: 5px 0 0 0;
	}

	#searchContainer {
		width: 50%;
		float: right;
		margin: 5px 0 0 0;
	}

	#tree { 
		margin: 5px;
	}

	.dijitTreeIcon.class {
		display: none;
	}

	.dijitTreeLabel.class {
		margin-left: 0;
	}

	.dijitTreeRow input {
		margin: 0 3px 0 4px;
	}

	.actions {
		display: none;
	}
	</style>

</head>
<body class="tundra">
	<h1>Zend Framework 1.9 stable</h1>

<div id="treeContainer" class="dojo">
<h2>Select classes from the tree</h2>
<div id="tree"></div>
</div>

<div id="searchContainer" class="actions dojo">
	<h2>...and download them and their dependencies</h2>
	<form id="form" action="/pack/library/1.9/packet?time=1304964872" method="post">

	<fieldset>
		<legend>Package format:</legend>
		<input type="radio" name="format" value="zip" id="formatZip" /> <label for="formatZip">Zip</label>
		<input type="radio" name="format" value="phar" id="formatPhar" /> <label for="formatPhar">PHP Archive (Phar)</label>
	</fieldset>
	<input type="hidden" name="classes" value="" />

	<input type="submit" value="Get package" />
	</form>
	<div id="contents">
		<h3>Package contents:</h3>
	</div>
</div>

<div class="listContainer">
<h2>Packages</h2>
<ul id="list">

<li><a href="/pack/library/1.9/package/Zend_Registry">Zend_Registry</a></li>
<li><a href="/pack/library/1.9/package/Zend_Mail">Zend_Mail</a></li>
<li><a href="/pack/library/1.9/package/Zend_Navigation">Zend_Navigation</a></li>
<li><a href="/pack/library/1.9/package/Zend_Queue">Zend_Queue</a></li>
<li><a href="/pack/library/1.9/package/Zend_Pdf">Zend_Pdf</a></li>
<li><a href="/pack/library/1.9/package/Zend_Dojo">Zend_Dojo</a></li>
<li><a href="/pack/library/1.9/package/Zend_Paginator">Zend_Paginator</a></li>
<li><a href="/pack/library/1.9/package/Zend_Gdata">Zend_Gdata</a></li>
<li><a href="/pack/library/1.9/package/Zend_Locale">Zend_Locale</a></li>

<li><a href="/pack/library/1.9/package/Zend_Acl">Zend_Acl</a></li>
<li><a href="/pack/library/1.9/package/Zend_XmlRpc">Zend_XmlRpc</a></li>
<li><a href="/pack/library/1.9/package/Zend_Auth">Zend_Auth</a></li>
<li><a href="/pack/library/1.9/package/Zend_Wildfire">Zend_Wildfire</a></li>
<li><a href="/pack/library/1.9/package/Zend_TimeSync">Zend_TimeSync</a></li>
<li><a href="/pack/library/1.9/package/Zend_File_Transfer">Zend_File_Transfer</a></li>
<li><a href="/pack/library/1.9/package/Zend_Tag">Zend_Tag</a></li>
<li><a href="/pack/library/1.9/package/Zend_Server">Zend_Server</a></li>
<li><a href="/pack/library/1.9/package/Zend_Gdata_MimeFile">Zend_Gdata_MimeFile</a></li>

<li><a href="/pack/library/1.9/package/Zend_Gdata_MimeBodyString">Zend_Gdata_MimeBodyString</a></li>
<li><a href="/pack/library/1.9/package/Zend_Memory">Zend_Memory</a></li>
<li><a href="/pack/library/1.9/package/Zend_CodeGenerator">Zend_CodeGenerator</a></li>
<li><a href="/pack/library/1.9/package/Zend_Measure">Zend_Measure</a></li>
<li><a href="/pack/library/1.9/package/Zend_Crypt">Zend_Crypt</a></li>
<li><a href="/pack/library/1.9/package/Zend_Service">Zend_Service</a></li>
<li><a href="/pack/library/1.9/package/Zend_Service_Amazon">Zend_Service_Amazon</a></li>
<li><a href="/pack/library/1.9/package/Service">Service</a></li>
<li><a href="/pack/library/1.9/package/Zend_Config">Zend_Config</a></li>

<li><a href="/pack/library/1.9/package/Zend_Feed">Zend_Feed</a></li>
<li><a href="/pack/library/1.9/package/Zend_Feed_Reader">Zend_Feed_Reader</a></li>
<li><a href="/pack/library/1.9/package/Zend_View">Zend_View</a></li>
<li><a href="/pack/library/1.9/package/Zend_Date">Zend_Date</a></li>
<li><a href="/pack/library/1.9/package/Zend_Text_Figlet">Zend_Text_Figlet</a></li>
<li><a href="/pack/library/1.9/package/Zend_Text">Zend_Text</a></li>
<li><a href="/pack/library/1.9/package/Zend_Text_Table">Zend_Text_Table</a></li>
<li><a href="/pack/library/1.9/package/Zend_Log">Zend_Log</a></li>
<li><a href="/pack/library/1.9/package/Zend_Json">Zend_Json</a></li>

<li><a href="/pack/library/1.9/package/Zend_OpenId">Zend_OpenId</a></li>
<li><a href="/pack/library/1.9/package/Zend_Ldap%0D">Zend_Ldap
</a></li>
<li><a href="/pack/library/1.9/package/Zend_Ldap">Zend_Ldap</a></li>
<li><a href="/pack/library/1.9/package/Zend_Reflection">Zend_Reflection</a></li>
<li><a href="/pack/library/1.9/package/Zend_Rest">Zend_Rest</a></li>
<li><a href="/pack/library/1.9/package/Zend_Controller">Zend_Controller</a></li>
<li><a href="/pack/library/1.9/package/Zend_Captcha">Zend_Captcha</a></li>
<li><a href="/pack/library/1.9/package/Zend_Application">Zend_Application</a></li>
<li><a href="/pack/library/1.9/package/Zend_Translate">Zend_Translate</a></li>

<li><a href="/pack/library/1.9/package/Zend_Layout">Zend_Layout</a></li>
<li><a href="/pack/library/1.9/package/Zend_Currency">Zend_Currency</a></li>
<li><a href="/pack/library/1.9/package/Zend_Db">Zend_Db</a></li>
<li><a href="/pack/library/1.9/package/Zend_Mime">Zend_Mime</a></li>
<li><a href="/pack/library/1.9/package/Zend_Console_Getopt">Zend_Console_Getopt</a></li>
<li><a href="/pack/library/1.9/package/Zend_Form">Zend_Form</a></li>
<li><a href="/pack/library/1.9/package/Zend_Amf%0D">Zend_Amf
</a></li>
<li><a href="/pack/library/1.9/package/Zend_Amf">Zend_Amf</a></li>
<li><a href="/pack/library/1.9/package/Zend_Test">Zend_Test</a></li>

<li><a href="/pack/library/1.9/package/package">package</a></li>
<li><a href="/pack/library/1.9/package/Zend_ProgressBar">Zend_ProgressBar</a></li>
<li><a href="/pack/library/1.9/package/Zend_Validate">Zend_Validate</a></li>
<li><a href="/pack/library/1.9/package/Zend_Http">Zend_Http</a></li>
<li><a href="/pack/library/1.9/package/Zend_Loader">Zend_Loader</a></li>
<li><a href="/pack/library/1.9/package/Zend">Zend</a></li>
<li><a href="/pack/library/1.9/package/Zend_Version">Zend_Version</a></li>
<li><a href="/pack/library/1.9/package/Zend_Search_Lucene">Zend_Search_Lucene</a></li>
<li><a href="/pack/library/1.9/package/Zend_Search_Lucene%0D">Zend_Search_Lucene

</a></li>
<li><a href="/pack/library/1.9/package/Zend_Search">Zend_Search</a></li>
<li><a href="/pack/library/1.9/package/Zend_Uri">Zend_Uri</a></li>
<li><a href="/pack/library/1.9/package/Zend_Debug">Zend_Debug</a></li>
<li><a href="/pack/library/1.9/package/Zend_Soap">Zend_Soap</a></li>
<li><a href="/pack/library/1.9/package/Zend_Dom">Zend_Dom</a></li>
<li><a href="/pack/library/1.9/package/Zend_Filter">Zend_Filter</a></li>
<li><a href="/pack/library/1.9/package/Zend_InfoCard">Zend_InfoCard</a></li>
<li><a href="/pack/library/1.9/package/Zend_Session">Zend_Session</a></li>

<li><a href="/pack/library/1.9/package/default">default</a></li>
<li><a href="/pack/library/1.9/package/Zend_Cache">Zend_Cache</a></li>
<li><a href="/pack/library/1.9/package/Zend_Cache%0D">Zend_Cache
</a></li>
<li><a href="/pack/library/1.9/package/Zend_Tool">Zend_Tool</a></li>
</ul>
</div>

<div class="listContainer">
<h2>Classes</h2>
<ul id="list2">
</ul>
</div>

	<script type="text/javascript">
    //<![CDATA[
dojo.require('dojox.data.QueryReadStore');
dojo.require('dijit.TitlePane');

dojo.addOnLoad(function() {
	dojo.query('.listContainer').style({ display: 'none' });
	dojo.query('.dojo').style({ display: 'block' });
	var form = dojo.byId('form');
	form.onsubmit = function() {
		var classes = [];
		var selected = dojo.query('.selected');
		if(selected.length == 0)
			return false;

		for(var i = 0, len = selected.length; i < len; i++)
		{
			classes.push(selected[i].textContent);
		}

		form.classes.value = classes.join(',');
	};

	var store = new dojox.data.QueryReadStore({ url: '/pack/library/1.9' });
	var model = new CU.dojo.QueryStoreModel({ 
		store: store, 
		query: { package: '', format: 'json' },  
		rootId: 'treeRoot', 
		rootLabel: 'Packages', 
		childrenAttrs: 'children'
	}, 'store');
	var tree = new CU.dojo.ChkTree({ model: model }, 'tree');

	dojo.connect(tree, 'onNodeUnchecked', function(node) {
		dojo.removeClass(node.labelNode, 'selected');
		try {
		dijit.byId(node.labelNode.textContent).destroy();
		} catch(e) {
		}
	});

	dojo.connect(tree, 'onNodeChecked', function(node) {
		var clazz = node.labelNode.innerHTML;
		dojo.addClass(node.labelNode, 'selected');
		var div = dojo.doc.createElement('div');
		dojo.place(div, dojo.byId('contents'), 'last');
		var panel = new dijit.TitlePane({ title: clazz, id: clazz, open: false, 'content': 'Loading dependencies...' }, div);
		panel.startup();

		dojo.xhrGet({
			url: '/pack/library/1.9/dependencies/REPLACE?format=json'.replace('REPLACE', clazz),
			handleAs: 'json',
			load: function(response, ioArgs) {
				var pnl = dijit.byId(clazz);
				if(!pnl)
					return response;

				pnl.attr('content', '<strong>Files included for ' + clazz + '</strong><br /><br />' + response.join('<br />'));
				return response;
			}
		});

	});

	tree.startup();
});
    //]]>
</script></body>
</html>
