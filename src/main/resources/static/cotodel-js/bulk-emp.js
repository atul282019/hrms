async function saveBulkEmpUpload(){
	
	var fileInput = document.getElementById("up").value;
	var fileName = document.getElementById("fileName").value;
	var base64file = document.getElementById("base64Output").value;
	var createdby =  document.getElementById("employerName").value;
	var orgId = document.getElementById("employerId").value;

	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
	const dataString = orgId+fileName+base64file+createdby+clientKey+secretKey;

	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	
	if(fileInput =="" || fileInput == null){
		 document.getElementById("fileInputError").innerHTML="Please Select File";
		 return false;
	}
	else{
		document.getElementById("fileInputError").innerHTML="";
	}
	
	$.ajax({
		type: "POST",
	     url:"/saveBulkemp",
		 dataType: 'json',   
	      data: {
					"orgId":orgId,
					"fileName":fileName,
					"file":base64file,
					"createdby":createdby,
					"clientKey":clientKey,
				    "hash":hashHex
			 },  		 
	        success:function(data){
	        var data1 = data.data;
	       console.log("data",data);
		   console.log("data1",data1);
		   var data2 = jQuery.parseJSON(data1);
		   console.log("data2",data2);
		  
			if(data2.status==true){
				
				
				//document.getElementById("continueButton").disabled = false;
				 var success = data2.data.success;
				 //send all success ids to backend while stroing in html
				 const vehicleIds = success.map(item => item.id);
				 document.getElementById("empIds").value = vehicleIds.join(",");
				 
				 var fail = data2.data.fail;
				 // Set counts for both modals
				     document.getElementById("rejectConfirm").innerText = data2.data.failCount;
				     document.getElementById("rejectTotal").innerText = data2.data.totalCount;
				     document.getElementById("correctConfirm").innerText = data2.data.successCount;
				     document.getElementById("correctTotal").innerText = data2.data.totalCount;

				     if (success.length === 0) {
				         document.getElementById("continueButton").disabled = true;
				         $('#ModalBulkReject').modal('show');
				     } else {
				         document.getElementById("continueButton").disabled = false;
				         $('#ModalBulkConfirm').modal('show');
				     }

				 document.getElementById("BulkVoucherIssuanceTable").style.display="block";
				 document.getElementById("BulkVoucherIssuance-wrap").style.display="none";
				 
				 document.getElementById("failed").innerHTML=data2.data.failCount;
				 document.getElementById("success").innerHTML=data2.data.successCount;
				 document.getElementById("successTotal").innerHTML=data2.data.totalCount;
				 document.getElementById("failedTotal").innerHTML=data2.data.totalCount;
				 document.getElementById("failedUploadDownload").innerHTML=data2.data.failCount;
 				 document.getElementById("successUploadDownload").innerHTML=data2.data.successCount;
 				 document.getElementById("successUploadTotal").innerHTML=data2.data.totalCount;
 				 document.getElementById("failedUploadTotal").innerHTML=data2.data.totalCount;
				
				 var table = $('#successUpload').DataTable( {
			      destroy: true,	
				 // "dom": 'rtip',
				 //dom: 'Bfrtip',
			     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
			      "buttons": ["csv", "excel"],
			      "language": {"emptyTable": "No Data available"  },
			     "aaData": success,
				  "aoColumns": [ 
					{ "mData": null, "render": function (data, type, row, meta) {
					       return meta.row + 1;  // 👈 row index + 1
					   }},
			        { "mData": "userType"},
					{ "mData": "beneficiaryName"},
					{ "mData": "mobile"},
					   
					
				 	],
					
				});
				

					
				var table = $('#failedUpload').DataTable( {
					
		          destroy: true,	
				 // "dom": 'rtip',
				 //dom: 'Bfrtip',
			     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
	             "buttons": ["csv", "excel"],
	             "language": {"emptyTable": "No Data available"  },
		         "aaData": fail,
	      		  "aoColumns": [ 
					{ "mData": null, "render": function (data, type, row, meta) {
					       return meta.row + 1;  // 👈 row index + 1
					   }},
					{ "mData": "userType"},
					{ "mData": "beneficiaryName"},
					{ "mData": "mobile"},
					{ "mData": "message"},   
					   

	    		 	],
					
					   
				});
			}else if(data2.status==false){
				//document.getElementById("signinLoader").style.display="none";
				document.getElementById("fileInputError").textContent=data2.message;
				
			}
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
	
}

async function createBulkemp(){
	

	
	var createdby =  document.getElementById("employerName").value;
	var orgId = document.getElementById("employerId").value;
	var arrayofidStr = document.getElementById("empIds").value;
	const arrayofid = arrayofidStr.split(",");
	
		
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
	const dataString = orgId+createdby
	+arrayofid.join("")+clientKey+secretKey;

	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	

	//document.getElementById("signinLoader").style.display="flex";
	$.ajax({
		type: "POST",
	     url:"/createBulkemp",
		 dataType: 'json',   
		// traditional: true,
	      data: {
					"orgId":orgId,
					"arrayofid": arrayofid,					
					"CreatedBy":createdby,
					"clientKey":clientKey,
				    "hash":hashHex
			 },  		 
	        success:function(data){
	        var data1 = data.data;
	       console.log("data",data);
		   console.log("data1",data1);
		   var data2 = jQuery.parseJSON(data1);
		   console.log("data2",data2);
			//document.getElementById("signinLoader").style.display="none";
			if(data2.status==true){
				//document.getElementById("continueButton").disabled = false;
				 var success = data2.data.success;
				 //send all success ids to backend while stroing in html
			//	 const vehicleIds = success.map(item => item.id);
			//	 document.getElementById("vehicleIds").value = vehicleIds.join(",");
				 
				 var fail = data2.data.fail;
				/* if (success.length === 0 ) {
				         document.getElementById("continueButton").disabled = true;
				     } else {
				         document.getElementById("continueButton").disabled = false;
				     }

				 document.getElementById("BulkVoucherIssuanceTable").style.display="block";
				 document.getElementById("BulkVoucherIssuance-wrap").style.display="none";
				 
				 document.getElementById("failed").innerHTML=data2.data.failCount;
				 document.getElementById("success").innerHTML=data2.data.successCount;
				 document.getElementById("successTotal").innerHTML=data2.data.totalCount;
				 document.getElementById("failedTotal").innerHTML=data2.data.totalCount;
				 document.getElementById("failedUploadDownload").innerHTML=data2.data.failCount;
 				 document.getElementById("successUploadDownload").innerHTML=data2.data.successCount;
 				 document.getElementById("successUploadTotal").innerHTML=data2.data.totalCount;
 				 document.getElementById("failedUploadTotal").innerHTML=data2.data.totalCount;*/
				

		window.location.href="/manageEmployee";
					

			}else if(data1.status==false){
				document.getElementById("signinLoader").style.display="none";
				
			}
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
	
}

function convertImageToBase64() {
           const fileInput = document.getElementById('up');
           const output = document.getElementById('base64Output');
		   const outputFileName = document.getElementById('fileName');
		  
		  
           if (!fileInput.files || fileInput.files.length === 0) {
			 document.getElementById("fileInputError").innerHTML="Please select file";
			 return;
           }
           var filePath = fileInput.value;
              // var allowedExtensions =  /(\.jpg|\.jpeg|\.png|\.gif)$/i;
			   var allowedExtensions =  /(\.xlsx)$/i;
               if (!allowedExtensions.exec(filePath)) {
				  document.getElementById("fileInputError").innerHTML="Invalid file type";
                   fileInput.value = '';
                   return false;
               } 
			   else{
				document.getElementById("fileInputError").innerHTML="";
			   }
			  		
           const file = fileInput.files[0];	   
           const reader = new FileReader();
           reader.onload = function(event) {
               const base64String = event.target.result.split(',')[1]; 
               output.value = base64String; 
			   console.log("FileName:", fileInput.files[0].name);
			   outputFileName.value =fileInput.files[0].name;
			
			   document.querySelector('.form-group.choose-file .form-control').value = fileInput.files[0].name;
           };
           reader.readAsDataURL(file);
		   document.getElementById("bulksubmit").disabled=false;
    }
	