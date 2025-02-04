function addTravelRow() {
    event.preventDefault(); // Prevent the default anchor behavior

    // Get the table body where new rows will be added
    const tableBody = document.getElementById('reimbursementsBody');

    // Get the first row as a template to clone
    const templateRow = document.querySelector('.template-row');

    if (templateRow) {
        // Clone the row
        const newRow = templateRow.cloneNode(true);

        // Reset the values of the inputs in the cloned row
        newRow.querySelectorAll('input, select, textarea').forEach(input => {
            if (input.type === 'checkbox' || input.tagName === 'SELECT') {
                input.selectedIndex = 0;
                input.checked = false;
            } else {
                input.value = '';
            }
        });

        // Replace the delete button with a delete image
        const deleteButton = newRow.querySelector('.remove-row');
        if (deleteButton) {
            // Replace the delete button with an image
            const deleteImage = document.createElement('img');
            deleteImage.src = 'img/delete-white.svg'; // Path to the delete image
            deleteImage.alt = 'Delete';
            deleteImage.style.cursor = 'pointer'; // Make it look clickable
            deleteImage.className = 'btn btn-danger btn-sm remove-row'; // Keep the same class for consistency

            // Add click event to the image
            deleteImage.addEventListener('click', function () {
                newRow.remove();
            });

            // Replace the button with the image
            deleteButton.parentElement.replaceChild(deleteImage, deleteButton);
        }

        // Add the cloned row to the table body
        tableBody.appendChild(newRow);
    }
}

// Add event listeners for existing delete buttons
document.querySelectorAll('.remove-row').forEach(button => {
    button.addEventListener('click', function () {
        // Get the row to be deleted
        const row = button.closest('tr');

        // Prevent deleting the first row
        const allRows = row.parentElement.children;
        if (row !== allRows[0]) {
            row.remove();
        } else {
            alert("You cannot delete the first row.");
        }
    });
});


function addTravelRowAcomodation() {
    event.preventDefault(); // Prevent the default anchor behavior

    // Get the table body where new rows will be added
    const tableBody = document.getElementById('reimbursementsBodyAccomodation');

    // Get the first row as a template to clone
    const templateRow = document.querySelector('.template-row-accomodation');

    if (templateRow) {
        // Clone the row
        const newRow = templateRow.cloneNode(true);

        // Reset the values of the inputs in the cloned row
        newRow.querySelectorAll('input, select, textarea').forEach(input => {
            if (input.type === 'checkbox' || input.tagName === 'SELECT') {
                input.selectedIndex = 0;
                input.checked = false;
            } else {
                input.value = '';
            }
        });

        // Replace the delete button with a delete image
        const deleteButton = newRow.querySelector('.remove-row-acomodation');
        if (deleteButton) {
            // Replace the delete button with an image
            const deleteImage = document.createElement('img');
            deleteImage.src = 'img/delete-white.svg'; // Path to the delete image
            deleteImage.alt = 'Delete';
            deleteImage.style.cursor = 'pointer'; // Make it look clickable
            deleteImage.className = 'btn btn-danger btn-sm remove-row-acomodation'; // Keep the same class for consistency

            // Add click event to the image
            deleteImage.addEventListener('click', function () {
                newRow.remove();
            });

            // Replace the button with the image
            deleteButton.parentElement.replaceChild(deleteImage, deleteButton);
        }

        // Add the cloned row to the table body
        tableBody.appendChild(newRow);
    }
}

// Add event listeners for existing delete buttons
document.querySelectorAll('.remove-row-acomodation').forEach(button => {
    button.addEventListener('click', function () {
        // Get the row to be deleted
        const row = button.closest('tr');

        // Prevent deleting the first row
        const allRows = row.parentElement.children;
        if (row !== allRows[0]) {
            row.remove();
        } else {
            alert("You cannot delete the first row.");
        }
    });
});



function addInCityCab() {
    event.preventDefault(); // Prevent the default anchor behavior

    // Get the table body where new rows will be added
    const tableBody = document.getElementById('reimbursementsBodyInCityCab');

    // Get the first row as a template to clone
    const templateRow = document.querySelector('.template-row-incitycab');

    if (templateRow) {
        // Clone the row
        const newRow = templateRow.cloneNode(true);

        // Reset the values of the inputs in the cloned row
        newRow.querySelectorAll('input, select, textarea').forEach(input => {
            if (input.type === 'checkbox' || input.tagName === 'SELECT') {
                input.selectedIndex = 0;
                input.checked = false;
            } else {
                input.value = '';
            }
        });

        // Replace the delete button with a delete image
        const deleteButton = newRow.querySelector('.remove-row-incitycab');
        if (deleteButton) {
            // Replace the delete button with an image
            const deleteImage = document.createElement('img');
            deleteImage.src = 'img/delete-white.svg'; // Path to the delete image
            deleteImage.alt = 'Delete';
            deleteImage.style.cursor = 'pointer'; // Make it look clickable
            deleteImage.className = 'btn btn-danger btn-sm remove-row-incitycab'; // Keep the same class for consistency

            // Add click event to the image
            deleteImage.addEventListener('click', function () {
                newRow.remove();
            });

            // Replace the button with the image
            deleteButton.parentElement.replaceChild(deleteImage, deleteButton);
        }

        // Add the cloned row to the table body
        tableBody.appendChild(newRow);
    }
}

// Add event listeners for existing delete buttons
document.querySelectorAll('.remove-row-incitycab').forEach(button => {
    button.addEventListener('click', function () {
        // Get the row to be deleted
        const row = button.closest('tr');

        // Prevent deleting the first row
        const allRows = row.parentElement.children;
        if (row !== allRows[0]) {
            row.remove();
        } else {
            alert("You cannot delete the first row.");
        }
    });
});


function addFood() {
    event.preventDefault(); // Prevent the default anchor behavior

    // Get the table body where new rows will be added
    const tableBody = document.getElementById('reimbursementsBodyFood');

    // Get the first row as a template to clone
    const templateRow = document.querySelector('.template-row-food');

    if (templateRow) {
        // Clone the row
        const newRow = templateRow.cloneNode(true);

        // Reset the values of the inputs in the cloned row
        newRow.querySelectorAll('input, select, textarea').forEach(input => {
            if (input.type === 'checkbox' || input.tagName === 'SELECT') {
                input.selectedIndex = 0;
                input.checked = false;
            } else {
                input.value = '';
            }
        });

        // Replace the delete button with a delete image
        const deleteButton = newRow.querySelector('.remove-row-food');
        if (deleteButton) {
            // Replace the delete button with an image
            const deleteImage = document.createElement('img');
            deleteImage.src = 'img/delete-white.svg'; // Path to the delete image
            deleteImage.alt = 'Delete';
            deleteImage.style.cursor = 'pointer'; // Make it look clickable
            deleteImage.className = 'btn btn-danger btn-sm remove-row-food'; // Keep the same class for consistency

            // Add click event to the image
            deleteImage.addEventListener('click', function () {
                newRow.remove();
            });

            // Replace the button with the image
            deleteButton.parentElement.replaceChild(deleteImage, deleteButton);
        }

        // Add the cloned row to the table body
        tableBody.appendChild(newRow);
    }
}

// Add event listeners for existing delete buttons
document.querySelectorAll('.remove-row-food').forEach(button => {
    button.addEventListener('click', function () {
        // Get the row to be deleted
        const row = button.closest('tr');

        // Prevent deleting the first row
        const allRows = row.parentElement.children;
        if (row !== allRows[0]) {
            row.remove();
        } else {
            alert("You cannot delete the first row.");
        }
    });
});



function addmiscellaneous() {
    event.preventDefault(); // Prevent the default anchor behavior

    // Get the table body where new rows will be added
    const tableBody = document.getElementById('reimbursementsBodyMiscellaneous');

    // Get the first row as a template to clone
    const templateRow = document.querySelector('.template-row-miscellaneous');

    if (templateRow) {
        // Clone the row
        const newRow = templateRow.cloneNode(true);

        // Reset the values of the inputs in the cloned row
        newRow.querySelectorAll('input, select, textarea').forEach(input => {
            if (input.type === 'checkbox' || input.tagName === 'SELECT') {
                input.selectedIndex = 0;
                input.checked = false;
            } else {
                input.value = '';
            }
        });

        // Replace the delete button with a delete image
        const deleteButton = newRow.querySelector('.remove-row-miscellaneous');
        if (deleteButton) {
            // Replace the delete button with an image
            const deleteImage = document.createElement('img');
            deleteImage.src = 'img/delete-white.svg'; // Path to the delete image
            deleteImage.alt = 'Delete';
            deleteImage.style.cursor = 'pointer'; // Make it look clickable
            deleteImage.className = 'btn btn-danger btn-sm remove-row-miscellaneous'; // Keep the same class for consistency

            // Add click event to the image
            deleteImage.addEventListener('click', function () {
                newRow.remove();
            });

            // Replace the button with the image
            deleteButton.parentElement.replaceChild(deleteImage, deleteButton);
        }

        // Add the cloned row to the table body
        tableBody.appendChild(newRow);
    }
}

// Add event listeners for existing delete buttons
document.querySelectorAll('.remove-row-miscellaneous').forEach(button => {
    button.addEventListener('click', function () {
        // Get the row to be deleted
        const row = button.closest('tr');

        // Prevent deleting the first row
        const allRows = row.parentElement.children;
        if (row !== allRows[0]) {
            row.remove();
        } else {
            alert("You cannot delete the first row.");
        }
    });
});

//////////////////////////////////////////////////////////////Advance Cash Travel Request script



function cashAdvanceSubmit(){
	
	   var employerid = document.getElementById("employerId").value.trim();
	   var empId = document.getElementById("empId").value.trim();
	   var cashDate = document.getElementById("cashDate").value.trim();
	   var cashExpenseTitle = document.getElementById("cashExpenseTitle").value.trim();
	   var cashCurrency = document.getElementById("cashCurrency").value.trim();
	   var cashAmmount = document.getElementById("cashAmmount").value.trim();
	   var cashModeOfPayment = document.getElementById("cashModeOfPayment").value.trim();
	   var cashRemark = document.getElementById("cashRemark").value.trim();
	   var employerName = document.getElementById("employerName").value.trim();
		document.querySelectorAll(".error-message").forEach(el => el.remove());
		
		var errorMessage = document.getElementById("error-message"); // Get error span

		  // Clear previous error messages
		  errorMessage.innerText = "";

		  // Validation
		  if (!employerid || !empId || !cashDate || !cashExpenseTitle || !cashCurrency || !cashAmmount || !cashModeOfPayment) {
		      errorMessage.innerText = "All fields are required. Please fill in all fields.";
		      return;
		  }

		  // Date validation (should not be empty)
		  if (!cashDate) {
		      errorMessage.innerText = "Please select a valid date.";
		      return;
		  }

		  // Amount validation (should be a positive number)
		  if (isNaN(cashAmmount) || parseFloat(cashAmmount) <= 0) {
		      errorMessage.innerText = "Please enter a valid amount greater than zero.";
		      return;
		  }

		  // Mode of payment validation
		  if (cashModeOfPayment !== "Credit Card" && cashModeOfPayment !== "Cash") {
		      errorMessage.innerText = "Please select a valid mode of payment.";
		      return;
		  }
		 
		document.getElementById("signinLoader").style.display="flex";				
		$.ajax({
			type: "POST",
			url: "/cashAdvanceRequest",
			data:{
				
				"employeeId":empId,
				"employerId":employerid,
				"username":employerName,
				"requestType":"Cash",
				"cashDate":cashDate,
				"approvedAmount":"",
				"currency":cashCurrency,
				"amount":cashAmmount,
				"modeOfPayment":cashModeOfPayment,
				"remarks":cashRemark,
				
			},
			success: function(data) {
				newData = data;
				var data1 = jQuery.parseJSON(newData);
				var data2 = data1.list;
				var modalfirst = document.getElementById("ModalExpensesSubmitted");
			    modalfirst.style.display = "block";
				document.getElementById("signinLoader").style.display="none";
			},
			error: function(e) {
				alert('Failed to fetch JSON data' + e);
			}
		});
}

function closeCashSuccess(){
	var modalfirst = document.getElementById("ModalExpensesSubmitted");
	modalfirst.style.display = "none";
	
	var modalfirst2 = document.getElementById("ModalCashAdvanceRequest");
	modalfirst2.style.display = "none";
	
	var modalfirst3 = document.getElementById("ModalChooseAdvanceRequest");
	modalfirst3.style.display = "none";
	
}

//////////////////////////////////////////////////////////////////////
function getTableDataTravel() {
	
	let allValid = true;
    document.querySelectorAll(".template-row").forEach(row => {
        if (!validateRowTravel(row)) {
            allValid = false;
        }
    });

    if (!allValid) {
         preventDefault();
        //alert("Please correct the errors before submitting.");
		return false;
    }
		
    const tableRows = document.querySelectorAll("#reimbursementsBody tr");
    let tableData = [];

    tableRows.forEach(row => {
        let rowData = {};

        // Get Mode (Select Dropdown)
        const modeSelect = row.querySelector('select[name="mode"]');
        if (modeSelect) {
            rowData.mode = modeSelect.value;
        }

        // Get To-Be-Booked-By (Select Dropdown)
        const bookedBySelect = row.querySelector('select[name="toBeBookedBy"]');
        if (bookedBySelect) {
            rowData.toBeBookedBy = bookedBySelect.value;
        }

        // Get Date (Input Field)
        const dateInput = row.querySelector('input[name="date"]');
        if (dateInput) {
            rowData.date = dateInput.value;
        }

        // Get Location (Departure & Arrival)
        const departureInput = row.querySelector('input[name="departure"]');
        const arrivalInput = row.querySelector('input[name="arrival"]');
        rowData.departure = departureInput ? departureInput.value : "";
        rowData.arrival = arrivalInput ? arrivalInput.value : "";

        // Get Preferred Time (Select Dropdowns)
        const preferredTimeSelects = row.querySelectorAll('td:nth-child(6) select');
        if (preferredTimeSelects.length > 0) {
            rowData.preferredTime = preferredTimeSelects[0].value;
            rowData.timePreference = preferredTimeSelects.length > 1 ? preferredTimeSelects[1].value : "";
        }

        // Get Carrier Details (Select & Input Field)
        const carrierSelect = row.querySelector('td:nth-child(7) select');
        const carrierDetailsInput = row.querySelector('input[name="carrierDetails"]');
        rowData.carrierClass = carrierSelect ? carrierSelect.value : "";
        rowData.carrierDetails = carrierDetailsInput ? carrierDetailsInput.value : "";

		const amount = row.querySelector('input[name="amount"]');
		rowData.amount = amount ? amount.value : "";

        // Get Mode of Payment (Select Dropdown)
        const paymentModeSelect = row.querySelector('td:nth-child(9) select');
        rowData.paymentMode = paymentModeSelect ? paymentModeSelect.value : "";

        // Get Remarks (Textarea)
        const remarksTextarea = row.querySelector('td:nth-child(9) textarea');
        rowData.remarks = remarksTextarea ? remarksTextarea.value : "";
		
		const travelSubType = row.querySelector('td:nth-child(9) input');
		rowData.travelSubType = travelSubType ? travelSubType.value : "";
		
		const hotelDetails="";
		rowData.hotelDetails=hotelDetails ? hotelDetails:"";
		const location="";
		rowData.location=location ?location:"";
		const checkoutDate="";
		rowData.checkoutDate=checkoutDate ? checkoutDate:"";
		const checkinDate="";
		rowData.checkinDate=checkinDate ? checkinDate:"";
		const type="";
		rowData.type=type ?type:"";
		const fromLocation="";
		rowData.fromLocation=fromLocation ? fromLocation:"";
		const toLocation="";
		rowData.toLocation=toLocation ?toLocation:"";
		const typeOfMeal="";
		rowData.typeOfMeal=typeOfMeal ?typeOfMeal:"";
		const startDate="";
		rowData.startDate = startDate ?startDate:"";
		const numberOfDays="";
		numberOfDays.numberOfDays =numberOfDays ?numberOfDays:"";;
				
        tableData.push(rowData);
    });

	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;
	document.getElementById("signinLoader").style.display="flex";
    console.log(tableData);
   $.ajax({
   		type: "POST",
   		url: "/travelAdvanceRequest",
		contentType: "application/json",
   		data:JSON.stringify({
   			
   			"employeeId":empId,
   			"employerId":employerid,
			"requestType":"Travel",
   			"travelReimbursement":tableData
   		}),
   		success: function(data) {
   			//newData = data;
   			//var data1 = jQuery.parseJSON(newData);
   			//var data2 = data1.list;
   			var modalfirst = document.getElementById("ModalConfirm");
   		    modalfirst.style.display = "block";
   			document.getElementById("signinLoader").style.display="none";
   		},
   		error: function(e) {
   			alert('Failed to fetch JSON data' + e);
   		}
   	});
   	
   }
   
   
   function validateRowTravel(row) {
       let isValid = true;
       let mode = row.querySelector('select[name="mode"]');
       let date = row.querySelector('input[name="date"]');
       let departure = row.querySelector('input[name="departure"]');
       let arrival = row.querySelector('input[name="arrival"]');

       // Clear previous errors
       row.querySelectorAll(".error-msg").forEach(el => el.remove());

       if (!mode.value) {
           showError(mode, "Mode is required");
           isValid = false;
       }

       if (!date.value || new Date(date.value) < new Date()) {
           showError(date, "Please select a valid future date");
           isValid = false;
       }

       if (!departure.value.trim()) {
           showError(departure, "Departure location is required");
           isValid = false;
       }

       if (!arrival.value.trim()) {
           showError(arrival, "Arrival location is required");
           isValid = false;
       }
	   if (departure.value.trim().toLowerCase() === arrival.value.trim().toLowerCase()) {
	              showError(arrival, "Arrival location cannot be the same as Departure");
	              isValid = false;
	          }
       return isValid;
   }

   function showError(element, message) {
       let error = document.createElement("div");
       error.className = "error-msg";
       error.style.color = "red";
       error.style.fontSize = "12px";
       error.innerText = message;
       element.parentNode.appendChild(error);
   }

   document.querySelectorAll(".template-row").forEach(row => {
       row.addEventListener("change", function () {
           validateRowTravel(row);
       });
   });
/////////////////////////////////////////////////////////////////////////
function getaddTableRowAcomodation(){
	
	if (!validateTable()) {
	     preventDefault();
		  // Prevent form submission if validation fails
		  return false;
	 }
	const tableBody = document.getElementById("reimbursementsBodyAccomodation");
	const rows = tableBody.querySelectorAll("tr");
	let tableData = [];

	rows.forEach(row => {
	    let rowData = {};

	    // Get checkbox state
	    const checkbox = row.querySelector('input[type="checkbox"]');
	    rowData["selected"] = checkbox ? checkbox.checked : false;

	    // Get selected option from Type dropdown
	    const typeSelect = row.querySelector('select[name="mode"]');
	    rowData["type"] = typeSelect ? typeSelect.value : "";

	    // Get selected option from To-Be-Booked-By dropdown
	    const bookedBySelect = row.querySelector('select[name="toBeBookedBy"]');
	    rowData["toBeBookedBy"] = bookedBySelect ? bookedBySelect.value : "";

	    // Get dates
	    const checkinDate = row.querySelector('input[name="checkinDate"]');
	    rowData["checkinDate"] = checkinDate ? checkinDate.value : "";

	    const checkoutDate = row.querySelector('input[name="checkoutDate"]');
	    rowData["checkoutDate"] = checkoutDate ? checkoutDate.value : "";

	    // Get location input
	    const locationInput = row.querySelector('input[id="accommodationDepartureFrom"]');
	    rowData["location"] = locationInput ? locationInput.value : "";

	    // Get hotel details input
	    const hotelDetailsInput = row.querySelector('input[id="accommodationDetails"]');
	    rowData["hotelDetails"] = hotelDetailsInput ? hotelDetailsInput.value : "";

	    // Get preference dropdown value
	    const preferenceSelect = row.querySelector('select[id="accommodationPreference"]');
	    rowData["preferredTime"] = preferenceSelect ? preferenceSelect.value : "";
		
		const amount = row.querySelector('input[id="accomodationAmount"]');
		rowData["amount"] = amount ? amount.value : "";
	    // Get mode of payment
	    const paymentSelect = row.querySelector('select[id="accommodationAmountPayment"]');
	    rowData["paymentMode"] = paymentSelect ? paymentSelect.value : "";

	    // Get remarks textarea value
	    const remarksTextarea = row.querySelector('textarea[id="accommodationRemarks"]');
	    rowData["remarks"] = remarksTextarea ? remarksTextarea.value : "";

		const travelSubType = row.querySelector('td:nth-child(9) input');
		rowData.travelSubType = travelSubType ? travelSubType.value : "";
       tableData.push(rowData);
	   });

	   var employerid = document.getElementById("employerId").value;
	   var empId = document.getElementById("empId").value;
	   document.getElementById("signinLoader").style.display="flex";
	
       console.log(tableData);
   
	  $.ajax({
	  		type: "POST",
	  		url: "/travelAdvanceRequest",
		contentType: "application/json",
	  		data:JSON.stringify({
	  			
	  			"employeeId":empId,
	  			"employerId":employerid,
			    "requestType":"Accomodation",
	  			"travelReimbursement":tableData
	  		}),
	  		success: function(data) {
	  			var modalfirst = document.getElementById("ModalConfirm");
	  		    modalfirst.style.display = "block";
	  			document.getElementById("signinLoader").style.display="none";
	  		},
	  		error: function(e) {
	  			alert('Failed to fetch JSON data' + e);
	  		}
	  	});
	  	
}

    function showError(input, message) {
        let errorSpan = input.parentElement.querySelector(".error-message");
        if (!errorSpan) {
            errorSpan = document.createElement("span");
            errorSpan.classList.add("error-message");
            errorSpan.style.color = "red";
            errorSpan.style.display = "block";
            errorSpan.style.fontSize = "12px";
            input.parentElement.appendChild(errorSpan);
        }
        errorSpan.textContent = message;
    }

    function clearErrors(row) {
        row.querySelectorAll(".error-message").forEach(error => error.remove());
    }

    function validateTable() {
        let isValid = true;
        let rows = document.querySelectorAll("#reimbursementsBodyAccomodation .template-row-accomodation");

        rows.forEach((row) => {
            clearErrors(row);

            let checkin = row.querySelector("#accommodationCheckinDate");
            let checkout = row.querySelector("#accommodationCheckoutDate");
            let location = row.querySelector("#accommodationDepartureFrom");
            let hotelDetails = row.querySelector("#accommodationDetails");
            let paymentMode = row.querySelector("#accommodationAmountPayment");

            if (!checkin.value) {
                showError(checkin, "Check-in date is required.");
                isValid = false;
            }

            if (!checkout.value) {
                showError(checkout, "Check-out date is required.");
                isValid = false;
            }

            if (checkin.value && checkout.value && new Date(checkin.value) >= new Date(checkout.value)) {
                showError(checkout, "Check-out date must be after Check-in date.");
                isValid = false;
            }

            if (!location.value.trim()) {
                showError(location, "Location is required.");
                isValid = false;
            }

            if (!hotelDetails.value.trim()) {
                showError(hotelDetails, "Hotel details are required.");
                isValid = false;
            }

            if (!paymentMode.value || paymentMode.value === "NA") {
                showError(paymentMode, "Please select a valid mode of payment.");
                isValid = false;
            }
        });

        return isValid;
    }

    document.querySelectorAll(".remove-row-acomodation").forEach((btn) => {
        btn.addEventListener("click", function () {
            this.closest("tr").remove();
        });
    });
///////////////////////////////////////////////////////////

function getTableDataIncity() {
    const rows2 = document.querySelectorAll(".template-row-incitycab");

    // Function to show error message without duplication
    function showError(input, message) {
        let errorDiv = input.parentNode.querySelector(".error-message"); // Look inside parent
        if (!errorDiv) {
            errorDiv = document.createElement("div");
            errorDiv.className = "error-message text-danger";
            input.parentNode.appendChild(errorDiv);
        }
        errorDiv.innerText = message;
        input.classList.add("is-invalid");
    }

    // Function to remove error message only when field is corrected
    function removeError(input) {
        let errorDiv = input.parentNode.querySelector(".error-message"); // Look inside parent
        if (input.value.trim() !== "" && errorDiv) {
            errorDiv.remove();
            input.classList.remove("is-invalid");
        }
    }

    // Validation function
    function validateRow(row2) {
        let isValid = true;

        const dateInput = row2.querySelector("#cabsDate");
        const timeInput = row2.querySelector("#cabsTime");
        const fromInput = row2.querySelector("#cabsFrom");
        const toInput = row2.querySelector("#cabsTo");
        const paymentMode = row2.querySelector("#cabsPayment");
        const bookedBy = row2.querySelector("#cabsBookedBy");

        // Date Validation (Cannot be in the past)
        const today = new Date().toISOString().split("T")[0];
        if (dateInput.value < today) {
            showError(dateInput, "Date cannot be in the past.");
            isValid = false;
        } else {
            removeError(dateInput);
        }

        // Time Validation (HH:mm format)
        const timePattern = /^([01]\d|2[0-3]):([0-5]\d)$/;
        if (!timePattern.test(timeInput.value)) {
            showError(timeInput, "Invalid time format (HH:mm required).");
            isValid = false;
        } else {
            removeError(timeInput);
        }

        // Location Validation
        if (fromInput.value.trim() === "") {
            showError(fromInput, "From location is required.");
            isValid = false;
        } else {
            removeError(fromInput);
        }

        if (toInput.value.trim() === "") {
            showError(toInput, "To location is required.");
            isValid = false;
        } else {
            removeError(toInput);
        }

        // Prevent "From" and "To" from being the same
        if (fromInput.value.trim() !== "" && toInput.value.trim() !== "" && fromInput.value.trim().toLowerCase() === toInput.value.trim().toLowerCase()) {
            showError(toInput, "From and To locations cannot be the same.");
            showError(fromInput, "From and To locations cannot be the same.");
            isValid = false;
        } else {
            removeError(toInput);
            removeError(fromInput);
        }

        // Payment Mode Validation
        if (bookedBy.value === "self" && paymentMode.value === "NA") {
            showError(paymentMode, "Payment mode cannot be 'NA' when booked by self.");
            isValid = false;
        } else {
            removeError(paymentMode);
        }

        return isValid;
    }

    // Real-time validation (Prevent error duplication)
    rows2.forEach(row => {
        row.querySelectorAll("input, select").forEach(input => {
            input.addEventListener("input", function () {
                validateRow(row);
            });
        });
    });

    let allValid = true;
    rows2.forEach(row => {
        if (!validateRow(row)) {
            allValid = false;
        }
    });

    if (!allValid) {
        event.preventDefault(); // Prevent form submission if validation fails
        return;
    }

    const tableBody = document.getElementById('reimbursementsBodyInCityCab');
    const rows = tableBody.querySelectorAll('tr');
    let tableData = [];

    rows.forEach(row => {
        let rowData = {};

        rowData.checked = row.querySelector('input[type="checkbox"]')?.checked || false;
        rowData.mode = row.querySelector('#cabsByMode')?.value || '';
        rowData.toBeBookedBy = row.querySelector('#cabsBookedBy')?.value || '';
        rowData.date = row.querySelector('#cabsDate')?.value || '';
        rowData.timePreference = row.querySelector('#cabsTime')?.value || '';
        rowData.fromLocation = row.querySelector('#cabsFrom')?.value || '';
        rowData.toLocation = row.querySelector('#cabsTo')?.value || '';
		rowData.amount = row.querySelector('#cabAmount')?.value || '';
        rowData.paymentMode = row.querySelector('#cabsPayment')?.value || '';
        rowData.remarks = row.querySelector('#cabsRemarks')?.value || '';

        tableData.push(rowData);
    });

    var employerid = document.getElementById("employerId").value;
    var empId = document.getElementById("empId").value;
    document.getElementById("signinLoader").style.display = "flex";

    console.log(tableData);

    $.ajax({
        type: "POST",
        url: "/travelAdvanceRequest",
        contentType: "application/json",
        data: JSON.stringify({
            "employeeId": empId,
            "employerId": employerid,
            "requestType": "In-City-Cab",
            "travelReimbursement": tableData
        }),
        success: function (data) {
            var modalfirst = document.getElementById("ModalConfirm");
            modalfirst.style.display = "block";
            document.getElementById("signinLoader").style.display = "none";
        },
        error: function (e) {
            alert('Failed to fetch JSON data' + e);
        }
    });
}


///////////////////////////////////////////////

function getTableDataFood(){
	       let isTableValid = true;
	        const rows = document.querySelectorAll(".template-row-food");

	        rows.forEach(row => {
	            if (!validateRow(row)) {
	                isTableValid = false;
	            }
	        });

	        if (!isTableValid) {
				return false;
	        }

		const tableRows = document.querySelectorAll("#reimbursementsBodyFood tr");
		    let tableData = [];
	
		    tableRows.forEach(row => {
		        let rowData = {
		            typeOfMeal: row.querySelector("#typeOfMeals")?.value || "",
		            startDate: row.querySelector("#mealDate")?.value || "",
		            numberOfDays: row.querySelector("#noOfDays")?.value || "",
		            location: row.querySelector("#mealLocation")?.value || "",
					amount: row.querySelector("#foodAmount")?.value || "",
		            modeOfPayment: row.querySelector("#travelAmountPayment")?.value || "",
		            remark: row.querySelector("#travelRemarks")?.value || "",
		            checked: row.querySelector("input[type='checkbox']")?.checked || false
		        };
	
		        tableData.push(rowData);
		    });

		   var employerid = document.getElementById("employerId").value;
		   var empId = document.getElementById("empId").value;
		   document.getElementById("signinLoader").style.display="flex";

		    console.log(tableData);

		  $.ajax({
		  		type: "POST",
		  		url: "/travelAdvanceRequest",
			contentType: "application/json",
		  		data:JSON.stringify({
		  			
		  			"employeeId":empId,
		  			"employerId":employerid,
				    "requestType":"Meal",
		  			"travelReimbursement":tableData
		  		}),
		  		success: function(data) {
		  			var modalfirst = document.getElementById("ModalConfirm");
		  		    modalfirst.style.display = "block";
		  			document.getElementById("signinLoader").style.display="none";
		  		},
		  		error: function(e) {
		  			alert('Failed to fetch JSON data' + e);
		  		}
		  	});
	
}
function showErrorMessage(element, message) {
       let errorSpan = element.parentElement.querySelector(".error-message");
       if (!errorSpan) {
           errorSpan = document.createElement("span");
           errorSpan.className = "error-message";
           errorSpan.style.color = "red";
           errorSpan.style.fontSize = "12px";
           element.parentElement.appendChild(errorSpan);
       }
       errorSpan.textContent = message;
   }

   function clearErrorMessage(element) {
       let errorSpan = element.parentElement.querySelector(".error-message");
       if (errorSpan) {
           errorSpan.remove();
       }
   }

   function validateRow(row) {
       let isValid = true;

       const mealType = row.querySelector("#typeOfMeals");
       const mealDate = row.querySelector("#mealDate");
       const noOfDays = row.querySelector("#noOfDays");
       const mealLocation = row.querySelector("#mealLocation");
       const paymentMode = row.querySelector("#travelAmountPayment");

       if (!mealType.value) {
           isValid = false;
           showErrorMessage(mealType, "Meal type is required.");
       } else {
           clearErrorMessage(mealType);
       }

       if (!mealDate.value) {
           isValid = false;
           showErrorMessage(mealDate, "Meal date is required.");
       } else {
           const today = new Date().toISOString().split("T")[0]; // Get today's date
           if (mealDate.value < today) {
               isValid = false;
               showErrorMessage(mealDate, "Meal date cannot be in the past.");
           } else {
               clearErrorMessage(mealDate);
           }
       }

       if (!noOfDays.value) {
           isValid = false;
           showErrorMessage(noOfDays, "Number of days is required.");
       } else {
           clearErrorMessage(noOfDays);
       }

       if (!mealLocation.value.trim()) {
           isValid = false;
           showErrorMessage(mealLocation, "Location is required.");
       } else {
           clearErrorMessage(mealLocation);
       }

       if (!paymentMode.value) {
           isValid = false;
           showErrorMessage(paymentMode, "Payment mode is required.");
       } else {
           clearErrorMessage(paymentMode);
       }

       return isValid;
   }

   
//////////////////////////////////////////////
function getTableDataMiscellaneous(){
	
	let isTableValid = true;
	       const rows = document.querySelectorAll(".template-row-miscellaneous");

	       rows.forEach(row => {
	           if (!validateRow(row)) {
	               isTableValid = false;
	           }
	       });

	       if (!isTableValid) {
	          return false;
	       }

		const tableRows = document.querySelectorAll("#reimbursementsBodyFood tr");
		let tableData = [];
		    tableRows.forEach(row => {
		        let rowData = {
		            title: row.querySelector("#miscTitle")?.value || "",
		            toBeBookedBy: row.querySelector("#miscBookedBy")?.value || "",
		            date: row.querySelector("#miscDate")?.value || "",
		            amount: row.querySelector("#miscAmount")?.value || "",
		            modeOfPayment: row.querySelector("#travelAmountPayment")?.value || "",
		            remark: row.querySelector("#travelRemarks")?.value || "",
		            checked: row.querySelector("input[type='checkbox']")?.checked || false
		        };
	
		        tableData.push(rowData);
		    });
		   var employerid = document.getElementById("employerId").value;
		   var empId = document.getElementById("empId").value;
		   document.getElementById("signinLoader").style.display="flex";
		   console.log(tableData);
		  $.ajax({
		  		type: "POST",
		  		url: "/travelAdvanceRequest",
			contentType: "application/json",
		  		data:JSON.stringify({
		  			
		  			"employeeId":empId,
		  			"employerId":employerid,
				    "requestType":"Miscellaneous",
		  			"travelReimbursement":tableData
		  		}),
		  		success: function(data) {
		  			var modalfirst = document.getElementById("ModalConfirm");
		  		    modalfirst.style.display = "block";
		  			document.getElementById("signinLoader").style.display="none";
		  		},
		  		error: function(e) {
		  			alert('Failed to fetch JSON data' + e);
		  		}
		  	});
	
}

function showErrorMessage(element, message) {
      let errorSpan = element.parentElement.querySelector(".error-message");
      if (!errorSpan) {
          errorSpan = document.createElement("span");
          errorSpan.className = "error-message";
          errorSpan.style.color = "red";
          errorSpan.style.fontSize = "12px";
          element.parentElement.appendChild(errorSpan);
      }
      errorSpan.textContent = message;
  }

  function clearErrorMessage(element) {
      let errorSpan = element.parentElement.querySelector(".error-message");
      if (errorSpan) {
          errorSpan.remove();
      }
  }

  function validateRow(row) {
      let isValid = true;

      const typeOfMeals = row.querySelector("#typeOfMeals");
      const miscBookedBy = row.querySelector("#miscBookedBy");
      const mealDate = row.querySelector("#mealDate");
      const foodAmount = row.querySelector("#foodAmount");
      const foodPayment = row.querySelector("#foodPayment");
      const remarks = row.querySelector("#foodRemarks");

      if (!typeOfMeals.value.trim()) {
          isValid = false;
          showErrorMessage(typeOfMeals, "Title is required.");
      } else {
          clearErrorMessage(typeOfMeals);
      }

     /* if (!miscBookedBy.value) {
          isValid = false;
          showErrorMessage(miscBookedBy, "Please select who will book.");
      } else {
          clearErrorMessage(miscBookedBy);
      }*/

      if (!mealDate.value) {
          isValid = false;
          showErrorMessage(mealDate, "Date is required.");
      } else {
          const today = new Date().toISOString().split("T")[0]; // Get today's date
          if (mealDate.value < today) {
              isValid = false;
              showErrorMessage(mealDate, "Date cannot be in the past.");
          } else {
              clearErrorMessage(mealDate);
          }
      }

      if (!foodAmount.value.trim() || isNaN(foodAmount.value) || parseFloat(foodAmount.value) <= 0) {
          isValid = false;
          showErrorMessage(foodAmount, "Valid amount is required.");
      } else {
          clearErrorMessage(foodAmount);
      }

      if (!foodPayment.value) {
          isValid = false;
          showErrorMessage(foodPayment, "Payment mode is required.");
      } else {
          clearErrorMessage(foodPayment);
      }

      if (!remarks.value.trim()) {
          isValid = false;
          showErrorMessage(remarks, "Remarks are required.");
      } else {
          clearErrorMessage(remarks);
      }

      return isValid;
  }
  
////////////////////////////////////////
function ReviewandSybmit(){
	
	    document.getElementById("signinLoader").style.display="flex";
		var employerid = document.getElementById("employerId").value;
		var empId = document.getElementById("empId").value;	
		$.ajax({
		type: "GET",
		url: "/getTravelReviewData",
		data:{
			"employeeId":empId,
			"employerId":employerid,
			"status":0
			},
		        success:function(data){
		       // var data1 = data;
		       console.log(data);
			   var data1 = jQuery.parseJSON(data);
				
				var travelReimbursement = data1.data.travelReimbursement;
				var mealReimbursement = data1.data.mealReimbursement;
				var miscellaneousReimbursement = data1.data.miscellaneousReimbursement;
				var inCityCabReimbursement = data1.data.inCityCabReimbursement;
				var accomodationReimbursement = data1.data.accomodationReimbursement;
				document.getElementById("signinLoader").style.display="none";
				
				if(data1.status==true){
					const tableBody = document.querySelector("#travelTableViewMode tbody");
					tableBody.innerHTML = "";
		           data1.data.travelReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
						   <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td><input type="date" class="form-control" value="${item.date}"></td>
		                   <td>
						<input type="text"class="form-control mb-2" value="${item.departure}">
						<input type="text" class="form-control mb-2" value="${item.arrival}">
						</td>
		                   <td>
		                       <select class="form-control">
		                           <option value="6 am - 12 pm" ${item.preferredTime === "6 am - 12 pm" ? "selected" : ""}>6 am - 12 pm</option>
		                           <option value="Before 6 am">Before 6 am</option>
		                           <option value="12 pm - 6 pm">12 pm - 6 pm</option>
		                           <option value="After 6 pm">After 6 pm</option>
		                       </select>
		                   </td>
		                   <td><input type="text" class="form-control" value="${item.carrierDetails}"></td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>								    
							    <div class="mt-0" >
							        <span class="text-muted">Limit:</span>
							        <span 
							            class="border rounded px-3 py-1 bg-gray-200" 
							            style="border-color: darkgray; display: inline-block;">
							            INR 8,000
							        </span>
							    </div>
		                   </td>
		                   <td><textarea class="form-control">${item.remarks}</textarea></td>
						   <td></td>
		               `;
		               tableBody.appendChild(row);
		           });
					//////////////////////////////////////////
					
					const tableBodyAccomodation = document.querySelector("#accommodationTableViewMode tbody");
					tableBodyAccomodation.innerHTML = "";
		            data1.data.accomodationReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="train" ${item.type === "Hotels" ? "selected" : ""}>Hotels</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td>
						      <label for="accommodationCheckinDate">Check-in</label>
						      <input type="date" class="form-control" value="${item.checkinDate}">
						      <label for="accommodationCheckoutDate">Check-out</label>
						      <input type="date" class="form-control" value="${item.checkoutDate}">
						   </td>
		                   <td>
							<input type="text" class="form-control" value="${item.location}">
							</td>
		                   <td>
		                      <input type="text" class="form-control" value="${item.hotelDetails}">
		                   </td>
		                   <td><input type="text" class="form-control" value="${item.preferredTime}"></td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						  
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>
							   <div class="mt-0">
						        <span class="text-muted">Limit:</span>
						        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
						            INR 8,000
						        </span>
						    </div>
		                   </td>
		                   <td><textarea class="form-control">${item.remarks}</textarea></td>
		               `;
		               tableBodyAccomodation.appendChild(row);
		           });
					//////////////////////////////////////////////////////
					const tableBodyInCityCab = document.querySelector("#inCityCabsTableView tbody");
					tableBodyInCityCab.innerHTML = "";
		            data1.data.accomodationReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select  class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td>
						      <input type="date" class="form-control" value="${item.date}">
						      
						   </td>
		                 
		                   <td>
						   <input type="text" name="location" placeholder="From" 
						   	 class="form-control mb-2" id="cabsFrom" style="margin-top: 10px;" value="${item.preferredTime}">
		                   </td>
		                   <td>
						   <div style="margin-bottom: -5px;">
							<input type="text" name="location" placeholder="From" 
							 class="form-control mb-2" id="cabsFrom" style="margin-top: 10px;" value="${item.fromLocation}">
							 <br>
							 <input type="text" name="location" placeholder="To" value="${item.toLocation}"
							 class="form-control mb-2" id="cabsTo">
						   </td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>
							   <div class="mt-0">
						        <span class="text-muted">Limit:</span>
						        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
						            INR 8,000
						        </span>
						    </div>
		                   </td>
		                   <td><textarea class="border rounded px-2 py-1 w-full" style="width: 120%; height: 100px;" >${item.remarks}</textarea></td>
						   <td></td>
		               `;
		               tableBodyInCityCab.appendChild(row);
		           });
				   //////////////////////////////////////////////////
				   const foodTableView = document.querySelector("#foodTableView tbody");
				   foodTableView.innerHTML = "";
	   	            data1.data.mealReimbursement.forEach(item => {
	   	               const row = document.createElement("tr");
	   	               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
	   	                   <td>
	   	                       <select class="form-control typeOfMeals">
	   	                           <option value="train" ${item.typeOfMeal === "Daily Meals" ? "selected" : ""}>Daily Meals</option>
	   	                           <option value="bus" ${item.typeOfMeal === "Weekly Meals" ? "selected" : ""}>Weekly Meals</option>
	   	                           <option value="flights" ${item.typeOfMeal === "Monthly Meals" ? "selected" : ""}>Monthly Meals</option>
	   	                       </select>
							   <div class="error-message" id="error-typeOfMeals"></div>
	   	                   </td>
	   	                  
	   	                   <td>
	   					      <input type="date" class="form-control mealDate" value="${item.date}">
	   					      
	   					   </td>
	   	                   <td>
		   					<input type="text" class="form-control noOfDays" value="${item.numberOfDays}">
							<div class="error-message" id="error-noOfDays"></div>
	   					</td>
	   	                   <td>
	   	                      <input type="text" class="form-control" value="${item.location}">
							  <div class="error-message" id="error-mealLocation"></div>
	   	                   </td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
	   	                   <td>
	   					   <div style="margin-bottom: 28px;">
	   	                       <select class="form-control">
	   	                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
	   	                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
	   	                       </select>
	   						   </div>
	   						   <div class="mt-0">
	   					        <span class="text-muted">Limit:</span>
	   					        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
	   					            INR 8,000
	   					        </span>
	   					    </div>
	   	                   </td>
	   	                   <td><textarea class="border rounded px-2 py-1 w-full" style="width: 130%; height: 100px;" >${item.remarks}</textarea></td>
						   <td></td>
						   <td></td>
						   <td></td>
						   <td></td>
	   	               `;
	   	               foodTableView.appendChild(row);
	   	           });
					///////////////////////////////////////////////////////
					const miscellaneousTableView = document.querySelector("#miscellaneousTableView tbody");
					miscellaneousTableView.innerHTML = "";
	   	            data1.data.miscellaneousReimbursement.forEach(item => {
	   	               const row = document.createElement("tr");
	   	               row.innerHTML = `
					       <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
	   	                   <td>
						   
						   <input type="text" name="" placeholder="Product Samples - 10 Pieces" 
						   	 class="form-control mb-2" id="miscTitle">
	   	                       
	   	                   </td>
	   	                   <td>
	   	                       <select class="form-control">
	   	                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
	   	                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
	   	                       </select>
	   	                   </td>
	   	                   <td>
	   					      <input type="date" class="form-control" value="${item.date}">
	   					      
	   					   </td>
	   	                   <td>
	   					<input type="text" class="form-control mb-2" value="${item.amount}">
	   					</td>
						</td>
		                <td>  
		                </td>
		                <td>
		                </td>
	   	                   <td>
	   					   <div style="margin-bottom: 28px;">
	   	                       <select class="form-control">
	   	                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
	   	                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
	   	                       </select>
	   						   </div>
	   						   <div class="mt-0">
	   					        <span class="text-muted">Limit:</span>
	   					        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
	   					            INR 8,000
	   					        </span>
	   					    </div>
	   	                   </td>
	   	                   <td><textarea  class="border rounded px-2 py-1 w-full" placeholder="Add Remarks (Optional)"
						   	style="width: 100%; height: 100px;" >${item.remarks}</textarea></td>
							<td></td>
	   	               `;
	   	               miscellaneousTableView.appendChild(row);
	   	           });
				   
				   var modalfirst = document.getElementById("travelDetailsTable1");
				   modalfirst.style.display = "block";
				}
				else if(data1.status==false){
				document.getElementById("signinLoader").style.display="none";
			
			}
	     },
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});

}


function closeReviewEdit(){
	var modalfirst = document.getElementById("travelDetailsTable1");
	modalfirst.style.display = "none";
}

/////////////////////////////////////////////

function getAllTablesData(){
	
	let travelData = getTableData("travelTableViewMode", ["id","mode", "toBeBookedBy", "date", "fromLocation", "toLocation", "preferredTime", "carrierDetails", "amount","paymentMode", "remarks"]);
	let accommodationData = getTableData("accommodationTableViewMode", ["id","type", "toBeBookedBy", "checkinDate", "checkoutDate", "location", "hotelDetails", "preferencesTime", "amount","paymentMode", "remarks"]);
	let inCityCabsData = getTableData("inCityCabsTableView", ["id","mode", "toBeBookedBy", "date", "preferredTime", "location","amount", "paymentMode", "remarks"]);
	let foodTableView = getTableData("foodTableView", ["id","typeOfMeal","date","numberOfDays","amount", "paymentMode", "remarks"]);
	let miscellaneousTableView = getTableData("miscellaneousTableView", ["id","title", "toBeBookedBy", "date", "amount","paymentMode", "remarks"]);
	
	let requestData = {
	    travelReimbursement: travelData,
	    accommodationReimbursement: accommodationData,
	    inCityCabsReimbursement: inCityCabsData,
		miscellaneousReimbursement: miscellaneousTableView,
		mealReimbursement: foodTableView

	};
	console.log("Request Payload:", requestData);
	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;
    document.getElementById("signinLoader").style.display = "flex";
	$.ajax({
	  		type: "POST",
	  		url: "/travelAdvanceRequestUpdate",
		    contentType: "application/json",
	  		data:JSON.stringify({
	  			
	  			"employeeId":empId,
	  			"employerId":employerid,
	  			"travelRequestUpdate":requestData,
	  		}),
	  		success: function(data) {
	  			//newData = data;
	  			//var data1 = jQuery.parseJSON(newData);
	  			//var data2 = data1.list;
	  			var modalfirst = document.getElementById("modalTravelUpdated");
	  		    modalfirst.style.display = "block";
	  			document.getElementById("signinLoader").style.display="none";
	  		},
	  		error: function(e) {
	  			alert('Failed to fetch JSON data' + e);
	  		}
	  	});
	  	
}


function traveUpdateClose(){
	var modalfirst = document.getElementById("modalTravelUpdated");
	modalfirst.style.display = "none";
}


function getTableData(tableId, columnNames) {
    let table = document.getElementById(tableId);
    let data = [];

    if (table) {
        let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
        for (let row of rows) {
            let rowData = {};
            let inputs = row.querySelectorAll("input, select, textarea");

            inputs.forEach((input, index) => {
                if (input.tagName === "SELECT") {
                    rowData[columnNames[index]] = input.options[input.selectedIndex].value;
                } else {
                    rowData[columnNames[index]] = input.value;
                }
            });

            data.push(rowData);
        }
    }

    return data;
}
//////////////////////////////////////////////////////



function deleteAdvanceTravel(value){
	
	var result = confirm("Are you sure you want to delete?");
	if (result) {
		 document.getElementById("signinLoader").style.display="flex";
	}
	else{
		return false;
	}
	 var row = jQuery(value).closest('tr');
	 var  id = $(row).find("input[name='customCheck4']").val();
	
	 	$.ajax({
		type: "POST",
		url:"/deleteAdvanceTravel",
        data: {
				"id": id
       		 },
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
            //console.log(newData);
            var data1 = jQuery.parseJSON( newData );
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			getCashAdvanceRequestList();
			//console.log(newData);
           },
         error: function(e){
             alert('Error: ' + e);
         }
    }); 
}

/////////////////////////////////////////////////////////////

function viewAdvanceTravel(value){
	
	    document.getElementById("signinLoader").style.display="flex";
		var employerid = document.getElementById("employerId").value;
		var empId = document.getElementById("empId").value;	
		$.ajax({
		type: "GET",
		url: "/getTravelReviewData",
		data:{
			"employeeId":empId,
			"employerId":employerid,
			"status":0
			},
		        success:function(data){
		       // var data1 = data;
		       console.log(data);
			   var data1 = jQuery.parseJSON(data);
				
				var travelReimbursement = data1.data.travelReimbursement;
				var mealReimbursement = data1.data.mealReimbursement;
				var miscellaneousReimbursement = data1.data.miscellaneousReimbursement;
				var inCityCabReimbursement = data1.data.inCityCabReimbursement;
				var accomodationReimbursement = data1.data.accomodationReimbursement;
				document.getElementById("signinLoader").style.display="none";
				
				if(data1.status==true){
					const tableBody = document.querySelector("#viewTravelRequest tbody");
					tableBody.innerHTML = "";
		           data1.data.travelReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
						   <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td><input type="date" class="form-control" value="${item.date}"></td>
		                   <td>
						<input type="text"class="form-control mb-2" value="${item.departure}">
						<input type="text" class="form-control mb-2" value="${item.arrival}">
						</td>
		                   <td>
		                       <select class="form-control">
		                           <option value="6 am - 12 pm" ${item.preferredTime === "6 am - 12 pm" ? "selected" : ""}>6 am - 12 pm</option>
		                           <option value="Before 6 am">Before 6 am</option>
		                           <option value="12 pm - 6 pm">12 pm - 6 pm</option>
		                           <option value="After 6 pm">After 6 pm</option>
		                       </select>
		                   </td>
		                   <td><input type="text" class="form-control" value="${item.carrierDetails}"></td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>								    
							    <div class="mt-0" >
							        <span class="text-muted">Limit:</span>
							        <span 
							            class="border rounded px-3 py-1 bg-gray-200" 
							            style="border-color: darkgray; display: inline-block;">
							            INR 8,000
							        </span>
							    </div>
		                   </td>
		                   <td><textarea class="form-control">${item.remarks}</textarea></td>
						   <td></td>
		               `;
		               tableBody.appendChild(row);
		           });
					//////////////////////////////////////////
					
					const tableBodyAccomodation = document.querySelector("#viewAccomodationOnly tbody");
					tableBodyAccomodation.innerHTML = "";
		            data1.data.accomodationReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td>
						      <label for="accommodationCheckinDate">Check-in</label>
						      <input type="date" class="form-control" value="${item.checkinDate}">
						      <label for="accommodationCheckoutDate">Check-out</label>
						      <input type="date" class="form-control" value="${item.checkoutDate}">
						   </td>
		                   <td>
							<input type="text" class="form-control" value="${item.location}">
							</td>
		                   <td>
		                      <input type="text" class="form-control" value="${item.hotelDetails}">
		                   </td>
		                   <td><input type="text" class="form-control" value="${item.preferredTime}"></td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						  
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>
							   <div class="mt-0">
						        <span class="text-muted">Limit:</span>
						        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
						            INR 8,000
						        </span>
						    </div>
		                   </td>
		                   <td><textarea class="form-control">${item.remarks}</textarea></td>
		               `;
		               tableBodyAccomodation.appendChild(row);
		           });
					//////////////////////////////////////////////////////
					const tableBodyInCityCab = document.querySelector("#viewIncityCabOnly tbody");
					tableBodyInCityCab.innerHTML = "";
		            data1.data.accomodationReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select  class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td>
						      <input type="date" class="form-control" value="${item.date}">
						      
						   </td>
		                 
		                   <td>
						   <input type="text" name="location" placeholder="From" 
						   	 class="form-control mb-2" id="cabsFrom" style="margin-top: 10px;" value="${item.preferredTime}">
		                   </td>
		                   <td>
						   <div style="margin-bottom: -5px;">
							<input type="text" name="location" placeholder="From" 
							 class="form-control mb-2" id="cabsFrom" style="margin-top: 10px;" value="${item.fromLocation}">
							 <br>
							 <input type="text" name="location" placeholder="To" value="${item.toLocation}"
							 class="form-control mb-2" id="cabsTo">
						   </td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>
							   <div class="mt-0">
						        <span class="text-muted">Limit:</span>
						        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
						            INR 8,000
						        </span>
						    </div>
		                   </td>
		                   <td><textarea class="border rounded px-2 py-1 w-full" style="width: 120%; height: 100px;" >${item.remarks}</textarea></td>
						   <td></td>
		               `;
		               tableBodyInCityCab.appendChild(row);
		           });
				   //////////////////////////////////////////////////
				   const foodTableView = document.querySelector("#viewFoodOnly tbody");
				   foodTableView.innerHTML = "";
	   	            data1.data.mealReimbursement.forEach(item => {
	   	               const row = document.createElement("tr");
	   	               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
	   	                   <td>
	   	                       <select class="form-control typeOfMeals">
	   	                           <option value="train" ${item.typeOfMeal === "Daily Meals" ? "selected" : ""}>Daily Meals</option>
	   	                           <option value="bus" ${item.typeOfMeal === "Weekly Meals" ? "selected" : ""}>Weekly Meals</option>
	   	                           <option value="flights" ${item.typeOfMeal === "Monthly Meals" ? "selected" : ""}>Monthly Meals</option>
	   	                       </select>
							   <div class="error-message" id="error-typeOfMeals"></div>
	   	                   </td>
	   	                  
	   	                   <td>
	   					      <input type="date" class="form-control mealDate" value="${item.date}">
	   					      
	   					   </td>
	   	                   <td>
		   					<input type="text" class="form-control noOfDays" value="${item.numberOfDays}">
							<div class="error-message" id="error-noOfDays"></div>
	   					</td>
	   	                   <td>
	   	                      <input type="text" class="form-control" value="${item.location}">
							  <div class="error-message" id="error-mealLocation"></div>
	   	                   </td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
	   	                   <td>
	   					   <div style="margin-bottom: 28px;">
	   	                       <select class="form-control">
	   	                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
	   	                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
	   	                       </select>
	   						   </div>
	   						   <div class="mt-0">
	   					        <span class="text-muted">Limit:</span>
	   					        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
	   					            INR 8,000
	   					        </span>
	   					    </div>
	   	                   </td>
	   	                   <td><textarea class="border rounded px-2 py-1 w-full" style="width: 130%; height: 100px;" >${item.remarks}</textarea></td>
						   <td></td>
						   <td></td>
						   <td></td>
						   <td></td>
	   	               `;
	   	               foodTableView.appendChild(row);
	   	           });
					///////////////////////////////////////////////////////
					const miscellaneousTableView = document.querySelector("#viewMiscellaneousOnly tbody");
					miscellaneousTableView.innerHTML = "";
	   	            data1.data.miscellaneousReimbursement.forEach(item => {
	   	               const row = document.createElement("tr");
	   	               row.innerHTML = `
					       <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
	   	                   <td>
						   
						   <input type="text" name="" placeholder="Product Samples - 10 Pieces" 
						   	 class="form-control mb-2" id="miscTitle">
	   	                       
	   	                   </td>
	   	                   <td>
	   	                       <select class="form-control">
	   	                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
	   	                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
	   	                       </select>
	   	                   </td>
	   	                   <td>
	   					      <input type="date" class="form-control" value="${item.date}">
	   					      
	   					   </td>
	   	                   <td>
	   					<input type="text" class="form-control mb-2" value="${item.amount}">
	   					</td>
						</td>
		                <td>  
		                </td>
		                <td>
		                </td>
	   	                   <td>
	   					   <div style="margin-bottom: 28px;">
	   	                       <select class="form-control">
	   	                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
	   	                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
	   	                       </select>
	   						   </div>
	   						   <div class="mt-0">
	   					        <span class="text-muted">Limit:</span>
	   					        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
	   					            INR 8,000
	   					        </span>
	   					    </div>
	   	                   </td>
	   	                   <td><textarea  class="border rounded px-2 py-1 w-full" placeholder="Add Remarks (Optional)"
						   	style="width: 100%; height: 100px;" >${item.remarks}</textarea></td>
							<td></td>
	   	               `;
	   	               miscellaneousTableView.appendChild(row);
	   	           });
				   
				   var modalfirst = document.getElementById("travelRequestView");
				   modalfirst.style.display = "block";
				}
				else if(data1.status==false){
				document.getElementById("signinLoader").style.display="none";
			
			}
	     },
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});

}

function closeView(){
	var modalfirst = document.getElementById("travelRequestView");
	modalfirst.style.display = "none";
}



function getCashAdvacceTravelApprovalList(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	var empId = document.getElementById("empId").value;	
	$.ajax({
		type: "GET",
		url: "/getAdanceCashTravelApprpvalList",
		data: {
			"employeeId": empId,
			"employerId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			 //console.log(data2);
			document.getElementById("signinLoader").style.display="none";
			
			var table = $('#tableCashAdvacceTravelApprovalFlow').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				
                { "mData": "id", "render": function (data2, type, row) {
					 return ' <div class="table-check"><input type="checkbox" value="'+data2+'" id="customCheck4" name="customCheck4" ></div>';
                 }}, 
				{ "mData": "sequenceId"},   
               // { "mData": "cashDate"},   
				{ "mData": "requestType"},
				{ "mData": function (data2, type, row) {
			        return data2.currency + " " + data2.amount;
			    }},
				{ "mData": "statusRemarks"},
				{ "mData": "modeOfPayment"}, 
				{ "mData": "approvedAmount"},
				
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td> <div  class="d-flex align-items-center"> <button class="btn-attach" id="btnApprove" onclick="viewAdvanceTravelApprove(this)"> View <img src="img/attached.svg" alt=""> </button> <div class="dropdown no-arrow ml-2"> <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fas fa-ellipsis-v fa-sm"></i></a><br> <div class="dropdown-menu dropdown-menu-right shadow"  aria-labelledby="userDropdown"><button class="dropdown-item py-2" onclick="deleteAdvanceTravel(this)" > Delete  </button><a class="dropdown-item py-2" href="#"> Download </a> </div> </div> </div> </td>';
                 }}, 
    		 	],
				createdRow: function (row, data2, dataIndex) 
				   {
				    //console.log("row : "+JSON.stringify(data2));
				  
					var requestType = data2.requestType;
					//var statusMessage = data2.statusMessage;

				    if(requestType=="Accomodation")
				    {
					var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+requestType;
					 $(row).find('td:eq(2)').html(imgTag);
				    }
				    if(requestType=="In-City-Cab")
				    {
				      var imgTag = ' <img src="img/citycabs.svg" alt="" class="mr-2">'+requestType;
					  $(row).find('td:eq(2)').html(imgTag);
				    }
				    
				    if(requestType=="Miscellaneous")
				    {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+requestType;
						 $(row).find('td:eq(2)').html(imgTag);
				    }
				    
				    if(requestType=="Travel")
				    {
					var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+requestType;
				     $(row).find('td:eq(2)').html(imgTag);
				    }
				    if(requestType=="Meal")
				    {
				     var imgTag = '<img src="img/food.svg" alt="" class="mr-2">'+requestType;
					
				     $(row).find('td:eq(2)').html(imgTag);
				    }
					if(requestType=="Cash")
				    {
				     var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+requestType;
					
				     $(row).find('td:eq(2)').html(imgTag);
				    }
		     	 }
    		 	
      		});		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}
///////////////////////////////////////////////////////////////



function viewAdvanceTravelApprove(value){
	
	    document.getElementById("signinLoader").style.display="flex";
		var employerid = document.getElementById("employerId").value;
		var empId = document.getElementById("empId").value;	
		$.ajax({
		type: "GET",
		url: "/getTravelReviewData",
		data:{
			"employeeId":empId,
			"employerId":employerid,
			"status":0
			},
		        success:function(data){
		       // var data1 = data;
		       console.log(data);
			   var data1 = jQuery.parseJSON(data);
				
				var travelReimbursement = data1.data.travelReimbursement;
				var mealReimbursement = data1.data.mealReimbursement;
				var miscellaneousReimbursement = data1.data.miscellaneousReimbursement;
				var inCityCabReimbursement = data1.data.inCityCabReimbursement;
				var accomodationReimbursement = data1.data.accomodationReimbursement;
				document.getElementById("signinLoader").style.display="none";
				
				if(data1.status==true){
					const tableBody = document.querySelector("#viewTravelRequest tbody");
					tableBody.innerHTML = "";
		           data1.data.travelReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
						   <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td><input type="date" class="form-control" value="${item.date}"></td>
		                   <td>
						<input type="text"class="form-control mb-2" value="${item.departure}">
						<input type="text" class="form-control mb-2" value="${item.arrival}">
						</td>
		                   <td>
		                       <select class="form-control">
		                           <option value="6 am - 12 pm" ${item.preferredTime === "6 am - 12 pm" ? "selected" : ""}>6 am - 12 pm</option>
		                           <option value="Before 6 am">Before 6 am</option>
		                           <option value="12 pm - 6 pm">12 pm - 6 pm</option>
		                           <option value="After 6 pm">After 6 pm</option>
		                       </select>
		                   </td>
		                   <td><input type="text" class="form-control" value="${item.carrierDetails}"></td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>								    
							    <div class="mt-0" >
							        <span class="text-muted">Limit:</span>
							        <span 
							            class="border rounded px-3 py-1 bg-gray-200" 
							            style="border-color: darkgray; display: inline-block;">
							            INR 8,000
							        </span>
							    </div>
		                   </td>
		                   <td><textarea class="form-control">${item.remarks}</textarea></td>
						   <td></td>
		               `;
		               tableBody.appendChild(row);
		           });
					//////////////////////////////////////////
					
					const tableBodyAccomodation = document.querySelector("#viewAccomodationOnly tbody");
					tableBodyAccomodation.innerHTML = "";
		            data1.data.accomodationReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td>
						      <label for="accommodationCheckinDate">Check-in</label>
						      <input type="date" class="form-control" value="${item.checkinDate}">
						      <label for="accommodationCheckoutDate">Check-out</label>
						      <input type="date" class="form-control" value="${item.checkoutDate}">
						   </td>
		                   <td>
							<input type="text" class="form-control" value="${item.location}">
							</td>
		                   <td>
		                      <input type="text" class="form-control" value="${item.hotelDetails}">
		                   </td>
		                   <td><input type="text" class="form-control" value="${item.preferredTime}"></td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						  
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>
							   <div class="mt-0">
						        <span class="text-muted">Limit:</span>
						        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
						            INR 8,000
						        </span>
						    </div>
		                   </td>
		                   <td><textarea class="form-control">${item.remarks}</textarea></td>
		               `;
		               tableBodyAccomodation.appendChild(row);
		           });
					//////////////////////////////////////////////////////
					const tableBodyInCityCab = document.querySelector("#viewIncityCabOnly tbody");
					tableBodyInCityCab.innerHTML = "";
		            data1.data.accomodationReimbursement.forEach(item => {
		               const row = document.createElement("tr");
		               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
		                   <td>
		                       <select  class="form-control">
		                           <option value="train" ${item.mode === "train" ? "selected" : ""}>Train</option>
		                           <option value="bus" ${item.mode === "bus" ? "selected" : ""}>Bus</option>
		                           <option value="flights" ${item.mode === "flights" ? "selected" : ""}>Flights</option>
		                       </select>
		                   </td>
		                   <td>
		                       <select class="form-control">
		                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
		                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
		                       </select>
		                   </td>
		                   <td>
						      <input type="date" class="form-control" value="${item.date}">
						      
						   </td>
		                 
		                   <td>
						   <input type="text" name="location" placeholder="From" 
						   	 class="form-control mb-2" id="cabsFrom" style="margin-top: 10px;" value="${item.preferredTime}">
		                   </td>
		                   <td>
						   <div style="margin-bottom: -5px;">
							<input type="text" name="location" placeholder="From" 
							 class="form-control mb-2" id="cabsFrom" style="margin-top: 10px;" value="${item.fromLocation}">
							 <br>
							 <input type="text" name="location" placeholder="To" value="${item.toLocation}"
							 class="form-control mb-2" id="cabsTo">
						   </td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
		                   <td>
						   <div style="margin-bottom: 28px;">
		                       <select class="form-control">
		                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
		                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
		                       </select>
							   </div>
							   <div class="mt-0">
						        <span class="text-muted">Limit:</span>
						        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
						            INR 8,000
						        </span>
						    </div>
		                   </td>
		                   <td><textarea class="border rounded px-2 py-1 w-full" style="width: 120%; height: 100px;" >${item.remarks}</textarea></td>
						   <td></td>
		               `;
		               tableBodyInCityCab.appendChild(row);
		           });
				   //////////////////////////////////////////////////
				   const foodTableView = document.querySelector("#viewFoodOnly tbody");
				   foodTableView.innerHTML = "";
	   	            data1.data.mealReimbursement.forEach(item => {
	   	               const row = document.createElement("tr");
	   	               row.innerHTML = `
					      <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
	   	                   <td>
	   	                       <select class="form-control typeOfMeals">
	   	                           <option value="train" ${item.typeOfMeal === "Daily Meals" ? "selected" : ""}>Daily Meals</option>
	   	                           <option value="bus" ${item.typeOfMeal === "Weekly Meals" ? "selected" : ""}>Weekly Meals</option>
	   	                           <option value="flights" ${item.typeOfMeal === "Monthly Meals" ? "selected" : ""}>Monthly Meals</option>
	   	                       </select>
							   <div class="error-message" id="error-typeOfMeals"></div>
	   	                   </td>
	   	                  
	   	                   <td>
	   					      <input type="date" class="form-control mealDate" value="${item.date}">
	   					      
	   					   </td>
	   	                   <td>
		   					<input type="text" class="form-control noOfDays" value="${item.numberOfDays}">
							<div class="error-message" id="error-noOfDays"></div>
	   					</td>
	   	                   <td>
	   	                      <input type="text" class="form-control" value="${item.location}">
							  <div class="error-message" id="error-mealLocation"></div>
	   	                   </td>
						   <td><input type="text" class="form-control" value="${item.amount}"></td>
	   	                   <td>
	   					   <div style="margin-bottom: 28px;">
	   	                       <select class="form-control">
	   	                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
	   	                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
	   	                       </select>
	   						   </div>
	   						   <div class="mt-0">
	   					        <span class="text-muted">Limit:</span>
	   					        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
	   					            INR 8,000
	   					        </span>
	   					    </div>
	   	                   </td>
	   	                   <td><textarea class="border rounded px-2 py-1 w-full" style="width: 130%; height: 100px;" >${item.remarks}</textarea></td>
						   <td></td>
						   <td></td>
						   <td></td>
						   <td></td>
	   	               `;
	   	               foodTableView.appendChild(row);
	   	           });
					///////////////////////////////////////////////////////
					const miscellaneousTableView = document.querySelector("#viewMiscellaneousOnly tbody");
					miscellaneousTableView.innerHTML = "";
	   	            data1.data.miscellaneousReimbursement.forEach(item => {
	   	               const row = document.createElement("tr");
	   	               row.innerHTML = `
					       <td>
						   <input type="hidden" class="form-control" value="${item.id}">
		                   </td>
	   	                   <td>
						   
						   <input type="text" name="" placeholder="Product Samples - 10 Pieces" 
						   	 class="form-control mb-2" id="miscTitle">
	   	                       
	   	                   </td>
	   	                   <td>
	   	                       <select class="form-control">
	   	                           <option value="company" ${item.toBeBookedBy === "company" ? "selected" : ""}>Company</option>
	   	                           <option value="self" ${item.toBeBookedBy === "self" ? "selected" : ""}>Self</option>
	   	                       </select>
	   	                   </td>
	   	                   <td>
	   					      <input type="date" class="form-control" value="${item.date}">
	   					      
	   					   </td>
	   	                   <td>
	   					<input type="text" class="form-control mb-2" value="${item.amount}">
	   					</td>
						</td>
		                <td>  
		                </td>
		                <td>
		                </td>
	   	                   <td>
	   					   <div style="margin-bottom: 28px;">
	   	                       <select class="form-control">
	   	                           <option value="Credit Card" ${item.paymentMode === "Credit Card" ? "selected" : ""}>Credit Card</option>
	   	                           <option value="Cash" ${item.paymentMode === "Cash" ? "selected" : ""}>Cash</option>
	   	                       </select>
	   						   </div>
	   						   <div class="mt-0">
	   					        <span class="text-muted">Limit:</span>
	   					        <span class="border rounded px-3 py-1 bg-gray-200" style="border-color: darkgray; display: inline-block;">
	   					            INR 8,000
	   					        </span>
	   					    </div>
	   	                   </td>
	   	                   <td><textarea  class="border rounded px-2 py-1 w-full" placeholder="Add Remarks (Optional)"
						   	style="width: 100%; height: 100px;" >${item.remarks}</textarea></td>
							<td></td>
	   	               `;
	   	               miscellaneousTableView.appendChild(row);
	   	           });
				   
				   var modalfirst = document.getElementById("TravelRequestApprove");
				   modalfirst.style.display = "block";
				}
				else if(data1.status==false){
				document.getElementById("signinLoader").style.display="none";
			
			}
	     },
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});

}

function closeApproveView(){
	var modalfirst = document.getElementById("TravelRequestApprove");
	modalfirst.style.display = "none";
}


