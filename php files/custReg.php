<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$output = array();

$email = $_REQUEST["email"];
$fname = $_REQUEST["fname"];
$lname = $_REQUEST["lname"];
$password = $_REQUEST["password"];


$options = [
    'cost' => 12,
];
$pass = password_hash($password, PASSWORD_BCRYPT, $options);

$sql1 = "SELECT COUNT(*) AS RESULT FROM CUSTOMERS WHERE CUSTOMER_EMAIL = '$email'";
$check = mysqli_query($link, $sql1);
if($check){
    $check_count = mysqli_fetch_array($check);
    if($check_count['RESULT'] != '0'){
	echo "FALSE";
    } else{
	$sql2 = "INSERT INTO CUSTOMERS (CUSTOMER_EMAIL,FNAME, LNAME, PASSWORD) VALUES ('$email','$fname', '$lname', '$pass')";
	if(mysqli_query($link, $sql2)){
    		//
		if ($result = mysqli_query($link,"SELECT CUSTOMER_ID, PASSWORD FROM CUSTOMERS ORDER BY CUSTOMER_ID DESC LIMIT 1")){
			while ($row=$result->fetch_assoc()){
				$output[] = $row;
			}
		}
		echo json_encode($output);
		//

	} else{
    		echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
	}
    }

} else{
    echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
}

mysqli_close($link);
?>