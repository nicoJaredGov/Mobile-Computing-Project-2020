<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

$output = array();
$customerId = $_REQUEST["customerId"];
$choice = $_REQUEST["choice"];

if ($choice == 1){
$sql = "SELECT ORDER_ID,TIME_CREATED,TIME_COLLECTED,RATING,ORDER_STATUS,ORDERS.RESTAURANT_NAME,EMP_FNAME,EMP_LNAME FROM ORDERS LEFT JOIN EMPLOYEES ON ORDERS.EMPLOYEE_ID = EMPLOYEES.EMPLOYEE_ID WHERE CUSTOMER_ID = '$customerId' AND (ORDER_STATUS = 'pending' OR ORDER_STATUS = 'ready')";
} elseif ($choice == 2) {
$sql = "SELECT ORDER_ID,TIME_CREATED,TIME_COLLECTED,RATING,ORDER_STATUS,ORDERS.RESTAURANT_NAME,EMP_FNAME,EMP_LNAME FROM ORDERS LEFT JOIN EMPLOYEES ON ORDERS.EMPLOYEE_ID = EMPLOYEES.EMPLOYEE_ID WHERE CUSTOMER_ID = '$customerId' AND ORDER_STATUS = 'collected'";
} else {
$sql = "SELECT ORDER_ID,TIME_CREATED,TIME_COLLECTED,RATING,ORDER_STATUS,ORDERS.RESTAURANT_NAME,EMP_FNAME,EMP_LNAME FROM ORDERS LEFT JOIN EMPLOYEES ON ORDERS.EMPLOYEE_ID = EMPLOYEES.EMPLOYEE_ID WHERE CUSTOMER_ID = '$customerId' AND (ORDER_STATUS = 'pending' OR ORDER_STATUS = 'ready')";
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
