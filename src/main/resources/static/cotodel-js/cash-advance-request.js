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

function getTableDataTravel() {
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
function getaddTableRowAcomodation(){
	

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



function getTableDataIncity(){
	const tableBody = document.getElementById('reimbursementsBodyInCityCab');
	    const rows = tableBody.querySelectorAll('tr'); // Get all rows in tbody
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
	   document.getElementById("signinLoader").style.display="flex";

	    console.log(tableData);

	  $.ajax({
	  		type: "POST",
	  		url: "/travelAdvanceRequest",
		contentType: "application/json",
	  		data:JSON.stringify({
	  			
	  			"employeeId":empId,
	  			"employerId":employerid,
			    "requestType":"In-City-Cab",
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