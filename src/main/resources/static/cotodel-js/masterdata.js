function getStateMaster() {
  	$.ajax({
		type: "GET",
        url: ""+$('#ctx').attr('content')+"/getStateMaster",
           success: function(data){
            newData = data;
//            console.log(newData);
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
        url: ""+$('#ctx').attr('content')+"/getOrgMaster",
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
             option.text ="Select OrgType";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.org_type;
             option.value = values.org_type;
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
        url: ""+$('#ctx').attr('content')+"/getOrgMaster",
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
             option.text ="Select OrgType";
             option.value = "";
             x.add(option);
             }
             var option = document.createElement("option");
             option.text = values.org_type;
             option.value = values.org_type;
             x.add(option);

             count++;
             }   
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
} 
