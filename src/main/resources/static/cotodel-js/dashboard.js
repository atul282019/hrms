function getProfileStatus(){
	//document.getElementById("overlay").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	var employeeId = "2";//document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "/getCompanyProfileStatus",
		data: {
				"employerId":employerId,
				 "employeeId" :employeeId
		},
		beforeSend: function(xhr) {
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			//var data2 = data1.data;
			//$('#profile').html(data1.profile);
			$('#profileComplete').html(data1.data.profileComplete);
			
			//var e1 = document.getElementById("profile");
			document.getElementById("profile").style.width = 132*data1.data.profileComplete+"px";
        	//e1.style.width = data1.profile+""+px;
			//document.getElementById("perCtcBasic").value="5"data2[0].per_ctc;
			
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
} 

/* try executing this function if above dosent work
function getProfileStatus() { 
    var employerId = document.getElementById("employerId").value;
    var employeeId = "2";  // You can change this to dynamically fetch employeeId if necessary
    $.ajax({
        type: "POST",
        url: "/getCompanyProfileStatus",
        data: {
            "employerId": employerId,
            "employeeId": employeeId
        },
        beforeSend: function(xhr) {
            // Optional: Show loading indicator
        },
        success: function(data) {
            var data1 = jQuery.parseJSON(data); // Parse the response
            var profileComplete = data1.data.profileComplete;

            // Update the profile completion text
            document.getElementById("profileComplete").innerText = profileComplete ;

            // Calculate progress bar width (assuming 3 steps)
            var progressPercentage = (profileComplete / 3) * 100; // 3 is the total number of steps
            document.getElementById("profile").style.width = progressPercentage + "%"; // Set the width in percentage
        },
        error: function(e) {
            alert('Error: ' + e);  // Handle error
        }
    });
}*/
