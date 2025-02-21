$(document).ready(function() {
		    $('#btnRevoke').click(function() {
			  var employerMobile = document.getElementById("employerMobile").value;
			  document.getElementById("authenticate").disabled = false;
		      $.ajax({
		        url:"/smsOtpSender",
		        type: 'POST',
				data: {
							"mobile": employerMobile,
						},
				dataType: 'json',
				success: function(data) {
				var obj = data;
		        if (obj['status'] == true) {
		            // If successful, open the OTP modal
					var timeleft = "60";
					var resendCodeElement = document.getElementById("resendCode");
		               // Hide the "Resend OTP" link initially
		               resendCodeElement.style.display = "none";
					var downloadTimer = setInterval(function() {
						document.getElementById("countdown").innerHTML = "00:"+timeleft;
						timeleft -= 1;
						document.getElementById("orderId").value= obj['orderId'];
						if (timeleft < 0) {
							clearInterval(downloadTimer);
							resendCodeElement.style.display = "block";
							document.getElementById("authenticate").disabled = true;
						}
					}, 1000);
					
					$("#RevokeUPIVoucherModal").show();
		              $("#revokeModal").hide();
		          } else {
		            alert("Error: " + response.message);
		          }
		        },
		        error: function() {
				  //$('#otpModal').fadeIn();
		         // alert("An error occurred. Please try again.");
		        }
		      });
		    });

		    // Close the modal when clicking outside the modal content
		    $(window).on('click', function(event) {
		      if (event.target.id === 'revokeModal') {
		          $("#RevokeUPIVoucherModal").hide();
		      }
		    });
		  });
		  	


function resendVoucherOTP() {
	
	//var userName = document.getElementById("banklinkedMobile").value;

	var employerMobile = document.getElementById("employerMobile").value;
	var orderId = document.getElementById("orderId").value;
	document.getElementById("signinLoader").style.display = "flex";
	document.getElementById("authenticate").disabled = false;
	$.ajax({
		type: "POST",
		url:"/smsOtpResender",
		dataType: 'json',
		data: {
			"mobile": employerMobile,
			"orderId":orderId
		},
		success: function(data) {
			var obj = data;
			document.getElementById("signinLoader").style.display = "none";
			if (obj['status'] == "SUCCESS") {
				
				var timeleft = "60";
				var resendCodeElement = document.getElementById("resendCode");
	               // Hide the "Resend OTP" link initially
	               resendCodeElement.style.display = "none";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					document.getElementById("orderId").value= obj['orderId'];
					if (timeleft < 0) {
						clearInterval(downloadTimer);
						document.getElementById("countdown").innerHTML = " ";
						resendCodeElement.style.display = "block";
						document.getElementById("authenticate").disabled = true;
					}
				}, 1000);
			}else if (obj['status'] == "FAILURE") {
			
			} else {
				
			}
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}


function verfyIssueVoucherOTP() {
	//document.getElementById("authenticate").disabled = true;
  	var password1 = document.getElementById("password1").value;
  	var password2 = document.getElementById("password2").value;
  	var password3 = document.getElementById("password3").value;
  	var password4 = document.getElementById("password4").value;
  	var password5 = document.getElementById("password5").value;
  	var password6 = document.getElementById("password6").value;
  	var orderId = document.getElementById("orderId").value;
  	var employerMobile = document.getElementById("employerMobile").value;
  	
  	if (document.getElementById("employerMobile").value == "") {
  		document.getElementById("mobError").innerHTML="Please Enter mobile..";
  		
  		x = false;
  	} else if (password1 == "" && password1.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password1.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password2 == "" && password2.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password2.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password3 == "" && passwor3.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password3.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password4 == "" && password4.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password4.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password5 == "" && password5.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password5.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	 if (password6 == "" && password6.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password6.length < 1) {
  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError").innerHTML="";
  	}
  	
  	$.ajax({
  			type: "POST",
  			url:"/verifyOTP",
  			dataType: 'json',
  			data: {
  				"password1": password1,
  				"password2": password2,
  				"password3": password3,
  				"password4": password4,	
  				"password5": password5,
  				"password6": password6,
  				"mobile": employerMobile,
  				"orderId": orderId,
  				"userName":employerMobile
  			},
  			success: function(data) {
  				var obj = data;
				document.getElementById("password1").value="";
				document.getElementById("password2").value="";
				document.getElementById("password3").value="";
			    document.getElementById("password4").value="";
				document.getElementById("password5").value="";
				document.getElementById("password6").value="";
				//document.getElementById("authenticate").disabled = false;
  				if (obj['status']== true) {
					$('#RevokeUPIVoucherModal').hide();
					revoke();
  				}else if (obj['status'] == false) {
					document.getElementById("otpError").textContent=obj['message'];
					document.getElementById("otpError").style.display="block";
					$('#RevokeUPIVoucherModal').show();
					//$('#revokeUPIVcAuthenticateFail').show();
				} else {
  				
  				}
  			},
  			error: function(e) {
  				alert('Error: ' + e);
  			}
  		});
  }

  
    function focusNext(currentInput) {
        // Move focus to the next input box
        var maxLength = parseInt(currentInput.getAttribute("maxlength"));
        var currentLength = currentInput.value.length;

        if (currentLength >= maxLength) {
            var nextIndex = Array.from(currentInput.parentElement.children).indexOf(currentInput) + 1;
            var nextInput = currentInput.parentElement.children[nextIndex];

            if (nextInput) {
                nextInput.focus();
            }
        }
    }

function focusBack(){
	  var elts = document.getElementsByClassName('test')
	  Array.from(elts).forEach(function(elt) {
	  elt.addEventListener("keydown", function(event) {
	    // Number 13 is the "Enter" key on the keyboard
	    if (event.keyCode === 13 ||
	        event.keyCode !== 8 && elt.value.length === Number(elt.maxLength)
	    ) {
	      // Focus on the next sibling
	      elt.nextElementSibling.focus()
	    }
	    if (event.keyCode == 8) {
	      elt.value = '';
	      if (elt.previousElementSibling != null) {
	        elt.previousElementSibling.focus();
	        event.preventDefault();
	      }
	    }
	  });
	})
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
	 				 const issueBulk = document.getElementById('issueBulk');
	 				 issueManually.disabled =false;
	 				 issueBulk.disabled =false;
					 
				   } else {
					document.getElementById("nolinkBankAccount").style.display="block";
					 document.getElementById("linkedAccount").style.display="none";
				}		
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}



function getIssueVoucherList(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "/getIssueVoucherList",
		data: {
			//"employeeId": employerid,
			"orgId": employerid,
			"timePeriod":"AH",
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			 //console.log(data2);
			document.getElementById("signinLoader").style.display="none";
			
			var table = $('#issueVoucherTable').DataTable( {
	          destroy: true,	
			 // "dom": 'rtip',
			 //dom: 'Bfrtip',
			 lengthChange: true,
		     "responsive": true, searching: true,bInfo: false, paging: true,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["csv", "excel"],
             "language": {"emptyTable": "UPI Vouchers section is currently not enabled. Please link your bank account to enable this section."  },
	         "aaData": data2,
      		  "aoColumns": [ 
				{ "mData": "id", "render": function (data2, type, row) {
				   return ' <div class="table-check"><input type="hidden" class="checkBoxClass" value="'+data2+'" id="'+data2+'" name="revoke"></div>';
			    }}, 
				{ "mData": "name"},
                { "mData": "mobile"},   
				/*{ "mData": "accountNumber", "render": function (data2, type, row) {
					
				 return '<div class="tdflex"><img src="img/indianbank.png" width="16px" alt=""><span" ></span></div>';
								  
				}},*/
			    { "mData": "accountNumber"},   
				{ "mData": "purposeDesc"},  
				//{ "mData": "mcc"}, 
				{ "mData": "amount"},
				{ "mData": "creationDate"},
				{ "mData": "expDate"},
				{ "mData": "redemtionType"},
				{ "mData": "type"},      
				
				{ "mData": "id", "render": function (data2, type, row) {
				    if (row.type === "Revoke" || row.type === "fail" || row.type === "Redeem") {
				        return '';
				    } else {
				        return `
				            <td>
				                <div class="dropdown no-arrow ml-2">
				                    <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				                        <i class="fas fa-ellipsis-v fa-sm"></i>
				                    </a>
				                    <br>
				                    <div class="dropdown-menu dropdown-menu-right shadow" aria-labelledby="userDropdown">
				                        <button 
				                            class="dropdown-item py-2" 
				                            value="${data2}" 
				                            id="btnRevoke" 
				                            onclick="openRevokeDialog('${encodeURIComponent(JSON.stringify(row))}')">
				                            Revoke
				                        </button>
				                        <button 
				                            class="dropdown-item py-2"  
				                            data-toggle="modal" 
				                            data-target="#tableSendSms" 
				                            value="${data2}" 
				                            id="btnSend" 
				                            onclick="sendsms('${encodeURIComponent(JSON.stringify(row))}')">
				                            Send SMS
				                        </button>
				                    </div>
				                </div>
				            </td>`;
						}
				    }}, 
			
    		 	],
				
				createdRow: function (row, data2, dataIndex) 
                    {
                   
                 	var voucherDesc = data2.voucherDesc;
                 	var type = data2.type;
					
                     if(voucherDesc=="Meal")
                     {
					 var imgTag = '<img src="img/food.svg" alt="" class="mr-2">'+voucherDesc;
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(voucherDesc=="Miscellaneous")
                     {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+voucherDesc;
 					
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     
                     if(voucherDesc=="Food")
                     {
						 var imgTag = ' <img src="img/food.svg" alt="" class="mr-2">'+voucherDesc;
	 					 $(row).find('td:eq(1)').html(imgTag);
                     }
                     
                     if(voucherDesc=="Cash Advance")
                     {
					 var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+voucherDesc;
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(voucherDesc=="Travel")
                     {
					 var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+voucherDesc;
 					
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(voucherDesc=="Stay")
                     {
					 var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+voucherDesc;
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     
                     if(type=="fail")
                     {
						var imgTag = ' <img src="img/table-fail.svg" alt="" class="mr-2">';
						 $(row).find('td:eq(9)').html(imgTag);
                    //  $(row).find('td:eq(10)').addClass('tdactive');
                     }
                     if(type=="Created")
                     {
						var imgTag = ' <img src="img/table-create.svg" alt="" class="mr-2">';
						 $(row).find('td:eq(9)').html(imgTag);
                     // $(row).find('td:eq(10)').addClass('tdsubmitted');
                     }
					 if(type=="Revoke")
                     {
				
						var imgTag = ' <img src="img/Revoke.svg" alt="" class="mr-2">';
					    $(row).find('td:eq(9)').html(imgTag);
					 
                     }
					 if(type=="Redeem")
                     {
						var imgTag = ' <img src="img/Redeem.svg" alt="" class="mr-2">';
						 $(row).find('td:eq(9)').html(imgTag);
                     }
					 var bankcode = data2.bankcode;
					 var bankIcon = data2.bankIcon;
					 var accountNumber = data2.accountNumber;
					 if(bankcode=="ICICI")
                     {	
	 					 var imgTag = ' <img src="data:image/png;base64,' + bankIcon + '" alt=""] width="16px" height=""16px>';
	 					 $(row).find('td:eq(3)').html(imgTag+" "+accountNumber);
                     }
                  }
			});
      		//}).buttons().container().appendTo('#issueVoucherTable_wrapper .col-md-6:eq(0)');		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}
function colosethis(){
	
	document.getElementById("tableRevoke").style.display="none";
}

function sendsms(rowData){
		// var row = jQuery(value).closest('tr');
		// var  id = $(row).find("input[name='revoke']").val();
		 var employerId = document.getElementById("employerId").value; 
		 if (typeof rowData === "string") {
		     rowData = JSON.parse(decodeURIComponent(rowData));
		  }
		 document.getElementById("signinLoader").style.display="flex";
		  	$.ajax({
		 	type: "POST",
		 	url:"/erupiVoucheSmsSend",
		     data: {
		 			"id": rowData.id,
					"org":employerId,
		    		 },
		    		  beforeSend : function(xhr) {
		 			//xhr.setRequestHeader(header, token);
		 			},
		         success: function(data){
					var newdata=data;
					var data1 = jQuery.parseJSON(newdata);
				 document.getElementById("signinLoader").style.display="none";
				 if(data1.status==true){
					$('#smsUPIVcAuthenticate').show();
					$('#smsUPIVcAuthenticateFail').hide();
				 }
				 else{
					$('#smsUPIVcAuthenticateFail').show();
					$('#smsUPIVcAuthenticate').hide();
				 }
				
		        },
		      error: function(e){
		          alert('Error: ' + e);
		      }
		 }); 
		 
		 $(window).on('click', function(event) {
		       //if (event.target.id === 'otpModal') {
		       //  $('#smsUPIVcAuthenticate').fadeOut();
		      // }
		     });
}


function revoke(){
		// var row = jQuery(value).closest('tr');
		// var  id = $(row).find("input[name='revoke']").val();
		var employerId = document.getElementById("employerId").value; 
		var revokeId = document.getElementById("revokeId").value;
		 document.getElementById("signinLoader").style.display="flex";
		  	$.ajax({
		 	type: "POST",
		 	url:"/revokeCreatedVoucher",
		     data: {
		 			"id": revokeId,
					"orgId": employerId
		    		 },
		    		  beforeSend : function(xhr) {
		 			//xhr.setRequestHeader(header, token);
		 			},
		         success: function(data){
					newData = data;
					var data1 = jQuery.parseJSON(newData);
					$('#RevokeUPIVoucherModal').hide();
					document.getElementById("revokeId").value="";
					document.getElementById("authenticate").disabled = false;
					 document.getElementById("signinLoader").style.display="none";
					
					if(data1.status==true){
					$('#revokeUPIVcAuthenticate').show();				
					$('#RevokeUPIVoucherModal').hide();
					}
					else{
						$('#revokeUPIVcAuthenticateFail').show();		
					}
					getIssueVoucherList();
			        },
			      error: function(e){
					$('#revokeUPIVcAuthenticateFail').show();		
			          alert('Error: ' + e);
			      }
		 }); 
		
}
function sortTable(columnIndex) {
           const rows = Array.from(tableBody.rows);
           const sortedRows = rows.sort((a, b) => {
               const cellA = a.cells[columnIndex].innerText;
               const cellB = b.cells[columnIndex].innerText;
               if (!isNaN(cellA) && !isNaN(cellB)) {
                   return Number(cellA) - Number(cellB);
               }
               return cellA.localeCompare(cellB);
           });

           tableBody.innerHTML = "";
           sortedRows.forEach(row => tableBody.appendChild(row));
       }

	   
	   document.getElementById('downloadBtn').addEventListener('click', function () {
	       const table = document.getElementById('issueVoucherTable');

	      const clonedTable = table.cloneNode(true);
	       const columnsToRemove = [0, 9, 10]; 
	       columnsToRemove.sort((a, b) => b - a);

	       const rows = clonedTable.rows;
	       for (let i = 0; i < rows.length; i++) {
	           columnsToRemove.forEach((colIndex) => {
	               if (rows[i].cells.length > colIndex) {
	                   rows[i].deleteCell(colIndex);
	               }
	           });
	       }
	       const workbook = XLSX.utils.table_to_book(clonedTable, { sheet: "Sheet1" });
	       XLSX.writeFile(workbook, 'issueVoucherTable.xlsx');
	   });
