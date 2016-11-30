<?php
session_start();
ob_start();
function printNavBar(){
  //this function to print navigation bar to web pages 
  echo'<nav id="top">
  <div class="container">
  <div id="top-links" class="nav pull-left">  
  <ul class="social list-inline">
                    <li ><a id="fb" href="#"><i class="fa fa-lg fa-facebook"></i></a><span class="hidden-xs hidden-sm hidden-md"></span></li>
                    <li ><a id="tw" href="#"><i class="fa fa-lg fa-twitter"></i></a><span class="hidden-xs hidden-sm hidden-md"></span></li>
                    <li><a id="go" href="#"><i class="fa fa-lg fa-instagram" ></i></a><span class="hidden-xs hidden-sm hidden-md"></span></li>
                    
                </ul></div>
  <div id="top-links" class="nav pull-right">
      <ul class="list-inline">
        <li><a href="#"><i class="fa fa-phone"></i></a> <span class="hidden-xs hidden-sm hidden-md">123456789</span></li>
        <li class="dropdown"><a id="ua" href="#" title="My Account" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <span class="hidden-xs hidden-sm hidden-md">My Account</span> <span class="caret"></span></a>
          '.printAccountMenu().'
        </li>
        <li><a href="/mrrobot/profile.php?id=wishlist" id="wishlist-total" title="Wish List (0)"><i class="fa fa-heart"></i> <span class="hidden-xs hidden-sm hidden-md">Wish List (0)</span></a></li>
        <li><a href="#" title="Shopping Cart"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Shopping Cart</span></a></li>
        <li><a href="#" title="Checkout"><i class="fa fa-share"></i> <span class="hidden-xs hidden-sm hidden-md">Checkout</span></a></li>
      </ul>
    </div>
  </nav>';
}
function printfooter(){
echo'
<footer>
<div class="container">
<div class="row">
<div class="col-lg-4">
<h5>Information</h5>
 <ul class="list-unstyled">
     <li><a href="#">About Us</a></li>
    <li><a href="#">Delivery Information</a></li>
    <li><a href="#">Privacy Policy</a></li>
  <li><a href="#">Terms &amp; Conditions</a></li>
 </ul>
</div>
<div class="col-md-4">
<h5>Custumer Service</h5>
 <ul class="list-unstyled">
   <li><a href="#">Contact Us</a></li>
   <li><a href="#">Returns</a></li>
   <li><a href="#">Site Map</a></li>
 </ul>
</div>
<div class="col-md-4">
<h5>My account</h5>
<ul class="list-unstyled">
          <li><a href="/mrrobot/profile.php">My Account</a></li>
          <li><a href="/mrrobot/profile.php?id=history">Order History</a></li>
          <li><a href="/mrrobot/profile.php?id=wishlist">Wish List</a></li>
          <li><a href="#">Newsletter</a></li>
        </ul>

</div>
<hr>
<div id="mrrobot-logo">
<p>Powered By : <a href="#"><img class="logo" src="images//mrrobotLogo.svg"/></a><br></p>
</div>
<span style="text-align=center;"> MR ROBOT © 2016</span>
</div>
</footer>
';
}
function printItem(){
  echo '<div class="product-thumb transition">
      <div class="image"><a href="#"><img id="zoom_02" src="images/items/macbook_1-200x200.jpg" alt="MacBook" title="MacBook" class="img-responsive" data-zoom-image="images/items/macbook_air.png"></a></div>
      <div class="caption">
        <h4><a href="#">MacBook</a></h4>
        <p>
  
    Intel Core 2 Duo processor
  
    Powered by an Intel Core 2 Duo processor at speeds up to 2.1..</p>
                        <p class="price">
                    $602.00                              <span class="price-tax">Store:Platinum</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" ><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" ><i class="fa fa-heart"></i></button>
        <button type="button" ><i class="fa fa-exchange"></i></button>
      </div>
     </div>
   ';
}
// print cart content when press at cart button 
function viewcart(){
  if(true){
    echo'  <li>
        <p class="text-center" id="cart_info">Your shopping cart is empty!</p>
      </li>';}
      else
  
  echo'<li>
      <table class="table table-striped">
                <tr>
          <td class="text-center">            <a href="#"><img style="width:42px; height:43px;" src="images/items/macbook_1-200x200.jpg" alt="macbook" title="macbook" class="img-thumbnail" /></a>
            </td>
          <td class="text-left"><a href="#">Macbook</a>
                        </td>
          <td class="text-right">1</td>
          <td class="text-right">660 JD</td>
          <td class="text-center"><button type="button" onclick="#" title="Remove" class="btn btn-danger btn-xs"><i class="fa fa-times"></i></button></td>
        </tr>
                      </table>
    </li>
    <li>
      <div>
        <table class="table table-bordered">
                    <tr>
            <td class="text-right"><strong>Sub-Total</strong></td>
            <td class="text-right">660 JD</td>
          </tr>
            
             
                    <tr>
            <td class="text-right"><strong>Total</strong></td>
            <td class="text-right">660 JD</td>
          </tr>
                  </table>
        <p class="text-right"><a href="/mrrobot/cart.php"><strong><i class="fa fa-shopping-cart"></i> View Cart</strong></a>&nbsp;&nbsp;&nbsp;<a href="#"><strong><i class="fa fa-share"></i> Checkout</strong></a></p>
      </div>
    </li>';
}
function printAccountMenu(){
  if(!isset($_COOKIE['platinumMallUser'])){
    return'
    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="/Mrrobot/register.php">Register</a></li>
            <li><a href="/mrrobot/login.php">Login</a></li>
                      </ul>
        </li>';
  }
  else{
    return'
    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="/Mrrobot/profile.php">Account</a></li>
            <li><a href="/mrrobot/profile.php?id=orders">Orders</a></li>
            <li><a href="/mrrobot/profile.php?id=history">Orders History</a></li>
            <li><a href="/mrrobot/profile.php?id=setting">Settings</a></li>
            <li><a href="/mrrobot/profilssss">Log Out</a></li>
                      </ul>
        </li>';

  }
}

?>