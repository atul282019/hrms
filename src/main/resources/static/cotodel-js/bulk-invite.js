function validateEmails() {
    const emailInput = document.getElementById("inviteEmployee").value.trim();
    const emailError = document.getElementById("inviteEmployeeError");

    // Regular expression for a valid email
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    // Split the input by commas and remove any extra spaces
    const emails = emailInput.split(",").map(email => email.trim());

    // Check each email, ignoring empty entries or those containing only spaces
    const invalidEmails = emails.filter(email => {
        // Exclude blank or space-only emails
        if (email === "") {
            return true; // Treat blank email as invalid
        }
        return !emailRegex.test(email); // Validate against regex
    });

    if (invalidEmails.length > 0) {
        emailError.innerText = `Invalid email(s): ${invalidEmails.join(", ")}`;
		document.getElementById("btnSendInvite").disabled = true;
		return false;
    } else {
		document.getElementById("btnSendInvite").disabled = false;
        emailError.innerText = "";
    }
}
function validateContractorEmails() {
    const emailInput = document.getElementById("inviteContractor").value.trim();
    const emailError = document.getElementById("inviteContractorError");
	
	// Regular expression for a valid email
	   const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	   // Split the input by commas and remove any extra spaces
	   const emails = emailInput.split(",").map(email => email.trim());

	   // Check each email, ignoring empty entries or those containing only spaces
	   const invalidEmails = emails.filter(email => {
	       // Exclude blank or space-only emails
	       if (email === "") {
	           return true; // Treat blank email as invalid
	       }
	       return !emailRegex.test(email); // Validate against regex
	   });

	   if (invalidEmails.length > 0) {
	       emailError.innerText = `Invalid email(s): ${invalidEmails.join(", ")}`;
		   document.getElementById("btnSendInvite").disabled = true;
		   return false;
	   } else {
		   document.getElementById("btnSendInvite").disabled = false;
	       emailError.innerText = "";
	   }
}

function sendBulkInviteSubmit(){
	
		var inviteEmployee =document.getElementById("inviteEmployee").value;
		var inviteContractor =document.getElementById("inviteContractor").value;
		
		if(inviteEmployee==""){
			document.getElementById("inviteEmployeeError").innerHTML="Please Enter Emails Separated By Commas.";
			document.getElementById("inviteEmployee").focus();
			return false;
			}else{
				document.getElementById("inviteEmployeeError").innerHTML="";
			}
		
		if(inviteContractor==""){
			document.getElementById("inviteContractorError").innerHTML="Please Enter Emails Separated By Commas.";
			document.getElementById("inviteContractor").focus();
			return false;
		}else{
			document.getElementById("inviteContractorError").innerHTML="";
		}
		validateEmails();
		validateContractorEmails();
		var formData = new FormData(bulkInvite);
		//formData.append("employerId",employerId);
		document.getElementById("signinLoader").style.display="flex";
		document.getElementById("btnSendInvite").disabled = true;
	 	$.ajax({
		type: "POST",
	     url:"/sendInviteEmail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			document.getElementById("btnSendInvite").disabled = false;
			console.log(data1);
			console.log(data1.correct);			
			console.log(data1.incorrect);
			if(data1.status==true){
				// document.getElementById("employeeId").value=data1.data.id;
				 document.getElementById("invitesuccmsg").innerHTML="Email Send Successfully";
				 document.getElementById("invitemsgdiv").style.display="block";
				 document.getElementById("bulkInvite").reset();
				 $('#invitemsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("btnSendInvite").disabled = false;
				 document.getElementById("invitefailmsg").innerHTML=data1.message;
				 document.getElementById("invitefailmsgDiv").style.display="block";
				 $('#invitefailmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("btnSendInvite").disabled = false;
				 document.getElementById("invitefailmsg").innerHTML=data1.message;
				 document.getElementById("invitefailmsgDiv").style.display="block";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });			
}

