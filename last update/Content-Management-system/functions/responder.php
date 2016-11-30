<?php
include("engine.php");
if(isset($_POST['validate_login'])){
	$username = $_POST['username'];
	$password = $_POST['password'];

    $ch = curl_init();
	curl_setopt($ch, CURLOPT_URL,'http://localhost:8080/PlatinumMall/admins/'.getStoreId()."/".$username."/".$password);
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

	$result = curl_exec($ch);
	if($result!="0"){
		$adminId='';
		for($i=0,$j=0;$i<strlen($result);$i++){
			$adminId.=$j==1?$result[$i]:'';
			$j=($result[$i]=='&')?1:$j;
		}
		$_SESSION['adminSession']=$adminId;
	}
	echo $result;
curl_close($ch);
}
//logout
if(isset($_POST['logout'])){
	session_destroy();
}
//-- get item data to print it in edit item window
if(isset($_POST['getItemData']) && isset($_POST['key'])){
	$key=$_POST['key'];
    $ch = curl_init();
	curl_setopt($ch, CURLOPT_URL,'http://localhost:8080/PlatinumMall/items/'.$key);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = curl_exec($ch);
	curl_close($ch);
	$data = array();
	$items = array();
	$result = json_decode($result,true);
	
	foreach ($result as $key => $value) {
		echo $key."<br>";
	
	}

	
}



?>