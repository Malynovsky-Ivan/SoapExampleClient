function validateForm() {
	var length = document.forms["form"]["length"].value;
	var limit = document.forms["form"]["limit"].value;
	var regex = /^[0-9]+$/;
	if ((length !== "") && !length.match(regex)) {
		alert("Incorrect \"String lenght\" input. Must be positive number!");
		return false;
	}
	if ((limit !== "") && !limit.match(regex)) {
		alert("Incorrect \"Chars limit\" input. Must be positive number!");
		return false;
	}
}