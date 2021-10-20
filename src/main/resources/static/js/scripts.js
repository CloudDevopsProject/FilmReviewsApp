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
	});
})

//Function below will display an alert to the user before adjusting another users role
function confirmUserRoleAdjustment(fullName, role) {
	//The adjustedTo value will be set as the opposite of whatever is passed as an argument
	if(role == "admin") {
		adjustedTo = "generic";
	} else {
		adjustedTo = "admin";
	}
	if(!confirm("Are you sure you want to change " + fullName + "'s role from " + role + " user to " + adjustedTo + " user")) {
		window.event.preventDefault();
	} 
}

function filterUsers(value) {
	//Convert the search value to lower case
	value = value.toLowerCase();
	let table = document.getElementById("usersTable");
	let tableBody = table.getElementsByTagName("tbody")[0];
	let tableRows = tableBody.getElementsByTagName("tr");
	let resultFound = false;
	//Loop all records in the table 
	for(i=0;i<tableRows.length;i++) {
		emailField = tableRows[i].getElementsByTagName("td")[2];
		if(emailField){
			//Convert all stored email addresses to lowercase
			let address = emailField.innerHTML.toLowerCase();
			if(address.indexOf(value) > -1) {
				tableRows[i].style.display = "";
				resultFound = true;
			} else {
				tableRows[i].style.display = "none";
			}
		}
	}
	//If there are no results matching the email address display that information
	if (resultFound == false) {
		let tableBodyNoResults = document.createElement("tbody");
		tableBodyNoResults.setAttribute('id', 'noUserMatches');
		let tableRow = document.createElement("tr");
		let tableDataBlock = document.createElement("td");
		tableDataBlock.innerHTML = "No Results found";
		tableRow.append(tableDataBlock);
		tableBodyNoResults.append(tableRow);
		table.append(tableBodyNoResults);	
	} else {
		//Remove display of no results if results have been found or if no search value is provided
		if(document.getElementById("noUserMatches")) {
			document.getElementById("noUserMatches").remove();
		}
	}
}