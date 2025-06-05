let profilePhoto="";
function validateAmount(amount) {
           // Regex to allow positive numbers with up to 2 decimal places
           const amountRegex = /^[0-9]+(\.[0-9]{1,2})?$/;
           return amountRegex.test(amount);
  }
  function resetErrorMessages() {
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
  function saveEmployeeValidation(){
  	    
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
		const regex_alphanumeric=/^[a-zA-Z0-9]+$/;

  	    let namespace = name.replace(/\s+/g, ' ').trim();
  	    let emailspace = email.replace(/\s+/g, ' ').trim();
  	    let jobTitlespace = jobTitle.replace(/\s+/g, ' ').trim();
  	    let departmentspace = department.replace(/\s+/g, ' ').trim();

		const employeeType = document.getElementById("employeeType").value;
		const employeeTypeText = document.getElementById("employeeType").text;
				
	    if (employeeType==1) {
			
			if (name === "" || namespace !== name) {
	        document.getElementById("nameError").textContent = namespace !== name 
	            ? "Only single spaces are allowed." 
		            : "Please enter name";
		        //document.getElementById("name").focus();
		        return false;
		    }
			else if(!regName.test(name))
			{
				document.getElementById("nameError").textContent="Only Alphabets are allowed in Name";
				return false;
			} 
					 else {
		        document.getElementById("nameError").textContent = "";
		    }
			if (mobile === "" || !mobile.match(regMobile)) {
			        document.getElementById("mobileError").textContent = 
			            mobile === "" ? "Please enter valid mobile number" : "Mobile number is invalid.";
			      //  document.getElementById("mobile").focus();
			        return false;
			    } else {
			        document.getElementById("mobileError").textContent = "";
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

		 

		    if (hireDate === "") {
		        document.getElementById("hireDateError").textContent = "Please select date";
		        return false;
		    } else {
		        document.getElementById("hireDateError").textContent = "";
		    }

		    /*if (jobTitle === "" || jobTitlespace !== jobTitle) {
		        document.getElementById("jobTitleError").textContent = jobTitlespace !== jobTitle 
		            ? "Only single spaces are allowed."
		            : "Please enter job title";
		        return false;
		    }*/
			if (jobTitle === "" ) 
			{
		        document.getElementById("jobTitleError").textContent = "Please enter job title";
		        return false;
			}
			else if (!regName.test(jobTitle)){
				document.getElementById("jobTitleError").textContent = "Only Alphabets Are Allowed in jobTitle";
				return false;
				
			} else {
		        document.getElementById("jobTitleError").textContent = "";
		    }

		    /*if (department === "" || departmentspace !== department) {
		        document.getElementById("departmentError").textContent = departmentspace !== department 
		            ? "Only single spaces are allowed."
		            : "Please enter department";
		        return false;
		    }*/
			if (department === "" ) 
			{
			        document.getElementById("departmentError").textContent = "Please select department";
			        return false;
		    }	 			
			/*else if (!regName.test(department)){
							document.getElementById("departmentError").textContent = "Only Alpha Numeric Values Are Allowed in Department";
							return false;
							
								}*/
			
			else {
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
	   else if (employeeType==2 || employeeType==3 || employeeType==4) {

		if (name === "" || namespace !== name) {
		    document.getElementById("nameError").textContent = namespace !== name 
		        ? "Only single spaces are allowed." 
		        : "Please enter name";
		    return false;
		}
		else if(!regName.test(name))
		{
			document.getElementById("nameError").textContent="Only Alphabets are allowed in Name";
			return false;
		} 
		 else {
    		document.getElementById("nameError").textContent = "";
		}
		if (mobile === "" || !mobile.match(regMobile)) {
		        document.getElementById("mobileError").textContent = 
		            mobile === "" ? "Please enter valid mobile number" : "Mobile number is invalid.";
		        return false;
		    } else {
		        document.getElementById("mobileError").textContent = "";
		    }
			
			
			if (email === "" || !email.match(regEmail) || emailspace !== email) 
			{
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
	     
	    } else {
	        document.getElementById("empTypeError").textContent = "Please select employee type";
	        return false;
	    }
}
async function saveEmployeeOnboarding(){
	    
	    var employerId = document.getElementById("employerId").value;
	    var employeeId = document.getElementById("employeeId").value;

	    const name = document.getElementById("name").value;
	    const email = document.getElementById("email").value;
	    const mobile = document.getElementById("mobile").value;
	    const hireDate = document.getElementById("hireDate").value;
	    const jobTitle = document.getElementById("jobTitle").value;
		const departmentDropdown = document.getElementById("department");
		var department = departmentDropdown.options[departmentDropdown.selectedIndex].text;
	    const reporting = document.getElementById("reporting");
		const managerName1 = reporting.options[reporting.selectedIndex].text;
		const managerName = reporting.options[reporting.selectedIndex].value;
	    const ctc = document.getElementById("salary").value;
	    const locationDropdown = document.getElementById("location");
		const selectedLocation = locationDropdown.options[locationDropdown.selectedIndex].text; 
	    const residentOfIndia = document.getElementById("residence").value;
		const Id = document.getElementById("Id").value;
		const profilePhotoBase64 = document.getElementById("profilePhotoBase64").value;
		
	    const regName = /^[a-zA-Z\s]*$/;
	    const onlySpace = /^$|.*\S+.*/;
	    const regMobile = /^[6-9]\d{9}$/gi;
	    const regEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		const regex_alphanumeric=/^[a-zA-Z0-9]+$/;

	    let namespace = name.replace(/\s+/g, ' ').trim();
	    let emailspace = email.replace(/\s+/g, ' ').trim();
	    let jobTitlespace = jobTitle.replace(/\s+/g, ' ').trim();
	    let departmentspace = department.replace(/\s+/g, ' ').trim();

	   
		const employeeType = document.getElementById("employeeType").value;
		
		const dropdown = document.getElementById("employeeType");
		const emporcount = dropdown.options[dropdown.selectedIndex].text;
		  
	    if (employeeType==1) {	
			if (name === "" || namespace !== name) {
	        document.getElementById("nameError").textContent = namespace !== name 
	            ? "Only single spaces are allowed." 
		            : "Please enter name";
		        document.getElementById("name").focus();
		        return false;
		    }
			else if(!regName.test(name))
			{
				document.getElementById("nameError").textContent="Only Alphabets are allowed in Name";
				return false;
			} 
			 else {
		        document.getElementById("nameError").textContent = "";
		    }
			if (mobile === "" || !mobile.match(regMobile)) {
			        document.getElementById("mobileError").textContent = 
			            mobile === "" ? "Please enter valid mobile number" : "Mobile number is invalid.";
			        document.getElementById("mobile").focus();
			        return false;
			    } else {
			        document.getElementById("mobileError").textContent = "";
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
		    if (hireDate === "") {
		        document.getElementById("hireDateError").textContent = "Please select date";
		        document.getElementById("hireDate").focus();
		        return false;
		    } else {
		        document.getElementById("hireDateError").textContent = "";
		    }

				/*if (jobTitle === "" || jobTitlespace !== jobTitle) {
		        document.getElementById("jobTitleError").textContent = jobTitlespace !== jobTitle 
		            ? "Only single spaces are allowed."
		            : "Please enter job title";
		        return false;
			    }*/
				if (jobTitle === "" ) 
				{
			        document.getElementById("jobTitleError").textContent = "Please enter job title";
			        return false;
				}
				else if (!regName.test(jobTitle)){
					document.getElementById("jobTitleError").textContent = "Only Alphabets Are Allowed in jobTitle";
					return false;
					
				} else {
			        document.getElementById("jobTitleError").textContent = "";
			    }

			    /*if (department === "" || departmentspace !== department) {
			        document.getElementById("departmentError").textContent = departmentspace !== department 
			            ? "Only single spaces are allowed."
			            : "Please enter department";
			        return false;
			    }*/
				if (department === "" ) 
				{
			        document.getElementById("departmentError").textContent = "Please select department";
			        return false;
			    }	  			
				/*else if (!regName.test(department)){
				document.getElementById("departmentError").textContent = "Only Alpha Numeric Values Are Allowed in Department";
				return false;
				
					}*/
				
				else {
			        document.getElementById("departmentError").textContent = "";
			    }

		    if (managerName === "0") {
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
	    }
	   else if (employeeType==2 || employeeType==3 || employeeType==4) {
		department=document.getElementById("organizationName").value;
		if (name === "" || namespace !== name) {
		    document.getElementById("nameError").textContent = namespace !== name 
		        ? "Only single spaces are allowed." 
		        : "Please enter name";
		    document.getElementById("name").focus();
		    return false;
		}		
		else if(!regName.test(name))
		{
			document.getElementById("nameError").textContent="Only Alphabets are allowed in Name";
			return false;
		} 
		 else {
		    document.getElementById("nameError").textContent = "";
		}
		if (mobile === "" || !mobile.match(regMobile)) {
		        document.getElementById("mobileError").textContent = 
		            mobile === "" ? "Please enter valid mobile number" : "Mobile number is invalid.";
		        document.getElementById("mobile").focus();
		        return false;
		    } else {
		        document.getElementById("mobileError").textContent = "";
		    }
			
			if (email === "" || !email.match(regEmail) || emailspace !== email) 
				{
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
	     
	    } else {
	        document.getElementById("empTypeError").textContent = "Please select employee type";
	        return false;
	    }
		console.log("profile photo in base 64 before check",profilePhoto);
			if(profilePhoto=="")
			{
				profilePhoto=profilePhotoBase64;
			}
			
	console.log("profile photo in base 64 after check",profilePhoto);
	var filetype = document.getElementById("profilePhotoType").value;
	var filename = document.getElementById("filename").value;
	const clientKey = "client-secret-key"; // Extra security measure
    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
    const dataString = Id+employerId+employeeId+name+email+mobile+hireDate+jobTitle+department
	+ctc+selectedLocation+residentOfIndia+emporcount+profilePhoto+filetype+filename+managerName+managerName1+clientKey+secretKey;

    // Generate SHA-256 hash
    const encoder = new TextEncoder();
    const data = encoder.encode(dataString);
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

    // Prepare request payload
    const requestData = {
		Id:Id,
		employerId:employerId,
		employeeId:employeeId,
		name:name,
		email:email,
		mobile:mobile,
		herDate:hireDate,
		jobTitle:jobTitle,
		depratment:department,
		managerId:managerName,
		managerName:managerName1,
		ctc:ctc,
		location:selectedLocation,
		residentOfIndia:residentOfIndia,
		empOrCont:emporcount,
		empPhoto:profilePhoto,
		filetype:filetype,
		filename:filename,
        clientKey: clientKey,  // Extra key for validation
        hash: hashHex
    };

	document.getElementById("signinLoader").style.display="flex";
	document.getElementById("empOnboarding").disabled=true;
	 	$.ajax({
		type: "POST",
	     url:"/employeeRegistration",
		 contentType: "application/json",
		 data: JSON.stringify(requestData),
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
				 setTimeout(() => {
				             window.location.href="/manageEmployee";
				         }, 400);
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


async function getEmployeeOnboarding() {
	document.getElementById("signinLoader").style.display="flex";
    var employeeId = document.getElementById("employeeId").value;
    var employerId = document.getElementById("employerId").value;
    
	const clientKey = "client-secret-key"; // Extra security measure
    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
    const dataString = employerId+employeeId+clientKey+secretKey;

    // Generate SHA-256 hash
    const encoder = new TextEncoder();
    const data = encoder.encode(dataString);
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	const requestData = {
			employerId:employerId,
			employeeId:employeeId,
	        key: clientKey,  // Extra key for validation
	        hash: hashHex
	    };
    $.ajax({
        type: "POST",
        url: "/getEmployeeOnboarding",
		contentType: "application/json",
		data: JSON.stringify(requestData),
        beforeSend: function(xhr) {
            //xhr.setRequestHeader(header, token);
        },
        success: function(data) {
			document.getElementById("signinLoader").style.display="none";
            newData = data;
            console.log("Emp onboarding data", newData);
            var data1 = jQuery.parseJSON(newData);
            var data2 = data1.data;
            
            // Filter employees with status 1 (Active)
            var filteredData = data2.filter(function(employee) {
                return employee.status === 1;
            });
            
            var table = $('#employeeTable').DataTable({
                destroy: true,
                "responsive": true,
                searching: false,
                bInfo: false,
                paging: false,
                "lengthChange": true,
                "autoWidth": false,
                "pagingType": "full_numbers",
                "pageLength": 50,
                "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
                "language": {"emptyTable": "No Active Employees Found"},
                
                // Use the filtered data instead of original data
                "aaData": filteredData,
                "aoColumns": [
                    { "mData": null, "render": function(data, type, row, meta) { return meta.row + 1; } },
                    { "mData": "id", "render": function(data1, type, row) {
                        return '<input type="hidden" class="form-input" id="id" name="id" value="' + data1 + '">';
                    }},
                    { "mData": "userDetailsId", "render": function(data1, type, row) {
                        return '<input type="hidden" class="form-input" id="userDetailsId" name="userDetailsId" value="' + data1 + '">';
                    }},
                    { "mData": "name" },
                    { "mData": "mobile" },
                    { "mData": "email" },
                    { "mData": "empOrCont" },
                    { "mData": "status", "render": function(data, type, row) {
                        return 'Active'; // Since we're only showing Active employees
                    }},
                    { "mData": "userDetailsId", "render": function(data1, type, row) {
                        return '<td align="right"><button class="btn p-0" type="button" data-toggle="canvas" data-target="#bs-canvas-right" aria-expanded="false" aria-controls="bs-canvas-right" onclick="viewData(this)" title="Profile"><i class="fas fa-ellipsis-v fa-sm"></i></button></td>';
                    }},
                ]
            });
        },
        error: function(e) {
            alert('Failed to fetch JSON data' + e);
        }
    });
}
  

 
 async function pupolateDataEmployee() {
	 
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
	const clientKey = "client-secret-key"; // Extra security measure
    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
    const dataString = employerId+employeeId+clientKey+secretKey;

    // Generate SHA-256 hash
    const encoder = new TextEncoder();
    const data = encoder.encode(dataString);
    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	const requestData = {
			employerId:employerId,
			employeeId:employeeId,
	        key: clientKey,  // Extra key for validation
	        hash: hashHex
	    };
		$.ajax({
		type: "POST",
		url: "/getEmployeeOnboarding",
		contentType: "application/json",
					data: JSON.stringify(requestData),
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
           var table = $('#getEmployeeMasterList').DataTable( {	
		     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
             "language": {"emptyTable": "No Data available"  },
	        
	         "aaData": data2,
      		  "aoColumns": [ 
				{ "mData": "id"},
				{ "mData": "userDetailsId"},
      		    { "mData": "name"},          
      		    { "mData": "mobile"},
      		    { "mData": "jobTitle"},
      		    { "mData": "empOrCont"},
      		  	{ "mData": "userDetailsId", "render": function (data1, type, row) {
                    return '<td align="right"><button class="btn p-0" type="button" data-toggle="canvas" data-target="#bs-canvas-right" aria-expanded="false" aria-controls="bs-canvas-right" onclick="viewData(this)" title="Profile"><i class="fas fa-ellipsis-v fa-sm"></i></button></td>';
                 }}, 
    		 	]
      		});
      	     		  
                  },
         error: function(e){
             alert('Error: ' + e);
         }
    });
}
  var empid="";
  var userDetailsId="";
  function settingEmpMngrId()
  {
	sessionStorage.setItem("employeeId",empid);
	sessionStorage.setItem("userDetailsId",userDetailsId);
  }
  
 async function pupolateData(id,userDetailsid) {
	 empid=id;
	 userDetailsId=userDetailsid;
	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
	
	    const clientKey = "client-secret-key"; // Extra security measure
	    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

	    // Concatenate data (must match backend)
	    const dataString = id+employerId+clientKey+secretKey;

	    // Generate SHA-256 hash
	    const encoder = new TextEncoder();
	    const data = encoder.encode(dataString);
	    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	    const hashArray = Array.from(new Uint8Array(hashBuffer));
	    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
		const requestData = {
				id:id,
				employerId:employerId,
		        key: clientKey,  // Extra key for validation
		        hash: hashHex
		    };
			
		$.ajax({
		type: "POST",
		url:"/getEmployeeOnboardingById",
		contentType: "application/json",
							data: JSON.stringify(requestData),
       		  beforeSend : function(xhr) {
				//xhr.setRequestHeader(header, token);
				},
            success: function(data){
            newData = data;
            console.log("new Data=========="+newData);
            var data1 = jQuery.parseJSON( newData );
			console.log("data1=========="+data1);
			var data2 = data1.data;
			console.log("data2=========="+data2);
  			document.getElementById("email").innerHTML=data2.email;
  			document.getElementById("mobile").innerHTML=data2.mobile;
  			document.getElementById("designation").innerHTML=data2.jobTitle;
  			document.getElementById("empname").innerHTML=data2.name;
  			document.getElementById("reportingManager").innerHTML=data2.managerName || 'N/A';
  			document.getElementById("empType").innerHTML=data2.empOrCont;
  			document.getElementById("herDate").innerHTML=data2.herDate;
  			document.getElementById("emailid").innerHTML=data2.email;
			document.getElementById("location").innerHTML=data2.location;
			
			
			var profileImageBase64 = data2.empPhoto; // Assuming API returns this field
			            var defaultImage = "img/no-image-available.png"; // Default profile picture

						if (profileImageBase64) {
						    let profileImg = document.querySelector(".bs-canvas-top-img img");
						    profileImg.src = "data:image/png;base64," + profileImageBase64;
						    profileImg.style.width = "100px";
						    profileImg.style.height = "100px";
						    profileImg.style.objectFit = "cover"; // Ensures no distortion
						    //profileImg.style.borderRadius = "50%"; // Optional for a circular look
						} else {
						    document.querySelector(".bs-canvas-top-img img").src = defaultImage;
						}
			
			settingEmpMngrId();
           },
         error: function(e){
             alert('Error: ' + e);
         }
    });
}

function validateAndConvertImageToBase64() {
        const fileInput = document.getElementById('profilePhoto');
        const file = fileInput.files[0];

        // Validate file input
        /*if (!file) {
            alert('No file selected. Please choose a file.');
           return;
        }*/ 
		const filetype = file.type;
	    const filename = file.name; // fix here
	    document.getElementById("profilePhotoType").value = filetype;
	    document.getElementById("filename").value = filename;
		
        const allowedTypes = ['image/jpeg', 'image/png'];
        if (!allowedTypes.includes(file.type)) {
            alert('Invalid file type. Please upload a JPG or PNG image.');
            fileInput.value = ''; // Clear the input
            return;
        }

        // Convert to Base64
        const reader = new FileReader();
        reader.onload = function(event) {
            const base64String = event.target.result.split(',')[1];
			console.log(base64String);
			profilePhoto=base64String;
			
        };
        reader.onerror = function() {
            console.log('Error reading file.');
        };
        reader.readAsDataURL(file);
    }
	
	async function getDeactiveEmployees() {
		document.getElementById("signinLoader").style.display="flex";
	    var employeeId = document.getElementById("employeeId").value;
	    var employerId = document.getElementById("employerId").value;
		const clientKey = "client-secret-key"; // Extra security measure
		    const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

		    // Concatenate data (must match backend)
		    const dataString = employerId+employeeId+clientKey+secretKey;

		    // Generate SHA-256 hash
		    const encoder = new TextEncoder();
		    const data = encoder.encode(dataString);
		    const hashBuffer = await crypto.subtle.digest("SHA-256", data);
		    const hashArray = Array.from(new Uint8Array(hashBuffer));
		    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
			const requestData = {
					employerId:employerId,
					employeeId:employeeId,
			        key: clientKey,  // Extra key for validation
			        hash: hashHex
			    };
	    $.ajax({
	        type: "POST",
	        url: "/getEmployeeOnboarding",
			contentType: "application/json",
			data: JSON.stringify(requestData),
	        beforeSend: function(xhr) {
	            //xhr.setRequestHeader(header, token);
	        },
	        success: function(data) {
				document.getElementById("signinLoader").style.display="none";
	            newData = data;
	            console.log("Emp onboarding data", newData);
	            var data1 = jQuery.parseJSON(newData);
	            var data2 = data1.data;
	            
	            // Filter only deactive employees (status = 0)
	            var filteredData = data2.filter(function(employee) {
	                return employee.status === 0;
	            });
	            
	            var table = $('#employeeTable').DataTable({
	                destroy: true,
	                "responsive": true,
	                searching: false,
	                bInfo: false,
	                paging: false,
	                "lengthChange": true,
	                "autoWidth": false,
	                "pagingType": "full_numbers",
	                "pageLength": 50,
	                "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
	                "language": {"emptyTable": "No Deactive Employees Found"},
	                
	                "aaData": filteredData,
	                "aoColumns": [
	                    { "mData": null, "render": function(data, type, row, meta) { return meta.row + 1; } },
	                    { "mData": "id", "render": function(data1, type, row) {
	                        return '<input type="hidden" class="form-input" id="id" name="id" value="' + data1 + '">';
	                    }},
	                    { "mData": "userDetailsId", "render": function(data1, type, row) {
	                        return '<input type="hidden" class="form-input" id="userDetailsId" name="userDetailsId" value="' + data1 + '">';
	                    }},
	                    { "mData": "name" },
	                    { "mData": "mobile" },
	                    { "mData": "email" },
	                    { "mData": "empOrCont" },
	                    { "mData": "status", "render": function(data, type, row) {
	                        return 'Deactive'; // All employees in this table are deactive
	                    }},
	                    { "mData": null, "render": function(data, type, row) {
	                        // Show only Activate option for these deactive employees
	                        return '<div class="dropdown">' +
	                               '<button class="btn p-0" type="button" data-toggle="dropdown" aria-expanded="false" title="Options">' +
	                               '<i class="fas fa-ellipsis-v fa-sm"></i></button>' +
	                               '<div class="dropdown-menu dropdown-menu-right">' +
	                               
	                               '<a class="dropdown-item " href="#" onclick="activateEmployee(' + row.id + ', ' + row.userDetailsId + ')">Activate</a>' +
	                               '</div></div>';
	                    }}
	                ]
	            });
	        },
	        error: function(e) {
	            alert('Failed to fetch JSON data' + e);
	        }
	    });
	}

	// Function specifically for activating employees
	function activateEmployee(id, userDetailsId) {
	    const employerId = document.getElementById("employerId").value;
	    
	    if (confirm("Are you sure you want to activate this employee?")) {
	        $.ajax({
	            type: "POST",
	            url: "/toggleEmployee", // Endpoint for activation
	            data: {
	                "employerId": employerId,
	                "id": id,
	                "userDetailsId": userDetailsId,
	                "status": "Active" // Or use 1 if your backend expects numeric status
	            },
	            dataType: "json",
	            success: function(response) {
					console.log("activateEmployee ",response);
					if(response.status==true)
	                {alert("Employee activated successfully!");
	                //getDeactiveEmployees(); // Refresh the table of deactive employees
					window.location.href="/manageEmployee";}
	            },
	            error: function(error) {
	                alert("Error activating employee: " + error.responseText);
	            }
	        });
	    }
	}