 
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
 
 
 function carporateRegistration() {
     

      const name = document.getElementById("username").value.trim();
      const emailInput = document.getElementById("email").value.trim();
      const email = emailInput.toLowerCase();
      const mobile = document.getElementById("mobile").value.trim();
      const orgname = document.getElementById("orgname").value.trim();
      const privacyCheck = document.getElementById("terms").checked;
     // const whatsappCheck = document.getElementById("whatsapp").checked;

      const companySizeInput = document.querySelector('input[name="companySize"]:checked');
      const noofEmp = companySizeInput ? companySizeInput.value : "";

      const regMobile = /^[6-9]\d{9}$/;
      const regEmail = /^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})$/;
      const regName = /^[A-Za-z]+( [A-Za-z]+)*$/;

      if (!name || !regName.test(name)) {
          document.getElementById("usernameError1").innerHTML = "Please enter a valid full name.";
          document.getElementById("username").focus();
          return false;
      } else {
          document.getElementById("usernameError1").innerHTML = "";
      }

      if (!email || !regEmail.test(email)) {
          document.getElementById("emailError1").innerHTML = "Please enter a valid email address.";
          document.getElementById("email").focus();
          return false;
      } else {
          document.getElementById("emailError1").innerHTML = "";
      }

      if (!mobile || !regMobile.test(mobile)) {
          document.getElementById("mobileError1").innerHTML = "Please enter a valid 10-digit mobile number starting with 6-9.";
          document.getElementById("mobile").focus();
          return false;
      } else {
          document.getElementById("mobileError1").innerHTML = "";
      }

      if (!orgname) {
          document.getElementById("orgnameError1").innerHTML = "Please enter your company name.";
          document.getElementById("orgname").focus();
          return false;
      } else {
          document.getElementById("orgnameError1").innerHTML = "";
      }

     /* if (!noofEmp) {
          document.getElementById("noofEmpError1").innerHTML = "Please select your company size.";
          return false;
      } else {
          document.getElementById("noofEmpError1").innerHTML = "";
      }*/

      if (!privacyCheck) {
          document.getElementById("privacyCheckError1").innerHTML = "Please agree to the privacy policy and terms of use.";
          document.getElementById("terms").focus();
          return false;
      } else {
          document.getElementById("privacyCheckError1").innerHTML = "";
      }

     // document.getElementById("signinLoader").style.display = "flex";
 	 
      $.ajax({
          type: "POST",
          url: "/registerUser",
 		 dataType: 'json',
 		data: {
              "companySize": noofEmp,
              "name": name,
              "email": email,
              "mobile": mobile,
  			 "organizationName": orgname,
  			 "consent": privacyCheck,
 			 //"whatsupCheck": whatsappCheck,
 			 "companyType": "Carporate"
          },
          success: function(data) {
 			console.log(data);
             // var data1 = jQuery.parseJSON(data);
            //  document.getElementById("signinLoader").style.display = "none";
              if (data.status === true) {
                  document.getElementById("otsuccmsg").innerHTML = "Our Team Will Contact you soon, Thanks for Registration.";
                  document.getElementById("otmsgdiv").style.display = "block";
                  $('#otmsgdiv').delay(5000).fadeOut(400);
 				 
				  $("#demoModal").modal('show');
                  //localStorage.setItem("userName", mobile);
                 // window.location.href = "/signin";
 			  
              } else if (data.status === false) {
 				  //$("#demoModal").show();
                  document.getElementById("otfailmsg").innerHTML = data.message;
                  document.getElementById("otfailmsgDiv").style.display = "block";
                  $('#otfailmsgDiv').delay(5000).fadeOut(400);
              } else {
                  document.getElementById("captchaError").innerHTML = data1.message;
              }
          },
          error: function(e) {
              alert('Error: ' + e);
          }
      });
  }

 // Updated function using element IDs and original AJAX request
 function fleetRegistration() {
    

     const name = document.getElementById("username").value.trim();
     const emailInput = document.getElementById("email").value.trim();
     const email = emailInput.toLowerCase();
     const mobile = document.getElementById("mobile").value.trim();
     const orgname = document.getElementById("orgname").value.trim();
     const privacyCheck = document.getElementById("terms").checked;
     //const whatsappCheck = document.getElementById("whatsapp").checked;

     const companySizeInput = document.querySelector('input[name="companySize"]:checked');
     const noofEmp = companySizeInput ? companySizeInput.value : "";

     const regMobile = /^[6-9]\d{9}$/;
     const regEmail = /^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})$/;
     const regName = /^[A-Za-z]+( [A-Za-z]+)*$/;

     if (!name || !regName.test(name)) {
         document.getElementById("usernameError1").innerHTML = "Please enter a valid full name.";
         document.getElementById("username").focus();
         return false;
     } else {
         document.getElementById("usernameError1").innerHTML = "";
     }

     if (!email || !regEmail.test(email)) {
         document.getElementById("emailError1").innerHTML = "Please enter a valid email address.";
         document.getElementById("email").focus();
         return false;
     } else {
         document.getElementById("emailError1").innerHTML = "";
     }

     if (!mobile || !regMobile.test(mobile)) {
         document.getElementById("mobileError1").innerHTML = "Please enter a valid 10-digit mobile number starting with 6-9.";
         document.getElementById("mobile").focus();
         return false;
     } else {
         document.getElementById("mobileError1").innerHTML = "";
     }

     if (!orgname) {
         document.getElementById("orgnameError1").innerHTML = "Please enter your company name.";
         document.getElementById("orgname").focus();
         return false;
     } else {
         document.getElementById("orgnameError1").innerHTML = "";
     }

    /* if (!noofEmp) {
         document.getElementById("noofEmpError1").innerHTML = "Please select your company size.";
         return false;
     } else {
         document.getElementById("noofEmpError1").innerHTML = "";
     }*/

     if (!privacyCheck) {
         document.getElementById("privacyCheckError1").innerHTML = "Please agree to the privacy policy and terms of use.";
         document.getElementById("terms").focus();
         return false;
     } else {
         document.getElementById("privacyCheckError1").innerHTML = "";
     }

    // document.getElementById("signinLoader").style.display = "flex";
	 
     $.ajax({
         type: "POST",
         url: "/registerUser",
		 dataType: 'json',
		data: {
             "companySize": noofEmp,
             "name": name,
             "email": email,
             "mobile": mobile,
 			 "organizationName": orgname,
 			 "consent": privacyCheck,
			 //"whatsupCheck": whatsappCheck,
			 "companyType": "Fleet"
         },
         success: function(data) {
			console.log(data);
            // var data1 = jQuery.parseJSON(data);
           //  document.getElementById("signinLoader").style.display = "none";
             if (data.status === true) {
                 document.getElementById("otsuccmsg").innerHTML = "Our Team Will Contact you soon, Thanks for Registration.";
                 document.getElementById("otmsgdiv").style.display = "block";
                 $('#otmsgdiv').delay(5000).fadeOut(400);
				 
				  $("#demoModal").modal('show'); 
                 //localStorage.setItem("userName", mobile);
                // window.location.href = "/signin";
			  
             } else if (data.status === false) {
				//$("#demoModal").show();
                 document.getElementById("otfailmsg").innerHTML = data.message;
                 document.getElementById("otfailmsgDiv").style.display = "block";
                 $('#otfailmsgDiv').delay(5000).fadeOut(400);
             } else {
                 document.getElementById("captchaError").innerHTML = data1.message;
             }
         },
         error: function(e) {
             alert('Error: ' + e);
         }
     });
 }

 const form = document.getElementById("registrationForm");
 if (form) {
     form.addEventListener("submit", userRegistration);
 }
 
 function OTPforfleet(){
 	
	const name = document.getElementById("username").value.trim();
	    const emailInput = document.getElementById("email").value.trim();
	    const email = emailInput.toLowerCase();
	    const mobile = document.getElementById("mobile").value.trim();
	    const orgname = document.getElementById("orgname").value.trim();
	    const privacyCheck = document.getElementById("terms").checked;
	    //const whatsappCheck = document.getElementById("whatsapp").checked;

	    const companySizeInput = document.querySelector('input[name="companySize"]:checked');
	    const noofEmp = companySizeInput ? companySizeInput.value : "";

	    const regMobile = /^[6-9]\d{9}$/;
	    const regEmail = /^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})$/;
	    const regName = /^[A-Za-z]+( [A-Za-z]+)*$/;

	    if (!name || !regName.test(name)) {
	        document.getElementById("usernameError1").innerHTML = "Please enter a valid full name.";
	        document.getElementById("username").focus();
	        return false;
	    } else {
	        document.getElementById("usernameError1").innerHTML = "";
	    }

	    if (!email || !regEmail.test(email)) {
	        document.getElementById("emailError1").innerHTML = "Please enter a valid email address.";
	        document.getElementById("email").focus();
	        return false;
	    } else {
	        document.getElementById("emailError1").innerHTML = "";
	    }

	    if (!mobile || !regMobile.test(mobile)) {
	        document.getElementById("mobileError1").innerHTML = "Please enter a valid 10-digit mobile number starting with 6-9.";
	        document.getElementById("mobile").focus();
	        return false;
	    } else {
	        document.getElementById("mobileError1").innerHTML = "";
	    }

	    if (!orgname) {
	        document.getElementById("orgnameError1").innerHTML = "Please enter your company name.";
	        document.getElementById("orgname").focus();
	        return false;
	    } else {
	        document.getElementById("orgnameError1").innerHTML = "";
	    }

	   /* if (!noofEmp) {
	        document.getElementById("noofEmpError1").innerHTML = "Please select your company size.";
	        return false;
	    } else {
	        document.getElementById("noofEmpError1").innerHTML = "";
	    }*/

	    if (!privacyCheck) {
	        document.getElementById("privacyCheckError1").innerHTML = "Please agree to the privacy policy and terms of use.";
	        document.getElementById("terms").focus();
	        return false;
	    } else {
	        document.getElementById("privacyCheckError1").innerHTML = "";
	    }

	    document.getElementById("signinLoader").style.display = "flex";
	 
 	   var userMobile = document.getElementById("mobile").value;
 	  //if (checkbox.checked) {
 		$.ajax({
 			type: 'POST',
 	        url:"/otpWithOutRegister",
 			data: {
 						"mobile": userMobile,
 					},
 			dataType: 'json',
 			success: function(data) {
 			var obj = data;
			document.getElementById("signinLoader").style.display = "none";
 	        if (obj['status'] == true) {
 				// Mask the mobile number (show only last 4 digits)
 				var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
 				document.getElementById("maskedMobileDisplay1").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Role.`;				
 										
 	            // If successful, open the OTP modal
 				var timeleft = "60";
 				var resendCodeElement = document.getElementById("resendCode");
 	               // Hide the "Resend OTP" link initially
 	               resendCodeElement.style.display = "none";
 				var downloadTimer = setInterval(function() {
 					document.getElementById("countdown").innerHTML = "00:"+timeleft;
 					timeleft -= 1;
 					//document.getElementById("optBtn").style.display = "none";
 					document.getElementById("orderId").value= obj['orderId'];
 					//document.getElementById("verifyotpdiv").style.display = "block";
 					if (timeleft < 0) {
 						clearInterval(downloadTimer);
 						resendCodeElement.style.display = "block";	
 					}	
 				}, 1000);
 	            $("#fleetOTPModal").show();  
				//$("#demoModal").show();  
 	          } else {
 	            alert("Error: " + obj.message);
 	          }
 	        },
 	        error: function() {
 			  //$('#otpModal').fadeIn();
 	         // alert("An error occurred. Please try again.");
 	        }
 	      });
 	
 }
 
 function OTPforCorporate(){
 	
     const name = document.getElementById("username").value.trim();
     const emailInput = document.getElementById("email").value.trim();
     const email = emailInput.toLowerCase();
     const mobile = document.getElementById("mobile").value.trim();
     const orgname = document.getElementById("orgname").value.trim();
     const privacyCheck = document.getElementById("terms").checked;
    // const whatsappCheck = document.getElementById("whatsapp").checked;

     const companySizeInput = document.querySelector('input[name="companySize"]:checked');
     const noofEmp = companySizeInput ? companySizeInput.value : "";

     const regMobile = /^[6-9]\d{9}$/;
     const regEmail = /^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})$/;
     const regName = /^[A-Za-z]+( [A-Za-z]+)*$/;

     if (!name || !regName.test(name)) {
         document.getElementById("usernameError1").innerHTML = "Please enter a valid full name.";
         document.getElementById("username").focus();
         return false;
     } else {
         document.getElementById("usernameError1").innerHTML = "";
     }

     if (!email || !regEmail.test(email)) {
         document.getElementById("emailError1").innerHTML = "Please enter a valid email address.";
         document.getElementById("email").focus();
         return false;
     } else {
         document.getElementById("emailError1").innerHTML = "";
     }

     if (!mobile || !regMobile.test(mobile)) {
         document.getElementById("mobileError1").innerHTML = "Please enter a valid 10-digit mobile number starting with 6-9.";
         document.getElementById("mobile").focus();
         return false;
     } else {
         document.getElementById("mobileError1").innerHTML = "";
     }

     if (!orgname) {
         document.getElementById("orgnameError1").innerHTML = "Please enter your company name.";
         document.getElementById("orgname").focus();
         return false;
     } else {
         document.getElementById("orgnameError1").innerHTML = "";
     }

     if (!privacyCheck) {
         document.getElementById("privacyCheckError1").innerHTML = "Please agree to the privacy policy and terms of use.";
         document.getElementById("terms").focus();
         return false;
     } else {
         document.getElementById("privacyCheckError1").innerHTML = "";
     }

     document.getElementById("signinLoader").style.display = "flex";
  
 	   var userMobile = document.getElementById("mobile").value;
 	  //if (checkbox.checked) {
 		$.ajax({
 			type: 'POST',
 	        url:"/otpWithOutRegister",
 			data: {
 						"mobile": userMobile,
 					},
 			dataType: 'json',
 			success: function(data) {
 			var obj = data;
 		    document.getElementById("signinLoader").style.display = "none";
 	        if (obj['status'] == true) {
 				// Mask the mobile number (show only last 4 digits)
 				var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
 				document.getElementById("maskedMobileDisplay1").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Role.`;				
 										
 	            // If successful, open the OTP modal
 				var timeleft = "60";
 				var resendCodeElement = document.getElementById("resendCode");
 	               // Hide the "Resend OTP" link initially
 	               resendCodeElement.style.display = "none";
 				var downloadTimer = setInterval(function() {
 					document.getElementById("countdown").innerHTML = "00:"+timeleft;
 					timeleft -= 1;
 					//document.getElementById("optBtn").style.display = "none";
 					document.getElementById("orderId").value= obj['orderId'];
 					//document.getElementById("verifyotpdiv").style.display = "block";
 					if (timeleft < 0) {
 						clearInterval(downloadTimer);
 						resendCodeElement.style.display = "block";	
 					}	
 				}, 1000);
 	            $("#corporateOTPModal").show();  
 	          } else {
 	            alert("Error: " + obj.message);
 	          }
 	        },
 	        error: function() {
 			  //$('#otpModal').fadeIn();
 	         // alert("An error occurred. Please try again.");
 	        }
 	      });
 	
 }

 
 function resendfleetOTP(){
	
	document.getElementById("password1").value="";
  	 document.getElementById("password2").value="";
  	 document.getElementById("password3").value="";
  	 document.getElementById("password4").value="";
  	 document.getElementById("password5").value="";
  	 document.getElementById("password6").value="";
	 document.getElementById("otpError").innerHTML="";
 	var userMobile = document.getElementById("mobile").value;
 	var orderId = document.getElementById("orderId").value;
 		$.ajax({
 			type: 'POST',
 	        url:"/smsOtpResender",
 			data: {
 						"mobile": userMobile,
 						"orderId": orderId
 					},
 			dataType: 'json',
 			success: function(data) {
 			var obj = data;
 	        if (obj['status'] == true) {
 				var maskedMobile = "XXXXXX" + userMobile.toString().slice(-4);
 				document.getElementById("maskedMobileDisplay").innerText = `OTP code has been sent to your phone ${maskedMobile}. Enter OTP to validate Role.`;												
 	            // If successful, open the OTP modal
 				var timeleft = "60";
 				var resendCodeElement = document.getElementById("resendCode");
 	               // Hide the "Resend OTP" link initially
 	               resendCodeElement.style.display = "none";
 				var downloadTimer = setInterval(function() {
 					document.getElementById("countdownadd").innerHTML = "00:"+timeleft;
 					timeleft -= 1;
 					//document.getElementById("optBtn").style.display = "none";
 					document.getElementById("orderId").value= obj['orderId'];
 					//document.getElementById("verifyotpdiv").style.display = "block";
 					if (timeleft < 0) {
 						clearInterval(downloadTimer);
 						resendCodeElement.style.display = "block";	
 					}	
 				}, 1000);
 	            $("#fleetOTPModal").show();  
 	          } else {
 	            alert("Error: " + obj.message);
 	          }
 	        },
 	        error: function() {
 			  //$('#otpModal').fadeIn();
 	         // alert("An error occurred. Please try again.");
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
 		  	var userMobile = document.getElementById("userMobile").value;
 		  	 if (password1 == "" && password1.length < 1) {
 		  		//document.getElementById("mobError").innerHTML="";
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
 		  		//document.getElementById("mobError").innerHTML="";
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
 		  		//document.getElementById("mobError").innerHTML="";
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
 		  		//document.getElementById("mobError").innerHTML="";
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
 		  		//document.getElementById("mobError").innerHTML="";
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
 		  		//document.getElementById("mobError").innerHTML="";
 		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
 		  		x = false;
 		  	}
 		  	 else if (password6.length < 1) {
 		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
 		  		x = false;
 		  	}
 		  	else{
 		  		document.getElementById("otpError").innerHTML="";
 		  	}
 			document.getElementById("authenticate").disabled = true;
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
 		  				"mobile": userMobile,
 		  				"orderId": orderId,
 		  				"userName":userMobile
 		  			},
 		  			success: function(data) {
 		  				var obj = data;
 		  				if (obj['status']== true) {
 							roleUpdate();
 							$("#fleetOTPModal").hide();
 							//$("#roleAcessModalSuccessful").show();
 							
 		  				}else if (obj['status'] == false) {
							
 							document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
 							document.getElementById("authenticate").disabled = false;
							
							 document.getElementById("password1").value="";
			  	   		  	 document.getElementById("password2").value="";
			  	   		  	 document.getElementById("password3").value="";
			  	   		  	 document.getElementById("password4").value="";
			  	   		  	 document.getElementById("password5").value="";
			  	   		  	 document.getElementById("password6").value="";
 						} else {
 							
 		  				}
 		  			},
 		  			error: function(e) {
 		  				alert('Error: ' + e);
 		  			}
 		  		});
 		  }

		  
		  function verfyFleetRegistrationOtp() {
		   			
		   		  	var password1 = document.getElementById("password1").value;
		   		  	var password2 = document.getElementById("password2").value;
		   		  	var password3 = document.getElementById("password3").value;
		   		  	var password4 = document.getElementById("password4").value;
		   		  	var password5 = document.getElementById("password5").value;
		   		  	var password6 = document.getElementById("password6").value;
		   		  	var orderId = document.getElementById("orderId").value;
		   		  	var userMobile = document.getElementById("mobile").value;
		   		  	 if (password1 == "" && password1.length < 1) {
		   		  		//document.getElementById("mobError").innerHTML="";
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
		   		  		//document.getElementById("mobError").innerHTML="";
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
		   		  		//document.getElementById("mobError").innerHTML="";
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
		   		  		//document.getElementById("mobError").innerHTML="";
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
		   		  		//document.getElementById("mobError").innerHTML="";
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
		   		  		//document.getElementById("mobError").innerHTML="";
		   		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
		   		  		x = false;
		   		  	}
		   		  	 else if (password6.length < 1) {
		   		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		   		  		x = false;
		   		  	}
		   		  	else{
		   		  		document.getElementById("otpError").innerHTML="";
		   		  	}
		   			//document.getElementById("authenticate").disabled = true;
		   		  	$.ajax({
		   		  			type: "POST",
		   		  			url:"verifyOtpWithOutRegister",
		   		  			dataType: 'json',
		   		  			data: {
		   		  				"password1": password1,
		   		  				"password2": password2,
		   		  				"password3": password3,
		   		  				"password4": password4,	
		   		  				"password5": password5,
		   		  				"password6": password6,
		   		  				"mobile": userMobile,
		   		  				"orderId": orderId,
		   		  				"userName":userMobile
		   		  			},
		   		  			success: function(data) {
		   		  				var obj = data;
		   		  				if (obj['status']== true) {
		   							fleetRegistration();
		   							$("#fleetOTPModal").hide();
		   							//$("#roleAcessModalSuccessful").show();
		   							
		   		  				}else if (obj['status'] == false) {
		   							document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
		   							document.getElementById("authenticate").disabled = false;
									
									document.getElementById("password1").value="";
					  	   		  	 document.getElementById("password2").value="";
					  	   		  	 document.getElementById("password3").value="";
					  	   		  	 document.getElementById("password4").value="";
					  	   		  	 document.getElementById("password5").value="";
					  	   		  	 document.getElementById("password6").value="";
		   						} else {
		   							
		   		  				}
		   		  			},
		   		  			error: function(e) {
		   		  				alert('Error: ' + e);
		   		  			}
		   		  		});
		   		  }

				  function verfyCorporateRegistrationOtp() {
				  	   			
				  	   		  	var password1 = document.getElementById("password1").value;
				  	   		  	var password2 = document.getElementById("password2").value;
				  	   		  	var password3 = document.getElementById("password3").value;
				  	   		  	var password4 = document.getElementById("password4").value;
				  	   		  	var password5 = document.getElementById("password5").value;
				  	   		  	var password6 = document.getElementById("password6").value;
				  	   		  	var orderId = document.getElementById("orderId").value;
				  	   		  	var userMobile = document.getElementById("mobile").value;
				  	   		  	 if (password1 == "" && password1.length < 1) {
				  	   		  		//document.getElementById("mobError").innerHTML="";
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
				  	   		  		//document.getElementById("mobError").innerHTML="";
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
				  	   		  		//document.getElementById("mobError").innerHTML="";
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
				  	   		  		//document.getElementById("mobError").innerHTML="";
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
				  	   		  		//document.getElementById("mobError").innerHTML="";
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
				  	   		  		//document.getElementById("mobError").innerHTML="";
				  	   		  		document.getElementById("otpError").innerHTML="Please Enter OTP..";
				  	   		  		x = false;
				  	   		  	}
				  	   		  	 else if (password6.length < 1) {
				  	   		  		document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
				  	   		  		x = false;
				  	   		  	}
				  	   		  	else{
				  	   		  		document.getElementById("otpError").innerHTML="";
				  	   		  	}
				  	   			//document.getElementById("authenticate").disabled = true;
				  	   		  	$.ajax({
				  	   		  			type: "POST",
				  	   		  			url:"verifyOtpWithOutRegister",
				  	   		  			dataType: 'json',
				  	   		  			data: {
				  	   		  				"password1": password1,
				  	   		  				"password2": password2,
				  	   		  				"password3": password3,
				  	   		  				"password4": password4,	
				  	   		  				"password5": password5,
				  	   		  				"password6": password6,
				  	   		  				"mobile": userMobile,
				  	   		  				"orderId": orderId,
				  	   		  				"userName":userMobile
				  	   		  			},
				  	   		  			success: function(data) {
				  	   		  				var obj = data;
				  	   		  				if (obj['status']== true) {
				  	   							carporateRegistration();
				  	   							$("#corporateOTPModal").hide();
				  	   							//$("#roleAcessModalSuccessful").show();
				  	   							
				  	   		  				}else if (obj['status'] == false) {
				  	   							document.getElementById("otpError").innerHTML="Please Enter Valid OTP..";
				  	   							document.getElementById("authenticate").disabled = false;
												document.getElementById("password1").value="";
								  	   		  	 document.getElementById("password2").value="";
								  	   		  	 document.getElementById("password3").value="";
								  	   		  	 document.getElementById("password4").value="";
								  	   		  	 document.getElementById("password5").value="";
								  	   		  	 document.getElementById("password6").value="";
				  	   						} else {
				  	   							
				  	   		  				}
				  	   		  			},
				  	   		  			error: function(e) {
				  	   		  				alert('Error: ' + e);
				  	   		  			}
				  	   		  		});
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

		  function focusBack(){
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

