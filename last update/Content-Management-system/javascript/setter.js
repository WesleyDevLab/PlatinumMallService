$(document).ready(function(){
	NProgress.start();
	NProgress.set(0.5)
	$("#background_shadow").css({height:$(document).height()});
	$(".bar").css({"top":windowH+"500px"});
	NProgress.done();
	var buttons= [];
	var windowH=window.innerHeight;
	var headerH=document.getElementById('header').offsetHeight;
	var navH=windowH-headerH;
	document.getElementById('navigator').style.height=navH+395+"px";

	var cut=120;
		$(document).scroll(function(){
		var scroll_pos=$(document).scrollTop();
		
	});
	//Initilize the background shadow
	$("#background_shadow").click(function(){
		$(this).fadeOut();
		$("#window").fadeOut();//upload_window
		$("#logout_window").fadeOut();//logout window
		$("#add_item_box").fadeOut();//add item window
		$("#edit_item_box").fadeOut();// edit item window
	});

	$("#header_logout img").click(function(){
		$("#background_shadow").css({display:'none',height:$(document).height()});
		$("#background_shadow").fadeIn();
		$(".wrapper").append('<div id="logout_window"><span id="red_top"><i class="fa fa-plug" style="font-size:29px;color:rgba(255,255,255,0.7);"></i></span><br><p>Are you sure you want to logout ?</p><button id="cancel_button" class="pure-button pure-button-active">Cancel</button><button id="logout_confirm" class="button-error pure-button">Logout</button></div>');
		$("#logout_window").fadeIn();

			$("#cancel_button").click(function(){
				$("#logout_window").fadeOut();
				$("#background_shadow").fadeOut();
			});
			$("#logout_confirm").click(function(){
				$("#logout_window").fadeOut();
				$("#background_shadow").fadeOut();
				$.ajax({
					url:'../functions/responder.php',
					type:'POST',
					data:'logout=1',
					success:function(result){
						window.location.href="../login.php";
					}
				});
			});
	}); 
	
$("#nav_button").click(function(){
		$(this).addClass("buttonOne");
		$("#nav_button2").removeClass("buttonTwo");
		$("#nav_button3").removeClass("buttonThree");
		$("#nav_button4").removeClass("buttonFour");
		$("#nav_button5").removeClass("buttonFive");
	});

$("#nav_button2").click(function(){
		$(this).addClass("buttonTwo");
		$("#nav_button").removeClass("buttonOne");
		$("#nav_button3").removeClass("buttonThree");
		$("#nav_button4").removeClass("buttonFour");
		$("#nav_button5").removeClass("buttonFive");
	});

$("#nav_button3").click(function(){
		$(this).addClass("buttonThree");
		$("#nav_button").removeClass("buttonOne");
		$("#nav_button2").removeClass("buttonTwo");
		$("#nav_button4").removeClass("buttonFour");
		$("#nav_button5").removeClass("buttonFive");
	});
$("#nav_button4").click(function(){
		$(this).addClass("buttonFour");
		$("#nav_button").removeClass("buttonOne");
		$("#nav_button2").removeClass("buttonTwo");
		$("#nav_button3").removeClass("buttonThree");
		$("#nav_button5").removeClass("buttonFive");
	});

$("#nav_button5").click(function(){
		$(this).addClass("buttonFive");
		$("#nav_button").removeClass("buttonOne");
		$("#nav_button2").removeClass("buttonTwo");
		$("#nav_button3").removeClass("buttonThree");
		$("#nav_button4").removeClass("buttonFour");
	});

//---
$("#header_button i").hover(function(){
	var size=$(this).css("font-size");
	if(size=="45px")
	  $(this).animate({"font-size":"50px"},'fast').delay(50).animate({"font-size":"45px"},'fast');
});

$("#header_button #users_button_header").click(function(){
	$("#users_window").css({display:"block"})
	$("#users_window").animate({width:"800px",height:"565px"},'fast');
});

$("#exit_button_users_window").click(function(){
$("#users_window").animate({width:"0px",height:"0px"},'fast',function(){
	$("#users_window").css({display:"none"});
});

});

$("#users_window_button").click(function(){
getWindow("add_user.php");
$(this).addClass("users_window_button_bottom_border");
});
//--Category click action
$("#category").click(function(){
	$("#category_window").fadeIn();
});

//--Checking Category name
$("#category_input").keyup(function(){
	var name=$(this).val().trim();
	if(!name.length){
		$("#category_input").css({"border-color":"lightblue"});
		$("#error_category_name").css({"display":"none"});
		return;
	}
	
	$.ajax({
		url:'../functions/responder.php',
		type:'POST',
		data:'check_category_name=1&name='+name,
		success:function(result){
			if(result==0){
				$("#category_input").css({"border-color":"red"});
				$("#error_category_name").css({"display":"block"});
			}
			else{
				$("#category_input").css({"border-color":"lightblue"});
				$("#error_category_name").css({"display":"none"});
			}
		}
	});
});

$("#close_categories").click(function(){
$("#category_window").fadeOut();
});
//add category
$("#category_add_button").click(function(event){
event.preventDefault();
event.stopPropagation();
var category_name=$("#category_input").val();
if(!category_name.length){
	alert("Fill the field");
	return;
}
	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'add_cat=1&name='+category_name,
		success:function(result){
			alert(result);
		}
	});

});

//Delete category
$("#delete_cat*").click(function(){
	var flag=confirm("Warning:All the items of the category will be deleted as well,Do you want to delete the category ?");
	if(flag){
		var index=$("#delete_cat*").index(this);
		var key=$("#delete_cat*:eq("+index+")").attr("alt");

		$.ajax({
			type:'POST',
			url:'../functions/responder.php',
			data:'delete_cat=1&key='+key,
			success:function(result){
				alert(result);
			}
		});
	}
});

});
// getting specific page to display it in the index content area
function getPage(name){
	NProgress.start();
	$.ajax({
		 xhr: function() {
        var xhr = new window.XMLHttpRequest();
        xhr.upload.addEventListener("progress", function(evt) {
            if (evt.lengthComputable) {
                var percentComplete = evt.loaded / evt.total;
                NProgress.set(percentComplete);
            }
       }, false);

       return xhr;
    },
		url:"../cms/"+name,
		type:'POST',
		success:function(result){
			$("#content").css({"display":"none"});
			$("#content").html(result);
			$("#content").fadeIn();
			NProgress.done();
		}

	});
}
function getWindow(name){
	NProgress.start();
	NProgress.set(0.5);
	$.ajax({
		url:"../cms/"+name,
		type:'POST',
		success:function(result){
			$("#user_window_content").html(result);
			NProgress.done();
		}

	});
}

