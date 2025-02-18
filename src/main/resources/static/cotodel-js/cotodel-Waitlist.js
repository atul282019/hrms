function saveWaitlistData() {
    // Collect form values
    var companyName = document.getElementById("companyName").value;
    var companySize = document.querySelector("input[name='companySize']:checked");
    var industry = document.querySelector("input[name='industry']:checked");
    var contactPerson = document.getElementById("contactPerson").value;
    var contactNumber = document.getElementById("contactNumber").value;
    var emailId = document.getElementById("emailId").value;
    
    var companySizeValue = companySize ? companySize.value : "";
    var industryValue = industry ? industry.value : "";
    
    // Validation Patterns
    var companyNamePattern = /^[a-zA-Z0-9/ ]+$/; // Allows letters, numbers, spaces, and '/'
    var namePattern = /^[a-zA-Z ]+$/; // Only allows letters and spaces
    var contactNumberPattern = /^[6-9]\d{9}$/; // Indian mobile number validation (10 digits, starts with 6-9)
    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // Standard email format
    
    // Validate required fields
    if (!companyName || !contactPerson || !contactNumber || !emailId || !companySizeValue || !industryValue) {
        alert("Please fill all required fields.");
        return;
    }
     
    // Apply validation rules
    if (!companyNamePattern.test(companyName)) {
        alert("Company Name can only contain letters, numbers, spaces, and '/'");
        return;
    }
    
    if (!namePattern.test(contactPerson)) {
        alert("Contact Person Name can only contain letters and spaces.");
        return;
    }
    
    if (!contactNumberPattern.test(contactNumber)) {
        alert("Please enter a valid 10-digit Contact Number starting with 6-9.");
        return;
    }
    
    if (!emailPattern.test(emailId)) {
        alert("Please enter a valid Email ID.");
        return;
    }

    // Prepare request data
    var formData = {
        companyName: companyName,
        contactPerson: contactPerson,
        contactNumber: contactNumber,
        emailId: emailId,
        companySize: companySizeValue,
        industry: industryValue
    };

    // Send AJAX request
    $.ajax({
        type: "POST",
        url: "/userWaitList", // Backend API endpoint
        data: { formData },
        
        success: function (response) {
            if (response.status === true) {
                document.querySelector("form").reset(); // Reset form
                $("#waitlistApproved").modal("show"); // Show success modal
            } else {
                alert("Error: " + response.msg);
            }
        },
        error: function (error) {
            alert("Something went wrong. Please try again later.");
        }
    });
}
