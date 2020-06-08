<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$email = $_REQUEST["email"];
$fname = $_REQUEST["fname"];
$lname = $_REQUEST["lname"];
$password = $_REQUEST["password"];

$sql = "INSERT INTO CUSTOMERS (CUSTOMER_EMAIL,FNAME, LNAME, PASSWORD) VALUES ('$email','$fname', '$lname', '$password')";

if(mysqli_query($link, $sql)){
    echo "TRUE";
} else{
    echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
}
 
mysqli_close($link);
?>