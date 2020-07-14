<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html public "✰">
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
<head>
		<meta charset="utf-8">

		<title>Adminica | The Professional Admin Theme</title>

  	<!-- iPhone, iPad and Android specific settings -->

		<meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1;">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />

		<link href="assets/adminica/images/interface/iOS_icon.png" rel="apple-touch-icon">

	<!-- Styles -->

		<link rel="stylesheet" href="assets/adminica/styles/adminica/reset.css">
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,700">
		<link rel="stylesheet" href="assets/adminica/styles/plugins/all/plugins.css">
		<link rel="stylesheet" href="assets/adminica/styles/adminica/all.css">


		<link rel="stylesheet" href="assets/adminica/styles/themes/nav_side.css" >
		<link rel="stylesheet" href="assets/adminica/styles/themes/skin_light.css" >
		<link rel="stylesheet" href="assets/adminica/styles/themes/theme_orange.css" >
		<link rel="stylesheet" href="assets/adminica/styles/themes/bg_white_wood.css" >

		<link rel="stylesheet" href="assets/adminica/styles/adminica/colours.css"> 
		<script src="assets/adminica/scripts/plugins-min.js"></script>


		<script src="assets/adminica/scripts/adminica/adminica_all-min.js"></script>
</head>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="2">
			<div id="sidebar" class="sidebar pjax_links">
				<div class="cog">+</div>
				<a href="index.php" class="logo"><span>Adminica</span></a>

				<div class="user_box dark_box clearfix">
					<img src="assets/adminica/images/interface/profile.jpg" width="55" alt="Profile Pic" />
					<h2>Administrator</h2>
					<h3><a href="#">John Smith</a></h3>
					<ul>
						<li><a href="#">settings</a><span class="divider">|</span></li>
						<li><a href="login_slide.php" class="dialog_button" data-dialog="dialog_logout">Logout</a></li>
					</ul>
				</div><!-- #user_box -->

				<ul class="side_accordion" id="nav_side"> <!-- add class 'open_multiple' to change to from accordion to toggles -->
					<li><a href="#" class="dialog_button" data-dialog="dialog_welcome"><img src="assets/adminica/images/icons/small/grey/speech_bubble.png"/>Adminica</a></li>
					<li><a href="empty.php" class="pjax"><img src="assets/adminica/images/icons/small/grey/document.png"/>Empty</a></li>

					<li><a href="#"><img src="assets/adminica/images/icons/small/grey/mail.png"/>Mailbox<div class="alert badge alert_red">5</div></a>
						<ul class="drawer">
							<li><a href="#">Inbox</a></li>
							<li><a href="#">Sent Items</a></li>
							<li><a href="#">Drafts<div class="alert badge alert_grey">2</div></a></li>
							<li><a href="#">Trash<div class="alert badge alert_grey">3</div></a></li>
						</ul>
					</li>
					<li><a href="#"><img src="assets/adminica/images/icons/small/grey/documents.png"/>Documents<div class="alert badge alert_black">2</div></a>
						<ul class="drawer">
							<li><a href="files.php" class="pjax">View All</a></li>
							<li><a href="files.php" class="pjax">Upload/Download<div class="alert badge alert_grey">2</div></a></li>
						</ul>
					</li>
					<li><a href="#"><img src="assets/adminica/images/icons/small/grey/users.png"/>Members</a>
						<ul class="drawer">
							<li><a href="contacts.php" class="pjax">Add New</a></li>
							<li><a href="contacts.php" class="pjax">Edit/Delete</a></li>
							<li><a href="contacts.php" class="pjax">Search Profiles</a></li>
						</ul>
					</li>
					<li><a href="http://www.google.com"><img src="assets/adminica/images/icons/small/grey/graph.png"/>Statistics</a></li>
					<li><a href="#"><img src="assets/adminica/images/icons/small/grey/cog_2.png"/>Settings</a>
						<ul class="drawer">
							<li><a href="#">Account</a></li>
							<li><a href="#">System</a></li>
						</ul>
					</li>
				</ul>

				<div id="search_side" class="dark_box"><form><input class="" type="text" placeholder="Search Adminica..."></form></div>

				<ul id="side_links" class="side_links" style="margin-bottom:0;">
					<li><a href="#">Documentation</a>
					<li><a href="#">Support Forum</a></li>
					<li><a href="#">Contact</a></li>
					<li><a href="#">Subscribe</a></li>
				</ul>
			</div><!-- #sidebar -->

			<div id="main_container" class="main_container container_16 clearfix">
				<div id="nav_top" class="dropdown_menu clearfix round_top">
					<ul class="clearfix">
						<li class="icon_only"><a href="index.php" class="pjax"><img src="assets/adminica/images/icons/small/grey/laptop.png"/><span class="display_none">Home</span></a></li>

						<li><a href="#"><img src="assets/adminica/images/icons/small/grey/frames.png"/><span>Layout</span></a>
							<ul class="open_multiple">
								<li><a href="layout.php" class="pjax"><span>Grid System</span></a></li>
								<li><a href="text.php" class="pjax"><span>Text & Typography</span></a></li>
								<li><a class="hide_mobile" href="#"><span>Layout Width</span></a>
									<ul class="drawer ">
										<li><a class="hide_mobile" href="assets/adminica/styles/themes/layout_switcher.php?style=layout_fixed.css"><span>Fixed</span></a></li>
										<li><a class="hide_mobile" href="assets/adminica/styles/themes/layout_switcher.php?style=switcher.css"><span>Fluid</span></a></li>
									</ul>
								</li>
								<li><a class="hide_mobile" href="#"><span>Layout Position</span></a>
									<ul class="drawer">
										<li><a class="hide_mobile" href="assets/adminica/styles/themes/nav_switcher.php?style=switcher.css"><span>Side</span></a></li>
										<li><a class="hide_mobile" href="assets/adminica/styles/themes/nav_switcher.php?style=nav_top.css"><span>Top</span></a></li>
										<li><a class="hide_mobile" href="assets/adminica/styles/themes/nav_switcher.php?style=nav_slideout.css"><span>Slide</span></a></li>
										<li><a class="hide_mobile" href="assets/adminica/styles/themes/nav_switcher.php?style=nav_stacks.css"><span>Stacks</span></a></li>
									</ul>
								</li>
								<li><a class="hide_mobile" href="#"><span>Isolated Layout</span><div class="alert badge">&#9733</div></a>
									<ul class="drawer">
										<li><a href="isolated_tabs.php" class="pjax"><span>Isolated Tabs</span></a></li>
										<li><a href="isolated_wizard.php" class="pjax"><span>Isolated Wizard</span></a></li>
										<li><a href="isolated_graphs.php" class="pjax"><span>Isolated Graphs</span></a></li>
									</ul>
								</li>
							</ul>
						</li>
						<li><a href="#"><img src="assets/adminica/images/icons/small/grey/coverflow.png"/><span>Widgets</span></a>
							<ul>
								<li><a href="tabs.php" class="pjax"><span>Tab Boxes</span></a></li>
								<li><a href="accordions.php" class="pjax"><span>Accordions Boxes</span></a></li>
								<li><a href="wizard.php" class="pjax"><span>Step by Step Wizard</span><div class="alert badge">&#9733</div></a></li>
								<li><a href="code.php"><span>Code View</span></a></li>
								<li><a href="editor.php" class="pjax"><span>WYSIWYG Editor</span></a></li>
								<li><a href="dialog.php" class="pjax"><span>Dialog Windows</span><div class="alert badge">&#9733</div></a></li>
							</ul>
						</li>
						<li><a href="#"><img src="assets/adminica/images/icons/small/grey/create_write.png"/><span>Forms</span></a>
							<ul>
								<li><a href="form_fields.php" class="pjax"><span>Input Fields</span><div class="alert badge">&#9733</div></a></li>
								<li><a href="buttons.php" class="pjax"><span>Buttons</span></a></li>
								<li><a href="form_validation.php" class="pjax"><span>Validation</span></a></li>
								<li><a href="#"><span>Example Forms</span><div class="alert badge">&#9733</div></a>
									<ul class="drawer">
										<li><a href="form_contact.php" class="pjax"><span>Contact</span></a></li>
										<li><a href="form_grid.php" class="pjax"><span>Grid</span></a></li>
									</ul>
								</li>
							</ul>
						</li>
						<li><a href="gallery.php" class="pjax"><img src="assets/adminica/images/icons/small/grey/assets/adminica/images.png"/><span>Gallery</span></a></li>
						<li><a href="#"><img src="assets/adminica/images/icons/small/grey/blocks_assets/adminica/images.png"/><span>Tables</span></a>
							<ul>
								<li><a href="tables.php" class="pjax"><span>DataTables</span></a></li>
								<li><a href="tables_static.php" class="pjax"><span>Regular Tables</span></a></li>
							</ul>
						</li>
						<li><a href="#"><img src="assets/adminica/images/icons/small/grey/file_cabinet.png"/><span>Org</span><div class="alert badge">&#9733</div></a>
							<ul>
								<li><a href="files.php" class="pjax"><img src="assets/adminica/images/icons/small/grey/folder.png"/><span>Files</span></a></li>
								<li><a href="contacts.php" class="pjax"><img src="assets/adminica/images/icons/small/grey/users.png"/><span>Contacts</span><div class="alert badge">&#9733</div></a></li>
							</ul>
						</li>
						<li><a href="calendar.php" class="pjax"><img src="assets/adminica/images/icons/small/grey/month_calendar.png"/><span>Cal</span></a></li>
						<li><a href="charts.php" class="pjax"><img src="assets/adminica/images/icons/small/grey/graph.png"/><span>Charts</span></a>
							<ul class="dropdown_right">
								<li><a href="charts.php" class="pjax"><span>Large Charts</span></a></li>
								<li><a href="graphs.php" class="pjax"><span>Info Graphs</span></a><div class="alert badge">&#9733</div></li>
							</ul></li>
						<li class="icon_only"><a href="#"><img src="assets/adminica/images/icons/small/grey/locked_2.png"/><span class="display_none">Login</span><div class="alert badge">&#9733</div></a>
							<ul class="dropdown_right">
								<li><a href="login_button.php" class="pjax"><span>Regular Login</span></a></li>
								<li><a href="#" class="dialog_button" data-dialog="dialog_logout"><span>Slide Login</span><div class="alert badge">&#9733</div></a></li>
							</ul>
						</li>
					</ul>

					<div id="mobile_nav">
						<div class="main"></div>
						<div class="side"></div>
					</div>
				</div><!-- #nav_top -->
			
				<div class="flat_area grid_16">
					<h2>Empty Page
					<small>( A good starting point )</small>
					<div class="holder">
						<label>Dynamic Loading:</label>
						<div class="jqui_checkbox" id="pjax_switch">
							<input type="radio" name="dynamic_switch" id="dynamic_on"/><label for="dynamic_on">On</label>
							<input type="radio" name="dynamic_switch" id="dynamic_off" checked="checked"/><label for="dynamic_off">Off</label>
						</div>						
					</div>
					</h2>
					<p>At vero eos et accusamus et iusto odio <a href="#">dignissimos ducimus qui blanditiis</a> praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et <strong>expedita distinctio</strong>. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
				</div>
			
				<div class="box grid_16">
					<h2 class="box_head">Empty Box</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="block">
						<div class="section">
						</div>
					</div>
				</div>
			</div> <!-- #main_container -->
		</div> <!-- wrapper -->
		
		<div class="display_none">
			<div id="dialog_register" class="dialog_content no_dialog_titlebar wide" title="Register for Adminica">
				<div class="block">
					<div class="section">
						<h2>Registration Form</h2>
					</div>
		
					<div class="columns clearfix">
						<div class="col_50">
							<fieldset class="label_top top">
								<label for="text_field_inline">Username<span>Between 5 and 20 characters</span></label>
								<div>
									<input type="text">
								</div>
							</fieldset>
						</div>
			
						<div class="col_50">
							<fieldset class="label_top top right">
								<label for="text_field_inline">Username again…<span>so we know you're human</span></label>
								<div>
									<input type="text">
								</div>
							</fieldset>
						</div>
					</div>

					<div class="columns clearfix">
						<div class="col_50">
							<fieldset class="label_top">
								<label for="text_field_inline">Password<span>Between 5 and 20 characters</span></label>
								<div>
									<input type="text">
								</div>
							</fieldset>
						</div>
						<div class="col_50">
							<fieldset class="label_top right">
								<label for="text_field_inline">Repeat Password again…</label>
								<div>
									<input type="text">
								</div>
							</fieldset>
						</div>
					</div>

					<fieldset class="label_side bottom">
						<label>Permission</label>
						<div class="uniform inline clearfix">
							<label for="agree_1"><input type="checkbox" name="agree_1" value="yes" id="agree_1"/>I agree with the terms and conditions</label>
						</div>
					</fieldset>

					<div class="button_bar clearfix">
						<button class="dark blue no_margin_bottom link_button" data-link="index.php">
							<div class="ui-icon ui-icon-check"></div>
							<span>Register</span>
						</button>
						<button class="light send_right close_dialog">
							<div class="ui-icon ui-icon-closethick"></div>
							<span>Cancel</span>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="display_none">
			<div id="dialog_form" class="dialog_content" title="Dialog with Form fields">
				<div class="block">
					<div class="section">
						<div class="alert dismissible alert_black">
							<img width="24" height="24" src="assets/adminica/images/icons/small/white/alert_2.png">
							<strong>All the form fields</strong> can be just as easily used in a dialog.
						</div>
					</div>
		
					<fieldset class="label_side top">
						<label>Text Field<span>Label placed beside the Input</span></label>
						<div>
							<input type="text">
						</div>
					</fieldset>

					<fieldset class="label_side">
						<label>Textarea<span>Regular Textarea</span></label>
						<div class="clearfix">
							<textarea class="autogrow"></textarea>
						</div>
					</fieldset>

					<fieldset class="label_side">
					<label>Bounce Slider<span>Slide to Unlock</span></label>
						<div style="padding-top:25px;">
							<div class="slider_unlock" title="Slide to Close"></div>
							<button class="close_dialog display_none"></button>
						</div>
					</fieldset>

					<div class="button_bar clearfix">
						<button class="dark green close_dialog">
							<div class="ui-icon ui-icon-check"></div>
							<span>Submit</span>
						</button>
						<button class="dark red close_dialog">
							<div class="ui-icon ui-icon-closethick"></div>
							<span>Cancel</span>
						</button>
					</div>
				</div>
			</div>
		</div>		

		<div class="display_none">						
			<div id="dialog_delete" class="dialog_content narrow no_dialog_titlebar" title="Delete Confirmation">
				<div class="block">
					<div class="section">
						<h1>Delete File</h1>
						<div class="dashed_line"></div>	
						<p>Please confirm that you want to delete this file.</p>
					</div>
					<div class="button_bar clearfix">
						<button class="delete_confirm dark red no_margin_bottom close_dialog">
							<div class="ui-icon ui-icon-check"></div>
							<span>Delete</span>
						</button>
						<button class="light send_right close_dialog">
							<div class="ui-icon ui-icon-closethick"></div>
							<span>Cancel</span>
						</button>
					</div>
				</div>
			</div>
		</div> 		

		<div class="display_none">
			<div id="dialog_welcome" class="dialog_content no_dialog_titlebar wide" title="Welcome to Adminica">
				<div class="block lines">
					<div class="columns clearfix">
						<div class="col_50 no_border_top">
							<div class="section">
								<h1>Adminica</h1>
								<p><strong>Adminica</strong> is a <strong>cleanly coded</strong>, <strong>beautifully styled</strong>, easily <strong>customisable</strong>, <strong>cross-browser</strong> compatible <strong>Web Application Interface</strong>.</p>

								<p><strong>Adminica</strong> is packed full of features, allowing you<strong> unlimited combinations</strong> of layouts, controls and assets/adminica/styles to ensure you have a <strong>trully unique app</strong>. </p>

								<p><strong>Adminica</strong>  can <strong>scale itself automatically</strong> to fit whatever screen resolution the user has. The interface<strong> works perfectly all the way down to iPhone size</strong></p>
							</div>
						</div>

						<div class="col_50 no_border_top no_border_right">
							<div class="section">
								<h2>Features</h2>
							</div>
							<ul class="flat large text">
								<li><a href="assets/adminica/styles/themes/skin_switcher.php?style=theme_light.css">Light</a> and <a href="assets/adminica/styles/themes/skin_switcher.php?style=switcher.css">Dark</a> Theme</li>
								<li><a href="assets/adminica/styles/themes/layout_switcher.php?style=layout_fixed.css">Fixed</a> and <a href="assets/adminica/styles/themes/layout_switcher.php?style=switcher.css">Fluid</a> width layout</li>
								<li><a href="assets/adminica/styles/themes/nav_switcher.php?style=switcher.css">Sidebar</a>, <a href="assets/adminica/styles/themes/nav_switcher.php?style=nav_top.css">Full Width</a>, <a href="assets/adminica/styles/themes/nav_switcher.php?style=nav_slideout.css">Slide Menu</a> or <a href="assets/adminica/styles/themes/nav_switcher.php?style=nav_slideout.css">Stack Menu</a>.</li>
								<li class="theme_colour">
									<a class="black" href="assets/adminica/styles/themes/theme_switcher.php?style=switcher.css">
										<span>Black</span></a>
									<a class="blue" href="assets/adminica/styles/themes/theme_switcher.php?style=theme_blue.css">
										<span>Blue</span></a>
									<a class="navy" href="assets/adminica/styles/themes/theme_switcher.php?style=theme_navy.css">
										<span>Navy</span></a>
									<a class="red" href="assets/adminica/styles/themes/theme_switcher.php?style=theme_red.css">
										<span>Red</span></a>
									<a class="green" href="assets/adminica/styles/themes/theme_switcher.php?style=theme_green.css">
										<span>Green</span></a>
									<a class="magenta" href="assets/adminica/styles/themes/theme_switcher.php?style=theme_magenta.css">
										<span>Magenta</span></a>
									<a class="orange" href="assets/adminica/styles/themes/theme_switcher.php?style=theme_brown.css">
										<span>Brown</span></a>
								</li>
							</ul>
							<fieldset class="label_side label_small no_lines" style="border-top:0;">
								<label>All Pages</label>
								<div class="clearfix">
									<div id="pagesSelect"></div>
								</div>
							</fieldset>
						</div>
					</div>

				<div class="columns clearfix no_lines">
					<div class="col_33">
						<fieldset class="no_label">
							<div class="clearfix">
								<button class="full_width light">
									<img src="assets/adminica/images/icons/small/grey/book.png">
									<span>Documentation</span>
								</button>
							</div>
						</fieldset>
					</div>
					<div class="col_33">
						<fieldset class="no_label">
							<div class="clearfix">
								<button class="full_width light">
									<img src="assets/adminica/images/icons/small/grey/help.png">
									<span>Support Forum</span>
								</button>
							</div>
						</fieldset>
					</div>
					<div class="col_33 no_border_right">
						<fieldset class="no_label">
							<div class="clearfix">
								<button class="full_width light link_button" data-link="http://themeforest.net/user/Tricycle">
									<img src="assets/adminica/images/icons/small/grey/speech_bubble_2.png">
									<span>Contact</span>
								</button>
							</div>
						</fieldset>
					</div>
				</div>

				<div class="columns clearfix no_lines">
					<div class="col_33">
						<fieldset class="label_top">
							<label for="text_field_inline">Themeforest Username</label>
							<div>
								<input type="text">
							</div>
						</fieldset>
					</div>
					<div class="col_33">

						<fieldset class="label_top">
							<label for="text_field_inline">Email Address<span>required</span></label>
							<div>
								<input type="email" >
							</div>
						</fieldset>
					</div>
					<div class="col_33 no_border_right">
						<fieldset class="label_top empty_label">
							<div class="clearfix">
								<button class="full_width" ><span>Subscribe for Updates</span></button>
							</div>
						</fieldset>
					</div>
				</div>

				<div class="button_bar clearfix" type="submit" value="Subscribe" name="subscribe" id="mc-embedded-subscribe">
					<button class="dark blue close_dialog wide">
						<div class="ui-icon ui-icon-check"></div>
						<span>Submit</span>
					</button>
					<button class="light send_right close_dialog wide">
						<div class="ui-icon ui-icon-closethick"></div>
						<span>Close</span>
					</button>
				</div>
			</div>
		</div>
	</div> <!-- #pjax -->		

	<div class="display_none">						
		<div id="dialog_logout" class="dialog_content narrow" title="Logout">
			<div class="block">
				<div class="section">
					<h1>Thank you</h1>
					<div class="dashed_line"></div>	
					<p>We will now log you out of Adminica in a 10 seconds...</p>
				</div>
				<div class="button_bar clearfix">
					<button class="dark blue no_margin_bottom link_button" data-link="login_slide.php">
						<div class="ui-icon ui-icon-check"></div>
						<span>Ok</span>
					</button>
					<button class="light send_right close_dialog">
						<div class="ui-icon ui-icon-closethick"></div>
						<span>Cancel</span>
					</button>
				</div>
			</div>
		</div>
	</div> 

	<div id="loading_overlay">
		<div class="loading_message round_bottom">
			<img src="assets/adminica/images/interface/loading.gif" alt="loading" />
		</div>
	</div>

</body>
</html>