let statusInput1 =1;
let voucherLogo="";
function saveVoucher()
{ 
	console.log("saveVoucherTypeMaster clicked");

	const voucherDescDropdown = document.getElementById("voucherDesc");
    const selectedOption = voucherDescDropdown.options[voucherDescDropdown.selectedIndex];
    const voucherId = selectedOption.dataset.id || null; // Get the ID or set it as null if not available

    var voucherDesc = voucherDescDropdown.value;// Default to dropdown value
    const voucherType = document.getElementById("voucherType").value;
    const voucherSubType = document.getElementById("voucherSubType").value;
    const purposeCode = document.getElementById("purposeCode").value;



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
	if (voucherLogo.length === 0) {
        document.getElementById("voucherLogoError").innerHTML="Please select voucher image";
        document.getElementById("voucherLogo").focus();
        return false;
    }

	
	
	
	 	$.ajax({
		type: "POST",
	     url:"/savevoucherTypeMaster",
          data: {
			  "id": voucherId, 
			"voucherDesc": voucherDesc,
			"voucherType": voucherType,
			"voucherSubType":voucherSubType,
			"purposeCode":purposeCode,
			"status": statusInput1,
			"voucherLogo":voucherLogo,
					
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
    console.log("Inside initializevoucherDropdown()");

    // Fetch voucher data and populate dropdown
    $.ajax({
        type: "GET",
        url: "/getvoucherTypeMaster", // Replace with your actual endpoint
        dataType: "json",
        success: function (response) {
            const voucherDropdown = document.getElementById("voucherDesc");
            voucherDropdown.innerHTML = ""; // Clear existing options

            // Default option
            const defaultOption = document.createElement("option");
            defaultOption.value = "";
            defaultOption.textContent = "Select voucher";
            defaultOption.disabled = true;
            defaultOption.selected = true;
            voucherDropdown.appendChild(defaultOption);

            // Populate dropdown with voucher descriptions and subtypes from the response
            if (response.status === true) {
                response.data.forEach((voucher) => {
                    const option = document.createElement("option");
                    //option.value = voucher.voucherCode; // Assuming voucherCode as value
                    option.value = voucher.voucherDesc;
                    option.textContent = voucher.voucherDesc; // Display voucherDesc
                    option.dataset.id = voucher.id; // Store the voucher ID
                    option.dataset.voucherSubType = voucher.voucherSubType || ""; // Store voucherSubType in data attribute
                    option.dataset.voucherType = voucher.voucherType || ""; // Store voucherType in data attribute
                    option.dataset.purposeCode = voucher.purposeCode;
                    voucherDropdown.appendChild(option);
                });
            }

            // Add "Add New" option
            const addNewOption = document.createElement("option");
            addNewOption.value = "addNew";
            addNewOption.textContent = "Add New Voucher";
            voucherDropdown.appendChild(addNewOption);

            // Event listener for dropdown changes
            voucherDropdown.addEventListener("change", function () {
                const customVoucherInput = document.getElementById("customVoucherDesc");
                const voucherTypeDropdown = document.getElementById("voucherType");
                const voucherSubTypeInput = document.getElementById("voucherSubType");
				const purposeCodeInput = document.getElementById("purposeCode");
                if (this.value === "addNew") {
                    // Show input for new voucher description
                    customVoucherInput.style.display = "block";
                    customVoucherInput.value = ""; // Clear custom input
                    voucherTypeDropdown.value = ""; // Clear voucher type
                    voucherSubTypeInput.value = ""; // Clear voucher subtype
                 	purposeCodeInput.value = "";
                } else {
                    // Hide custom voucher input
                    customVoucherInput.style.display = "none";

                    // Autofill voucher type and subtype based on selected voucher
                    const selectedOption = this.options[this.selectedIndex];
                    voucherTypeDropdown.value = selectedOption.dataset.voucherType || ""; // Autofill or clear
                    const voucherId = selectedOption.dataset.id;
                    voucherSubTypeInput.value = selectedOption.dataset.voucherSubType || ""; // Autofill or clear
				const purposeCode = selectedOption.dataset.purposeCode;  
				purposeCodeInput.value = purposeCode || "";             
 				console.log(`Selected Voucher ID: ${voucherId}`);
                }
            });
        },
        error: function () {
            console.error("Failed to fetch voucher descriptions");
        },
    });
}

function convertImageToBase64() {
    const fileInput = document.getElementById("voucherLogo");
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
            voucherLogo=base64String; 
        };

        // Read file as Data URL (Base64)
       reader.readAsDataURL(file);
    } else {
        alert("Please select a valid image file.");
    }
}

