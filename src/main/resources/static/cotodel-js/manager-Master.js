

function savemngrMaster()
{
	
	var orgId = document.getElementById("orgId").value;//getting from session
	//var ifsc=document.getElementById("ifsc").value;
	var userNamefromSession = document.getElementById("createdby").value;//getting from session
	var managerLeveldesc = document.getElementById("managerLevel").value;
	var remarks = document.getElementById("remarks").value;
	
	if(managerLeveldesc==""){
		document.getElementById("managerLevelError").innerHTML="Please Enter managerLeveldesc";
		document.getElementById("managerLevel").focus();
		return false;
	}else{
		document.getElementById("managerLevelError").innerHTML="";
	}
	
	if(remarks==""){
		document.getElementById("remarksError").innerHTML="remarks";
		document.getElementById("remarks").focus();
		return false;
	}else{
		document.getElementById("remarksError").innerHTML="";
	}


	
	 	$.ajax({
		type: "POST",
	     url:"/savemanagerMaster",
          data: {
			"orgId": orgId,
			"managerLblDesc":managerLeveldesc,
			"createdBy":userNamefromSession,
			"remarks":remarks,
			
			
      		 },dataType: "json", 
      		    		 
            success:function(data){
            var data1 = data;
            console.log(data);
			//var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status=='SUCCESS'){
				 document.getElementById("otsuccmsg").innerHTML="Data Saved Successfully.";
				 document.getElementById("otmsgdiv").style.display="block";
				 document.getElementById("managerMaster").reset();
				 setTimeout(function() {
                 window.location.href = "/displaymanagerMaster"; // Update with your previous page URL
                 }, 1000);
				 document.getElementById("managerMaster").disabled=false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status=='FAILURE'){
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 document.getElementById("managerMaster").disabled=false;
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				console.log("logging from the save bank master")
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




function toggleStatus()
{
	const statusText=document.getElementById('statusText');
		const statusToggle=document.getElementById('statusToggle');
		const statusInput = document.getElementById('status');
		statusText.textContent=statusToggle.checked ? "Active":"Inactive";
	if (statusToggle.checked) {
            statusText.textContent = "Active";
            statusInput.value = 1;
            statusInput1=1; // Set status as 1 for active
        } else {
            statusText.textContent = "Inactive";
            statusInput.value = 0;  // Set status as 0 for inactive
        statusInput1=0;
        }

}



 
  
  
  