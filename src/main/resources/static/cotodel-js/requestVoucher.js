
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
		            const tableBody = $("#requestedVoucherList tbody");
		            // Clear existing table rows
		            tableBody.empty();
		            // Check if vouchers exist
		            if (vouchers.length === 0) {
		                tableBody.append(`<tr><td colspan="9" class="text-center">No Vouchers Found</td></tr>`);
		                return;
		            }
					vouchers.forEach(voucher => {
					    const formattedDate = voucher.creationDate
					        ? (() => {
					            const date = new Date(voucher.creationDate);
					            const dd = String(date.getDate()).padStart(2, '0');
					            const mm = String(date.getMonth() + 1).padStart(2, '0');
					            const yyyy = date.getFullYear();
					            return `${dd}-${mm}-${yyyy}`;
					        })()
					        : "N/A";

					    let status = voucher.statusMessage || "N/A";
					    let colorClass = "";

					    if (status === "Requested") {
					        colorClass = "color: #ffc107; font-weight: bold;";
					    } else if (status === "Voucher Created") {
					        colorClass = "color: #28a745; font-weight: bold;";
					    } else if (status === "Approved by manager") {
					        colorClass = "color: #007bff; font-weight: bold;";
					    } else if (status === "Rejected by manager") {
					        colorClass = "color:#dc3545; font-weight:bold;";
					    }

					    const row = `
					    <tr>
					        <td>${voucher.name}</td>
					        <td>${voucher.mobile}</td>
					        <td>${voucher.purposeDescription}</td>
					        <td style="text-align: center;">${new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', minimumFractionDigits: 2 }).format(voucher.amount || 0)}</td>
					        <td>${formattedDate}</td>
					        <td>${voucher.remarks || "N/A"}</td>
					        <td><span style="${colorClass}">${status}</span></td>
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
