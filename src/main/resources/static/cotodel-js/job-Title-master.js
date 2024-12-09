document.addEventListener("DOMContentLoaded", function () {
    console.log("Initializing auto-suggestion functionality");

    let jobDescriptions = []; // Store job descriptions
    const orgId = document.getElementById("orgId").value; // Get organization ID
    const jobDescInput = document.getElementById("jobDesc"); // Input field
    const suggestionList = document.getElementById("suggestions"); // Suggestion list container

    // Disable input field initially
    jobDescInput.disabled = true;

    // Fetch job descriptions from the server
    function fetchJobDescriptions() {
        $.ajax({
            type: "POST",
            url: "/getjobMasterWithId",
            data: { orgId: orgId },
            dataType: "json",
            success: function (response) {
                if (response.data && response.data.length > 0) {
                    // Extract and clean job descriptions
                    jobDescriptions = response.data
                        .filter((item) => item.jobDisc) // Exclude items without jobDisc
                        .map((item) => item.jobDisc.trim()); // Trim whitespace

                    console.log("Job descriptions loaded:", jobDescriptions);

                    // Enable input field after loading data
                    jobDescInput.disabled = false;
                } else {
                    console.warn("No valid job descriptions found.");
                }
            },
            error: function (error) {
                console.error("Error fetching job descriptions:", error);
            },
        });
    }

    // Handle user input for auto-suggestions
    function handleInputEvent(event) {
        const query = event.target.value.toLowerCase(); // Convert input to lowercase
        suggestionList.innerHTML = ""; // Clear previous suggestions

        if (query.length >= 3) {
            // Filter jobDescriptions based on the user's input
            const filteredSuggestions = jobDescriptions.filter((desc) =>
                desc.toLowerCase().includes(query)
            );

            if (filteredSuggestions.length > 0) {
                suggestionList.style.display = "block"; // Show suggestions list

                // Add suggestions to the list
                filteredSuggestions.forEach((item) => {
                    const listItem = document.createElement("li");
                    listItem.textContent = item;
                    listItem.style.cursor = "pointer"; // Add pointer cursor
                    listItem.addEventListener("click", function () {
                        jobDescInput.value = this.textContent; // Update input field
                        suggestionList.innerHTML = ""; // Clear suggestions
                        suggestionList.style.display = "none"; // Hide the list
                    });
                    suggestionList.appendChild(listItem);
                });
            } else {
                suggestionList.style.display = "none"; // Hide if no matches
            }
        } else {
            suggestionList.style.display = "none"; // Hide suggestions if query is too short
        }
    }

    // Fetch job descriptions on page load
    fetchJobDescriptions();

    // Attach event listener for user input
    jobDescInput.addEventListener("input", handleInputEvent);

    // Hide suggestion list if clicked outside
    document.addEventListener("click", function (event) {
        if (!jobDescInput.contains(event.target) && !suggestionList.contains(event.target)) {
            suggestionList.style.display = "none"; // Hide the suggestion list if clicked outside
        }
    });
});







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
          data: {
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
				 document.getElementById("jobTitlemaster").reset();//form
				 setTimeout(function() {
				                     window.location.href = "/displayjobTitlemaster"; // Update with your previous page URL
				                 }, 1000);
				 document.getElementById("savejobmaster").disabled=false;//button
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status=='FAILURE'){
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 document.getElementById("jobTitlemaster").disabled=false;
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



 
  
  
  