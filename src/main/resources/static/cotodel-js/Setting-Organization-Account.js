function fetchOrgDetails(type) {
	var employerid = document.getElementById("employerId").value;
	const gstContainer = document.getElementById('gstInputContainer');
	 const panContainer = document.getElementById('panInputContainer');

	document.getElementById("signinLoader").style.display="flex";
		
        $.ajax({
			type: "POST",
           url:"/getpayrollDetails", 
		   data: {
				"employerId": employerid,
	      		 },
            dataType: 'json',
            success: function (response) {
				console.log("company Data"+response)
				document.getElementById("signinLoader").style.display="none";
					
				if (response.status) {
				    const data = response.data;
                // Auto-fill the form fields with fetched data
               // document.getElementById("gstnNo").value = data.gstnNo || "";

				if (type === "GST") {
					gstContainer.style.display = 'block';
					panContainer.style.display = 'none';
                    document.getElementById("gstnNo").value = data.gstnNo || "";
                } else if (type === "PAN") {
					gstContainer.style.display = 'none';
					        panContainer.style.display = 'block';
                    document.getElementById("panNo").value = data.pan || "";
                }

          }

			},
            error: function ( status, error) {
                console.error("Failed to fetch data:", status, error);
                alert("Unable to fetch employer data. Please try again.");
            }
        });
    }
	function validateInput() {
	    const selectedRadio = document.querySelector('input[name="orgRadio"]:checked').value;
	    const gstInput = document.getElementById('gstnNo');
	    const panInput = document.getElementById('panNo');
	    const gstContainer = document.getElementById('gstInputContainer');
	    const panContainer = document.getElementById('panInputContainer');
		const currentForm = document.getElementById('Form1');
	    const additionalForm = document.getElementById('Form2');
	    const errorElement = document.getElementById('bankNameError');
	    let isValid = true;

	    // Clear previous error messages and reset input borders
	    errorElement.textContent = '';
	    gstContainer.style.borderColor = '';
	    panContainer.style.borderColor = '';

	    // Color to apply when there's an error
	    const errorColor = 'red'; // The color you want to use

	    if (selectedRadio === 'GST') {
	        const gstValue = gstInput.value.trim();
	        const gstRegex = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/;

	        if (!gstValue) {
	            errorElement.textContent = 'GST Number is required.';
	            gstContainer.style.borderColor = errorColor;
	            isValid = false;
	        } else if (!gstRegex.test(gstValue)) {
	            errorElement.textContent = 'Invalid GST Number. Please enter a valid GST.';
	            gstContainer.style.borderColor = errorColor;
	            isValid = false;
				
				getDetailByGSTNo(gstValue);
	        }
	    } else if (selectedRadio === 'PAN') {
	        const panValue = panInput.value.trim();
	        const panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;

	        if (!panValue) {
	            errorElement.textContent = 'PAN Number is required.';
	            panContainer.style.borderColor = errorColor;
	            isValid = false;
	        } else if (!panRegex.test(panValue)) {
	            errorElement.textContent = 'Invalid PAN Number. Please enter a valid PAN.';
	            panContainer.style.borderColor = errorColor;
	            isValid = false;
	        }
	    }

	    if (isValid) {
	        // Reset border color to default (if any)
			
	        gstContainer.style.borderColor = '';
	        panContainer.style.borderColor = '';
			currentForm.style.display = 'none';

	        // Show the additional form
	        additionalForm.style.display = 'block';
			toggleVisibility();
			
	        //alert('Validation successful!');
	        // Proceed to the next step (submit form or any other action)
	    }
	}
	function goBackToForm1() {
	    const currentForm = document.getElementById('Form2');
	    const form1 = document.getElementById('Form1');

	    // Hide Form 2 (Additional Form)
	    currentForm.style.display = 'none';

	    // Show Form 1 (GST/PAN input form)
	    form1.style.display = 'block';
		//window.location.href="/Setting-Organization-Account";
	}
	
	// Function to toggle visibility of PAN text box based on selected radio button
	function toggleVisibility() {
	    const selectedRadio = document.querySelector('input[name="orgRadio"]:checked').value;
	    const gstContainer = document.getElementById('gstInputContainer1');
	    const panContainer = document.getElementById('panInputContainer1');
		const gstInput = document.getElementById('gstnNo').value;
		const panInput = document.getElementById('panNo').value;

	    // Check which radio button is selected (GST or PAN)
		gstContainer.style.display = 'block';
			        panContainer.style.display = 'none';
					document.getElementById('gstnNo1').value=gstInput;
					
	     if (selectedRadio === 'PAN') {
	        // If PAN is selected, show PAN input and hide GST input
	        gstContainer.style.display = 'none';
	        panContainer.style.display = 'block';
			document.getElementById('panNo1').value=panInput;
	    }
	}
	function addDynamicIcon(inputElement) {
	    // Add the 'filled' class if there is input text; otherwise, remove it
	    if (inputElement.value.trim() !== "") {
	        inputElement.classList.add("filled"); // Adds the icon when input is filled
	    } else {
	        inputElement.classList.remove("filled"); // Removes the icon if input is cleared
	    }
	}
	function continueToForm3() {
	    const currentForm1 = document.getElementById('Form1');
	    const currentForm2 = document.getElementById('Form2');
	    const nextForm3 = document.getElementById('Form3'); // Assuming you have Form3 defined
		const checkboxsection = document.getElementById('checkboxsection');

	    // Hide both Form1 and Form2
	    currentForm1.style.display = 'none';
	    currentForm2.style.display = 'none';

	    // Show Form3
	    nextForm3.style.display = 'block';
		checkboxsection.display = 'block';
		
		
	}
	
	function showOtpSection() {
	        // Hide the Back and Authenticate buttons
	        document.getElementById('backButton').style.display = 'none';
	        document.getElementById('authButton').style.display = 'none';
	        
	        // Show the OTP section
	        document.getElementById('otpsection').style.display = 'block';
	    }
	
		function goBackToForm2() {
			    const currentForm = document.getElementById('Form3');
			    const form1 = document.getElementById('Form2');
				const otpsection = document.getElementById('otpsection');
				document.getElementById('backButton').style.display = 'block';
		        document.getElementById('authButton').style.display = 'block';

			    // Hide Form 2 (Additional Form)
			    currentForm.style.display = 'none';otpsection

			    // Show Form 1 (GST/PAN input form)
			    form1.style.display = 'block';
				otpsection.style.display = 'none';
			}
			
function getDetailByGSTNo(gstValue){
	

	$.ajax({
		type: "POST",
	   url:"/getpayrollDetails", 
	   data: {
			"gstnNo": gstValue,
	  		 },
	    dataType: 'json',
	    success: function (response) {
			console.log("company Data"+response)
			document.getElementById("signinLoader").style.display="none";
				
			if (response.status) {
			    const data = response.data;
	        // Auto-fill the form fields with fetched data
	       // document.getElementById("gstnNo").value = data.gstnNo || "";

			if (type === "GST") {
				gstContainer.style.display = 'block';
				panContainer.style.display = 'none';
	            document.getElementById("gstnNo").value = data.gstnNo || "";
	        } else if (type === "PAN") {
				gstContainer.style.display = 'none';
				        panContainer.style.display = 'block';
	            document.getElementById("panNo").value = data.pan || "";
	        }

	  }

		},
	    error: function ( status, error) {
	        console.error("Failed to fetch data:", status, error);
	        alert("Unable to fetch employer data. Please try again.");
	    }
	});
	
}