<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

$output = array();
$empId = $_REQUEST["empId"];
$restaurant = $_REQUEST["restaurant"];
$choice = $_REQUEST["choice"];

if ($choice == 1){
$sql = "SELECT ORDER_ID, TIME_CREATED, TIME_COLLECTED, RATING, ORDER_STATUS, FNAME, LNAME FROM ORDERS JOIN CUSTOMERS ON CUSTOMERS.CUSTOMER_ID = ORDERS.CUSTOMER_ID WHERE EMPLOYEE_ID = '$empId' AND (ORDER_STATUS = 'pending' OR ORDER_STATUS = 'ready')";
} elseif ($choice == 2) {
$sql = "SELECT ORDER_ID, TIME_CREATED, TIME_COLLECTED, RATING, ORDER_STATUS, FNAME, LNAME FROM ORDERS JOIN CUSTOMERS ON CUSTOMERS.CUSTOMER_ID = ORDERS.CUSTOMER_ID WHERE RESTAURANT_NAME = '$restaurant' AND EMPLOYEE_ID IS NULL";
} elseif ($choice == 3) {
$sql = "SELECT ORDER_ID, TIME_CREATED, TIME_COLLECTED, RATING, ORDER_STATUS, FNAME, LNAME FROM ORDERS JOIN CUSTOMERS ON CUSTOMERS.CUSTOMER_ID = ORDERS.CUSTOMER_ID WHERE RESTAURANT_NAME = '$restaurant' AND ORDER_STATUS = 'collected' AND EMPLOYEE_ID = '$empId' ";
} else {
$sql = "SELECT ORDER_ID, TIME_CREATED, TIME_COLLECTED, RATING, ORDER_STATUS, FNAME, LNAME FROM ORDERS JOIN CUSTOMERS ON CUSTOMERS.CUSTOMER_ID = ORDERS.CUSTOMER_ID WHERE EMPLOYEE_ID = '$empId' AND (ORDER_STATUS = 'pending' OR ORDER_STATUS = 'ready')";
}

if ($result = mysqli_query($link,$sql)){
	while ($row=$result->fetch_assoc()){
		$output[] = $row;
	}
	echo json_encode($output);
} else{
	echo "FALSE";
}
mysqli_close($link);
?>
