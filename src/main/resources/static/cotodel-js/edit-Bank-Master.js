
let bankLogo="";

function saveBankMaster()
{ 
	var bankId = document.getElementById("bankId").value;
	var bankName = document.getElementById("bankNameInput").value;
	//var ifsc=document.getElementById("ifsc").value;
	var bankCode = document.getElementById("bankCodeInput").value;
	var updatedstatus=document.getElementById("statusInput").value;
	
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
        document.getElementById("bankLogoError").innerHTML="Please Enter bank logo";
        document.getElementById("bankLogo").focus();
        return false;
    }
	

	console.log("Printing bankname "+bankName);
	 	$.ajax({
		type: "POST",
	     url:"/savebankMaster",
          data: {
			  "id":bankId,
			"bankName": bankName,
			"bankCode": bankCode,
			"status": updatedstatus,
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





function convertImageToBase64() {
    const fileInput = document.getElementById("newbankLogo");
    const file = fileInput.files[0]; // Get the selected file

    if (file) {
        const reader = new FileReader();
        
        // When file is read, execute this function
        reader.onload = function(event) {
            const base64String = event.target.result.split(',')[1]; // Extract Base64 part

            // Display Base64 string in the output div (optional)
            //document.getElementById("base64Output").textContent = base64String;

            // priting base64 string on console
            //console.log("Base64 String", base64String);
            bankLogo=base64String; 
        };

        // Read file as Data URL (Base64)
       reader.readAsDataURL(file);
    } else {
        alert("Please select a valid image file.");
    }
}

 function enableEditing(spanId, inputId) {
        const span = document.getElementById(spanId);
        const input = document.getElementById(inputId);

        // Set the input value to the current span text for Bank Name or Bank Code
        if (input.tagName === "INPUT") {
            input.value = span.textContent.trim();
        }

        // For status, match the display text with the correct value
        if (input.tagName === "SELECT") {
            input.value = span.dataset.value; // Use the data-value for numeric mapping
        }

        // Show the input for editing
        span.style.display = 'none';
        input.style.display = 'inline-block';
        input.focus();
    }

    // Function to save the changes and switch back to span
    function saveAndDisplay(spanId, inputId) {
        const span = document.getElementById(spanId);
        const input = document.getElementById(inputId);

        // Update the span based on input type
        if (input.tagName === "INPUT") {
            span.textContent = input.value.trim(); // Update text for Bank Name or Bank Code
        } else if (input.tagName === "SELECT") {
            const selectedOption = input.options[input.selectedIndex];
            span.textContent = selectedOption.text; // Show "Active"/"Inactive" to the user
            span.dataset.value = selectedOption.value; // Store numeric value in data attribute
        }

        // Hide the input and show the updated span
        input.style.display = 'none';
        span.style.display = 'inline-block';
    }
 
  
  
  