
document.addEventListener("DOMContentLoaded", function () {
    console.log("Initializing manager level auto-suggestion");

    let managerLevels = []; // Array to store manager level descriptions
    const orgId = document.getElementById("orgId").value; // Get organization ID
    const managerLevelInput = document.getElementById("managerLevel"); // Manager level input field

    // Create suggestion list dynamically
    const suggestionList = document.createElement("ul");
    suggestionList.classList.add("suggestion-list");
    managerLevelInput.parentElement.style.position = "relative"; // Ensure parent has relative positioning
    managerLevelInput.parentElement.appendChild(suggestionList);

    // Disable input field initially
    managerLevelInput.disabled = true;

    // Fetch manager level data
    function fetchManagerLevels() {
        $.ajax({
            type: "POST",
            url: "/getmanagerMasterWithId",
            data: { orgId: orgId },
            dataType: "json",
            success: function (response) {
                if (response.data && response.data.length > 0) {
                    managerLevels = response.data
                        .filter((item) => item.managerLblDesc) // Exclude items without levelDesc
                        .map((item) => item.managerLblDesc.trim()); // Trim whitespace
                    console.log("Manager levels loaded:", managerLevels);

                    // Enable input field after loading data
                    managerLevelInput.disabled = false;
                } else {
                    console.warn("No valid manager levels found.");
                }
            },
            error: function (error) {
                console.error("Error fetching manager levels:", error);
            },
        });
    }

    // Handle user input for auto-suggestions
    function handleInputEvent(event) {
        const query = event.target.value.toLowerCase(); // Convert input to lowercase
        suggestionList.innerHTML = ""; // Clear previous suggestions

        if (query.length >= 3) {
            // Filter managerLevels based on the user's input
            const filteredSuggestions = managerLevels.filter((level) =>
                level.toLowerCase().includes(query)
            );

            if (filteredSuggestions.length > 0) {
                suggestionList.classList.add("show"); // Show suggestions list

                filteredSuggestions.forEach((item) => {
                    const listItem = document.createElement("li");
                    listItem.textContent = item;
                    listItem.addEventListener("click", function () {
                        managerLevelInput.value = this.textContent; // Update input field
                        suggestionList.innerHTML = ""; // Clear suggestions
                        suggestionList.classList.remove("show"); // Hide the list
                    });
                    suggestionList.appendChild(listItem);
                });
            } else {
                suggestionList.classList.remove("show"); // Hide if no matches
            }
        } else {
            suggestionList.classList.remove("show"); // Hide suggestions if query is too short
        }
    }

    // Fetch manager levels on page load
    fetchManagerLevels();

    // Attach event listener for user input
    managerLevelInput.addEventListener("input", handleInputEvent);

    // Hide suggestion list if clicked outside
    document.addEventListener("click", function (event) {
        if (
            !managerLevelInput.contains(event.target) &&
            !suggestionList.contains(event.target)
        ) {
            suggestionList.classList.remove("show"); // Hide the suggestion list
        }
    });
});



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



 
  
  
  