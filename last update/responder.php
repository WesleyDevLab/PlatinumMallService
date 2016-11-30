<?php
if(isset($_POST['addNewUser'])){

	$data=array();
	$data["firstName"] = $_POST['firstName'];
	$data["lastName"] = $_POST['lastName'];
	$data["cellPhone"] = $_POST['telephone'];
	$data["email"] = $_POST['email'];
	$data["city"] = $_POST['city'];
	$data["address1"] = $_POST['address'];
	$data["address2"] = $_POST['address2'];
	$data["password"] = $_POST['password'];
	$data["subscribe"] = $_POST['sub'];
	$data["date"]=$_POST['date'];
	$data = json_encode($data);

    $ch = curl_init();
	curl_setopt($ch, CURLOPT_URL,'http://localhost:8080/PlatinumMall/users');
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data); 
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array(                                                                          
    'Content-Type: application/json'));
	$result = curl_exec($ch);
	echo $result;
}



?>