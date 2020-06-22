<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$custID = $_REQUEST["custID"];
$empID = $_REQUEST["empID"];

$sql = "INSERT INTO ORDERS (ORDER_STATUS, CUSTOMER_ID, EMPLOYEE_ID) VALUES ('Pending', '$custID', '$empID')";

if(mysqli_query($link, $sql)){
    echo "TRUE";
} else{
    echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
}
 
mysqli_close($link);
?>
