
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
		url: "" + $('#ctx').attr('content') + "/smsOtpSender",
		dataType: 'json',
		data: {
			"mob": userName,
		},
		success: function(data) {
			var obj = data;
			document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == "SUCCESS") {
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
			}else if (obj['status'] == "FAILURE") {

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
	var userName = document.getElementById("mob").value;
	var orderId = document.getElementById("orderId").value;
	
	if (userName == "") {
		document.getElementById("mob").focus();
		document.getElementById("mobError").innerHTML="Please Enter Mobile Number";
		return false;
	}
	else if(userName.length < 10){
			document.getElementById("mobError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mob").focus();
			return false;
	}
	
	else if(!userName.match(regMobile)){
			document.getElementById("mobError").innerHTML="Please Enter Valid Mobile Number";
			document.getElementById("mob").focus();
			return false;
	}
	document.getElementById("optBtn").disabled = true;
	document.getElementById("mobError").innerHTML="";
	
	document.getElementById("loginLoader").style.display = "flex";
	$.ajax({
		type: "POST",
		url: "" + $('#ctx').attr('content') + "/smsOtpResender",
		dataType: 'json',
		data: {
			"mob": userName,
			"orderId":orderId
		},
		success: function(data) {
			var obj = data;
			document.getElementById("loginLoader").style.display = "none";
			if (obj['status'] == "SUCCESS") {
				$('#errorOtp').hide('slow');
				$('#loginIdDiv').hide('slow');
				var timeleft = "60";
				var downloadTimer = setInterval(function() {
					document.getElementById("countdown").innerHTML = "00."+timeleft;
					timeleft -= 1;
					document.getElementById("optBtn").style.display = "none";
					document.getElementById("orderId").value= obj['orderId'];
					document.getElementById("verifyotpdiv").style.display = "block";
					if (timeleft <= 0) {
						clearInterval(downloadTimer);
						document.getElementById("optBtn").disabled = false;
						document.getElementById("countdown").innerHTML = " ";
						document.getElementById("optBtn").style.display = "none";
						document.getElementById("verifyotpdiv").style.display = "none";
						
						$('#loginIdDiv').hide('slow');
					}
					document.getElementById('password').focus();
				}, 1000);
				$('#loginIdDiv').show('slow');
			}else if (obj['status'] == "FAILURE") {

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
	//var employerId=document.getElementById("employerId").value; 
	//var employeeId=document.getElementById("employeeId").value;
	//var empOrCont = document.getElementById("contractor").value;
	//var empOrCont = document.getElementById("employee").value;
	
	var name = document.getElementById("name").value;
	var email = document.getElementById("email").value;
	var mobile = document.getElementById("mobilecode").value;
	var proofOfIdentity = document.getElementById("proofOfIdentity").value;
	
	/*var herDate = document.getElementById("hireDate").value;
	var jobTitle = document.getElementById("jobTitle").value;
	var depratment = document.getElementById("department").value;
	var managerName = document.getElementById("reporting").value;
	var ctc = document.getElementById("salary").value;
	var location = document.getElementById("location").value;
	var residentOfIndia = document.getElementById("residence").value;*/
	
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
	
	//formData.append("employerId",employerId);
	//formData.append("employeeId",employeeId);
	
	//formData.append("empOrCont",empOrCont);
	formData.append("name",name);
	
	formData.append("email",email);
	formData.append("mobile",mobile);
	formData.append("proofOfIdentity",proofOfIdentity);
	//formData.append("herDate",herDate);
	
	//formData.append("jobTitle",jobTitle);
	//formData.append("depratment",depratment);
	
	//formData.append("managerName",managerName);
	//formData.append("ctc",ctc);
	
	//formData.append("location",location);
	//formData.append("residentOfIndia",residentOfIndia);
	
	document.getElementById("signinLoader").style.display="flex";
	document.getElementById("empOnboarding").disabled=true;
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveEmployeeProfile",
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
			}else if(data1.status==false){
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
	//var employerId=document.getElementById("employerId").value; 
	//var employeeId=document.getElementById("employeeId").value;
	//var empOrCont = document.getElementById("contractor").value;
	var id = document.getElementById("empid").value;
	
	var pan = document.getElementById("inputpan").value;
	var accountNo = document.getElementById("accountNo").value;
	var confirmAccountNo = document.getElementById("confirmAccountNo").value;
	var bankIfsc = document.getElementById("bankIfsc").value;
	
	var beneficiaryName = document.getElementById("beneficiaryName").value;
	/*var jobTitle = document.getElementById("jobTitle").value;
	var depratment = document.getElementById("department").value;
	var managerName = document.getElementById("reporting").value;
	var ctc = document.getElementById("salary").value;
	var location = document.getElementById("location").value;
	var residentOfIndia = document.getElementById("residence").value;*/
	
	var regName = /^[a-zA-Z\s]*$/;
	var onlySpace = /^$|.*\S+.*/;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	
	if(pan==""){
		document.getElementById("panError").innerHTML="Please Enter Name";
		document.getElementById("pan1").focus();
		return false;
	}else{
		document.getElementById("panError").innerHTML="";
	}
	
	if(accountNo==""){
		document.getElementById("accountNoError").innerHTML="Please Enter Name";
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
        
    } else {    
       document.getElementById("bankIfscError").innerHTML="";
		   
    } 
   
	var formData = new FormData();
	
	//formData.append("employerId",employerId);
	//formData.append("employeeId",employeeId);
	
	formData.append("id",id);
	formData.append("pan",pan);
	
	formData.append("bankAccountNumber",confirmAccountNo);
	formData.append("ifscCode",bankIfsc);
	formData.append("beneficiaryName",beneficiaryName);
	formData.append("confirmAccountNo",confirmAccountNo);
	
	//formData.append("jobTitle",jobTitle);
	//formData.append("depratment",depratment);
	
	//formData.append("managerName",managerName);
	//formData.append("ctc",ctc);
	
	//formData.append("location",location);
	//formData.append("residentOfIndia",residentOfIndia);
	
	document.getElementById("signinLoader").style.display="flex";
	document.getElementById("empOnboarding").disabled=true;
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveEmployeeProfile",
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