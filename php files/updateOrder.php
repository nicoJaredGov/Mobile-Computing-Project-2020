<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$orderId = $_REQUEST["orderId"];
$empId = $_REQUEST["empId"];
$status = $_REQUEST["status"]; //0 - n/a, 1 = ready, 2 = collected, 3 = thumbs up, 4 = thumbs down

if($status == 0){
	$sql = "UPDATE ORDERS SET EMPLOYEE_ID = '$empId' WHERE ORDER_ID = '$orderId'";
} elseif($status == 1){
	$sql = "UPDATE ORDERS SET ORDER_STATUS = 'ready' WHERE ORDER_ID = '$orderId'";
} elseif($status == 2){
	$sql = "UPDATE ORDERS SET ORDER_STATUS = 'collected', TIME_COLLECTED = CURRENT_TIMESTAMP() WHERE ORDER_ID = '$orderId'";
} elseif($status == 3){
	$sql = "UPDATE ORDERS SET RATING = 1 WHERE ORDER_ID = '$orderId'";
} else{
	$sql = "UPDATE ORDERS SET RATING = -1 WHERE ORDER_ID = '$orderId'";
}
	
if(mysqli_query($link, $sql)){
    echo "TRUE";
} else{
    echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
}
 
mysqli_close($link);
?>
