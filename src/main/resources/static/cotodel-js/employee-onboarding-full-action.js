
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
 

	async function getEmployeeOnboardingById() {
		   // var employeeId = document.getElementById("employeeId").value;
			const employeeId = sessionStorage.getItem("employeeId");
			const userDetailsId = sessionStorage.getItem("userDetailsId");
			
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
			async function autoFillEmployeeForm() {
							//this is id only but i have chanaged the name in session while getting to avoid confusion
						    const id = sessionStorage.getItem("employeeId");
							var employerId=document.getElementById("employerId").value;
							$("#Id").val(id);
			 
						    if (!id) {
						        console.log("No Employee ID found in session.");
						        return;
						    }
							const clientKey = "client-secret-key"; // Extra security measure
						    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND
			 
						    // Concatenate data (must match backend)
						    const dataString = id+employerId+clientKey+secretKey;
			 
						    // Generate SHA-256 hash
						    const encoder = new TextEncoder();
						    const data = encoder.encode(dataString);
						    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
						    const hashArray = Array.from(new Uint8Array(hashBuffer));
						    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
									
			 
							const requestData = {
											id:id,
											employerId:employerId,
									        key: clientKey,  // Extra key for validation
									        hash: hashHex
									    };
										
									$.ajax({
									type: "POST",
									url:"/getEmployeeOnboardingById",
									contentType: "application/json",
														data: JSON.stringify(requestData),
							       		  beforeSend : function(xhr) {
											//xhr.setRequestHeader(header, token);
											},
							            success: function(response){
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
			            url: "/toggleEmployee",  // Change to the actual API endpoint
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
			function formatDate(dateStr) {
			    if (!dateStr) return '';
			    const date = new Date(dateStr);
			    const day = String(date.getDate()).padStart(2, '0');
			    const month = String(date.getMonth() + 1).padStart(2, '0');
			    const year = date.getFullYear();
			    return `${day}-${month}-${year}`;
			}
			function erupiVoucherCreateListLimit() {
			  var employerid = document.getElementById("orgId").value;
			  var mobile = document.getElementById("mob").value;
			  var timePeriod = "AH";

			  $.ajax({
			    type: "POST",
			    url: "/erupiVoucherCreateListLimit",
			    data: {
			      "orgId": employerid,
			      "timePeriod": timePeriod,
			      "mobile": mobile
			    },
			    success: function (data) {
			      var data1 = jQuery.parseJSON(data);
			      var data2 = data1.data;
			      console.log("datad for /erupiVoucherCreateListLimit ", data2);

			      var table = $('#vouchersTableList').DataTable({
			        destroy: true,
			        lengthChange: true,
			        responsive: true,
			        searching: false,
			        bInfo: false,
			        paging: false,
			        autoWidth: false,
			        pageLength: 50,
			        buttons: ["csv", "excel"],
			        language: {
			          "emptyTable": 'As per the last update, currently there are no UPI Vouchers transactions recorded...'
			        },
			        aaData: data2,
			        aoColumns: [
			          {
			            "mData": "creationDate",
			            "render": function (data) {
			              return formatDate(data);
			            }
			          },
			          {
			            "mData": null,
			            "render": function (data, type, row) {
			              return `<div>${row.name}</div><div>${row.mobile}</div>`;
			            }
			          },
			          { "mData": "merchanttxnId" },
			          {
			            "mData": null,
			            "render": function (data, type, row) {
			              const base64Icon = row.bankIcon;
			              const imgHTML = base64Icon
			                ? `<img src="data:image/png;base64,${base64Icon}" alt="Bank Icon" width="24" height="24">`
			                : '';
			              const maskedAccount = row.accountNumber && row.accountNumber.length >= 4
			                ? `xxxx${row.accountNumber.slice(-4)}`
			                : row.accountNumber || '';
			              return `<div style="display: flex; align-items: center; gap: 8px;">${imgHTML}<span>${maskedAccount}</span></div>`;
			            }
			          },
			          {
			            "mData": null,
			            "render": function (data, type, row) {
			              const mccIcon = row.mccMainIcon;
			              const imgHTML = mccIcon
			                ? `<img src="data:image/png;base64,${mccIcon}" alt="MCC Icon" width="24" height="24">`
			                : '';
			              return `<div style="display: flex; align-items: center; gap: 8px;">${imgHTML}<span>${row.purposeDesc || ''}</span></div>`;
			            }
			          },
			          {
			            "mData": "redemtionType",
			            "render": function (data) {
			              const normalized = (data || '').toLowerCase();
			              const label = normalized === "multiple" ? "Multiple"
			                : normalized === "single" ? "Single"
			                : data;
			              const redemptionImage = label === "Single"
			                ? `<img src="img/single-redemption-green.svg" alt="Single" width="24" height="24" style="margin-left: 8px;">`
			                : label === "Multiple"
			                  ? `<img src="img/tiffinbox.svg" alt="Multiple" width="24" height="24" style="margin-left: 8px;">`
			                  : '';
			              return `<div style="display: flex; align-items: center;">${redemptionImage}${label}</div>`;
			            }
			          },
			          {
			            "mData": "expDate",
			            "render": function (data) {
			              return formatDate(data);
			            }
			          },
			          {
			            "mData": "type",
			            "render": function (data, type, row) {
			              const expDate = new Date(row.expDate);
			              const today = new Date();
			              const isExpired = expDate < today;
			              let labelText = '', labelClass = '';
			              if (isExpired && row.type === "Created") {
			                labelText = "Expired";
			                labelClass = "pill bg-grey-txt-grey-pill";
			              } else {
			                switch (data) {
			                  case "Created": labelText = "Active"; labelClass = "pill bg-lightgreen-txt-green-pill"; break;
			                  case "Redeemed": labelText = "Redeemed"; labelClass = "pill-redeemed bg-grey-txt-grey-pill "; break;
			                  case "fail": labelText = "Failed"; labelClass = "pill bg-lightred-txt-red-pill "; break;
			                  case "Revoke": labelText = "Revoked"; labelClass = "pill bg-lightyellow-txt-yellow-pill"; break;
			                  default: labelText = data; labelClass = "pill bg-lightgrey-txt-grey-pill";
			                }
			              }
			              return `<span class="${labelClass}">${labelText}</span>`;
			            }
			          },
			          {
			            "mData": "amount",
			            "class": "text-right",
			            "render": function (data2) {
			              if (!data2) return '';
			              let amount = parseFloat(data2).toFixed(2);
			              return '<div class="amount-cell">₹' + parseFloat(amount).toLocaleString('en-IN', {
			                minimumFractionDigits: 2,
			                maximumFractionDigits: 2
			              }) + '</div>';
			            }
			          },
					  {
					    "mData": "redeemAmount",
					    "class": "text-right",
					    "render": function (data2) {
					      let amount = parseFloat(data2 || 0).toFixed(2);
					      return '<div class="amount-cell">₹' + parseFloat(amount).toLocaleString('en-IN', {
					        minimumFractionDigits: 2,
					        maximumFractionDigits: 2
					      }) + '</div>';
					    }
					  }
			        ]
			      });

			    },
			    error: function (e) {
			      alert('Failed to fetch JSON data' + e);
			    }
			  });
			}
			
			async function getVoucherTransactionList() {
						var orgId = document.getElementById("orgId").value;
					  	var mobile = document.getElementById("mob").value;
					    $.ajax({
					        type: "POST",
					        url: "/getVoucherTransactionList",
							data: { 
									"orgId":orgId,
									"mobile":mobile,
									"timePeriod":"AH"	
							 },
					        beforeSend: function(xhr) {
					            //xhr.setRequestHeader(header, token);
					        },
					        success: function(data) {
					            newData = data;
					            console.log("Emp onboarding data", newData);
					            var data1 = jQuery.parseJSON(newData);
					            var data2 = data1.data;
								console.log(" upi voucher issue getVoucherTransactionList()=",data1);
					            
					            var table = $('#vouchersTableTransactionList').DataTable({
					                destroy: true,
					                "responsive": true,
					                searching: false,
					                bInfo: false,
					                paging: false,
					                "lengthChange": true,
					                "autoWidth": false,
					                "pagingType": "full_numbers",
					                "pageLength": 50,
					                "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
					                "language": {
										"emptyTable": 'As per the last update, currently there are no UPI Vouchers transactions recorded on the platform. If your team members have redeemed</br> a UPI Voucher already, please refresh and check again at the end of the day to view corresponding transactions.</br>If your team and you haven’t already, start issuing and using <a href="/upiVoucherIssuanceNew">UPI Vouchers</a> to experience the magic!'
										},
					                
					                "aaData": data2,
					                "aoColumns": [
					                  //  { "mData": "creationDate" },
										{ 
										  "mData": "creationDate", 
										  "render": function (data) {
										      return formatDate(data);
										  }
										},
										{ "mData": "merchanttxnId" },
										{ "mData": "bankrrn" },
										{
										  "mData": null,
										  "render": function (data, type, row) {
										    return `<div>${row.name}</div><div>${row.mobile}</div>`;
										  }
										},
										{
										  "mData": "purposeDesc",
										  "render": function (data, type, row) {
										   
										      return '<img src="data:image/png;base64,' + row.mccMainIcon + '" alt="" width="24px" height="24px" style="margin-top:-5px;">'+ data;
										    
										  }
										},
										{ "mData": "payeeName" },
										{
										  "mData": "redeemAmount",
										"class":"text-right",
										"render": function (data2, type, row) {
										    if (!data2) return '';
										    let amount = parseFloat(data2);
										    let formattedAmount = amount.toFixed(2); // enforce 2 decimal places
										    let localizedAmount = parseFloat(formattedAmount).toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
										    return '<div class="amount-cell">₹' + localizedAmount + '</div>';
										}
										},
										
										//{ "mData": "merchanttxnId" },
										
					                ],

									
							});		
												
					        },
					        error: function(e) {
					            alert('Failed to fetch JSON data' + e);
					        }
					    });
					}