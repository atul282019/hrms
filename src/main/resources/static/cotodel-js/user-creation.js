function newUserCreation(){ 
	var username =document.getElementById("username").value;
	var email = document.getElementById("email").value;
	var email = email.toLowerCase();
	var mobile = document.getElementById("mobile").value;
	//var orgname = document.getElementById("orgname").value;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	
	var status = document.getElementById("status").value;
	
	
	if(username==""){
		document.getElementById("usernameError1").innerHTML="Please Enter Name";
		document.getElementById("username").focus();
		return false;
	}else{
		document.getElementById("usernameError1").innerHTML="";
	}
	if(email==""){
		document.getElementById("emailError1").innerHTML="Please Enter Email";
		document.getElementById("email").focus();
		return false;
	}else if(!email.match(regEmail)){
		document.getElementById("emailError1").innerHTML="Please Enter valid Email";
		document.getElementById("email").focus();
		return false;
	}else{
		document.getElementById("emailError1").innerHTML="";
	}
	
	if(mobile==""){
		document.getElementById("mobileError1").innerHTML="Please Enter Mobile Number";
		document.getElementById("mobile").focus();
		return false;
	}else if(!mobile.match(regMobile)){
		document.getElementById("mobileError1").innerHTML="Please Enter Valid Mobile Number";
		document.getElementById("mobile").focus();
		return false;
	}else{
		document.getElementById("mobileError1").innerHTML="";
	}
	if(status==false){
		document.getElementById("statusError1").innerHTML="Please Checked Status";
		document.getElementById("status").focus();
		return false;
	}else{
		document.getElementById("statusError1").innerHTML="";
	}
	
	document.getElementById("signinLoader").style.display="flex";
    var formData = new FormData(registerUser);
	 	$.ajax({
		type: "POST",
	     url:"/singleUserCreation",
	     //url:"/registerUser",
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
				 document.getElementById("registerUser").reset();
				 $('#successmsgdiv').delay(5000).fadeOut(400);
				 getUserDetails();
				
			}else if(data1.status==false){
				 document.getElementById("failmsg").innerHTML=data1.message;
				 document.getElementById("failmsgDiv").style.display="block";
				 $('#failmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("failmsg").innerHTML="API Gateway not respond. Please try again.";
				 document.getElementById("failmsgDiv").style.display="block";
				 $('#failmsgDiv').delay(5000).fadeOut(400);
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
 }
 
 
 function getUserDetails() {
	var employerId=document.getElementById("employerId").value;
	$.ajax({
		type: "GET",
		url:"/getUserList",
		data: {
			"employerId":employerId
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			document.getElementById("signinLoader").style.display="none";
			var table = $('#userTable').DataTable({
				  destroy: true,
				 "responsive": true, "lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
            	 "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],"aaSorting": [],
	             "language": {"emptyTable": "No History available"  },
					"aaData": data2,
					"aoColumns": [
						//{ "mData": "id" },
						{ "mData": "username" },
						{ "mData": "email" },
						{ "mData": "mobile" },
						{ "mData": "status" }
					
					]
				});
			},
			error: function(e) {
				alert('Error: ' + e);
			}
		});
}
 