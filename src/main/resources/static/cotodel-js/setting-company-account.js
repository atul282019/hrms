function fetchOrgDetails(type) {
	var employerid = document.getElementById("employerId").value;
	const gstContainer = document.getElementById('gstInputContainer');
	 const panContainer = document.getElementById('panInputContainer');

	document.getElementById("signinLoader").style.display="flex";
		
        $.ajax({
			type: "POST",
           url:"/getCompanyProfileStatus", 
		   data: {
				"employerId": employerid,
	      		 },
            dataType: 'json',
            success: function (response) {
				console.log("company Data"+response)
				document.getElementById("signinLoader").style.display="none";
					
				if (response.status) {
				    const data = response.data;

				if (type === "GST") {
					gstContainer.style.display = 'block';
					panContainer.style.display = 'none';
                    document.getElementById("gstnNo").value = data.gstIdentificationNumber || "";
                } else if (type === "PAN") {
					gstContainer.style.display = 'none';
					        panContainer.style.display = 'block';
                    document.getElementById("panNo").value = data.pan || "";
                }

          }

			},
            error: function ( status, error) {
                console.error("Failed to fetch data:", status, error);
                alert("Unable to fetch employer data. Please try again.");
            }
        });
    }
	function validateInput() {
	    const selectedRadio = document.querySelector('input[name="orgRadio"]:checked').value;
	    const gstInput = document.getElementById('gstnNo');
	    const panInput = document.getElementById('panNo');
	    const gstContainer = document.getElementById('gstInputContainer');
	    const panContainer = document.getElementById('panInputContainer');
		const currentForm = document.getElementById('Form1');
	    const additionalForm = document.getElementById('Form2');
	    const errorElement = document.getElementById('bankNameError');
	    let isValid = true;

	    // Clear previous error messages and reset input borders
	    errorElement.textContent = '';
	    gstContainer.style.borderColor = '';
	    panContainer.style.borderColor = '';

	    // Color to apply when there's an error
	    const errorColor = 'red'; // The color you want to use

	    if (selectedRadio === 'GST') {
	        const gstValue = gstInput.value.trim();
	        const gstRegex = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/;

	        if (!gstValue) {
	            errorElement.textContent = 'GST Number is required.';
	            gstContainer.style.borderColor = errorColor;
	            isValid = false;
	        } else if (!gstRegex.test(gstValue)) {
	            errorElement.textContent = 'Invalid GST Number. Please enter a valid GST.';
	            gstContainer.style.borderColor = errorColor;
	            isValid = false;
		   }
		   getDetailByGSTNo(gstValue);
	    } else if (selectedRadio === 'PAN') {
	        const panValue = panInput.value.trim();
	        const panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;

	        if (!panValue) {
	            errorElement.textContent = 'PAN Number is required.';
	            panContainer.style.borderColor = errorColor;
	            isValid = false;
	        } else if (!panRegex.test(panValue)) {
	            errorElement.textContent = 'Invalid PAN Number. Please enter a valid PAN.';
	            panContainer.style.borderColor = errorColor;
	            isValid = false;
	        }
			
			getDetailByPanNo(panValue);
	    }

	    if (isValid) {
	        // Reset border color to default (if any)
			
	        gstContainer.style.borderColor = '';
	        panContainer.style.borderColor = '';
			currentForm.style.display = 'none';

	        // Show the additional form
	        additionalForm.style.display = 'block';
			toggleVisibility();
			$("#tab2").addClass("active");
	        //alert('Validation successful!');
	        // Proceed to the next step (submit form or any other action)
	    }
	}
	function goBackToForm1() {
	    const currentForm = document.getElementById('Form2');
	    const form1 = document.getElementById('Form1');

	    // Hide Form 2 (Additional Form)
	    currentForm.style.display = 'none';

	    // Show Form 1 (GST/PAN input form)
	    form1.style.display = 'block';
		//window.location.href="/Setting-Organization-Account";
	}
	
	// Function to toggle visibility of PAN text box based on selected radio button
	function toggleVisibility() {
	    const selectedRadio = document.querySelector('input[name="orgRadio"]:checked').value;
	    const gstContainer = document.getElementById('gstInputContainer1');
	    const panContainer = document.getElementById('panInputContainer1');
		const gstInput = document.getElementById('gstnNo').value;
		const panInput = document.getElementById('panNo').value;

	    // Check which radio button is selected (GST or PAN)
		gstContainer.style.display = 'block';
	    panContainer.style.display = 'none';
		document.getElementById('gstnNo1').value=gstInput;
					
	     if (selectedRadio === 'PAN') {
	        // If PAN is selected, show PAN input and hide GST input
	        gstContainer.style.display = 'none';
	        panContainer.style.display = 'block';
			document.getElementById('panNo1').value=panInput;
	    }
	}
	function addDynamicIcon(inputElement) {
	    // Add the 'filled' class if there is input text; otherwise, remove it
	    if (inputElement.value.trim() !== "") {
	        inputElement.classList.add("filled"); // Adds the icon when input is filled
	    } else {
	        inputElement.classList.remove("filled"); // Removes the icon if input is cleared
	    }
	}
	function continueToForm3() {
	    const currentForm1 = document.getElementById('Form1');
	    const currentForm2 = document.getElementById('Form2');
	    const nextForm3 = document.getElementById('Form3'); // Assuming you have Form3 defined
		const checkboxsection = document.getElementById('checkboxsection');

		//var gst = document.getElementById('gstradio').value;
		//var pan = document.getElementById('panradio').value;
		//var gstnNo = document.getElementById('gstnNo').value;
		//var panNo = document.getElementById('panNo').value;
		var gstnNo1 = document.getElementById('gstnNo1').value;
		var panNo1 = document.getElementById('panNo1').value;
		var legalName = document.getElementById('legalName').value;
		var tradeName = document.getElementById('tradeName').value;
		var orgType = document.getElementById('orgType').value;
		
		var address1 = document.getElementById('address1').value;
		var address2 = document.getElementById('address2').value;
		var district = document.getElementById('district').value;
		var pincode = document.getElementById('Pincode').value;
		var state = document.getElementById('state').value;
		var consent = document.getElementById('flexCheckDefault').value;
		
		var employerName = document.getElementById('employerName').value;
		var employerId = document.getElementById('employerId').value;
		var userMobile = document.getElementById('userMobile').value;
		
		var panNoFormat = new RegExp('[A-Z]{5}[0-9]{4}[A-Z]{1}');
		
		const radio1 = document.getElementById("gst");
		const radio2 = document.getElementById("pan");
		const status = document.getElementById("status");
		
		if (radio2.checked) {
			if (panNo1 =="") {    
	            status.textContent="Please Enter Valid Pan Number";
				document.getElementById("panNo1").focus();
				return false;
	        } else {    
				status.textContent="";
	        }  
	        if (panNoFormat.test(panNo1)) {    
	            status.textContent="";
	        } else {    
	            status.textContent="Please Enter Valid Pan Number";
				document.getElementById("panNo1").focus();
				return false;     
	        }
			if (legalName =="") {    
	            status.textContent="Please Enter Legal Name for Business";
				document.getElementById("legalName").focus();
				return false;
	        } else {    
				status.textContent="";
	        }
			if (tradeName =="") {    
	            status.textContent="Please Enter Trade Name";
				document.getElementById("tradeName").focus();
				return false;
	        } else {    
				status.textContent="";
	        }     
			if (orgType =="") {    
	            status.textContent="Please Enter Organization Type";
				document.getElementById("orgType").focus();
				return false;
	        } else {    
				status.textContent="";
	        }    	 
			if (address1 =="") {    
	            status.textContent="Please Enter Address Line 1";
				document.getElementById("address1").focus();
				return false;
	        } else {    
				status.textContent="";
	        } 
			if (address2 =="") {    
	            status.textContent="Please Enter Address Line 2";
				document.getElementById("address2").focus();
				return false;
	        } else {    
				status.textContent="";
	        } 
			if (district =="") {    
	            status.textContent="Please Enter District";
				document.getElementById("district").focus();
				return false;
	        } else {    
				status.textContent="";
	        } 
			if (pincode =="") {    
	            status.textContent="Please Enter Pincode";
				document.getElementById("Pincode").focus();
				return false;
	        } else {    
				status.textContent="";
	        } 
			if (state =="") {    
	            status.textContent="Please Enter State";
				document.getElementById("state").focus();
				return false;
	        } else {    
				status.textContent="";
	        } 
												
												
		  } else if (radio1.checked) {
			if (gstnNo1 =="") {    
		            status.textContent="Please Enter Valid GST Number";
					document.getElementById("gstnNo1").focus();
					return false;
		        } else {    
		           status.textContent.innerHTML="";    
		        }  
				if (legalName =="") {    
					            status.textContent="Please Enter Legal Name for Business";
								document.getElementById("legalName").focus();
								return false;
					        } else {    
								status.textContent="";
					        }
							if (tradeName =="") {    
					            status.textContent="Please Enter Trade Name";
								document.getElementById("tradeName").focus();
								return false;
					        } else {    
								status.textContent="";
					        }     
							if (orgType =="") {    
					            status.textContent="Please Enter Organization Type";
								document.getElementById("orgType").focus();
								return false;
					        } else {    
								status.textContent="";
					        }    	 
							if (address1 =="") {    
					            status.textContent="Please Enter Address Line 1";
								document.getElementById("address1").focus();
								return false;
					        } else {    
								status.textContent="";
					        } 
							
							if (address2 =="") {    
					            status.textContent="Please Enter Address Line 2";
								document.getElementById("address2").focus();
								return false;
					        } else {    
								status.textContent="";
					        } 
							if (district =="") {    
					            status.textContent="Please Enter District";
								document.getElementById("district").focus();
								return false;
					        } else {    
								status.textContent="";
					        } 
							
							
							if (pincode =="") {    
					            status.textContent="Please Enter Pincode";
								document.getElementById("Pincode").focus();
								return false;
					        } else {    
								status.textContent="";
					        } 
							if (state =="") {    
					            status.textContent="Please Enter State";
								document.getElementById("state").focus();
								return false;
					        } else {    
								status.textContent="";
					        } 
		    } else {
		      status.textContent = "Please Select GST or PAN";
		    }
		  
		
	
		$.ajax({
		type: "POST",
		url:"/saveOrganizationDetail",
		    data: {
				
				"gstnNo1":gstnNo1,
				"pan": panNo1,
				"legalNameOfBusiness": legalName,
				"tradeName":legalName,
				"constitutionOfBusiness":tradeName,
				"orgType": orgType,
				"address1": address1,
				"address2": address2,
				"districtName": district,
				"pincode": pincode,
				"stateName": state,
			    "consent": "",
				"streetName": "",
				"buildingNumber": "",
				"buildingName":"",
				"location":"",
				"floorNumber":"",
				"natureOfPrincipalPlaceOfBusiness":"",
				"centerJurisdictionCode":"",
				"gstIdentificationNumber":gstnNo1,
				"pan":panNo1,
				"createdBy":employerName,
				"employerMobile":userMobile,
				"employerId":employerId,
				
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
				
				// Hide both Form1 and Form2
			    currentForm1.style.display = 'none';
			    currentForm2.style.display = 'none';
			    // Show Form3
			    nextForm3.style.display = 'block';
				checkboxsection.display = 'block';
						
				}else if(data1.status==false){
				}else{
				}
		       },
		     error: function(e){
		         alert('Error: ' + e);
		     }
		}); 	
	}
	
	function changeOtpStatus() {
			
			var employerName = document.getElementById('employerName').value;
			var employerId = document.getElementById('employerId').value;
			var userMobile = document.getElementById('userMobile').value;
		
			var gstnNo1 = document.getElementById('gstnNo1').value;
			var panNo1 = document.getElementById('panNo1').value;
			var legalName = document.getElementById('legalName').value;
			var tradeName = document.getElementById('tradeName').value;
			var orgType = document.getElementById('orgType').value;
			
			var address1 = document.getElementById('address1').value;
			var address2 = document.getElementById('address2').value;
			var district = document.getElementById('district').value;
			var pincode = document.getElementById('Pincode').value;
			var state = document.getElementById('state').value;
			var consent = document.getElementById('flexCheckDefault').value;
				

			$.ajax({
			type: "POST",
			url:"/updateOrganizationDetail",
			    data: {
					
					"gstnNo1":gstnNo1,
					"pan": panNo1,
					"legalNameOfBusiness": legalName,
					"tradeName":legalName,
					"constitutionOfBusiness":tradeName,
					"orgType": orgType,
					"address1": address1,
					"address2": address2,
					"districtName": district,
					"pincode": pincode,
					"stateName": state,
				    "consent": "",
					"streetName": "",
					"buildingNumber": "",
					"buildingName":"",
					"location":"",
					"floorNumber":"",
					"natureOfPrincipalPlaceOfBusiness":"",
					"centerJurisdictionCode":"",
					"gstIdentificationNumber":gstnNo1,
					"pan":panNo1,
					"createdBy":employerName,
					"employerMobile":userMobile,
					"employerId":employerId,
				    "consent": "Yes",
					"otpStatus":"yes",
					"createdBy":employerName,
					"employerMobile":userMobile,
					"employerId":employerId,
					
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
					}else if(data1.status==false){
					}else{
					}
			       },
			     error: function(e){
			         alert('Error: ' + e);
			     }
			}); 	
		}
	
	function showOtpSection() {
	        // Hide the Back and Authenticate buttons
	        document.getElementById('backButton').style.display = 'none';
	        document.getElementById('authButton').style.display = 'none';
	        
	        // Show the OTP section
	        document.getElementById('otpsection').style.display = 'block';
	    }
	
		function goBackToForm2() {
			    const currentForm = document.getElementById('Form3');
			    const form1 = document.getElementById('Form2');
				const otpsection = document.getElementById('otpsection');
				document.getElementById('backButton').style.display = 'block';
		        document.getElementById('authButton').style.display = 'block';

			    // Hide Form 2 (Additional Form)
			    currentForm.style.display = 'none';otpsection

			    // Show Form 1 (GST/PAN input form)
			    form1.style.display = 'block';
				otpsection.style.display = 'none';
			}
			
function getDetailByGSTNo(gstValue){
document.getElementById("signinLoader").style.display="flex";
//alert("gst no"+gstValue);
	$.ajax({
		type: "POST",
	   url:"/getGSTDetailsByGSTNumber", 
	   data: {
			"gst": gstValue,
	  		 },
	    dataType: 'json',
	    success: function (response) {
			console.log("company Data"+response)
			if (response.status) {
			    const data = response.data;
	        // Auto-fill the form fields with fetched data
	       // document.getElementById("gstnNo").value = data.gstnNo || "";

			//if (type === "GST") {
				//gstContainer.style.display = 'block';
				//panContainer.style.display = 'none';
	            document.getElementById("gstnNo").value = data.gstnNo || "";
				if(data.legalNameOfBusiness!==null){
				document.getElementById("legalName").value = data.legalNameOfBusiness || "";
				document.getElementById("legalName").classList.remove('input-with-dynamic-icon');
				document.getElementById("legalName").classList.add('input-with-static-icon');
				}
				else{
					document.getElementById("legalName").remove('did-floating-input input-with-dynamic-icon');
				}
				if(data.tradeName!==null){
				document.getElementById("tradeName").value = data.tradeName || "";
				document.getElementById("tradeName").classList.remove('input-with-dynamic-icon');
				document.getElementById("tradeName").classList.add('input-with-static-icon');
				}
				
				if(data.constitutionOfBusiness!==null){
				document.getElementById("orgType").value = data.constitutionOfBusiness || "";
				document.getElementById("orgType").classList.remove('input-with-dynamic-icon');
				document.getElementById("orgType").classList.add('input-with-static-icon');
				}
				
				if(data.constitutionOfBusiness!==null){
				document.getElementById("orgType").value = data.constitutionOfBusiness || "";
				document.getElementById("orgType").classList.remove('input-with-dynamic-icon');
				document.getElementById("orgType").classList.add('input-with-static-icon');
				}
				
				if(data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.location!==null){
				document.getElementById("address1").value = data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.location || "";
				document.getElementById("address1").classList.remove('input-with-dynamic-icon');
				document.getElementById("address1").classList.add('input-with-static-icon');
				}
			

				if(data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.streetName!==null){
				document.getElementById("address2").value = data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.streetName || "";
				document.getElementById("address2").classList.remove('input-with-dynamic-icon');
				document.getElementById("address2").classList.add('input-with-static-icon');
				}
			
				if(data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.districtName!==null){
				document.getElementById("district").value = data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.districtName || "";
				document.getElementById("district").classList.remove('input-with-dynamic-icon');
				document.getElementById("district").classList.add('input-with-static-icon');
				}
				
				if(data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.pincode!==null){
				document.getElementById("Pincode").value =data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.pincode || "";
				document.getElementById("Pincode").classList.remove('input-with-dynamic-icon');
				document.getElementById("Pincode").classList.add('input-with-static-icon');
				}
				
				if(data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.stateName!==null){
				document.getElementById("state").value = data.principalPlaceOfBusinessFields.principalPlaceOfBusinessAddress.stateName || "";
				document.getElementById("state").classList.remove('input-with-dynamic-icon');
				document.getElementById("state").classList.add('input-with-static-icon');
				}
				
				document.getElementById("signinLoader").style.display="none";
	      //  } else if (type === "PAN") {
			//	gstContainer.style.display = 'none';
		//		panContainer.style.display = 'block';
	      //      document.getElementById("panNo").value = data.pan || "";
	     //   }
	     }

		},
	    error: function ( status, error) {
	        console.error("Failed to fetch data:", status, error);
	        alert("Unable to fetch employer data. Please try again.");
	    }
	});
	
}

			
function getDetailByPanNo(panValue){
	//alert("gst no"+panValue);
	$.ajax({
		type: "POST",
	   url:"/getGSTDetailsByGSTNumber", 
	   data: {
			"panDetails": panValue,
	  		 },
	    dataType: 'json',
	    success: function (response) {
			console.log("company Data"+response)
			document.getElementById("signinLoader").style.display="none";
				
			if (response.status) {
			    const data = response.data;
	        // Auto-fill the form fields with fetched data
	       // document.getElementById("gstnNo").value = data.gstnNo || "";

			if (type === "GST") {
				gstContainer.style.display = 'block';
				panContainer.style.display = 'none';
	            document.getElementById("gstnNo").value = data.gstnNo || "";
	        } else if (type === "PAN") {
				gstContainer.style.display = 'none';
				        panContainer.style.display = 'block';
	            document.getElementById("panNo").value = data.pan || "";
	        }

	  }

		},
	    error: function ( status, error) {
	        console.error("Failed to fetch data:", status, error);
	        alert("Unable to fetch employer data. Please try again.");
	    }
	});
	
}

function getOTP() {
    var userMobile = document.getElementById("userMobile").value;
    document.getElementById("signinLoader").style.display = "flex";
 
    $.ajax({
        type: "POST",
        url: "/smsOtpSender",
        dataType: 'json',
        data: {
			 "mob": userMobile
		},
        success: function (data) {
            var obj = data;
            document.getElementById("signinLoader").style.display = "none";

            if (obj['status'] == "SUCCESS") {
				// Hide the Back and Authenticate buttons
				document.getElementById("orderId").value = obj['orderId'];
		        document.getElementById('backButton').style.display = 'none';
		        document.getElementById('authButton').style.display = 'none';
		        // Show the OTP section
		        document.getElementById('otpsection').style.display = 'block';
				var timeleft = 60; // 3 minutes in seconds
				   var downloadTimer = setInterval(function () {
				       var minutes = Math.floor(timeleft / 60); 
				       var seconds = timeleft % 60; 
				       document.getElementById("countdown").innerHTML = 
				           (minutes < 10 ? "0" + minutes : minutes) + ":" + 
				           (seconds < 10 ? "0" + seconds : seconds);

				       timeleft -= 1;
					   					   
				       if (timeleft < 0) {
						clearInterval(downloadTimer);
                        document.getElementById("countdown").innerHTML = " ";
				       }
				   }, 1000); 
            } else if (obj['status'] == "FAILURE") {
				document.getElementById("orderId").value = obj['orderId'];
		        document.getElementById('backButton').style.display = 'block';
		        document.getElementById('authButton').style.display = 'block';
		        document.getElementById('otpsection').style.display = 'none';
            } else {
				document.getElementById("orderId").value = obj['orderId'];
				document.getElementById('backButton').style.display = 'block';
			    document.getElementById('authButton').style.display = 'block';
			    document.getElementById('otpsection').style.display = 'none';
            }
        },
        error: function (e) {
            alert('Error: ' + e);
        }
    });
}

function resendOTP() {
	
    console.log("resend otp clicked");
	document.getElementById("password1").value = "";
	document.getElementById("password2").value = "";
	document.getElementById("password3").value = "";
	document.getElementById("password4").value = "";
	document.getElementById("password5").value = "";
	document.getElementById("password6").value = "";
	document.getElementById("otpError").value = "";
	
    var userMobile = document.getElementById("userMobile").value;
    var orderId = document.getElementById("orderId").value;
    document.getElementById("resendOTPText").style.display = "none"; 
	document.getElementById("signinLoader").style.display = "flex";
	
    $.ajax({
        type: "POST",
        url: "/smsOtpResender",
        dataType: 'json',
        data: { "mob": userMobile, "orderId": orderId },
        success: function (data) {
            var obj = data;
			document.getElementById("signinLoader").style.display = "none";
            if (obj['status'] == "SUCCESS") {
				const button = document.getElementById('otpVerifyBtn');
				button.disabled = false; 
                $('#errorOtp').hide('slow');
                document.getElementById("countdown").innerHTML = "00:60"; 
                startCountdown(); 
            } else {
                $('#errorOtp').html(obj['msg']);
                document.getElementById("resendOTPText").style.display = "block";
            }
        },
        error: function (e) {
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
  	var employerMobile = document.getElementById("userMobile").value;
  	
	if (password1 == "" && password1.length < 1) {
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
		const button = document.getElementById('otpVerifyBtn');
		 button.disabled = false; 
  		document.getElementById("otpError").innerHTML="";
  	}
	//document.getElementById("authenticate").disabled = true;
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
  				"mob": employerMobile,
  				"orderId": orderId,
  				"userName":employerMobile
  			},
  			success: function(data) {
  				var obj = data;

  				if (obj['status']== true) {
					
					$('#ModalConfirm').modal('show');
					$("#tab3").addClass("active");
					changeOtpStatus();
					//document.getElementById("password1").value="";
					//document.getElementById("password2").value="";
					//document.getElementById("password3").value="";
					//document.getElementById("password4").value="";
				    //document.getElementById("password5").value="";
					//document.getElementById("password6").value="";
					
  				}else if (obj['status'] == false) {
					$('#ModalReject').modal('show');
					 document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
					 document.getElementById("otpVerifyBtn").disabled = true;
				} else {
  				
  				}
  			},
  			error: function(e) {
  				alert('Error: ' + e);
  			}
  		});
  }


function startCountdown() {
        var timeleft =60; // 3 minutes in seconds
	    //var timeleft = Math.floor(Math.random() * 60); /// 1 min  random in seconds
        var downloadTimer = setInterval(function () {
        var minutes = Math.floor(timeleft / 60); // Calculate minutes
        var seconds = timeleft % 60; // Calculate remaining seconds

        // Update countdown display in "MM:SS" format
        document.getElementById("countdown").innerHTML = 
            (minutes < 10 ? "0" + minutes : minutes) + ":" + 
            (seconds < 10 ? "0" + seconds : seconds);

        timeleft -= 1;

        if (timeleft < 0) {
            clearInterval(downloadTimer);
            document.getElementById("countdown").innerHTML = " ";
            document.getElementById("resendOTPText").style.display = "block";
            document.getElementById("optBtn").disabled = false;
            document.getElementById("verifyotpdiv").style.display = "none";
        }
    }, 1000); // Runs every second
}
function toggleOtpVisibility() {
    // Get the icon element
    const toggleIcon = document.getElementById('toggleOtpVisibility').querySelector('i');

    // Get all OTP input fields (currently only of type password)
    const otpInputs = document.querySelectorAll('.otp-box input[type="password"], .otp-box input[type="text"]');

    // Check if the current icon is "eye" (which means password is hidden)
    const isPasswordVisible = toggleIcon.classList.contains("fa-eye");

    // Loop through each OTP input and toggle between 'password' and 'text' type
    otpInputs.forEach(input => {
        if (isPasswordVisible) {
            input.type = "text";  // Show password (set input type to text)
        } else {
            input.type = "password";  // Hide password (set input type to password)
        }
    });

    // Toggle the icon between 'fa-eye' (eye) and 'fa-eye-slash' (crossed eye)
    if (isPasswordVisible) {
        toggleIcon.classList.remove("fa-eye");
        toggleIcon.classList.add("fa-eye-slash");  // Change to crossed-eye icon (showing password)
    } else {
        toggleIcon.classList.remove("fa-eye-slash");
        toggleIcon.classList.add("fa-eye");  // Change back to eye icon (hiding password)
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

	function enableButton(currentInput) {
	        // Move focus to the next input box
			const button = document.getElementById('otpVerifyBtn');
		    button.disabled = false; 
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
		const button = document.getElementById('otpVerifyBtn');
		button.disabled = true; 
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


function validateAndShowModal() {
        // Show the modal if validation passes
        $('#ModalConfirm').modal('show');
    }

function closePopup(){
	window.location.href = "/dashboard";
	$('#ModalConfirm').modal('hide');
}

document.getElementById('flexCheckDefault').addEventListener('change', function() {
	 const button = document.getElementById('authButton');
	 button.disabled = !this.checked; // Enable button when checkbox is checked
 });
	
function resetErrorMessages(){
	
	const errorFields = [
	          "nameError",
	          "mobileError",
	          "emailError",
	          "hireDateError",
	          "jobTitleError",
	          "departmentError",
	          "reportingError",
	          "salaryError",
	          "locationError",
	          "residenceError"
	      ];

	      errorFields.forEach((field) => {
	          document.getElementById(field).textContent = "";
	      });
	
}