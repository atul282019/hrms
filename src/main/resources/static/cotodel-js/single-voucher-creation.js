function singleVoucherValidation(){
				
            	var banklist = document.getElementById("banklist").value; 
				var voucher = document.getElementById("btnforvccategoryforbulkissuance").value;
				var parts = voucher.split("|");
				var voucherName = parts[0]; // "4C"
				//var PurposeCode = parts[1]; // "5111"
			   	var beneficiaryName = document.getElementById("search").value;
			   	var beneficiaryMobile = document.getElementById("mobile").value;
				var redemptionType = document.getElementById("redemptionType").value;
			   	var amount = document.getElementById("amount").value;
			   	var startDate = document.getElementById("startDate").value;
			   	var validity = document.getElementById("expiryDate").value;
			   	
			   	var voucherCode = document.getElementById("voucherCode").value;
			   	var voucherType = document.getElementById("voucherType").value;;
			   	var voucherSubType = document.getElementById("voucherSubType").value;
			   	var voucherDesc = document.getElementById("voucherDesc").value;
			   	var purposeCode= document.getElementById("purposeCode").value;
			   	var activeStatus = document.getElementById("activeStatus").value;
			   	var createdby = document.getElementById("employerName").value;
			   	var bankCode = document.getElementById("bankCode").value;
			   	var mcc = parts[1]; 
			   	var payerva = document.getElementById("payerva").value;
			   	
			   	 document.getElementById("voucherlbl").innerHTML=voucherName;
			   	 document.getElementById("redemptionLbl").innerHTML = document.getElementById("redemptionType").value; 
			   	 //document.getElementById("redemptionLbl").innerHTML = PurposeCode;
			   	 document.getElementById("namelbl").innerHTML = beneficiaryName;
			   	 document.getElementById("mobilelbl").innerHTML = beneficiaryMobile;
			   	 document.getElementById("amountlbl").innerHTML = $("#amount").val();
			   	 document.getElementById("startdatelbl").innerHTML = $("#startDate").val();
			   	 document.getElementById("validitylbl").innerHTML = $("#expiryDate").val();
			     var employerId = document.getElementById("employerId").value;
			     var employerName = document.getElementById("employerName").value;
			   	const amountValue = amount.trim();
			   			  
			   	if(banklist=="" || banklist==null){
			   				alert("Please select bank");
			   				return false;
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
				if(voucher=="" || voucher==null){
			   			document.getElementById("voucherError").innerHTML="Please select voucher";
			   			return false;
			   		}
			   	else{
			   		document.getElementById("voucherError").innerHTML="";
			   	}
				if(redemptionType=="" || redemptionType==null){
				   			document.getElementById("redemptionTypeError").innerHTML="Please select redemption type";
				   			return false;
				   		}
				   	else{
				   		document.getElementById("redemptionTypeError").innerHTML="";
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
			   			document.getElementById("expiryDateError").innerHTML="Please select validity";
			   			return false;
			   		}
			   		else{
			   			document.getElementById("expiryDateError").innerHTML="";
			   		}
				   var element = document.getElementById("lable2");
				   							   	element.classList.add("active");
				   var element = document.getElementById("lable3");
				   	   	element.classList.add("active");
				   	   	
					$("#selectvouchers-wrap04").show();
					$("#selectvouchers-wrap03").hide();
					
	}
			