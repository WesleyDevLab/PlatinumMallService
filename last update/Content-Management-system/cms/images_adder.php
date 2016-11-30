<?php
if(!empty($_FILES['file'])){
foreach ($_FILES['file']['name'] as $key => $name) {
	$val=substr($name, -3);
	$val=strtolower($val);
	if($val!='jpg' && $val!='png' && $val!='gif')//complex
		die(print("0"));
	
	$name=rand(0,10000).'.'.$val;
	if($_FILES['file']['error'][$key]==0 && move_uploaded_file($_FILES['file']['tmp_name'][$key],'../slider_img/'.$name)){
		$uploaded[]=$name;
	}
}
if(!empty($_POST['ajax']))
	die(json_encode($uploaded));
}
?>

<link rel="stylesheet" type="text/css" href="../css/imagesadder.css">
<script type="text/javascript" src="../javascript/image_adder.js"></script>



<div id="upload">
	<img id="upload_img" src="../images/upload.png" title="Upload Photos">
</div>

<div id="adder">
	<div id="paragraph_box">
	<p>These are the photos in <u>the photos slider</u> ,upload/delete the photo you want.</p>
	</div>
</div>

<div id="window">
        <div id="top_div">
            <div id="title">
                Upload Photos
            </div>
        </div>
        <div id="bottom_div">

            <p id="top_content">
                Select the photos you want to upload , you can select multiple files or single.<br>Note that files extentaions must be <b>jpg , png or gif.</b>
            </p>
            <br />
            <div id="buttom_content">
                
                <form action="Images_adder.php" method="POST" enctype="multipart/form-data">
                    <div class="myLabel">
                        <input type="file" id="file" name="file[]" multiple="multiple" />
                        <span>Choose photo</span>
                    </div>
                    <input type="submit" id="submit" name="submit_button" value="Upload" />
                </form>
            </div>
        </div>

    </div>