<?php
error_reporting(E_ALL);
include "sql_data.php";

$array = array("code" => "500");

if (isset($_GET["name"]) && isset($_GET["email"]) && isset($_GET["password"]) && isset($_GET["phone"]))
{
	
	$name = $_GET["name"];
	$email = $_GET["email"];
	$password = hash("sha256", $_GET["password"]);
	$phone = $_GET["phone"];

	if ($name != NULL && $email != NULL && $password != NULL && $phone != NULL)
	{

		$link = mysqli_connect($sql_host, $sql_user, $sql_pass, $sql_db);
		if(!$link) die("Error while trying to connect to the database");

		$query = mysqli_query($link, "INSERT INTO `gtbcn_users`(`name`, `email`, `password`, `phone`) 
		VALUES (\"" . mysqli_real_escape_string($link, $name) . "\", \"" . mysqli_real_escape_string($link, $email) . "\",
		\"" . mysqli_real_escape_string($link, $password) . "\",  \"" . mysqli_real_escape_string($link, $phone) . "\")");
		
		if($query)
		{
			$array["code"] = "200";
		}

		mysqli_close($link);
	}
}

echo json_encode($array);
?>
