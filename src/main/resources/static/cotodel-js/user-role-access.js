  function getUserWithRole() {
	var employerId = document.getElementById("employerId").value;
	var userMobile = document.getElementById("userMobile").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url:"/getUserWithRole",
		data: {"mobile":userMobile,"orgId":employerId},
		success: function(data) {
			newData = data;
			//var newData = '{"data": []}';for checking with empty data
			var data1 = jQuery.parseJSON(newData);

			document.getElementById("signinLoader").style.display = "none";
			
			
			var checkboxContainer = document.getElementById("checkboxContainer");

		    if (!data1.data || data1.data.length === 0) { 
		        console.log("Hiding checkbox container due to empty data.");
		        if (checkboxContainer) {
		            checkboxContainer.style.setProperty("display", "none", "important");
		        }
		        return;
		    } else {
		        console.log("Showing checkbox container.");
		        if (checkboxContainer) {
		            checkboxContainer.style.setProperty("display", "flex", "important");
		        }
		    }

			// Get unique role descriptions
			const roleDescriptions = [...new Set(
			    data1.data.flatMap(user => user.userRole.map(role => role.roleDesc))
			)];

			// Sort role descriptions alphabetically
			roleDescriptions.sort();

			// Populate table headers with role descriptions
			const tableHeadRow = document.getElementById("userTable").querySelector("thead tr");
			roleDescriptions.forEach(role => {
			    const th = document.createElement("th");
			    th.textContent = role;
			    tableHeadRow.appendChild(th);
			}); 

			// Populate table body
			const tableBody = document.getElementById("userTable").querySelector("tbody");
			data1.data.forEach(user => {
			    const row = document.createElement("tr");

			    // ID
			    const idCell = document.createElement("td");
			    idCell.textContent = user.id;
			    row.appendChild(idCell);

			    // Username
			    const usernameCell = document.createElement("td");
			    usernameCell.textContent = user.username || "N/A";
			    row.appendChild(usernameCell);

			    // Email
			    const emailCell = document.createElement("td");
			    emailCell.textContent = user.email || "N/A";
			    row.appendChild(emailCell);

			    // Mobile
			    const mobileCell = document.createElement("td");
			    mobileCell.textContent = user.mobile || "N/A";
			    row.appendChild(mobileCell);

			    // Roles as checkboxes (unique roles only)
			    const uniqueRoles = [...new Map(user.userRole.map(role => [role.roleDesc, role])).values()];
			    roleDescriptions.forEach(roleDesc => {
			        const roleCell = document.createElement("td");
			        const role = uniqueRoles.find(role => role.roleDesc === roleDesc);
			        const checkbox = document.createElement("input");
			        checkbox.type = "checkbox";
			        checkbox.checked = role && role.id !== null; // Check only if role.id is not null
			        checkbox.disabled = true; // Optional: Disable checkboxes
			        roleCell.appendChild(checkbox);
			        row.appendChild(roleCell);
			    });

			    // Append row to table
			    tableBody.appendChild(row);
			});
				
			},
			error: function(e) {
				alert('Error: ' + e);
			}
		});
}
 
function editUserRoleWithMoreUser() {
	
	var employerId = document.getElementById("employerId").value;
	var userMobile = document.getElementById("userMobile").value;
	document.getElementById("signinLoader").style.display="flex";
	
	$.ajax({
		type: "GET",
		url:"/getUserWithRole",
		data: {"mobile":userMobile,"orgId":employerId},
		success: function(data) {
			newData = data;
			//var newData = '{"data": []}'; for checking with empty data
			var data1 = jQuery.parseJSON(newData);
			//var data2 = data1.data;
			
			document.getElementById("signinLoader").style.display="none";
			
			var checkboxContainer = document.getElementById("checkboxContainer");

			    if (!data1.data || data1.data.length === 0) {
			        console.log("Hiding checkbox container due to empty data.");
			        if (checkboxContainer) {
			            checkboxContainer.style.setProperty("display", "none", "important");
			        }
			        return;
			    } else {
			        console.log("Showing checkbox container.");
			        if (checkboxContainer) {
			            checkboxContainer.style.setProperty("display", "flex", "important");
			        }
			    }
			
			
			
			  // Get unique role descriptions
			        const roleDescriptions = [...new Set(
			            data1.data.flatMap(user => user.userRole.map(role => role.roleDesc))
			        )];

			        // Sort role descriptions alphabetically
			        roleDescriptions.sort();

			        // Populate table headers with role descriptions
			        const tableHeadRow = document.getElementById("editUserRole").querySelector("thead tr");
			        roleDescriptions.forEach(role => {
			            const th = document.createElement("th");
			            th.textContent = role;
			            tableHeadRow.insertBefore(th, tableHeadRow.lastElementChild); // Add before the last column (Actions)
			        });

			        // Function to create a row
			            function createRow(user = { id: "", username: "", email: "", mobile: "", userRole: [] }) {
			            const tableBody = document.getElementById("editUserRole").querySelector("tbody");
			            const row = document.createElement("tr");

			            // ID
			            const idCell = document.createElement("td");
			            idCell.textContent = user.id || "N/A";
			            row.appendChild(idCell);

			            // Username
			            const usernameCell = document.createElement("td");
			            usernameCell.textContent = user.username || "N/A";
			            row.appendChild(usernameCell);

			            // Email
			            const emailCell = document.createElement("td");
			            emailCell.textContent = user.email || "N/A";
			            row.appendChild(emailCell);

			            // Mobile
			            const mobileCell = document.createElement("td");
			            mobileCell.textContent = user.mobile || "N/A";
			            row.appendChild(mobileCell);

						const uniqueRoles = [...new Map(user.userRole?.map(role => [role.roleDesc, role])).values()];
						roleDescriptions.forEach(roleDesc => {
						    const roleCell = document.createElement("td");
						    const checkbox = document.createElement("input");
						    checkbox.type = "checkbox";

						    // Check if the user has the current role and role ID is not null
						    const role = uniqueRoles.find(role => role.roleDesc === roleDesc);
						    checkbox.checked = role && role.id !== null;


			                roleCell.appendChild(checkbox);
			                row.appendChild(roleCell);
			            });

			            // Actions (Delete button)
			            const actionCell = document.createElement("td");

			            // Delete button
			            const deleteButton = document.createElement("button");
						deleteButton.className = "mr-3"; 
						
						const deleteIcon = document.createElement("img");
						deleteIcon.src = "img/role-delete.png"; // Replace with the correct image path
						deleteIcon.alt = "delete";
						deleteIcon.style="height:15px"
						deleteIcon.style="width:15px"
						deleteButton.appendChild(deleteIcon);
						deleteButton.title = "delete"; // Tooltip for accessibility

				
				        	deleteButton.addEventListener("click", () => {
				               // row.remove();
				               //alert("Are you sure you want to delete this user role");
							   
							   if (confirm("Are you sure you want to delete this user role?")) {

							// Extract roles
							const rowdelete = deleteButton.parentElement.parentElement;

						    // Extract basic user information
						    const cells = rowdelete.querySelectorAll("td");
						    const id = cells[0].textContent.trim();
						    const username = cells[1].textContent.trim();
						    const email = cells[2].textContent.trim();
						    const mobile = cells[3].textContent.trim();
						    const userRoleDelete = [];
						    const headers = document.querySelectorAll("thead th");
						    for (let i = 4; i < cells.length - 1; i++) { // Skip ID, username, email, mobile, and actions
						        const checkbox = cells[i].querySelector("input[type='checkbox']");
						        if (checkbox && checkbox.checked) {
						            const roleDesc = headers[i].textContent.trim();
						            userRoleDelete.push({ roleDesc }); // Add more role details if needed
						        }
						    }

						    // Create the row data object
						    const rowDataDelete = {
						        id,
						        username,
						        email,
						        mobile,
						        userRoleDelete,
						    };					
							/// for edit user role code
								var employerId = document.getElementById("employerId").value;
								var employername = document.getElementById("employerName").value;
								document.getElementById("signinLoader").style.display = "flex";
										$.ajax({
											type: "POST",
											url: "/deleteUserRole",
											dataType: 'json',
											data: {
												"orgId":employerId,
												"createdBy":employername,
												"id":rowDataDelete.id,
												"username":rowDataDelete.username,
												"email":rowDataDelete.email,
												"mobile":rowDataDelete.mobile,
												"roleDesc":  rowDataDelete.userRoleDelete.map(role => role.roleDesc)
											},
											success: function(data) {
												var obj = data;
												window.location.href = "/roleAccess";
												document.getElementById("signinLoader").style.display = "none";
												
											},
											error: function(e) {
												alert('Error: ' + e);
											}
										});
									/// for edit user role code
							       alert("Item deleted.");
							       } else {
							           // User clicked "No"
							           alert("deletion canceled.");
							       }
							   

							      });
							    actionCell.appendChild(deleteButton);
						
			        row.appendChild(actionCell);

			            // Append row to table
			            tableBody.appendChild(row);
			        }

			        // Populate table with initial data
			        data1.data.forEach(user => createRow(user));
			},
			error: function(e) {
				alert('Error: ' + e);
			}
		});
}


	    function searchEmployee() {
	        const searchBox = document.getElementById('searchBox').value;
	        const employerId = document.getElementById('employerId').value;
			var userMobile = document.getElementById("userMobile").value;
	        // Show loader
	        document.getElementById("signinLoader").style.display = "flex";
			

	        $.ajax({
	            type: "POST",
	            url: "/userSearch",
	            dataType: 'json',
	            data: {
	                "orgId": employerId,
	                "userName": searchBox,
					"mobile": userMobile
	            },
	            success: function (data) {
	                document.getElementById("signinLoader").style.display = "none";

	                if (data && data.status && data.data.length > 0) {
	                    const roleHeaders = generateHeaders(data.data);
	                    generateRows(data.data, roleHeaders);
	                } else {
	                    alert("No data found for the given search.");
	                }
	            },
	            error: function (e) {
	                document.getElementById("signinLoader").style.display = "none";
	                console.error('Error:', e);
	                alert('An error occurred while fetching the data.');
	            }
	        });
	    }

	    // Function to Generate Table Headers Dynamically
	    function generateHeaders(users) {
	        const tableHeader = document.getElementById('tableHeader');
	        tableHeader.innerHTML = "";

	        // Static headers
	        let headerHTML = `
	            <tr>
	                <th>ID</th>
					<th>Username</th>
					<th>Email</th>
	                <th>Mobile</th>
	        `;

	        // Extract unique role descriptions for dynamic headers
	        const roleHeaders = new Set();
	        users.forEach(user => {
	            user.userRole.forEach(role => roleHeaders.add(role.roleDesc));
	        });

	        // Add role headers
	        roleHeaders.forEach(roleDesc => {
	            headerHTML += `<th>${roleDesc}</th>`;
	        });

	        // Add the action column
	       // headerHTML += `<th>Action</th></tr>`;
	        tableHeader.innerHTML = headerHTML;

	        // Return ordered role descriptions for later use
	        return Array.from(roleHeaders);
	    }

	    // Function to Generate Table Rows
	    function generateRows(users, roleHeaders) {
	        const tableBody = document.getElementById('tableBody');
	        tableBody.innerHTML = "";

	        users.forEach(user => {
	            const row = document.createElement('tr');

	            // Start with basic user details
	            let rowHTML = `
	                <td>${user.id}</td>
					<td>${user.username || '-'}</td>
					<td>${user.email || '-'}</td>
	                <td>${user.mobile || '-'}</td>
	            `;

	            // Generate role checkboxes in columns
				roleHeaders.forEach(roleHeader => {
				    const role = user.userRole.find(r => r.roleDesc === roleHeader);
				    const isChecked = role && role.checked ? 'checked' : '';
				    const roleId = role ? role.roleId : null;

				    rowHTML += `
				        <td data-roledesc="${roleHeader}">
				            <input type="checkbox" 
				                   class="form-check-input role-checkbox" 
				                   data-userid="${user.id}" 
				                   data-roleid="${roleId}" 
				                   ${isChecked}>
				        </td>
				    `;
				});
	            // Add action column
	           // rowHTML += `
	            //    <td>
	           //         <button class="btn btn-sm btn-primary" onclick="addAction(${user.id})">Add</button>
	           //     </td>
	           // `;

	            row.innerHTML = rowHTML;
	            tableBody.appendChild(row);
	        });

	        // Add event listeners for checkboxes
	        document.querySelectorAll('.role-checkbox').forEach(checkbox => {
	            checkbox.addEventListener('change', handleCheckboxChange);
	        });
	    }

function getOTP(){
	
	const checkbox = document.getElementById("styled-checkbox-40");
	var userMobile = document.getElementById("userMobile").value;
	  if (checkbox.checked) {
		$.ajax({
			type: 'POST',
	        url:"/smsOtpSender",
			data: {
						"mobile": userMobile,
					},
			dataType: 'json',
			success: function(data) {
			var obj = data;
			
	        if (obj['status'] == true) {
				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
				document.getElementById("maskedMobileDisplay1").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Role.`;				
										
	            // If successful, open the OTP modal
				var timeleft = "60";
				var resendCodeElement = document.getElementById("resendCode");
	               // Hide the "Resend OTP" link initially
	               resendCodeElement.style.display = "none";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00:"+timeleft;
					timeleft -= 1;
					//document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft < 0) {
						clearInterval(downloadTimer);
						resendCodeElement.style.display = "block";	
					}	
				}, 1000);
	            $("#roleAcessOTPModal").show();  
	          } else {
	            alert("Error: " + obj.message);
	          }
	        },
	        error: function() {
			  //$('#otpModal').fadeIn();
	         // alert("An error occurred. Please try again.");
	        }
	      });
	 } 		  
	 else {
		document.getElementById("styled-checkbox-40Error").innerHTML="Please check consent";
		
	    //alert("Please check consent");
	  }
	
}
function getOTPforCotodel(){
	
	//const checkbox = document.getElementById("confirmAmount");
	var userMobile = document.getElementById("userMobile").value;
	const amount = document.getElementById("advanceAmount").value.trim();
	  const isConfirmed = document.getElementById("confirmAmount").checked;
	  
	  // Get both error elements
	  const amountErrorElement = document.getElementById("advanceAmountError");
	  const confirmErrorElement = document.getElementById("confirmAmountError");
	  
	  // Clear any previous error messages
	  amountErrorElement.textContent = "";
	  confirmErrorElement.textContent = "";
	  
	  // Initialize a flag to track validation status
	  let isValid = true;
	  
	  // Check if amount is empty
	  if (amount === "") {
	    amountErrorElement.textContent = "Please enter an amount.";
	    isValid = false;
	  }
	  
	  // Check if checkbox is not checked
	  if (!isConfirmed) {
	    confirmErrorElement.textContent = "Please select the confirmation checkbox.";
	    isValid = false;
	  }
	  
	  // If validation fails, return false
	  if (!isValid) {
	    return false;
	  }
	
	  //if (checkbox.checked) {
		$.ajax({
			type: 'POST',
	        url:"/smsOtpSender",
			data: {
						"mobile": userMobile,
					},
			dataType: 'json',
			success: function(data) {
			var obj = data;
	        if (obj['status'] == true) {
				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
				document.getElementById("maskedMobileDisplay2").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Amount.`;				
										
	            // If successful, open the OTP modal
				var timeleft = "60";
				var resendCodeElement = document.getElementById("resendCode");
	               // Hide the "Resend OTP" link initially
	               resendCodeElement.style.display = "none";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown1").innerHTML = "00:"+timeleft;
					timeleft -= 1;
					//document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft < 0) {
						clearInterval(downloadTimer);
						resendCodeElement.style.display = "block";	
					}	
				}, 1000);
	            $("#cotodelAmountOTPModal").show();  
	          } else {
	            alert("Error: " + obj.message);
	          }
	        },
	        error: function() {
			  //$('#otpModal').fadeIn();
	         // alert("An error occurred. Please try again.");
	        }
	      });
	// } 		  
	// else {
	//	document.getElementById("confirmAmountError").innerHTML="Please check consent";
		
	    //alert("Please check consent");
	  //}
	
}
function resendVoucherOTP(){
	
	//const checkbox = document.getElementById("styled-checkbox-40");
	var userMobile = document.getElementById("userMobile").value;
	var orderId = document.getElementById("orderId").value;
	//  if (checkbox.checked) {
		$.ajax({
			type: 'POST',
	        url:"/smsOtpResender",
			data: {
						"mobile": userMobile,
						"orderId": orderId
					},
			dataType: 'json',
			success: function(data) {
			var obj = data;
	        if (obj['status'] == true) {
				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
				document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Role.`;				
										
	            // If successful, open the OTP modal
				var timeleft = "60";
				var resendCodeElement = document.getElementById("resendCode");
	               // Hide the "Resend OTP" link initially
	               resendCodeElement.style.display = "none";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdownadd").innerHTML = "00:"+timeleft;
					timeleft -= 1;
					//document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft < 0) {
						clearInterval(downloadTimer);
						resendCodeElement.style.display = "block";	
					}	
				}, 1000);
	            $("#roleAcessOTPModal").show();  
	          } else {
	            alert("Error: " + obj.message);
	          }
	        },
	        error: function() {
			  //$('#otpModal').fadeIn();
	         // alert("An error occurred. Please try again.");
	        }
	      });
	// } 		  
	// else {
	//	document.getElementById("styled-checkbox-40Error").innerHTML="Please check consent";
		
	    //alert("Please check consent");
	//  }
	
}
// otp for add role

function getAddOTP(){
	const checkbox = document.getElementById("styled-checkbox-41");
	var userMobile = document.getElementById("userMobile").value;

	if (checkbox.checked) {
		$.ajax({
			type: 'POST',
	        url:"/smsOtpSender",
			data: {
						"mobile": userMobile,
					},
			dataType: 'json',
			success: function(data) {
			var obj = data;
	        if (obj['status'] == true) {
				$(".lastrowaddBtnWrap").hide();
				$("#addRoleAcessOTPModal").show();
				//addRoleAcessOTPModal
				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
				document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Role.`;				
										
	            // If successful, open the OTP modal
				var timeleft = "60";
				
				var downloadTimer = setInterval(function() {
					document.getElementById("countdownadd").innerHTML = "00:"+timeleft;
					timeleft -= 1;
					//document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft <0) {
						clearInterval(downloadTimer);
						
						
					}	
				}, 1000);
	            $("#roleAcessOTPModal").show();  
	          } else {
	            alert("Error: " + obj.message);
	          }
	        },
	        error: function() {
			  //$('#otpModal').fadeIn();
	         // alert("An error occurred. Please try again.");
	        }
	      });
		  }
		  else {
  		    document.getElementById("styled-checkbox-41Error").innerHTML="Please check consent";
  		  }
	}
 // Handle Checkbox State Changes
	    function handleCheckboxChange(event) {
	        const checkbox = event.target;
	        const userId = checkbox.dataset.userid;
	        const roleId = checkbox.dataset.roleid;
	        const isChecked = checkbox.checked;

	        console.log(`User ID: ${userId}, Role ID: ${roleId}, Checked: ${isChecked}`);
	        // Add your backend update logic here if needed
	    }

	    // Action Button Click Handler
		function addAction(userId) {
		    // Find the row containing the clicked button
		    const orgId = document.getElementById("employerId").value;
			// Find the row containing the clicked button
			  const row = event.target.closest('tr');

			  // Extract the static data from the row
			  const id = row.cells[0].innerText;
			  const mobile = row.cells[2].innerText;
			  const username = row.cells[3].innerText;

			  // Collect checked role descriptions
			  const roleDesc = [];
			  row.querySelectorAll('.role-checkbox').forEach(checkbox => {
			      if (checkbox.checked) {
			          const roleDescription = checkbox.closest('td').getAttribute('data-roledesc');
			          if (roleDescription) {
			              roleDesc.push({ roleDesc: roleDescription });
			          }
			      }
			  });

			  // Build the final JSON object
			  const rowData = {
			      id: id,
			      username: username,
			      mobile: mobile,
			      roleDesc: roleDesc
			  };

			  console.log(rowData);
			  var employerId = document.getElementById("employerId").value;
		    $.ajax({
		        url: '/editUserRole',
		        type: 'POST',
		        dataType: 'json',
		        data: {
		            //orgId: orgId,
					"orgId":employerId,
					"id":rowData.id,
					"username":rowData.username,
					"mobile":rowData.mobile,
					"roleDesc": rowData.roleDesc.map(role => role.roleDesc) 
		        },
		        success: function(response) {
					window.location.href = "/roleAccess";
		        },
		        error: function(err) {
		            console.error("Error sending user data:", err);
		        }
		    });
		}
				 
		    function focusNext(currentInput) {
		        // Move focus to the next input box
		        var maxLength = parseInt(currentInput.getAttribute("maxlength"));
		        var currentLength = currentInput.value.length;

		        if (currentLength >= maxLength) {
		            var nextIndex = Array.from(currentInput.parentElement.children).indexOf(currentInput) + 1;
		            var nextInput = currentInput.parentElement.children[nextIndex];

		            if (nextInput) {
		                nextInput.focus();
		            }
		        }
		    }

		function focusBack(){
			document.getElementById("otpError1").innerHTML="";
			  var elts = document.getElementsByClassName('test')
			  Array.from(elts).forEach(function(elt) {
			  elt.addEventListener("keydown", function(event) {
			    // Number 13 is the "Enter" key on the keyboard
			    if (event.keyCode === 13 ||
			        event.keyCode !== 8 && elt.value.length === Number(elt.maxLength)
			    ) {
			      // Focus on the next sibling
			      elt.nextElementSibling.focus()
			    }
			    if (event.keyCode == 8) {
			      elt.value = '';
			      if (elt.previousElementSibling != null) {
			        elt.previousElementSibling.focus();
			        event.preventDefault();
			      }
			    }
			  });
			})
		}
		

		function verfyIssueVoucherOTP() {
			
		  	var password1 = document.getElementById("password1").value;
		  	var password2 = document.getElementById("password2").value;
		  	var password3 = document.getElementById("password3").value;
		  	var password4 = document.getElementById("password4").value;
		  	var password5 = document.getElementById("password5").value;
		  	var password6 = document.getElementById("password6").value;
		  	var orderId = document.getElementById("orderId").value;
		  	var userMobile = document.getElementById("userMobile").value;
		  	 if (password1 == "" && password1.length < 1) {
		  		//document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password1.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password2 == "" && password2.length < 1) {
		  		//document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password2.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password3 == "" && passwor3.length < 1) {
		  		//document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password3.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password4 == "" && password4.length < 1) {
		  		//document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password4.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password5 == "" && password5.length < 1) {
		  		//document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password5.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password6 == "" && password6.length < 1) {
		  		//document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password6.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
			document.getElementById("authenticate").disabled = true;
		  	$.ajax({
		  			type: "POST",
		  			url:"/verifyOTP",
		  			dataType: 'json',
		  			data: {
		  				"password1": password1,
		  				"password2": password2,
		  				"password3": password3,
		  				"password4": password4,	
		  				"password5": password5,
		  				"password6": password6,
		  				"mobile": userMobile,
		  				"orderId": orderId,
		  				"userName":userMobile
		  			},
		  			success: function(data) {
		  				var obj = data;

		  				if (obj['status']== true) {
							roleUpdate();
							$("#roleAcessOTPModal").hide();
							$("#roleAcessModalSuccessful").show();
							window.location.href="/roleAccess";
		  				}else if (obj['status'] == false) {
							document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
							roleUpdate();
							$("#roleAcessOTPModal").show();
							$("#roleAcessModalSuccessful").hide();
							
							document.getElementById("authenticate").disabled = false;
						} else {
							
		  				}
		  			},
		  			error: function(e) {
		  				alert('Error: ' + e);
		  			}
		  		});
		  }

		  function verfyAddRoleOTP() {
			
			document.getElementById("authenticate").disabled = true;
		  	var password1 = document.getElementById("password11").value;
		  	var password2 = document.getElementById("password22").value;
		  	var password3 = document.getElementById("password33").value;
		  	var password4 = document.getElementById("password44").value;
		  	var password5 = document.getElementById("password55").value;
		  	var password6 = document.getElementById("password66").value;
		  	var orderId = document.getElementById("orderId").value;
		  	var userMobile = document.getElementById("userMobile").value;
		  	 if (password1 == "" && password1.length < 1) {
		  		document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password1.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password2 == "" && password2.length < 1) {
		  		document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password2.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password3 == "" && passwor3.length < 1) {
		  		document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password3.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password4 == "" && password4.length < 1) {
		  		document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password4.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password5 == "" && password5.length < 1) {
		  		document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password5.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
		  	 if (password6 == "" && password6.length < 1) {
		  		document.getElementById("mobError").innerHTML="";
		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		  		x = false;
		  	}
		  	 else if (password6.length < 1) {
		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		  		x = false;
		  	}
		  	else{
		  		document.getElementById("otpError").innerHTML="";
		  	}
			
		  	$.ajax({
		  			type: "POST",
		  			url:"/verifyOTP",
		  			dataType: 'json',
		  			data: {
		  				"password1": password1,
		  				"password2": password2,
		  				"password3": password3,
		  				"password4": password4,	
		  				"password5": password5,
		  				"password6": password6,
		  				"mobile": userMobile,
		  				"orderId": orderId,
		  				"userName":userMobile
		  			},
		  			success: function(data) {
		  				var obj = data;

		  				if (obj['status']== true) {
							addRole();
							$("#AddRoleAcessOTPModal").hide();
							$("#roleAcessModalSuccessful").show();
							window.location.href="/roleAccess";
		  				}else if (obj['status'] == false) {
							document.getElementById("otpError1").innerHTML="Please Enter Valid OTP..";
						} else {
		  				
		  				}
		  			},
		  			error: function(e) {
		  				alert('Error: ' + e);
		  			}
		  		});
		  }
		  			  		  
		  function  roleUpdate(){
		  	
			const employerId = document.getElementById("employerId").value;
			const employerName = document.getElementById("employerName").value;
			const userMobile = document.getElementById("userMobile").value;
			var checkvalue=null;
			const checkbox = document.getElementById("styled-checkbox-40");

			  if (checkbox.checked) {
			    checkvalue ="yes";
			  } else {
			    checkvalue ="no";
			  }
		  	document.getElementById("password1").value="";
		  	document.getElementById("password2").value="";
		    document.getElementById("password3").value="";
		  	document.getElementById("password4").value="";
		  	document.getElementById("password5").value="";
		    document.getElementById("password6").value="";
			
			// Retrieve all rows from the table body
				const rows = document.querySelectorAll("#editUserRole tbody tr");
				const allRowsData = [];

				// Loop through each row to extract data
				rows.forEach(row => {
				    // Extract basic user information
				    const cells = row.querySelectorAll("td");
				    const id = cells[0].textContent.trim();
				    const username = cells[1].querySelector("input") 
				                     ? cells[1].querySelector("input").value.trim() 
				                     : cells[1].textContent.trim();
				    const email = cells[2].querySelector("input") 
				                  ? cells[2].querySelector("input").value.trim() 
				                  : cells[2].textContent.trim();
				    const mobile = cells[3].querySelector("input") 
				                   ? cells[3].querySelector("input").value.trim() 
				                   : cells[3].textContent.trim();

				    // Extract roles
				    const userRole = [];
				    const headers = document.querySelectorAll("thead th");
				    for (let i = 4; i < cells.length - 1; i++) { // Skip ID, username, email, mobile, and actions
				        const checkbox = cells[i].querySelector("input[type='checkbox']");
				        if (checkbox && checkbox.checked) {
				            const roleDesc = headers[i].textContent.trim();
				            userRole.push({ roleDesc }); // Add more role details if needed
				        }
				    }

				    // Add row data to the array
				    allRowsData.push({
				        id,
				        username,
				        email,
				        mobile,
				        userRole,
				    });
				});

				// Log the collected data (optional)
				console.log(allRowsData);
				//alert(JSON.stringify(allRowsData, null, 2));
				// AJAX call to send the data
				document.getElementById("signinLoader").style.display = "flex";
				$.ajax({
				    type: "POST",
				    url: "/editUserRole",
				    dataType: 'json',
					contentType: 'application/json',
				    data: JSON.stringify({
						orgId: employerId,
				        employerId: employerId,
						userMobile: userMobile,
						consent: checkvalue,
				        createdBy: employerName,
				        userDTO: allRowsData, // Send all rows as an array
				    }),
				    success: function(data) {
				        console.log("Success:", data);
				        document.getElementById("signinLoader").style.display = "none";
				    },
				    error: function(e) {
				        console.error("Error:", e);
				        alert('Error: ' + e.responseText || e.statusText);
				        document.getElementById("signinLoader").style.display = "none";
				    }
				});

		  }
		  function  addRole(){
						
			const employerId = document.getElementById("employerId").value;
			const employerName = document.getElementById("employerName").value;
			const userMobile = document.getElementById("userMobile").value;
			var checkvalue=null;
			const checkbox = document.getElementById("styled-checkbox-41");

			  if (checkbox.checked) {
			    checkvalue ="yes";
			  } else {
			    checkvalue ="no";
			  }
		  	document.getElementById("password11").value="";
		  	document.getElementById("password22").value="";
		    document.getElementById("password33").value="";
		  	document.getElementById("password44").value="";
		  	document.getElementById("password55").value="";
		    document.getElementById("password66").value="";
			
			// Retrieve all rows from the table body
			const rows = document.querySelectorAll("#resultTable tbody tr");
			const allRowsData = [];

			// Loop through each row to extract data
			rows.forEach(row => {
			    // Extract basic user information
			    const cells = row.querySelectorAll("td");
			    const id = cells[0].textContent.trim();
			    const username = cells[1].querySelector("input") 
			                     ? cells[1].querySelector("input").value.trim() 
			                     : cells[1].textContent.trim();
			    const email = cells[2].querySelector("input") 
			                  ? cells[2].querySelector("input").value.trim() 
			                  : cells[2].textContent.trim();
			    const mobile = cells[3].querySelector("input") 
			                   ? cells[3].querySelector("input").value.trim() 
			                   : cells[3].textContent.trim();

			    // Extract roles
			    const userRole = [];
				const roleColumns = Array.from(document.querySelectorAll("#resultTable thead th"))
	             .slice(4);
				 for (let i = 4; i < cells.length; i++) { // Ensures last role column is included
				             const checkbox = cells[i].querySelector("input[type='checkbox']");
				             const roleIndex = i - 4; // Align with roleColumns
				             if (checkbox && checkbox.checked && roleColumns[roleIndex]) {
				                 userRole.push({ roleDesc: roleColumns[roleIndex].textContent.trim() });
				             }
				         }

			    // Add row data to the array
			    allRowsData.push({
			        id,
			        username,
			        email,
			        mobile,
			        userRole,
			    });
			});

			// Log the collected data (optional)
			console.log(allRowsData);
			//alert(JSON.stringify(allRowsData, null, 2));
				// AJAX call to send the data
				document.getElementById("signinLoader").style.display = "flex";
				$.ajax({
				    type: "POST",
				    url: "/editUserRole",
				    dataType: 'json',
					contentType: 'application/json',
				    data: JSON.stringify({
				        orgId: employerId,
				        employerId: employerId,
						userMobile: userMobile,
						consent: checkvalue,
				        createdBy: employerName,
				        userDTO: allRowsData, // Send all rows as an array
				    }),
				    success: function(data) {
				        console.log("Success:", data);
						window.location.href = "/roleAccess";
				        document.getElementById("signinLoader").style.display = "none";
				    },
				    error: function(e) {
				        console.error("Error:", e);
				        alert('Error: ' + e.responseText || e.statusText);
				        document.getElementById("signinLoader").style.display = "none";
				    }
				});
  
		  }
		  
		  function toggleSubmitButton() {
		  	       const checkbox = document.getElementById('styled-checkbox-40');
		  	       const submitButton = document.getElementById('editRole');
		  	       
		  	       // Enable the button only if the checkbox is checked
		  	       submitButton.disabled = !checkbox.checked;
		  	   }
			   
			   
			   function cotodelLinkedBankDetail() {
			       document.getElementById("signinLoader").style.display = "flex";
			       
			       $.ajax({
			           type: "POST",
			           url: "/getErupiLinkAccountDetails",
			           data: {
			               "acNumber": "12345678912345"
			           },
			           beforeSend: function(xhr) {
			               //xhr.setRequestHeader(header, token);
			           },
			           success: function(data) {
			               console.log("Linked bank account data:", data);
			               
			               var parsedData = typeof data === "string" ? JSON.parse(data) : data;
			               if (parsedData.status === true && parsedData.data) {
			                   var item = parsedData.data;
							    $("#CId").val(item.id);
		   						$("#CbankCode").val(item.bankCode);
		   						$("#CbankName").val(item.bankName);
								$("#CaccountHolderName").val(item.accountHolderName);
								$("#CacNumber").val(item.acNumber);
		   						$("#CaccountType").val(item.accountType);
								$("#CifscCode").val(item.ifsc);
								//$("#Cbalance").val(item.balance);
								
			                   console.log("cotodelLinkedBankDetail()", item);
			                   
			                   const wrapper = document.getElementById('data-wrapper1');
			                   wrapper.innerHTML = '';
			                   
			                   const container = document.createElement('div');
			                   container.className = 'data-container';
			                   
			                   const fieldsToDisplay = ["bankName", "accountHolderName", "acNumber", "accountType", "ifsc"];
			                   
			                   const fieldLabels = {
			                       bankName: "Bank Name",
			                       accountHolderName: "Account Holder Name",
			                       acNumber: "Account Number",
			                       accountType: "Account Type",
			                       ifsc: "IFSC",
			                       
			                   };
			                   
			                   fieldsToDisplay.forEach(key => {
			                       const fieldDiv = document.createElement('div');
			                       fieldDiv.className = 'field';
			                       fieldDiv.innerHTML = `<span class="label">${fieldLabels[key]}:</span> ${item[key] ?? 'N/A'}`;
			                       container.appendChild(fieldDiv);
			                   });
			                   
			                   wrapper.appendChild(container);
			               }
			               
			               document.getElementById("signinLoader").style.display = "none";
			           },
			           error: function(error) {
			               console.error("Error fetching bank details:", error);
			               document.getElementById("signinLoader").style.display = "none";
			           }
			       });
			   }

			   function resendCotodelOTP(){
			   	
			   	//const checkbox = document.getElementById("styled-checkbox-40");
			   	var userMobile = document.getElementById("userMobile").value;
			   	var orderId = document.getElementById("orderId").value;
			   	//  if (checkbox.checked) {
			   		$.ajax({
			   			type: 'POST',
			   	        url:"/smsOtpResender",
			   			data: {
			   						"mobile": userMobile,
			   						"orderId": orderId
			   					},
			   			dataType: 'json',
			   			success: function(data) {
			   			var obj = data;
			   	        if (obj['status'] == true) {
							// Mask the mobile number (show only last 4 digits)
							var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
							document.getElementById("maskedMobileDisplay2").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Amount.`;				
											
			   	            // If successful, open the OTP modal
			   				var timeleft = "60";
			   				var resendCodeElement = document.getElementById("resendCode");
			   	               // Hide the "Resend OTP" link initially
			   	               resendCodeElement.style.display = "none";
			   				var downloadTimer = setInterval(function() {
			   					document.getElementById("countdown1").innerHTML = "00:"+timeleft;
			   					timeleft -= 1;
			   					//document.getElementById("optBtn").style.display = "none";
			   					document.getElementById("orderId").value= obj['orderId'];
			   					//document.getElementById("verifyotpdiv").style.display = "block";
			   					if (timeleft < 0) {
			   						clearInterval(downloadTimer);
			   						resendCodeElement.style.display = "block";	
			   					}	
			   				}, 1000);
			   	            $("#cotodelAmountOTPModal").show();  
			   	          } else {
			   	            alert("Error: " + obj.message);
			   	          }
			   	        },
			   	        error: function() {
			   			  //$('#otpModal').fadeIn();
			   	         // alert("An error occurred. Please try again.");
			   	        }
			   	      });
			   	// } 		  
			   	// else {
			   	//	document.getElementById("styled-checkbox-40Error").innerHTML="Please check consent";
			   		
			   	    //alert("Please check consent");
			   	//  }
			   	
			   }
			   function verfyCotodelOTP() {
			   		//document.getElementById("authenticate").disabled = true;
			   	  	var password1 = document.getElementById("password111").value;
			   	  	var password2 = document.getElementById("password222").value;
			   	  	var password3 = document.getElementById("password333").value;
			   	  	var password4 = document.getElementById("password444").value;
			   	  	var password5 = document.getElementById("password555").value;
			   	  	var password6 = document.getElementById("password666").value;
			   	  	var orderId = document.getElementById("orderId").value;
			   	  	var userMobile = document.getElementById("userMobile").value;
			   	  	 if (password1 == "" && password1.length < 1) {
			   	  		document.getElementById("mobError").innerHTML="";
			   	  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
			   	  		x = false;
			   	  	}
			   	  	 else if (password1.length < 1) {
			   	  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
			   	  		x = false;
			   	  	}
			   	  	else{
			   	  		document.getElementById("otpError").innerHTML="";
			   	  	}
			   	  	 if (password2 == "" && password2.length < 1) {
			   	  		document.getElementById("mobError").innerHTML="";
			   	  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
			   	  		x = false;
			   	  	}
			   	  	 else if (password2.length < 1) {
			   	  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
			   	  		x = false;
			   	  	}
			   	  	else{
			   	  		document.getElementById("otpError").innerHTML="";
			   	  	}
			   	  	 if (password3 == "" && passwor3.length < 1) {
			   	  		document.getElementById("mobError").innerHTML="";
			   	  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
			   	  		x = false;
			   	  	}
			   	  	 else if (password3.length < 1) {
			   	  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
			   	  		x = false;
			   	  	}
			   	  	else{
			   	  		document.getElementById("otpError").innerHTML="";
			   	  	}
			   	  	 if (password4 == "" && password4.length < 1) {
			   	  		document.getElementById("mobError").innerHTML="";
			   	  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
			   	  		x = false;
			   	  	}
			   	  	 else if (password4.length < 1) {
			   	  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
			   	  		x = false;
			   	  	}
			   	  	else{
			   	  		document.getElementById("otpError").innerHTML="";
			   	  	}
			   	  	 if (password5 == "" && password5.length < 1) {
			   	  		document.getElementById("mobError").innerHTML="";
			   	  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
			   	  		x = false;
			   	  	}
			   	  	 else if (password5.length < 1) {
			   	  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
			   	  		x = false;
			   	  	}
			   	  	else{
			   	  		document.getElementById("otpError").innerHTML="";
			   	  	}
			   	  	 if (password6 == "" && password6.length < 1) {
			   	  		document.getElementById("mobError").innerHTML="";
			   	  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
			   	  		x = false;
			   	  	}
			   	  	 else if (password6.length < 1) {
			   	  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
			   	  		x = false;
			   	  	}
			   	  	else{
			   	  		document.getElementById("otpError").innerHTML="";
			   	  	}
			   	  	
			   	  	$.ajax({
			   	  			type: "POST",
			   	  			url:"/verifyOTP",
			   	  			dataType: 'json',
			   	  			data: {
			   	  				"password1": password1,
			   	  				"password2": password2,
			   	  				"password3": password3,
			   	  				"password4": password4,	
			   	  				"password5": password5,
			   	  				"password6": password6,
			   	  				"mobile": userMobile,
			   	  				"orderId": orderId,
			   	  				"userName":userMobile
			   	  			},
			   	  			success: function(data) {
			   	  				var obj = data;

			   	  				if (obj['status']== true) {
									
			   						$("#cotodelAmountOTPModal").hide();
									submitCotodelBankDetails();
			   						
									
			   						//window.location.href="/roleAccess";
			   	  				}else if (obj['status'] == false) { 
									//document.getElementById("otpError2").innerHTML=data.message;
									document.getElementById("otpError2").innerHTML="Incorrect Otp!!";
			   					} else {
			   	  				
			   	  				}
								$(".otp-box-onboarding input[type='password']").val("");//clearing all input feilds
			   	  			},
			   	  			error: function(e) {
			   	  				alert('Error: ' + e);
			   	  			}
			   	  		});
			   	  }
				  function submitCotodelBankDetails() {
				      document.getElementById("signinLoader").style.display = "flex";
					  var employerId = document.getElementById("employerId").value;
					  var userMobile = document.getElementById("userMobile").value;
				      var CId = document.getElementById("CId").value;
				      var ClinkId = document.getElementById("ClinkId").value;
				      var CbankCode = document.getElementById("CbankCode").value;
				      var CbankName = document.getElementById("CbankName").value;
				      var CaccountHolderName = document.getElementById("CaccountHolderName").value;
				      var CacNumber = document.getElementById("CacNumber").value;
				      var CaccountType = document.getElementById("CaccountType").value.trim();;
				      var CifscCode = document.getElementById("CifscCode").value;
				      
				      //var Cbalance = document.getElementById("Cbalance").value;
					  var CnewAmount = document.getElementById("advanceAmount").value;
					  var createdby = document.getElementById("employerName").value;
					  //let CaccountTypeobj = CaccountType.toUpperCase() === "SAVING" 
					   //   ? { saving: "SAVING", current: null } 
					    //  : { saving: null, current: "CURRENT" };   
					      
				      $.ajax({
				          type: "POST",
				          url: "/submitCotodelDetails",
						  // Update with the correct endpoint
				          data:{
				              "linkId": CId,
							  "orgId": employerId,
				              "mobile":userMobile,
				              //"linkId": ClinkId,
				              "bankCode": CbankCode,
				              "bankName": CbankName,
				              "accountHolderName": CaccountHolderName,
				              "acNumber": CacNumber,
				              "accountType": CaccountType,
				              "ifscCode": CifscCode,				         
				              //"balance": Cbalance,
							  "createdby":createdby,
							  "amountLimit":CnewAmount,
				              
				          },
						  				          
						  success: function(data) {
							newData = data;
							var data1 = jQuery.parseJSON(newData);
							console.log("parsed data",data1);
							if(data1.status==true){
							//$("#amountApproved").show();
				              //console.log("Bank details submitted successfully:", response);
							  $("#amountApproved").show();
							  //setTimeout(function() {
							    //    window.location.href = "/roleAccess";
								
							   //}, 1100);
							   localStorage.setItem("activeTab", "#menu33");
							  }
							  else if(data1.status==false){
								alert(data1.message);
							  }
							  document.getElementById("advanceAmount").value="";
							  $("#confirmAmount").prop("checked", false);
							
				          },
						  error: function(e){
							
						              alert('Error: ' + e);
						          }
				      });
					  document.getElementById("signinLoader").style.display = "none";
				  }		  		  