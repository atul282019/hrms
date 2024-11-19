let statusInput1 =1;
function saveVoucher()
{ console.log("saveVoucherTypeMaster clicked");

	var voucherDesc = document.getElementById("voucherDesc").value;
	var voucherType = document.getElementById("voucherType").value;
	var voucherSubType = document.getElementById("voucherSubType").value;
	
	var purposeCode= document.getElementById("purposeCode").value;

	if(voucherDesc==""){
		document.getElementById("voucherDescError").innerHTML="Please Enter or select voucherDesc";
		document.getElementById("voucherDesc").focus();
		return false;
	}else{
		document.getElementById("voucherDescError").innerHTML="";
	}
	
	if(voucherType==""){
		document.getElementById("voucherTypeError").innerHTML="Please Enter voucherType";
		document.getElementById("voucherType").focus();
		return false;
	}else{
		document.getElementById("voucherTypeError").innerHTML="";
	}
	if(voucherSubType==""){
		document.getElementById("voucherSubTypeError").innerHTML="Please Enter voucherType";
		document.getElementById("voucherSubType").focus();
		return false;
	}else{
		document.getElementById("voucherSubTypeError").innerHTML="";
	}

	if(purposeCode==""){
		document.getElementById("purposeCodeError").innerHTML="Please Enter purposeCode";
		document.getElementById("purposeCode").focus();
		return false;
	}else{
		document.getElementById("purposeCodeError").innerHTML="";
	}
	
	
	
	
	 	$.ajax({
		type: "POST",
	     url:"/savevoucherTypeMaster",
          data: {
			"voucherDesc": voucherDesc,
			"voucherType": voucherType,
			"voucherSubType":voucherSubType,
			"purposeCode":purposeCode,
			"status": statusInput1,
					
      		 },
      		 dataType: "json", 
     		 
            success:function(data){
            var data1 = data;
            console.log(data);
			 //data1 = jQuery.parseJSON(data1);
			document.getElementById("signinLoader").style.display="none";
			if(data1.status=='SUCCESS'){
				 document.getElementById("otsuccmsg").innerHTML="Data Saved Successfully.";
				 document.getElementById("otmsgdiv").style.display="block";
				 document.getElementById("vouchermasterForm").reset();
				 document.getElementById("saveVoucherTypeMaster").disabled=false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
			}else if(data1.status=='FAILURE'){
				 document.getElementById("otfailmsg").innerHTML=data1.message;
				 document.getElementById("otfailmsgDiv").style.display="block";
				 document.getElementById("vouchermasterForm").disabled=false;
				 $('#otfailmsgDiv').delay(5000).fadeOut(400);
			}else{
				console.log("logging from the saveVoucherTypeMaster")
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


function initializevoucherDropdown() {
      // Local variable to store bank data by bank code
console.log("Inside initializeBankDropdown()")
    // Fetch bank data and populate dropdown
    $.ajax({
        type: "GET",
        url: "/getvoucherTypeMaster",  // Replace with your actual endpoint
        dataType: "json",
        success: function(response) {
            const voucherDropdown = document.getElementById("voucherDesc");
            voucherDropdown.innerHTML = "";  // Clear existing options

            // Default option
            const defaultOption = document.createElement("option");
            defaultOption.value = "";
            defaultOption.textContent = "Select voucher";
            defaultOption.disabled = true;
            defaultOption.selected = true;
            voucherDropdown.appendChild(defaultOption);

            // Populate dropdown with voucher descriptions and subtypes from the response
            if (response.status === true) {
                response.data.forEach(voucher => {
                    const option = document.createElement("option");
                    option.value = voucher.voucherCode;  // Assuming voucherCode as value
                    option.textContent = voucher.voucherDesc;  // Display voucherDesc
                    option.dataset.voucherSubType = voucher.voucherSubType;  // Store voucherSubType in data attribute
                    voucherDropdown.appendChild(option);
                });
            }

            // Add "Add New" option
            const addNewOption = document.createElement("option");
            addNewOption.value = "addNew";
            addNewOption.textContent = "Add New Voucher";
            voucherDropdown.appendChild(addNewOption);

            // Add change event to handle option selection
            voucherDropdown.addEventListener("change", function() {
                const customVoucherInput = document.getElementById("customVoucherDesc");
                const voucherSubTypeInput = document.getElementById("voucherSubType");
                
                if (this.value === "addNew") {
                    customVoucherInput.style.display = "block";  // Show input for new voucher description
                    voucherSubTypeInput.value = "";  // Clear voucherSubType field for new entry
                } else {
                    customVoucherInput.style.display = "none";  // Hide input for predefined option

                    // Get selected voucher's subtype and set it in the input field
                    const selectedOption = this.options[this.selectedIndex];
                    const voucherSubType = selectedOption.dataset.voucherSubType;
                    voucherSubTypeInput.value = voucherSubType || "";  // Set subtype or clear if undefined
                }
            });
        },
        error: function() {
            console.error("Failed to fetch voucher descriptions");
        }
    });
}

// Call `initializeBankDropdown` on page load
//document.addEventListener("DOMContentLoaded", initializeBankDropdown);

 
  
  
  