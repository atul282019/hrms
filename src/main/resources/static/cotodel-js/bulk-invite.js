function sendBulkInviteSubmit(){
	
	var formData = new FormData(bulkInvite);
	//formData.append("employerId",employerId);
	document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/sendInviteEmail",
         data: formData,
         processData: false,
         contentType: false,       		 
            success: function(data){
            newData = data;
			var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			console.log(data1);
			console.log(data1.correct);			
			console.log(data1.incorrect);
			if(data1.status==true){
				// document.getElementById("employeeId").value=data1.data.id;
				 document.getElementById("invitesuccmsg").innerHTML="Email Send Successfully";
				 document.getElementById("invitemsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 $('#invitemsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status==false){
				 document.getElementById("invitefailmsg").innerHTML=data1.message;
				 document.getElementById("invitefailmsgDiv").style.display="block";
				 $('#invitefailmsgDiv').delay(5000).fadeOut(400);
			}else{
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

