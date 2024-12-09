

function savejobMaster()
{ console.log("Inside savejobMaster");
	//var managerLeveldesc = document.getElementById("managerLevel").value;
	//var ifsc=document.getElementById("ifsc").value;
	var userNamefromSession = document.getElementById("createdby").value;//getting from session
	//var managerLeveldesc = document.getElementById("managerLevel").value;
	var jobDesc = document.getElementById("jobDesc").value;
	var reportingDropdownValue = document.getElementById('reporting').value;
	var orgId= document.getElementById("orgId").value;
	var remarks= document.getElementById("remarks").value;
	var id = document.getElementById("id").value;
	
	
	if(jobDesc==""){
		document.getElementById("jobDescError").innerHTML="Please Enter jobDesc";
		document.getElementById("jobDesc").focus();
		return false;
	}else{
		document.getElementById("jobDescError").innerHTML="";
	}
	
	if(reportingDropdownValue==""){
			document.getElementById("reportingError").innerHTML="Please select a value from dropdown";
			document.getElementById("reporting").focus();
			return false;
		}else{
			document.getElementById("reportingError").innerHTML="";
		}
	/*if(orgId==""){
		document.getElementById("orgIdError").innerHTML="Please Enter orgId";
		document.getElementById("orgId").focus();
		return false;
	}else{
		document.getElementById("orgId").innerHTML="";
	}*/
	
	if(remarks==""){
			document.getElementById("remarksError").innerHTML="Please Enter remarks";
			document.getElementById("remarks").focus();
			return false;
		}else{
			document.getElementById("remarks").innerHTML="";
		}

	//console.log("Printing bankname "+bankName);
	 	$.ajax({
		type: "POST",
	     url:"/savejobTitlemaster",
          data: {"id":id,
			"jobDisc":jobDesc,
			"managerLblId":reportingDropdownValue,
			"createdBy":userNamefromSession,
			"orgId":orgId,
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
				 document.getElementById("editjobTitlemaster").reset();//form
				 setTimeout(function() {
				                     window.location.href = "/displayjobTitlemaster"; // Update with your previous page URL
				                 }, 1000);
				 document.getElementById("savejobmaster").disabled=false;//button
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status=='FAILURE'){
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 document.getElementById("editjobTitlemaster").disabled=false;
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				//console.log("logging from the save bank master")
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



 
  
  
  