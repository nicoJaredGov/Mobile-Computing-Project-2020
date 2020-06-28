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

$sql1 = "SELECT COUNT(*) AS RESULT FROM CUSTOMERS WHERE CUSTOMER_EMAIL = '$custEmail'";
$check = mysqli_query($link, $sql1);
if($check){
    $check_count = mysqli_fetch_array($check);
    if($check_count['RESULT'] == '0'){
	echo "Customer does not exist";
    } else {
	$sql2 = "INSERT INTO ORDERS (TIME_CREATED, CUSTOMER_ID, EMPLOYEE_ID, RESTAURANT_NAME) VALUES (CURRENT_TIMESTAMP(),(SELECT CUSTOMER_ID FROM CUSTOMERS WHERE CUSTOMER_EMAIL = '$custEmail') ,'$empId','$restaurant')";
	if(mysqli_query($link, $sql2)){
    		echo "TRUE";
	} else{
    		echo "ERROR: Could not able to execute $sql2. " . mysqli_error($link);
	}
    }
} else{
    echo "ERROR: Could not able to execute $sql1. " . mysqli_error($link);
}

 
mysqli_close($link);
?>
