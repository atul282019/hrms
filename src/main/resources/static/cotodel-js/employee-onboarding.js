
function saveEmployeeOnboarding(){
	var employerId=document.getElementById("employerId").value; 
	var employeeId=document.getElementById("employeeId").value;
	var empOrCont = document.getElementById("contractor").value;
	var empOrCont = document.getElementById("employee").value;
	
	var name = document.getElementById("name").value;
	var email = document.getElementById("email").value;
	var mobile = document.getElementById("mobile").value;
	var herDate = document.getElementById("hireDate").value;
	var jobTitle = document.getElementById("jobTitle").value;
	var depratment = document.getElementById("department").value;
	var managerName = document.getElementById("reporting").value;
	var ctc = document.getElementById("salary").value;
	var location = document.getElementById("location").value;
	var residentOfIndia = document.getElementById("residence").value;
	
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
		document.getElementById("mobileError").innerHTML="Please Enter valid mobile Number";
		document.getElementById("mobile").focus();
		return false;
	}
  	else if (!mobile.match(regMobile)) {    
        document.getElementById("mobileError").innerHTML="Please Enter Valid Mobile Number";
        document.getElementById("mobile").focus();
        return false;     
    } else {    
       document.getElementById("mobileError").innerHTML="";
		  
    }         
    if(herDate==""){
		document.getElementById("hireDateError").innerHTML="Please select Date";
		document.getElementById("hireDate").focus();
		return false;
	}else{
		document.getElementById("hireDateError").innerHTML="";
	}
	
	if(jobTitle==""){
		document.getElementById("jobTitleError").innerHTML="Please Enter job Title";
		document.getElementById("jobTitle").focus();
		return false;
	}else{
		document.getElementById("jobTitleError").innerHTML="";
	}
	
	if(depratment==""){
		document.getElementById("departmentError").innerHTML="Please Enter Department";
		document.getElementById("department").focus();
		return false;
	}else{
		document.getElementById("departmentError").innerHTML="";
	}
	
	if(managerName==""){
		document.getElementById("reportingError").innerHTML="Please Select Manager Name";
		document.getElementById("reporting").focus();
		return false;
	}else{
		document.getElementById("reportingError").innerHTML="";
	}
	if(ctc==""){
		document.getElementById("salaryError").innerHTML="Please Enter Salary";
		document.getElementById("salary").focus();
		return false;
	}else{
		document.getElementById("salaryError").innerHTML="";
	}
	
	if(location==""){
		document.getElementById("locationError").innerHTML="Please Select Location";
		document.getElementById("location").focus();
		return false;
	}else{
		document.getElementById("locationError").innerHTML="";
	}
	if(residentOfIndia==""){
		document.getElementById("residenceError").innerHTML="Please Select Residence";
		document.getElementById("residence").focus();
		return false;
	}else{
		document.getElementById("residenceError").innerHTML="";
	}
	var formData = new FormData(employeeOnboarding);
	
	formData.append("employerId",employerId);
	formData.append("employeeId",employeeId);
	
	formData.append("empOrCont",empOrCont);
	formData.append("name",name);
	
	formData.append("email",email);
	formData.append("herDate",herDate);
	
	formData.append("jobTitle",jobTitle);
	formData.append("depratment",depratment);
	
	formData.append("managerName",managerName);
	formData.append("ctc",ctc);
	
	formData.append("location",location);
	formData.append("residentOfIndia",residentOfIndia);
	
	document.getElementById("signinLoader").style.display="flex";
	document.getElementById("empOnboarding").disabled=true;
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/employeeOnboarding",
         data: formData,
         processData: false,
         contentType: false,       		 
           success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 document.getElementById("otsuccmsg").innerHTML="Data Saved Successfully.";
				 document.getElementById("otmsgdiv").style.display="block";
				 document.getElementById("employeeOnboarding").reset();
				 document.getElementById("empOnboarding").disabled=false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 document.getElementById("empOnboarding").disabled=false;
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("otfailmsg").innerHTML="API Gateway not respond. Please try again.";
				 document.getElementById("otfailmsgDiv").style.display="block";
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	
}


function getEmployeeOnboarding() {

	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeOnboarding",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
           // console.log(newData);
            var data1 = jQuery.parseJSON( newData );
			var data2 = data1.data;
			//console.log(newData);
           var table = $('#employeeTable').DataTable( {
	          destroy: true,	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				{ "mData": "id"},
      		    { "mData": "name"},          
      		    { "mData": "depratment"},
      		    { "mData": "jobTitle"},
      		    { "mData": "empOrCont"},
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td align="right"><button class="btn p-0" type="button" data-toggle="canvas" data-target="#bs-canvas-right" aria-expanded="false" aria-controls="bs-canvas-right"   onclick="viewData(this)"><i class="fas fa-ellipsis-v fa-sm"></i></button></td>';
                 }}, 
    		 	]
      		});
      	     		  
    		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}
  

 
 function pupolateDataEmployee() {
	 
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
		$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeOnboarding",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
           // console.log(newData);
            var data1 = jQuery.parseJSON( newData );
            
			var data2 = data1.data;
			//console.log(newData);
			//document.getElementById("empployeecount").innerHTML=data2.length; 
           var table = $('#employee').DataTable( {	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				{ "mData": "id"},
      		    { "mData": "name"},          
      		    { "mData": "depratment"},
      		    { "mData": "jobTitle"},
      		    { "mData": "empOrCont"},
      		  	{ "mData": "id", "render": function (data1, type, row) {
                    return '<td align="right"><button class="btn p-0" type="button" data-toggle="canvas" data-target="#bs-canvas-right" aria-expanded="false" aria-controls="bs-canvas-right"   onclick="viewData(this)"><i class="fas fa-ellipsis-v fa-sm"></i></button></td>';
                 }}, 
    		 	]
      		});
      	     		  
                  },
         error: function(e){
             alert('Error: ' + e);
         }
    });
}
  
  
  function pupolateData(id) {
	 
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
		$.ajax({
		type: "GET",
		url:""+$('#ctx').attr('content')+"/getEmployeeOnboardingById",
        data: {
				"id": id,
				"employerId": employerId,
       		 },
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
           // console.log(newData);
            var data1 = jQuery.parseJSON( newData );
			var data2 = data1.data;
			//console.log(newData);
  			document.getElementById("email").innerHTML=data2.email;
  			document.getElementById("mobile").innerHTML=data2.mobile;
  			document.getElementById("designation").innerHTML=data2.jobTitle;
  			document.getElementById("empname").innerHTML=data2.name;
  			document.getElementById("reportingManager").innerHTML=data2.managerName;
  			document.getElementById("empType").innerHTML=data2.empOrCont;
  			document.getElementById("herDate").innerHTML=data2.herDate;
  			document.getElementById("emailid").innerHTML=data2.email;
  			
           },
         error: function(e){
             alert('Error: ' + e);
         }
    });
}