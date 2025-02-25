function saveWaitlistData() {
    // Collect form values
    var companyName = document.getElementById("companyName").value;
    var companySize = document.querySelector("input[name='companySize']:checked");
    var industry = document.querySelector("input[name='industry']:checked");
    var contactPerson = document.getElementById("contactPerson").value;
    var contactNumber = document.getElementById("contactNumber").value;
    var emailId = document.getElementById("emailId").value;
	var eRupiStatus = document.getElementById("eRupiStatus").checked;
    
    var companySizeValue = companySize ? companySize.value : "";
    var industryValue = industry ? industry.value : "";
    
    // Validation Patterns
    var companyNamePattern = /^[a-zA-Z0-9/ ]+$/; // Allows letters, numbers, spaces, and '/'
    var namePattern = /^[a-zA-Z ]+$/; // Only allows letters and spaces
    var contactNumberPattern = /^[6-9]\d{9}$/; // Indian mobile number validation (10 digits, starts with 6-9)
    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // Standard email format
    
    // Validate required fields
    if (!companyName || !contactPerson || !contactNumber || !emailId || !companySizeValue || !industryValue) {
        alert("Please fill all required fields.");
        return;
    }
     
    // Apply validation rules
    if (!companyNamePattern.test(companyName)) {
        alert("Company Name can only contain letters, numbers, spaces, and '/'");
        return;
    }
    
    if (!namePattern.test(contactPerson)) {
        alert("Contact Person Name can only contain letters and spaces.");
        return;
    }
    
    if (!contactNumberPattern.test(contactNumber)) {
        alert("Please enter a valid 10-digit Contact Number starting with 6-9.");
        return;
    }
    
    if (!emailPattern.test(emailId)) {
        alert("Please enter a valid Email ID.");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/userWaitList", // Backend API endpoint
        data: { 		
			"companyName": companyName,
		        "contactPersonName": contactPerson,
		        "contactNumber": contactNumber,
		        "email": emailId,
		        "companySize": companySizeValue,
		        "industry": industryValue,
				
				erupistatus:eRupiStatus, 
			},
			dataType: "json",
        success: function (response) {
			console.log(response);
            if (response.status == true) {
                
                $("#waitlistApproved").show(); // Show success modal
				//alert("waitlistApproved!");
				document.querySelector("form").reset(); // Reset form
            } else {
                alert("Error: " + response.message);
            }
        },
        error: function (error) {
            alert("Something went wrong. Please try again later.");
        }
    });
}

function getWaitlist() {
		    const employerId = document.getElementById('employerId').value;

		    $.ajax({
		        type: "GET",
		        url: "/getuserWaitList",
		        data: { employerId },
		        success: function(data) {
		            const parseddata = JSON.parse(data);
		            console.log(data);
					var employer=parseddata.data;

		            // Get the table body
		            const tableBody = $("#reimbursementTable tbody");

		            // Clear existing table rows
		            tableBody.empty();

		            // Check if vouchers exist
		            if (employer.length === 0) {
		                tableBody.append(`<tr><td colspan="9" class="text-center">No Vouchers Found</td></tr>`);
		                return;
		            }

		            // Populate the table dynamically
		            employer.forEach((employer, index) => {
						const formattedDate = employer.createdDate ? employer.createdDate.split("T")[0] : "N/A";
						//const employerData = encodeURIComponent(JSON.stringify(employer)); // Encode to pass safely
		                const row = `
						<tr>
						            <td>${employer.id}</td>
						            <td>${employer.companyName}</td>
						            <td>${employer.companySize}</td>
						            <td>${employer.industry}</td>
						            <td>${employer.contactPersonName}</td>
						            <td>${employer.contactNumber}</td>
						            <td>${employer.email}</td>
						            <td>${formattedDate}</td>
									<td>${employer.statusRemarks}</td>
									<td> 
		                            <div class="dropdown no-arrow ml-2">
		                                <a class="dropdown-toggle" href="#" id="dropdownMenu${index}" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		                                    <i class="fas fa-ellipsis-v fa-sm"></i>
		                                </a>
		                                <br>
		                                <div class="dropdown-menu dropdown-menu-right shadow" aria-labelledby="dropdownMenu${index}">
		                                    <button 
		                                        class="dropdown-item py-2" 
		                                        data-employer='${JSON.stringify(employer)}' 
		                                        onclick="updateStatus(this, 'Approved')">
		                                        Approve
		                                    </button>
		                                    <button 
		                                        class="dropdown-item py-2" 
		                                        data-employer='${JSON.stringify(employer)}' 
		                                        onclick="updateStatus(this, 'Rejected')">
		                                        Reject
		                                    </button>
		                                </div>
		                            </div>
		                        </td>
						        </tr>
		                `;

		                tableBody.append(row);
		            });
		        },
		        error: function(e) {
		            alert('Error: ' + e.responseText);
		        }
		    });
		}
		
		function updateStatus(button, status) {
		    const employerData = JSON.parse(button.getAttribute("data-employer")); // Decode the employer object
		    //console.log("employer data in update status",employer);
			//employer.status = status; // Update status
			

		    $.ajax({
		        type: "POST",
		        url: "/updateuserWaitList",
				data: { 		
					"id": employerData.id, // Keeping the ID to identify the employer
							        "companyName": employerData.companyName,
							        "contactPersonName": employerData.contactPersonName,
							        "contactNumber": employerData.contactNumber,
							        "email": employerData.email,
							        "companySize": employerData.companySize,
							        "industry": employerData.industry,
							        "erupistatus": employerData.erupistatus, // Assuming this is needed
							        "status": status // Update the status (Approved/Rejected)
						},
						dataType: "json",
		        success: function(response) {
					if(response.status==true)
						{
							
									            window.location.href="/displayWaitlist"; 
						}
		            
		        },
		        error: function(error) {
		            console.log('Error updating status: ' + error.responseText);
		        }
		    });
		}	
