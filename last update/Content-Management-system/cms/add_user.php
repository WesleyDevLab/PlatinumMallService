<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
<script type="text/javascript" src="../javascript/jquery.js"></script>
<script>
$(document).ready(function(){
     var container=$("#user_setup");
     var index=0;
     var forms=[$("#0"),$("#1"),$("#2")];

     container.html(forms[index].fadeIn());

     $("#next").click(function(){
        index==2?$("#next").fadeOut():index++;
        index>0?$("#prev").fadeIn():$("#prev").fadeOut();;
        forms[index-1].fadeOut();
        container.html(forms[index].fadeIn());
     });

     $("#prev").click(function(){
        index==0?$("#prev").fadeOut():index--;
        index<2?$("#next").fadeIn():index--;
        forms[index+1].fadeOut();
        container.html(forms[index].fadeIn());
     });

});
   
</script>

	<div id="user_setup">
        <!-- -->
		<form style="display:none" id="0" class="pure-form">
    	  <fieldset>
        <legend><b>Fill up the fields</b></legend>

        First Name:<input type="text" placeholder="First Name">&nbsp;&nbsp;
        Last Name:<input type="text" placeholder="Last Name">
        <br><br>
        <label for="state">Type</label>
        <select id="state">
            <option>Super user</option>
            <option>Normal user</option>
        </select>
        <br><br>
        E-mail:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="email" placeholder="Email">
         &nbsp;&nbsp;
         <label for="state">Gender</label>
        <select id="state">
            <option>Male</option>
            <option>Female</option>
        </select>
        <br><br>
        Password:&nbsp;&nbsp;<input type="password" placeholder="Password"><br><br>
        re-password:&nbsp;&nbsp;<input type="password" size="17" placeholder="re-password">

    

        <button type="submit" class="pure-button pure-button-primary">Sign in</button>
   	    </fieldset>
    </form>
    <!--  -->

    <form style="display:none" id="1">
        <input type="submit" value="test">
    </form>   

    <!-- -->
    <form style="display:none" id="2">
        This is the third
    </form>   
    <!-- -->


</div>
<div id="prev" style="display:none">Previous</div>
<div id="next">Next</div>

