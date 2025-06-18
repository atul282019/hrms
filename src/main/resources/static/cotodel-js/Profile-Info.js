async function getEmployeeOnboardingById() {
		   // var employeeId = document.getElementById("employeeId").value;
			const employeeId = document.getElementById("orgId").value;
			const userDetailsId = sessionStorage.getItem("userDetailsId");
			//document.getElementById
			const clientKey = "client-secret-key"; // Extra security measure
			const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

			    // Concatenate data (must match backend)
			    const dataString = employeeId+clientKey+secretKey;

			    // Generate SHA-256 hash
			    const encoder = new TextEncoder();
			    const data = encoder.encode(dataString);
			    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
			    const hashArray = Array.from(new Uint8Array(hashBuffer));
			    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
				const requestData = {
						id:employeeId,
				        key: clientKey,  // Extra key for validation
				        hash: hashHex
				    };
		    $.ajax({
		        type: "POST",
		        url: "/getEmployeeDetailByEmployeeId",
				contentType: "application/json",
				data: JSON.stringify(requestData),
		        success: function(response) {
					//console.log(response);
					var data1 = jQuery.parseJSON(response);
							console.log("data 1  getEmployeeOnboardingById()",data1);
		            if (data1.status && data1.data) {
		                var data = data1.data;

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
	async function getEmployeeOnboardingByManagerId(managerId) {
		

		const clientKey = "client-secret-key"; // Extra security measure
		const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

		// Concatenate data (must match backend)
		const dataString = managerId+clientKey+secretKey;

		// Generate SHA-256 hash
		const encoder = new TextEncoder();
		const data = encoder.encode(dataString);
		const hashBuffer = await crypto.subtle.digest("SHA-256", data);
		const hashArray = Array.from(new Uint8Array(hashBuffer));
		const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

		// Prepare request payload
		const requestData = {
			managerId:managerId,
		    key: clientKey,  // Extra key for validation
		    hash: hashHex
		};
			    $.ajax({
			        type: "POST",
			        url: "/getEmployeeOnboardingByManagerId",
					contentType: "application/json",
				    data: JSON.stringify(requestData),
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