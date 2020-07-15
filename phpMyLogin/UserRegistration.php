<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

include 'DatabaseConfig.php';

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName) or die('unable to connect');
 $username = $_POST['username'];
 $password = $_POST['password'];
 $usertype = $_POST['usertype'];

 if($username == '' || $password == '' || $usertype == ''){

    echo 'please fill all values';

 }else{

$CheckSQL = "SELECT * FROM Users WHERE username='$u_name'";
 
$check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));
 
 if(isset($check)){

 echo 'User Name Already Exist';

 }
else{ 
$Sql_Query = "INSERT INTO Users (username,password,usertype) values ('$username','$password',$usertype)";

 if(mysqli_query($con,$Sql_Query))
{
 echo 'Registration Successfully';
}
else
{
 echo 'Something went wrong';
 }
 }

 mysqli_close($con);

}
}
?>