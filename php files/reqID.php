<?php
$username = "s2067058";
$password = "";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output = array();

if ($result = mysqli_query($link,"SELECT CUSTOMER_ID AS LastID FROM CUSTOMERS WHERE CUSTOMER_ID = @@Identity")){
	while ($row=$result->fetch_assoc()){
		$output[] = $row;
	}
}
mysqli_close($link);
echo json_encode($output);
?>
