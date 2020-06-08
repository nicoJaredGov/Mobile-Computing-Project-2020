<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output = array();

$user = $_REQUEST["user"];
if ($result = mysqli_query($link,"SELECT EMPLOYEE_ID, EMP_PASSWORD from EMPLOYEES where EMPLOYEE_ID='$user'")){
        while ($row=$result->fetch_assoc()){
                $output[] = $row;
        }
	echo json_encode($output);
} else{
	echo "FALSE";
}
mysqli_close($link);
?>
