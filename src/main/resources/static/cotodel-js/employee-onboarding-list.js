function getEmployeeOnboarding() {

	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeOnboarding",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			//document.getElementById("signinLoader").style.display="none";
			
            function populateTable(data) {
            var tableBody = $('#example1 tbody');
            
            $.each(data, function(index, item) {
                var row = $('<tr>');
                row.append($('<td>').text(item.name));
                row.append($('<td>').text(item.depratment));
                row.append($('<td>').text(item.jobTitle));
                row.append($('<td >').text(item.empOrCont));
              /*  row.append($('<td >'));*/
                tableBody.append(row);
            });
        }
        // Call the function to populate table with JSON list data
        populateTable(data1.data);    
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}


function getEmployeeOnboardingFailList() {

	var employeeId= document.getElementById("employeeId").value;
	var employerId=document.getElementById("employerId").value;
	//document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "GET",
		url: ""+$('#ctx').attr('content') + "/getEmployeeOnboarding",
		data: {
			"employeeId": employeeId,
			"employerId": employerId,
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			//document.getElementById("signinLoader").style.display="none";
			
            function populateTable(data) {
            var tableBody = $('#example2 tbody');
            
            $.each(data, function(index, item) {
                var row = $('<tr>');
                row.append($('<td>').text(item.name));
                row.append($('<td>').text(item.depratment));
                row.append($('<td>').text(item.jobTitle));
                row.append($('<td >').text(item.empOrCont));
              /*  row.append($('<td >'));*/
                tableBody.append(row);
            });
        }
        // Call the function to populate table with JSON list data
        populateTable(data1.data);    
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}