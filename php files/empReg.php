<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$fname = $_REQUEST["fname"];
$lname = $_REQUEST["lname"];
$password = $_REQUEST["password"];
$employeeNum = $_REQUEST["employeeNum"];

$sql1 = "SELECT COUNT(*) AS RESULT FROM EMPLOYEES WHERE EMPLOYEE_ID = '$employeeNum'";
$check = mysqli_query($link, $sql1);
if($check){
    $check_count = mysqli_fetch_array($check);
    if($check_count['RESULT'] == '1'){
	echo "This username is already registered.";
    } else{
	$sql2 = "INSERT INTO EMPLOYEES (EMP_FNAME, EMP_LNAME, EMP_PASSWORD) VALUES ('$fname', '$lname', '$password')";
	if(mysqli_query($link, $sql2)){
    		echo "TRUE";
	} else{
    		echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
	}
    }

} else{
    echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
}

 
mysqli_close($link);
?>
