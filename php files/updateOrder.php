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
	$stmt = $link->prepare("UPDATE ORDERS SET EMPLOYEE_ID = ?  WHERE ORDER_ID = ?");
	$stmt->bind_param("ii",$empId,$orderId);
	$stmt->execute();
} elseif($status == 1){
	$stmt = $link->prepare("UPDATE ORDERS SET ORDER_STATUS = 'ready' WHERE ORDER_ID = ?");
	$stmt->bind_param("i", $orderId);
	$stmt->execute();
} elseif($status == 2){
	$stmt = $link->prepare("UPDATE ORDERS SET ORDER_STATUS = 'collected', TIME_COLLECTED = ? WHERE ORDER_ID = ?");
	$date = date('Y-m-d h:i:s a', time());
	$stmt->bind_param("si", $date, $orderId);
	$stmt->execute();
} elseif($status == 3){
	$stmt = $link->prepare("UPDATE ORDERS SET RATING = 1 WHERE ORDER_ID = ?");
	$stmt->bind_param("i", $orderId);
	$stmt->execute();
} else{
	$stmt = $link->prepare("UPDATE ORDERS SET RATING = -1 WHERE ORDER_ID = ?");
	$stmt->bind_param("i", $orderId);
	$stmt->execute();
}
	
echo "TRUE"; 
mysqli_close($link);
?>
