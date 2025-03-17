function getProfileStatus(){
	//document.getElementById("overlay").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	var employeeId = "2";
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
			try {
	               let parsedData = typeof data === "string" ? JSON.parse(data) : data;

	               if (parsedData.data && parsedData.data.profileComplete) {
	                   let profileComplete = parsedData.data.profileComplete;

	                   if (profileComplete === 3) {
						let anchorStart = document.getElementById("anchorStart");
						anchorStart.textContent = "Complete";
					    anchorStart.href = ""; 
	                    document.getElementById("btnsetupStart").style.display = "none";
	                   }

	                   $('#profileComplete').html(profileComplete);
	                   document.getElementById("profile").style.width = (132 * parseInt(profileComplete)) + "px";
	               } else {
	                   console.error("Unexpected response structure:", parsedData);
	               }
	           } catch (error) {
	               console.error("Error parsing JSON:", error);
	           }
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
} 
