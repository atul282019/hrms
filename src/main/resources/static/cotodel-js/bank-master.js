
let bankLogo="";
let statusInput1 =1;
function saveBankMaster()
{
	var bankName = document.getElementById("bankName").value;
	//var ifsc=document.getElementById("ifsc").value;
	var bankCode = document.getElementById("bankCode").value;

	
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
        document.getElementById("bankLogoError").innerHTML="Please Select a image ";
        document.getElementById("bankLogo").focus();
        return false;
    }
	

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
/*function convertImageToBase64() {
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
}*/

/*function validateAndConvertImage() {
    const fileInput = document.getElementById("bankLogo");
    const file = fileInput.files[0];
    const errorDiv = document.getElementById("bankLogoError");

    errorDiv.textContent = "";

    if (file) {
        const validMimeTypes = ["image/jpeg", "image/png"];
        const maxSizeInMB = 2;
        const maxSizeInBytes = maxSizeInMB * 1024 * 1024;

        if (!validMimeTypes.includes(file.type)) {
            errorDiv.textContent = "Invalid file type. Please upload a JPEG or PNG image.";
            fileInput.value = "";
            return;
        }

        if (file.size > maxSizeInBytes) {
            errorDiv.textContent = `File size exceeds ${maxSizeInMB}MB. Please upload a smaller image.`;
            fileInput.value = "";
            return;
        }

        const reader = new FileReader();
        reader.onload = function(event) {
            const base64String = event.target.result.split(",")[1];
            console.log("Base64 String", base64String);
			bankLogo=base64String;
        };

        reader.readAsDataURL(file);
    } else {
        errorDiv.textContent = "Please select a valid image file.";
    }
}*/
function validateAndConvertImage() {
    const fileInput = document.getElementById("bankLogo");
    const file = fileInput.files[0];
    const errorDiv = document.getElementById("bankLogoError");

    // Clear any previous error messages
    errorDiv.textContent = "";

    if (file) {
        const validMimeTypes = ["image/jpeg", "image/png"];
        const maxSizeInMB = 2;
        const maxSizeInBytes = maxSizeInMB * 1024 * 1024;

        // Validate file type
        if (!validMimeTypes.includes(file.type)) {
            displayError("Invalid file type. Please upload a JPEG or PNG image.", errorDiv, fileInput);
            return;
        }

        // Validate file size
        if (file.size > maxSizeInBytes) {
            displayError(`File size exceeds ${maxSizeInMB}MB. Please upload a smaller image.`, errorDiv, fileInput);
            return;
        }

        // Convert to Base64
        const reader = new FileReader();
        reader.onload = function (event) {
            const base64String = event.target.result.split(",")[1];
            console.log("Base64 String", base64String);
            bankLogo = base64String; // Assign Base64 string to the variable
        };

        reader.readAsDataURL(file);
    } else {
        displayError("Please select a valid image file.", errorDiv, fileInput);
    }
}

// Helper function to display error and reset file input
function displayError(message, errorDiv, fileInput) {
    errorDiv.textContent = message;
    setTimeout(() => {
        errorDiv.textContent = ""; // Clear error message after 0.5 seconds
    }, 1500);
    fileInput.value = ""; // Reset the file input field
}




 
  
  
  