<?php
include('../functions/engine.php');
?>
<link rel="stylesheet" type="text/css" href="../css/additem.css">
<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script type="text/javascript" src="../javascript/jquery.js"></script>
<script type="text/javascript" src="../javascript/add_item.js"></script>
  <!-- wysiwyg files -->
  <link rel="stylesheet" type="text/css" href="../ckeditor/samples/toolbarconfigurator/lib/codemirror/neo.css">
  <script src="../ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="../ckeditor/samples/js/sample.js"></script>
  <!--  -->
  
<div id = "add_item_content_box">

 	<div id="adder">
       <div id="paragraph_box">
		 <p>These are the items you have , <u>add/edit/delete</u> the items you want.</p>
		</div>
	</div>

  <div id="option_bar_and_ai_icon_box">
    <div id="option_bar">
		
		<form class="pure-form">
		   <label id="search_label">Search</label>
		   <input id="search" class="pure-input-rounded" type="text" placeholder="Type Something">
		   &nbsp;&nbsp;&nbsp;

		   <label id="select_label">
		   Select Category
		   &nbsp;&nbsp;
		   </label>
		    <select class="select_item_category" id="state">
		    	<option id="select_item" value="0">ALL</option>
		    	<option id="select_item" value="key">Value</option>'; 	
		    
		   </select>
		</form>
	 </div>

   <i title="Add new item" id="add_item_icon" class="fa fa-cart-plus"></i>
  </div>
<!-- add item window -->
  <div id="add_item_box">
	<div id="aib_icon_top_container">

		<div id="add_item_sb_btn_box">
        <div id="bb"><br>
        	<button id="add_item_submit_button" class="pure-button pure-button-primary">Submit</button>
        </div>
      </div>

	   <div id="ic"><i id="close_add_item_window" class="fa fa-close"></i></div>
	</div>

	<div id="add_item_form">

		<form id="add_item_form_styling" class="pure-form pure-form-stacked">
			<table id="table_add_item" border="0" >
				<tr>
					<legend><strong>Adding New Item</strong></legend>
				</tr>

				<tr>
					<td width="55px">Name:</td>
					<td width="320px" >
						<input id="item_name_field" class="pure-u-23-24" type="text">
					</td>
					<td width="55px">Price:</td>
					<td width="120px">
						<input id="item_price_field" class="pure-u-23-24" type="text">
					</td>
					<td width="75px">
					Quantity
					</td>
					<td>
						<input id="form_number" min="0" type="number" />
					</td>
				</tr>

				<tr>
					<td width="0%">
						<br>
					</td>
				</tr>

				<tr>
					<td>Category:</td>

					<td>
					<select id="category_list" class="pure-input-1-2">
						<option value="key">value</option>
						<option value="key">value2</option>
                
               		 </select>
					</td>
					<td>Brand</td>
					<td>
						<select id="brand_list" class="pure-input-1-2">
						<option value="0">NO</option>
	                    <option value="1">YES</option>
	               		</select>
					</td>
						<td>
							Discount

						</td>
						<td>
						  <input id="new_price" class="pure-u-23-24" type="text">
						</td>
				</tr>
			</table>
			<!--    -->
  
			<div id="text_editor">
				<strong>Description</strong>
				
			<div id="editor_container">
			  <div id="editorAddItem"></div>
			</div>

			</div>
			<script type="text/javascript">
				 $(function(){
				 	CKEDITOR.replace("editorAddItem");
     			});
			</script>
		</form>
	</div>
     
  </div>

 <div id="view_item_box">
<?php 
  getItems(getStoreId());
?>
  <div id="item_token">
			<div id="item_photo">
				<img src="../images/placeholder.jpg"/>
			</div>
	  		<div id="bottom_container">
	  			<div id="item_name">
		<p>item name</p>
		</div>
		<div id="item_buttons">
	  		<i alt="'.$row['id'].'" title="Edit this item" id="edit_item" class="fa fa-gear"></i><br>
	  		<i alt="'.$row['id'].'" title="Delete this item" id="delete_item" class="fa fa-trash"></i>
	    </div>
	    </div>
	    
 </div>


<!-- EDIT ITEM-->

<div id="edit_item_box">
 <i id="close_edit_item_window" class="fa fa-close"></i>

<div id="window_tabs">

	<div class="item_information_button" id="tab_button">
		<i class="fa fa-info" style="font-size:30px"></i>
		Information
	</div>

	<div class="specification_button" id="tab_button">
		<i class="fa fa-sticky-note" style="font-size:30px"></i>
		Specification
	</div>
	<div class="item_images_button" id="tab_button">
		<i id="item_images_icon" class="fa fa-image" style="font-size:30px"></i>
		Photos
	</div>
	<div>
		<button id="add_item_submit_button2" class="pure-button pure-button-primary">Update</button>
	</div>
</div>
<hr>
<div id="item_photo_adder">
<?php
	if(!empty($_FILES['file'])){

	   foreach ($_FILES['file']['name'] as $key => $name) {
		  $val=substr($name, -3);
		  $val=strtolower($val);
		 if($val!='jpg' && $val!='png' && $val!='gif')//complex
			   die(print("0"));

			$query="SELECT * FROM photos";
			$result=$GLOBALS['conn']->query($query);
			$num=mysqli_num_rows($result);
			$name=$num+1;
			$name.=".".$val;

			if(!(empty($_POST['primary_key'])) && $_FILES['file']['error'][$key]==0 && 
				move_uploaded_file($_FILES['file']['tmp_name'][$key],'../items_photos/'.$name)){

			$uploaded[]=$name;
		
			$key=$_POST['primary_key'];
			$path='items_photos/'.$name;
			$query="INSERT INTO `photos`(`id`, `item_id`, `photo_path`, `photo_name`) VALUES ('','$key','$path','$name')";
			$GLOBALS['conn']->query($query);
		}
		$counter++;
	}
	if(!empty($_POST['ajax']))
		die(json_encode($uploaded));
	}
?>
	<div id="bottom_div2">
		<form action="" method="POST" enctype="multipart/form-data">
                    <div class="myLabel">
                        <input type="file" id="file" name="file[]" multiple="multiple" />
                        <span>Choose photo</span>
                    </div>
                    <input type="submit" id="submit2" name="submit_button" value="Upload" />
         </form>

         <div id="item_images_viewer"><!-- images for specific item displays here -->
        	
         </div>

	</div>
</div>
<!-- specification -->
<div id="specification_wrapper">
	<h3>* Item's Specifications</h3>
	<div id="specification_body">
	 <div id="specification_list">

		<div id="specification_token">
			<span id="specification_key">key</span>
			<span id="specification_value">value</span>
			<span id="specification_key_holder"></span>
			<span id="delete"><i id="delete_specification" class="fa fa-close"></i></span>
		</div>

	</div>
	
	<div id="add_new_specification">
		<h4>Add new Specification to your item !</h4>
		<div id="specification_form">
			<form id="add_item_form_styling" class="pure-form pure-form-stacked">
				Key:<input id="" class="pure-u-23-24" type="text"><br>
				Value:<input id="" class="pure-u-23-24" type="text">
				<button id="add_new_specification_button" class="pure-button pure-button-primary">Add</button>
			</form>
		</div>
	</div>

	</div>
</div>
<!-- End specification-->

<div id="window_wrapper">

<div id="add_item_form">
		<form id="add_item_form_styling" class="pure-form pure-form-stacked">
			<table id="table_add_item" border="0">
				<tr>
					<legend><strong>Edit Item</strong></legend>
				</tr>

				<tr>
					<td width="55px">Name:</td>
					<td width="320px" >
						<input id="item_name_field2" class="pure-u-23-24" type="text">
					</td>
					<td width="55px">Price:</td>
					<td width="120px">
						<input id="item_price_field2" class="pure-u-23-24" type="text">
					</td>
					<td width="75px">
					Quantity
					</td>
					<td>
						<input id="form_number2" min="0" type="number" />
					</td>
				</tr>

				<tr>
					<td width="0%">
						<br>
					</td>
				</tr>

				<tr>
					<td>Category:</td>

					<td>
					<select id="category_list2" class="pure-input-1-2">
						<option value="key">Value</option>
						<option value="key">value2</option>
               		 </select>
					</td>
					<td>Brand</td>
					<td>
						<select class="edit_offer_list" id="offer_list" class="pure-input-1-2">
						<option value="0">NO</option>
	                    <option value="1">YES</option>
	               		</select>
					</td>
						<td>
							Discount:
						</td>
						<td>
						  <input id="discount_ratio" type="text" />
						</td>
				</tr>
			
			</table>


			<div id="text_editor_at_the_edit_item_box">
				<div id="describtion_strong">Description</div>
			  
			  <div id="editorEditItem"></div>
			
			</div>
			<script type="text/javascript">
				 $(function(){
				 	CKEDITOR.replace("editorEditItem");
     			});
			</script>
		</form>
		<span style="display:none;" id="key_holder"></span>
		<!---->
	</div>
  </div>
 </div>

</div>