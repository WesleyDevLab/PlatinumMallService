$(document).ready(function(){
	$("#editor_container").css({"margin-top":"20px"});

	$("#add_item_icon").click(function(){
		$("#background_shadow").css({display:'none',height:$(document).height()});
		$("#background_shadow").fadeIn();
		$("#add_item_box").fadeIn();
	});

	$("#close_add_item_window").click(function(){
		$("#background_shadow").fadeOut();
		$("#add_item_box").fadeOut();
	});
	$("#close_edit_item_window").click(function(){
		$("#background_shadow").fadeOut();
		$("#edit_item_box").fadeOut();
	});

$("#add_item_submit_button").click(function(){
	var item_name=$("#item_name_field").val();
	var item_price=$("#item_price_field").val();
	var quantity=$("#form_number").val();
	var category=$("#category_list").val();
	var item_offer=$("#offer_list").val();
	var item_new_price="";
	var Brand=$("#brand").val();
	var productCode=$("#productCode").val();
	var description=CKEDITOR.instances['editorAddItem'].getData();
	if(item_offer==1)
		item_new_price=$("#new_price").val();

	if(!productCode.length || !Brand.length || !item_name.length || !item_price.length || !quantity.length || !category.length || (item_offer==1 && !item_new_price.length) || !description.length){
		alert("WARNING: Please fill All fields");
		return;
	}
	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'addNewItem=1&item_name='+item_name+'&item_price='+item_price+'&quantity='+quantity+'&category='+category+'&item_offer='+item_offer+'&item_new_price='+item_new_price+'&description='+description+'&brand='+Brand+"&product_code="+productCode,
		success:function(result){
			if(result==1){
				alert("Item "+item_name+" Added successfully");
				$("#background_shadow").fadeOut();
				$("#add_item_box").fadeOut();
			}
		}
	});
	
});

$("#delete_item*").click(function(){
	var flag=confirm("Are you sure you want to delete this item ?");
	if(flag){
	var key=($(this).attr("alt"));
	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'deleteItem=1&key='+key,
		success:function(result){
			alert(result);
			getPage('../cms/add_item.php');
		}
	});
}
});

$("#edit_item*").click(function(){
	$("#window_wrapper").fadeIn();
	$("#item_window_back").fadeOut();
	$("#item_photo_adder").fadeOut();
	
	var key=($(this).attr("alt"));
	$("#key_holder").html(key);

	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'getItemData=1&key='+key,
		success:function(result){
			//var info=JSON.parse(result);
			console.log(result);
		}
	});



$("#background_shadow").css({display:'none',height:$(document).height()});
$("#background_shadow").fadeIn();
$("#edit_item_box").fadeIn();

});

$("#add_item_submit_button2").click(function(){// this is for update button
	var name=$("#item_name_field2").val();
	var price=$("#item_price_field2").val();
	var quantity=$("#form_number2").val();
	var category=$("#category_list2").val();
	var offer=$(".edit_offer_list").val();
	var new_price=$("#new_price2").val();
	var description=(CKEDITOR.instances['editorEditItem'].getData());
	var Brand=$("#Brand2").val();
	var productCode=$("#productCode2").val();
	var key=$("#key_holder").text();
	if(!productCode.length || !Brand.length || !name.length || !price.length || !quantity.length || !category.length || (offer==1 && !new_price.length) || !description.length){
		alert("Please Fill all fields");
		return;
	}
	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'updatItem=1&key='+key+'&name='+name+'&price='+price+'&quantity='+quantity+'&category='+category+'&offer='+offer+'&new_price='+new_price+'&description='+description+'&brand='+Brand+"&product_code="+productCode,
		success:function(result){
			alert(result);
		}
	});

});


$(".item_images_button").click(function(){
	$("#window_wrapper").hide();
	$("#specification_wrapper").hide();
	$("#item_photo_adder").fadeIn();
	getItemPhotos($("#key_holder").text());
});

$(".item_information_button").click(function(){
	$("#item_photo_adder").hide();
	$("#specification_wrapper").hide();
	$("#window_wrapper").fadeIn();
});

$(".specification_button").click(function(){
	$("#item_photo_adder").hide();
	$("#window_wrapper").hide();
	$("#specification_wrapper").fadeIn();
});


$("#submit2").click(function(event){
	event.preventDefault();
	event.stopPropagation();
	var fileInput=document.getElementById("file");
	
	var data= new FormData();
	var primary_key=$("#key_holder").text();

	data.append('ajax','true');
	data.append('primary_key',primary_key);

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
		getItemPhotos(primary_key);

	});
	request.open("POST","../cms/add_item.php");
	request.setRequestHeader('Cache-Control','no-cache');
	NProgress.start();
	request.send(data);
});

function getItemPhotos(key){
	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'getItemPhotos=1&key='+key,
		success:function(result){
			photos=JSON.parse(result);
			$("#item_images_viewer").html("");
			for(var i=0;i<photos.length;i+=2){// make photos nodes
				var image_box=document.createElement("div");
				var image=document.createElement("img");
				var delete_span=document.createElement("span");
				var primary_span=document.createElement("span");
				delete_span.setAttribute("id","delete_this_photo");
				delete_span.appendChild(document.createTextNode("Delete\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0"));
				primary_span.appendChild(document.createTextNode("Primary"));
				primary_span.setAttribute("id","make_primary");
				delete_span.setAttribute("alt",photos[i]);
				primary_span.setAttribute("alt",photos[i]);
				image_box.setAttribute("class","image_box");
				image.setAttribute("alt",photos[i]);
				image.setAttribute("src","../"+photos[i+1]);
				image_box.appendChild(image);
				image_box.appendChild(delete_span);
				image_box.appendChild(primary_span);
				$("#item_images_viewer").append(image_box);
				//--Events
				
				$(delete_span).click(function(){//Delete photo
					var flag=confirm("Are you sure you want to delete this photo ?");
					if(!flag)
						return;
					var photo_key=($(this).attr("alt"));
					$.ajax({
						type:'GET',
						url:'../functions/responder.php',
						data:'delete_item_photo=1&key='+photo_key,
						success:function(result){
							alert(result);
							getItemPhotos(key);
						}
					});
				});
				
				$(primary_span).click(function(){
					var primary_key=($(this).attr("alt"));
					var item_key=$("#key_holder").text();
					$.ajax({
							type:'GET',
							url:'../functions/responder.php',
							data:'makePrimary=1&key='+primary_key+"&photo_key="+item_key,
							success:function(result){
								alert(result);
						}
					});
				});

			}
		}
	});


}


$(".select_item_category").change(function(){
	var cat_id=($(this).val());
	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'getItemsWithCategory=1&key='+cat_id,
		success:function(result){
			$("#view_item_box").html("");
			$("#view_item_box").html(result);
		}
	});

});

$("#search").keyup(function(){
	var query=($(this).val());
	if(!query.length){
		$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'getItemsWithCategory=1&key=0',
		success:function(result){
			$("#view_item_box").html("");
			$("#view_item_box").html(result);
		}
	});
		return;
	}
	$.ajax({
		type:'POST',
		url:'../functions/responder.php',
		data:'getItemsByquery=1&query='+query,
		success:function(result){
			$("#view_item_box").html("");
			$("#view_item_box").html(result);
		}
	});
});

//-End
});