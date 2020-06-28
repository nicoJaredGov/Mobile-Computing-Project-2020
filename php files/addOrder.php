<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$custEmail = $_REQUEST["custEmail"];
$empId = $_REQUEST["empId"];
$restaurant = $_REQUEST["restaurant"];

$sql1 = "SELECT COUNT(*) AS RESULT, CUSTOMER_ID FROM CUSTOMERS WHERE CUSTOMER_EMAIL = '$custEmail'";
$check = mysqli_query($link, $sql1);
if($check){
    $check_count = mysqli_fetch_array($check);
    if($check_count['RESULT'] == '0'){
	echo "Customer does not exist";
    } else {
	$stmt = $link->prepare("INSERT INTO ORDERS (TIME_CREATED, CUSTOMER_ID, EMPLOYEE_ID, RESTAURANT_NAME) VALUES (?,?,?,?)");
	$custId = $check_count['CUSTOMER_ID'];
	$date = date('Y-m-d h:i:s a', time());

	$stmt->bind_param("siis", $date, $custId ,$empId ,$restaurant);
	$stmt->execute();
	echo "TRUE";
    }
} else{
    echo "ERROR: Could not able to execute $sql1. " . mysqli_error($link);
}

 
mysqli_close($link);
?>
