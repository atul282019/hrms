function validateAmount(amount) {
           // Regex to allow positive numbers with up to 2 decimal places
           const amountRegex = /^[0-9]+(\.[0-9]{1,2})?$/;
           return amountRegex.test(amount);
  }

  function saveEmployeeValidation(){
  	    
  	    var employerId = document.getElementById("employerId").value;
  	    var employeeId = document.getElementById("employeeId").value;

  	    const name = document.getElementById("name").value;
  	    const email = document.getElementById("email").value;
  	    const mobile = document.getElementById("mobile").value;
  	    const hireDate = document.getElementById("hireDate").value;
  	    const jobTitle = document.getElementById("jobTitle").value;
  	    const department = document.getElementById("department").value;
  	    const managerName = document.getElementById("reporting").value;
  	    const ctc = document.getElementById("salary").value;
  	    const location = document.getElementById("location").value;
  	    const residentOfIndia = document.getElementById("residence").value;

  	    const regName = /^[a-zA-Z\s]*$/;
  	    const onlySpace = /^$|.*\S+.*/;
  	    const regMobile = /^[6-9]\d{9}$/gi;
  	    const regEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  	    let namespace = name.replace(/\s+/g, ' ').trim();
  	    let emailspace = email.replace(/\s+/g, ' ').trim();
  	    let jobTitlespace = jobTitle.replace(/\s+/g, ' ').trim();
  	    let departmentspace = department.replace(/\s+/g, ' ').trim();

  	    let empOrCont = null;

  	    if (document.getElementById("employee").checked) {
  	        empOrCont = "Employee";
  	        document.getElementById("empTypeError").textContent = "";
  	    } else if (document.getElementById("contractor").checked) {
  	        empOrCont = "Contractor";
  	        document.getElementById("empTypeError").textContent = "";
  	    } else {
  	        document.getElementById("empTypeError").textContent = "Please select employee type";
  	        return false;
  	    }

  	    if (name === "" || namespace !== name) {
  	        document.getElementById("nameError").textContent = namespace !== name 
  	            ? "Only single spaces are allowed." 
  	            : "Please enter name";
  	        return false;
  	    } else {
  	        document.getElementById("nameError").textContent = "";
  	    }

  	    if (email === "" || !email.match(regEmail) || emailspace !== email) {
  	        document.getElementById("emailError").textContent = 
  	            emailspace !== email 
  	                ? "Only single spaces are allowed."
  	                : !email.match(regEmail) 
  	                    ? "Please enter a valid email" 
  	                    : "Please enter email";
  	        return false;
  	    } else {
  	        document.getElementById("emailError").textContent = "";
  	    }

  	    if (mobile === "" || !mobile.match(regMobile)) {
  	        document.getElementById("mobileError").textContent = 
  	            mobile === "" ? "Please enter valid mobile number" : "Mobile number is invalid.";
  	        return false;
  	    } else {
  	        document.getElementById("mobileError").textContent = "";
  	    }

  	    if (hireDate === "") {
  	        document.getElementById("hireDateError").textContent = "Please select date";
  	        return false;
  	    } else {
  	        document.getElementById("hireDateError").textContent = "";
  	    }

  	    if (jobTitle === "" || jobTitlespace !== jobTitle) {
  	        document.getElementById("jobTitleError").textContent = jobTitlespace !== jobTitle 
  	            ? "Only single spaces are allowed."
  	            : "Please enter job title";
  	        return false;
  	    } else {
  	        document.getElementById("jobTitleError").textContent = "";
  	    }

  	    if (department === "" || departmentspace !== department) {
  	        document.getElementById("departmentError").textContent = departmentspace !== department 
  	            ? "Only single spaces are allowed."
  	            : "Please enter department";
  	        return false;
  	    } else {
  	        document.getElementById("departmentError").textContent = "";
  	    }

  	    if (managerName === "") {
  	        document.getElementById("reportingError").textContent = "Please select manager name";
  	        return false;
  	    } else {
  	        document.getElementById("reportingError").textContent = "";
  	    }

  	    if (ctc === "" || isNaN(ctc) || parseFloat(ctc) <= 0) {
  	        document.getElementById("salaryError").textContent = ctc === "" 
  	            ? "Please enter salary" 
  	            : "Please enter a valid amount greater than zero.";
  	        return false;
  	    } else {
  	        document.getElementById("salaryError").textContent = "";
  	    }

  	    if (location === "") {
  	        document.getElementById("locationError").textContent = "Please select location";
  	        return false;
  	    } else {
  	        document.getElementById("locationError").textContent = "";
  	    }

  	    if (residentOfIndia === "") {
  	        document.getElementById("residenceError").textContent = "Please select residence";
  	        return false;
  	    } else {
  	        document.getElementById("residenceError").textContent = "";
  	    }
}
function saveEmployeeOnboarding(){
	    
	    var employerId = document.getElementById("employerId").value;
	    var employeeId = document.getElementById("employeeId").value;

	    const name = document.getElementById("name").value;
	    const email = document.getElementById("email").value;
	    const mobile = document.getElementById("mobile").value;
	    const hireDate = document.getElementById("hireDate").value;
	    const jobTitle = document.getElementById("jobTitle").value;
	    const department = document.getElementById("department").value;
	    const managerName = document.getElementById("reporting").value;
	    const ctc = document.getElementById("salary").value;
	    const location = document.getElementById("location").value;
	    const residentOfIndia = document.getElementById("residence").value;

	    const regName = /^[a-zA-Z\s]*$/;
	    const onlySpace = /^$|.*\S+.*/;
	    const regMobile = /^[6-9]\d{9}$/gi;
	    const regEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	    let namespace = name.replace(/\s+/g, ' ').trim();
	    let emailspace = email.replace(/\s+/g, ' ').trim();
	    let jobTitlespace = jobTitle.replace(/\s+/g, ' ').trim();
	    let departmentspace = department.replace(/\s+/g, ' ').trim();

	    let empOrCont = null;

	    if (document.getElementById("employee").checked) {
	        empOrCont = "Employee";
	        document.getElementById("empTypeError").textContent = "";
	    } else if (document.getElementById("contractor").checked) {
	        empOrCont = "Contractor";
	        document.getElementById("empTypeError").textContent = "";
	    } else {
	        document.getElementById("empTypeError").textContent = "Please select employee type";
	        return false;
	    }

	    if (name === "" || namespace !== name) {
	        document.getElementById("nameError").textContent = namespace !== name 
	            ? "Only single spaces are allowed." 
	            : "Please enter name";
	        document.getElementById("name").focus();
	        return false;
	    } else {
	        document.getElementById("nameError").textContent = "";
	    }

	    if (email === "" || !email.match(regEmail) || emailspace !== email) {
	        document.getElementById("emailError").textContent = 
	            emailspace !== email 
	                ? "Only single spaces are allowed."
	                : !email.match(regEmail) 
	                    ? "Please enter a valid email" 
	                    : "Please enter email";
	        document.getElementById("email").focus();
	        return false;
	    } else {
	        document.getElementById("emailError").textContent = "";
	    }

	    if (mobile === "" || !mobile.match(regMobile)) {
	        document.getElementById("mobileError").textContent = 
	            mobile === "" ? "Please enter valid mobile number" : "Mobile number is invalid.";
	        document.getElementById("mobile").focus();
	        return false;
	    } else {
	        document.getElementById("mobileError").textContent = "";
	    }

	    if (hireDate === "") {
	        document.getElementById("hireDateError").textContent = "Please select date";
	        document.getElementById("hireDate").focus();
	        return false;
	    } else {
	        document.getElementById("hireDateError").textContent = "";
	    }

	    if (jobTitle === "" || jobTitlespace !== jobTitle) {
	        document.getElementById("jobTitleError").textContent = jobTitlespace !== jobTitle 
	            ? "Only single spaces are allowed."
	            : "Please enter job title";
	        document.getElementById("jobTitle").focus();
	        return false;
	    } else {
	        document.getElementById("jobTitleError").textContent = "";
	    }

	    if (department === "" || departmentspace !== department) {
	        document.getElementById("departmentError").textContent = departmentspace !== department 
	            ? "Only single spaces are allowed."
	            : "Please enter department";
	        document.getElementById("department").focus();
	        return false;
	    } else {
	        document.getElementById("departmentError").textContent = "";
	    }

	    if (managerName === "") {
	        document.getElementById("reportingError").textContent = "Please select manager name";
	        document.getElementById("reporting").focus();
	        return false;
	    } else {
	        document.getElementById("reportingError").textContent = "";
	    }

	    if (ctc === "" || isNaN(ctc) || parseFloat(ctc) <= 0) {
	        document.getElementById("salaryError").textContent = ctc === "" 
	            ? "Please enter salary" 
	            : "Please enter a valid amount greater than zero.";
	        document.getElementById("salary").focus();
	        return false;
	    } else {
	        document.getElementById("salaryError").textContent = "";
	    }

	    if (location === "") {
	        document.getElementById("locationError").textContent = "Please select location";
	        document.getElementById("location").focus();
	        return false;
	    } else {
	        document.getElementById("locationError").textContent = "";
	    }

	    if (residentOfIndia === "") {
	        document.getElementById("residenceError").textContent = "Please select residence";
	        document.getElementById("residence").focus();
	        return false;
	    } else {
	        document.getElementById("residenceError").textContent = "";
	    }

	var formData = new FormData(employeeOnboarding);
	
	formData.append("employerId",employerId);
	formData.append("employeeId",employeeId);
	
	formData.append("empOrCont",empOrCont);
	formData.append("name",name);
	
	formData.append("email",email);
	formData.append("mobile",mobile);
	formData.append("herDate",hireDate);
	
	formData.append("jobTitle",jobTitle);
	formData.append("depratment",department);
	
	formData.append("managerName",managerName);
	formData.append("ctc",ctc);
	
	formData.append("location",location);
	formData.append("residentOfIndia",residentOfIndia);
	
	document.getElementById("signinLoader").style.display="flex";
	document.getElementById("empOnboarding").disabled=true;
	 	$.ajax({
		type: "POST",
	     url:"/employeeOnboarding",
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
		url: "/getEmployeeOnboarding",
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
		url: "/getEmployeeOnboarding",
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
		url:"/getEmployeeOnboardingById",
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