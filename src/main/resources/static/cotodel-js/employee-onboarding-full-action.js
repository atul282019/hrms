
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
 

/* when there are more than one employee
function getEmployeeOnboardingByManagerId(managerId) {
    $.ajax({
        type: "GET",
        url: "/getEmployeeOnboardingByManagerId",
        data: { "managerId": managerId },
        success: function(response) {
            var data1 = jQuery.parseJSON(response);
            console.log(data1);

            if (data1.status && data1.data) {           
                let teamData = data1.data;
                let teamContainer = document.getElementById("reportingTeamWrap");
                let teamCount = document.getElementById("teamCount");
                let viewAllContainer = document.getElementById("viewAllContainer");
                let viewAllBtn = document.getElementById("viewAllBtn");

                teamContainer.innerHTML = "";  // Clear previous content
                teamCount.textContent = teamData.length; // Update team count

                teamData.forEach((member, index) => {
                    let isHidden = index >= 4 ? 'style="display: none;"' : ""; // Hide elements after 4

                    let teamMemberHTML = `
                        <div class="Reporting-team-info row" ${isHidden}>
                            <div class="col-3 text-center">
                                <img src="img/sm-profile.svg" alt="">
                            </div>
                            <div class="col-9">
                                <p>${member.name || "N/A"}</p>
                                <label>${member.jobTitle || "N/A"}</label>
                            </div>
                        </div>
                    `;

                    teamContainer.innerHTML += teamMemberHTML;
                });

                // Show "View All" button if more than 4 members exist
                if (teamData.length > 4) {
                    viewAllContainer.style.display = "block";
                } else {
                    viewAllContainer.style.display = "none";
                }

                // Add event listener for "View All" button
                viewAllBtn.onclick = function(event) {
                    event.preventDefault();
                    document.querySelectorAll(".Reporting-team-info.row").forEach(el => {
                        el.style.display = "flex"; // Show all elements
                    });
                    viewAllContainer.style.display = "none"; // Hide button
                };

            } else {
                console.log("No data found for the given Manager ID");
            }
        },
        error: function(error) {
            console.log("Error fetching data: " + error.responseText);
        }
    });
} */	
	

 
function getEmployeeOnboardingByManagerId(managerId) {
	   // var reportingManagerid = document.getElementById("reportingManagerid").value;
		//managerId=561;
	    $.ajax({
	        type: "GET",
	        url: "/getEmployeeOnboardingByManagerId",
	        data: { "managerId": managerId },
	        success: function(response) {
				
				var data1 = jQuery.parseJSON(response);
				console.log(data1);
				
	            if (data1.status && data1.data) {           
					var data3 = data1.data[0];
					console.log(data3);
				document.getElementById("reportingName1").textContent = data3.name || "N/A";
				document.getElementById("mngrPosition").textContent = data3.jobTitle || "N/A";
	           
					
	            } else {
	                console.log("No data found for the given Manager ID");
	            }
	        },
	        error: function(error) {
	            console.log("Error fetching data: " + error.responseText);
	        }
	    });
	}
	
	function getEmployeeOnboardingById() {
		   // var employeeId = document.getElementById("employeeId").value;
			const employeeId = sessionStorage.getItem("employeeId");
		    $.ajax({
		        type: "GET",
		        url: "/getEmployeeOnboardingById",
		        data: { "id": employeeId },
		        success: function(response) {
					//console.log(response);
					var data1 = jQuery.parseJSON(response);
							
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
						$("#payIncrease").text(data.herDate || "N/A");
						$("#praise").text(data.herDate || "N/A");
						$("#profileEmail").text(data.email || "N/A");
						$("#mngrName").text(data.managerName || "N/A");
						$("#location").text(data.location || "N/A");
						
						getEmployeeOnboardingByManagerId(data.managerId);
						
		            } else {
		                console.log("No data found for the given Employee ID");
		            }
		        },
		        error: function(error) {
		            console.log("Error fetching data: " + error.responseText);
		        }
		    });
		}