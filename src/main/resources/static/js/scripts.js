$(document).ready(function() {
	
	//Regular Expressions that will be used to validate forms
	const lettersOnlyRegex = new RegExp("^.[A-z]*$");	//Check for only lower o upper case letters
	
	//Prevent the registration form from being submitted if it hasn't been validated
	$("#registrationForm").on("submit", function(event) {
		//Get all form elements
		let firstName = $("input[name = 'firstName']");
		let lastName =  $("input[name = 'lastName']");
		let password = $("#password");
		let passwordConfirm = $("#passwordConfirm");
		
		//Submit form if passwords provided match and users name only contains allowed characters
		if(password.val() == passwordConfirm.val() && (lettersOnlyRegex.test(firstName.val()) && lettersOnlyRegex.test(lastName.val()))) {
			return true;
		} else {
			//Display form validation error messages to the user
			event.preventDefault();
			//If there is an error for user's submitted first name
			if(!lettersOnlyRegex.test(firstName.val())) {
				//Avoid error duplication
				if(firstName.parent().children().last()[0].localName != "p") {
					let formError = document.createElement("p");
					formError.style.color = "red";
					formError.innerHTML = "Values entered must be between A-Z, a-z";
					firstName.parent().append(formError);
				}	
			//Remove the error message if first name is valid 
			} else {
				if(firstName.parent().children().last()[0].localName == "p") {
					firstName.parent().children('p').remove();
				}
			}
			//If there is an error for user's submitted last name
			if(!lettersOnlyRegex.test(lastName.val())) {
				//Avoid error duplication
				if(lastName.parent().children().last()[0].localName != "p") {
					let formError = document.createElement("p");
					formError.style.color = "red";
					formError.innerHTML = "Values entered must be between A-Z, a-z";
					lastName.parent().append(formError);	
				}
			//Remove the error message if last name is valid 
			} else {
				if(lastName.parent().children().last()[0].localName == "p") {
					lastName.parent().children('p').remove();
				}
			}
			//If passwords provided don't match'
			if (password.val() != passwordConfirm.val()) {
				//Avoid error duplication
				if(password.parent().children().last()[0].localName != "p") {
					let formError = document.createElement("p");
					formError.style.color = "red";
					formError.innerHTML = "Passwords must match'";
					password.parent().append(formError);
				}
			//Remove the error message if both passwords match after user changes values
			} else {
				if(password.parent().children().last()[0].localName == "p") {
					password.parent().children('p').remove();
				}
			}
		}
	})
})