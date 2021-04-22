function display_amount(element){
	var id = element.id.split("-")[1];
	
	document.getElementById("button-"+id).style.display = "none";
	document.getElementById("form-"+id).style.display = "block";
	document.getElementById("close-"+id).style.display = "block";
	document.getElementById("message-donation").style.display = "block";
}

function close_amount(element){
	var id = element.id.split("-")[1];
	
	document.getElementById("button-"+id).style.display = "block";
	document.getElementById("form-"+id).style.display = "none";
	document.getElementById("close-"+id).style.display = "none";
}