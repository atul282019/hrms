function getEmployeeType(){
	
	$.ajax({
			type: "GET",
	        url: "/getEmployeeType",
	           success: function(data){
	            newData = data;
	            console.log(newData);
				$("#employeeType option").remove();
	            var obj = jQuery.parseJSON( data );
	             obj = obj.data;
	        	 var count=0;
	         	for (var key in obj) {

	             var values =  obj[key];
	             var x = document.getElementById("employeeType");
	             if(count==0){
	             var option = document.createElement("option");
	             option.text ="Select Employee Type";
	             option.value = "";
	             x.add(option);
	             }
	             var option = document.createElement("option");
	             option.text = values.empType;
	             option.value = values.id;
	             x.add(option);

	             count++;
	             }   
	         },
	         error: function(e){
	             alert('Error: ' + e);
	         }
	    });	
}

function getEmployeeMasterList(){
	var employerId=document.getElementById("employerId").value;
	var employeeId=document.getElementById("userDetailIdValue").value;
	
	$.ajax({
			type: "GET",
	        url: "/getEmployeeMasterList",
			data:{
				"orgId" : employerId,
				"employeeId": employeeId
			},
	           success: function(data){
	            newData = data;
	            console.log(newData);
				$("#reporting option").remove();
	            var obj = jQuery.parseJSON( data );
	             obj = obj.data;
	        	 var count=0;
	         	for (var key in obj) {

	             var values =  obj[key];
	             var x = document.getElementById("reporting");
	             if(count==0){
	             var option = document.createElement("option");
	             option.text ="Select Reporting Manager";
	             option.value = "";
	             x.add(option);
	             }
	             var option = document.createElement("option");
	             option.text = values.username;
	             option.value = values.id;
	             x.add(option);

	             count++;
	             }   
	         },
	         error: function(e){
	             alert('Error: ' + e);
	         }
	    });	
}

function getOfficeLocationMaster(){
	var employerId=document.getElementById("employerId").value;
	$.ajax({
			type: "GET",
	        url: "/getofficeLocationMaster",
			data:{
					"orgId" : employerId
				},
	           success: function(data){
	            newData = data;
	            console.log(newData);
				$("#location option").remove();
	            var obj = jQuery.parseJSON( data );
	             obj = obj.data;
	        	 var count=0;
	         	for (var key in obj) {

	             var values =  obj[key];
	             var x = document.getElementById("location");
	             if(count==0){
	             var option = document.createElement("option");
	             option.text ="Select Office Location";
	             option.value = "";
	             x.add(option);
	             }
	             var option = document.createElement("option");
	             option.text = values.officeAddress;
	             option.value = values.id;
	             x.add(option);
	             count++;
	             }   
	         },
	         error: function(e){
	             alert('Error: ' + e);
	         }
	    });	
}

function getStateMaster() {
  	$.ajax({
		type: "GET",
        url: "/getStateMaster",
           success: function(data){
            newData = data;
            console.log(newData);
			$("#stateCode option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("stateCode");
             if(count==0){
             var option = document.createElement("option");
             option.text ="Select State";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.state_name;
             option.value = values.state_code;
             x.add(option);

             count++;
             }   
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
} 

function getOrgMaster() {
  $.ajax({
		type: "GET",
        url: "/getOrgMaster",
           success: function(data){
            newData = data;
//            console.log(newData);
			$("#organizationType option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("organizationType");
             if(count==0){
             var option = document.createElement("option");
             option.text ="Select Organization Type";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.org_type;
             option.value = values.id;
             x.add(option);

             count++;
             }   
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
} 

function getOrgMaster2() {
  $.ajax({
		type: "GET",
        url: "/getOrgMaster",
           success: function(data){
            newData = data;
//            console.log(newData);
			$("#orgType2 option").remove();
            var obj = jQuery.parseJSON( data );
             obj = obj.data;
        	 var count=0;
         	for (var key in obj) {

             var values =  obj[key];
             var x = document.getElementById("orgType2");
             if(count==0){
             var option = document.createElement("option");
             option.text ="Select Organization Type";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.org_type;
             option.value = values.id;
             x.add(option);

             count++;
             }   
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
} 
function getDepartmentMasterList(){
	
	$.ajax({
			type: "GET",
	        url: "/getDepartmentMaster",
			dataType: "json",
	           success: function(data){
	            newData = data;
	            console.log("Department master",newData);
				$("#department option").remove();
	            //var obj = jQuery.parseJSON( data );
				var obj = newData;
	             obj = obj.data;
	        	 var count=0;
	         	for (var key in obj) {

	             var values =  obj[key];
	             var x = document.getElementById("department");
	             if(count==0){
	             var option = document.createElement("option");
	             option.text ="Select Department";
	             option.value = "";
	             x.add(option);
	             }
	             var option = document.createElement("option");
	             option.text = values.departmentName;
	             option.value = values.id;
	             x.add(option);

	             count++;
	             }   
	         },
	         error: function(e){
	             console.log('Error: ' + e);
	         }
	    });	
}