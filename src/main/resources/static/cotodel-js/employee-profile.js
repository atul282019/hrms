
function getOTP() {
	var regMobile = /^[6-9]\d{9}$/gi;
	var userName = document.getElementById("mobilecode").value;
	if (userName == "") {
		document.getElementById("mobilecode").focus();
		document.getElementById("mobilecodeError").innerHTML="Please Enter Mobile Number";
		return false;
	}
	else if(userName.length < 10){
			document.getElementById("mobilecodeError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mobilecode").focus();
			return false;
	}
	
	else if(!userName.match(regMobile)){
			document.getElementById("mobilecodeError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mobilecode").focus();
			return false;
	}
	document.getElementById("optBtn").disabled = true;
	document.getElementById("mobilecodeError").innerHTML="";
	
	document.getElementById("loginLoader").style.display = "flex";
	$.ajax({
		type: "POST",
		url: "/smsOtpSender",
		dataType: 'json',
		data: {
			"mobile": userName,
		},
		success: function(data) {
			var obj = data;
			document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == true) {
				$('#errorOtp').hide('slow');
				$('#loginIdDiv').hide('slow');
				var timeleft = "60";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					document.getElementById("empOnboarding").removeAttribute("disabled");
					document.getElementById("optBtn").style="display:none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					
					if (timeleft <= 0) {
						clearInterval(downloadTimer);
						document.getElementById("optBtn").style="display:none";
						document.getElementById("countdown").innerHTML = " ";
						document.getElementById("optBtn").style.display = "none";
						//document.getElementById("verifyotpdiv").style.display = "none";
						$('#loginIdDiv').hide('slow');
					}
					//document.getElementById('password1').focus();
				}, 1000);
				$('#loginIdDiv').show('slow');
			}else if (obj['status'] == false) {

				$('#errorOtp').html(obj['msg']);
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				document.getElementById("optBtn").disabled = false;
				$('#successerror').hide('slow');
			} else {
				$('#errorOtp').html("OTP Failed");
				document.getElementById("optBtn").disabled = false;
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				$('#successerror').hide('slow');
			}
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

function resendOTP() {
	var regMobile = /^[6-9]\d{9}$/gi;
	var userName = document.getElementById("mobilecode").value;
	var orderId = document.getElementById("orderId").value;
	
	if (userName == "") {
		
		document.getElementById("mobilecodeError").innerHTML="Please Enter Mobile Number";
		document.getElementById("mobilecode").focus();
		return false;
	}
	else if(userName.length < 10){
			document.getElementById("mobilecodeError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mobilecode").focus();
			return false;
	}
	
	else if(!userName.match(regMobile)){
			document.getElementById("mobilecodeError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mobilecode").focus();
			return false;
	}
	document.getElementById("optBtn").disabled = true;
	document.getElementById("mobilecodeError").innerHTML="";
	
	document.getElementById("loginLoader").style.display = "flex";
	$.ajax({
		type: "POST",
		url: "/smsOtpResender",
		dataType: 'json',
		data: {
			"mobile": userName,
			"orderId":orderId
		},
		success: function(data) {
			var obj = data;
			document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == true) {
				$('#errorOtp').hide('slow');
				$('#loginIdDiv').hide('slow');
				var timeleft = "60";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					//document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft <= 0) {
						clearInterval(downloadTimer);
						document.getElementById("optBtn").disabled = false;
						document.getElementById("countdown").innerHTML = " ";
						document.getElementById("optBtn").style.display = "none";
						//document.getElementById("verifyotpdiv").style.display = "none";
						
						$('#loginIdDiv').hide('slow');
					}
					//document.getElementById('password').focus();
				}, 1000);
				$('#loginIdDiv').show('slow');
			}else if (obj['status'] == false) {

				$('#errorOtp').html(obj['msg']);
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				document.getElementById("optBtn").disabled = false;
				$('#successerror').hide('slow');
			} else {
				$('#errorOtp').html("OTP Failed");
				document.getElementById("optBtn").disabled = false;
				$('#successmessage').hide('slow');
				document.getElementById("optBtn").style.display = "block";
				$('#successerror').hide('slow');
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
            validatePan();
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


  function focusNextOtp(currentInput) {
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

function focusBackOtp(){
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


function validatePan(){
	var pan1 = document.getElementById("pan1").value;
	var pan2 = document.getElementById("pan2").value;
	var pan3 = document.getElementById("pan3").value;
	var pan4 = document.getElementById("pan4").value;
	var pan5 = document.getElementById("pan5").value;
	var pan6 = document.getElementById("pan6").value;
	var pan7 = document.getElementById("pan7").value;
	var pan8 = document.getElementById("pan8").value;
	var pan9 = document.getElementById("pan9").value;
	var pan10 = document.getElementById("pan10").value;
	var fulpancard  =(pan1+pan2+pan3+pan4+pan5+pan6+pan7+pan8+pan9+pan10).toUpperCase();
	
	var panNoFormat = new RegExp('[A-Z]{5}[0-9]{4}[A-Z]{1}');
	
	 if (panNoFormat.test(fulpancard)) {    
         
          document.getElementById("panError").innerHTML="";
          document.getElementById("inputpan").value=fulpancard;
       
               
    } else {    
        document.getElementById("panError").innerHTML="Please Enter Valid Pan Number";
		//document.getElementById("pan").focus();	
		return false;     
    }  
}


function saveEmployeeProfile(){
	
	var name = document.getElementById("name").value;
	var email = document.getElementById("email").value;
	var mobile = document.getElementById("mobilecode").value;
	var proofOfIdentity = document.getElementById("proofOfIdentity").value;
	var id = document.getElementById("IdfromApi").value;
	
	
	var regName = /^[a-zA-Z\s]*$/;
	var onlySpace = /^$|.*\S+.*/;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	
	if(name==""){
		document.getElementById("nameError").innerHTML="Please Enter Name";
		document.getElementById("name").focus();
		return false;
	}else{
		document.getElementById("nameError").innerHTML="";
	}
	
	if(email==""){
		document.getElementById("emailError").innerHTML="Please Enter email";
		document.getElementById("email").focus();
		return false;
	}else{
		document.getElementById("emailError").innerHTML="";
	}
	
	if (!email.match(regEmail)) {    
        document.getElementById("emailError").innerHTML="Please Enter Valid Email";
        document.getElementById("email").focus();
        return false;  
        
    } else {    
       document.getElementById("emailError").innerHTML="";
		   
    } 
    
	if(mobile==""){
		document.getElementById("mobilecodeError").innerHTML="Please Enter valid mobile Number";
		document.getElementById("mobilecode").focus();
		return false;
	}
  	else if (!mobile.match(regMobile)) {    
        document.getElementById("mobilecodeError").innerHTML="Please Enter Valid Mobile Number";
        document.getElementById("mobilecode").focus();
        return false;     
    } else {    
       document.getElementById("mobilecodeError").innerHTML="";
		  
    }         
   
	var formData = new FormData();
	
	formData.append("id",id);
	formData.append("name",name);
	
	formData.append("email",email);
	formData.append("mobile",mobile);
	formData.append("proofOfIdentity",proofOfIdentity);
	
	document.getElementById("signinLoader").style.display="flex";
	document.getElementById("empOnboarding").disabled=true;
	 	$.ajax({
		type: "POST",
	     url:"/saveEmployeeProfile",
         data: formData,
         processData: false,
         contentType: false,       		 
           success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 document.getElementById("successmsg").innerHTML="Data Saved Successfully.";
				 document.getElementById("successmsgdiv").style.display="block";
				 //document.getElementById("tab2").addClass("active");
				 $("#tab2").addClass("active");
				 document.getElementById("empid").value=data1.data.id;
				 //document.getElementById("employeeOnboarding").reset();
			     $("#employee-onboarding-two").show();
            	 $("#employee-onboarding-one").hide();
				// document.getElementById("empOnboarding").disabled=false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}
			//else if(data1.status==false && data1.)
				else if(data1.status==false){
				 document.getElementById("failmsg").innerHTML=data1.message;
				 document.getElementById("failmsgDiv").style.display="block";
				 //document.getElementById("empOnboarding").disabled=false;
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("failmsg").innerHTML="API Gateway not respond. Please try again.";
				 document.getElementById("failmsgDiv").style.display="block";
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
}





function saveEmployeeProfileTab2(){
	
	var id = document.getElementById("IdfromApi").value;
	
	var pan = document.getElementById("inputpan").value;
	var accountNo = document.getElementById("accountNo").value;
	var confirmAccountNo = document.getElementById("confirmAccountNo").value;
	var bankIfsc = document.getElementById("bankIfsc").value;
	
	var beneficiaryName = document.getElementById("beneficiaryName").value;
	
	var regName = /^[a-zA-Z\s]*$/;
	var onlySpace = /^$|.*\S+.*/;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	var ifscRegex = /^[A-Za-z]{4}\d{7}$/ ;
	var ifscRegex1 = /^[A-Za-z]{4}/ ;
	
	if(pan==""){
		document.getElementById("panError").innerHTML="Please Enter Pan No.";
		document.getElementById("pan1").focus();
		return false;
	}else{
		document.getElementById("panError").innerHTML="";
	}
	
	if(accountNo==""){
		document.getElementById("accountNoError").innerHTML="Please Enter Bank Account Number";
		document.getElementById("accountNo").focus();
		return false;
	}else{
		document.getElementById("accountNoError").innerHTML="";
	}
	
	
	if(confirmAccountNo==""){
		document.getElementById("confirmAccountNoError").innerHTML="Please Enter Confirm Account Number";
		document.getElementById("confirmAccountNoError").focus();
		return false;
	}else{
		document.getElementById("confirmAccountNoError").innerHTML="";
	}
	
	if (bankIfsc=="") {    
        document.getElementById("bankIfscError").innerHTML="Please Enter Valid IFSC Code";
        document.getElementById("bankIfsc").focus();
        return false;  
        
    } 
	else if(!bankIfsc.match(ifscRegex1)){
			document.getElementById("bankIfscError").innerHTML="First four alphabets are expected in IFSC";
			document.getElementById("bankIfsc").focus();
			return false;
		}
		/*else if(!bankIfsc.match(ifscRegex)){
				document.getElementById("bankIfscError").innerHTML="Special symbol not allowed in IFSC";
				document.getElementById("bankIfsc").focus();
				return false;
			}*/else {    
       document.getElementById("bankIfscError").innerHTML="";
		   
    }
	 
		 if(!beneficiaryName.match(regName)){
					document.getElementById("beneficiaryNameError").innerHTML="Special symbol not allowed in beneficiaryName";
					document.getElementById("beneficiaryName").focus();
					return false;
				}else {    
	       document.getElementById("beneficiaryNameError").innerHTML="";
			   
	    }
   
	var formData = new FormData();
	
	formData.append("id",id);
	formData.append("pan",pan);
	
	formData.append("bankAccountNumber",confirmAccountNo);
	formData.append("ifscCode",bankIfsc);
	formData.append("beneficiaryName",beneficiaryName);
	formData.append("confirmAccountNo",confirmAccountNo);
	
	document.getElementById("signinLoader").style.display="flex";
	document.getElementById("empOnboarding").disabled=true;
	 	$.ajax({
		type: "POST",
	     url:"/saveEmployeeProfile",
         data: formData,
         processData: false,
         contentType: false,       		 
           success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 document.getElementById("successmsgtab2").innerHTML="Data Saved Successfully.";
				 document.getElementById("successmsgdivtab2").style.display="block";
				 document.getElementById("empid").value=data1.data.id;
				 //document.getElementById("employeeOnboarding").reset();
			    /* $("#employee-onboarding-two").show();
            	 $("#employee-onboarding-one").hide();*/
				// document.getElementById("empOnboarding").disabled=false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
				 clearFields();
			}else if(data1.status==false){
				 document.getElementById("failmsgtab2").innerHTML=data1.message;
				 document.getElementById("failmsgDivtab2").style.display="block";
				 //document.getElementById("empOnboarding").disabled=false;
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("failmsgtab2").innerHTML="API Gateway not respond. Please try again.";
				 document.getElementById("failmsgDivtab2").style.display="block";
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
}
function autoFillEmployeeForm() {
			    const employeeId = document.getElementById("employeeId").value;
				

			    $.ajax({
			        type: "GET",
			        url: "/getEmployeeOnboardingByUserDetailId",
			        data: { "userDetailsId": employeeId },
			        success: function(response) {
			            var data1 = jQuery.parseJSON(response);
						console.log("data1",data1);

			            if (data1.status && data1.data) {
			                var data = data1.data;

			                // Fill form fields
							
							$("#IdfromApi").val(data.id);
							$("#name").val(data.name || "").prop("readonly", true);
							$("#mobilecode").val(data.mobile || "").prop("readonly", true);
							$("#email").val(data.email || "").prop("readonly", true);
							$("#proofOfIdentity").val(data.proofOfIdentity || "Proof of Identity");
			               $("#accountNo").val(data.bankAccountNumber || "");
			               $("#confirmAccountNo").val(data.bankAccountNumber || "");
			               $("#bankIfsc").val(data.ifscCode || "");
			               $("#beneficiaryName").val(data.beneficiaryName || "");

			               // Autofill PAN Card fields character by character
			               if (data.pan) {
			                   for (let i = 0; i < data.pan.length; i++) {
			                       $(`#pan${i + 1}`).val(data.pan[i]);
			                   }
			               }      
			            } else {
			                console.log("No data found for the given Employee ID.");
			            }
			        },
			        error: function(error) {
			            console.log("Error fetching data: " + error.responseText);
			        }
			    });
			}
			function clearFields() {
			    // Clearing input fields but not error messages
			    $(" #accountNo, #confirmAccountNo, #bankIfsc, #beneficiaryName").val("");
			    
			    // Clearing PAN input fields
			    $(".otp-box-onboarding input").val("");

			    // Hiding success message after a delay
			    setTimeout(function () {
			        $("#successmsgdivtab2").fadeOut();
			    }, 3000);
			}
			
			function verifyOTP() {
				//document.getElementById("authenticate").disabled = true;
			  	var password1 = document.getElementById("password1").value;
			  	var password2 = document.getElementById("password2").value;
			  	var password3 = document.getElementById("password3").value;
			  	var password4 = document.getElementById("password4").value;
			  	var password5 = document.getElementById("password5").value;
			  	var password6 = document.getElementById("password6").value;
			  	var orderId = document.getElementById("orderId").value;
			  	var employerMobile = document.getElementById("mobilecode").value;
			  	
			  	if (document.getElementById("mobilecode").value == "") {
			  		document.getElementById("mobError").innerHTML="Please Enter mobile..";
			  		
			  		x = false;
			  	} else if (password1 == "" && password1.length < 1) {
			  		document.getElementById("mobilecodeError").innerHTML="";
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
			  		document.getElementById("mobilecodeError").innerHTML="";
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
			  		document.getElementById("mobilecodeError").innerHTML="";
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
			  		document.getElementById("mobilecodeError").innerHTML="";
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
			  		document.getElementById("mobilecodeError").innerHTML="";
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
								saveEmployeeProfile();
								
			  				}else if (obj['status'] == false) {
								//document.getElementById("otpError").textContent=obj['message'];
								document.getElementById("otpError").textContent="Invalid otp";
								document.getElementById("otpError").style.display="block";
								
							} else {
			  				
			  				}
			  			},
			  			error: function(e) {
			  				alert('Error: ' + e);
			  			}
			  		});
			  }