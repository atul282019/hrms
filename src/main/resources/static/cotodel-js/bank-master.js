
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
        document.getElementById("bankLogoError").innerHTML="Please Enter bank code";
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
/*function initializeBankDropdown() {
    let bankData = {}; // Store bank data by bank code
    console.log("Inside initializeBankDropdown()");

    // Fetch bank data and populate dropdown
    $.ajax({
        type: "GET",
        url: "/getbankmaster", // Replace with your actual endpoint
        dataType: "json",
        success: function(response) {
            if (response.status === true) {
                const bankDropdown = document.getElementById("bankName");

                bankDropdown.innerHTML = ""; // Clear existing options

                // Default "Select Bank" option
                const defaultOption = document.createElement("option");
                defaultOption.value = "";
                defaultOption.textContent = "Select a bank name";
                defaultOption.disabled = true;
                defaultOption.selected = true;
                bankDropdown.appendChild(defaultOption);

                // Populate dropdown with bank data
                response.data.forEach(bank => {
                    bankData[bank.bankcode] = bank; // Store bank data by bankCode
                    const option = document.createElement("option");
                    option.value = bank.bankcode; // Use bankCode as the value
                    option.textContent = bank.bankname; // Display bankName
                    bankDropdown.appendChild(option);
                });

                // Add "Add New Bank" option
                const addNewOption = document.createElement("option");
                addNewOption.value =document.getElementById("bankName");
                addNewOption.textContent = "Add New Bank...";
                bankDropdown.appendChild(addNewOption);

                // Event listener for dropdown change
                bankDropdown.addEventListener("change", function () {
                    const selectedValue = bankDropdown.value;

                    if (selectedValue === "addNew") {
                        // Create a small input field dynamically
                        const inputField = document.createElement("input");
                        inputField.type = "text";
                        inputField.id = "newBankInput";
                        inputField.placeholder = "Enter new bank name";
                        inputField.className = "form-control mt-2";

                        // Remove existing input field if it exists
                        const existingInput = document.getElementById("newBankInput");
                        if (existingInput) {
                            existingInput.remove();
                        }

                        // Append the new input field below the dropdown
                        bankDropdown.parentNode.appendChild(inputField);

                        // Listen for changes in the input field
                        inputField.addEventListener("input", function () {
                            // Placeholder: Store the entered new bank name if required
                            console.log("New Bank Name Entered:", inputField.value);
                        });
                    } else {
                        // Remove the input field if the user selects a valid bank
                        const existingInput = document.getElementById("newBankInput");
                        if (existingInput) {
                            existingInput.remove();
                        }
                    }
                });
            } else {
                console.error("Failed to fetch bank names:", response.message);
                document.getElementById("bankNameError").innerHTML = "Failed to load bank names.";
            }
        },
        error: function (error) {
            console.error("Error fetching bank names:", error);
            document.getElementById("bankNameError").innerHTML = "Error loading bank names.";
        }
    });
}
*/
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


 
  
  
  