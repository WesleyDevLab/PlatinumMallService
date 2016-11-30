<?php
//require("functions/connect.ph");
require("functions/engine.php");
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/navbar.css">
<link rel="stylesheet" type="text/css" href="css/footer.css">
<link rel="stylesheet" type="text/css" href="css/register.css">
<script src="https://code.jquery.com/jquery-2.2.4.js"
			  integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
			  crossorigin="anonymous"></script>
<meta name="viewport" content="width=device-width ,inital-scale=1.0" />
<link href="images/cart.png" rel="icon" />
<link href="javascript/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="javascript/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="//fonts.googleapis.com/css?family=Open+Sans:400,400i,300,700" rel="stylesheet" type="text/css">
<link href="javascript/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<script src='https://www.google.com/recaptcha/api.js'></script>
<script src="javascript/navbar.js" type="text/javascript"></script>
<script src="javascript/register.js" type="text/javascript"></script>

	<title>Registration</title>
</head>
<body>
<?php printNavBar(); ?>
<div class="container">
<div class="scroll_to_top" style="display: block;">
        <span id="up_arrow" class="glyphicon glyphicon-menu-up">
      </span></div>
<ul class="breadcrumb">
        <li><a href="/mrrobot"><i class="fa fa-home"></i></a></li>
        <li><a href="#">Register</a></li>
      </ul>

<div class="row">         
       <div id="content" class="col-sm-9">      <h1>Register Account</h1>
      <p>If you already have an account with us, please login at the <a href="/mrrobot/login.php">login page</a>.</p>
      <div class="form-horizontal">
        <fieldset id="account">
          <legend>Your Personal Details</legend>
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-firstname">First Name</label>
            <div class="col-sm-10">
              <input type="text" name="firstname" value="" placeholder="First Name" id="input-firstname" class="form-control" />
                          </div>
          </div>
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-lastname">Last Name</label>
            <div class="col-sm-10">
              <input type="text" name="lastname" value="" placeholder="Last Name" id="input-lastname" class="form-control" />
                          </div>
          </div>
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-email">E-Mail</label>
            <div class="col-sm-10">
              <input type="email" name="email" value="" placeholder="E-Mail" id="input-email" class="form-control" />
                          </div>
          </div>
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-telephone">Telephone</label>
            <div class="col-sm-10">
              <input type="tel" name="telephone" value="" placeholder="Telephone" id="input-telephone" class="form-control" />
                          </div>
          </div>
      
                  </fieldset>
        <fieldset id="address">
          <legend>Your Address</legend>
  
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-address-1">Address 1</label>
            <div class="col-sm-10">
              <input type="text" name="address_1" value="" placeholder="Address 1" id="input-address-1" class="form-control" />
                          </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label" for="input-address-2">Address 2</label>
            <div class="col-sm-10">
              <input type="text" name="address_2" value="" placeholder="Address 2" id="input-address-2" class="form-control" />
            </div>
          </div>
          
         
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-city">City</label>
            <div class="col-sm-10">
              <select name="city_id" id="input-city" class="form-control">
                      <option value="0"> --- Please Select --- </option>
                      <option value="Ajloon">Ajloon</option>
                      <option value="Amman">Amman</option>
                      <option value="Aqaba">Aqaba</option>
                      <option value="Balqa">Balqa</option>
                      <option value="Irbid">Irbid</option>
                      <option value="Jerash">Jerash</option>
                      <option value="Karak">Karak</option>
                      <option value="Ma'An">Ma'An</option>
                      <option value="Madaba">Madaba</option>
                      <option value="Mafraq">Mafraq</option>
                      <option value="Tafileh">Tafileh</option>
                      <option value="Zarqa">Zarqa</option>
              </select>
            </div>
          </div>
         
        </fieldset>
        <fieldset>
          <legend>Your Password</legend>
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-password">Password</label>
            <div class="col-sm-10">
              <input type="password" name="password" value="" placeholder="Password" id="input-password" class="form-control" />
                          </div>
          </div>
          <div class="form-group required">
            <label class="col-sm-2 control-label" for="input-confirm">Password Confirm</label>
            <div class="col-sm-10">
              <input type="password" name="confirm" value="" placeholder="Password Confirm" id="input-confirm" class="form-control" />
                          </div>
          </div>
        </fieldset>
        <fieldset>
          <legend>Newsletter</legend>
          <div class="form-group">
            <label class="col-sm-2 control-label">Subscribe</label>
            <div class="col-sm-5">
              <label class="radio-inline">
                <input  type="radio" name="newsletter" value="1" />
                Yes
              </label>
              <label class="radio-inline">
                <input  id="subscribe" type="radio" name="newsletter" value="0" checked="checked" />
                No
              </label>
             </div>
            <div class="col-sm-5">
             
             <div class="g-recaptcha" data-sitekey="6LeVSQsUAAAAABthF5mD0cPXm51AYiN5AINq5Gva"></div>


            </div>
          </div>
        </fieldset>
            <div class="buttons">
          <div class="pull-right">I have read and agree to the <a href="/Mrrobot/privacypolicy.htm" target="_blank" class="agree"><b>Privacy Policy</b></a>
            <input id="agree" type="checkbox" name="agree" value="1" />
                        &nbsp;
            <input id="add_new_user" type="submit" value="Update" class="btn btn-primary" />
          </div>
        </div>
              </div>
      </div>
</div>
</div>
<?php printfooter();?>
</body>
</html>