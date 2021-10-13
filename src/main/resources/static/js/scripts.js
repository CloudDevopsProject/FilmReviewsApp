$(document).ready(function() {
	
	//Prevent the registration form from being submitted if the passwords provided do not match
	$("#registrationForm").on("submit", function(event) {
		let password = $("#password").val();
		let passwordConfirm = $("#passwordConfirm").val();
		if (password == passwordConfirm) {
			return true;
		} else {
			$(".passwordError").css("color", "red");
			$(".passwordError").text("Passwords don't match");
			event.preventDefault();
		}
	})
	
})