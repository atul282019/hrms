 
function validateRegistrationForm(){
	
	
	   
	var name =document.getElementById("username").value;
	var email = document.getElementById("email").value;
		 email = email.toLowerCase();
	var mobile = document.getElementById("mobile").value;
	var orgname = document.getElementById("orgname").value;
	//regex 
	var regName = /^[A-Za-z]+( [A-Za-z]+)*$/;  // Name must not be only spaces, allows space between words
	var regMobile = /^[6-9]\d{9}$/; // Mobile number starting with 6-9 and 10 digits
    var regEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{3,5}$/; // Email validation
   
	var regOrgName = /^[A-Za-z0-9]+( [A-Za-z0-9]+)*$/; 
 //Organization name must not be only spaces, allows space between words
	
	var noofEmp = document.getElementById("noofEmp").value;
	var privacyCheck = document.getElementById("privacyCheck").checked;
	var whatsupCheck = document.getElementById("whatsupCheck").checked;
	
	if(name==""){
		document.getElementById("usernameError1").innerHTML="Please Enter Name";
		document.getElementById("username").focus();
		return false;
	}else if (!name.match(regName)) {
        document.getElementById("usernameError1").innerHTML = "special character not allowed in name";
        document.getElementById("username").focus();
        return false;
	}
		else{
		document.getElementById("usernameError1").innerHTML="";
	}
	if(orgname==""){
		document.getElementById("orgnameError1").innerHTML="Please Enter Organization Name";
		document.getElementById("orgname").focus();
		return false;
	}else if (!orgname.match(regOrgName)) {
        document.getElementById("orgnameError1").innerHTML = "special character not allowed in  Organization name ";
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
	/*if(privacyCheck==false){
		document.getElementById("privacyCheckError1").innerHTML="Please agree to the privacy policy and terms of use.";
		document.getElementById("privacyCheck").focus();
		return false;
	}else{
		document.getElementById("privacyCheckError1").innerHTML="";
	}*/
	/*if(whatsupCheck==false){
		document.getElementById("whatsupCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("whatsupCheck").focus();
		return false;
	}else{
		document.getElementById("whatsupCheckError1").innerHTML="";
		return true;
	}*/
	
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
	//var orgname = document.getElementById("orgname").value;
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	var regName = /^[A-Za-z]+( [A-Za-z]+)*$/;  // Name must not be only spaces, allows space between words
	//var captcha= document.getElementById("captcha").value;
	var noofEmp = document.getElementById("noofEmp").value;
	var privacyCheck = document.getElementById("privacyCheck").checked;
	//var whatsupCheck = document.getElementById("whatsupCheck").checked;
	//var eRupiStatus = document.getElementById("eRupiStatus").checked;
	//console.log("eRupiStatus",eRupiStatus);
	
	
	if(name==""){
		document.getElementById("usernameError1").innerHTML="Please Enter Name";
		document.getElementById("username").focus();
		return false;
	}	else if (!name.match(regName)) {
	        document.getElementById("usernameError1").innerHTML = "special character not allowed in name";
	        document.getElementById("username").focus();
	        return false;
		}else{
		document.getElementById("usernameError1").innerHTML="";
	}
	/* if(orgname==""){
		document.getElementById("orgnameError1").innerHTML="Please Enter Organization Name";
		document.getElementById("orgname").focus();
		return false;
	}else{
		document.getElementById("orgnameError1").innerHTML="";
	}*/
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
	if(noofEmp==""){
		document.getElementById("noofEmpError1").innerHTML="Please Select No of Employee";
		document.getElementById("noofEmp").focus();
		return false;
	}else{
		document.getElementById("noofEmpError1").innerHTML="";
	}
	if(privacyCheck==false){
		document.getElementById("privacyCheckError1").innerHTML="Please agree to the privacy policy and terms of use.";
		document.getElementById("privacyCheck").focus();
		return false;
	}else{
		document.getElementById("privacyCheckError1").innerHTML="";
	}
	/*if(whatsupCheck==false){
		document.getElementById("whatsupCheckError1").innerHTML="Please Checked Terms And Condition";
		document.getElementById("whatsupCheck").focus();
		return false;
	}else{
		document.getElementById("whatsupCheckError1").innerHTML="";
		
	}*/
	/*if(captcha==""){
			document.getElementById("captchaError").innerHTML="Captcha is Required";
			//document.getElementById("mobileu").focus();
			return false;
		}*/
	
	document.getElementById("signinLoader").style.display="flex";
    var formData = new FormData(registerUser);
	//formData.append("organizationName",orgname);
	formData.append("companySize",noofEmp);
	formData.append("name",name);
	formData.append("consent",privacyCheck);
	
	
	console.log("form data",formData);
	 	$.ajax({
		type: "POST",
	     url:"/registerUser",
         data: formData,
         processData: false,
         contentType: false,
         success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status==true ){
				 document.getElementById("otsuccmsg").innerHTML="Our Team Will Contact you soon, Thanks for Registration.";
				 document.getElementById("otmsgdiv").style.display="block";
				 //document.getElementById("getInTouchUser").reset();
				 $('#otmsgdiv').delay(5000).fadeOut(400);
				//window.location.href = "http://localhost:9191/hrms/tempLogin?mobile="+mobile+"&email="+email;
				 var mobile=document.getElementById("mobile").value;
    			 localStorage.setItem("userName", mobile);
    			 window.location.href = "/signin";
			}else if(data1.status==false){
				//reCaptcha();
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}			 
			
			else{
				 document.getElementById("captchaError").innerHTML=data1.message;
				
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
 }
 function reCaptcha() {
   	document.getElementById("captcha").value = "";
    // var requestUri = $('#ctx').attr('content');
     document.getElementById("captcha_id").src = "" + "/captcha";
     document.getElementById("captcha").focus();
  
   }
/* function validateRegistrationForm(){
	var name =document.getElementById("username").value;  
	
	var email = document.getElementById("email").value;
	var email = email.toLowerCase();
	var mobile = document.getElementById("mobile").value;
	var orgid = document.getElementById("orgname").value;
	
	var regMobile = /^[6-9]\d{9}$/gi;
	var regEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{3,5})$/;
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
	
}	*/


 /*function validateRegistrationForm(){
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


function verifyEmail(){ 
	
	var mobile = document.getElementById("mobile").value;
	document.getElementById("emailLoader").style.display="flex";
	
    var formData = new FormData(emailregisterUser);
	 	$.ajax({
		type: "POST",
	     url:"/verifyRegisterUser",
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
 }*/