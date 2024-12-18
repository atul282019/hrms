  function getUserWithRole() {
	var employerId = document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url:"/getUserWithRole",
		data: {
			"orgId":70
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			//var data2 = data1.data;
			
			document.getElementById("signinLoader").style.display="none";
			
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
			              const roleExists = uniqueRoles.some(role => role.roleDesc === roleDesc);
			              const checkbox = document.createElement("input");
			              checkbox.type = "checkbox";
			              checkbox.checked = roleExists;
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
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url:"/getUserWithRole",
		data: {
			"orgId":70
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			//var data2 = data1.data;
			
			document.getElementById("signinLoader").style.display="none";
			
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

			            // Roles as checkboxes
			            const uniqueRoles = [...new Map(user.userRole?.map(role => [role.roleDesc, role])).values()];
			            roleDescriptions.forEach(roleDesc => {
			                const roleCell = document.createElement("td");
			                const checkbox = document.createElement("input");
			                checkbox.type = "checkbox";

			                // Check if the user has the current role
			                checkbox.checked = uniqueRoles.some(role => role.roleDesc === roleDesc);

			                // Enable the checkbox and handle role change
			                checkbox.addEventListener("change", () => {
			                    if (checkbox.checked) {
			                        //alert(`Role "${roleDesc}" added to user ID ${user.id}`);
			                    } else {
			                       // alert(`Role "${roleDesc}" removed from user ID ${user.id}`);
			                    }
			                    // Handle backend update logic here
			                });

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

						
			           
						const editButton = document.createElement("button");
						editButton.className = "mr-3"; 
												
						// Add an image as an icon
						const editIcon = document.createElement("img");
						
						editIcon.src = "img/update.png"; 
						editIcon.style="height:15px"
						editIcon.style="width:15px"
						// Replace with the correct image path
						editIcon.alt = "update";
						editButton.appendChild(editIcon);

						editButton.title = "Update"; // Tooltip for accessibility
						editButton.addEventListener("click", () => {
							
						alert("Are you sure you want to update this user role");
			            // Retrieve the parent row of the button
			            const row = editButton.parentElement.parentElement;

					    // Extract basic user information
					    const cells = row.querySelectorAll("td");
					    const id = cells[0].textContent.trim();
					    const username = cells[1].textContent.trim();
					    const email = cells[2].textContent.trim();
					    const mobile = cells[3].textContent.trim();
		
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
		
					    // Create the row data object
					    const rowData = {
					        id,
					        username,
					        email,
					        mobile,
					        userRole,
					    };
		
					    // Display the row data
					    console.log(rowData);
						
						   /// for edit user role code
							var employerId = document.getElementById("employerId").value;
							var employername = document.getElementById("employerName").value;
							document.getElementById("signinLoader").style.display = "flex";
							$.ajax({
								type: "POST",
								url: "/editUserRole",
								dataType: 'json',
								data: {
									"orgId":70,
									"id":rowData.id,
									"username":rowData.username,
									"email":rowData.email,
									"mobile":rowData.mobile,
									"roleDesc":  rowData.userRole.map(role => role.roleDesc)
								},
								success: function(data) {
									var obj = data;
									document.getElementById("signinLoader").style.display = "none";
									
								},
								error: function(e) {
									alert('Error: ' + e);
								}
							});
						/// for edit user role code
						
					    //alert(JSON.stringify(rowData, null, 2));
					});
					actionCell.appendChild(editButton);
				
					deleteButton.addEventListener("click", () => {
				               // row.remove();
				               alert("Are you sure you want to delete this user role");
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
														"orgId":70,
														"createdby":employername,
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

							            });
							            actionCell.appendChild(deleteButton);
						
			        row.appendChild(actionCell);

			            // Append row to table
			            tableBody.appendChild(row);
			        }

			        // Populate table with initial data
			        data1.data.forEach(user => createRow(user));

			        // Add Row functionality
			       // document.getElementById("addRowButton").addEventListener("click", () => {
			        //    const newRow = { id: "", username: "New User", email: "newuser@example.com", mobile: "1234567890", userRole: [] };
			         //   createRow(newRow);
			       // });
			},
			error: function(e) {
				alert('Error: ' + e);
			}
		});
}

