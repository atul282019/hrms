
function resendVoucherOTP() {
	console.log("inside resendVoucherOTP()");
	document.getElementById("authenticate").disabled = false;
	var userName = document.getElementById("banklinkedMobile").value;
	var orderId = document.getElementById("orderId").value;
	var employerMobile = document.getElementById("employerMobile").value;
	
	$.ajax({
		type: "POST",
		url:"/smsOtpResender",
		dataType: 'json',
		data: {
			"mobile": userName,
			"orderId":orderId
		},
		success: function(data) {
			var obj = data;
			//document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == true) {
				// Mask the mobile number (show only last 4 digits)
				var maskedMobile = "XXXXXX" + employerMobile.toString().slice(-4);
				document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate issuance.`;				
					
				var timeleft = "60";
				var resendCodeElement = document.getElementById("resendCode");
	               // Hide the "Resend OTP" link initially
	               resendCodeElement.style.display = "none";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					//document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft < 0) {
						clearInterval(downloadTimer);
						//document.getElementById("optBtn").disabled = false;
						document.getElementById("countdown").innerHTML = " ";
						resendCodeElement.style.display = "block";
					}
				}, 1000);
				//$('#loginIdDiv').show('slow');
			}else if (obj['status'] == false) {
				alert(obj.message);
			
			} else {
				
			}
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}


function verfyIssueVoucherOTP() {
	
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
	document.getElementById("authenticate").disabled = true;
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

  				if (obj['status']== true) {
					
					$('#otpModal1').fadeOut();
					issueVoucher();
  					$('#errorOtp').hide('slow');
  				}else if (obj['status'] == false) {
					document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
					document.getElementById("authenticate").disabled = false;
					
					document.getElementById("password1").value="";
					document.getElementById("password2").value="";
					document.getElementById("password3").value="";
					document.getElementById("password4").value="";
					document.getElementById("password5").value="";
					document.getElementById("password6").value="";
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




/* old workingfunction 
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
			            newData = data;
			            console.log(newData);
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
			
}*/
function getLinkedBankDetail() {
  const employerid = document.getElementById("employerId").value;

  $.ajax({
    type: "POST",
    url: "/getErupiLinkBankAccountDetail",
    data: { orgId: employerid },
    success: function (data) {
      const select = document.getElementById("banklist");
      select.innerHTML = "";

      const parsed = JSON.parse(data).data;
      console.log("getLinkedBankDetail()=", parsed);

      const dropdownList = document.getElementById("dropdownList");
      dropdownList.innerHTML = "";

      const bankKeys = Object.keys(parsed);

      if (bankKeys.length === 1) {
        const onlyBank = parsed[bankKeys[0]];
        const masked = "XXXX" + onlyBank.acNumber.slice(-4);

        const option = document.createElement("option");
        option.value = onlyBank.acNumber;
        option.text = onlyBank.accountSeltWallet === "Wallet"
          ? "cotowallet"
          : `${onlyBank.bankName} | ${masked}`;
        select.appendChild(option);

        const selectedHTML = onlyBank.accountSeltWallet === "Wallet"
          ? `<div class="dropdown-cotowallet" style="font-family: 'Instrument Sans', sans-serif;">
                <span style="font-weight: 500; color: #4A4E69;">coto</span>
                <span style="font-weight: 700; color: #2F945A;">wallet</span>
             </div>
             <span class="dropdown-mask">${masked}</span>
             <i class="bi bi-caret-down-fill dropdown-arrow"></i>`
          : `<div class="dropdown-item">
                <div class="dropdown-bank-info">
                  <img src="data:image/png;base64,${onlyBank.bankIcon}" alt="logo" style="width: 24px; height: 24px;">
                  <span>${onlyBank.bankName}</span>
                </div>
                <span class="dropdown-mask">${masked}</span>
                <i class="bi bi-caret-down-fill dropdown-arrow"></i>
             </div>`;

        document.getElementById("selectedBank").innerHTML = selectedHTML;
        select.value = onlyBank.acNumber;
        getBankDetailByBankAccountNumber();
        showLinkedAccAmount(onlyBank.acNumber, onlyBank.accountSeltWallet);
      } else {
        let walletBank = null;

        bankKeys.forEach((key) => {
          const bank = parsed[key];
          const masked = "XXXX" + bank.acNumber.slice(-4);

          const option = document.createElement("option");
          option.value = bank.acNumber;
          option.text = bank.accountSeltWallet === "Wallet"
            ? "cotowallet"
            : `${bank.bankName} | ${masked}`;
          select.appendChild(option);

          const div = document.createElement("div");
          div.className = "dropdown-item";

          if (bank.accountSeltWallet === "Wallet") {
            div.innerHTML = `
              <div class="dropdown-cotowallet" style="font-family: 'Instrument Sans', sans-serif; font-size: 21px;">
                <span style="font-weight: 500; color: #4A4E69;">coto</span>
                <span style="font-weight: 700; color: #2F945A;">wallet</span>
              </div>
              <span class="dropdown-mask">${masked}</span>
            `;
          } else {
            div.innerHTML = `
              <div class="dropdown-bank-info">
                <img src="data:image/png;base64,${bank.bankIcon}" alt="logo" style="width: 24px; height: 24px;">
                <span>${bank.bankName}</span>
              </div>
              <span class="dropdown-mask">${masked}</span>
            `;
          }

          div.onclick = function () {
            const selectedHTML = div.innerHTML + '<i class="bi bi-caret-down-fill dropdown-arrow"></i>';
            document.getElementById("selectedBank").innerHTML = selectedHTML;
            select.value = bank.acNumber;
            document.getElementById("dropdownList").style.display = "none";
            getBankDetailByBankAccountNumber();
            showLinkedAccAmount(bank.acNumber, bank.accountSeltWallet);
          };

          dropdownList.appendChild(div);

          if (bank.accountSeltWallet === "Wallet" && walletBank === null) {
            walletBank = { bank, masked, div };
          }
        });

        if (walletBank) {
          const selectedHTML = walletBank.div.innerHTML + '<i class="bi bi-caret-down-fill dropdown-arrow"></i>';
          document.getElementById("selectedBank").innerHTML = selectedHTML;
          select.value = walletBank.bank.acNumber;
          getBankDetailByBankAccountNumber();
          showLinkedAccAmount(walletBank.bank.acNumber, walletBank.bank.accountSeltWallet);
        }
      }
    },
    error: function (e) {
      alert("Error: " + e);
    },
  });
}

document.getElementById("selectedBank").addEventListener("click", function () {
  const list = document.getElementById("dropdownList");
  list.style.display = list.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", function (e) {
  const dropdown = document.getElementById("customDropdown");
  if (!dropdown.contains(e.target)) {
    document.getElementById("dropdownList").style.display = "none";
  }
});

/*function  getVoucherDetailByBoucherCode(){
	createSingleVoucherValidation();
 	var voucherCode = document.getElementById("selectedOptionsDropdown").value;
 	$.ajax({
	type: "POST",
	url:"/getmccMasterListByPurposeCode",
       data: {
			"purposeCode": voucherCode
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
		success: function(data){
		           newData = data;
		           console.log(newData);
				$("#voucherTypeMCC option").remove();
		           var obj = jQuery.parseJSON( data );
		            obj = obj.data;
		       	 var count=0;
		        	for (var key in obj) {

		            var values =  obj[key];
		            var x = document.getElementById("voucherTypeMCC");
		            if(count==0){
		            var option = document.createElement("option");
		            option.text ="Select Voucher Type MCC";
		            option.value = "";
		            x.add(option);
		            }
		            var option = document.createElement("option");
		            option.text = values.mccDesc;
		            option.value = values.mcc;
		            x.add(option);

		            count++;
		            }   
		        },
		        error: function(e){
		            alert('Error: ' + e);
		        }
   }); 
			
}*/

function  getBankDetailByBankAccountNumber(){
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
			//document.getElementById("mcc").value=data1.data.mcc; 
			document.getElementById("submurchentid").value=data1.data.submurchentid;  
			document.getElementById("merchentid").value=data1.data.merchentIid;  
			document.getElementById("payerva").value=data1.data.payerva;  
												 
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}

function validateAmount(amount) {
           // Regex to allow positive numbers with up to 2 decimal places
           const amountRegex = /^[0-9]+(\.[0-9]{1,2})?$/;
           return amountRegex.test(amount);
  }


function  createSingleVoucherValidation(){
	
	var banklist = document.getElementById("banklist").value;
	
	var voucher = $("#selectedOptionsDropdown option:selected").val(); //document.getElementById("voucherId").value;
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
	 document.getElementById("vmcclbl").innerHTML = $("#voucherTypeMCC option:selected").text();
	 document.getElementById("namelbl").innerHTML = $("#beneficiaryName").val();
	 document.getElementById("mobilelbl").innerHTML = $("#beneficiaryMobile").val();
	 document.getElementById("amountlbl").innerHTML = $("#amount").val();
	 document.getElementById("startdatelbl").innerHTML = $("#startDate").val();
	 document.getElementById("validitylbl").innerHTML = $("#expiryDate").val();
    var employerId = document.getElementById("employerId").value;
	var employerName = document.getElementById("employerName").value;
	var voucherTypeMCC = $("#voucherTypeMCC option:selected").val();
	
	const amountValue = amount.trim();
			  
	if(banklist=="" || banklist==null){
				document.getElementById("banklistError").innerHTML="Please select bank";
				return false;
			}
		else{
			document.getElementById("banklistError").innerHTML="";
		}
			
	if(voucher=="" || voucher== null){
				document.getElementById("selectedOptionsDropdownError").innerHTML="Please select voucher";
				return false;
			}
		else{
			document.getElementById("selectedOptionsDropdownError").innerHTML="";
		}
		if(voucherTypeMCC=="" || voucherTypeMCC== null){
				document.getElementById("voucherTypeMCCError").innerHTML="Please select voucher MCC";
				return false;
			}
		else{
			document.getElementById("voucherTypeMCCError").innerHTML="";
		}

		if(voucherType=="" || voucherType==null){
				document.getElementById("voucherTypeError").innerHTML="Please select type";
				return false;
			}
		else{
			document.getElementById("voucherTypeError").innerHTML="";
		}

	if(beneficiaryName=="" || beneficiaryName==null){
			document.getElementById("beneficiaryNameError").innerHTML="Please enter beneficiary name";
			return false;
		}
	else{
		document.getElementById("beneficiaryNameError").innerHTML="";
	}

	if(beneficiaryMobile=="" || beneficiaryMobile==null){
			document.getElementById("beneficiaryMobileError").innerHTML="Please enter beneficiary mobile number";
			return false;
		}
	else{
		document.getElementById("beneficiaryMobileError").innerHTML="";
	}

	if(amount=="" || amount==null){
			document.getElementById("amountError").innerHTML="Please enter amount";
			return false;
		}
        else if (isNaN(amountValue) || !validateAmount(amountValue)) {
            amountError.textContent = "Please enter a valid amount (e.g., 10 or 10.50).";
        } 
		else if (amountValue === "0" || amountValue === "00" || amountValue === "000"
			|| amountValue === "0000" || amountValue === "00000" || amountValue === "000000"
		) {
                amount.value = ""; // Clear the input
                amountError.textContent = "Zero is not allowed.";
				return false;
            }
		else {
            amountError.textContent = "";
      }
	
	if(startDate=="" || startDate==null){
			document.getElementById("startDateError").innerHTML="Please select voucher start date";
			return false;
		}
		else{
			document.getElementById("startDateError").innerHTML="";
		}

	if(validity=="" || validity==null){
			document.getElementById("expiryDateError").innerHTML="Please select expiry date";
			return false;
		}
		else{
			document.getElementById("expiryDateError").innerHTML="";
		}
		if (validity <= startDate) {
	        document.getElementById("expiryDateError").innerHTML="Expiry date should be greater than start date";
			return false;
            }
		 else {
       	 document.getElementById("expiryDateError").innerHTML="";
        }
		$("#selectvouchers-wrap04").show();
		$("#selectvouchers-wrap03").hide();
		
}


async function  issueVoucher(){
	
	document.getElementById("password1").value="";
	document.getElementById("password2").value="";
    document.getElementById("password3").value="";
	document.getElementById("password4").value="";
	document.getElementById("password5").value="";
    document.getElementById("password6").value="";
	
	var banklist = document.getElementById("banklist").value;
	
	var voucher = document.getElementById("voucherId").value;
	var beneficiaryName = document.getElementById("search").value;
	var beneficiaryMobile = document.getElementById("mobile").value;
	var redemptionType = document.getElementById("redemptionType").value;
	var amount = document.getElementById("amount").value;
	var startDate = document.getElementById("startDate").value;
	var validity = document.getElementById("expiryDate").value;
	
	var voucherType = document.getElementById("voucherType").value;;
	var voucherCode = document.getElementById("voucherCode").value;
	var voucherDesc = document.getElementById("voucherType").value;;
	var  purposeCode= document.getElementById("purposeCode").value;
	var activeStatus = document.getElementById("activeStatus").value;
	var createdby = document.getElementById("employerName").value;
	var bankCode = document.getElementById("bankCode").value;
	var mcc = document.getElementById("mcc").value;
	var payerva = document.getElementById("payerva").value;
	var acNumber = document.getElementById("acNumber").value;
	
	var merchentid = document.getElementById("merchentid").value;
	var submurchentid = document.getElementById("submurchentid").value;
	
    var employerId = document.getElementById("employerId").value;
	var employerName = document.getElementById("employerName").value;
	var requestId = document.getElementById("requestId").value;
	
	var customCheck45 = document.getElementById("customCheck45").checked; 
	if (customCheck45) {
	    customCheck45 = "yes"; 
	} else {
	    customCheck45 = "no";  
	}
	
	document.getElementById("signinLoader").style.display="flex";
	const clientKey = "client-secret-key"; // Extra security measure
    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	const dataString = beneficiaryName+beneficiaryMobile+startDate+validity+voucherCode+customCheck45+
	createdby+employerId+merchentid+submurchentid+redemptionType+mcc+voucherCode+
	voucherDesc+bankCode+acNumber+payerva+"01"+amount+clientKey+secretKey;
	
		console.log("data string"+dataString); 
    // Generate SHA-256 hash
    const encoder = new TextEncoder();
    const data = encoder.encode(dataString);
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
							
	 	$.ajax({
		type: "POST",
		url:"/createSingleVoucherRequested",
	       data: {
				   "name": beneficiaryName,
				   "mobile": beneficiaryMobile,
				   "amount": amount,
				   "startDate": startDate,
				   "validity": validity,
				   "purposeCode": voucherCode,
				   "consent": customCheck45,
				   "createdby": createdby,
				   "orgId": employerId,
				   "merchantId": merchentid,
				   "subMerchantId": submurchentid,
				   "redemtionType": redemptionType,
				   "mcc": mcc,
				   "voucherType": voucherType,
				   "voucherCode": voucherCode,
				   "voucherDesc": voucherType,
				   "activeStatus": activeStatus,
				   "bankcode":bankCode,
				   "accountNumber":acNumber,
				   "requestId":requestId,
				   "payerVA":payerva,
				   "mandateType":"01",
				   "clientKey":clientKey,
				   "hash":hashHex
				   
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
						
						document.getElementById("voucherId").value="";
						document.getElementById("search").value="";
						document.getElementById("mobile").value="";
						document.getElementById("redemptionType").value="";
						document.getElementById("amount").value="";
						document.getElementById("startDate").value="";
						document.getElementById("expiryDate").value="";
						document.getElementById("btnforvccategoryforbulkissuance").value="";
						
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
						
						document.getElementById("selectvouchers-wrap03").style.display="none";
						document.getElementById("selectvouchers-wrap04").style.display="none";
						document.getElementById("upi-voucher-wrapThree").style.display="block";
						
						//window.location.href = "/upiVoucherIssuanceManually";
						var element = document.getElementById("lable3");
					    element.classList.add("active");
						
						const tableBody = document.getElementById("successFailVoucherTable").getElementsByTagName("tbody")[0];

						 data1.data
						     const row = tableBody.insertRow();

						     row.insertCell().textContent = data1.data.name;
						     row.insertCell().textContent = data1.data.mobile;
						     row.insertCell().textContent = data1.data.voucherDesc;
						     row.insertCell().textContent = data1.data.redemtionType;
						     row.insertCell().textContent = data1.data.amount;
						     //row.insertCell().textContent = item.voucherCode;
							 row.insertCell().textContent = data1.data.startDate;
							 row.insertCell().textContent = data1.data.expDate;
							// row.insertCell().textContent = item.response;
							 

						     // Add the response cell with an image
						     const responseCell = row.insertCell();
						     const img = document.createElement("img");
						     if (data1.data.response === "SUCCESS") {
						       img.src = "img/status-check.svg"; 
						       img.alt = "Success";
						     } else {
						       img.src = "img/status-cross.svg"; 
						       img.alt = "Failure";
						     }
						     img.width = 20;
						     img.height = 20;
						     responseCell.appendChild(img);

						    // row.insertCell().textContent = item.voucherDesc;
						   
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
	            alert('Error: ' , e);
				console.log("Inside Error block",e);
	        }
	   }); 
			
	}
/*
function getVoucherSummaryList(){
			var employerid = document.getElementById("employerId").value;
			$.ajax({
				type: "POST",
				url: "/getVoucherSummaryList",
				data: {
					"orgId": employerid,
					//"workflowid":100003
				},
				success: function(data) {
					newData = data;
					var data1 = jQuery.parseJSON(newData);
					var data2 = data1.data;
					console.log(data2);
					 
					 const container = document.getElementById('jsonData');	
					
							data1.data.forEach(voucher => {
							    const voucherDiv = document.createElement('div');
							    voucherDiv.classList.add('selectvouchers-carosel-cards');
							    voucherDiv.innerHTML = `
							        <div>
							            <h5>
							                <img src="data:image/png;base64,${voucher.voucherIcon}" class="logo" width="20px" height="20px">
							                ${voucher.voucherName}
							            </h5>
							            <div class="d-flex justify-content-between my-1 mb-3">
							                <span class="info"></span> ${voucher.count}
							            </div>
							            <div class="d-flex justify-content-between my-1">
							                <span></span> ₹${voucher.totalAmount}
							            </div>
							        </div>
							    `;
							    container.appendChild(voucherDiv);
							});

													
																									
					},
					error: function(e) {
						alert('Failed to fetch JSON data' + e);
				}
			});
		}
*/

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
						              logoHTML = `<img src="data:image/png;base64,${account.bankLogo}" alt="${account.bankName}" class="logo" width="20px" height="20px">`;
						          }
								  accountSection.innerHTML = `
								  ${logoHTML}  
								    `;
							 container.appendChild(accountSection);
					      
							 dataSection.innerHTML = `
							 
					          <p><strong>${account.bankName}</strong></p>
					          <p>
							  <strong>${account.accountHolderName}</strong></p>
					          <p><span class="mb-0">A/C No: </span><strong>${account.acNumber}</strong> </p>
							 
					      `;
					      container.appendChild(dataSection);
					},
					error: function(e) {
						alert('Failed to fetch JSON data' + e);
				}
			});
		}
		function toggleSubmitButton() {
			       const checkbox = document.getElementById('customCheck45');
			       const submitButton = document.getElementById('submitButton');
			       
			       // Enable the button only if the checkbox is checked
			       submitButton.disabled = !checkbox.checked;
			   }

			   document.getElementById('downloadBtn').addEventListener('click', function () {
			          const table = document.getElementById('successFailVoucherTable');

			         const clonedTable = table.cloneNode(true);
			          const columnsToRemove = [8,9]; 
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
				  
				  
				  function showLinkedAccAmount(accountNumber, accountSeltWallet) {
				  			      const employerid = document.getElementById("employerId").value;
				  			      console.log("accountNumber, employerid", accountNumber, employerid);

				  			      if (accountSeltWallet === "Self") {
				  			          document.querySelector(".button-check-balance").style.display = "block";
				  			          document.querySelector(".tip-disabled").style.display = "block";

				  			          document.querySelector(".div-2").style.display = "none";
				  			          document.querySelector(".last-updated-on").style.display = "none";
				  			          return;
				  			      }

				  			      document.querySelector(".button-check-balance").style.display = "none";
				  			      document.querySelector(".tip-disabled").style.display = "none";

				  			      document.querySelector(".div-2").style.display = "block";
				  			      document.querySelector(".last-updated-on").style.display = "block";

				  			      $.ajax({
				  			          type: "POST",
				  			          url: "/showLinkedAccAmount",
				  			          data: {
				  			              "acNumber": accountNumber,
				  			              "orgId": employerid
				  			          },
				  			          success: function (data) {
				  			              console.log("showLinkedAccAmount", data);
				  			              const obj = jQuery.parseJSON(data);
				  			              if (obj.status === true) {
				  			                  const rawAmount = obj.balance;
				  			                  const formattedAmount = (!isNaN(rawAmount) && rawAmount !== null)
				  			                      ? Number(rawAmount).toLocaleString('en-IN')
				  			                      : "Amount Not Available";
				  			                  document.querySelector(".text-wrapper-3").textContent = formattedAmount;
				  			              }
				  			          },
				  			          error: function (e) {
				  			              alert('Error: ' + e);
				  			          }
				  			      });
				  			  }

				  function formatTimestamp(timestamp) {
				      let dateObj = new Date(timestamp.replace(" ", "T")); // Ensure proper parsing

				      let hours = dateObj.getHours().toString().padStart(2, '0');
				      let minutes = dateObj.getMinutes().toString().padStart(2, '0');
				      let seconds = dateObj.getSeconds().toString().padStart(2, '0');

				      let day = dateObj.getDate();
				      let month = dateObj.toLocaleString('en-US', { month: 'short' }); // "Mar"
				      let year = dateObj.getFullYear();
				      let weekday = dateObj.toLocaleString('en-US', { weekday: 'short' }); // "Fri"

				      return `${hours}:${minutes}:${seconds} ${weekday} ${day} ${month} ${year}`;
				  }
				  function validateAmount() {
				  	   				      // Get available balance (removing currency symbols & commas)
				  	   				      let availableBalance = document.querySelector(".text-wrapper-3").textContent.replace(/[₹,]/g, '').trim();
				  	   				      let amountInput = document.getElementById("amount");
				  	   				      let continueButton = document.getElementById("continueButton1"); // Target the specific button
				  	   				      let errorMessage = document.getElementById("amountError");

				  	   				      // Convert values to float (handle decimal values correctly)
				  	   				      let balance = parseFloat(availableBalance);
				  	   				      let amount = parseFloat(amountInput.value.trim());

				  	   				      // Disable button by default
				  	   				      continueButton.disabled = true;

				  	   				      // Ensure valid numbers before comparison
				  	   				     

				  	   				      // Validation: Ensure entered amount is <= available balance
				  	   				      if (amount > balance || !balance) {
				  	   				          errorMessage.textContent = "Maximum amount allowed is ₹" + balance;
				  	   				          errorMessage.style.display = "inline"; // Show error message
				  	   				          amountInput.value = balance; // Auto-trim input value
				  	   				      } else {
				  	   				          errorMessage.style.display = "none"; // Hide error message
				  	   				          continueButton.disabled = false; // Enable button when valid
				  	   				      }
				  	   				  }
									  
									  function validateAmount1() {
									    const requested = parseFloat(document.getElementById("requestedamount").value);
									    const amountInput = document.getElementById("amount");
									    const entered = parseFloat(amountInput.value);
									    const errorDiv = document.getElementById("amountError");

									    // Extract available balance from text, remove ₹, commas
									    const balanceText = document.querySelector(".text-wrapper-3").textContent.replace(/[^0-9.]/g, "");
									    const availableBalance = parseFloat(balanceText);

									    if (!isNaN(requested) && !isNaN(entered) && !isNaN(availableBalance)) {
									      if (entered > requested) {
									        errorDiv.textContent = "Entered amount cannot exceed the requested amount.";
									        amountInput.value = requested;
									      } else if (entered > availableBalance) {
									        errorDiv.textContent = "Entered amount cannot exceed available balance.";
									        amountInput.value = availableBalance;
									      } else {
									        errorDiv.textContent = "";
									      }
									    } else {
									      errorDiv.textContent = "";
									    }
									  }


 


