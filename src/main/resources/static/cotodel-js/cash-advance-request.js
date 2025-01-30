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

        // Get Mode of Payment (Select Dropdown)
        const paymentModeSelect = row.querySelector('td:nth-child(8) select');
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

		const tableRows = document.querySelectorAll("#reimbursementsBodyFood tr");
		    let tableData = [];
	
		    tableRows.forEach(row => {
		        let rowData = {
		            typeOfMeal: row.querySelector("#typeOfMeals")?.value || "",
		            startDate: row.querySelector("#mealDate")?.value || "",
		            numberOfDays: row.querySelector("#noOfDays")?.value || "",
		            location: row.querySelector("#mealLocation")?.value || "",
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

//////////////////////////////////////////////
function getTableDataMiscellaneous(){
		const tableRows = document.querySelectorAll("#reimbursementsBodyFood tr");
		let tableData = [];
		    tableRows.forEach(row => {
		        let rowData = {
		            typeOfMeal: row.querySelector("#typeOfMeals")?.value || "",
		            startDate: row.querySelector("#mealDate")?.value || "",
		            numberOfDays: row.querySelector("#noOfDays")?.value || "",
		            location: row.querySelector("#mealLocation")?.value || "",
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