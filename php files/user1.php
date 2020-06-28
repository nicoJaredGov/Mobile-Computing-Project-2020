<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output = array();

$user = $_REQUEST["user"];
$password = $_REQUEST["password"];

$sql = "SELECT * from EMPLOYEES where EMP_EMAIL='$user'";
if ($result = mysqli_query($link,$sql)){
	while ($row=$result->fetch_assoc()){
			$output[] = $row;
		}
	if ($output == NULL){
		echo "NULL";
	} elseif (password_verify($password, $output[0]["EMP_PASSWORD"])) {
		echo json_encode($output);
	} else {
    		echo "WRONG";
	} 

} else {
	echo "FALSE";
}
mysqli_close($link);
?>
