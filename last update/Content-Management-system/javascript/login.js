$(document).ready(function(){
	$("#sb").click(function(event){
		event.preventDefault();
		event.stopPropagation();
		var username=$("#username").val();
		var password=$("#password").val();
		if(username.length && password.length){
			$.ajax({
				url:'functions/responder.php',
				type:'POST',
				data:'validate_login=1&username='+username+"&password="+password,
				success:function(result){
					console.log(result);
					if(result!="0"){
					$("#login_box").addClass("animated fadeOutDown");
					setTimeout(function(){
					 window.location.replace("cms/");
					},900);
				   }
					else{
						$("#login_box").addClass("animated shake");
						setTimeout(function(){
							$("#login_box").removeClass("animated shake");
						},1000);
						
					}
				}
			});
		}
		else{
			$("#login_box").addClass("animated shake");
			  setTimeout(function(){
				$("#login_box").removeClass("animated shake");
				},1000);
		}
		 
	});
});