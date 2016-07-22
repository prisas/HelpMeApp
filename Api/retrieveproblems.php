<?php
error_reporting(E_ALL);
include "sql_data.php";

$array = array("code" => "500", "result" => array());

$link = mysqli_connect($sql_host, $sql_user, $sql_pass, $sql_db);
if(!$link) die("Error while trying to connect to the database");

if (isset($_GET["id"]) && $_GET["id"] != NULL)
{
	$id = $_GET["id"];

	$query = mysqli_query($link, "SELECT * FROM `gtbcn_problems` WHERE `id` = '" . mysqli_real_escape_string($link, $id) . "'");
	if(!$query) die("Error while communicating with the database");

	$info = mysqli_fetch_array($query);
	
	if ($info != NULL)
	{
		$object["title"] = $info["title"];
		$object["description"] = $info["description"];
		$object["long"] = $info["long"];
		$object["lat"] = $info["lat"];
		$object["user_id"] = $info["user_id"];
		$object["image_uri"] = $info["image_uri"];

		$userid = $object["user_id"];

		$query = mysqli_query($link, "SELECT `name` FROM `gtbcn_users` WHERE `id` = '" . mysqli_real_escape_string($link, $userid) . 			"'");

		if(!$query) die("Error while communicating with the database");
		$info2 = mysqli_fetch_array($query);

		$object["name"] = $info2["name"];


		$array = array("code" => "200", "result" => $object);
	}
}
else
{
	
	$query = mysqli_query($link, "SELECT * FROM `gtbcn_problems` ORDER BY `id` DESC");
	if(!$query) die("Error while communicating with the database");

	$objects = array();
	$i = 0;
	while ($info = mysqli_fetch_array($query))
	{
		$objects[$i]["title"] = $info["title"];
		$objects[$i]["id"] = $info["id"];
		$i++;
	}

	$array = array("code" => "200", "result" => $objects);

}

echo json_encode($array);
?>
