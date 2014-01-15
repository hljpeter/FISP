<html>
<head id="Head1" runat="server">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>NavigationHide</title>
	<style type="text/css">
    body {
    	margin-left: 0px;
    	margin-top: 0px;
    	padding: 0px;
    	background-color: #fff;
    }
    a, a:hover, a:visited {
    	 text-decoration: none;
    </style>
</head>
    <script  language="JavaScript"  type ="text/javascript">
    function ChangeVisible()
    {
	    if(parent.document.getElementById('mainFrame').cols != "0,10,*")
	    {
		    parent.document.getElementById('mainFrame').cols = "0,10,*";
				parent.document.getElementById("mainFrame").rows=parent.document.getElementById("mainFrame").rows;
		    document.all.menuSwitch.innerHTML = "<a href='#' onclick='ChangeVisible();'>&raquo;</a>";
	    }
	    else
	    {
		    parent.document.getElementById('mainFrame').cols = "220,10,*";
				parent.document.getElementById("mainFrame").rows=parent.document.getElementById("mainFrame").rows;
		    document.all.menuSwitch.innerHTML = "<a href='#' onclick='ChangeVisible();'>&laquo;</a>";
	    }
    }
    </script>
    
<body style="text-align: center; padding: 0px; margin:0px;">
 	<div style="margin-left: auto; margin-top: auto">
 		<span id="menuSwitch"><a href="#" onclick="ChangeVisible();">&laquo;</a></span>
 	</div>
</body>
</html>