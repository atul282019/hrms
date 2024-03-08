
function getEmployeeProjectDetail() {
	//document.getElementById("overlay").style.display = "flex";
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeProjectDetail",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			var table = $('#projectTable').DataTable({
				 "responsive": true, "lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],"aaSorting": [],
             "language": {"emptyTable": "No History available"  },
				"aaData": data2,
				"aoColumns": [
					{ "mData": "region" },
					{ "mData": "projectName" },
					{ "mData": "roleInProject" },
					{ "mData": "assignFromDate" },
					{ "mData": "assignToDate" },
					{ "mData": "sharingPercentage"},
					{ "mData": "technicalSupport"}
				
				]
			}).buttons().container().appendTo('#certificateTable_wrapper .col-md-6:eq(0)');
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

function getEmployeeCertificateDetail() {
	//document.getElementById("overlay").style.display = "flex";
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeCertificateDetail",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			var table = $('#certificateTable').DataTable({
				 "responsive": true, "lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],"aaSorting": [],
             "language": {"emptyTable": "No History available"  },
				"aaData": data2,
				"aoColumns": [
					{ "mData": "docName" },
					{ "mData": "institutes" },
					{ "mData": "docType" },
					{ "mData": "docNo" },
					{ "mData": "docDate" },
					
					{ "mData": "remarks"},
				
					
				]
			}).buttons().container().appendTo('#certificateTable_wrapper .col-md-6:eq(0)');
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}


function getEmployeeExperienceDetail() {
	//document.getElementById("overlay").style.display = "flex";
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeExperienceDetail",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			var table = $('#experienceTable').DataTable({
				 "responsive": true, "lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],"aaSorting": [],
             "language": {"emptyTable": "No History available"  },
				"aaData": data2,
				"aoColumns": [
					{ "mData": "designation" },
					{ "mData": "company" },
					{ "mData": "fromDate" },
					{ "mData": "toDate" },
					{ "mData": "noOfYear" },
					{ "mData": "company"},
					{ "mData": "country"},
					{ "mData": "referenceEmail"},
					/*{ "mData": "referenceMobile"},*/
				/*	{ "mData": "remarks"}*/
					
				]
			}).buttons().container().appendTo('#experienceTable_wrapper .col-md-6:eq(0)');
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}


function getEmployeeQualificationDetail() {
	//document.getElementById("overlay").style.display = "flex";
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeQualificationDetail",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			var table = $('#qualificationDetail').DataTable({
				 "responsive": true, "lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],"aaSorting": [],
             "language": {"emptyTable": "No History available"  },
				"aaData": data2,
				"aoColumns": [
					{ "mData": "fromDate" },
					{ "mData": "toDate" },
					{ "mData": "education" },
					{ "mData": "institutes" },
					{ "mData": "referenceType" },
					{ "mData": "remarks"}
				]
			}).buttons().container().appendTo('#qualificationDetail_wrapper .col-md-6:eq(0)');
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

function getEmployeeFamilyDetail() {
	//document.getElementById("overlay").style.display = "flex";
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeFamilyDetail",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			var table = $('#empFamilyDetail').DataTable({
				 "responsive": true, "lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],"aaSorting": [],
             "language": {"emptyTable": "No History available"  },
				"aaData": data2,
				"aoColumns": [
					{ "mData": "name" },
					{ "mData": "dob" },
					{ "mData": "relation" },
					{ "mData": "nominee" },
					{ "mData": "insuranceNo" },
					{ "mData": "mobile" },
					{ "mData": "email" }
				]
			}).buttons().container().appendTo('#empFamilyDetail_wrapper .col-md-6:eq(0)');
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}


function getEmployeeDetail() {
	var employerId = document.getElementById("employerId").value;  
	document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeDetail",
		data: {
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			var table = $('#employeeDetail').DataTable({
				 "responsive": true, "lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],"aaSorting": [],
             "language": {"emptyTable": "No History available"  },
				"aaData": data2,
				"aoColumns": [
					{ "mData": "firstName" },
					{ "mData": "nationality" },
					{ "mData": "dateOfBirth" },
					{ "mData": "dateOfJoining" },
					{ "mData": "gender" },
					{ "mData": "panNo" },
					{ "mData": "accountNumber" },
					{ "mData": "uanNo" },
					{ "mData": "bankName" }
				]
			}).buttons().container().appendTo('#employeeDetail_wrapper .col-md-6:eq(0)');
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

function saveEmployeeProject(){
	var employerId=document.getElementById("employerId").value; 
	var employeeId=document.getElementById("employeeId").value;
	var formData = new FormData(projectForm);
	formData.append("employerId",employerId);
	formData.append("employeeId",employeeId);
	document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveEmployeeProject",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			//console.log(data1)
			if(data1.status==true){
				 document.getElementById("certificatesuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("certificatemsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 $('#certificatemsgdiv').delay(5000).fadeOut(400);
				 getEmployeeProjectDetail();
			}else if(data1.status==false){
				 document.getElementById("certificatefailmsg").innerHTML=data1.message;
				 document.getElementById("certificatefailmsgDiv").style.display="block";
				 $('#certificatefailmsgDiv').delay(5000).fadeOut(400);
				 getEmployeeProjectDetail();
			}else{
				 document.getElementById("certificatemsgdiv").style.display="none";
				 document.getElementById("certificatefailmsgDiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
}

function saveEmployeeCertificate(){
	var employerId=document.getElementById("employerId").value;  
	var employeeId=document.getElementById("employeeId").value;
	var formData = new FormData(certificateForm);
	formData.append("employerId",employerId);
	formData.append("employeeId",employeeId);
	document.getElementById("signinLoader").style.display="none";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveEmployeeCertificate",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			//console.log(data1)
			if(data1.status==true){
				 document.getElementById("certificatesuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("certificatemsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 $('#certificatemsgdiv').delay(5000).fadeOut(400);
				 getEmployeeCertificateDetail();
			}else if(data1.status==false){
				 document.getElementById("certificatefailmsg").innerHTML=data1.message;
				 document.getElementById("certificatefailmsgDiv").style.display="block";
				 $('#certificatefailmsgDiv').delay(5000).fadeOut(400);
				 getEmployeeCertificateDetail();
			}else{
				 document.getElementById("certificatemsgdiv").style.display="none";
				 document.getElementById("certificatefailmsgDiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
}

function saveEmployeeExperience(){
	var employerId=document.getElementById("employerId").value;
	var employeeId=document.getElementById("employeeId").value;
	
	var formData = new FormData(experience);
	formData.append("employerId",employerId);
	formData.append("employeeId",employeeId);
	document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveEmployeeExperience",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			//console.log(data1)
			if(data1.status==true){
				 document.getElementById("experiencesuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("experiencemsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 getEmployeeExperienceDetail();
				 $('#experiencemsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("experiencefailmsg").innerHTML=data1.message;
				 document.getElementById("experiencefailmsgDiv").style.display="block";
				 $('#experiencefailmsgDiv').delay(5000).fadeOut(400);
				 getEmployeeExperienceDetail();
			}else{
				 document.getElementById("experiencemsgdiv").style.display="none";
				 document.getElementById("experiencefailmsgDiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });				
}

function saveQualificationDetail(){
	
	var employerId=document.getElementById("employerId").value; 
	var employeeId=document.getElementById("employeeId").value; 
	var formData = new FormData(qualification);
	formData.append("employerId",employerId);
	formData.append("employeeId",employeeId);
	document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveQualificationDetail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			//console.log(data1)
			if(data1.status==true){
				 document.getElementById("qualificationsuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("qualificationmsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 getEmployeeQualificationDetail();
				 $('#qualificationmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("qualificationfailmsg").innerHTML=data1.message;
				 document.getElementById("qualificationfailmsgDiv").style.display="block";
				 $('#qualificationfailmsgDiv').delay(5000).fadeOut(400);
				 getEmployeeQualificationDetail();
			}else{
				 document.getElementById("qualificationmsgdiv").style.display="none";
				 document.getElementById("qualificationfailmsgDiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });				
}

function SaveFmailyDetail(){
	
	var employerId=document.getElementById("employerId").value;
	var employeeId=document.getElementById("employeeId").value;
	var formData = new FormData(employeeFamilyForm);
	formData.append("employerId",employerId);
	formData.append("employeeId",employeeId);
	document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveFamilyDetail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			//console.log(data1)
			if(data1.status==true){
				 document.getElementById("empfamilysuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("empfamilymsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 getEmployeeFamilyDetail();
				 $('#empmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("empfamilyfailmsg").innerHTML=data1.message;
				 document.getElementById("empfamilyfailmsgDiv").style.display="block";
				 $('#empfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("empfamilymsgdiv").style.display="none";
				 document.getElementById("empfamilyfailmsgDiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });				
}


function validateEmployeeAndSubmit(){
	
	/* var firstName = document.getElementById("firstName").value;
	 var lastName = document.getElementById("lastName").value;
	 var dateOfBirth = document.getElementById("dateOfBirth").value; 
	 var gender = document.getElementById("gender").value; 
	 
	 var Mobile = document.getElementById("Mobile").value; 
     var picUrl = document.getElementById("picUrl").value; 
	 var email = document.getElementById("email").value; 
	 var address = document.getElementById("address").value; 
	 
	 var roleId = document.getElementById("roleId").value;
	 var username = document.getElementById("username").value;
	       
	 var pwd = document.getElementById("pwd").value; 
	 var bankAccount = document.getElementById("bankAccount").value; 
	 var ifsc = document.getElementById("ifsc").value;  
	 var pan = document.getElementById("pan").value;  
	 var urn = document.getElementById("urn").value;  
	 var aadhaar = document.getElementById("aadhaar").value;  

	 var dateOfJoining = document.getElementById("dateOfJoining").value;  
	 var designation = document.getElementById("designation").value;
	 var department = document.getElementById("department").value;   
	 var salaryType = document.getElementById("salaryType").value;
	 var salaryAmount = document.getElementById("salaryAmount").value;   
	 */

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
	var employerId = document.getElementById("employerId").value;  
	var formData = new FormData(empdetailForm);
	formData.append("employerId",employerId);
	document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveEmployeeDetail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			console.log(data1.data.employerId);
			console.log(data1.data.id);
			if(data1.status==true){
				 document.getElementById("employeeId").value=data1.data.id;
				 document.getElementById("empsuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("empmsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 getEmployeeDetail();
				 $('#empmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				getEmployeeDetail();
				 document.getElementById("empfailmsg").innerHTML=data1.message;
				 document.getElementById("empfailmsgDiv").style.display="block";
				 $('#empfailmsgDiv').delay(5000).fadeOut(400);
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
			//console.log(data1)
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