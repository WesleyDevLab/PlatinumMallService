$(document).ready(function(){
	
	$('#critb_top_bar*').click(function(){
		var index = $('#critb_top_bar*').index(this);
		$('#requested_items_table_container*').eq(index).slideToggle(400);
	});

	$('#customer_request_box_top_bar*').click(function(){

         var len = $('#crcb_bottom_section*').length;
         var index = $('#customer_request_box_top_bar*').index(this);
         
         for(i=0;i<len;i++){
         	if($('#crcb_bottom_section*').eq(i).css('display')=='block' && i!=index){
         		$('#crcb_bottom_section*').eq(i).slideUp(400);
         	}
         }
		
		$('#crcb_bottom_section*').eq(index).slideToggle(400);
	});
  
});