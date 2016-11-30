<?php
session_start();
//----
//reads store id from a file
function getStoreId(){
		$storeId = fopen("../id.txt", "r") or die("Unable to open file!");
		return fread($storeId,filesize("../id.txt"));
		fclose($storeId);
}
function checkLoggedIn(){
	if(!isset($_SESSION['adminSession']))
		header('Location:../login.php');
	
}
// get's admin's first name
function getAdminFirstName(){
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL,'http://localhost:8080/PlatinumMall/admins/'.getStoreId()."/".$_SESSION['adminSession']);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: text/xml'));
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = curl_exec($ch);
	$result = json_decode($result);
	foreach ($result as $key => $value) 
		if($key=="firstName")return $value;
}
function getPrimaryPhoto($itemId){
	$url="http://localhost:8080/PlatinumMall/photos/".$itemId;
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL,$url);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = curl_exec($ch);
	$result = json_decode($result,true);
	foreach ($result as $key => $value)
		if($value["primary"]==1)
			return $value["path"];
		return "no";
}
function getItems($storeId){
 $url="http://localhost:8080/PlatinumMall/items/getitemsbystoreid/".$storeId;
 $ch = curl_init();
 curl_setopt($ch, CURLOPT_URL,$url);
 curl_setopt($ch, CURLOPT_POST, true);
 curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 $result = curl_exec($ch);
 $result = json_decode($result,true);
 foreach ($result as $key => $value) {
 	echo'
 		<div id="item_token">
			<div id="item_photo">';
			$photoPath = getPrimaryPhoto($value["id"]);
			if($photoPath=="no")
			echo'<img src="../images/placeholder.jpg"/>';
			else echo '<img src="../items_photos/'.$photoPath.' "/>';

			echo'</div>
	  		<div id="bottom_container">
	  			<div id="item_name">
		<p>'.$value["name"].'</p>
		</div>
		<div id="item_buttons">
	  		<i alt="'.$value['id'].'" title="Edit this item" id="edit_item" class="fa fa-gear"></i><br>
	  		<i alt="'.$value['id'].'" title="Delete this item" id="delete_item" class="fa fa-trash"></i>
	    </div>
	    </div>
	    
 </div>
 	';

 }

}



?>