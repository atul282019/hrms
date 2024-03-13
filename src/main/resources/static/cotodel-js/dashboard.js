function getProfileStatus(){
	//document.getElementById("overlay").style.display = "flex";
	var employerId = document.getElementById("employerId").value;
	var employeeId = "2";//document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "" + $('#ctx').attr('content') + "/getCompanyProfileStatus",
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
			$('#profile').html(data1.profile);
			//document.getElementById("perCtcBasic").value=data2[0].per_ctc;
			
		},
		error: function(e) {
			alert('Error: ' + e);
		}
	});
}

