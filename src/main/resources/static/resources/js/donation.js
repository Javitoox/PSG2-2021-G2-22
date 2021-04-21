function display_amount(element){
	var id = element.id.split("-")[1];
	
	document.getElementById("donation-state").style.display = "none";
	document.getElementById("donation-donate").style.display = "block";
	document.getElementById("button-"+id).style.display = "none";
	document.getElementById("form-"+id).style.display = "block";
	document.getElementById("form-"+id).style.float = "left";
	document.getElementById("close-"+id).style.display = "block";
}