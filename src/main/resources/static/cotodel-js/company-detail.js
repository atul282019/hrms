
function initializeOrgTypeDropdown() {
    const orgTypeDropdown = document.getElementById("orgType2");
    const selectedOrgType = orgTypeDropdown.value;
    const subTypeDropdown = document.getElementById("organizationsubType");
    subTypeDropdown.innerHTML = "";
    const defaultOption = document.createElement("option");
    defaultOption.textContent = "Select Organization Sub Type*";
    defaultOption.value = "";
    defaultOption.disabled = true;
    defaultOption.selected = true;
    subTypeDropdown.appendChild(defaultOption);

    // Clear any previous error message
    document.getElementById("organizationsubTypeError").textContent = "";

    $.ajax({
        type: "GET",
        url: "/getorgsubType",
        data: {
            orgTypeId: selectedOrgType,
        },
        dataType: "json",
        success: function (response) {
            console.log("Response received:", response);

            if (response.status === true && Array.isArray(response.data) && response.data.length > 0) {
                // Populate dropdown with data from the response
                response.data.forEach((item) => {
                    const option = document.createElement("option");
                    option.value = item.id;
                    option.textContent = item.orgType;
                    subTypeDropdown.appendChild(option);
                });
            } else {
                console.warn("No data available for the selected organization type.");
                document.getElementById("organizationsubTypeError").textContent = "No sub-types available for the selected organization type.";
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching organization sub types:", status, error);
            document.getElementById("organizationsubTypeError").textContent = "Error loading organization sub types.";
        },
    });
}


function openBrandPage(){
	 $("#form2").show();
     $("#form1").hide();
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
                var panNoFormat = new RegExp('[A-Z]{5}[0-9]{4}[A-Z]{1}');
                
                 if(organizationType==""){
					document.getElementById("organizationTypeError").innerHTML="Please Select Organization Type";
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
	
	function tab2Validation() {
		
				var organizationType = document.getElementById("organizationType").value;
				var pan = document.getElementById("pan").value;
	            var panNoFormat = new RegExp('[A-Z]{5}[0-9]{4}[A-Z]{1}');
	            
	             if(organizationType==""){
					document.getElementById("organizationTypeError").innerHTML="Please Select Organization Type";
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
	       
	} 

  function validateBrandNumber(){
	
	         	 var brandName = document.getElementById("brandName").value; 
			     var orgType2 = document.getElementById("orgType2").value;  
				 var companyName = document.getElementById("companyName").value; 
				 
				 var officeAddress = document.getElementById("officeAddress").value;
				 var addressLine = document.getElementById("addressLine").value;
				       
				 var pinCode = document.getElementById("pinCode").value; 
				 var stateCode = document.getElementById("stateCode").value;  
			
				const specialChrRegex = /[^a-zA-Z0-9\s.,-]/; 
                 if(brandName==""){
					document.getElementById("brandNameError").innerHTML="Please Enter Brand Name";
					document.getElementById("brandName").focus();
					return false;
				}
				else if (specialChrRegex.test(brandName)) { // Check for special characters
				        document.getElementById("brandNameError").innerText = "Please Enter a Valid Brand Name (No special characters)";
						return false;
				    }
				else{
					document.getElementById("brandNameError").innerHTML="";
				}
                if (orgType2 =="") {    
                    document.getElementById("orgType2Error").innerHTML="Please Select Organization Type";
                    document.getElementById("orgType2").focus();
                    return false;   
                } else {    
                   document.getElementById("orgType2Error").innerHTML="";
                }   
                if (companyName =="") {    
                    document.getElementById("companyNameError").innerHTML="Please Enter Company Name";
                    document.getElementById("companyName").focus();
                    return false;   
                }
				else if (specialChrRegex.test(companyName)) { // Check for special characters
				        document.getElementById("companyNameError").innerText = "Please Enter a Valid Company Name (No special characters)";
						return false;
				    }
				 else {    
                   document.getElementById("companyNameError").innerHTML="";
                }  
                if (officeAddress =="") {    
                    document.getElementById("officeAddressError").innerHTML="Please Enter Registered office Address";
                    document.getElementById("officeAddress").focus();
                    return false;   
                }
				else if (specialChrRegex.test(officeAddress)) { // Check for special characters
				        document.getElementById("officeAddressError").innerText = "Please Enter a Valid Registered office Address (No special characters)";
						return false;
					}
				 else {    
                   document.getElementById("officeAddressError").innerHTML="";
                }  
                if (addressLine =="") {    
                    document.getElementById("addressLineError").innerHTML="Please Enter Address Line 2";
                    document.getElementById("addressLine").focus();
                    return false;   
                }
				else if (specialChrRegex.test(addressLine)) { // Check for special characters
				        document.getElementById("addressLineError").innerText = "Please Enter a Valid Registered office Address (No special characters)";
				    }
				 else {    
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

	function validateTab3() {
	    // Retrieve values
	    const brandName = document.getElementById("brandName").value.trim();
	    const orgType2 = document.getElementById("orgType2").value;
	    const companyName = document.getElementById("companyName").value.trim();
	    const officeAddress = document.getElementById("officeAddress").value.trim();
		const addressLine = document.getElementById("addressLine").value.trim();
	    const pinCode = document.getElementById("pinCode").value.trim();
	    const stateCode = document.getElementById("stateCode").value;
		const specialChrRegex = /[^a-zA-Z0-9\s.,-]/; 
	    if (brandName === "") {
	        document.getElementById("brandNameError").innerText = "Please Enter Brand Name";
	    }
		else if (specialChrRegex.test(brandName)) { // Check for special characters
		        document.getElementById("brandNameError").innerText = "Please Enter a Valid Brand Name (No special characters)";
		    }
		 else {
	        document.getElementById("brandNameError").innerText = "";
	    }
		
	    if (orgType2 === "Select Organization Type*" || orgType2 === "") {
	        document.getElementById("orgType2Error").innerText = "Please select an organization type.";
	    } else {
	        document.getElementById("orgType2Error").innerText = "";
	    }

	    if (companyName === "") {
	        document.getElementById("companyNameError").innerText = "Please Enter Company Name";
	    }
		else if (specialChrRegex.test(companyName)) { // Check for special characters
		        document.getElementById("companyNameError").innerText = "Please Enter a Valid Company Name (No special characters)";
		    }
		 else {
	        document.getElementById("companyNameError").innerText = "";
	    }

	    if (officeAddress === "") {
	        document.getElementById("officeAddressError").innerText = "Please Enter Office Address";
	    }
		else if (specialChrRegex.test(officeAddress)) { // Check for special characters
	        document.getElementById("officeAddressError").innerText = "Please Enter a Valid Office Address (No special characters)";
	    }
		 else {
	        document.getElementById("officeAddressError").innerText = "";
	    }
		if (addressLine =="") {    
           document.getElementById("addressLineError").innerText ="Please Enter Address Line 2";
        }
	    else if (specialChrRegex.test(addressLine)) { // Check for special characters
	        document.getElementById("addressLineError").innerText = "Please Enter a Valid Address Line2 (No special characters)";
	    }
	   else {    
           document.getElementById("addressLineError").innerHTML="";
        } 
	    if (pinCode === "") {
	        document.getElementById("pinCodeError").innerText = "Please Enter Pin Code";
	    } else if (pinCode.length !== 6) {
	        document.getElementById("pinCodeError").innerText = "PIN Code must be 6 digits.";
	    } else {
	        document.getElementById("pinCodeError").innerText = "";
	    }
	    if (stateCode === "Select State*" || stateCode === "") {
	        document.getElementById("stateCodeError").innerText = "Please select a state.";
	    } else {
	        document.getElementById("stateCodeError").innerText = "";
	    }
	}

function validateDate() {
					var paidDate = document.getElementById("paidDate").value;
	             	
	                if (paidDate =="") {    
						document.getElementById("paidDateError").innerHTML="Please Select Date";
						document.getElementById("paidDate").focus();
						return false;   
	                } else {    
	                   document.getElementById("paidDateError").innerHTML="";
						document.getElementById("paidDate").focus();   
	                }
		}
function validateFormAndSubmit() {
				var paidDate = document.getElementById("paidDate").value;
             	var runPayrollFlag = document.getElementById("runPayrollFlag").value;
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
                 if (runPayrollFlag =="") {    
                    document.getElementById("runPayrollFlagError").innerHTML="Please Checked Automatically run payroll on selected date";
					document.getElementById("runPayrollFlag").focus();
					return false;   
                } else {    
                   document.getElementById("runPayrollFlagError").innerHTML="";
					document.getElementById("runPayrollFlag").focus();   
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
		var tableid = document.getElementById("tableid").value;
		
		var orgType2 = document.getElementById("orgType2").value; 
		var organizationType = document.getElementById("organizationType").value; 
	    var orgsubType = document.getElementById("organizationsubType").value;
		 
		 
        var formData = new FormData(saveCompany);
        formData.append("employerId",employerid);
		formData.append("id",tableid);
		formData.append("orgType",orgType2);
		formData.append("orgsubType",orgsubType);
			
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
	
	      console.log("validatePayrollAndSubmit clicked");
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
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 document.getElementById("payrollsuccessmsg").innerHTML=data1.message;
				 document.getElementById("payrollsuccessmsgdiv").style.display="block";
				 $("#tab3").addClass("active");
				  //window.location.href = "/dashboard";
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

function AutoFillForm() {
	var employerid = document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
		
        $.ajax({
			type: "POST",
           url:"/getpayrollDetails", 
		   data: {
				"employerId": employerid,
	      		 },
            dataType: 'json',
            success: function (response) {
				console.log("company Data"+response)
				document.getElementById("signinLoader").style.display="none";
					
				if (response.status) {
				    const data = response.data;
                // Auto-fill the form fields with fetched data
                document.getElementById("gstnNo").value = data.gstnNo || "";
                document.getElementById("pan").value = data.pan || "";
                document.getElementById("brandName").value = data.brandName || "";
                document.getElementById("companyName").value = data.companyName || "";
                document.getElementById("officeAddress").value = data.officeAddress || "";
                document.getElementById("addressLine").value = data.addressLine || "";
                document.getElementById("pinCode").value = data.pinCode || "";
				document.getElementById("tableid").value = data.id;
				
                const orgTypeSelect = document.getElementById("orgType2");
                if (orgTypeSelect) {
					
                    orgTypeSelect.value = data.orgType || "";
					initializeOrgTypeDropdown();
					
                }
				
				const organizationType = document.getElementById("organizationType");
                if (organizationType) {
                    organizationType.value = data.organizationType || "";
                }
				
				const organizationsubType = document.getElementById("organizationsubType");
                if (organizationsubType) {
                    organizationsubType.value = data.orgsubType || "";
                }

                const stateCodeSelect = document.getElementById("stateCode");
                if (stateCodeSelect) {
                    stateCodeSelect.value = data.stateCode || "";
                }
				
				document.getElementById("payrollEnabledFlag").checked = data.payrollEnabledFlag || false;
                document.getElementById("runPayrollFlag").checked = data.runPayrollFlag || false;
                document.getElementById("salaryAdvancesFlag").checked = data.salaryAdvancesFlag || false;

                document.getElementById("paidDate").value = data.paidDate || "";
          }
			},
            error: function ( status, error) {
                console.error("Failed to fetch data:", status, error);
                alert("Unable to fetch employer data. Please try again.");
            }
        });
    }
  
	