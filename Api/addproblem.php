<?php
error_reporting(E_ALL);
include "sql_data.php";

$array = array("code" => "500");

if (isset($_GET["title"]) && isset($_GET["description"]) && isset($_GET["long"]) && isset($_GET["lat"]) && isset($_GET["user_id"]))
{
	$title = $_GET["title"];
	$description = $_GET["description"];
	$long = $_GET["long"];
	$lat = $_GET["lat"];
	$userid = $_GET["user_id"];



	if ($title != NULL && $description != NULL && $long != NULL && $lat != NULL && $userid != NULL)
	{

		$link = mysqli_connect($sql_host, $sql_user, $sql_pass, $sql_db);
		if(!$link) die("Error while trying to connect to the database");


		if (!isset($_GET["image_uri"]) || $_GET["image_uri"] == NULL)
		{
			$query = mysqli_query($link, "INSERT INTO `gtbcn_problems`(`title`, `description`, `long`, `lat`, `user_id`) 
			VALUES (\"" . mysqli_real_escape_string($link, $title) . "\", \"" . mysqli_real_escape_string($link, $description) . 				"\", \"" . mysqli_real_escape_string($link, $long) . "\", \"" . mysqli_real_escape_string($link, $lat) . "\",  \"" . 				mysqli_real_escape_string($link, $userid) . "\")");
		}
		else
		{
			$imageuri = $_GET["image_uri"];

			$query = mysqli_query($link, "INSERT INTO `gtbcn_problems`(`title`, `description`, `long`, `lat`, `user_id`, 				`image_uri`) VALUES (\"" . mysqli_real_escape_string($link, $title) . "\", \"" . mysqli_real_escape_string($link, 				$description) . "\", \"" . mysqli_real_escape_string($link, $long) . "\", \"" . mysqli_real_escape_string($link, 				$lat) . "\", \"" . mysqli_real_escape_string($link, $userid) . "\", \"" . mysqli_real_escape_string($link, $imageuri) . 			"\")");
		}
		
		if($query)
		{
			$array["code"] = "200";
		}

		mysqli_close($link);

	}


}

echo json_encode($array);
?>
