<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
<html>
	<head>
		<title>Dojo: Hello World!</title>

		<!-- SECTION 1 -->
		<style type="text/css">
			@import "<c:url value="/js/dojo/dijit/themes/tundra/tundra.css" />";
			@import "<c:url value="/js/dojo/dojo/resources/dojo.css" />";
		</style>
		<script type="text/javascript" src="<c:url value="/js/dojo/dojo/dojo.js" />"
		  djConfig="parseOnLoad: true"></script>
		  
		<!-- SECTION 2 -->
		<script type="text/javascript">
			// Load Dojo's code relating to the Button widget
			dojo.require("dijit.form.Button");
		</script>

		<script>
			function helloCallback(data,ioArgs) {
			  alert(data);
			}       
			function helloError(data, ioArgs) {
			  alert('Error when retrieving data from the server!');
			}
		</script>
	</head>

	<body class="tundra">
		<button dojoType="dijit.form.Button" id="helloButton">
			Hello World!
			<script type="dojo/method" event="onClick">
				dojo.xhrGet({
					url: '<c:url value="response.htm"/>',
					load: helloCallback,
					error: helloError,
					content: {name: dojo.byId('name').value }
				});
			</script>
		</button>
		<form id="myForm" method="POST">
			Please enter your name: <input type="text" name="name">
		</form>
	</body>
</html>
--%>
<%--
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="pageTitle" /></title>

    <script type="text/javascript" src="http://o.aolcdn.com/dojo/1.2.3/dojo/dojo.xd.js" 
	        djConfig="parseOnLoad: true"></script>
    <script type="text/javascript">
        dojo.require("dojo.parser");

        dojo.require("dojo.io.iframe");

		dojo.require("dijit.Toolbar");

		dojo.require("dijit.layout.LayoutContainer");
		dojo.require("dijit.layout.SplitContainer");
		dojo.require("dijit.layout.AccordionContainer");
		dojo.require("dijit.layout.TabContainer");
		dojo.require("dijit.layout.ContentPane");
		
		dojo.require("dijit.form.Button");
    </script>

	<style type="text/css">
		@import "http://o.aolcdn.com/dojo/1.2.3/resources/dojo.css";
		@import "http://o.aolcdn.com/dojo/1.2.3/dijit/themes/tundra/tundra.css";
		@import "<c:url value="/styles/zpdojo.css" />";
	</style>

</head>
<body class="tundra">

	<div dojoType="dijit.layout.LayoutContainer" id="main">

		<!-- toolbar with new mail button, etc. -->
		<div dojoType="dijit.Toolbar" layoutAlign="top" style="height:25px;">
		   Toolbar will go here
		</div>
		
		<div dojoType="dijit.layout.ContentPane" layoutAlign="bottom" 
		     id="footer" align="left">
			<span style="float:right;">DojoMail v1.0 (demo only)</span>

			Progress bar will go here
		</div>
		
		<div dojoType="dijit.layout.SplitContainer"
				orientation="horizontal"
				sizerWidth="5"
				activeSizing="0"
				title="UnTab"
				layoutAlign="client"
			>
				
			<div dojoType="dijit.layout.AccordionContainer" sizeMin="20" sizeShare="20">
				<div dojoType="dijit.layout.AccordionPane" title="Folders">
				    Folders will go here
				</div>
				<div dojoType="dijit.layout.AccordionPane" title="Mine">
				    Yo lo hice
				</div>
				
				<div dojoType="dijit.layout.AccordionPane" title="Address Book">
				    Address Book will go here
				</div>
			</div>
			
			<div dojoType="dijit.layout.SplitContainer"
				id="rightPane"
				orientation="vertical"
				sizerWidth="5"
				activeSizing="0"
				sizeMin="50" sizeShare="85"
			>
				<div dojoType="dijit.layout.TabContainer" 
				    id="tabs" jsId="tabs" layoutAlign="client">
					<div title="OtroTab" id="listPane" dojoType="dijit.layout.ContentPane" sizeMin="20" sizeShare="20">
						Message List will go here
					</div>
							
					<div title="OtroTabMas" id="message" dojoType="dijit.layout.ContentPane" sizeMin="20" sizeShare="80">
						Message will go here
						<iframe
						width="99%"
						height="99%"
						name="REVISION_HISTORY_AREA"
						src="<c:url value="menu.htm" />"
						SCROLLING="yes"
						FRAMEBORDER="no"
						></iframe>
					</div>
		            			<!-- main section with tree, table, and preview -->
					
				</div> <!--  End tab container -->

			</div> <!--  End right hand side split container -->
		</div><!-- End entire-screen split container -->
	</div> <!--  End layout container -->	
</body>
</html>
--%>
<%--
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Layout Container Demo 2</title>
    <style type="text/css">
        @import "http://o.aolcdn.com/dojo/1.1/dijit/themes/tundra/tundra.css";
        @import "http://o.aolcdn.com/dojo/1.1/dojo/resources/dojo.css"
    </style>
    <script type="text/javascript" src="http://o.aolcdn.com/dojo/1.1/dojo/dojo.xd.js"
        djConfig="parseOnLoad: true"></script>
    <style> 
        /* NOTE: for a full screen layout, must set body size equal to the viewport. */
        html, body, #main { height: 100%; width: 100%; margin: 0; padding: 0; }
    </style>
    <script type="text/javascript">
        dojo.require("dojo.parser");
        dojo.require("dijit.layout.ContentPane");
        dojo.require("dijit.layout.BorderContainer");
     </script>
</head>
<body class="tundra">
<div id="main" dojoType="dijit.layout.BorderContainer" design="headline">
   <div dojoType="dijit.layout.ContentPane" region="top" style="background-color:red">
        The Dojo Book
   </div>
   <div dojoType="dijit.layout.ContentPane" region="left"
        style="background-color:lightblue;width: 120px;">
        Table of Contents
    </div>
    <div dojoType="dijit.layout.ContentPane" region="center"
        style="background-color:yellow">
            <blockquote><a href="../node/717">Introduction</a>
                <ol>
                    <li><a href="../node/718">Dojo: What is It?</a></li>
                    <li><a href="../node/719">History</a></li>
                    <li><a href="../node/733">What Dojo Gives You</a></li>
                </ol>
                </blockquote>
    </div>
</div>       
</body>
</html>--%>
<html>
	<head>
		<title>Prueba Oliver</title>
	</head>
	<body>
		Soy una prueba
	</body>
</html>