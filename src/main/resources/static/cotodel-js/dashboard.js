

let voucherData = []; // Global to hold response

function loadVoucherData() {
  //document.getElementById("signinLoader").style.display = "flex";
  const employerId = document.getElementById("employerId").value;
  
  $.ajax({
    type: "POST",
    url: "/activeInactiveVoucherAmount",
    data: { "orgId": employerId },
    success: function(response) {
      try {
        response = JSON.parse(response);

        if (response.status && response.data && response.data.length > 0) {
          voucherData = response.data; // Store globally

          document.getElementById("offerbox1").style.display = "none";
          document.getElementById("accountSetupDiv2").style.display = "none";
          document.getElementById("businessSection").style.display = "none";
		  document.getElementById("accountSetup").style.display = "none";
		  
          document.getElementById("activeVoucherContainer").style.display = "block";
          document.getElementById("userTransactionSection").style.display = "block";

          populateVoucherDropdown(voucherData);
          populateVoucherUI(voucherData[0]); // Default load
		 //populateVoucherUI({ accountNumber: "1234567890", totalAmount: "152", redeemAmount: "100" });  // 0% spent
        }

      } catch (error) {
        console.error("Error parsing JSON:", error);
      }
    },
    error: function(e) {
      alert('Error: ' + e);
    }
  });
}

function populateVoucherDropdown(dataList) {
  const dropdown = document.querySelector('.voucher-dropdown');
  dropdown.innerHTML = ''; // Clear existing options

  dataList.forEach((data, index) => {
    const maskedAccount = 'xxxx' + data.accountNumber.slice(-4);
    const label = `${data.bankName} ${maskedAccount}`;
    const logoBase64 = data.bankLogo || ''; // base64 string only (without data:image/... prefix)

    const option = document.createElement("option");
    option.value = index;
    option.textContent = label;

    // Construct full data URL if logo is present
    if (logoBase64) {
      option.setAttribute("data-image", `data:image/png;base64,${logoBase64}`);
    }

    dropdown.appendChild(option);
  });
}

$(document).on('change', '.voucher-dropdown', function() {
  const selectedIndex = this.selectedIndex;
  if (voucherData[selectedIndex]) {
    populateVoucherUI(voucherData[selectedIndex]);
  }
});

async function getVoucherTransactionList() {
	const orgId = document.getElementById('employerId').value;
    $.ajax({
        type: "POST",
        url: "/getVoucherTransactionList",
		data: { "orgId":orgId,
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
			console.log(" dashboard getVoucherTransactionList()=",data1);
            
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
					{ "mData": "bankrrn" },
                    { "mData": "name"},
                    { "mData": "purposeDesc" },
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
				createdRow: function (row, data2, dataIndex) 
                {
				var purposeDesc = data2.purposeDesc;
                  if(purposeDesc=="Meal")
                  {
				 var imgTag = '<img src="img/food.svg" alt="" class="mr-2">'+purposeDesc;
                   $(row).find('td:eq(3)').html(imgTag);
                  }
				 else if(purposeDesc=="Petroleum Voucher")
                  {
  			      var imgTag = '<img src="img/fuel-grey.png" alt="" class="mr-2">'+purposeDesc;
                   $(row).find('td:eq(3)').html(imgTag);
                  }	
              }
		});		
							
        },
        error: function(e) {
            alert('Failed to fetch JSON data' + e);
        }
    });
}

async function getVehicleManagementList() {
	const orgId = document.getElementById('employerId').value;
    $.ajax({
        type: "GET",
        url: "/getVehicleList",
		data: { "orgId":orgId,
				"limit":"Yes"
		 },
        beforeSend: function(xhr) {
            //xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            newData = data;
            console.log("Emp onboarding data", newData);
            var data1 = jQuery.parseJSON(newData);
            var data2 = data1.data;
         
            var table = $('#vehicleManagementTable1').DataTable({
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
					"emptyTable": 'No vehicles have been mapped with the platform yet. Please proceed to the <a href="/vehiclemanagement">Vehicle Management</a> section to onboard vehicles from your fleet.'
						},
                
                // Use the filtered data instead of original data
                "aaData": data2,
                "aoColumns": [
                    { "mData": "vehicleType" },
                    { "mData": "vehicleNumber" },
                    { "mData": "vehicleManufactor" },
					{
					  "mData": "driverName2",
					  "render": function(data1, type, row) {
					    if (data1 === "" || data1 === null) {
					      return '<img src="img/vector-not-assigned.png" alt="" class="mr-2"> Driver is not assigned';
					    } else {
					      //return row.driverName2 + "</br>+91 " + row.driverMobile + ""+'<img src="img/vector-people.png" alt="" style="margin-left: 100px;">';
						  return row.driverName2 + "</br>+91 " + row.driverMobile + 
						    '<img src="img/people-grey.png" alt="not-assigned" style="height:24px; width:24px; float:right; margin-left:5px;">';	  					  
					  }
					  }
					},
				
                ],
				createdRow: function (row, data1, dataIndex) 
                {
             	var vehicleType = data1.vehicleType;
                 if(vehicleType=="SUV / MUV")
                 {
				 var imgTag = '<img src="img/car-icon.png" alt="" class="mr-2">'+vehicleType;
                 $(row).find('td:eq(0)').html(imgTag);
                 }
                 else if(vehicleType=="Sedan")
                 {
				 var imgTag = ' <img src="img/car-icon.png" alt="Truck Icon" style="height:24px; width:24px; margin-right:4px;">'+vehicleType;
				 $(row).find('td:eq(0)').html(imgTag);
                 }
                 
                 else if(vehicleType=="Truck")
                 {
					var imgTag = ' <img src="img/truck-icon.png" alt="Truck Icon" style="height:24px; width:24px; margin-right:4px;">'+vehicleType;
					 $(row).find('td:eq(0)').html(imgTag);
                 }
                 
                 else if(vehicleType=="Mini Truck")
                 {
				 var imgTag = '<img src="img/truck-icon.png" alt="" class="mr-2">'+vehicleType;
                 $(row).find('td:eq(0)').html(imgTag);
                 }
                 else if(vehicleType=="Hatchback")
                 {
				 var imgTag = '<img src="img/car-icon.png" alt="" class="mr-2">'+vehicleType;
				 $(row).find('td:eq(0)').html(imgTag);
                 }
                 else if(vehicleType=="Two-Wheeler")
                 {
				 var imgTag = '<img src="img/two-wheeler-icon.webp" alt="" class="mr-2" style="height:24px; width:24px;">'+vehicleType;
                  $(row).find('td:eq(0)').html(imgTag);
                 }
				 else if(vehicleType=="Amb./Spl. Purp")
                 {
				 var imgTag = '<img src="img/ambulance.webp" alt="" class="mr-2" style="height:24px; width:24px;">'+vehicleType;
                  $(row).find('td:eq(0)').html(imgTag);
                 }
				 else{
					var imgTag = '<img src="img/car-icon.png" alt="" class="mr-2">'+vehicleType;
					 $(row).find('td:eq(0)').html(imgTag);
				 }				
              }
		});									
        },
        error: function(e) {
            alert('Failed to fetch JSON data' + e);
        }
    });
}


function erupiVoucherCreateListLimit() {
  var employerid = document.getElementById("employerId").value;

  $.ajax({
    type: "POST",
    url: "/erupiVoucherCreateListLimit",
    data: {
      "orgId": employerid,
      "timePeriod": "Yes",
    },
    success: function (data) {
      newData = data;
      var data1 = jQuery.parseJSON(newData);
      var data2 = data1.data;
		console.log("dashboard erupiVoucherCreateListLimit() ",data1);
      var table = $('#vouchersTableList').DataTable({
        destroy: true,
        lengthChange: true,
        responsive: true,
        searching: false,
        bInfo: false,
        paging: false,
        autoWidth: false,
        pagingType: "full_numbers",
        pageLength: 50,
        buttons: ["csv", "excel"],
        language: {
          "emptyTable": 'As per the last update, currently there are no UPI Vouchers transactions recorded on the platform. If your team members have redeemed</br> a UPI Voucher already, please refresh and check again at the end of the day to view corresponding transactions.</br>If your team and you haven’t already, start issuing and using <a href="/upiVoucherIssuanceNew">UPI Vouchers</a> to experience the magic!'
        },
        aaData: data2,
        aoColumns: [
          { "mData": "name" },
          { "mData": "mobile" },
          { "mData": "accountNumber" },
          { "mData": "purposeDesc" },
          { "mData": "type" },
          {
            "mData": "creationDate",
            "render": function (data) {
              return formatDate(data);
            }
          },
          {
            "mData": "expDate",
            "render": function (data) {
              return formatDate(data);
            }
          },
          {
            "mData": "amount",
			"class":"text-right",
			"render": function (data2, type, row) {
				      if (!data2) return '';
				      let amount = parseFloat(data2);
				      let formattedAmount = amount.toFixed(2); // enforce 2 decimal places
				      let localizedAmount = parseFloat(formattedAmount).toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
				      return '<div class="amount-cell">₹' + localizedAmount + '</div>';
				  }
          },
          { "mData": "redeemAmount" ,
			"class":"text-right",
								  "render": function (data2, type, row) {
								      if (!data2) return '';
								      let amount = parseFloat(data2);
								      let formattedAmount = amount.toFixed(2); // enforce 2 decimal places
								      let localizedAmount = parseFloat(formattedAmount).toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
								      return '<div class="amount-cell">₹' + localizedAmount + '</div>';
								  }
		   },
        ],

        createdRow: function (row, data2, dataIndex) {
          var purposeDesc = data2.purposeDesc;

          if (purposeDesc == "Meal") {
            var imgTag = '<img src="img/food.svg" alt="" class="mr-2">' + purposeDesc;
            $(row).find('td:eq(3)').html(imgTag);
          } else if (purposeDesc == "Petroleum Voucher") {
            var imgTag = '<img src="img/fuel-grey.png" alt="" class="mr-2">' + purposeDesc;
            $(row).find('td:eq(3)').html(imgTag);
          }

          var type = data2.type;
          if (type == "fail") {
            var imgTag = ' <img src="img/table-fail.svg" alt="" class="mr-2">';
            $(row).find('td:eq(9)').html(imgTag);
          }
          if (type == "Created") {
            var imgTag = ' <img src="img/table-create.svg" alt="" class="mr-2">';
            $(row).find('td:eq(9)').html(imgTag);
          }
          if (type == "Revoke") {
            var imgTag = ' <img src="img/Revoke.svg" alt="" class="mr-2">';
            $(row).find('td:eq(9)').html(imgTag);
          }
          if (type == "Redeem") {
            var imgTag = ' <img src="img/Redeem.svg" alt="" class="mr-2">';
            $(row).find('td:eq(9)').html(imgTag);
          }

          var bankcode = data2.bankcode;
          var bankIcon = data2.bankIcon;
          var accountNumber = data2.accountNumber;
          if (bankcode == "ICICI") {
            var imgTag = ' <img src="data:image/png;base64,' + bankIcon + '" alt="" width="16px" height="16px">';
            $(row).find('td:eq(2)').html(imgTag + " " + accountNumber);
          }
        }
      });
    },
    error: function (e) {
      alert('Failed to fetch JSON data' + e);
    }
  });
}


function loadActiveInactiveUserList(){
	
	var employerId = document.getElementById("employerId").value;
		$.ajax({
			type: "POST",
			url: "/loadActiveInactiveUserList",
			data: {
					"employerId":employerId,
					"type":"total",
			},
			beforeSend: function(xhr) {
			},
			success: function(response) {
				var responseData = JSON.parse(response);
				const defaultImg = "img/user3.png"; // fallback image

				// Set total and active counts
				document.getElementById("totalCount").textContent = responseData.data.total;
				document.getElementById("activeCount").textContent = responseData.data.active;

				const tbody = document.getElementById("userTableBody");
				tbody.innerHTML = ""; // clear previous rows

				responseData.data.empList.forEach((emp) => {
				  const tr = document.createElement("tr");

				  const userTd = document.createElement("td");
				  const img = document.createElement("img");
				  img.className = "rounded-circle me-2";
				  img.width = 30;

				  if (emp.empPhoto && emp.empPhoto.trim() !== "") {
				    img.src = `data:image/png;base64,${emp.empPhoto}`;
				  } else {
				    img.src = defaultImg; // fallback
				  }

				  userTd.appendChild(img);
				  userTd.append(` ${emp.name}`);

				  // --- Department ---
				  const deptTd = document.createElement("td");
				  deptTd.textContent =
				    emp.depratment && emp.depratment.trim() !== ""
				      ? emp.depratment
				      : "-";
					  const mobile = document.createElement("td");
		  			  mobile.textContent =
	  			    emp.mobile && emp.mobile.trim() !== ""
	  			      ? emp.mobile
	  			      : "-";

					  const empOrCont = document.createElement("td");
		  			  empOrCont.textContent =
		  			    emp.empOrCont && emp.empOrCont.trim() !== ""
		  			      ? emp.empOrCont
		  			      : "-";

						  const email = document.createElement("td");
			  			  email.textContent =
			  			    emp.email && emp.email.trim() !== ""
			  			      ? emp.email
			  			      : "-";

				  // --- Employee Code ---
				  const empCodeTd = document.createElement("td");
				  empCodeTd.textContent = emp.empCode;

				  // Append to row
				  tr.appendChild(userTd);
				  tr.appendChild(deptTd);
				  tr.appendChild(empCodeTd);
				  tr.appendChild(mobile);
				  tr.appendChild(email);
				  tr.appendChild(empOrCont);
				  // Add to table
				  tbody.appendChild(tr);
				});

			},
			error: function(e) {
				alert('Error: ' + e);
			}
		});
}

function activeUser(){
	
	var employerId = document.getElementById("employerId").value;
		$.ajax({
			type: "POST",
			url: "/loadActiveInactiveUserList",
			data: {
					"employerId":employerId,
					"type":"active",
			},
			beforeSend: function(xhr) {
			},
			success: function(response) {
				var responseData = JSON.parse(response);
				const defaultImg = "img/user3.png"; // fallback image

				// Set total and active counts
				document.getElementById("totalCount").textContent = responseData.data.total;
				document.getElementById("activeCount").textContent = responseData.data.active;

				const tbody = document.getElementById("userTableBody");
				tbody.innerHTML = ""; // clear previous rows

				responseData.data.empList.forEach((emp) => {
				  const tr = document.createElement("tr");

				  const userTd = document.createElement("td");
				  const img = document.createElement("img");
				  img.className = "rounded-circle me-2";
				  img.width = 30;

				  if (emp.empPhoto && emp.empPhoto.trim() !== "") {
				    img.src = `data:image/png;base64,${emp.empPhoto}`;
				  } else {
				    img.src = defaultImg; // fallback
				  }

				  userTd.appendChild(img);
				  userTd.append(` ${emp.name}`);

				  // --- Department ---
				  const deptTd = document.createElement("td");
				  deptTd.textContent =
				    emp.depratment && emp.depratment.trim() !== ""
				      ? emp.depratment
				      : "-";
					  const mobile = document.createElement("td");
		  			  mobile.textContent =
	  			    emp.mobile && emp.mobile.trim() !== ""
	  			      ? emp.mobile
	  			      : "-";

					  const empOrCont = document.createElement("td");
		  			  empOrCont.textContent =
		  			    emp.empOrCont && emp.empOrCont.trim() !== ""
		  			      ? emp.empOrCont
		  			      : "-";

						  const email = document.createElement("td");
			  			  email.textContent =
			  			    emp.email && emp.email.trim() !== ""
			  			      ? emp.email
			  			      : "-";

				  // --- Employee Code ---
				  const empCodeTd = document.createElement("td");
				  empCodeTd.textContent = emp.empCode;

				  // Append to row
				  tr.appendChild(userTd);
				  tr.appendChild(deptTd);
				  tr.appendChild(empCodeTd);
				  tr.appendChild(mobile);
				  tr.appendChild(email);
				  tr.appendChild(empOrCont);
				  // Add to table
				  tbody.appendChild(tr);
				});

			},
			error: function(e) {
				alert('Error: ' + e);
			}
		});
}

function loadCategoryVoucherData(){
	//document.getElementById("overlay").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	var timePeriod = document.getElementById("timePeriod").value;
	$.ajax({
		type: "POST",
		url: "/usedAmountByCategories",
		data: {
				"orgId":employerId,
				"timePeriod":timePeriod,
		},
		beforeSend: function(xhr) {
		},
		success: function(response) {
		    var response = JSON.parse(response);
		    try {
		        if (response.status && response.data && response.data.length > 0) {
		            const categoryBreakdown = document.querySelector('.category-breakdown');
		            categoryBreakdown.innerHTML = ''; // Clear existing items

		            response.data.forEach(item => {
		                const name = item.voucherName;
		                const amount = item.totalAmount.toLocaleString(undefined, { minimumFractionDigits: 2 });

		                // If base64 icon exists, use it, else fallback to a default image
		                const icon = item.mccMainIcon && item.mccMainIcon.trim() !== ""
		                    ? `data:image/png;base64,${item.mccMainIcon}`
		                    : "img/default-icon.png";

		                const categoryItem = document.createElement('div');
		                categoryItem.className = 'category-item';
		                categoryItem.innerHTML = `
		                    <div><img src="${icon}" class="category-icon">${name}</div>
		                    <span class="category-amount">₹${amount}</span>
		                `;
		                categoryBreakdown.appendChild(categoryItem);
		            });
		        }
		    } catch (error) {
		        console.error("Error parsing JSON:", error);
		    }
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
} 




function populateVoucherUI(data) {
  const accountNumber = data.accountNumber;
  const maskedAccount = 'xxxx' + accountNumber.slice(-4);
  const bankName = data.bankName || "Bank";

  const total = parseFloat(data.totalAmount);
  const available = parseFloat(data.redeemAmount);
  
  const spent = total - available;

  const spentPercent = total > 0 ? ((available / total) * 100) : 0;

  document.querySelector('.voucher-amount').textContent = `₹${spent.toLocaleString(undefined, { minimumFractionDigits: 2 })}`;
  document.querySelector('.voucher-spent').textContent = `₹${available.toLocaleString(undefined, { minimumFractionDigits: 2 })}`;
  document.querySelector('.voucher-progress-text').textContent = `${spentPercent.toFixed(1)}%`;

  const progressCircle = document.querySelector('.voucher-progress-bar');
  const radius = 55;
  const circumference = 2 * Math.PI * radius;

  progressCircle.style.strokeDasharray = `${circumference}`;
  progressCircle.style.strokeDashoffset = `${circumference * (1 - available / total)}`;
}






document.addEventListener("DOMContentLoaded", function() {
  const dropdown = document.querySelector('.voucher-dropdown');
  if (dropdown) {
    dropdown.addEventListener('change', function() {
      const selectedIndex = this.selectedIndex;
      if (window.voucherData && voucherData[selectedIndex]) {
        populateVoucherUI(voucherData[selectedIndex]);
      }
    });
  }
});


function getBankListWithVocher() {
	    const employerId = document.getElementById("employerId").value;

	    $.ajax({
	        type: "POST",
	        url: "/voucherCreateBankList",
	        data: { orgId: employerId },
	        beforeSend: function (xhr) {
	            // You can add headers if needed
	        },
	        success: function (data) {
	            try {
	                const parsedData = jQuery.parseJSON(data);
	                const dataList = document.getElementById('bankListData');
	                const totalIssueCount = document.getElementById('totalIssueCount');
	                const totalIssueAmount = document.getElementById('totalIssueAmount');
	                const redemVCount = document.getElementById('redemVCount');
	                const redemVAmount = document.getElementById('redemVAmount');
	                const expRevokeCount = document.getElementById('expRevokeCount');
	                const expRevokeAmount = document.getElementById('expRevokeAmount');
	                const activeCount = document.getElementById('activeCount');
	                const activeAmount = document.getElementById('activeAmount');

					const totalVoucher = document.getElementById('totalVoucher');
					const totalvoucherValue = document.getElementById('totalvoucherValue');
									
	                // Clear previous list
	                dataList.innerHTML = "";

	                parsedData.data.forEach((item) => {
	                    const div = document.createElement('div');
	                    div.className = 'left-activeupivcmarked';
	                    div.innerHTML = `
	                        <div class="img-bank">
	                            <img src="data:image/png;base64,${item.bankLogo}" width="18" height="18" alt="Bank Logo">
	                        </div>
	                        <span>${item.bankName}</span>
	                        <input type="hidden" value="${item.bankAccount}" />
	                        <label>${item.bankAccountMask || ''}</label>
	                    `;

	                    // Set the default active class for null bank account
	                    if (item.bankAccount === null) {
	                        div.classList.add('active');

	                        // Fetch details for null account on page load
	                        $.ajax({
	                            type: "POST",
	                            url: "/voucherCreateSummaryDetailByAccount",
	                            data: {
	                                "orgId": employerId,
	                                "accNumber": null,
	                            },
	                            success: function (data) {
	                                const jsonData = jQuery.parseJSON(data);
	                                totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
	                                totalIssueAmount.textContent = jsonData.issueDetail.totalIssueAmount || "0";
	                                redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
	                                redemVAmount.textContent = jsonData.issueDetail.redemVAmount || "0";
	                                expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
	                                expRevokeAmount.textContent = jsonData.issueDetail.expRevokeAmount || "0";
	                                activeCount.textContent = jsonData.issueDetail.activeCount || "0";
	                                activeAmount.textContent = jsonData.issueDetail.activeAmount || "0";
									
									totalVoucher.innerHTML = jsonData.issueDetail.totalIssueCount || "0";
								    totalvoucherValue.textContent = jsonData.issueDetail.totalIssueAmount || "0";
	                            },
	                            error: function (e) {
	                                console.error('Error fetching default account details:', e);
	                            },
	                        });
	                    }

	                    // Add a click event listener to the div
	                    div.addEventListener('click', () => {
	                        const activeDiv = dataList.querySelector('.active');
	                        if (activeDiv) activeDiv.classList.remove('active');

	                        div.classList.add('active');

	                        const bankAccount = item.bankAccount;
	                        
	                            $.ajax({
	                                type: "POST",
	                                url: "/voucherCreateSummaryDetailByAccount",
	                                data: {
	                                    "orgId": employerId,
	                                    "accNumber": bankAccount,
	                                },
	                                success: function (data) {
	                                    const jsonData = jQuery.parseJSON(data);
	                                    totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
	                                    totalIssueAmount.textContent = jsonData.issueDetail.totalIssueAmount || "0";
	                                    redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
	                                    redemVAmount.textContent = jsonData.issueDetail.redemVAmount || "0";
	                                    expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
	                                    expRevokeAmount.textContent = jsonData.issueDetail.expRevokeAmount || "0";
	                                    activeCount.textContent = jsonData.issueDetail.activeCount || "0";
	                                    activeAmount.textContent = jsonData.issueDetail.activeAmount || "0";
	                                },
	                                error: function (e) {
	                                    console.error('Error fetching account details:', e);
	                                },
	                            });
	                       
	                    });

	                    dataList.appendChild(div);
	                });

	                // Update totals with parsed data
	                totalIssueCount.textContent = parsedData.issueDetail.totalIssueCount || "0";
	                totalIssueAmount.textContent = parsedData.issueDetail.totalIssueAmount || "0";
	                redemVCount.textContent = parsedData.issueDetail.redemVCount || "0";
	                redemVAmount.textContent = parsedData.issueDetail.redemVAmount || "0";
	                expRevokeCount.textContent = parsedData.issueDetail.expRevokeCount || "0";
	                expRevokeAmount.textContent = parsedData.issueDetail.expRevokeAmount || "0";
	                activeCount.textContent = parsedData.issueDetail.activeCount || "0";
	                activeAmount.textContent = parsedData.issueDetail.activeAmount || "0";
	            } catch (error) {
	                console.error('Error parsing response:', error);
	                alert('Failed to process the response data.');
	            }
	        },
	        error: function (error) {
	            console.error('Error in AJAX request:', error);
	            alert('Failed to fetch bank list. Please try again.');
	        },
	    });
	}
	
	
	function  getLinkedBankDetail(){
			
		    //document.getElementById("signinLoader").style.display="flex";
		 	var employerid = document.getElementById("employerId").value;
		 	$.ajax({
			type: "POST",
			url:"/getErupiLinkBankAccountDetail",
		       data: {
					"orgId": employerid
		      		 },
		      		  beforeSend : function(xhr) {
					//xhr.setRequestHeader(header, token);
					},
					   success: function(data){
			          
						var obj = jQuery.parseJSON( data );
						 obj = obj.data;
						 
						if (obj && obj.length > 0) {
						     // Show the div with data
							 document.getElementById("nolinkBankAccount").style.display="none";
			 				 document.getElementById("linkedAccount").style.display="block";
			 				 const issueManually = document.getElementById('issueManually');
			 				 issueManually.disabled =false;
							 
						   } else {
							const issueManually = document.getElementById('issueManually');
							issueManually.disabled =true;
							document.getElementById("nolinkBankAccount").style.display="block";
							 document.getElementById("linkedAccount").style.display="none";
						}		
		          },
		        error: function(e){
		            alert('Error: ' + e);
		        }
		   }); 
					
		}


		function getProfileStatus(){
			document.getElementById("signinLoader").style.display = "flex";
			var employerId = document.getElementById("employerId").value;
			var employeeId = document.getElementById("employeeId").value;
			$.ajax({
				type: "POST",
				url: "/getCompanyProfileStatus",
				data: {
						"employerId":employerId,
						"employeeId" :employeeId
				},
				beforeSend: function(xhr) {
				},
				success: function(data) {
					try {
						
						   document.getElementById("signinLoader").style.display = "none";
			               let parsedData = typeof data === "string" ? JSON.parse(data) : data;
						  
			               if (parsedData.data && parsedData.data.profileTotal) {
			                  //let profileComplete = parsedData.data.profileComplete;
							  let profileComplete =parsedData.data.profileTotal;
							  let profileCompanyComplete =parsedData.data.profileCompanyComplete;
							  let erupiLinkAccount = parsedData.data.erupiLinkAccount;
							  let profileVehicleComplete = parsedData.data.profileVehicleComplete;
							  let profileDriverComplete = parsedData.data.profileDriverComplete;
							  if (profileCompanyComplete === "0" ||  erupiLinkAccount==="0" || erupiLinkAccount==="0"
								|| profileVehicleComplete==="0" || profileDriverComplete ==="0" ) {
									document.getElementById("profileContainer").style.display = "block";
									document.getElementById("accountSetup").style.display = "block";
							  }
			                   if (profileCompanyComplete === "1") {
								let anchorStart = document.getElementById("anchorStart");
								anchorStart.textContent = "Completed";
							    anchorStart.href = ""; 
								anchorStart.style = "color:#86889B"; 
								
								let anchorStartAccount = document.getElementById("btnTextAccount");
								anchorStartAccount.textContent = "Start";
								anchorStartAccount.href = "/roleAccess"; 
								anchorStartAccount.style = "color:#0d6efd"; 
														
								document.getElementById("btnsetupStart").classList.add("cd-step-item", "bg-transparent");
								document.getElementById("btnsetupBankAccount").classList.remove("bg-transparent");
							   }
							   if (profileCompanyComplete === "1" &&  erupiLinkAccount==="1") {
			   					let anchorStart = document.getElementById("anchorStart");
			   					anchorStart.textContent = "Completed";
			   				    anchorStart.href = ""; 
			   					anchorStart.style = "color:#86889B"; 
								
								let anchorStartAccount = document.getElementById("anchorStartAccount");
								anchorStartAccount.textContent = "Completed";
								anchorStartAccount.href = ""; 
								anchorStartAccount.style = "color:#86889B"; 
								
								let btnTextVehicles = document.getElementById("anchorStartVehicles");
								btnTextVehicles.textContent = "Start";
								btnTextVehicles.href = "/manageEmployee"; 
								btnTextVehicles.style = "color:#0d6efd"; 
								
			   					document.getElementById("btnsetupStart").classList.add("cd-step-item", "bg-transparent");
			   					document.getElementById("btnsetupBankAccount").classList.add("cd-step-item", "bg-transparent");
								document.getElementById("btnsetupPartner").classList.remove("bg-transparent");
								document.getElementById("bankContent").style.display = "block";
								document.getElementById("bankContentInprogress").style.display = "none";
			                   }else if(profileCompanyComplete === "1" &&  erupiLinkAccount==="2"){
								let anchorStart = document.getElementById("anchorStart");
			   					anchorStart.textContent = "Completed";
			   				    anchorStart.href = ""; 
			   					anchorStart.style = "color:#86889B"; 
								
								let anchorStartAccount = document.getElementById("anchorStartAccount");
								anchorStartAccount.textContent = "Inprogress";
								anchorStartAccount.href = ""; 
								anchorStartAccount.style = "color:#FF9500"; 
								
			   					document.getElementById("btnsetupStart").classList.add("cd-step-item", "bg-transparent");
			   					document.getElementById("btnsetupBankAccount").classList.add("cd-step-item", "bg-transparent");
								document.getElementById("btnsetupPartner").classList.remove("bg-transparent");
								document.getElementById("bankContent").style.display = "none";
								document.getElementById("bankContentInprogress").style.display = "block";
							   }
							    if (profileCompanyComplete === "1" &&  erupiLinkAccount==="1" && profileDriverComplete==="1") {
		   	   					let anchorStart = document.getElementById("anchorStart");
		   	   					anchorStart.textContent = "Completed";
		   	   				    anchorStart.href = ""; 
		   	   					anchorStart.style = "color:#86889B"; 
		   						
		   						let anchorStartAccount = document.getElementById("anchorStartAccount");
		   						anchorStartAccount.textContent = "Completed";
		   						anchorStartAccount.href = ""; 
		   						anchorStartAccount.style = "color:#86889B"; 
								
								let btnTextVehicles = document.getElementById("anchorStartVehicles");
								btnTextVehicles.textContent = "Completed";
								btnTextVehicles.href = ""; 
								btnTextVehicles.style = "color:#86889B"; 
							
		   	   					document.getElementById("btnsetupStart").classList.add("cd-step-item", "bg-transparent");
		   	   					document.getElementById("btnsetupBankAccount").classList.add("cd-step-item", "bg-transparent");
		   						document.getElementById("btnsetupPartner").classList.add("cd-step-item", "bg-transparent");
		   						
								
								document.getElementById("profileContainer").style.display = "block";
								document.getElementById("accountSetup").style.display = "none";
								document.getElementById("userTransactionSection").style.display = "block";
								document.getElementById("activeVoucherContainer").style.display = "block";
																				
								document.getElementById("accountSetupDiv2").style.display = "none";
								document.getElementById("offerbox1").style.display = "none";
									
		   	                   }
			                   $('#profileComplete').html(profileComplete);
			                   document.getElementById("profile").style.width = (66.33 * parseInt(profileComplete)) + "px";
							   
			               } else {
			                   console.log("Unexpected response structure:", parsedData.message || "No message");
			               }
			           } catch (error) {
			               console.error("Error parsing JSON:", error);
			           }
				},
				error: function(e) {
					alert('Error: ' + e);
				}
			});
		} 

/*		function getProfileStatus() {
		    document.getElementById("signinLoader").style.display = "flex";
		    const employerId = document.getElementById("employerId").value;
		    const employeeId = document.getElementById("employeeId").value;
			const employeeRole = document.getElementById("employeeRole").value;
			

		    $.ajax({
		        type: "POST",
		        url: "/getCompanyProfileStatus",
		        data: { employerId, employeeId },
		        success: function (data) {
		            try {
		                const parsedData = typeof data === "string" ? JSON.parse(data) : data;
		                const profile = parsedData?.data;

		                if (!profile?.profileTotal) {
		                    console.warn("Invalid profile data", parsedData.message || "No message");
		                    return;
		                }

		                const {
		                    profileTotal,
		                    profileCompanyComplete,
		                    erupiLinkAccount,
		                    profileVehicleComplete,
		                    profileDriverComplete
		                } = profile;

		                const setAnchorStatus = (id, text, href, color) => {
		                    const el = document.getElementById(id);
		                    if (el) {
		                        el.textContent = text;
		                        el.href = href;
		                        el.style.color = color;
		                    }
		                };

						if (profileCompanyComplete === "0") {
							document.getElementById("profileContainer").style.display = "block";
							document.getElementById("accountSetup").style.display = "block";
		                }
		                // Step 1: Company Profile
		                if (profileCompanyComplete === "1") {
		                    setAnchorStatus("anchorStart", "Completed", "", "#86889B");
		                    setAnchorStatus("btnTextAccount", "Start", "/roleAccess", "#0d6efd");
		                    document.getElementById("btnsetupStart").classList.add("cd-step-item", "bg-transparent");
		                    document.getElementById("btnsetupBankAccount").classList.remove("bg-transparent");
		                } // Step 2: eRUPI Account
		                if (profileCompanyComplete === "1") {
		                    if (erupiLinkAccount === "1") {
		                        setAnchorStatus("anchorStartAccount", "Completed", "", "#86889B");
		                        setAnchorStatus("anchorStartVehicles", "Start", "/manageEmployee", "#0d6efd");
		                        document.getElementById("btnsetupBankAccount").classList.add("cd-step-item", "bg-transparent");
		                        document.getElementById("btnsetupPartner").classList.remove("bg-transparent");
		                        document.getElementById("bankContent").style.display = "block";
		                        document.getElementById("bankContentInprogress").style.display = "none";
		                    } else if (erupiLinkAccount === "2") {
		                        setAnchorStatus("anchorStartAccount", "Inprogress", "", "#FF9500");
		                        document.getElementById("btnsetupBankAccount").classList.add("cd-step-item", "bg-transparent");
		                        document.getElementById("btnsetupPartner").classList.remove("bg-transparent");
		                        document.getElementById("bankContent").style.display = "none";
		                        document.getElementById("bankContentInprogress").style.display = "block";
		                    }
		                }

		                // Step 3: Driver Complete
		                if (profileCompanyComplete === "1" && erupiLinkAccount === "1" && profileDriverComplete === "1") {
		                    setAnchorStatus("anchorStartVehicles", "Completed", "", "#86889B");
		                    document.getElementById("btnsetupPartner").classList.add("cd-step-item", "bg-transparent");
		                    document.getElementById("profileContainer").style.display = "block";
		                    document.getElementById("accountSetup").style.display = "none";
		                    document.getElementById("userTransactionSection").style.display = "block";
		                    document.getElementById("accountSetupDiv2").style.display = "block";
		                }

		                // Update Profile Completion UI
		                $('#profileComplete').html(profileTotal);
		                document.getElementById("profile").style.width = (66.33 * parseInt(profileTotal)) + "px";

		            } catch (error) {
		                console.error("Error parsing response:", error);
		            } finally {
		                document.getElementById("signinLoader").style.display = "none";
		            }
		        },
		        error: function (e) {
		            document.getElementById("signinLoader").style.display = "none";
		            alert('Error: ' + e.statusText || 'Request failed');
		        }
		    });
		}*/
function getBankListWithVocher() {
	    const employerId = document.getElementById("employerId").value;

	    $.ajax({
	        type: "POST",
	        url: "/voucherCreateBankList",
	        data: { orgId: employerId },
	        beforeSend: function (xhr) {
	            // You can add headers if needed
	        },
	        success: function (data) {
	            try {
	                const parsedData = jQuery.parseJSON(data);
	                const dataList = document.getElementById('bankListData');
	                const totalIssueCount = document.getElementById('totalIssueCount');
	                const totalIssueAmount = document.getElementById('totalIssueAmount');
	                const redemVCount = document.getElementById('redemVCount');
	                const redemVAmount = document.getElementById('redemVAmount');
	                const expRevokeCount = document.getElementById('expRevokeCount');
	                const expRevokeAmount = document.getElementById('expRevokeAmount');
	                const activeCount = document.getElementById('activeCount');
	                const activeAmount = document.getElementById('activeAmount');

					const totalVoucher = document.getElementById('totalVoucher');
					const totalvoucherValue = document.getElementById('totalvoucherValue');
									
	                // Clear previous list
	                dataList.innerHTML = "";

	                parsedData.data.forEach((item) => {
	                    const div = document.createElement('div');
	                    div.className = 'left-activeupivcmarked';
	                    div.innerHTML = `
	                        <div class="img-bank">
	                            <img src="data:image/png;base64,${item.bankLogo}" width="18" height="18" alt="Bank Logo">
	                        </div>
	                        <span>${item.bankName}</span>
	                        <input type="hidden" value="${item.bankAccount}" />
	                        <label>${item.bankAccountMask || ''}</label>
	                    `;

	                    // Set the default active class for null bank account
	                    if (item.bankAccount === null) {
	                        div.classList.add('active');

	                        // Fetch details for null account on page load
	                        $.ajax({
	                            type: "POST",
	                            url: "/voucherCreateSummaryDetailByAccount",
	                            data: {
	                                "orgId": employerId,
	                                "accNumber": null,
	                            },
	                            success: function (data) {
	                                const jsonData = jQuery.parseJSON(data);
	                                totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
	                                totalIssueAmount.textContent = jsonData.issueDetail.totalIssueAmount || "0";
	                                redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
	                                redemVAmount.textContent = jsonData.issueDetail.redemVAmount || "0";
	                                expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
	                                expRevokeAmount.textContent = jsonData.issueDetail.expRevokeAmount || "0";
	                                activeCount.textContent = jsonData.issueDetail.activeCount || "0";
	                                activeAmount.textContent = jsonData.issueDetail.activeAmount || "0";
									
									totalVoucher.innerHTML = jsonData.issueDetail.totalIssueCount || "0";
								    totalvoucherValue.textContent = jsonData.issueDetail.totalIssueAmount || "0";
	                            },
	                            error: function (e) {
	                                console.error('Error fetching default account details:', e);
	                            },
	                        });
	                    }

	                    // Add a click event listener to the div
	                    div.addEventListener('click', () => {
	                        const activeDiv = dataList.querySelector('.active');
	                        if (activeDiv) activeDiv.classList.remove('active');

	                        div.classList.add('active');

	                        const bankAccount = item.bankAccount;
	                        
	                            $.ajax({
	                                type: "POST",
	                                url: "/voucherCreateSummaryDetailByAccount",
	                                data: {
	                                    "orgId": employerId,
	                                    "accNumber": bankAccount,
	                                },
	                                success: function (data) {
	                                    const jsonData = jQuery.parseJSON(data);
	                                    totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
	                                    totalIssueAmount.textContent = jsonData.issueDetail.totalIssueAmount || "0";
	                                    redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
	                                    redemVAmount.textContent = jsonData.issueDetail.redemVAmount || "0";
	                                    expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
	                                    expRevokeAmount.textContent = jsonData.issueDetail.expRevokeAmount || "0";
	                                    activeCount.textContent = jsonData.issueDetail.activeCount || "0";
	                                    activeAmount.textContent = jsonData.issueDetail.activeAmount || "0";
	                                },
	                                error: function (e) {
	                                    console.error('Error fetching account details:', e);
	                                },
	                            });
	                       
	                    });

	                    dataList.appendChild(div);
	                });

	                // Update totals with parsed data
	                totalIssueCount.textContent = parsedData.issueDetail.totalIssueCount || "0";
	                totalIssueAmount.textContent = parsedData.issueDetail.totalIssueAmount || "0";
	                redemVCount.textContent = parsedData.issueDetail.redemVCount || "0";
	                redemVAmount.textContent = parsedData.issueDetail.redemVAmount || "0";
	                expRevokeCount.textContent = parsedData.issueDetail.expRevokeCount || "0";
	                expRevokeAmount.textContent = parsedData.issueDetail.expRevokeAmount || "0";
	                activeCount.textContent = parsedData.issueDetail.activeCount || "0";
	                activeAmount.textContent = parsedData.issueDetail.activeAmount || "0";
	            } catch (error) {
	                console.error('Error parsing response:', error);
	                alert('Failed to process the response data.');
	            }
	        },
	        error: function (error) {
	            console.error('Error in AJAX request:', error);
	            alert('Failed to fetch bank list. Please try again.');
	        },
	    });
	}
	
	
	function  getLinkedBankDetail(){
		 	var employerid = document.getElementById("employerId").value;
		 	$.ajax({
			type: "POST",
			url:"/getErupiLinkBankAccountDetail",
		       data: {
					"orgId": employerid
		      		 },
		      		  beforeSend : function(xhr) {
					//xhr.setRequestHeader(header, token);
					},
					   success: function(data){
			          
						var obj = jQuery.parseJSON( data );
						 obj = obj.data;
						 
						if (obj && obj.length > 0) {
						     // Show the div with data
							 document.getElementById("nolinkBankAccount").style.display="none";
			 				 document.getElementById("linkedAccount").style.display="block";
			 				 const issueManually = document.getElementById('issueManually');
			 				 issueManually.disabled =false;
							 
						   } else {
							const issueManually = document.getElementById('issueManually');
							issueManually.disabled =true;
							document.getElementById("nolinkBankAccount").style.display="block";
							 document.getElementById("linkedAccount").style.display="none";
						}		
		          },
		        error: function(e){
		            alert('Error: ' + e);
		        }
		   }); 
					
		}

		function formatDate(dateStr) {
		    if (!dateStr) return '';
		    const date = new Date(dateStr);
		    const day = String(date.getDate()).padStart(2, '0');
		    const month = String(date.getMonth() + 1).padStart(2, '0');
		    const year = date.getFullYear();
		    return `${day}-${month}-${year}`;
		}