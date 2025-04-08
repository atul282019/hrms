	
	async function getDirectorList() {
		    const employerId = document.getElementById('employerId').value;
			document.getElementById("signinLoader").style.display="flex";
			
			const clientKey = "client-secret-key"; // Extra security measure
			    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

			    // Concatenate data (must match backend)
			    const dataString = employerId+clientKey+secretKey;

			    // Generate SHA-256 hash
			    const encoder = new TextEncoder();
			    const data = encoder.encode(dataString);
			    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
			    const hashArray = Array.from(new Uint8Array(hashBuffer));
			    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

			    // Prepare request payload
			    const requestData = {
					orgId:employerId,
			        key: clientKey,  // Extra key for validation
			        hash: hashHex
			    };

		    $.ajax({
		        type: "POST",
		        url: "/getDirectorOnboarding",
				contentType: "application/json",
			    data: JSON.stringify(requestData),
		        success: function(data) {
					document.getElementById("signinLoader").style.display="none";
		            const parseddata = JSON.parse(data);
		            console.log(data);
					var director=parseddata.data;

		            // Get the table body
		            const tableBody = $("#reimbursementTable tbody");

		            // Clear existing table rows
		            tableBody.empty();

		            // Check if vouchers exist
		            if (director.length === 0) {
		                tableBody.append(`<tr><td colspan="9" class="text-center">No Directors Found</td></tr>`);
		                return;
		            }

		            // Populate the table dynamically
		            director.forEach(director=> {
						const formattedDate = director.creationDate ? director.creationDate.split("T")[0] : "N/A";
		                const row = `
		                    <tr>
		                        <td>${director.id}</td>
								<td>${director.name}</td>
								<td>${director.email}</td>
								<td>${director.mobile}</td>
								<td>${director.din}</td>
								<td>${director.designation}</td>
								<td>${director.address}</td>
								<td>${director.createdby}</td>
								<td>${formattedDate}</td>
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
		
		
async function saveDirectorData() {
			const employerId = document.getElementById('employerId').value;
			const createdby_name = document.getElementById('Name').value;
		    // Gather form data
			var DirectorName = document.getElementById("DirectorName").value.trim();
			var mobno = document.getElementById("mobno").value.trim();
			
			var Din = document.getElementById("Din").value.trim();
		    var email = document.getElementById("email").value.trim();
		    var Designation = document.getElementById("Designation").value.trim();
		    var Address = document.getElementById("Address").value.trim();

			var nameRegex = /^[A-Za-z\s]+$/;  // Only letters & spaces
			    var mobileRegex = /^\d{10}$/;  // Exactly 10 digits
			    var dinRegex = /^[A-Za-z0-9]{1,8}$/;  // Exactly 8 alphanumeric characters
			    var emailRegex = /^[a-zA-Z0-9]+(?:\.[a-zA-Z0-9]+)?@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // Valid email format

			    // Validation for Director Name
			    if (DirectorName === "" || !nameRegex.test(DirectorName)) {
			        document.getElementById("DirectorNameError").innerHTML = "Please enter a valid Name (only letters and spaces are allowed)";
			        document.getElementById("DirectorName").focus();
			        return false;
			    } else {
			        document.getElementById("DirectorNameError").innerHTML = "";
			    }

			    // Validation for Mobile Number
			    if (!mobileRegex.test(mobno)) {
			        document.getElementById("mobnoError").innerHTML = "Please enter a valid 10-digit Mobile Number";
			        document.getElementById("mobno").focus();
			        return false;
			    } else {
			        document.getElementById("mobnoError").innerHTML = "";
			    }

			    // Validation for DIN (Director Identification Number) - 8 alphanumeric characters
			    if (!dinRegex.test(Din)) {
			        document.getElementById("DinError").innerHTML = "DIN must be exactly 8 alphanumeric characters";
			        document.getElementById("Din").focus();
			        return false;
			    } else {
			        document.getElementById("DinError").innerHTML = "";
			    }

			    // Validation for Email
			    if (!emailRegex.test(email)) {
			        document.getElementById("emailError").innerHTML = "Please enter a valid Email Address";
			        document.getElementById("email").focus();
			        return false;
			    }
				else if(email=="")
				{					
					document.getElementById("emailError").innerHTML = "Please enter Email Address";
					        document.getElementById("email").focus();
					        return false;} 
				else {
			        document.getElementById("emailError").innerHTML = "";
			    }

			    // Validation for Designation
			    if (Designation === "" || !nameRegex.test(Designation)) {
			        document.getElementById("DesignationError").innerHTML = "Please enter a valid Designation (only letters and spaces are allowed)";
			        document.getElementById("Designation").focus();
			        return false;
			    } else {
			        document.getElementById("DesignationError").innerHTML = "";
			    }

			    // Validation for Address
			    if (Address === "") {
			        document.getElementById("AddressError").innerHTML = "Please enter an Address";
			        document.getElementById("Address").focus();
			        return false;
			    } else {
			        document.getElementById("AddressError").innerHTML = "";
			    }

				document.getElementById("signinLoader").style.display="flex";

	        const clientKey = "client-secret-key"; // Extra security measure
	        const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	        // Concatenate data (must match backend)
	        const dataString = employerId+DirectorName+Din+email+mobno+Designation+Address+createdby_name+clientKey+secretKey;

	        // Generate SHA-256 hash
	        const encoder = new TextEncoder();
	        const data = encoder.encode(dataString);
	        const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	        const hashArray = Array.from(new Uint8Array(hashBuffer));
	        const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

	        // Prepare request payload
	        const requestData = {
				orgId : employerId,  
				name:DirectorName,
	            din: Din,
	            email: email, 
	            mobile: mobno,
	            designation: Designation,
	            address: Address,
				createdby: createdby_name,
	            key: clientKey,  // Extra key for validation
	            hash: hashHex
	        };
		    $.ajax({
		        type: "POST",
		        url: "/saveDirectorOnboarding",
				contentType: "application/json",
				data: JSON.stringify(requestData),
		        dataType: "json",
		        success: function (response) {
					document.getElementById("signinLoader").style.display="none";
		            if (response.status ===true) {
		               $("#AddDirectorSuccess").show();
					   setTimeout(function () {
					                       $('.modal').modal('hide'); // Close all modals
					                       location.reload(); // Refresh the page
					                   },1000);
		            } else {
		                $("#otfailmsg").text(response.message || "Failed to save data.");
		                $("#otfailmsgDiv").show();
		            }
		        },
		        error: function (error) {
					console.log(error);
		             alert("Error while saving data: " + (error.responseJSON?.error || "Unknown error"));
					 
		        }
		    });
		}
		function resetFormFields() {
		    document.getElementById("DirectorName").value = "";
		    document.getElementById("Din").value = "";
		    document.getElementById("email").value = "";
		    document.getElementById("Designation").value = "";
		    document.getElementById("Address").value = "";
		    document.getElementById("mobno").value = "";

		    // Reset dropdowns
		    
			$("#otmsgdiv").hide();
			
		}
