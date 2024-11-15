



function resendVoucherOTP() {
	
	var userName = document.getElementById("banklinkedMobile").value;
	var orderId = document.getElementById("orderId").value;
	
	
	//document.getElementById("optBtn").disabled = true;
	//document.getElementById("mobError").innerHTML="";
	
	//document.getElementById("loginLoader").style.display = "flex";
	$.ajax({
		type: "POST",
		url:"/smsOtpResender",
		dataType: 'json',
		data: {
			"mob": userName,
			"orderId":orderId
		},
		success: function(data) {
			var obj = data;
			//document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == "SUCCESS") {
				//$('#errorOtp').hide('slow');
				//$('#loginIdDiv').hide('slow');
				var timeleft = "60";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					//document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft <= 0) {
						clearInterval(downloadTimer);
						//document.getElementById("optBtn").disabled = false;
						document.getElementById("countdown").innerHTML = " ";
						//document.getElementById("optBtn").style.display = "none";
						//document.getElementById("verifyotpdiv").style.display = "none";
						
						//$('#loginIdDiv').hide('slow');
					}
				//	document.getElementById('password1').focus();
				}, 1000);
				//$('#loginIdDiv').show('slow');
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
	document.getElementById("authenticate").disabled = true;
  	var password1 = document.getElementById("password1").value;
  	var password2 = document.getElementById("password2").value;
  	var password3 = document.getElementById("password3").value;
  	var password4 = document.getElementById("password4").value;
  	var password5 = document.getElementById("password5").value;
  	var password6 = document.getElementById("password6").value;
  	var orderId = document.getElementById("orderId").value;
  	var linkedmobilenumber = document.getElementById("banklinkedMobile").value;
  	
  	if (document.getElementById("banklinkedMobile").value == "") {
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
  				"mob": linkedmobilenumber,
  				"orderId": orderId,
  				"userName":linkedmobilenumber
  			},
  			success: function(data) {
  				var obj = data;

  				if (obj['status']== true) {
					
					$('#otpModal').fadeOut();
					issueVoucher();
  					$('#errorOtp').hide('slow');
  				}else if (obj['status'] == false) {
				
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
			            newData = data;
			//            console.log(newData);
						$("#banklist option").remove();
			            var obj = jQuery.parseJSON( data );
			             obj = obj.data;
			        	 var count=0;
			         	for (var key in obj) {

			             var values =  obj[key];
			             var x = document.getElementById("banklist");
			             if(count==0){
			             var option = document.createElement("option");
			             option.text ="Select Bank";
			             option.value = "";
			             x.add(option);
			             }
			             var option = document.createElement("option");
			             option.value = values.acNumber;
			             option.text = values.bankName+" | "+values.acNumber;
			             x.add(option);

			             count++;
			             }
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}




function  getVoucherDetailByBoucherCode(){
	
    //document.getElementById("signinLoader").style.display="flex";
 	var voucherCode = document.getElementById("selectedOptionsDropdown").value;
 	$.ajax({
	type: "POST",
	url:"/getVoucherDetailByBoucherCode",
       data: {
			"voucherCode": voucherCode
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
           success: function(data){
           newData = data;
           //console.log(newData);
           var data1 = jQuery.parseJSON( newData );
		   //var data2 = data1.data;
			
		   document.getElementById("voucherId").value=data1.data.id;
			document.getElementById("voucherCode").value=data1.data.voucherCode;
			document.getElementById("voucherType").value=data1.data.voucherType;
			document.getElementById("voucherSubType").value=data1.data.voucherSubType;
			document.getElementById("voucherDesc").value=data1.data.voucherDesc;
			document.getElementById("purposeCode").value=data1.data.purposeCode;
			document.getElementById("activeStatus").value=data1.data.activeStatus;
			document.getElementById("createdby").value=data1.data.createdby;
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}



function  getBankDetailByBankAccountNumber(){
	
    //document.getElementById("signinLoader").style.display="flex";
 	var accountNumber = document.getElementById("banklist").value;
 	$.ajax({
	type: "POST",
	url:"/getBankDetailByAccountNo",
       data: {
			"acNumber": accountNumber
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
           success: function(data){
           newData = data;
           //console.log(newData);
           var data1 = jQuery.parseJSON( newData );
		   //var data2 = data1.data;
		   
		    document.getElementById("bankDetailView").style.display="flex";
			document.getElementById("accTypeView").innerHTML=data1.data.accountType;
			document.getElementById("accIfscView").innerHTML=data1.data.ifsc;
			document.getElementById("accHolderNameView").innerHTML=data1.data.accountHolderName;
			document.getElementById("accMidView").innerHTML=data1.data.merchentIid;
			
			document.getElementById("bankName").value=data1.data.bankName; 
			document.getElementById("bankCode").value=data1.data.bankCode; 
			document.getElementById("accountHolderName").value=data1.data.accountHolderName; 
			
			document.getElementById("acNumber").value=data1.data.acNumber; 
			document.getElementById("accountType").value=data1.data.accountType; 
			document.getElementById("ifsc").value=data1.data.ifsc; 
			
			document.getElementById("erupiFlag").value=data1.data.erupiFlag; 
			document.getElementById("branchCode").value=data1.data.branchCode; 
			document.getElementById("authStatus").value=data1.data.authStatus; 
			
			document.getElementById("authResponse").value=data1.data.authResponse; 
			document.getElementById("banklinkedMobile").value=data1.data.mobile; 
			document.getElementById("accstatus").value=data1.data.accstatus; 

			document.getElementById("orgId").value=data1.data.orgId; 
			document.getElementById("orgCode").value=data1.data.orgCode; 
			document.getElementById("tid").value=data1.data.tid;
			
			document.getElementById("merchentIid").value=data1.data.merchentIid; 
			document.getElementById("mcc").value=data1.data.mcc; 
			document.getElementById("submurchentid").value=data1.data.submurchentid;  
			document.getElementById("payerva").value=data1.data.payerva;  
												 
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}


function  createSingleVoucherValidation(){
	
    //document.getElementById("signinLoader").style.display="flex";
	var banklist = document.getElementById("banklist").value;
	
	var voucher = document.getElementById("voucherId").value;
	var beneficiaryName = document.getElementById("beneficiaryName").value;
	var beneficiaryMobile = document.getElementById("beneficiaryMobile").value;;
	var amount = document.getElementById("amount").value;
	var startDate = document.getElementById("startDate").value;
	var validity = document.getElementById("expiryDate").value;
	
	var voucherCode = document.getElementById("voucherCode").value;
	var voucherType = document.getElementById("voucherType").value;;
	var voucherSubType = document.getElementById("voucherSubType").value;
	var voucherDesc = document.getElementById("voucherDesc").value;
	var  purposeCode= document.getElementById("purposeCode").value;
	var activeStatus = document.getElementById("activeStatus").value;
	var createdby = document.getElementById("employerName").value;
	var bankCode = document.getElementById("bankCode").value;
	var mcc = document.getElementById("mcc").value;
	var payerva = document.getElementById("payerva").value;
	
	 document.getElementById("voucherlbl").innerHTML= $("#selectedOptionsDropdown option:selected").text();
	 document.getElementById("vtypelbl").innerHTML = $("#voucherType option:selected").text();
	 document.getElementById("namelbl").innerHTML = $("#beneficiaryName").val();
	 document.getElementById("mobilelbl").innerHTML = $("#beneficiaryMobile").val();
	 document.getElementById("amountlbl").innerHTML = $("#amount").val();
	 document.getElementById("startdatelbl").innerHTML = $("#startDate").val();
	 document.getElementById("validitylbl").innerHTML = $("#expiryDate").val();
    var employerId = document.getElementById("employerId").value;
	var employerName = document.getElementById("employerName").value;
	if(banklist=="" || banklist==null){
				document.getElementById("banklistError").innerHTML="Please Select Bank";
				return false;
			}
		else{
			document.getElementById("banklistError").innerHTML="";
		}
			
	if(voucher=="" || voucher==null){
				document.getElementById("selectedOptionsDropdownError").innerHTML="Please Select Voucher";
				return false;
			}
		else{
			document.getElementById("selectedOptionsDropdownError").innerHTML="";
		}

		if(voucherType=="" || voucherType==null){
				document.getElementById("voucherTypeError").innerHTML="Please Select Type";
				return false;
			}
		else{
			document.getElementById("voucherTypeError").innerHTML="";
		}

	if(beneficiaryName=="" || beneficiaryName==null){
			document.getElementById("beneficiaryNameError").innerHTML="Please Enter Beneficiary Name";
			return false;
		}
	else{
		document.getElementById("beneficiaryNameError").innerHTML="";
	}

	if(beneficiaryMobile=="" || beneficiaryMobile==null){
			document.getElementById("beneficiaryMobileError").innerHTML="Please Enter Beneficiary Mobile Number";
			return false;
		}
	else{
		document.getElementById("beneficiaryMobileError").innerHTML="";
	}

	if(amount=="" || amount==null){
			document.getElementById("amountError").innerHTML="Please Enter Amount";
			return false;
		}
	else{
		document.getElementById("amountError").innerHTML="";
	}

	if(startDate=="" || startDate==null){
			document.getElementById("startDateError").innerHTML="Please Select Voucher Start Date";
			return false;
		}
		else{
			document.getElementById("startDateError").innerHTML="";
		}

	if(validity=="" || validity==null){
			document.getElementById("expiryDateError").innerHTML="Please Enter Voucher Validity";
			return false;
		}
		else{
			document.getElementById("expiryDateError").innerHTML="";
		}
		$("#selectvouchers-wrap04").show();
		$("#selectvouchers-wrap03").hide();
		
}


function  issueVoucher(){
	
	document.getElementById("password1").value="";
	document.getElementById("password2").value="";
    document.getElementById("password3").value="";
	document.getElementById("password4").value="";
	document.getElementById("password5").value="";
    document.getElementById("password6").value="";
    //document.getElementById("signinLoader").style.display="flex";
	var banklist = document.getElementById("banklist").value;
	
	var voucher = document.getElementById("voucherId").value;
	var beneficiaryName = document.getElementById("beneficiaryName").value;
	var beneficiaryMobile = document.getElementById("beneficiaryMobile").value;;
	var amount = document.getElementById("amount").value;
	var startDate = document.getElementById("startDate").value;
	var validity = document.getElementById("expiryDate").value;
	
	var voucherCode = document.getElementById("voucherCode").value;
	var voucherType = document.getElementById("voucherType").value;;
	var voucherSubType = document.getElementById("voucherSubType").value;
	var voucherDesc = document.getElementById("voucherDesc").value;
	var  purposeCode= document.getElementById("purposeCode").value;
	var activeStatus = document.getElementById("activeStatus").value;
	var createdby = document.getElementById("employerName").value;
	var bankCode = document.getElementById("bankCode").value;
	var mcc = document.getElementById("mcc").value;
	var payerva = document.getElementById("payerva").value;
	
	 document.getElementById("voucherlbl").innerHTML= $("#selectedOptionsDropdown option:selected").text();
	 document.getElementById("vtypelbl").innerHTML = $("#voucherType option:selected").text();
	 document.getElementById("namelbl").innerHTML = $("#beneficiaryName").val();
	 document.getElementById("mobilelbl").innerHTML = $("#beneficiaryMobile").val();
	 document.getElementById("amountlbl").innerHTML = $("#amount").val();
	 document.getElementById("startdatelbl").innerHTML = $("#startDate").val();
	 document.getElementById("validitylbl").innerHTML = $("#expiryDate").val();
    var employerId = document.getElementById("employerId").value;
	var employerName = document.getElementById("employerName").value;
	
 	$.ajax({
	type: "POST",
	url:"/createSingleVoucher",
       data: {
			  
			   "name": beneficiaryName,
			   "mobile": beneficiaryMobile,
			   "amount": amount,
			   "startDate": startDate,
			   "expDate": validity,
			   "purposeCode": purposeCode,
			   "otpValidationStatus": "",
			   "consent": "",
			   "creationDate": "",
			   "createdby": createdby,
			   "accountId": "",
			   "orgId": employerId,
			   "merchanttxnid": "",
			   "bulktblId": "",
			   "redemtionType": "",
			   "mcc": mcc,
			   "voucherType": voucherType,
			   "voucherCode": voucherCode,
			   "voucherDesc": voucherDesc,
			   "activeStatus": activeStatus,
			   "bankCode":bankCode,
			   "voucherId":voucher,
			   "payerVA":payerva
			   
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
           success: function(data){
           newData = data;
           //console.log(newData);
           var data1 = jQuery.parseJSON( newData );
		   //var data2 = data1.data;
		   document.getElementById("signinLoader").style.display="none";
		   			
		   			if(data1.status==true){
						
						    document.getElementById("banklist").value="";
							
						    document.getElementById("voucherId").value=""
						    document.getElementById("beneficiaryName").value="";
							document.getElementById("beneficiaryMobile").value="";
							document.getElementById("amount").value="";
							document.getElementById("startDate").value="";
							document.getElementById("expiryDate").value="";
							
							document.getElementById("voucherCode").value="";
							document.getElementById("voucherType").value="";
							document.getElementById("voucherSubType").value="";
							document.getElementById("voucherDesc").value="";
							document.getElementById("purposeCode").value="";
							document.getElementById("activeStatus").value="";
							document.getElementById("employerName").value="";
							document.getElementById("bankCode").value="";
							document.getElementById("mcc").value="";
							document.getElementById("payerva").value="";
							
		   					document.getElementById("issuesuccmsg").innerHTML="Voucher Created Successfully.";
		   					document.getElementById("issuemsgdiv").style.display="block";
		   					 //document.getElementById("getInTouchUser").reset();
		   					 $('#otmsgdiv').delay(10000).fadeOut(800);
							 window.location.href = "/createUpiVoucherIssuance";
							document.getElementById('submitButton').disabled=false;
							document.getElementById('authenticate').disabled=false;
							
		   				}else if(data1.status==false){
							document.getElementById('submitButton').disabled=false;
							document.getElementById('authenticate').disabled=false;									
		   					 document.getElementById("issuesfailmsg").innerHTML=data1.message;
		   					 document.getElementById("issuesfailmsgDiv").style.display="block";
		   					 $('#otfailmsgDiv').delay(5000).fadeOut(400);
		   				}else{
							document.getElementById('submitButton').disabled=false;
							document.getElementById('authenticate').disabled=false;						
		   					 document.getElementById("issuesfailmsg").innerHTML="API Gateway not respond. Please try again.";
		   					 document.getElementById("issuesfailmsgDiv").style.display="block";
		   					 $('#otfailmsgDiv').delay(5000).fadeOut(400);
		   				}
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
		
}


function updateDropdown() {
		  
		  const dropdown = document.getElementById('selectedOptionsDropdown');
		  dropdown.innerHTML = '<option value="">--Select an option--</option>';

		  const checkboxes = document.querySelectorAll('.vouchers-checkbox:checked');

		  const errorMessage = document.getElementById('selectVoucherError');
		  			  
		  			  const meal = document.getElementById('meal');
		  			  const fuel = document.getElementById('fuel');
		  			  const travel = document.getElementById('Travel');
		  			  const conveyance = document.getElementById('Conveyance');
		  			  const uniform = document.getElementById('uniform');
		  			  const gadgets = document.getElementById('Gadgets');
		  			  const health = document.getElementById('Health');
		  			  const telecom = document.getElementById('Telecom');
		  			  const entertainment = document.getElementById('Entertainment');
		  			  const groceries = document.getElementById('Groceries');
		  			  const books = document.getElementById('books');
		  		 
		  		   		 if (!meal.checked && !fuel.checked
		  				 && !travel.checked && !conveyance.checked &&  !uniform.checked
		  				 &&  !gadgets.checked &&  !health.checked &&  !telecom.checked
		  				  &&  !entertainment.checked &&  !groceries.checked &&  !books.checked) {
		  		   		       errorMessage.textContent = "Please select at least one voucher."; 
		  					   return false;
		  		   		  }
		  			   else {
						
						       checkboxes.forEach(checkbox => {
								    const option = document.createElement('option');
								    option.value = checkbox.value;
								    option.textContent = checkbox.value;
								    dropdown.appendChild(option);
								  });
							
			  		   		    errorMessage.textContent = ""; 
		  					$("#selectvouchers-wrap02").show();
		  					$("#selectvouchers-wrap01").hide();
		  		   		  }
						
		}


function getVoucherSummaryList(){
			var employerid = document.getElementById("employerId").value;
			$.ajax({
				type: "POST",
				url: "/getVoucherSummaryList",
				data: {
					"orgId": employerid,
					"workflowid":100003
				},
				success: function(data) {
					newData = data;
					var data1 = jQuery.parseJSON(newData);
					var data2 = data1.data;
					 //console.log(data2);
					 const container = document.getElementById('jsonData');	
					        data1.data.forEach(voucher => {
					            const voucherDiv = document.createElement('div');
					            voucherDiv.classList.add('selectvouchers-carosel-cards');
					            voucherDiv.innerHTML = `
					                 <h5><img src="img/fuel-1.svg" alt=""><span></span> ${voucher.voucherName}</h5>
					                <div class="d-flex justify-content-between my-1 mb-3"><span class="info"></span> ${voucher.count}</div>
					                <div class="d-flex justify-content-between my-1"><span class="info"></span> ${voucher.totalAmount}</div>
					            `;
					            container.appendChild(voucherDiv);
					        });
					},
					error: function(e) {
						alert('Failed to fetch JSON data' + e);
				}
			});
		}


function getPrimaryBankDetail(){
			var employerid = document.getElementById("employerId").value;
			$.ajax({
				type: "POST",
				url: "/getPrimaryBankDetailsByOrgId",
				data: {
					"orgId": employerid,
					"workflowid":100003
				},
				success: function(data) {
					newData = data;
					var data1 = jQuery.parseJSON(newData);
					var data2 = data1.data;
					 //console.log(data2);
					 const container = document.getElementById('jsonDataBank');
					 const account = data1.data;
					     
					      const dataSection = document.createElement('div');
					      dataSection.classList.add('bnkdetils');
						  
						  const accountSection = document.createElement('div');
							  let logoHTML = '';

						          if (account.bankLogo) {
						              logoHTML = `<img src="data:image/png;base64,${account.bankLogo}" alt="${account.bankName}" class="logo" width="30">`;
						          }
								  accountSection.innerHTML = `
								  ${logoHTML}  
								    `;
							 container.appendChild(accountSection);
					      
							 dataSection.innerHTML = `
							 
					          <p><span ></span> ${account.bankName}</p>
					          <p><span></span> ${account.accountHolderName}</p>
					          <p><span class="mb-0"></span> <strong>${account.acNumber}</strong> </p>
					          <p><span class="info"></span> ${account.accountType}</p>
					 
					      `;
					      container.appendChild(dataSection);
					},
					error: function(e) {
						alert('Failed to fetch JSON data' + e);
				}
			});
		}
