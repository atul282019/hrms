function bulkSubmit(){
	
	 var regName = /^[a-zA-Z\s]*$/;
	 var onlySpace = /^$|.*\S+.*/;	 

	var employerId = document.getElementById("employerId").value;
	var formData = new FormData(empdetailForm);
	formData.append("employerId",employerId);
	document.forms[0].action = "" + $('#ctx').attr('content') + "/saveBulkFile";
	document.forms[0].method = "post";
	document.forms[0].submit();
	
	/*document.getElementById("signinLoader").style.display="flex";
	 	$.ajax({
		type: "POST",
	     url:""+$('#ctx').attr('content')+"/saveBulkFile",
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
			if(data1.status=="SUCCESS"){
				// document.getElementById("employeeId").value=data1.data.id;
				 document.getElementById("bulksuccmsg").innerHTML="Data Saved Successfully";
				 document.getElementById("bulkmsgdiv").style.display="block";
				 //document.getElementById("saveorg").reset();
				 //getEmployeeDetail();
				 $('#bulkmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status=="FAILURE"){
				//getEmployeeDetail();
				 document.getElementById("bulkfailmsg").innerHTML=data1.message;
				 document.getElementById("bulkfailmsgDiv").style.display="block";
				 $('#bulkfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				 document.getElementById("failmsgDiv").style.display="none";
				 document.getElementById("successmsgdiv").style.display="none";
				 //document.getElementById("FailedError").innerHTML="API Gateway not respond. Please try again.";
			}
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });	*/		
}
