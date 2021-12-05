//Regular Expressions that will be used to validate forms
const lettersOnlyRegex = new RegExp("^.[A-z]*$");	//Check for only lower or upper case letters
const lettersAndNumberOnlyRegex = new RegExp("^.[A-z0-9]*$");
const lettersAndSpacesOnlyRegex = new RegExp("^.[A-z ]*$")
const lettersNumbersParenthesesAndSpacesOnlyRegex = new RegExp("^.[A-z0-9() ]*$")
const lettersNumbersParenthesesSpacesAndCertainCharactersOnlyRegex = new RegExp("^.[A-z0-9()?.,\n ]*$")

$(document).ready(function() {

	//Prevent the login form from being submitted if it hasn't been validated
	$("#loginForm").on("submit", function(event) {
		let password = $("input[name = 'password']");
		//If there are characters other than letters and numbers in the password provided, prevent the form from being submitted
		if (lettersAndNumberOnlyRegex.test(password.val())) {
			return true;
		} else {
			event.preventDefault();
			displayFormError(password, "Password can only contain letters and numbers");
		}
	})

	//Prevent the registration form from being submitted if it hasn't been validated
	$("#registrationForm").on("submit", function(event) {
		//Get all form elements
		let firstName = $("input[name = 'firstName']");
		let lastName = $("input[name = 'lastName']");
		let password = $("#password");
		let passwordConfirm = $("#passwordConfirm");

		//Submit form if passwords provided match and inputs provided passed regex tests
		if (password.val() == passwordConfirm.val() && (lettersOnlyRegex.test(firstName.val()) && lettersOnlyRegex.test(lastName.val()) && lettersAndNumberOnlyRegex.test(password.val()))) {
			return true;
		} else {
			//Display form validation error messages to the user
			event.preventDefault();
			nameError = "Only letters can be accepted as inputs"
			//If there is an error for user's submitted first name
			if (!lettersOnlyRegex.test(firstName.val())) {
				displayFormError(firstName, nameError);
				//Remove the error message if first name is valid 
			} else {
				removeFormError(firstName);
			}
			//If there is an error for user's submitted last name
			if (!lettersOnlyRegex.test(lastName.val())) {
				displayFormError(lastName, nameError);
				//Remove the error message if last name is valid 
			} else {
				removeFormError(lastName);
			}
			if(!lettersAndNumberOnlyRegex.test(password.val())) {
				displayFormError(password, "Password can only contain letters and numbers");
			}
			//If passwords provided don't match'
			if (password.val() != passwordConfirm.val()) {
				passwordError = "Passwords don't match"
				displayFormError(password, passwordError);
				//Remove the error message if both passwords match after user changes values
			} else {
				removeFormError(password);
			}
		}
	});

	//Prevent the review form from being submitted if it hasn't been validated
	$("#reviewForm").on("submit", function(event) {
		//Only the Film name and description area of the form need to be validated as they are the only inputs allowing free form text input
		let movie = $("input[name = 'film']");
		let textArea = $("textArea");
		//Submit form if text input values match custom regex checks
		if (lettersNumbersParenthesesAndSpacesOnlyRegex.test(movie.val()) && lettersNumbersParenthesesSpacesAndCertainCharactersOnlyRegex.test(textArea.val() && textArea.val() != " ")) {
			return true;
		} else {
			event.preventDefault();
			errorMessage = "Only letters, numbers, parentheses and spaces can be accepted as inputs";
			//If there is an error for movie name
			if (!lettersNumbersParenthesesAndSpacesOnlyRegex.test(movie.val())) {
				displayFormError(movie, errorMessage);
			} else {
				//Remove the error message if movie name is valid 
				removeFormError(movie);
			}
			//If there is an error for textArea
			if (!lettersNumbersParenthesesSpacesAndCertainCharactersOnlyRegex.test(textArea.val())) {
				displayFormError(textArea, "Allowed Values are letters, number, spaces, dots, commas, apostrophes and qustion marks");
			} else {
				removeFormError(textArea);
			}
		}
	})

	//Prevent the contact form from being submitted if it hasn't been validated
	$("#commentForm").on("submit", function(event) {
		//Assign the freeflowing form input fields to variables
		let name = $("input[name = 'name']");
		let comment = $("textArea");
		//Submit form if text input values match custom regex checks
		if (lettersOnlyRegex.test(name.val()) && lettersNumbersParenthesesAndSpacesOnlyRegex.test(comment.val())) {
			return true;
		} else {
			event.preventDefault();
			//If there is an error for name
			if (!lettersOnlyRegex.test(name.val())) {
				displayFormError(name.parent(), "Only letters and spaces can be accepted inputs");
			} else {
				//Remove the error message if name is valid 
				removeFormError(name.parent());
			}
			//If there is an error for comment 
			if (!lettersNumbersParenthesesAndSpacesOnlyRegex.test(comment.val())) {
				displayFormError(comment, "Only letters, numbers, parentheses and spaces can be accepted as inputs");
			} else {
				//Remove the error message if name is valid 
				removeFormError(comment);
			}
		}
	})
})

//Function below will display an alert to the user before adjusting another users role
function confirmUserRoleAdjustment(fullName, role) {
	//The adjustedTo value will be set as the opposite of whatever is passed as an argument
	if (role == "admin") {
		adjustedTo = "generic";
	} else {
		adjustedTo = "admin";
	}
	if (!confirm("Are you sure you want to change " + fullName + "'s role from " + role + " user to " + adjustedTo + " user")) {
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
	for (i = 0; i < tableRows.length; i++) {
		emailField = tableRows[i].getElementsByTagName("td")[2];
		if (emailField) {
			//Convert all stored email addresses to lowercase
			let address = emailField.innerHTML.toLowerCase();
			if (address.indexOf(value) > -1) {
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
		if (!document.getElementById("noUserMatches")) {
			tableRow.append(tableDataBlock);
			tableBodyNoResults.append(tableRow);
			table.append(tableBodyNoResults);
		}
	} else {
		//Remove display of no results if results have been found or if no search value is provided
		if (document.getElementById("noUserMatches")) {
			document.getElementById("noUserMatches").remove();
		}
	}
}

function displayFormError(element, errorMessage) {
	//Avoid error duplication to check if error message is already present
	if (element.parent().children().last()[0].localName != "p") {
		let formError = document.createElement("p");
		formError.style.color = "red";
		formError.innerHTML = errorMessage;
		element.parent().append(formError);
	}
}

function removeFormError(element) {
	if (element.parent().children().last()[0].localName == "p") {
		element.parent().children('p').remove();
	}
}


// Function below will display an add button if an option from the review form options isn't already in the database'
function checkOptionExists(element, modelType) {
	let enteredVal = element.querySelector(".bs-searchbox input").value;
	//If the entered value passed the regex checks, display a button allowing user to add to database
	if(lettersAndSpacesOnlyRegex.test(enteredVal)) {
		//Event listener will remove the button created if the focus on the input is removed
		element.addEventListener('focusout', function() {
			//Timeout required to allow time for button click to call it's method'
			setTimeout(
				function() {
					if (element.querySelector("#addButton")) {
						element.querySelector("#addButton").remove();
					}
				}, 5000
			)
		});
	
		//Only do the following steps if the value provided is not an empty string
		if (enteredVal != "") {
			let matchAmount = 0
			//Get the value entered and convert to lower case
			let lowerCaseInputValue = enteredVal.toLowerCase();
			//Get the elements select element and traverse its options
			let selectElement = element.querySelector("select");
			for (i = 0; i < selectElement.length; i++) {
				//If the option already exists don't loop through the options
				let existingOption = selectElement.options[i].innerHTML.toLowerCase();
				if (existingOption.startsWith(lowerCaseInputValue)) {
					matchAmount++;
					break;
				}
			}
			//If there are no matches in the database for the users search query, display a button providing an option to add the value
			if (!matchAmount > 0) {
				if (!document.getElementById("addButton")) {
					let addButton = document.createElement("button");
					addButton.setAttribute('id', 'addButton');
					addButton.setAttribute('class', 'btn');
					addButton.innerHTML = `Add ${enteredVal}`;
					//If the button is clicked invoke a function which adds the value to the database
					element.append(addButton);
				} else {
					let addButton = document.getElementById("addButton");
					addButton.innerHTML = `Add ${enteredVal}`;
					addButton.setAttribute('type', 'button');
					addButton.addEventListener("click", function() {
						addNewOption(modelType, enteredVal);
					})
				}
			}
			} else {
				//If the button to add an option is displayed remove it as there is no input value provided
				if (document.getElementById("addButton")) {
					document.getElementById("addButton").remove();
				}
			}
		} else {
			if(document.getElementById("addButton")) {
				document.getElementById("addButton").remove()
			}
		}
}

function addNewOption(modelType, enteredVal) {
	// Store details already entered in the form in session storage
	scrapeReviewForm();
	//Make sure the enteredVal is validated
	if (!lettersAndSpacesOnlyRegex.test(enteredVal)) {
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
		//form.submit();
	} else {
		if(document.getElementById("addButton")) {
			document.getElementById("addButton").remove();
		}
	}
}

// This method will store the users entered values for the review form in session storage so if they add new directors/actors etc they wont have to renter everything 
function scrapeReviewForm() {
	let filmName = document.querySelector("[name='film']").value;
	sessionStorage.setItem("filmName", filmName);
	// Issue with retrieveing the multiple select array values and auto selecting so only one value will be selected 
	let genre = document.querySelector("[name='genre']").value;
	sessionStorage.setItem("genre", genre);
	let director = document.querySelector("[name='director']").value;
	sessionStorage.setItem("director", director);
	let actor = document.querySelector("[name='actor']").value;
	sessionStorage.setItem("actor", actor);
	let releaseYear = document.querySelector("[name='releaseYear']").value;
	sessionStorage.setItem("releaseYear", releaseYear);
	let rating = document.querySelector("[name='rating']").value;
	sessionStorage.setItem("rating", rating);
}

//The method below will be used to populate the review forms using data stored in the session data
function populateForm() {
	document.querySelector("[name='film']").value = sessionStorage.getItem("filmName");
	document.querySelector("[name='genre']").value = sessionStorage.getItem("genre");
	document.querySelector("[name='director']").value = sessionStorage.getItem("director");
	document.querySelector("[name='actor']").value = sessionStorage.getItem("actor");
	document.querySelector("[name='releaseYear']").value = sessionStorage.getItem("releaseYear");
	document.querySelector("[name='rating']").value = sessionStorage.getItem("rating");
}



