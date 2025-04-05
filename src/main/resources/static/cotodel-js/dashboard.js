
function getProfileStatus(){
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
						let anchorStart = document.getElementById("anchorStart");
						anchorStart.textContent = "Complete";
					    anchorStart.href = ""; 
	                    document.getElementById("btnsetupStart").style.display = "none";
	                   }

	                   $('#profileComplete').html(profileComplete);
	                   document.getElementById("profile").style.width = (132 * parseInt(profileComplete)) + "px";
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
