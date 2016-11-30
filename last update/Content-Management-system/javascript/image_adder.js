$(document).ready(function(){
printPhotos();
$("#upload img").hover(function(){
$("#upload img").attr("src","../images/upload_hover.png");
});
$("#upload img").mouseout(function(){
$("#upload img").attr("src","../images/upload.png");
});

$("#upload img").click(upload_div);

var submit=document.getElementById("submit");
submit.addEventListener('click',ajaxUploader);
});

function printPhotos(){
	var j=1;
	var dir="../slider_img";
	var fileextension=["jpg","png","gif"];
	$.ajax({
	    url: dir,
	    success: function (data) {
	  	for(var ex=0;ex<fileextension.length;ex++){
	        $(data).find("a:contains(" + fileextension[ex] + ")").each(function () {
	            var filename = this.href.replace(window.location.host, "").replace("http://", "");
	            var x="";// x is the name of the file after the cutting
	            for(var i=filename.length-1;i>=0;i--)
	            	if(filename[i]=='/')
	            		break;
	            	else x+=filename[i];
	            	x=x.split("").reverse().join("");// reverse the string
	            var child="<div id='token'><span id='delete'><img alt='"+x+"' id='trash_image' src='../images/trash.png'/></span><img id='image_token' alt='"+x+"' src='../slider_img/" + x + "'></div>";
	            child+=!(j%2)?"<br>":"";// to be edited
	            $("#adder").append(child);
	            j++;
	        });
	    }
	    },
	    complete:function(a,b){//the delete code
		var id=$("#trash_image*").each(function(i,obj){
			$("#trash_image*:eq("+i+")").click(function(){
			var file_name=$("#trash_image*:eq("+i+")").attr("alt");
			var flag=confirm("Do you want to delete this photo ?");
			if(flag){
				$.ajax({
					type:'POST',
					data:'delete_photo='+file_name,
					url:'../functions/responder.php',
					success:function(result){
					 if(result)
						 getPage("../cms/images_adder.php");
					}
				});
		   }
		});
	});
		}
	});
}

function upload_div(){
$("#background_shadow").css({display:'none',height:$(document).height()});
$("#background_shadow").fadeIn();
$("#window").css({left:"15%",top:"-10px"});
$("#window").fadeIn();

}
function ajaxUploader(event){
	event.preventDefault();
	event.stopPropagation();
	var fileInput=document.getElementById("file");
	var data= new FormData();
	data.append('ajax','true');
	for(var i=0;i<fileInput.files.length;i++)
		data.append('file[]',fileInput.files[i]);
	
	var request= new XMLHttpRequest();
	request.upload.addEventListener('progress',function(event){
			if(event.lengthComputable){
				var percent=Math.round(event.loaded/event.total);
				NProgress.set(percent);
			}
	});

	request.upload.addEventListener('load',function(event){
		$("#background_shadow").fadeOut();
		$("#window").fadeOut();
		getPage("../cms/images_adder.php");
	});

	request.upload.addEventListener('error',function(event){
		alert("Sorry, there was an error");
	});

	request.addEventListener('readystatechange',function(event){
		if(this.readyState==4)
			if(this.status==200){
				if(this.response==0)
					alert("Warning:Some of the file/s has non-permissible extension");
		 }
		 
	});
	request.open("POST","../cms/images_adder.php");
	request.setRequestHeader('Cache-Control','no-cache');
	NProgress.start();
	request.send(data);
}

