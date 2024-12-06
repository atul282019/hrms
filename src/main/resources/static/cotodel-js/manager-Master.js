

function savemanagerMaster()
{
	var managerLeveldesc = document.getElementById("managerLevel").value;
	//var ifsc=document.getElementById("ifsc").value;
	var userNamefromSession = document.getElementById("createdby").value;//getting from session
	var managerLeveldesc = document.getElementById("managerLevel").value;
	
	if(bankName==""){
		document.getElementById("bankNameerror").innerHTML="Please Enter Bank Name";
		document.getElementById("bankName").focus();
		return false;
	}else{
		document.getElementById("bankNameerror").innerHTML="";
	}
	
	if(bankCode==""){
		document.getElementById("bankcodeError").innerHTML="Please Enter bank code";
		document.getElementById("bankCode").focus();
		return false;
	}else{
		document.getElementById("bankcodeError").innerHTML="";
	}


	console.log("Printing bankname "+bankName);
	 	$.ajax({
		type: "POST",
	     url:"/savemanagerMaster",
          data: {
			"orgId": orgId,
			"managerLblDesc":managerLeveldesc,
			"createdBy":userNamefromSession,
			"remarks":,
			
			
      		 },dataType: "json", 
      		    		 
            success:function(data){
            var data1 = data;
            console.log(data);
			//var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status=='SUCCESS'){
				 document.getElementById("otsuccmsg").innerHTML="Data Saved Successfully.";
				 document.getElementById("otmsgdiv").style.display="block";
				 document.getElementById("bankMaster1").reset();
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

function initializeBankDropdown() {
    let bankData = [];  // Local variable to store bank data by bank code
console.log("Inside initializeBankDropdown()")
    // Fetch bank data and populate dropdown
    $.ajax({
        type: "GET",
        url: "/getbankmaster",  // Replace with your actual endpoint
        dataType: "json",
        success: function(response) {
			console.log("inside initializeBankDropdown"+response);
            if (response.status === true) {
                const bankDropdown = document.getElementById("bankName");
                bankDropdown.innerHTML =" Select a bank name";  // Clear existing options
				
				 const defaultOption = document.createElement("option");
                defaultOption.value = "";  // Empty value
                defaultOption.textContent = "Select a bank name";
                defaultOption.disabled = true;  // Make it unselectable after a choice is made
                defaultOption.selected = true;  // Set as the default selected option
                bankDropdown.appendChild(defaultOption);
				 
                // Populate dropdown 
                response.data.forEach(bank => {
                    bankData[bank.bankcode] = bank;  // Store bank data by bankCode
                    const option = document.createElement("option");
                    option.value = bank.bankcode;  // Use bankCode as the value
                    option.textContent = bank.bankname;  // Display bankName
                    bankDropdown.appendChild(option);
                });

                // Event listener to auto-populate bank code based on selected bank
                bankDropdown.addEventListener("change", function() {
                    const selectedBankCode = bankDropdown.value;
                    const bankCodeInput = document.getElementById("bankCode");
	                console.log("Selected bank code:", selectedBankCode);  // Debugging log
                        console.log("Bank data:", bankData);  // Log bankData to check its structure
                    // If a bank is selected, populate the bankCode field
                    /*if (selectedBankCode && bankData[selectedBankCode]) {
                        bankCodeInput.value = bankData[selectedBankCode].bankcode;
                    } */
                    
                     if (selectedBankCode && bankData[selectedBankCode]) {
                            bankCodeInput.value = bankData[selectedBankCode].bankcode;
                            console.log("Bank code set to:", bankCodeInput.value);  // Debugging log
                        }else if (bankCodeInput) {
                            bankCodeInput.value = "";  // Clear if no valid selection
                            console.log("Bank code cleared due to invalid selection");
                        }

                });
            } else {
                console.error("Failed to fetch bank names:", response.message);
                document.getElementById("bankNameError").innerHTML = "Failed to load bank names.";
            }
        },
        error: function(error) {
            console.error("Error fetching bank names:", error);
            document.getElementById("bankNameError").innerHTML = "Error loading bank names.";
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



 
  
  
  