/*function getWaitlist() {
		    const employerId = document.getElementById('employerId').value;
			document.getElementById("signinLoader").style.display="flex";
		    $.ajax({
		        type: "GET",
		        url: "/getsubmitedCDetails",
		        data: { employerId },
		        success: function(data) {
					document.getElementById("signinLoader").style.display="none";
		            const parseddata = JSON.parse(data);
		            console.log(data);
					var employer=parseddata.data;

		            // Get the table body
		            const tableBody = $("#reimbursementTable tbody");

		            // Clear existing table rows
		            tableBody.empty();

		            // Check if vouchers exist
		            if (employer.length === 0) {
		                tableBody.append(`<tr><td colspan="16" class="text-center">No Requests Found</td></tr>`);
		                return;
		            }

		            // Populate the table dynamically
		            employer.forEach((employer, index) => {
						//const formattedDate = employer.createdDate ? employer.createdDate.split("T")[0] : "N/A";
						//const employerData = encodeURIComponent(JSON.stringify(employer)); // Encode to pass safely
						let statusColorClass = "";
						    if (employer.statusMessage === "Approved") {
						        statusColorClass = "text-success"; // Green
						    } else if (employer.statusMessage === "Requested") {
						        statusColorClass = "text-warning"; // Yellow
						    } else if (employer.statusMessage === "Rejected") {
						        statusColorClass = "text-danger"; // Red
						    }
							let approver = employer.statusMessage === "Approved" 
							    ? employer.approvedby 
							    : employer.statusMessage === "Rejected" 
							    ? employer.rejectedby 
							    : "";
						const row = `
						<tr>
						            <td>${employer.id}</td>
						            <td>${employer.bankName}</td>
						            
						            <td>${employer.accountHolderName}</td>
						            <td>${employer.acNumber}</td>
						           
						            <td>${employer.ifscCode}</td>
									<td>${employer.createdby}</td>
									
						            <td>${employer.amountLimit}</td>
									<td>${employer.balance}</td>
									 <td class="${statusColorClass} font-weight-bold">${employer.statusMessage}</td>
									<td>${approver}</td>
									
									
									
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
			console.log("employerData",employerData);
			console.log("status",status);
			const sessionUser = document.getElementById("Name").value;
		    //console.log("employer data in update status",employer);
			//employer.status = status; // Update status
			
			document.getElementById("signinLoader").style.display="flex";
		    $.ajax({
		        type: "POST",
		        url: "/cApproveReject",
				data: { 		
					"id": employerData.id, 
					"linkId":employerData.linkId,
					"orgId":employerData.orgId,
		            "bankName": employerData.bankName,
		            "bankCode": employerData.bankCode,
		            "accountHolderName": employerData.accountHolderName,
		            "acNumber": employerData.acNumber,
		            "accountType": employerData.accountType,
		            "ifscCode": employerData.ifscCode,
		            "createdby": employerData.createdby,
		            "mobile": employerData.mobile,
		            "amountLimit": employerData.amountLimit,
					"balance":employerData.balance,
					"approvedby": (status === "Approved") ? sessionUser : employerData.approvedby, // Assign session user if approved
		            "rejectedby": (status === "Rejected") ? sessionUser : employerData.rejectedby, // Assign session user if rejected					           
		            "status": status  // Approved/Rejected
						},
						dataType: "json",
		        success: function(response) {
					document.getElementById("signinLoader").style.display="none";
					if(response.status==true)
						{
							
			            window.location.href="/cotodelApproval"; 
						}
						else
						{
							window.location.href="/cotodelApproval"; 
						}
		            
		        },
		        error: function(error) {
		            console.log('Error updating status: ' + error.responseText);
		        }
		    });
		}	*/
		
		function getWaitlist() {
		    const employerId = document.getElementById('employerId').value;
		    document.getElementById("signinLoader").style.display="flex";
		    $.ajax({
		        type: "GET",
		        url: "/getsubmitedCDetails",
		        data: { employerId },
		        success: function(data) {
		            document.getElementById("signinLoader").style.display="none";
		            const parseddata = JSON.parse(data);
		            console.log(data);
		            var employer=parseddata.data;

		            // Get the table body
		            const tableBody = $("#reimbursementTable tbody");

		            // Clear existing table rows
		            tableBody.empty();

		            // Check if vouchers exist
		            if (employer.length === 0) {
		                tableBody.append(`<tr><td colspan="16" class="text-center">No Requests Found</td></tr>`);
		                return;
		            }

		            // Populate the table dynamically
		            employer.forEach((employer, index) => {
		                let statusColorClass = "";
		                if (employer.statusMessage === "Approved") {
		                    statusColorClass = "text-success"; // Green
		                } else if (employer.statusMessage === "Requested") {
		                    statusColorClass = "text-warning"; // Yellow
		                } else if (employer.statusMessage === "Rejected") {
		                    statusColorClass = "text-danger"; // Red
		                }
		                let approver = employer.statusMessage === "Approved" 
		                    ? employer.approvedby 
		                    : employer.statusMessage === "Rejected" 
		                    ? employer.rejectedby 
		                    : "";
		                const row = `
		                <tr>
		                    <td>${employer.id}</td>
		                    <td>${employer.bankName}</td>
		                    <td>${employer.accountHolderName}</td>
		                    <td>${employer.acNumber}</td>
		                    <td>${employer.ifscCode}</td>
		                    <td>${employer.createdby}</td>
		                    <td>${employer.amountLimit}</td>
		                    <td>${employer.balance}</td>
		                    <td class="${statusColorClass} font-weight-bold">${employer.statusMessage}</td>
		                    <td>${approver}</td>
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
		                                    onclick="showConfirmationModal(this, 'Approved')">
		                                    Approve
		                                </button>
		                                <button 
		                                    class="dropdown-item py-2" 
		                                    data-employer='${JSON.stringify(employer)}' 
		                                    onclick="showConfirmationModal(this, 'Rejected')">
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

		// Function to show the confirmation modal
		function showConfirmationModal(button, status) {
		    const employerData = JSON.parse(button.getAttribute("data-employer"));
		    
		    // Store the data and status for use when the confirm button is clicked
		    $("#confirmationModal").data("employerData", employerData);
		    $("#confirmationModal").data("status", status);
		    
		    // Update modal title and content based on status
		    $("#modalTitle").text(`Confirm ${status}`);
		    $("#modalMessage").text(`Are you sure you want to ${status === 'Approved' ? 'approve' : 'reject'} this request?`);
		    
		    // Fill in the data for the selected row
		    $("#popupFieldName").text(employerData.accountHolderName);
		    $("#popupFieldMobile").text(employerData.mobile || "N/A");
		    $("#popupFieldAmount").text(employerData.amountLimit);
		    $("#popupFieldBankName").text(employerData.bankName);
		    $("#popupFieldAccountNumber").text(employerData.acNumber);
		    $("#popupFieldIfscCode").text(employerData.ifscCode);
		    $("#popupFieldCreatedBy").text(employerData.createdby);
		    $("#popupFieldBalance").text(employerData.balance);
		    
		    // Set the confirm button text based on status
		    $("#btnConfirm").text(status === 'Approved' ? 'APPROVE' : 'REJECT');
		    
		    // Update button class for visual differentiation
		    if (status === 'Approved') {
		        $("#btnConfirm").removeClass("btn-danger").addClass("btn-primary");
		    } else {
		        $("#btnConfirm").removeClass("btn-primary").addClass("btn-danger");
		    }
		    
		    // Show the modal
		    $("#confirmationModal").modal('show');
		}

		// Function to actually update the status (called from modal confirmation)
		function updateStatus() {
		    const employerData = $("#confirmationModal").data("employerData");
			console.log("employerData",employerData);
		    const status = $("#confirmationModal").data("status");
		    const sessionUser = document.getElementById("Name").value;
		    
		    document.getElementById("signinLoader").style.display="flex";
		    $.ajax({
		        type: "POST",
		        url: "/cApproveReject",
		        data: {         
		            "id": employerData.id, 
		            "linkId": employerData.linkId,
		            "orgId": employerData.orgId,
		            "bankName": employerData.bankName,
		            "bankCode": employerData.bankCode,
		            "accountHolderName": employerData.accountHolderName,
		            "acNumber": employerData.acNumber,
		            "accountType": employerData.accountType,
		            "ifscCode": employerData.ifscCode,
		            "createdby": employerData.createdby,
		            "mobile": employerData.mobile,
		            "amountLimit": employerData.amountLimit,
		            "balance": employerData.balance,
		            "approvedby": (status === "Approved") ? sessionUser : employerData.approvedby,
		            "rejectedby": (status === "Rejected") ? sessionUser : employerData.rejectedby,               
		            "status": status  // Approved/Rejected
		        },
		        dataType: "json",
		        success: function(response) {
		            document.getElementById("signinLoader").style.display="none";
		            // Close the modal
		            $("#confirmationModal").modal('hide');
		            
		            if(response.status==true) {
		                window.location.href="/cotodelApproval"; 
		            } else {
		                window.location.href="/cotodelApproval"; 
		            }
		        },
		        error: function(error) {
		            document.getElementById("signinLoader").style.display="none";
		            console.log('Error updating status: ' + error.responseText);
		            $("#confirmationModal").modal('hide');
		        }
		    });
		}