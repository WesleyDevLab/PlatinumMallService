<!DOCTYPE html>
<?php
include('../functions/engine.php');
checkLoggedIn();
?>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="../css/main.css"/>
	<link type="text/css" rel="stylesheet" href="../css/nprogress.css"/>
	<link rel="stylesheet" type="text/css" href="../css/animate_css.css">
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
	<script type="text/javascript" src="../javascript/nprogress.js"></script>
	<script type="text/javascript" src="../javascript/jquery.js"></script>
	<script type="text/javascript" src="../javascript/setter.js"></script>
	<title>CMS</title>
</head>

<body>
	<div id="background_shadow"></div>
	<div class="wrapper">
		
		<div id="header">
			<div id="header_logo">
				<a href="index.php">
					<img id="img_logo" src="../images/logo.png">
					<img id="logo_word" src="../images/latinum.png">
				</a>
			</div>
			
		<div id="header_button">
			<i id="users_button_header" class="fa fa-group"></i>
			<p>Users</p>
			</div>
			
			<div id="header_button">
			<i id="category" class="fa fa-cubes"></i>
			<p>Categories</p>
			</div>
			
			
			<div id="header_logout">
				<img title="Logout" src="../images/logout.png"/>
			</div>
		</div>

		<div id="navigator">
			<div id="profile">
				<?php
				echo "Hello , ".getAdminFirstName();
				?>
			</div>
			
			<div id="nav_button"  onclick="getPage('Images_adder.php')" >
				<i id="camera" class="fa fa-camera" style="font-size:18px"></i>&nbsp;&nbsp;&nbsp;&nbsp;
				Images Slider
			</div>
			<div id="nav_button2"  onclick="getPage('add_topic.php')" >
				<i class="fa fa-pencil-square" style="font-size:18px"></i>&nbsp;&nbsp;&nbsp;&nbsp;
				Add Topic
			</div>

			<div id="nav_button3" onclick="getPage('add_item.php');" >
				<i id="additem_icon" class="fa fa-cart-plus" style="font-size:18px"></i>&nbsp;&nbsp;&nbsp;&nbsp;
				Add Item
			</div>
			<div id="nav_button4" onclick="getPage('request.php')" >
				<i class="fa fa-mail-forward" style="font-size:18px"></i>&nbsp;&nbsp;&nbsp;&nbsp;
					<div id="req">Requests</div>
					<div class="animated rubberBand" id="request_number">
					 	<p>5</p>
					</div>
			</div>

			<div id="nav_button5" onclick="getPage('log.php')" >
				<i class="fa fa-bars" style="font-size:18px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;
					Log
			</div>
		</div>

		<div id="content">
			Hello World !
			<?php
				
			?>
		</div>

		<div id="users_window">
			<i id="exit_button_users_window" class="fa fa-remove" ></i>

			<div id="users_window_button">
				<i class="fa fa-user-plus"></i>
				<p>Add user</p>
			</div>
			<hr/>
			<!-- user window that contains adding new users-->
			<div id="user_window_content">
			</div>

		</div>
		
		<div id="category_window">
			<i id="close_categories" class="fa fa-close"></i>
			<div id="top_div">
      				 <center> <div id="top_bar"></div></center>
           		 <div id="title">
                Add Category
           		 </div>
        </div>

        	<div id="middle_div">

			
				<p id="top_content">Add New Categories and Mange them</p>
				<form class="pure-form">
             		<input id="category_input" class="pure-input-1" type="text" placeholder="type something here">
             		<button id="category_add_button" class="button-secondary pure-button">Add Category</button>
				</form>

				<div id="error_category_name">Sorry,This category is already exists.</div>
			
			</div>

			<div id="bottom_div_cat">
            <?php
            	
            ?>
        </div>

		</div>



	</div>
</body>

</html>

