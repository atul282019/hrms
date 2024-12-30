var uploadVoucherIcon_base64=null;
var uploadMccIcon_base64=null;
var uploadMccMainIcon_base64=null;
/*function fetchMccMasterList() {
    var voucherId = document.getElementById("VoucherId").value;

    // jQuery AJAX request
    $.ajax({
        url: '/getvoucherTypeMaster',
        type: 'GET',
        data: { "id": voucherId },
        dataType: 'json',
        success: function (data) {
            if (data && data.data) {
                const mccData = data.data[0]; // Assuming the response contains an array

                // Autofill form fields
                $('#voucherName').val(mccData.voucherName);
                $('#purposeCode').val(mccData.purposeCode);
                $('#purposeDesc').val(mccData.purposeDesc);
                $('#mccCode').val(mccData.mcc);
                $('#mccDesc').val(mccData.mccDesc);

                // Handle Voucher Icon
                if (mccData.voucherIcon) {
                    $('#voucherIcon').attr('src', 'data:image/png;base64,' + mccData.voucherIcon).show();
                    $('#uploadVoucherIcon')
                } else {
                    $('#voucherIcon').hide();
                    $('#uploadVoucherIcon').show();
                }

                // Handle MCC Icon
                if (mccData.mccIcon) {
                    $('#mccIcon').attr('src', 'data:image/png;base64,' + mccData.mccIcon).show();
                    $('#uploadMccIcon').hide();
                } else {
                    $('#mccIcon').hide();
                    $('#uploadMccIcon').show();
                }

                // Handle MCC Main Icon
                if (mccData.mccMainIcon) {
                    $('#mccMainIcon').attr('src', 'data:image/png;base64,' + mccData.mccMainIcon).show();
                    $('#uploadMccMainIcon').hide();
                } else {
                    $('#mccMainIcon').hide();
                    $('#uploadMccMainIcon').show();
                }
            } else {
                console.error('No data found');
            }
        },
        error: function (error) {
            console.error('Error fetching MCC Master List:', error);
        }
    });
}*/

function fetchMccMasterList() {
    var voucherId = document.getElementById("VoucherId").value;

    // jQuery AJAX request
    $.ajax({
        url: '/getvoucherTypeMaster',
        type: 'GET',
        data: { "id": voucherId },
        dataType: 'json',
        success: function (data) {
            if (data && data.data) {
                const mccData = data.data[0]; // Assuming the response contains an array

                // Autofill form fields
                $('#voucherName').val(mccData.voucherName);
                $('#purposeCode').val(mccData.purposeCode);
                $('#purposeDesc').val(mccData.purposeDesc);
                $('#mccCode').val(mccData.mcc);
                $('#mccDesc').val(mccData.mccDesc);

                // Handle Voucher Icon
                if (mccData.voucherIcon) {
					uploadVoucherIcon_base64 = trimBase64Prefix('data:image/png;base64,' + mccData.voucherIcon);
                    $('#voucherIcon').attr('src', 'data:image/png;base64,' + mccData.voucherIcon).show();
                } else {
                    $('#voucherIcon').hide();
                }
                $('#uploadVoucherIcon').show();

                // Handle MCC Icon
                if (mccData.mccIcon) {
					uploadMccIcon_base64 = trimBase64Prefix('data:image/png;base64,' + mccData.mccIcon);
                    $('#mccIcon').attr('src', 'data:image/png;base64,' + mccData.mccIcon).show();
                } else {
                    $('#mccIcon').hide();
                }
                $('#uploadMccIcon').show();

                // Handle MCC Main Icon
                if (mccData.mccMainIcon) {
					uploadMccMainIcon_base64 = trimBase64Prefix('data:image/png;base64,' + mccData.mccMainIcon);
                    $('#mccMainIcon').attr('src', 'data:image/png;base64,' + mccData.mccMainIcon).show();
                } else {
                    $('#mccMainIcon').hide();
                }
                $('#uploadMccMainIcon').show();
            } else {
                console.error('No data found');
            }
        },
        error: function (error) {
            console.error('Error fetching MCC Master List:', error);
        }
    });
}

function trimBase64Prefix(base64String) {
    return base64String.replace(/^data:image\/(png|jpeg|jpg);base64,/, '');
}
// Utility function to convert a file to Base64
function fileToBase64(file, callback) {
    const reader = new FileReader();
    reader.onload = function (event) {
        callback(event.target.result);
    };
    reader.onerror = function (error) {
        console.error('Error converting file to Base64:', error);
    };
    reader.readAsDataURL(file);
}

// Function to validate the file type (only .jpg, .jpeg, .png)
function isValidImage(file) {
    const validExtensions = ['image/jpeg', 'image/png'];
    return validExtensions.includes(file.type);
}

// Event listeners for image file inputs
$('#uploadVoucherIcon').on('change', function () {
    const file = this.files[0];
    if (file && isValidImage(file)) {
        fileToBase64(file, function (base64String) {
			 uploadVoucherIcon_base64=trimBase64Prefix(base64String);
            console.log('Base64 Voucher Icon:', uploadVoucherIcon_base64);
			
            $('#voucherIcon').attr('src', base64String).show();
            $('#uploadVoucherIcon').hide();
        });
    } else {
        alert('Please select a valid image file (.jpg, .jpeg, .png).');
        this.value = ''; // Reset the file input to prevent non-image file
    }
});

$('#uploadMccIcon').on('change', function () {
    const file = this.files[0];
    if (file && isValidImage(file)) {
        fileToBase64(file, function (base64String) {
			//uploadMccIcon_base64=base64String;
			uploadMccIcon_base64=trimBase64Prefix(base64String);
            console.log('Base64 MCC Icon:', uploadMccIcon_base64);
            $('#mccIcon').attr('src', base64String).show();
            $('#uploadMccIcon').hide();
        });
    } else {
        alert('Please select a valid image file (.jpg, .jpeg, .png).');
        this.value = ''; // Reset the file input to prevent non-image file
    }
});

$('#uploadMccMainIcon').on('change', function () {
    const file = this.files[0];
    if (file && isValidImage(file)) {
        fileToBase64(file, function (base64String) {
			uploadMccMainIcon_base64=trimBase64Prefix(base64String);
            console.log('Base64 MCC Main Icon:', uploadMccMainIcon_base64);
            $('#mccMainIcon').attr('src', base64String).show();
            $('#uploadMccMainIcon').hide();
        });
    } else {
        alert('Please select a valid image file (.jpg, .jpeg, .png).');
        this.value = ''; // Reset the file input to prevent non-image file
    }
});


function saveVoucherData() {
    // Gather form data
	var voucherId = document.getElementById("VoucherId").value.trim();
	    var voucherName = document.getElementById("voucherName").value.trim();
	    var purposeCode = document.getElementById("purposeCode").value.trim();
	    var purposeDesc = document.getElementById("purposeDesc").value.trim();
	    var mccCode = document.getElementById("mccCode").value.trim();
	    var mccDesc = document.getElementById("mccDesc").value.trim();
	    var status = document.getElementById("status").value.trim();



    // Send the AJAX request
    $.ajax({
        type: "POST",
        url: "/savevoucherTypeMaster",
		data: {
					  "id":voucherId,
					"purposeCode": purposeCode,
					"purposeDesc": purposeDesc,
					"mcc": mccCode,
					"mccDesc":mccDesc,
					"status":status,
					"voucherName":voucherName,
					"voucherIcon":uploadVoucherIcon_base64,
					"mccIcon":uploadMccIcon_base64,
					"mccMainIcon":uploadMccMainIcon_base64,

					
		      		 },dataType: "json", 
		      		       		 
        success: function (response) {
            if (response.status === "SUCCESS") {
                $("#otsuccmsg").text("Data saved successfully!");
                $("#otmsgdiv").show();
                setTimeout(function () {
                    window.location.href = "/displayvoucherMaster"; // Adjust URL as needed
                }, 1000);
            } else {
                $("#otfailmsg").text(response.message || "Failed to save data.");
                $("#otfailmsgDiv").show();
            }
        },
        error: function (error) {
            alert("Error while saving data: " + error.responseText);
        }
    });
}

// Helper function to convert images to Base64
function getBase64Image(inputSelector) {
    var file = $(inputSelector)[0].files[0];
    if (file) {
        var reader = new FileReader();
        reader.onload = function (e) {
            return e.target.result.split(",")[1]; // Remove Base64 header
        };
        reader.readAsDataURL(file);
    }
    return null;
}

