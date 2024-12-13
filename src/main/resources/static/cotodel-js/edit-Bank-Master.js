
let bankLogo="";

function saveEditedBankMaster()
{ 
    /*
	var bankId = document.getElementById("bankId").value;
	var bankName = document.getElementById("bankNameInput").value;
	//var ifsc=document.getElementById("ifsc").value;
	var bankCode = document.getElementById("bankCodeInput").value;
	var updatedstatus=document.getElementById("statusInput").value;
	var oldbankLogo = document.getElementById("oldbankLogo").value;
     if(bankLogo==="")
     {
        bankLogo=oldbankLogo;
     }
	*/
	var bankId = document.getElementById("bankId").value;

    // Get Bank Name (check both span and input field)
    var bankName = document.getElementById("bankName").value.trim();
    //var bankName = bankNameInput || document.getElementById("bankNameText").textContent.trim();

    // Get Bank Code (check both span and input field)
    var bankCode = document.getElementById("bankCode").value.trim();
    //var bankCode = bankCodeInput || document.getElementById("bankCodeText").textContent.trim();

    // Get Status (check both select and span data-value)
    var statusInput = document.getElementById("statusInput").value;
    var updatedStatus = statusInput || document.getElementById("statusText").dataset.value;

    // Get Bank Logo (default to old logo if new logo is not provided)
    var newbankLogo = bankLogo||document.getElementById("oldbankLogo").value;
    

	//console.log("Printing data "+data);
	 	$.ajax({
		type: "POST",
	     url:"/savebankMaster",
          data: {
			  "id":bankId,
			"bankName": bankName,
			"bankCode": bankCode,
			"status": updatedStatus,
			"bankLogo": newbankLogo
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
	
				 document.getElementById("saveBankmaster").disabled=false;
				 $('#otmsgdiv').delay(5000).fadeOut(400);
                 //window.location.reload().delay(5000);
                 setTimeout(function() {
                    window.location.href = "/displaybankMaster"; // Update with your previous page URL
                }, 1000);
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


function validateAndConvertImageToBase64() {
        const fileInput = document.getElementById('newbankLogo');
        const file = fileInput.files[0];

        // Validate file input
        if (!file) {
            alert('No file selected. Please choose a file.');
            return;
        }

        const allowedTypes = ['image/jpeg', 'image/png'];
        if (!allowedTypes.includes(file.type)) {
            alert('Invalid file type. Please upload a JPG or PNG image.');
            fileInput.value = ''; // Clear the input
            return;
        }

        // Convert to Base64
        const reader = new FileReader();
        reader.onload = function(event) {
            const base64String = event.target.result.split(',')[1];
            bankLogo=base64String;
            // You can now use the Base64 string as needed
        };
        reader.onerror = function() {
            console.log('Error reading file.');
        };
        reader.readAsDataURL(file);
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
 
  
  
  