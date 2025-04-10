/*function getVoucherListWithIcon() {
	        const voucherList = document.getElementById('voucherList');
	        const purposeList = document.getElementById('purposeList');
	        
	        $.ajax({
	            type: "GET",
	            url: "/getVoucherListWithIcon",
	            success: function(data) {
	                const parsedData = jQuery.parseJSON(data);

	                // Populate Voucher List
	                parsedData.data.forEach(item => {
	                    const li = document.createElement('li');
	                    li.classList.add('voucher-item');
	                    li.setAttribute('data-value', item.purposeCode);

	                    const a = document.createElement('a');
	                    a.href = "javascript:void(0);";

	                    const img = document.createElement('img');
	                    img.src = item.voucherIcon 
	                        ? `data:image/png;base64,${item.voucherIcon}`
	                        : '';
	                    img.classList.add('img-fluid');
						img.style.height = '20px';
						img.style.width = '20px';
	                    const span = document.createElement('span');
	                    span.textContent = item.voucherName;

	                    a.appendChild(img);
	                    a.appendChild(span);
	                    li.appendChild(a);
	                    voucherList.appendChild(li);
	                });

	                // Add click event listener to Voucher List
	                voucherList.addEventListener('click', event => {
	                    const clickedItem = event.target.closest('li'); // Get the clicked LI element
	                    if (clickedItem) {
	                        const purposeCode = clickedItem.getAttribute('data-value');
	                        const voucherIcon = clickedItem.querySelector('img').src;
	                        const voucherName = clickedItem.querySelector('span').textContent;

	                        // Bind the selected image and voucher name to specific elements
	                       
	                        // Clear previous selections
	                        [...voucherList.children].forEach(item => item.classList.remove('selected'));
	                        [...purposeList.children].forEach(item => item.remove()); // Clear Purpose List

	                        // Highlight the selected item
	                        clickedItem.classList.add('selected');

	                        // Fetch Purpose List using the selected purposeCode
	                        fetchPurposeListByVoucherCode(purposeCode);
	                    }
						document.getElementById("mccDiv").style.display="block";
	                });
	            },
	            error: function(e) {
	                alert('Error: ' + e.responseText);
	            }
	        });
	    }*/
		function getVoucherList() {
		    const voucherDropdown = document.getElementById('VoucherType');
			document.getElementById("signinLoader").style.display="flex";
		    $.ajax({
		        type: "GET",
		        url: "/getVoucherListWithIcon",
		        success: function(data) {
					document.getElementById("signinLoader").style.display="none";
		            const parsedData = jQuery.parseJSON(data);
					console.log("parsed data for /getVoucherListWithIcon",parsedData);
		            
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
		    const employeeId = document.getElementById('empId').value;
			document.getElementById("signinLoader").style.display="flex";
		    $.ajax({
		        type: "GET",
		        url: "/getRequestedVoucherList",
		        data: { employeeId },
		        success: function(data) {
					document.getElementById("signinLoader").style.display="none";
		            const parseddata = JSON.parse(data);
		            
					var vouchers=parseddata.data;
					console.log("getSavedVoucherList()",vouchers);
					

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
						//const maskedMobile = voucher.mobile 
						 //                   ? `XXX-XXX-${voucher.mobile.slice(-4)}` 
						  //                  : "N/A";
		                const row = `
		                    <tr>
		                        <td>${voucher.name}</td>
								<td>${voucher.employeeId}</td>
		                        <td>${voucher.voucherType}</td>
		                        <td>${voucher.voucherSubType}</td>
								<td>${voucher.purposeCode}</td>
								<td>${voucher.mcc}</td>
		                        <td>${voucher.mobile}</td>
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
		
		/*document.getElementById('VoucherType').addEventListener('change', function () {
		    const selectedVoucherCode = this.value;
		    if (selectedVoucherCode) {
		        fetchVoucherSubTypes(selectedVoucherCode);
		    } else {
		        document.getElementById('VoucherSubType').innerHTML = '<option value="">Select Voucher Sub Type</option>';
		    }
		});	*/

	    /*function fetchPurposeListByVoucherCode(purposeCode) {
	        const purposeList = document.getElementById('purposeList');

	        $.ajax({
	            type: "GET",
	            url: "/getPurposeListByVoucherCode",
	            data: { purposeCode },
	            success: function(data) {
	                const parsedData = jQuery.parseJSON(data);

	                // Populate Purpose List with radio buttons
					const imageElement = document.getElementById('voucherImageId');
					const textElement = document.getElementById('voucherTextId');

					
			       imageElement.src = parsedData.mccMainIcon.mccMainIcon
						                        ? `data:image/png;base64,${parsedData.mccMainIcon.mccMainIcon}`
						                        : 'img/fuelVouchers.svg'; // Set image
					textElement.textContent = parsedData.mccMainIcon.voucherName ? parsedData.mccMainIcon.voucherName : "";

	                parsedData.data.forEach(item => {
	                    const li = document.createElement('li');
	                    li.classList.add('purpose-item');
	                    li.setAttribute('data-value', item.mcc);

	                    // Create radio input
	                    const radioInput = document.createElement('input');
	                    radioInput.type = 'radio';
	                    radioInput.classList.add('voucher-radioinput');
	                    radioInput.name = 'selectvouchers'; // Group radio buttons by name

	                    // Create img element
	                    const img = document.createElement('img');
	                    img.src = item.mccIcon
	                        ? `data:image/png;base64,${item.mccIcon}`
	                        : '';
	                   // img.alt = item.mccDesc;
	                    img.classList.add('img-fluid');
						img.style.height = '20px';
						img.style.width = '20px';
	                    // Create span element with voucher name
	                    const span = document.createElement('span');
	                    span.textContent = item.mccDesc;

	                    // Append elements to li
	                    li.appendChild(radioInput);
	                    li.appendChild(img);
	                    li.appendChild(span);
	                    purposeList.appendChild(li);
	                });
	            },
	            error: function(e) {
	                alert('Error: ' + e.responseText);
	            }
	        });
	    }*/
		
		/*function fetchVoucherSubTypes(purposeCode) {
		    const subTypeDropdown = document.getElementById('VoucherSubType');

		    $.ajax({
		        type: "GET",
		        url: "/getPurposeListByVoucherCode",
		        data: { purposeCode },
		        success: function (data) {
		            const parsedData = jQuery.parseJSON(data);

		            // Clear existing options
		            subTypeDropdown.innerHTML = '<option value="">Select Sub Type</option>';

		            // Populate Sub Type Dropdown
		            parsedData.data.forEach(item => {
		                const option = document.createElement('option');
		                option.value = item.mcc;
		                option.textContent = item.mccDesc;

		                subTypeDropdown.appendChild(option);
		            });
		        },
		        error: function (e) {
		            alert('Error: ' + e.responseText);
		        }
		    });
		}*/
		
		async function saveVoucherData() {
		    // Gather form data
			var name = document.getElementById("Name").value.trim();
			var employeeId = document.getElementById("empId").value.trim();
			
			var employerId = document.getElementById("employerId").value.trim();
		    var mobile = document.getElementById("mobno").value.trim();
		    var amount = document.getElementById("VoucherAmount").value.trim();
		    var remarks = document.getElementById("VoucherRemarks").value.trim();
			
			var voucherTypeDropdown = document.getElementById("VoucherType");
			 var purposeCode = voucherTypeDropdown.value; // Getting the purposeCode

			 var voucherSubTypeDropdown = document.getElementById("VoucherSubType");
			 var mcc = voucherSubTypeDropdown.value; // Getting the mcc

		    // Fetch selected text for Voucher Type
		    var voucherTypeDropdown = document.getElementById("VoucherType");
		    var voucherType = voucherTypeDropdown.options[voucherTypeDropdown.selectedIndex].text;

		    // Fetch selected text for Voucher Sub Type
		    var voucherSubTypeDropdown = document.getElementById("VoucherSubType");
		    var voucherSubType = voucherSubTypeDropdown.options[voucherSubTypeDropdown.selectedIndex].text;
			
			if (purposeCode === "") {
			        document.getElementById("VoucherTypeerror").innerHTML = "Please select a Voucher Type";
			        voucherTypeDropdown.focus();
			        return false;
			    } else {
			        document.getElementById("VoucherTypeerror").innerHTML = "";
			    }

			    // Validation for Voucher Sub Type
			    if (mcc === "") {
			        document.getElementById("VoucherSubTypeerror").innerHTML = "Please select a Voucher Sub Type";
			        voucherSubTypeDropdown.focus();
			        return false;
			    } else {
			        document.getElementById("VoucherSubTypeerror").innerHTML = "";
			    }

			    // Validation for Voucher Amount
			    if (amount === "" || isNaN(amount) || Number(amount) <= 0) {
			        document.getElementById("VoucherAmountError").innerHTML = "Please enter a valid Voucher Amount";
			        document.getElementById("VoucherAmount").focus();
			        return false;
			    } else {
			        document.getElementById("VoucherAmountError").innerHTML = "";
			    }

			    // Validation for Remarks
			    if (remarks === "") {
			        document.getElementById("VoucherRemarksError").innerHTML = "Please enter Remarks";
			        document.getElementById("VoucherRemarks").focus();
			        return false;
			    } else {
			        document.getElementById("VoucherRemarksError").innerHTML = "";
			    }
				
				if (amount === "" || isNaN(amount) || Number(amount) < 1 || Number(amount) > 99999) {
				       document.getElementById("VoucherAmountError").innerHTML = "Please enter a valid amount (1-99999)";
				       document.getElementById("VoucherAmount").focus();
				       return false;
				   } else {
				       document.getElementById("VoucherAmountError").innerHTML = "";
				   }
			
				   document.getElementById("signinLoader").style.display="flex";
				   			const clientKey = "client-secret-key"; // Extra security measure
				   		    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

				   		    // Concatenate data (must match backend)
				   		    const dataString = 
							employerId+
							employeeId+
							purposeCode+
							mcc+
							name+
							voucherType+
							voucherSubType+
							mobile+
							amount+
							remarks+
							clientKey+secretKey;

				   		    // Generate SHA-256 hash
				   		    const encoder = new TextEncoder();
				   		    const data = encoder.encode(dataString);
				   		    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
				   		    const hashArray = Array.from(new Uint8Array(hashBuffer));
				   		    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');    
		    // Send the AJAX request
		    $.ajax({
		        type: "POST",
		        url: "/createVoucher",
		        data: {
		            
					"employerId":employerId,
					"employeeId":employeeId,
					"purposeCode":	purposeCode,
					"mcc": mcc,
		            "name": name,
		            "voucherType": voucherType, // Selected text of Voucher Type
		            "voucherSubType": voucherSubType, // Selected text of Voucher Sub Type
		            "mobile": mobile,
		            "amount": amount,
		            "remarks": remarks,
					"clientKey":clientKey,
		 			"hash":hashHex
		            
				},
					 	      		  beforeSend : function(xhr) {
					 	 		//xhr.setRequestHeader(header, token);
					 	 		},      	 
		        success: function (response) {
					var data1 = jQuery.parseJSON(response);
					document.getElementById("signinLoader").style.display="none";
		            if (data1.status === true) {
		               $("#VoucherRequestSuccess").show();
					   setTimeout(function () {
					                       $('.modal').modal('hide'); // Close all modals
					                       location.reload(); // Refresh the page
					                   },1000);
		            } else {
		                $("#otfailmsg").text(data1.message);
		                $("#otfailmsgDiv").show();
		            }
		        },
		        error: function (error) {
		            alert("Error while saving data: " + error.responseText);
		        }
		    });
		}
		function resetFormFields() {
		    document.getElementById("Name").value = "";
		    document.getElementById("employeeId").value = "";
		    document.getElementById("employerId").value = "";
		    document.getElementById("VoucherCategory").value = "";
		    document.getElementById("VoucherAmount").value = "";
		    document.getElementById("VoucherRemarks").value = "";

		    // Reset dropdowns
		    document.getElementById("VoucherType").selectedIndex = 0;
		    document.getElementById("VoucherSubType").innerHTML = '<option value="">Select Sub Type</option>';
			$("#otmsgdiv").hide();
			
		}
