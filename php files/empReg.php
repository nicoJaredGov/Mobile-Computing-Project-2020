<?php
$username = "s2067058";
$password = "abc2067058";
$database = "d2067058";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
 
// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$output = array();

$fname = $_REQUEST["fname"];
$lname = $_REQUEST["lname"];
$password = $_REQUEST["password"];
$empEmail = $_REQUEST["employeeEmail"];
$restaurant = $_REQUEST["restaurant"];

$options = [
    'cost' => 12,
];
$pass = password_hash($password, PASSWORD_BCRYPT, $options);


$sql1 = "SELECT COUNT(*) AS RESULT FROM EMPLOYEES WHERE EMP_EMAIL = '$empEmail'";
$check = mysqli_query($link, $sql1);
if($check){
    $check_count = mysqli_fetch_array($check);
    if($check_count['RESULT'] != '0'){
	echo "FALSE";
    } else{
	$sql2 = "INSERT INTO EMPLOYEES (EMP_EMAIL,EMP_FNAME,EMP_LNAME,EMP_PASSWORD,RESTAURANT_NAME) VALUES ('$empEmail','$fname', '$lname', '$pass','$restaurant')";
	if(mysqli_query($link, $sql2)){
    		$sql3 = "SELECT COUNT(*) AS RESULT, RESTAURANT_ID FROM RESTAURANTS WHERE RESTAURANT_NAME = '$restaurant'";
		$check2 = mysqli_query($link, $sql3);
		if($check2){
    			$check_count2 = mysqli_fetch_array($check2);
    			if(!($check_count2['RESULT'] != '0')){
    				$sql4 = "INSERT INTO RESTAURANTS (RESTAURANT_NAME) VALUES ('$restaurant')";
				if(mysqli_query($link, $sql4)){
					//
					if ($result = mysqli_query($link,"SELECT EMPLOYEE_ID, EMP_PASSWORD FROM EMPLOYEES ORDER BY EMPLOYEE_ID DESC LIMIT 1")){
						while ($row=$result->fetch_assoc()){
							$output[] = $row;
						}
					}
					echo json_encode($output);
					//
				} else{
    					echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
				}
    			} else{
				//
				if ($result = mysqli_query($link,"SELECT EMPLOYEE_ID, EMP_PASSWORD FROM EMPLOYEES ORDER BY EMPLOYEE_ID DESC LIMIT 1")){
						while ($row=$result->fetch_assoc()){
							$output[] = $row;
						}
					}
					echo json_encode($output);
				//
    			}
		} else{
    			echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
		}

	} else{
    		echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
	}
    }

} else{
    echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
}



mysqli_close($link);
?>
