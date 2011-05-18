<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head> 
        <title>Dijit Tree with Checkboxes </title>     

        <link rel="stylesheet" href="/Qtf/js/dojotoolkit/dijit/themes/nihilo/nihilo.css" />
        <script type="text/javascript" src="/Qtf/js/dojotoolkit/dojo/dojo.js"
            djConfig="parseOnLoad:false, isDebug:false"></script> 

        <script type="text/javascript">
        var selectedSuite =new Array();
        function renderSelectedSuite() {
        	var methodList = document.getElementById("divTmUl");
        	var methodDiv = document.getElementById("divTM");
        	var tagVisible ="<ul id='sltM' >";
        	var tagHidden ="";
	        	for(i in selectedSuite) {
	        		tagVisible = tagVisible + "<li value='" + i + "'>"+i+"</li>";
	        		tagHidden = tagHidden + "<input name='Mlist' type='hidden' value='" + i + "'/>";
	   		    }
	        	tagVisible = tagVisible + "</ul>";
	        	methodList.innerHTML= tagVisible;
	        	methodDiv.innerHTML= tagHidden;
	        };

        
        
			dojo.registerModulePath("tmpdir","/Qtf/js/tmpdir");
			dojo.require("dojo.data.ItemFileWriteStore");
			dojo.require("tmpdir.CheckBoxTree");

		function myTree( domLocation ) {
			var store = new dojo.data.ItemFileWriteStore( {
              url: "SuiteMap.jsp"
              });
			var model = new tmpdir.CheckBoxStoreModel( {
							store: store,
							query: {type: 'parent'},
							rootLabel: 'QTF Test Suite',
							checkboxAll:  true,
							checkboxRoot: true,
							checkboxState: true,
							checkboxStrict: true
							}); 
			var tree = new tmpdir.CheckBoxTree( {
							model: model,
							id: "MenuTree",
							allowMultiState: false,
							branchIcons: true,
							nodeIcons: true
							});
			tree.placeAt( domLocation );

			dojo.connect( tree,"onNodeChecked", function(storeItem, nodeWidget){
				if( !storeItem.root ) {
					var parent = nodeWidget.getParent();
					if(storeItem.type=="child") {
						var methodName = parent.label + "::" + storeItem.name;
					 	selectedSuite[methodName] = "1";
					 	renderSelectedSuite();
					} else {
					alert("Parent");
					}
					 }
				
				}
			);

			dojo.connect(tree, 'onNodeUnchecked', function(storeItem, nodeWidget) {
				if( !storeItem.root ) {
					var parent = nodeWidget.getParent();
					if(storeItem.type=="child") {
						var methodName = parent.label + "::" + storeItem.name;
						delete selectedSuite[methodName];
					 	renderSelectedSuite();
					} else {
					alert("Parent");
					}
					renderSelectedSuite();
				}
			});
			
			

		}

	
	</script> 
	</head>
    
    <body class="nihilo">
		<div id="CheckboxTree">	
			<script type="text/javascript">
				myTree("CheckboxTree");
			</script>
		</div>
		
		<div id="contents">
		<h3>Selected methods:</h3>
	</div>
	<div id="divTmUl" style="display: block"></div>
	<form id="suiteForm" method="post" action="start.jsp">
		<div id="divTM" name="divTM"></div>

		<input type="submit" value="Run Suite"></input>
	</form>
	
	</body> 
</html>