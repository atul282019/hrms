
  function resetErrorMessages() {
        const errorFields = [
            "nameError",
            "mobileError",
            "emailError",
            "hireDateError",
            "jobTitleError",
            "departmentError",
            "reportingError",
            "salaryError",
            "locationError",
            "residenceError"
        ];

        errorFields.forEach((field) => {
            document.getElementById(field).textContent = "";
        });
    }
 

	function getEmployeeOnboardingById() {
		   // var employeeId = document.getElementById("employeeId").value;
			const employeeId = sessionStorage.getItem("employeeId");
			const userDetailsId = sessionStorage.getItem("userDetailsId");
		    $.ajax({
		        type: "GET",
		        url: "/getEmployeeOnboardingById",
		        data: { "id": employeeId },
		        success: function(response) {
					//console.log(response);
					var data1 = jQuery.parseJSON(response);
							console.log("data 1  getEmployeeOnboardingById()",data1);
		            if (data1.status && data1.data) {
		                var data = data1.data;
		                
		                // Splitting the full name (Assumption)
		          /*      var nameParts = data.name ? data.name.split(" ") : ["", "", ""];
		                var firstName = nameParts[0] || "";
		                var middleName = nameParts.length > 2 ? nameParts[1] : "";
		                var lastName = nameParts.length > 2 ? nameParts[2] : nameParts[1] || "";*/

		                // Populate fields
						$("#fullName").text(data.name);
		                $("#firstName").text(data.name);
		                
		                $("#gender").text("N/A"); // No gender field in response
		                $("#dob").text("N/A"); // No DOB field in response
		                $("#maritalStatus").text("N/A"); // No marital status in response
		                $("#handicapped").text("N/A"); // No handicapped status in response

		                // Other details
		                $("#email").text(data.email || "N/A");
		                $("#mobile").text(data.mobile || "N/A");
		                $("#designation").text(data.jobTitle || "N/A");
		                $("#reportingManager").text(data.managerName || "N/A");
		                $("#empType").text(data.empOrCont || "N/A");
		                $("#herDate").text(data.herDate || "N/A");
						$("#depratment").text(data.depratment|| "N/A");
						$("#workAniv").text(data.herDate || "N/A");
						//$("#payIncrease").text(data.herDate || "N/A");
						
						var payIncreaseDate = data.herDate ? new Date(data.herDate) : null;

						if (payIncreaseDate) {
						    // Add one year
						    payIncreaseDate.setFullYear(payIncreaseDate.getFullYear() + 1);

						    // Format date to YYYY-MM-DD (adjust format if needed)
						    var formattedDate = payIncreaseDate.toISOString().split("T")[0];

						    // Set the updated date
						    $("#payIncrease").text(formattedDate);}
						
						$("#praise").text(formattedDate);
						$("#profileEmail").text(data.email || "N/A");
						$("#mngrName").text(data.managerName || "N/A");
						$("#location").text(data.location || "N/A");
						
						var profileImageBase64 = data.empPhoto; // Assuming API returns this field
						        var defaultImage = "img/no-image-available.png"; // Default profile picture

						        if (profileImageBase64) {
						            // Assuming the image is a PNG or JPEG, update the `src` dynamically
						            $(".profile-pic01").attr("src", "data:image/png;base64," + profileImageBase64);
						        } else {
						            $(".profile-pic01").attr("src", defaultImage);
						        }
						$("#Id").val(data.id);
						$("#employerId").val(data.employerId);
						$("#userDetailsId").val(data.userDetailsId);

						        //getEmployeeOnboardingByManagerId(data.managerId);
								getEmployeeOnboardingByManagerId(userDetailsId);
						    } else {
						        console.log("No data found for the given Employee ID");
						    }
					
					
		        },
		        error: function(error) {
		            console.log("Error fetching data: " + error.responseText);
		        }
		    });
		}
		function getEmployeeOnboardingByManagerId(managerId) {
			    $.ajax({
			        type: "GET",
			        url: "/getEmployeeOnboardingByManagerId",
			        data: { "managerId": managerId },
			        success: function(response) {
			            var data = JSON.parse(response);
			            console.log(data);

			            if (data.status && data.data.length > 0) {
			                var teamWrap = document.querySelector(".Reporting-team-wrap");
			                teamWrap.innerHTML = ""; // Clear previous entries
			                
			                // Update team count
			               document.getElementById("teamCount").textContent = data.data.length;

						   data.data.forEach(employee => {
						                      var empPhotoSrc = employee.empPhoto 
						                          ? `data:image/png;base64,${employee.empPhoto}` 
						                          : 'img/sm-profile.svg'; // Default image

						                      var empHTML = `
						                          <div class="Reporting-team-info row">
						                              <div class="col-3 text-center">
						                                  <img src="${empPhotoSrc}" alt="" style="width: 50px; height: 50px; border-radius: 50%;">
						                              </div>
						                              <div class="col-9">
						                                  <p>${employee.name}</p>
						                                  <label for="">${employee.jobTitle || 'N/A'}</label>
						                              </div>
						                          </div>`;
						                      teamWrap.innerHTML += empHTML;
						                  });

			                // Append "View All" link if more than a few employees exist
			                if (data.data.length > 5) {
			                    teamWrap.innerHTML += `
			                        <div class="Reporting-team-info row mb-0">
			                            <div class="col-12">
			                                <a href="#">View All</a>
			                            </div>
			                        </div>`;
			                }
			            } else {
			                console.log("No employees found for the given Manager ID");
			            }
			        },
			        error: function(error) {
			            console.log("Error fetching data: " + error.responseText);
			        }
			    });
			}
			function autoFillEmployeeForm() {
			    const employeeId = sessionStorage.getItem("employeeId");
				$("#Id").val(employeeId);

			    if (!employeeId) {
			        console.log("No Employee ID found in session.");
			        return;
			    }

			    $.ajax({
			        type: "GET",
			        url: "/getEmployeeOnboardingById",
			        data: { "id": employeeId },
			        success: function(response) {
			            var data1 = jQuery.parseJSON(response);

			            if (data1.status && data1.data) {
			                var data = data1.data;

			                // Fill form fields
			                $("#name").val(data.name || "");
                            $("#mobile").val(data.mobile || "").prop("readonly", data.mobile ? true : false);
			                $("#email").val(data.email || "");
			               // $("#hireDate").val(data.herDate || "");
			                $("#jobTitle").val(data.jobTitle || "");
			                $("#salary").val(data.salary || "");
							$("#profilePhotoBase64").val(data.empPhoto);
			                // Populate dropdowns
			                //$("#department").val(data.department || "");
			                //$("#reporting").val(data.managerId || "");
			                //$("#location").val(data.location || "");

			                
			                
			            } else {
			                console.log("No data found for the given Employee ID.");
			            }
			        },
			        error: function(error) {
			            console.log("Error fetching data: " + error.responseText);
			        }
			    });
			}
			function deactivateEmployee() {
			    const employerId = document.getElementById("employerId").value;
			    const Id = document.getElementById("Id").value;
			    const userDetailsId = document.getElementById("userDetailsId").value;

			    if (!Id) {
			        alert("Employee ID is missing.");
			        return;
			    }

			    if (confirm("Are you sure you want to deactivate this employee?")) {
			        $.ajax({
			            type: "POST",
			            url: "/deactiveEmployee",  // Change to the actual API endpoint
			            data: {
			                "employerId": employerId,
			                "id": Id,
			                "userDetailsId": userDetailsId,
			                "status": "Deactive"
			            },
			             dataType: "json", 
			            success: function(response) {
			                alert("Employee deactivated successfully!");
			                window.location.href="/manageEmployee"; // Reload the page to reflect changes
			            },
			            error: function(error) {
			                alert("Error deactivating employee: " + error.responseText);
			            }
			        });
			    }
			}
