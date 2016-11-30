<?php
if(isset($_SESSION['user_id']))
   header("location:cms/");
?>
<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/login.css">
   <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
   <link rel="stylesheet" type="text/css" href="css/animate_css.css">
   <script type="text/javascript" src="javascript/jquery.js"></script>
   <script type="text/javascript" src="javascript/login.js"></script>

	<title>Login</title>
</head>
   <body>

   	<div class="background_image"></div>

   	<div id="login_box">
         <div id="top_bar"></div>
        <span><b>Login Below</b></span>
        <form action="login.php" method="POST">
            <table>
                <tr>
                    <td>
                        <i style="font-size:20px" id="username_icon" class="fa fa-user"></i>
                        <input type="text" id="username" name="username" size="50px" placeholder="                  USERNAME" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <i id="password_icon" class="fa fa-lock" style="font-size:24px"></i>
                        <input type="password" id="password" name="password" placeholder="                  PASSWORD" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <button type="submit" id="sb" name="sb"><i class="fa fa-arrow-right" style="font-size:20px;color:white;"></i></button>
                     </td>
                </tr>
            </table>
        </form>
        <div id="link"><a href="">Forgot Password ?</a></div>
        <div id="bottom_bar"></div>
   	</div>

   </body>
</html>