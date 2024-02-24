function validateFormAndSubmit(){
	
	 var gstnNo = document.getElementById("gstnNo").value;
	 var organizationType = document.getElementById("organizationType").value;
	 var pan = document.getElementById("pan").value; 
	// var brnadName = document.getElementById("brnadName").value; 
	 
	// var brnadName = document.getElementById("brnadName").value; 
     var orgType2 = document.getElementById("orgType2").value; 
	 var panDetails = document.getElementById("panDetails").value; 
	 var companyName = document.getElementById("companyName").value; 
	 
	 var officeAddress = document.getElementById("officeAddress").value;
	  var addressLine = document.getElementById("addressLine").value;
	       
	 var pinCode = document.getElementById("pinCode").value; 
	 var companyPan2 = document.getElementById("companyPan2").value; 
	 var stateCode = document.getElementById("stateCode").value;  
	       

	 var regName = /^[a-zA-Z\s]*$/;
	 var onlySpace = /^$|.*\S+.*/;
	 
/*
	if(pname==""){
		document.getElementById("nameError").innerHTML="Please Enter Purpose Name";
		document.getElementById("pname").focus();
		return false;
	}else if(pname.length < 4 || pname.length > 70){
		document.getElementById("nameError").innerHTML="Please Enter Name between 4 to 70 character";
		document.getElementById("pname").focus();
		return false;
	}else if(!pname.match(regName) || !pname.match(onlySpace)){
		document.getElementById("nameError").innerHTML="Name cannot Consist Number or Special Character";
		document.getElementById("pname").focus();
		return false;
	}else{
		document.getElementById("nameError").innerHTML="";
	}
	
	if(pcode==""){
		document.getElementById("pcodeError").innerHTML="Please Enter Purpose Code";
		document.getElementById("pcode").focus();
		return false;
	}else if(pcode.length < 1 || pcode.length > 5){
		document.getElementById("pcodeError").innerHTML="Please Enter Valid Purpose Code";
		document.getElementById("pcode").focus();
		return false;
	}else{
		document.getElementById("pcodeError").innerHTML="";
	}
	
	if(payeecode==""){
		document.getElementById("payeecodeError").innerHTML="Please Enter Payee Code";
		document.getElementById("payeecode").focus();
		return false;
	}else if(payeecode.length != 4){
		document.getElementById("payeecodeError").innerHTML="Payee Code should be 4 character only";
		document.getElementById("payeecode").focus();
		return false;
	}else{
		document.getElementById("payeecodeError").innerHTML="";
		document.getElementById("overlay").style.display = "none";
	}
	*/
	var formData = new FormData(saveCompany);
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveCompanyDetail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			console.log(data1)
			if(data1.status==true){
				 document.getElementById("successmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("successmsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 $('#successmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("failmsg").innerHTML=data1.message;
				 document.getElementById("failmsgDiv").style.display="block";
				 $('#failmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("failmsgDiv").style.display="none";
				 document.getElementById("successmsgdiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });			
}

    
	function checkGstNumber() {
				var gstnNo = document.getElementById("gstnNo").value;
                var gstinformat = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/;   
                if (gstinformat.test(gstnNo)) {    
                    document.getElementById("gstnNoError").innerHTML="";
					document.getElementById("gstnNo").focus();
					
					return true;   
                } else {    
                   document.getElementById("gstnNoError").innerHTML="Please Enter Valid GSTN";
					document.getElementById("gstnNo").focus();
					return false;     
                }   
	}  
	function validateGstNumber() {
				var gstnNo = document.getElementById("gstnNo").value;
                var gstinformat = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/;   
                if (gstinformat.test(gstnNo)) {    
                    document.getElementById("gstnNoError").innerHTML="";
					document.getElementById("gstnNo").focus();
					$("#form2").show();
                    $("#form1").hide();
					return true;   
                } else {    
                   document.getElementById("gstnNoError").innerHTML="Please Enter Valid GSTN";
					document.getElementById("gstnNo").focus();
					return false;     
                }   
	}  
	
	
	function checkPanNumber() {
		
				var organizationType = document.getElementById("organizationType").value;
				var pan = document.getElementById("pan").value;
				///\d{2}[A-Z]{5}\d{4}[A-Z]{1}[A-Z\d]{1}[Z]{1}[A-Z\d]{1}/
                //var gstinformat = new RegExp('^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]1}[1-9A-Z]{1}Z[0-9A-Z]{1}$');    
                var panNoFormat = new RegExp('[A-Z]{5}[0-9]{4}[A-Z]{1}');
                
                 if(organizationType==""){
					document.getElementById("organizationTypeError").innerHTML="Please Select Account Type";
					document.getElementById("organizationType").focus();
					return false;
				}else{
					document.getElementById("organizationTypeError").innerHTML="";
					
				}
				
				 if (pan =="") {    
                    document.getElementById("panError").innerHTML="Please Enter Valid Pan Number";
					document.getElementById("pan").focus();
					return false;
                } else {    
                   document.getElementById("panError").innerHTML="";    
                }  
                if (panNoFormat.test(pan)) {    
                    document.getElementById("panError").innerHTML="";
                } else {    
                   document.getElementById("panError").innerHTML="Please Enter Valid Pan Number";
					document.getElementById("pan").focus();
					return false;     
                }   
                $("#form3").show();
                $("#form2").hide();
                
	}  
	

  function validateBrandNumber(){
	
	         	 var brandName = document.getElementById("brandName").value; 
			     var orgType2 = document.getElementById("orgType2").value; 
				 var panDetails = document.getElementById("panDetails").value; 
				 var companyName = document.getElementById("companyName").value; 
				 
				 var officeAddress = document.getElementById("officeAddress").value;
				 var addressLine = document.getElementById("addressLine").value;
				       
				 var pinCode = document.getElementById("pinCode").value; 
				 var companyPan2 = document.getElementById("companyPan2").value; 
				 var stateCode = document.getElementById("stateCode").value;  
				 
                 if(brandName==""){
					document.getElementById("brandNameError").innerHTML="Please Select Account Type";
					document.getElementById("brandName").focus();
					return false;
				}else{
					document.getElementById("brandNameError").innerHTML="";
				}
                if (orgType2 =="") {    
                    document.getElementById("orgType2Error").innerHTML="Please Select Organization Type";
                    document.getElementById("orgType2").focus();
                    return false;   
                } else {    
                   document.getElementById("orgType2Error").innerHTML="";
                }   
                 if (panDetails =="") {    
                    document.getElementById("panDetailsError").innerHTML="Please Enter Pan Details";
                    document.getElementById("panDetails").focus();
                    return false;   
                } else {    
                   document.getElementById("panDetailsError").innerHTML="";
                }   
                if (companyName =="") {    
                    document.getElementById("companyNameError").innerHTML="Please Enter Company Details";
                    document.getElementById("companyName").focus();
                    return false;   
                } else {    
                   document.getElementById("companyNameError").innerHTML="";
                }  
                if (officeAddress =="") {    
                    document.getElementById("officeAddressError").innerHTML="Please Enter Office Address";
                    document.getElementById("officeAddress").focus();
                    return false;   
                } else {    
                   document.getElementById("officeAddressError").innerHTML="";
                }  
                if (addressLine =="") {    
                    document.getElementById("addressLineError").innerHTML="Please Enter Address Line one";
                    document.getElementById("addressLine").focus();
                    return false;   
                } else {    
                   document.getElementById("addressLineError").innerHTML="";
                }  
                if (pinCode =="") {    
                    document.getElementById("pinCodeError").innerHTML="Please Enter Pincode";
                    document.getElementById("pinCode").focus();
                    return false;   
                } else {    
                   document.getElementById("pinCodeError").innerHTML="";
                }   
                
                 if (companyPan2 =="") {    
                    document.getElementById("companyPan2Error").innerHTML="Please Enter Company Pan";
                    document.getElementById("companyPan2").focus();
                    return false;   
                } else {    
                   document.getElementById("companyPan2Error").innerHTML="";
                }   
	 
	 			if (stateCode =="") {    
                    document.getElementById("stateCodeError").innerHTML="Please Select State";
                    document.getElementById("stateCode").focus();
                    return false;   
                } else {    
                   document.getElementById("stateCodeError").innerHTML="";
                } 
	 	 	$("#form4").show();
            $("#form3").hide();	
	}


function validateFormAndSubmit() {
				var paidDate = document.getElementById("paidDate").value;
             	var runFayrollFlag = document.getElementById("runFayrollFlag").value;
             	var salaryAdvancesFlag = document.getElementById("salaryAdvancesFlag").value;
             	
                if (paidDate =="") {    
					document.getElementById("paidDateError").innerHTML="Please Select Date";
                    ///document.getElementById("paidDateError").innerHTML="Please Select Date";
					document.getElementById("paidDate").focus();
					return false;   
                } else {    
                   document.getElementById("paidDateError").innerHTML="";
					document.getElementById("paidDate").focus();   
                }  
                 if (runFayrollFlag =="") {    
                    document.getElementById("runFayrollFlagError").innerHTML="Please Checked Automatically run payroll on selected date";
					document.getElementById("runFayrollFlag").focus();
					return false;   
                } else {    
                   document.getElementById("runFayrollFlagError").innerHTML="";
					document.getElementById("runFayrollFlag").focus();   
                }   
                 if (salaryAdvancesFlag =="") {    
                    document.getElementById("salaryAdvancesFlagError").innerHTML="Please Checked employees request salary advances";
					document.getElementById("salaryAdvancesFlag").focus();
					return false;   
                } else {    
                   document.getElementById("salaryAdvancesFlagError").innerHTML="";
					document.getElementById("salaryAdvancesFlag").focus();   
                }   
                
                var formData = new FormData(saveCompany);
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveCompanyDetail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			console.log(data1)
			if(data1.status==false){
				 document.getElementById("successmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("successmsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 //$('#successmsgdiv').delay(5000).fadeOut(400);
				// $("#form5").show();
				document.getElementById("saveNext").style.display="None";
				//document.getElementById("nextDiv").style.display="flex";
				$('#nextDiv').show();
				$("#form4").hide();
                $("#form5").show();
                $("#tab2").addClass("active");
				
                // $("#form4").hide();
			}else if(data1.status==true){
				 document.getElementById("failmsg").innerHTML=data1.message;
				 document.getElementById("failmsgDiv").style.display="block";
				 //$('#failmsgDiv').delay(5000).fadeOut(400);
				 //$("#form5").show();
                 //$("#form4").hide();
                 document.getElementById("saveNext").style.display="block";
				 document.getElementById("nextDiv").style.display="None";
				 $('#nextDiv').hide();
			}else{
				 document.getElementById("failmsgDiv").style.display="none";
				 document.getElementById("successmsgdiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });			
               
	}  
	
	
	function openTab5(){
		
				$("#form4").hide();
                $("#form5").show();
                $("#tab2").addClass("active");
	}