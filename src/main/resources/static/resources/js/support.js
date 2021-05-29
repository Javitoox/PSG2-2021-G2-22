window.addEventListener("load", conf, false);

var rows;

var oJSON = {
	"operation": "core/get",
	"class": "Contact",
	"key": "SELECT Person",
	"output_fields": "friendlyname, email, phone"
};

function conf() {
	rows = $("#rows");
	$.ajax({
		type: "POST",
		url: "http://192.168.1.111/itop/webservices/rest.php?version=1.0",
		dataType: "json",
		data: {
			auth_user: "javmarfer3",
			auth_pwd: "Psg2020%",
			json_data: JSON.stringify(oJSON)
		},
		crossDomain: "true"
	}).then(
		function(data) {
			for (let contact of Object.values(data.objects)) {
				let tr = $("<tr>");
				tr.append($("<td>").text(contact.fields.friendlyname));
				tr.append($("<td>").text(contact.fields.email));
				tr.append($("<td>").text(contact.fields.phone));
				rows.append(tr);
			}
		}
	);
}