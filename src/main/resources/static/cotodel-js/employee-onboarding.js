
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
				 //document.getElementById("getInTouchUser").reset();
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
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
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			//document.getElementById("signinLoader").style.display="none";
			var tableBody = $('#employeeTable tbody');
            
            
            const orgin =  data1.data;
			
			
			const warehouseQuant = data =>
			  document.getElementById("employeeTableBody").innerHTML = data.map(
			    item => ([
			      '<tr>',
			      ['name','depratment','jobTitle','empOrCont'].map(
			        key => `<td>${item[key]}</td>`
			      ),
			      '</tr>'
			    ])
			  ).flat(Infinity).join('');
  			warehouseQuant(data1.data);
		
            
            
            
            /*$.each(data2, function(index, item) {
                var row = $('<tr>');
              
                for (var key in item) {
                    if (item.hasOwnProperty(key)) {
                        var value = item[key];
                        row.append($('<td>').text(value));
                    }
                }
                tableBody.append(row);
            });
            */
          /*  $.each(data, function(index, item) {
                var row = $('<tr>');
                for (var key in item) {
                    if (item.hasOwnProperty(key)) {
                        var value = item[key];
                        row.append($('<td>').text(value));
                    }
                }
                tableBody.append(row);
            });*/
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}

// Function to populate table with JSON data
        function populateTable(data) {
          
        }
  
