<?php
$username = "";
$password = "";
$database = "";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output = array();

if ($result = mysqli_query($link,"SELECT * FROM CUSTOMERS ORDER BY CUSTOMER_ID DESC LIMIT 1")){
	while ($row=$result->fetch_assoc()){
		$output[] = $row;
	}
}
mysqli_close($link);
echo json_encode($output);
?>
