
function openBrandPage(){
	 $("#form2").show();
     $("#form1").hide();
}

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
	 //var companyPan2 = document.getElementById("companyPan2").value; 
	 var stateCode = document.getElementById("stateCode").value;  
	 
	 var regName = /^[a-zA-Z\s]*$/;
	 var onlySpace = /^$|.*\S+.*/;

	var formData = new FormData(saveCompany);
	 	$.ajax({
		type: "POST",
	     url:"/saveCompanyDetail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			//console.log(data1)
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
					document.getElementById("organizationTypeError").innerHTML="Please Select Organization Type";
					//document.getElementById("organizationType").focus();
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
				 //var companyPan2 = document.getElementById("companyPan2").value; 
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
      
        var employerid = document.getElementById("employerId").value;
        var formData = new FormData(saveCompany);
        formData.append("employerId",employerid);
       
       document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:"/saveCompanyDetail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			//console.log(data1)
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 document.getElementById("successmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("successmsgdiv").style.display="block";
				 
				 document.getElementById("employeeId").value=data1.data.employeeId;
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
			}else if(data1.status==false){
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
	
	
function getPayrollMaster()  {
	//document.getElementById("overlay").style.display = "flex";
	$.ajax({
		type: "GET",
		url:"/getPayrollMaster",
		data: {

		},
		beforeSend: function(xhr) {
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			$('#salaryComponentBasic').html(data2[0].per);
			//$('#salaryComponentBasic2').html(data2[0].per);
			document.getElementById("salaryComponentBasic2").value = data2[0].per;
			$('#perCtcBasic').html(data2[0].per_ctc);
			document.getElementById("perCtcBasic2").value = data2[0].per_ctc;
			
			$('#perBasic').html(data2[0].salary_component);
			//document.getElementById("perBasic2").value = data2[0].salary_component;
			$('#taxableBasic').html(data2[0].taxable);
			//document.getElementById("taxableBasic2").value = data2[0].taxable;
			
			$('#salaryComponentHra').html(data2[1].per);
			//$('#salaryComponentHra2').html(data2[1].per);
			document.getElementById("salaryComponentHra2").value = data2[1].per;
			$('#perCtcHra').html(data2[1].per_ctc);
			document.getElementById("perCtcHra2").value = data2[1].per_ctc;
			
			$('#perHra').html(data2[1].salary_component);
			//document.getElementById("perHra2").value = data2[1].salary_component;
			
			$('#taxableHra').html(data2[1].taxable);
			//document.getElementById("taxableHra2").value = data2[1].taxable;
			
			$('#salaryComponentSpecial').html(data2[2].per);
			//$('#salaryComponentSpecial2').html(data2[2].per);
			
			document.getElementById("salaryComponentSpecial2").value = data2[2].per;
			$('#perCtcSpecial').html(data2[2].per_ctc);
			document.getElementById("perCtcSpecial2").value = data2[2].per_ctc;
			
			$('#perSpecial').html(data2[2].salary_component);
			//document.getElementById("perSpecial2").value = data2[2].salary_component;
			
			$('#taxableSpecial').html(data2[2].taxable);
			//document.getElementById("taxableSpecial2").value = data2[2].taxable;
			
			
			$('#salaryComponentLta').html(data2[3].per);
			//$('#salaryComponentLta2').html(data2[3].per);
			document.getElementById("salaryComponentLta2").value = data2[3].per;
			$('#perCtcLta').html(data2[3].per_ctc);
			document.getElementById("perCtcLta2").value = data2[3].per_ctc;
				
			$('#perLta').html(data2[3].salary_component);
			//document.getElementById("perLta2").value = data2[3].salary_component;
			
			$('#taxableLta').html(data2[3].taxable);
			//document.getElementById("taxableLta2").value = data2[3].taxable;
			
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}


function validatePayrollAndSubmit(){
	
	      
	//calculating textbox value to 100
	 let total = 0;
      const textBoxes = document.querySelectorAll('.percent');
      
      textBoxes.forEach(textBoxes => {
        let value = parseInt(textBoxes.value, 10);
        total += isNaN(value) ? 0 : value;
      });

      if (total === 100) {
			document.getElementById("tableError").innerHTML="";
      } 
      else {
    	  document.getElementById("tableError").innerHTML="Total % age of CTC should be less than or equal to 100. Please adjust the values.";
    	  return false;
      }
      
      //end calculating textbox value to 100
      
	document.getElementById("signinLoader").style.display="flex";
	
	// get dynamic table data start
	var formData = new FormData(savePayroll);
    var allInputValues = [];
	
	 $('.newTable tbody tr').each(function () {
		 
	        var per = $(this).find('select.per').val();
	        var taxable = $(this).find('select.tax').val();
	       // alert(""+hours+""+rate);
	        var component = $(this).find('input.comp').val();
	        var percentage = $(this).find('input.percent').val();
	      // alert(""+comp+""+age);
	      var rowvalue=component+"@"+percentage+"@"+per+"@"+taxable;
	       allInputValues.push(rowvalue);
	    });  
   // console.log(allInputValues);
   // get dynamic table data start
    
    formData.append("listArray", allInputValues);
    
 
	 	$.ajax({
		type: "POST",
	     url:"/saveCompanyPayroll",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			//console.log(data1);
			//console.log(data.status);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 document.getElementById("payrollsuccessmsg").innerHTML=data1.message;
				 document.getElementById("payrollsuccessmsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 //$('#successmsgdiv').delay(5000).fadeOut(400);
				// $("#form5").show();
				//document.getElementById("saveNext").style.display="None";
				//document.getElementById("nextDiv").style.display="flex";
				/*$('#nextDiv').show();
				$("#form4").hide();
                $("#form5").show();
                $("#tab2").addClass("active");*/
				
                // $("#form4").hide();
			}else if(data1.status==false){
				 document.getElementById("payrollfailmsg").innerHTML=data1.message;
				 document.getElementById("payrollfailmsgDiv").style.display="block";
				 //$('#failmsgDiv').delay(5000).fadeOut(400);
				 //$("#form5").show();
                 //$("#form4").hide();
                 //document.getElementById("saveNext").style.display="block";
				 //document.getElementById("nextDiv").style.display="None";
				// $('#nextDiv').hide();
			}else{
				 document.getElementById("payrollfailmsgDiv").style.display="none";
				 document.getElementById("payrollsuccessmsgdiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });			
               
}  
	