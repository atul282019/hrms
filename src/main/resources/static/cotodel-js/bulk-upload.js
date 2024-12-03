function validateFileUpload() {
    const fileInput = document.getElementById("up");
    const fileError = document.getElementById("fileError");
    const allowedExtensions = ["csv","xlsx"]; // Allowed file extensions
    const maxSizeInMB = 5; // Max file size in MB

    // Clear any previous errors
    fileError.innerText = "";

    // Check if a file is selected
    if (fileInput.files.length === 0) {
        fileError.innerText = "Please select a file to upload.";
        return false;
    }
	else{
		fileError.innerText = "";
	}
	
    const file = fileInput.files[0];
    const fileName = file.name;
    const fileSizeInMB = file.size / (1024 * 1024); // Convert size to MB
    const fileExtension = fileName.split('.').pop().toLowerCase(); // Extract file extension

    // Validate file extension
    if (!allowedExtensions.includes(fileExtension)) {
        fileError.innerText = `Invalid file type. Allowed types: ${allowedExtensions.join(", ")}`;
        return false;
    }

    // Validate file size
    if (fileSizeInMB > maxSizeInMB) {
        fileError.innerText = `File size exceeds ${maxSizeInMB} MB. Please upload a smaller file.`;
        return false;
    }

    // If validation passes
    fileError.innerText = "";
    return true;
}
  

function showClearButton() {
            var fileInput = document.getElementById("up");
            document.getElementById("clearButton").style.display="block";
             var clearButton = document.getElementById("clearButton");
            
             if (fileInput.value !== "") {
                 clearButton.style.display = "inline-block";
             } else {
                clearButton.style.display = "none";
        }
   }


document.getElementById("bulksubmit").onclick = function() {

 	 var regName = /^[a-zA-Z\s]*$/;
	 var onlySpace = /^$|.*\S+.*/;	 
	var up = document.getElementById("up").value;
	if(up ===""){
		document.getElementById("fileError").innerText="Please Upload File";
		return false;
	}
	else{
		document.getElementById("fileError").innerText="";
	}
	this.disabled = true;
	document.getElementById("employerId").value;
	var employerId = document.getElementById("employerId").value;
	var formData = new FormData(empdetailForm);
	formData.append("employerId",employerId);
	document.forms[0].action = "/saveBulkFile";
	document.forms[0].method = "post";
	document.forms[0].submit();
	
}
