function erupiVoucherCreateListLimit(){
	
	var employerId = document.getElementById("employerId").value;
		$.ajax({
			type: "POST",
			url: "/erupiVoucherCreateListLimit",
			data: {
					"orgId":employerId,
			},
			beforeSend: function(xhr) {
			},
			success: function(response) {
				var responseData = JSON.parse(response);
				const defaultImg = "img/user3.png"; // fallback image

				// Set total and active counts
			//	document.getElementById("totalCount").textContent = responseData.data.total;
			//	document.getElementById("activeCount").textContent = responseData.data.active;

				const tbody = document.getElementById("voucherTableListBody");
				tbody.innerHTML = ""; // clear previous rows

				responseData.data.empList.forEach((emp) => {
				  const tr = document.createElement("tr");

				  // --- User Column with Base64 image or fallback ---
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

				  // --- Employee Code ---
				  const empCodeTd = document.createElement("td");
				  empCodeTd.textContent = emp.empCode;

				  // Append to row
				  tr.appendChild(userTd);
				  tr.appendChild(deptTd);
				  tr.appendChild(empCodeTd);

				  // Add to table
				  tbody.appendChild(tr);
				});

			},
			error: function(e) {
				alert('Error: ' + e);
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

				  // --- User Column with Base64 image or fallback ---
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

				  // --- Employee Code ---
				  const empCodeTd = document.createElement("td");
				  empCodeTd.textContent = emp.empCode;

				  // Append to row
				  tr.appendChild(userTd);
				  tr.appendChild(deptTd);
				  tr.appendChild(empCodeTd);

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
	$.ajax({
		type: "POST",
		url: "/usedAmountByCategories",
		data: {
				"orgId":employerId,
				"timePeriod":"LM",
		},
		beforeSend: function(xhr) {
		},
		success: function(response) {
			var response = JSON.parse(response);
			try {
				if (response.status && response.data && response.data.length > 0) {
					const categoryBreakdown = document.querySelector('.category-breakdown');
					  categoryBreakdown.innerHTML = ''; // Clear existing items

					  const iconMap = {
					    "Fuel": "img/fuel-grey.png",
					    "Health & Wellness": "img/Maintainence-grey.png",
					    "Meal": "img/food-icon-grey.png",
					    "Maintenance": "img/Maintainence-grey.png",
					    "Toll": "img/Toll-grey.png"
					  };

					  response.data.forEach(item => {
					    const name = item.voucherName;
					    const amount = item.totalAmount.toLocaleString(undefined, { minimumFractionDigits: 2 });
					    const icon = iconMap[name] || "img/default-icon.png";

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

function loadVoucherData(){
	//document.getElementById("overlay").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "/activeInactiveVoucherAmount",
		data: {
				"orgId":employerId,
		},
		beforeSend: function(xhr) {
		},
		success: function(response) {
			var response = JSON.parse(response);
			try {
				if (response.status && response.data && response.data.length > 0) {
				          populateVoucherUI(response.data[0]);
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
  const bankName = data.bankName;
  const totalAmount = data.totalAmount;
  const balance = parseFloat(data.balance);
  const spent = totalAmount;
  const available = balance;
  const total = spent + available;
  const spentPercent = ((spent / total) * 100).toFixed(1);

  // Update dropdown
  const dropdown = document.querySelector('.voucher-dropdown');
  dropdown.innerHTML = `<option>${bankName} ${maskedAccount}</option>`;

  // Update amounts
  document.querySelector('.voucher-amount').textContent = `₹${available.toLocaleString(undefined, {minimumFractionDigits: 2})}`;
  document.querySelector('.voucher-spent').textContent = `₹${spent.toLocaleString(undefined, {minimumFractionDigits: 2})}`;

  // Update progress circle (you might need to style it with stroke-dasharray)
  const progressText = document.querySelector('.voucher-progress-text');
  progressText.textContent = `${spentPercent}%`;

  const progressCircle = document.querySelector('.voucher-progress-bar');
  const radius = 55;
  const circumference = 2 * Math.PI * radius;
  const offset = circumference * (1 - spent / total);
  progressCircle.setAttribute('stroke-dasharray', circumference);
  progressCircle.setAttribute('stroke-dashoffset', offset);
}

// Call this on page load
document.addEventListener('DOMContentLoaded', function () {
  loadVoucherData();
});
function getProfileStatus1(){
	//document.getElementById("overlay").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	var employeeId = "2";
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
	               let parsedData = typeof data === "string" ? JSON.parse(data) : data;

	               if (parsedData.data && parsedData.data.profileComplete) {
	                   let profileComplete = parsedData.data.profileComplete;

	                   if (profileComplete === 3) {
						let anchorStart = document.getElementById("anchorStart1");
						anchorStart.textContent = "Complete";
					    anchorStart.href = ""; 
	                    document.getElementById("btnsetupStart1").style.display = "none";
	                   }

	                   $('#profileComplete1').html(profileComplete);
	                   document.getElementById("profile1").style.width = (132 * parseInt(profileComplete)) + "px";
	               } else {
	                   console.error("Unexpected response structure:", parsedData);
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
	//document.getElementById("overlay").style.display = "flex";
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
	               let parsedData = typeof data === "string" ? JSON.parse(data) : data;

	               if (parsedData.data && parsedData.data.profileTotal) {
	                  //let profileComplete = parsedData.data.profileComplete;
					  let profileComplete =parsedData.data.profileTotal;
					  let profileCompanyComplete =parsedData.data.profileCompanyComplete;
					  let erupiLinkAccount = parsedData.data.erupiLinkAccount;
					  let profileVehicleComplete = parsedData.data.profileVehicleComplete;
	                   if (profileCompanyComplete === "1") {
						let anchorStart = document.getElementById("anchorStart");
						anchorStart.textContent = "Completed";
					    anchorStart.href = ""; 
						anchorStart.style = "color:#86889B"; 
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
					    if (profileCompanyComplete === "1" &&  erupiLinkAccount==="1" && profileVehicleComplete==="1") {
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
																		
						
						document.getElementById("accountSetupDiv2").style.display = "block";
							
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
function getProfileStatus1(){
	//document.getElementById("overlay").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	var employeeId = "2";
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
	               let parsedData = typeof data === "string" ? JSON.parse(data) : data;

	               if (parsedData.data && parsedData.data.profileComplete) {
	                   let profileComplete = parsedData.data.profileComplete;

	                   if (profileComplete === 3) {
						let anchorStart = document.getElementById("anchorStart1");
						anchorStart.textContent = "Complete";
					    anchorStart.href = ""; 
	                    document.getElementById("btnsetupStart1").style.display = "none";
	                   }

	                   $('#profileComplete1').html(profileComplete);
	                   document.getElementById("profile1").style.width = (132 * parseInt(profileComplete)) + "px";
	               } else {
	                   console.error("Unexpected response structure:", parsedData);
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
