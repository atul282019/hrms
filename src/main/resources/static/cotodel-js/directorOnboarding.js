
		function getVoucherList() {
		    const voucherDropdown = document.getElementById('VoucherType');
		    
		    $.ajax({
		        type: "GET",
		        url: "/getVoucherListWithIcon",
		        success: function(data) {
		            const parsedData = jQuery.parseJSON(data);
		            
		            // Clear existing options
		            voucherDropdown.innerHTML = '<option value="">Select Voucher Type</option>';
		            
		            // Populate Voucher Dropdown
		            parsedData.data.forEach(item => {
		                const option = document.createElement('option');
		                option.value = item.purposeCode;
		                option.textContent = item.voucherName;
		                
		                voucherDropdown.appendChild(option);
		            });
		        },
		        error: function(e) {
		            alert('Error: ' + e.responseText);
		        }
		    });
		}	
		function getSavedVoucherList() {
		    const employerId = document.getElementById('employerId').value;

		    $.ajax({
		        type: "GET",
		        url: "/getRequestedVoucherList",
		        data: { employerId },
		        success: function(data) {
		            const parseddata = JSON.parse(data);
		            console.log(data);
					var vouchers=parseddata.data;

		            // Get the table body
		            const tableBody = $("#reimbursementTable tbody");

		            // Clear existing table rows
		            tableBody.empty();

		            // Check if vouchers exist
		            if (vouchers.length === 0) {
		                tableBody.append(`<tr><td colspan="9" class="text-center">No Vouchers Found</td></tr>`);
		                return;
		            }

		            // Populate the table dynamically
		            vouchers.forEach(voucher => {
						const formattedDate = voucher.creationDate ? voucher.creationDate.split("T")[0] : "N/A";
						const maskedMobile = voucher.mobile 
						                    ? `XXX-XXX-${voucher.mobile.slice(-4)}` 
						                    : "N/A";
		                const row = `
		                    <tr>
		                        <td>${voucher.name}</td>
								<td>${voucher.employeeId}</td>
		                        <td>${voucher.voucherType}</td>
		                        <td>${voucher.voucherSubType}</td>
		                        <td>${maskedMobile}</td>
		                        <td>${voucher.amount}</td>
		                        <td>${formattedDate }</td>
								<td>${voucher.remarks || "N/A"}</td>
								<td>${voucher.statusMessage || "N/A"}</td>
		                        
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
					"name":DirectorName,
		            "din": Din,
		            "email": email, 
		            "mobile": mobno,
		            "designation": Designation,
		            "address": Address,
		            
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
