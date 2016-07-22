<?php
error_reporting(E_ALL);
include "sql_data.php";

$array = array("code" => "403");

if (isset($_GET["email"]) && isset($_GET["password"]))
{

	$email = $_GET["email"];
	$password = hash("sha256", $_GET["password"]);

	if ($email != NULL && $password != NULL)
	{

		$link = mysqli_connect($sql_host, $sql_user, $sql_pass, $sql_db);
		if(!$link) die("Error while trying to connect to the database");

		$query = mysqli_query($link, "SELECT * FROM `gtbcn_users` WHERE `email` = '".mysqli_real_escape_string($link, $email)."' AND 			`password` = '".mysqli_real_escape_string($link, $password)."'");
		if(!$query) die("Error while communicating with the database");

		if (mysqli_affected_rows($link) != 0)
		{
	 		$array["code"] = "200";
		}

		mysqli_close($link);
		
	}
}

echo json_encode($array);		
?>
