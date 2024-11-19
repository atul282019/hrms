let bankLogo="";
let statusInput1 =1;
function saveBankMaster()
{

	var bankName = document.getElementById("bankName").value;
	//var ifsc=document.getElementById("ifsc").value;
	var bankCode = document.getElementById("bankCode").value;

	
	//var status = document.getElementById("statusToggle").value;
	//var statusText=document.getElementById("statusText").value;
	/*console.log(bankName+"Bankname");
	console.log(ifsc);
	console.log(bankCode);
	console.log(statusToggle);
	*/
	
	
	
	

	
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


    if (bankLogo.length === 0) {
        document.getElementById("bankLogoError").innerHTML="Please Enter bank code";
        document.getElementById("bankLogo").focus();
        return false;
    }
	else{
		document.getElementById("bankLogoError").innerHTML="";
	}
	
	/*if(ifsc==""){
		document.getElementById("ifscError").innerHTML="Please Enter IFSC";
		document.getElementById("ifsc").focus();
		return false;
	}else{
		document.getElementById("ifscError").innerHTML="";
	}*/
	
	
	
	/*if (statusToggle.checked) {
									                //statusText.textContent = "Active";
									                statusInput.value = "1";  // Set status as 1 for active
									            } else {
									                //statusText.textContent = "Inactive";
									                statusInput.value = "0";  // Set status as 0 for inactive
									            }
	*/
	
	/*var formData  = new FormData(document.getElementById("bankMaster1"));
	console.log(formData);
	
	formData.append("bankName",bankName);
	console.log("bankname "+bankName);
	
	formData.append("bankCode",bankCode);
	console.log("bankCode "+bankCode);
	
	formData.append("status",statusInput1);
	console.log("status ",statusInput1);
	
	formData.append("bankLogo",bankLogo);
	console.log("bankLogo "+bankLogo);
	
	formData.append("bankIfsc",ifsc);
	console.log("bankIfsc"+ifsc);
	
console.log(formData);
	document.getElementById("bankMaster1").disabled=true;
	*/
	console.log("Printing bankname "+bankName);
	 	$.ajax({
		type: "POST",
	     url:"/savebankMaster",
          data: {
			"bankName": bankName,
			"bankCode": bankCode,
			"status": statusInput1,
			"bankLogo": bankLogo,
			//"bankIfsc": ifsc
			
      		 },dataType: "json", 
      		  //beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			//},      		 
            success:function(data){
            var data1 = data;
            console.log(data);
			//var data1 = jQuery.parseJSON(newData);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status=='SUCCESS'){
				 document.getElementById("otsuccmsg").innerHTML="Data Saved Successfully.";
				 document.getElementById("otmsgdiv").style.display="block";
				 document.getElementById("bankMaster1").reset();
				 document.getElementById("saveBankmaster").disabled=false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status=='FAILURE'){
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 document.getElementById("saveBankmaster1").disabled=false;
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
function convertImageToBase64() {
    const fileInput = document.getElementById("bankLogo");
    const file = fileInput.files[0]; // Get the selected file

    if (file) {
        const reader = new FileReader();
        
        // When file is read, execute this function
        reader.onload = function(event) {
            const base64String = event.target.result.split(',')[1]; // Extract Base64 part

            // Display Base64 string in the output div (optional)
            //document.getElementById("base64Output").textContent = base64String;

            // priting base64 string on console
            console.log("Base64 String", base64String);
            bankLogo=base64String; 
        };

        // Read file as Data URL (Base64)
       reader.readAsDataURL(file);
    } else {
        alert("Please select a valid image file.");
    }
}
/*function getBankMaster() {

	//var employeeId= document.getElementById("employeeId").value;
	//var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
		$.ajax({
			type: "GET",
			url: "/getbankmaster",
			data: {
				"bankName": bankName,
				"bankCode": bankCode,
				"status": statusInput1,
				"bankLogo": bankLogo,
				"bankIfsc": ifsc
			},dataType: "json", 
	       		 // beforeSend : function(xhr) {
					//xhr.setRequestHeader(header, token);
					//},
	            success: function(data){
	            newData = data;
	            
				var data1 = jQuery.parseJSON(newData);
				document.getElementById("signinLoader").style.display="none";
				if(data1.status=='SUCCESS'){
					 document.getElementById("otsuccmsg").innerHTML="Data Saved Successfully.";
					 document.getElementById("otmsgdiv").style.display="block";
					 document.getElementById("bankMaster1").reset();
					 document.getElementById("saveBankmaster").disabled=false;
					 $('#otmsgdiv').delay(5000).fadeOut(400);
				}else if(data1.status=='FAILURE'){
					 document.getElementById("otfailmsg").innerHTML=data1.message;
					 document.getElementById("otfailmsgDiv").style.display="block";
					 document.getElementById("saveBankmaster1").disabled=false;
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
	}
  */
 // Function to fetch bank data, populate dropdown, and handle auto-population of bank code
function initializeBankDropdown() {
    let bankData = [];  // Local variable to store bank data by bank code
console.log("Inside initializeBankDropdown()")
    // Fetch bank data and populate dropdown
    $.ajax({
        type: "GET",
        url: "/getbankmaster",  // Replace with your actual endpoint
        dataType: "json",
        success: function(response) {
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

// Call `initializeBankDropdown` on page load
//document.addEventListener("DOMContentLoaded", initializeBankDropdown);

 
  
  
  