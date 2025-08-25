$(document).ready(function() {
		    $('#btnRevoke').click(function() {
			  var employerMobile = document.getElementById("employerMobile").value;
			  document.getElementById("authenticate").disabled = false;
			
		      $.ajax({
		        url:"/smsOtpWithTemplateMobileAndAmount",
		        type: 'POST',
				data: {
							"mobile": employerMobile,
							"template": "OTP Number Vouchers Revoke",
							"value":"1"
						},
				dataType: 'json',
				success: function(data) {
				var obj = data;
		        if (obj['status'] == true) {
					// Mask the mobile number (show only last 4 digits)
					var maskedMobile = "XXXXXX" + employerMobile.toString().slice(-4);
				    document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Revoke.`;				
										
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
		  
		 

		  function triggerBulkOtpSend(selectedIds) {
		  		
		  	    const employerMobile = document.getElementById("employerMobile").value;
		  	    document.getElementById("authenticate3").disabled = false;
				
		  	    $.ajax({
		  	      /*url: "/smsOtpSender",
		  	      type: 'POST',
		  	      data: { "mobile": employerMobile },
		  	      dataType: 'json',*/
		  		  url:"/smsOtpWithTemplateMobileAndAmount",
		    	        type: 'POST',
		    			data: {
		    						"mobile": employerMobile,
		    						"template": "OTP Number Vouchers Revoke",
									"value":selectedIds
		    					},
		    			dataType: 'json',
		  	      success: function(data) {
		  	        const obj = data;
		  	        if (obj['status'] === true) {
		  	          const maskedMobile = "XXXXXX" + employerMobile.toString().slice(-4);
		  	          document.getElementById("maskedMobileDisplay3").innerText =
		  	            `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Revoke.`;

		  	          let timeleft = 60;
		  	          const resendCodeElement = document.getElementById("resendCode3");
		  	          resendCodeElement.style.display = "none";

		  	          const downloadTimer = setInterval(function () {
		  	            document.getElementById("countdown3").innerHTML = "00:" + timeleft;
		  	            timeleft -= 1;
		  	            document.getElementById("orderId3").value = obj['orderId'];
		  	            if (timeleft < 0) {
		  	              clearInterval(downloadTimer);
		  	              resendCodeElement.style.display = "block";
		  	              document.getElementById("authenticate3").disabled = true;
		  	            }
		  	          }, 1000);

		  	          $("#RevokebulkUPIVoucherModal").modal('show');
		  	          $("#bulkrevokeModal").hide();
		  	        } else {
		  	          alert("Error: " + obj.message);
		  	        }
		  	      },
		  	      error: function() {
		  	        alert("An error occurred. Please try again.");
		  	      }
		  	    });
		  	  }


				  function resendVoucherOTP() {
				  	
				  	//var userName = document.getElementById("banklinkedMobile").value;

				  	var employerMobile = document.getElementById("employerMobile").value;
				  	var orderId = document.getElementById("orderId").value;
				  	document.getElementById("signinLoader").style.display = "flex";
				  	document.getElementById("authenticate").disabled = false;
				  	$.ajax({
				  		/*type: "POST",
				  		url:"/smsOtpResender",
				  		dataType: 'json',
				  		data: {
				  			"mobile": employerMobile,
				  			"orderId":orderId
				  		},*/
						url:"/smsOtpWithTemplateMobileAndAmount",
				        type: 'POST',
						dataType: 'json',
						data: {
									"mobile": employerMobile,
									"template": "OTP Number Vouchers Revoke",
									"value":"1"
								},
				  		success: function(data) {
				  			var obj = data;
				  			document.getElementById("signinLoader").style.display = "none";
				  			if (obj['status'] == true) {
				  				// Mask the mobile number (show only last 4 digits)
				  				var maskedMobile = "XXXXXX" + employerMobile.toString().slice(-4);
				  				document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Revoke.`;				
				  									
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
function resendbulkrevokeVoucherOTP() {
	
	//var userName = document.getElementById("banklinkedMobile").value;

	var employerMobile = document.getElementById("employerMobile").value;
	var orderId = document.getElementById("orderId").value;
	document.getElementById("signinLoader").style.display = "flex";
	document.getElementById("authenticate3").disabled = false;
	var ids=window.selectedVoucherIdsToRevoke;
	var idslen=ids.length;
	$.ajax({
		/*type: "POST",
		url:"/smsOtpResender",
		dataType: 'json',
		data: {
			"mobile": employerMobile,
			"orderId":orderId
		},*/
		url:"/smsOtpWithTemplateMobileAndAmount",
        type: 'POST',
		dataType: 'json',
		data: {
					"mobile": employerMobile,
					"template": "OTP Number Vouchers Revoke",
					"value":idslen
					
				},
		success: function(data) {
			var obj = data;
			document.getElementById("signinLoader").style.display = "none";
			if (obj['status'] == true) {
				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + employerMobile.toString().slice(-4);
				document.getElementById("maskedMobileDisplay3").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Revoke.`;				
									
				var timeleft = "60";
				var resendCodeElement = document.getElementById("resendCode3");
	               // Hide the "Resend OTP" link initially
	               resendCodeElement.style.display = "none";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown3").innerHTML = "00."+timeleft;
					timeleft -= 1;
					document.getElementById("orderId3").value= obj['orderId'];
					if (timeleft < 0) {
						clearInterval(downloadTimer);
						document.getElementById("countdown3").innerHTML = " ";
						resendCodeElement.style.display = "block";
						document.getElementById("authenticate3").disabled = true;
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
  	 if (password3 == "" && password3.length < 1) {
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
  			url:"/otpVerifyWithTemplate",
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

  

function bulkverfyrevokeVoucherOTP() {
	//document.getElementById("authenticate").disabled = true;
  	var password1 = document.getElementById("password111").value;
  	var password2 = document.getElementById("password222").value;
  	var password3 = document.getElementById("password333").value;
  	var password4 = document.getElementById("password444").value;
  	var password5 = document.getElementById("password555").value;
  	var password6 = document.getElementById("password666").value;
  	var orderId = document.getElementById("orderId3").value;
  	var employerMobile = document.getElementById("employerMobile").value;
  	var x=true;
  	if (document.getElementById("employerMobile").value == "") {
  		document.getElementById("mobError").innerHTML="Please Enter mobile..";
  		
  		x = false;
  	} else if (password1 == "" && password1.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError3").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password1.length < 1) {
  		document.getElementById("otpError3").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError3").innerHTML="";
  	}
  	 if (password2 == "" && password2.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError3").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password2.length < 1) {
  		document.getElementById("otpError3").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError3").innerHTML="";
  	}
  	 if (password3 == "" && password3.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError3").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password3.length < 1) {
  		document.getElementById("otpError3").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError3").innerHTML="";
  	}
  	 if (password4 == "" && password4.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError3").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password4.length < 1) {
  		document.getElementById("otpError3").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError3").innerHTML="";
  	}
  	 if (password5 == "" && password5.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError3").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password5.length < 1) {
  		document.getElementById("otpError3").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError3").innerHTML="";
  	}
  	 if (password6 == "" && password6.length < 1) {
  		document.getElementById("mobError").innerHTML="";
  		document.getElementById("otpError3").innerHTML="Please Enter OTP..";
  		x = false;
  	}
  	 else if (password6.length < 1) {
  		document.getElementById("otpError3").innerHTML="Please Enter Valid OTP..";
  		x = false;
  	}
  	else{
  		document.getElementById("otpError3").innerHTML="";
  	}
	
	if(x){ 
		document.getElementById("signinLoader").style.display="flex";
  	$.ajax({
  			type: "POST",
  			//url:"/verifyOTP",
			url:"/otpVerifyWithTemplate",
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
				document.getElementById("password111").value="";
				document.getElementById("password222").value="";
				document.getElementById("password333").value="";
			    document.getElementById("password444").value="";
				document.getElementById("password555").value="";
				document.getElementById("password666").value="";
				//document.getElementById("authenticate").disabled = false;
  				if (obj['status']== true) {
					//document.getElementById("signinLoader").style.display="none";
					$('#RevokebulkUPIVoucherModal').hide();
					if (window.selectedVoucherIdsToRevoke && window.selectedVoucherIdsToRevoke.length > 0) {
					    sendRowIdsToBackend(window.selectedVoucherIdsToRevoke);
					    window.selectedVoucherIdsToRevoke = null;
						
					  }
  				}else if (obj['status'] == false) {
					document.getElementById("signinLoader").style.display="none";
					document.getElementById("otpError3").textContent=obj['message'];
					document.getElementById("otpError3").style.display="block";
					$('#RevokebulkUPIVoucherModal').show();
					//$('#revokeUPIVcAuthenticateFail').show();
				} else {
  				
  				}
  			},
  			error: function(e) {
  				alert('Error: ' + e);
  			}
  		});
		}
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


// Utility to format amount with rupee, commas, 2 decimals
function formatRupee(amount) {
    const num = parseFloat(amount) || 0;
    return `<div style="text-align: right;">₹${num.toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</div>`;
}

function formatPlainAmount(amount) {
    const num = parseFloat(amount) || 0;
    return num.toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

function getBankListWithVocher() {
    const employerId = document.getElementById("employerId").value;

    $.ajax({
        type: "POST",
        url: "/voucherCreateBankList",
        data: { orgId: employerId },
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

                    if (item.bankAccount === null) {
                        div.classList.add('active');

                        $.ajax({
                            type: "POST",
                            url: "/voucherCreateSummaryDetailByAccount",
                            data: { "orgId": employerId, "accNumber": null },
                            success: function (data) {
                                const jsonData = jQuery.parseJSON(data);
                                totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
                                totalIssueAmount.innerHTML = formatRupee(jsonData.issueDetail.totalIssueAmount);
                                redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
                                redemVAmount.innerHTML = formatRupee(jsonData.issueDetail.redemVAmount);
                                expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
                                expRevokeAmount.innerHTML = formatRupee(jsonData.issueDetail.expRevokeAmount);
                                activeCount.textContent = jsonData.issueDetail.activeCount || "0";
                                activeAmount.innerHTML = formatRupee(jsonData.issueDetail.activeAmount);
                                //totalVoucher.innerHTML = jsonData.issueDetail.totalIssueCount || "0";
								 totalVoucher.innerHTML = jsonData.issueDetail.activeCount || "0";
                               //totalvoucherValue.textContent = formatPlainAmount(jsonData.issueDetail.totalIssueAmount);
								totalvoucherValue.textContent = formatPlainAmount(jsonData.issueDetail.activeAmount);
                                totalvoucherValue.style.textAlign = "right";
                                totalvoucherValue.style.display = "block";
                            },
                            error: function (e) {
                                console.error('Error fetching default account details:', e);
                            },
                        });
                    }

                    div.addEventListener('click', () => {
                        const activeDiv = dataList.querySelector('.active');
                        if (activeDiv) activeDiv.classList.remove('active');
                        div.classList.add('active');

                        $.ajax({
                            type: "POST",
                            url: "/voucherCreateSummaryDetailByAccount",
                            data: { "orgId": employerId, "accNumber": item.bankAccount },
                            success: function (data) {
                                const jsonData = jQuery.parseJSON(data);
                                totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
                                totalIssueAmount.innerHTML = formatRupee(jsonData.issueDetail.totalIssueAmount);
                                redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
                                redemVAmount.innerHTML = formatRupee(jsonData.issueDetail.redemVAmount);
                                expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
                                expRevokeAmount.innerHTML = formatRupee(jsonData.issueDetail.expRevokeAmount);
                                activeCount.textContent = jsonData.issueDetail.activeCount || "0";
                                activeAmount.innerHTML = formatRupee(jsonData.issueDetail.activeAmount);
								//totalVoucher.innerHTML = jsonData.issueDetail.totalIssueCount || "0";
                                totalVoucher.innerHTML = jsonData.issueDetail.activeCount || "0";
                                //totalvoucherValue.textContent = formatPlainAmount(jsonData.issueDetail.totalIssueAmount);
								totalvoucherValue.textContent = formatPlainAmount(jsonData.issueDetail.activeAmount);
								totalvoucherValue.style.textAlign = "right";
                                totalvoucherValue.style.display = "block";
                            },
                            error: function (e) {
                                console.error('Error fetching account details:', e);
                            },
                        });
                    });

                    dataList.appendChild(div);
                });

                totalIssueCount.textContent = parsedData.issueDetail.totalIssueCount || "0";
                totalIssueAmount.innerHTML = formatRupee(parsedData.issueDetail.totalIssueAmount);
                redemVCount.textContent = parsedData.issueDetail.redemVCount || "0";
                redemVAmount.innerHTML = formatRupee(parsedData.issueDetail.redemVAmount);
                expRevokeCount.textContent = parsedData.issueDetail.expRevokeCount || "0";
                expRevokeAmount.innerHTML = formatRupee(parsedData.issueDetail.expRevokeAmount);
                activeCount.textContent = parsedData.issueDetail.activeCount || "0";
                activeAmount.innerHTML = formatRupee(parsedData.issueDetail.activeAmount);
                //totalVoucher.innerHTML = parsedData.issueDetail.totalIssueCount || "0";
				totalVoucher.innerHTML = parsedData.issueDetail.activeCount || "0";
                //totalvoucherValue.textContent = formatPlainAmount(parsedData.issueDetail.totalIssueAmount);4
				totalvoucherValue.textContent = formatPlainAmount(parsedData.issueDetail.activeAmount);
                totalvoucherValue.style.textAlign = "right";
                totalvoucherValue.style.display = "block";

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

function getIssueVoucherList1(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "/erupiVoucherCreateListRedeem",
		data: {
			//"employeeId": employerid,
			"orgId": employerid,
			"timePeriod":"AH",
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			 console.log("/erupiVoucherCreateListRedeem data= ",data2);
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
				{
				  "mData": "amount",
				  "render": function(data2, type, row) {
				    if (data2 === "" || data2 === null) {
				      return '';
				    } else {
				      return '₹'+""+ data2;
				    }
				  }
				},
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
				order: [[0, 'desc']],  // <--- force descending sort by creationDate
                "aaData": data2,
                "aoColumns": [
                  //  { "mData": "creationDate" },
				  {
				    "mData": null,
				    "orderable": false,
				    "className": "dt-body-center",
				    "render": function (data, type, row, meta) {
				      return `<input type="checkbox" class="rowCheckbox" value="${row.merchanttxnId}" style="margin-left: 12px;">`;
				    }
				  },
					/*{ 
					  "mData": "creationDate", 
					  "render": function (data) {
					      return formatDate(data);
					  }
					}*/
					{
					  "mData": "creationDate",
					  "render": function (data, type) {
					    if (type === 'sort' || type === 'type') {
					      // Parse into Date object so sorting works
					      const date = new Date(data);
					      const y = date.getFullYear();
					      const m = String(date.getMonth() + 1).padStart(2, '0');
					      const d = String(date.getDate()).padStart(2, '0');
					      const hh = String(date.getHours()).padStart(2, '0');
					      const mm = String(date.getMinutes()).padStart(2, '0');
					      const ss = String(date.getSeconds()).padStart(2, '0');

					      // Return sortable string
					      return `${y}-${m}-${d}T${hh}:${mm}:${ss}`;
					    }
					    // For display in the table
					    return formatDateTime(data);
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
					   
					      return '<img src="data:image/png;base64,' + row.mccMainIcon + '" alt="" width="24px" height="24px" style="margin-top:-10px;">'+ data;
					    
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



function erupiVoucherCreateListLimit(timePeriod = "AH") {
  var employerid = document.getElementById("employerId").value;
  $.ajax({
    type: "POST",
    url: "/erupiVoucherCreateListLimit",
    data: {
      "orgId": employerid,
      "timePeriod": timePeriod,
    },
    success: function (data) {
      var data1 = jQuery.parseJSON(data);
      var data2 = data1.data;
      console.log("datad for /erupiVoucherCreateListLimit ", data2);
      console.log("showing voucher in table /erupiVoucherCreateListLimit ", data2);

      var table = $('#vouchersTableList').DataTable({
        destroy: true,
        lengthChange: true,
        responsive: true,
        searching: true,
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
            "mData": null,
            "orderable": false,
            "className": 'dt-body-center',
            /*enables checkboxes only if voucher status is active 
			"render": function (data, type, row) {
              const expDate = new Date(row.expDate);
              const today = new Date();
              const isExpired = expDate < today;
              const isActive = row.type === "Created" && !isExpired;
              return `<input type="checkbox" class="rowCheckbox" data-id="${row.id}" ${isActive ? "" : "disabled"}>`;
            }*/
			"render": function (data, type, row) {
			  return `<input type="checkbox" class="rowCheckbox" data-id="${row.id}">`;
			}

          },
          /*{
            "mData": "creationDate",
            "render": function (data) {
              return formatDateTime(data);
            }
          },*/
		  {
  		    "mData": "creationDate",
  		    "render": function (data, type) {
  		      if (type === 'sort' || type === 'type') {
  		        // Parse into Date object so sorting works
  		        const date = new Date(data);
  		        const y = date.getFullYear();
  		        const m = String(date.getMonth() + 1).padStart(2, '0');
  		        const d = String(date.getDate()).padStart(2, '0');
  		        const hh = String(date.getHours()).padStart(2, '0');
  		        const mm = String(date.getMinutes()).padStart(2, '0');
  		        const ss = String(date.getSeconds()).padStart(2, '0');

  		        // Return sortable string
  		        return `${y}-${m}-${d}T${hh}:${mm}:${ss}`;
  		      }
  		      // For display in the table
  		      return formatDateTime(data);
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
              return `
                <div style="display: flex; align-items: center; gap: 8px;">
                  ${imgHTML}
                  <span>${maskedAccount}</span>
                </div>
              `;
            }
          },
          {
            "mData": null,
            "render": function (data, type, row) {
              const mccIcon = row.mccMainIcon;
              const imgHTML = mccIcon
                ? `<img src="data:image/png;base64,${mccIcon}" alt="MCC Icon" width="24" height="24">`
                : '';
              return `
                <div style="display: flex; align-items: center; gap: 8px;">
                  ${imgHTML}
                  <span>${row.purposeDesc || ''}</span>
                </div>
              `;
            }
          },
		  {
		    "mData": "redemtionType",
		    "render": function (data, type, row) {
		      const normalized = (data || '').toLowerCase();

		      const label = normalized === "multiple" ? "Multiple"
		                 : normalized === "single"   ? "Single"
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
            /*"render": function (data, type, row) {
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
				  case "Partially Redeemed": labelText = "Partially Redeemed"; labelClass = "pill-wide bg-lightyellow-txt-yellow-pill"; break;
                  default: labelText = data; labelClass = "pill bg-lightgrey-txt-grey-pill";
                }
              }
              return `<span class="${labelClass}">${labelText}</span>`;
            }*/
			"render": function (data, type, row) {
			  // Safely parse dd-mm-yyyy to Date
			  /*function parseDDMMYYYY(dateStr) {
			    const [dd, mm, yyyy] = dateStr.split('-');
			    return new Date(`${yyyy}-${mm}-${dd}T00:00:00`);
			  }*/
			  // Adjust parsing depending on backend format (yyyy-mm-dd)
			  function parseDate(dateStr) {
			    if (!dateStr) return null;
			    
			    // If format looks like yyyy-mm-dd
			    if (/^\d{4}-\d{2}-\d{2}/.test(dateStr)) {
			      return new Date(dateStr + "T00:00:00");
			    }
			    
			    // If format looks like dd-mm-yyyy
			    if (/^\d{2}-\d{2}-\d{4}/.test(dateStr)) {
			      const [dd, mm, yyyy] = dateStr.split('-');
			      return new Date(`${yyyy}-${mm}-${dd}T00:00:00`);
			    }
			    
			    return new Date(dateStr); // fallback
			  }


			  //const expDate = parseDDMMYYYY(row.expDate);
			  const expDate = parseDate(row.expDate);
			  const today = new Date();
			  today.setHours(0, 0, 0, 0); // Compare only date part
			  //console.log("Exp date parsed:", expDate, "Row expDate:", row.expDate,"merchanttxnId",row.merchanttxnId, "Today:", today);

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
			      case "Partially Redeemed": labelText = "Partially Redeemed"; labelClass = "pill-wide bg-lightyellow-txt-yellow-pill"; break;
			      default: labelText = data; labelClass = "pill bg-lightgrey-txt-grey-pill";
			    }
			  }
			  return `<span class="${labelClass}">${labelText}</span>`;
			}

          },
          {
            "mData": "amount",
            "class": "text-right",
            "render": function (data2, type, row) {
              if (!data2) return '';
              let amount = parseFloat(data2);
              let formattedAmount = amount.toFixed(2);
              let localizedAmount = parseFloat(formattedAmount).toLocaleString('en-IN', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
              });
              return '<div class="amount-cell">₹' + localizedAmount + '</div>';
            }
          },
          {
            "mData": "redeemAmount",
            "class": "text-right",
            "render": function (data2, type, row) {
              if (!data2) return '';
              let amount = parseFloat(data2);
              let formattedAmount = amount.toFixed(2);
              let localizedAmount = parseFloat(formattedAmount).toLocaleString('en-IN', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
              });
              return '<div class="amount-cell">₹' + localizedAmount + '</div>';
            }
          },
          {
            "mData": null,
            "render": function (data, type, row) {
              const encodedRow = encodeURIComponent(JSON.stringify(row));
              const expDate = new Date(row.expDate);
              const today = new Date();
              const isExpired = expDate < today;
              const isActive = row.type === "Created" && !isExpired;
              const revokeBtn = isActive
                ? `<button class="dropdown-item py-2" onclick="openRevokeDialog('${encodedRow}')">Revoke</button>`
                : `<button class="dropdown-item py-2" disabled>Revoke</button>`;
              const smsBtn = isActive
                ? `<button class="dropdown-item py-2" data-toggle="modal" data-target="#tableSendSms" onclick="sendsms('${encodedRow}')">Send SMS</button>`
                : `<button class="dropdown-item py-2" disabled>Send SMS</button>`;
              return `
                <div class="dropdown no-arrow ml-2">
                  <a class="dropdown-toggle" href="#" role="button" data-toggle="dropdown">
                    <i class="fas fa-ellipsis-v fa-sm" style="cursor:pointer;"></i>
                  </a>
                  <div class="dropdown-menu dropdown-menu-right shadow">
                    ${revokeBtn}
                    ${smsBtn}
                    <button class="dropdown-item py-2 view-voucher-details" onclick="viewhistory('${encodedRow}')">View History</button>
                  </div>
                </div>`;
            }
          }
        ]
      });

      /*$('#checkAll').on('click', function () {
        var rows = $('#vouchersTableList').DataTable().rows({ 'search': 'applied' }).nodes();
        $('input[type="checkbox"].rowCheckbox', rows).prop('checked', this.checked);
      });*/
	  
	  // Search input
	   $('#searchInput').on('keyup', function () {
	     table.search(this.value).draw();
	     enableClearButton(this.value.trim() !== '');
	   });

	   // Clear filters button
	   $('#clearFilters').on('click', function () {
	     $('#searchInput').val('');
	     table.search('').draw();
	     enableClearButton(false);
	   });

	   // ✅ your check all logic stays the same
	  /* $('#checkAll').on('click', function () {
	     const rows = table.rows({ search: 'applied' }).nodes();
	     const isChecked = $(this).is(':checked');
	     $('input[type="checkbox"].rowCheckbox', rows).each(function () {
	       if (!$(this).is(':disabled')) {
	         $(this).prop('checked', isChecked);
	       }
	     });
	   });*/	 
	   // ✅ Header checkbox — only check visible, non-disabled rows
	 $('#checkAll').on('click', function () {
	     const isChecked = $(this).is(':checked');
	     $('#vouchersTableList tbody tr:visible').each(function () {
	         const checkbox = $(this).find('.rowCheckbox');
	         if (!checkbox.is(':disabled')) {
	             checkbox.prop('checked', isChecked);
	         }
	     });
	 });

    },
    error: function (e) {
      alert('Failed to fetch JSON data' + e);
    }
  });
}




/*
// Helper function to send IDs
function sendRowIdsToBackend(ids) {
	var userRole = document.getElementById("userRole").value;
	console.log('Sent IDs:', ids);
	// Remove ALL modal backdrops
   document.querySelectorAll('.modal-backdrop').forEach(backdrop => backdrop.remove());

   // Remove modal-open class from body
   document.body.classList.remove('modal-open');

   // Optional: Reset body overflow if it was locked
   document.body.style.overflow = '';
  $.ajax({
    type: 'POST',
    url: '/erupiVoucherRevokeBulk', // Replace with your actual endpoint
    data: {
		"arrayofid": ids	
	} ,
   
    success: function (response) {
		var data1 = jQuery.parseJSON(response);
		     var data2 = data1.data;
      console.log('Sent IDs:', ids);
	  //erupiVoucherCreateListLimit(timePeriod = "AH");//reload the table
	  //alert("Selected Vouchers Revoked Successfully");
	  $('#bulkrevokeSuccess').modal('show'); // Show modal
	  if(userRole=="9"||userRole==9)
	  {window.location.href="/upiVoucherIssuanceNew";}
	  else
		{window.location.href="/reputeUpiVoucherIssuance";}
    },
    error: function (xhr) {
      console.error('Failed to send IDs:', xhr);
    }
  });
}*/
function sendRowIdsToBackend(ids) {
  var userRole = document.getElementById("userRole").value;
  console.log('Sent IDs:', ids);

  // Remove all modal backdrops and reset body state
  document.querySelectorAll('.modal-backdrop').forEach(backdrop => backdrop.remove());
  document.body.classList.remove('modal-open');
  document.body.style.overflow = '';

  $.ajax({
    type: 'POST',
    url: '/erupiVoucherRevokeBulk',
    data: {
      "arrayofid": ids
    },
    success: function (response) {
      var data1 = jQuery.parseJSON(response);
      var data2 = data1.data;
      console.log('Sent IDs:', ids);

      // Show success modal
      $('#bulkrevokeSuccess').modal('show');

      // Redirect after modal display
      setTimeout(function () {
		document.getElementById("signinLoader").style.display="none";
        if (userRole == "9" || userRole == 9) {
          window.location.href = "/upiVoucherIssuanceNew";
        } else {
          window.location.href = "/reputeUpiVoucherIssuance";
        }
      }, 1500); // 1.5 seconds delay
    },
    error: function (xhr) {
      console.error('Failed to send IDs:', xhr);
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
				{
				  "mData": "amount",
				  "render": function(data2, type, row) {
				    if (data2 === "" || data2 === null) {
				      return '';
				    } else {
				      return '₹'+""+ data2;
				    }
				  }
				},
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
function formatDate(dateStr) { 
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
} 
function formatDateTime(dateStr) { 
  if (!dateStr) return '';
  const date = new Date(dateStr);

  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();

  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');

  return `${day}-${month}-${year}<br>${hours}:${minutes}:${seconds}`;
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

/*
function viewhistory(rowData) {
  const employerId = document.getElementById("employerId").value;

  if (typeof rowData === "string") {
    rowData = JSON.parse(decodeURIComponent(rowData));
  }

  document.getElementById("signinLoader").style.display = "flex";

  $.ajax({
    type: "POST",
    url: "/erupiVoucherStatusHistory",
    data: {
      id: rowData.id
    },
    success: function (data) {
      document.getElementById("signinLoader").style.display = "none";

      const parsed = jQuery.parseJSON(data);
      const data1 = parsed.data;
      console.log("parsed for /erupiVoucherStatusHistory ", parsed);
      console.log("data for /erupiVoucherStatusHistory ", data1);

      if (parsed.status==true) {
       
     
	  document.getElementById("bs-canvas-right1").style.right = "0";
	       document.getElementById("modal-overlay").style.display = "block";
      const details = data1;

    
      document.getElementById("voucherDesc").textContent = data1.voucherDesc;
      document.getElementById("voucherAmount").textContent = `₹${data1.voucherAmount}`;
	  document.getElementById("accountNumber").textContent = data1.accountNumber;
	  document.getElementById("name").textContent = data1.name;
	  document.getElementById("voucherMobile").textContent =data1.mobile;
	  document.getElementById("expDate").textContent =data1.expDate;
	  document.getElementById("issueDate").textContent =data1.issueDate;
	  document.getElementById("merchantTranId").textContent =data1.merchantTranId;
	  document.getElementById("activeAmount").textContent =data1.activeAmount;activeAmount 
      document.getElementById("voucherStatus").textContent = data1.voucherStatus;
	  document.getElementById("amountSpent").textContent = data1.amountSpent;
	  
      
      const transactionList = document.querySelector(".voucher-transaction-list");
      transactionList.innerHTML = '<div class="voucher-transaction-line"></div>';

      (data1.redeemData || []).forEach((txn, idx) => {
        const item = document.createElement("li");
        item.className = "voucher-transaction-item";
        item.innerHTML = `
          <div class="voucher-transaction-step">${idx + 1}</div>
          <div class="voucher-transaction-card">
            <div class="voucher-transaction-top">
              <div>
                <div class="voucher-meta-label">Transaction date</div>
                <div class="voucher-meta-value">${txn.date}</div>
              </div>
              <div>
                <div class="voucher-meta-label">Transaction RRN</div>
                <div class="voucher-meta-value">${txn.rrn}</div>
              </div>
            </div>
            <div class="voucher-transaction-bottom">
              <div>
                <div class="voucher-meta-label">Merchant Name</div>
                <div class="voucher-meta-value">${txn.merchant}</div>
              </div>
              <div>
                <div class="voucher-meta-label">Amount</div>
                <div class="voucher-meta-value">₹${txn.amount}</div>
              </div>
            </div>
          </div>`;
        transactionList.appendChild(item);
      });

     
    }
	}//closed if 
	,
    error: function (e) {
      document.getElementById("signinLoader").style.display = "none";
      alert('Error: ' + e);
    }
  });
}*/
/*function viewhistory(rowData) {
  const employerId = document.getElementById("employerId").value;

  if (typeof rowData === "string") {
    rowData = JSON.parse(decodeURIComponent(rowData));
  }

  document.getElementById("signinLoader").style.display = "flex";

  $.ajax({
    type: "POST",
    url: "/erupiVoucherStatusHistory",
    data: {
      id: rowData.id
    },
    success: function (data) {
      document.getElementById("signinLoader").style.display = "none";

      const parsed = jQuery.parseJSON(data);
      const data1 = parsed.data;
      console.log("parsed for /erupiVoucherStatusHistory ", parsed);
      console.log("data for /erupiVoucherStatusHistory ", data1);

      if (parsed.status === true) {
        document.getElementById("bs-canvas-right1").style.right = "0";
        document.getElementById("modal-overlay").style.display = "block";

        document.getElementById("voucherDesc").textContent = data1.voucherDesc;
        document.getElementById("voucherAmount").textContent = `₹${data1.voucherAmount}`;
        document.getElementById("name").textContent = data1.name;
        document.getElementById("voucherMobile").textContent = data1.mobile;
        document.getElementById("expDate").textContent = data1.expDate;
        document.getElementById("issueDate").textContent = data1.issueDate;
        document.getElementById("merchantTranId").textContent = data1.merchantTranId;
        document.getElementById("balanceAmount").textContent = `₹${data1.activeAmount}`;
        document.getElementById("redemtionType").textContent = data1.redemtionType;
		     document.getElementById("amountSpent").textContent = `₹${data1.amountSpent ?? 0}`;

        const statusBox = document.querySelector(".voucher-status-box");
        const statusTextEls = statusBox.querySelectorAll(".voucher-status-text");
        const balanceEl = document.getElementById("balanceAmount");
        const statusValue = data1.voucherStatus?.trim().toLowerCase();

        document.getElementById("voucherStatus").textContent = data1.voucherStatus;

        // Reset all known status classes
        statusBox.className = "voucher-status-box";
        statusTextEls.forEach(el => el.className = "voucher-status-text");
        balanceEl.className = "voucher-balance";

        // Apply CSS class based on status using switch-case
        switch (statusValue) {
          case "failed":
            statusBox.classList.add("voucher-status-box-failed");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-failed"));
            balanceEl.classList.add("voucher-balance-failed");
            break;
          case "revoke":
            statusBox.classList.add("voucher-status-box-Revoke");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-Revoke"));
            balanceEl.classList.add("voucher-balance-Revoke");
            break;	
		case "expired":
		           statusBox.classList.add("voucher-status-box-Expire");
		           statusTextEls.forEach(el => el.classList.add("voucher-status-text-Expire"));
		           balanceEl.classList.add("voucher-balance-Expire");
		           break;
          // Add more cases as needed for other statuses
        }

        const accountDisplay = document.getElementById("accountNumber");
        const bankLogo = data1.bankLogo
          ? `<img src='data:image/png;base64,${data1.bankLogo}' alt='Bank' width='24' height='24' style='vertical-align:middle;margin-right:6px;'>`
          : '';
        const maskedAcc = data1.accountNumber?.slice(-4) ?? '';
        accountDisplay.innerHTML = `${bankLogo}xxxx${maskedAcc}`;

        if (data1.voucherLogo) {
          const logoEl = document.getElementById("voucherLogoImg");
          logoEl.src = `data:image/png;base64,${data1.voucherLogo}`;
          logoEl.style.display = "inline-block";
        }

        const transactionList = document.querySelector(".voucher-transaction-list");
        transactionList.innerHTML = '<div class="voucher-transaction-line"></div>';

        (data1.redeemData || []).forEach((txn, idx) => {
          const item = document.createElement("li");
          item.className = "voucher-transaction-item";
          item.innerHTML = `
            <div class="voucher-transaction-step">${idx + 1}</div>
            <div class="voucher-transaction-card">
              <div class="voucher-transaction-top">
                <div>
                  <div class="voucher-meta-label">Transaction date</div>
                  <div class="voucher-meta-value">${txn.transactionDate}</div>
                </div>
                <div>
                  <div class="voucher-meta-label">Transaction RRN</div>
                  <div class="voucher-meta-value">${txn.bankrrn}</div>
                </div>
              </div>
              <div class="voucher-transaction-bottom">
                <div>
                  <div class="voucher-meta-label">Merchant Name</div>
                  <div class="voucher-meta-value">${txn.marchantName}</div>
                </div>
                <div>
                  <div class="voucher-meta-label">Amount</div>
                  <div class="voucher-meta-value">₹${txn.amount}</div>
                </div>
              </div>
            </div>`;
          transactionList.appendChild(item);
        });
      }
    },
    error: function (e) {
      document.getElementById("signinLoader").style.display = "none";
      alert('Error: ' + e);
    }
  });
}*/
/*function viewhistory(rowData) {
  const employerId = document.getElementById("employerId").value;

  if (typeof rowData === "string") {
    rowData = JSON.parse(decodeURIComponent(rowData));
  }

  document.getElementById("signinLoader").style.display = "flex";

  $.ajax({
    type: "POST",
    url: "/erupiVoucherStatusHistory",
    data: {
      id: rowData.id
    },
    success: function (data) {
      document.getElementById("signinLoader").style.display = "none";

      const parsed = jQuery.parseJSON(data);
      const data1 = parsed.data;
      console.log("parsed for /erupiVoucherStatusHistory ", parsed);
      console.log("data for /erupiVoucherStatusHistory ", data1);

      if (parsed.status === true) {
        document.getElementById("bs-canvas-right1").style.right = "0";
        document.getElementById("modal-overlay").style.display = "block";

        document.getElementById("voucherDesc").textContent = data1.voucherDesc;
        document.getElementById("voucherAmount").textContent = `₹${data1.voucherAmount}`;
        document.getElementById("name").textContent = data1.name;
        document.getElementById("voucherMobile").textContent = data1.mobile;
        document.getElementById("expDate").textContent = data1.expDate;
        //document.getElementById("issueDate").textContent = data1.issueDate;
		document.getElementById("issueDate").textContent = formatDate(data1.issueDate);
        document.getElementById("merchantTranId").textContent = data1.merchantTranId;
        document.getElementById("balanceAmount").textContent = `₹${data1.activeAmount}`;
        document.getElementById("redemtionType").textContent = data1.redemtionType;
        document.getElementById("amountSpent").textContent = `₹${data1.amountSpent ?? 0}`;
		document.getElementById("umn").textContent = data1.umn;
        const statusBox = document.querySelector(".voucher-status-box");
        const statusTextEls = statusBox.querySelectorAll(".voucher-status-text");
        const balanceEl = document.getElementById("balanceAmount");
       	//const statusValue = data1.voucherStatus?.trim().toLowerCase();
	   

        //document.getElementById("voucherStatus").textContent = data1.voucherStatus;
		const status = data1.voucherStatus;
		const capitalizedStatus = status.charAt(0).toUpperCase() + status.slice(1);
		document.getElementById("voucherStatus").textContent = capitalizedStatus;


        statusBox.className = "voucher-status-box";
        statusTextEls.forEach(el => el.className = "voucher-status-text");
        balanceEl.className = "voucher-balance";

        switch (capitalizedStatus) {
          case "Failed":
            statusBox.classList.add("voucher-status-box-failed");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-failed"));
            balanceEl.classList.add("voucher-balance-failed");
            break;
          case "Revoke":
            statusBox.classList.add("voucher-status-box-Revoke");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-Revoke"));
            balanceEl.classList.add("voucher-balance-Revoke");
            break;
          case "Expired":
            statusBox.classList.add("voucher-status-box-Expire");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-Expire"));
            balanceEl.classList.add("voucher-balance-Expire");
            break;
		case "Redeemed":
		    statusBox.classList.add("voucher-status-box-Expire");
		    statusTextEls.forEach(el => el.classList.add("voucher-status-text-Expire"));
		    balanceEl.classList.add("voucher-balance-Expire");
		    break;
        }

        const accountDisplay = document.getElementById("accountNumber");
        const bankLogo = data1.bankLogo
          ? `<img src='data:image/png;base64,${data1.bankLogo}' alt='Bank' width='24' height='24' style='vertical-align:middle;margin-right:6px;'>`
          : '';
        const maskedAcc = data1.accountNumber?.slice(-4) ?? '';
        accountDisplay.innerHTML = `${bankLogo}xxxx${maskedAcc}`;

        if (data1.voucherLogo) {
          const logoEl = document.getElementById("voucherLogoImg");
          logoEl.src = `data:image/png;base64,${data1.voucherLogo}`;
          logoEl.style.display = "inline-block";
        }

        const transactionList = document.querySelector(".voucher-transaction-list");
        transactionList.innerHTML = "";

        const redeemData = data1.redeemData || [];
        if (redeemData.length > 1) {
          transactionList.innerHTML = '<div class="voucher-transaction-line"></div>';
        }

        redeemData.forEach((txn, idx) => {
          const item = document.createElement("li");
          item.className = "voucher-transaction-item";
          item.innerHTML = `
            <div class="voucher-transaction-step">${idx + 1}</div>
            <div class="voucher-transaction-card">
              <div class="voucher-transaction-top">
                <div>
                  <div class="voucher-meta-label">Transaction date</div>
                  <div class="voucher-meta-value">${txn.transactionDate}</div>
                </div>
                <div>
                  <div class="voucher-meta-label">Transaction RRN</div>
                  <div class="voucher-meta-value">${txn.bankrrn}</div>
                </div>
              </div>
              <div class="voucher-transaction-bottom">
                <div>
                  <div class="voucher-meta-label">Merchant Name</div>
                  <div class="voucher-meta-value">${txn.marchantName}</div>
                </div>
                <div>
                  <div class="voucher-meta-label">Amount</div>
                  <div class="voucher-meta-value">₹${txn.amount}</div>
                </div>
              </div>
            </div>`;
          transactionList.appendChild(item);
		  // Insert dotted line only between items, not after the last
		  if (idx < redeemData.length - 1) {
		      const line = document.createElement("div");
		      line.className = "voucher-transaction-line";
		      transactionList.appendChild(line);
		    }
			
        });
      }
    },
    error: function (e) {
      document.getElementById("signinLoader").style.display = "none";
      alert('Error: ' + e);
    }
  });
} */

function viewhistory(rowData) {
  const employerId = document.getElementById("employerId").value;

  if (typeof rowData === "string") {
    rowData = JSON.parse(decodeURIComponent(rowData));
  }

  document.getElementById("signinLoader").style.display = "flex";

  $.ajax({
    type: "POST",
    url: "/erupiVoucherStatusHistory",
    data: { id: rowData.id },
    success: function (data) {
      document.getElementById("signinLoader").style.display = "none";

      const parsed = jQuery.parseJSON(data);
      const data1 = parsed.data;
      console.log("parsed for /erupiVoucherStatusHistory ", parsed);
      console.log("data for /erupiVoucherStatusHistory ", data1);

      if (parsed.status === true) {
        document.getElementById("bs-canvas-right1").style.right = "0";
        document.getElementById("modal-overlay").style.display = "block";

        document.getElementById("voucherDesc").textContent = data1.voucherDesc;
        document.getElementById("voucherAmount").textContent = `₹${data1.voucherAmount}`;
        document.getElementById("name").textContent = data1.name;
        document.getElementById("voucherMobile").textContent = data1.mobile;
        document.getElementById("expDate").textContent = data1.expDate;
        //document.getElementById("issueDate").textContent = data1.issueDate;
		document.getElementById("issueDate").textContent = formatDate(data1.issueDate);
        document.getElementById("merchantTranId").textContent = data1.merchantTranId;
        document.getElementById("balanceAmount").textContent = `₹${data1.activeAmount}`;
        document.getElementById("redemtionType").textContent = data1.redemtionType;
        document.getElementById("amountSpent").textContent = `₹${data1.amountSpent ?? 0}`;
		document.getElementById("umn").textContent = data1.umn;
        const statusBox = document.querySelector(".voucher-status-box");
        const statusTextEls = statusBox.querySelectorAll(".voucher-status-text");
        const balanceEl = document.getElementById("balanceAmount");
       	//const statusValue = data1.voucherStatus?.trim().toLowerCase();
	   

        //document.getElementById("voucherStatus").textContent = data1.voucherStatus;
		const status = data1.voucherStatus;
		if (status == null || status.trim() === "") {
		    document.getElementById("voucherStatus").textContent = status; // will show "null" or empty
		}
		else
		{
			const capitalizedStatus = status.charAt(0).toUpperCase() + status.slice(1);
			document.getElementById("voucherStatus").textContent = capitalizedStatus;


        statusBox.className = "voucher-status-box";
        statusTextEls.forEach(el => el.className = "voucher-status-text");
        balanceEl.className = "voucher-balance";

        switch (capitalizedStatus) {
          case "Failed":
            statusBox.classList.add("voucher-status-box-failed");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-failed"));
            balanceEl.classList.add("voucher-balance-failed");
            break;
          case "Revoke":
            statusBox.classList.add("voucher-status-box-Revoke");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-Revoke"));
            balanceEl.classList.add("voucher-balance-Revoke");
            break;
          case "Expired":
            statusBox.classList.add("voucher-status-box-Expire");
            statusTextEls.forEach(el => el.classList.add("voucher-status-text-Expire"));
            balanceEl.classList.add("voucher-balance-Expire");
            break;
		case "Redeemed":
		    statusBox.classList.add("voucher-status-box-Expire");
		    statusTextEls.forEach(el => el.classList.add("voucher-status-text-Expire"));
		    balanceEl.classList.add("voucher-balance-Expire");
		    break;
        }
	}

        const accountDisplay = document.getElementById("accountNumber");
        const bankLogo = data1.bankLogo
          ? `<img src='data:image/png;base64,${data1.bankLogo}' alt='Bank' width='24' height='24' style='vertical-align:middle;margin-right:6px;'>`
          : '';
        const maskedAcc = data1.accountNumber?.slice(-4) ?? '';
        accountDisplay.innerHTML = `${bankLogo}xxxx${maskedAcc}`;

        if (data1.voucherLogo) {
          const logoEl = document.getElementById("voucherLogoImg");
          logoEl.src = `data:image/png;base64,${data1.voucherLogo}`;
          logoEl.style.display = "inline-block";
        }

        const transactionList = document.querySelector(".voucher-transaction-list");
        transactionList.innerHTML = "";

        const redeemData = data1.redeemData || [];
        if (redeemData.length > 1) {
          transactionList.innerHTML = '<div class="voucher-transaction-line"></div>';
        }

        // If no transaction history
        if (redeemData.length === 0) {
          const noHistoryMsg = document.createElement("li");
          noHistoryMsg.className = "voucher-transaction-item no-history";
          noHistoryMsg.textContent = "No transaction history available.";
          transactionList.appendChild(noHistoryMsg);
        } else {
          if (redeemData.length > 1) {
            transactionList.innerHTML = '<div class="voucher-transaction-line"></div>';
          }

          redeemData.forEach((txn, idx) => {
            const item = document.createElement("li");
            item.className = "voucher-transaction-item";
            item.innerHTML = `
              <div class="voucher-transaction-step">${idx + 1}</div>
              <div class="voucher-transaction-card">
                <div class="voucher-transaction-top">
                  <div>
                    <div class="voucher-meta-label">Transaction date</div>
                    <div class="voucher-meta-value">${txn.transactionDate}</div>
                  </div>
                  <div>
                    <div class="voucher-meta-label">Transaction RRN</div>
                    <div class="voucher-meta-value">${txn.bankrrn}</div>
                  </div>
                </div>
                <div class="voucher-transaction-bottom">
                  <div>
                    <div class="voucher-meta-label">Merchant Name</div>
                    <div class="voucher-meta-value">${txn.marchantName}</div>
                  </div>
                  <div>
                    <div class="voucher-meta-label">Amount</div>
                    <div class="voucher-meta-value">₹${txn.amount}</div>
                  </div>
                </div>
              </div>`;
            transactionList.appendChild(item);

            // Insert dotted line between items
            if (idx < redeemData.length - 1) {
              const line = document.createElement("div");
              line.className = "voucher-transaction-line";
              transactionList.appendChild(line);
            }
          });
        }
      }
    },
    error: function (e) {
      document.getElementById("signinLoader").style.display = "none";
      alert('Error: ' + e);
    }
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

	   
	 /*  document.getElementById('downloadBtn').addEventListener('click', function () {
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
	   */
	   function requestedVoucherCount() {
	   											   
	   											    const orgId = document.getElementById('employerId').value;

	   											    $.ajax({
	   											        type: "GET",
	   											        url: "/getRequestedVoucherApproveList",
	   											        data: { orgId },
	   											        success: function (data) {
	   											            const data1 = jQuery.parseJSON(data);
	   											            const data2 = data1.data;
															
	
	   											            // Show count on bell badge
	   											            const badge = document.getElementById("bellCountBadge");
	   											            if (data2.length > 0) {
	   											                badge.style.display = "inline-block";
	   											                badge.textContent = data2.length;
	   											            } else {
	   											                badge.style.display = "none";
	   											            }

	   											           
	   											        },
	   											        error: function (e) {
	   											            alert('Failed to fetch JSON data' + e);
	   											        }
	   											    });
	   											}
