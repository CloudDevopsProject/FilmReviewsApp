//Regular Expressions that will be used to validate forms
const lettersOnlyRegex = new RegExp("^.[A-z]*$");	//Check for only lower o upper case letters
const lettersNumbersAndSpacesOnlyRegex = new RegExp("^.[A-z0-9 ]*$")

$(document).ready(function() {		
	
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
			nameError = "Only letters can be accepted as inputs"
			//If there is an error for user's submitted first name
			if(!lettersOnlyRegex.test(firstName.val())) {
				displayFormError(firstName, nameError);	
			//Remove the error message if first name is valid 
			} else {
				removeFormError(firstName);
			}
			//If there is an error for user's submitted last name
			if(!lettersOnlyRegex.test(lastName.val())) {
				displayFormError(lastName, nameError);
			//Remove the error message if last name is valid 
			} else {
				removeFormError(lastName);
			}
			//If passwords provided don't match'
			if (password.val() != passwordConfirm.val()) {
				passwordError = "Passwords don't match'"
				displayFormError(password, passwordError);
			//Remove the error message if both passwords match after user changes values
			} else {
				removeFormError(password);
			}
		}
	});
	
	//Prevent the review form from being submitted if it hasn't been validated
	$("#reviewForm").on("submit", function(event) {
		//Get all form elements
		let movie = $("input[name = 'film']");
		let genre =  $("input[name = 'genre']");
		let director = $("input[name = 'director']");
		let actor = $("input[name = 'actor']");
		let textArea = $("textArea");
		
		//Submit form if all input element values match custom regex check to only allow letters, spaces and numbers
		if(lettersNumbersAndSpacesOnlyRegex.test(movie.val()) && lettersNumbersAndSpacesOnlyRegex.test(genre.val()) && lettersNumbersAndSpacesOnlyRegex.test(director.val())
				&& lettersNumbersAndSpacesOnlyRegex.test(actor.val()) && lettersNumbersAndSpacesOnlyRegex.test(textArea.val())) {
			return true;
		} else {
			event.preventDefault();
			errorMessage = "Only letters, numbers and spaces can be accepted as inputs";
			//If there is an error for movie name
			if(!lettersNumbersAndSpacesOnlyRegex.test(movie.val())) {
				displayFormError(movie, errorMessage);	
			} else {
				//Remove the error message if movie name is valid 
				removeFormError(movie);
			}	
			//If there is an error for genre
			if(!lettersNumbersAndSpacesOnlyRegex.test(genre.val())) {
				displayFormError(genre, errorMessage);	
			} else {
				//Remove the error message if genre is valid 
				removeFormError(genre);
			}
			//If there is an error for director
			if(!lettersNumbersAndSpacesOnlyRegex.test(director.val())) {
				displayFormError(director, errorMessage);	
			} else {
				//Remove the error message if director is valid
				removeFormError(director); 

			}
			//If there is an error for actor
			if(!lettersNumbersAndSpacesOnlyRegex.test(actor.val())) {
				displayFormError(actor, errorMessage);
			} else {
				//Remove the error message if actor is valid 
				removeFormError(actor); 
			}
			//If there is an error for textArea
			if(!lettersNumbersAndSpacesOnlyRegex.test(textArea.val())) {
				displayFormError(textArea, errorMessage);	 
			} else {
				removeFormError(); 
			}
		}
	})
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
		//Only append the result information if it isn't already displayed to avoid duplication of results details
		if(!document.getElementById("noUserMatches")) {
			tableRow.append(tableDataBlock);
			tableBodyNoResults.append(tableRow);
			table.append(tableBodyNoResults);	
		}
	} else {
		//Remove display of no results if results have been found or if no search value is provided
		if(document.getElementById("noUserMatches")) {
			document.getElementById("noUserMatches").remove();
		}
	}
}

function displayFormError(element, errorMessage) {
	//Avoid error duplication to check if error message is already present
	if(element.parent().children().last()[0].localName != "p") {
		let formError = document.createElement("p");
		formError.style.color = "red";
		formError.innerHTML = errorMessage;
		element.parent().append(formError);
	}	
}

function removeFormError(element) {
	if(element.parent().children().last()[0].localName == "p") {
		element.parent().children('p').remove();
	}
}


// Function below will display an add button if an option from the review form options isn't already in the database'
function checkOptionExists(element, modelType){
	let enteredVal = element.querySelector(".bs-searchbox input").value;
	//Only do the following steps if the value provided is not an empty string
	if (enteredVal != "") {
		let matchAmount = 0
		//Get the value entered and convert to lower case
		let lowerCaseInputValue = enteredVal.toLowerCase();
		//Get the elements select element and traverse its options
		let selectElement = element.querySelector("select"); 
		for(i=0;i<selectElement.length;i++) {
			//If the option already exists don't loop through the options
			let existingOption = selectElement.options[i].innerHTML.toLowerCase();
			if(existingOption.startsWith(lowerCaseInputValue)) {
				matchAmount++;
				break;		
			}
		}
		//If there are no matches in the database for the users search query, display a button providing an option to add the value
		if(!matchAmount > 0) {
			if(!document.getElementById("addButton")) {
				let addButton = document.createElement("button");
				addButton.setAttribute('id', 'addButton');
				addButton.setAttribute('class', 'btn');
				addButton.innerHTML = `Add ${enteredVal}`;
				element.append(addButton);
			} else {
				let addButton = document.getElementById("addButton");
				addButton.innerHTML = `Add ${enteredVal}`;
				addButton.setAttribute('type', 'button');
				//If the button is clicked invoke a function which adds the value to the database
				addButton.addEventListener("click", function() {
					addNewOption(modelType, enteredVal);
				})
			}			
		}
	} else {
		//If the button to add an option is displayed remove it as there is no input value provided
		if(document.getElementById("addButton")) {
			document.getElementById("addButton").remove();
		}
	}
}

function addNewOption(modelType, enteredVal){
	//Make sure the enteredVal is validated
	if(lettersNumbersAndSpacesOnlyRegex.test(enteredVal)) {
		//Create a form so it can be posted to the backend wit hthe data provided by the user
		let form = document.createElement("form");
		form.style.display = "none";
		let formAction = "add" + modelType;		
		form.setAttribute("action", formAction);
		form.setAttribute("method", "post");
		//Input details to be populated with the functions enteredVal argument
		//Get the page csrf token
		let csrfInput = document.getElementById("csrfInput");
		form.append(csrfInput);
		let input = document.createElement("input");
		input.setAttribute('name', 'valueToBeAdded');
		input.setAttribute('value', enteredVal);
		form.append(input);
		document.body.appendChild(form);
		form.submit();
	}
}
	



/*function autoFillReviewFormDetails(objectIdentifier){
	let form = document.getElementById("reviewForm");
	let genreField = document.getElementById("movieGenre");
}*/