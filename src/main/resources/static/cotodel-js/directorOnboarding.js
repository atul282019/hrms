	
		function getDiretctorList() {
		    const employerId = document.getElementById('employerId').value;

		    $.ajax({
		        type: "GET",
		        url: "/getDirectorOnboarding",
		        data: { "orgId": employerId },
		        success: function(data) {
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
		                        <td>${director.orgId}</td>
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
		
		
		function saveDirectorData() {
			const employerId = document.getElementById('employerId').value;
			const createdby_name = document.getElementById('Name').value;
		    // Gather form data
			var DirectorName = document.getElementById("DirectorName").value.trim();
			var mobno = document.getElementById("mobno").value.trim();
			
			var Din = document.getElementById("Din").value.trim();
		    var email = document.getElementById("email").value.trim();
		    var Designation = document.getElementById("Designation").value.trim();
		    var Address = document.getElementById("Address").value.trim();

		    
		   
		    // Send the AJAX request
		    $.ajax({
		        type: "POST",
		        url: "/saveDirectorOnboarding",
		        data: {   
					"orgId": employerId,  
					"name":DirectorName,
		            "din": Din,
		            "email": email, 
		            "mobile": mobno,
		            "designation": Designation,
		            "address": Address,
					"createdby": createdby_name,
		            
		        },
		        dataType: "json",
		        success: function (response) {
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
		            alert("Error while saving data: " + error.responseText);
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
