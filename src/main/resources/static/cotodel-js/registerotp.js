
 function validateRegistrationForm(){
	var name =document.getElementById("username").value;
	var email = document.getElementById("email").value;
	var email = email.toLowerCase();
	var mobile = document.getElementById("mobile").value;
	var orgid = document.getElementById("orgname").value;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	var noofEmp = document.getElementById("noofEmp").value;
	var privacyCheck = document.getElementById("privacyCheck").checked;
	var whatsupCheck = document.getElementById("whatsupCheck").checked;
	
	if(name==""){
		document.getElementById("usernameError1").innerHTML="Please Enter Name";
		document.getElementById("username").focus();
		return false;
	}else{
		document.getElementById("usernameError1").innerHTML="";
	}
	if(orgid==""){
		document.getElementById("orgnameError1").innerHTML="Please Enter Organization Name";
		document.getElementById("orgname").focus();
		return false;
	}else{
		document.getElementById("orgnameError1").innerHTML="";
	}
	if(email==""){
		document.getElementById("emailError1").innerHTML="Please Enter Email";
		document.getElementById("email").focus();
		return false;
	}else if(!email.match(regEmail)){
		document.getElementById("emailError1").innerHTML="Please Enter Valid Email";
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
	if(noofEmp==""){
		document.getElementById("noofEmpError1").innerHTML="Please Select No of Employee";
		document.getElementById("noofEmp").focus();
		return false;
	}else{
		document.getElementById("noofEmpError1").innerHTML="";
	}
	if(privacyCheck==false){
		document.getElementById("privacyCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("privacyCheck").focus();
		return false;
	}else{
		document.getElementById("privacyCheckError1").innerHTML="";
	}
	if(whatsupCheck==false){
		document.getElementById("whatsupCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("whatsupCheck").focus();
		return false;
	}else{
		document.getElementById("whatsupCheckError1").innerHTML="";
		return true;
	}
	
}	

function validateemail()  {  
	var x=document.getElementById("email").value;  
	var atposition=x.indexOf("@");  
	var dotposition=x.lastIndexOf(".");  
	if (atposition<1 || dotposition<atposition+2 || dotposition+2>=x.length){   
  		document.getElementById("emailError").innerHTML="Please Enter Valid E-mail Address";
  		return false;  
  	}else{
		document.getElementById("emailError").innerHTML="";
 		return true;  
	}
}


function validateemailForUser()  {  
	var x=document.getElementById("emailu").value;  
	var atposition=x.indexOf("@");  
	var dotposition=x.lastIndexOf(".");  
	if (atposition<1 || dotposition<atposition+2 || dotposition+2>=x.length){   
  		document.getElementById("emailError1").innerHTML="Please Enter Valid E-mail Address";
  		return false;  
  	}else{
		document.getElementById("emailError1").innerHTML="";
 		return true;  
	}
}

function userRegistration(){ 
	var name =document.getElementById("username").value;
	var email = document.getElementById("email").value;
	var email = email.toLowerCase();
	var mobile = document.getElementById("mobile").value;
	var orgname = document.getElementById("orgname").value;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	
	var noofEmp = document.getElementById("noofEmp").value;
	var privacyCheck = document.getElementById("privacyCheck").checked;
	var whatsupCheck = document.getElementById("whatsupCheck").checked;
	
	
	if(name==""){
		document.getElementById("usernameError1").innerHTML="Please Enter Name";
		document.getElementById("username").focus();
		return false;
	}else{
		document.getElementById("usernameError1").innerHTML="";
	}
	 if(orgname==""){
		document.getElementById("orgnameError1").innerHTML="Please Enter Message";
		document.getElementById("orgname").focus();
		return false;
	}else{
		document.getElementById("orgnameError1").innerHTML="";
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
		document.getElementById("mobileu").focus();
		return false;
	}else if(!mobile.match(regMobile)){
		document.getElementById("mobileError1").innerHTML="Please Enter Valid Mobile Number";
		document.getElementById("mobileu").focus();
		return false;
	}else{
		document.getElementById("mobileError1").innerHTML="";
	}
	
	/*if(message==""){
		document.getElementById("messageError1").innerHTML="Please Enter Message";
		document.getElementById("messageu").focus();
		return false;
	}else{
		document.getElementById("messageError1").innerHTML="";
	}*/
	if(noofEmp==""){
		document.getElementById("noofEmpError1").innerHTML="Please Select No of Employee";
		document.getElementById("noofEmp").focus();
		return false;
	}else{
		document.getElementById("noofEmpError1").innerHTML="";
	}
	if(privacyCheck==false){
		document.getElementById("privacyCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("privacyCheck").focus();
		return false;
	}else{
		document.getElementById("privacyCheckError1").innerHTML="";
	}
	if(whatsupCheck==false){
		document.getElementById("whatsupCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("whatsupCheck").focus();
		return false;
	}else{
		document.getElementById("whatsupCheckError1").innerHTML="";
		
	}
	document.getElementById("signinLoader").style.display="flex";
    var formData = new FormData(registerUser);
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/registerUser",
	     //url:"/registerUser",
         data: formData,
         processData: false,
         contentType: false,
         success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true){
				 document.getElementById("otsuccmsg").innerHTML="Our Team Will Contact you soon, Thanks for Registration.";
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
 
 function validateRegistrationForm(){
	var name =document.getElementById("username").value;
	var email = document.getElementById("email").value;
	var email = email.toLowerCase();
	var mobile = document.getElementById("mobile").value;
	var orgid = document.getElementById("orgname").value;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	var noofEmp = document.getElementById("noofEmp").value;
	var privacyCheck = document.getElementById("privacyCheck").checked;
	var whatsupCheck = document.getElementById("whatsupCheck").checked;
	
	if(name==""){
		document.getElementById("usernameError1").innerHTML="Please Enter Name";
		document.getElementById("username").focus();
		return false;
	}else{
		document.getElementById("usernameError1").innerHTML="";
	}
	if(orgid==""){
		document.getElementById("orgnameError1").innerHTML="Please Enter Organization Name";
		document.getElementById("orgname").focus();
		return false;
	}else{
		document.getElementById("orgnameError1").innerHTML="";
	}
	if(email==""){
		document.getElementById("emailError1").innerHTML="Please Enter Email";
		document.getElementById("email").focus();
		return false;
	}else if(!email.match(regEmail)){
		document.getElementById("emailError1").innerHTML="Please Enter Valid Email";
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
	if(noofEmp==""){
		document.getElementById("noofEmpError1").innerHTML="Please Select No of Employee";
		document.getElementById("noofEmp").focus();
		return false;
	}else{
		document.getElementById("noofEmpError1").innerHTML="";
	}
	if(privacyCheck==false){
		document.getElementById("privacyCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("privacyCheck").focus();
		return false;
	}else{
		document.getElementById("privacyCheckError1").innerHTML="";
	}
	if(whatsupCheck==false){
		document.getElementById("whatsupCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("whatsupCheck").focus();
		return false;
	}else{
		document.getElementById("whatsupCheckError1").innerHTML="";
		return true;
	}
	
}	

function validateemail()  {  
	var x=document.getElementById("email").value;  
	var atposition=x.indexOf("@");  
	var dotposition=x.lastIndexOf(".");  
	if (atposition<1 || dotposition<atposition+2 || dotposition+2>=x.length){   
  		document.getElementById("emailError").innerHTML="Please Enter Valid E-mail Address";
  		return false;  
  	}else{
		document.getElementById("emailError").innerHTML="";
 		return true;  
	}
}


function validateemailForUser()  {  
	var x=document.getElementById("emailu").value;  
	var atposition=x.indexOf("@");  
	var dotposition=x.lastIndexOf(".");  
	if (atposition<1 || dotposition<atposition+2 || dotposition+2>=x.length){   
  		document.getElementById("emailError1").innerHTML="Please Enter Valid E-mail Address";
  		return false;  
  	}else{
		document.getElementById("emailError1").innerHTML="";
 		return true;  
	}
}

function verifyEmail(){ 
	
	var mobile = document.getElementById("mobile").value;
	document.getElementById("emailLoader").style.display="flex";
	
    var formData = new FormData(emailregisterUser);
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/verifyRegisterUser",
	     //url:"/registerUser",
         data: formData,
         processData: false,
         contentType: false,
         success: function(data){
            newData = data;
            document.getElementById("emailLoader").style.display="none";
			var data1 = jQuery.parseJSON(newData);
			if(data1.status==true){
				 document.getElementById("otsuccmsg").innerHTML="You are successfully verified, Thanks for Registration.";
				 document.getElementById("otmsgdiv").style.display="block";
				 //document.getElementById("getInTouchUser").reset();
				 $('#otmsgdiv').delay(5000).fadeOut(400);
				 document.getElementById("verifybutton").style.display = "none";
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