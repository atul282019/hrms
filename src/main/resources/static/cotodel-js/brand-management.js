
async function activateBrand(){
	alert("Offline Only");
	var createdby = document.getElementById("employerMobile").value;
	var orgId = document.getElementById("employerId").value;
	const outletCountRadioValue = document.querySelector('input[name="outletCount"]:checked').value;
	var multipleValue = document.getElementById("noOfOutlet").value;
	const salesMode = document.querySelector('input[name="salesMode"]:checked').id;

	let outletCount = outletCountRadioValue;

	if (outletCountRadioValue === "multipleOutlets") {
	    if (multipleValue.trim() === "") {
	        alert("Please enter number of outlets.");
			return false;
	    } else if (isNaN(multipleValue) || Number(multipleValue) <= 0) {
	        alert("Number of outlets must be a positive number.");
	        return false;
	    } else {
	        outletCount = multipleValue;
	    }
	}
	else{
		outletCount=1;
	}
	const clientKey = "client-secret-key"; // Extra security measure
	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

    // Concatenate data (must match backend)
	const dataString = orgId+createdby+clientKey+secretKey;

	const encoder = new TextEncoder();
	const data = encoder.encode(dataString);
	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
	const hashArray = Array.from(new Uint8Array(hashBuffer));
	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
	
	$.ajax({
		type: "POST",
	     url:"/addBrandDetails",
		 dataType: 'json',   
	      data: {
					"orgid":orgId,
					"storetypedesc":salesMode,
					"outletsNo":outletCount,
					"createdby":createdby,
					"clientKey":clientKey,
				    "hash":hashHex
			 },  		 
	        success:function(data){
	        var data1 = data.data;
	        console.log("data",data);
		    console.log("data1",data1);
			if(data.status==true){
				$('#PaymentSuccessModal').modal('show');
		        /* $('#AddVehicleModal').modal('hide');
				 getSupportTicketListList();

				 document.getElementById('bs-canvas-right2').style.right = '-378px';
				 document.getElementById('modal-overlay2').style.display = 'none';*/
			}else if(data.status==false){
				$('#PaymentSuccessModal').modal('show');
				//document.getElementById('bs-canvas-right2').style.right = '0';
			    //document.getElementById('modal-overlay2').style.display = 'block';
			}
	     },
	     error: function(e){
	         alert('Error: ' + e);
	     }
	});	
	
}
async function getAllGeographyValues() {
	      const inputs = document.querySelectorAll('#geoContainer .geo-input-group input');
	      const values = [];
		  
		  let hasError = false;

		     inputs.forEach(input => {
		         const trimmed = input.value.trim();

		         // Add validation: show red border if empty
		         if (!trimmed) {
		             input.style.border = "2px solid red";
		             hasError = true;
		         } else {
		             input.style.border = ""; // remove red border if previously shown
		             values.push({ name: trimmed });
		         }
		     });

		     // Stop if any field is empty
		     if (hasError) {
		        // alert("Please fill all geography fields.");
		         return false;
		     }
		  var createdby = document.getElementById("employerMobile").value;
		  var orgId = document.getElementById("employerId").value;
	      inputs.forEach(input => {
	        values.push({ name: input.value.trim() });
	      });

		  var createdby = document.getElementById("employerMobile").value;
		  	var orgId = document.getElementById("employerId").value;
		  	
		  	const clientKey = "client-secret-key"; // Extra security measure
		  	const secretKey = "0123456789012345"; // SAME KEY AS BACKEND

		      // Concatenate data (must match backend)
		  	const dataString = orgId+createdby+clientKey+secretKey;

		  	const encoder = new TextEncoder();
		  	const data = encoder.encode(dataString);
		  	const hashBuffer = await crypto.subtle.digest("SHA-256", data);
		  	const hashArray = Array.from(new Uint8Array(hashBuffer));
		  	const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
		  	
		  	$.ajax({
		  		type: "POST",
		  	     url:"/addGeograpgicDetails",
				 contentType: "application/json",
				 data:JSON.stringify({ 
		  	    
		  					"orgid":orgId,
							"geographicLocation":values,
		  					"createdby":createdby,
		  					"clientKey":clientKey,
		  				    "hash":hashHex
		  			}),		 
		  	        success:function(data){
		  	        var data = jQuery.parseJSON(data);
		  	        console.log("data",data);
		  			if(data.status==true){
		  				$('#PaymentSuccessModal').modal('show');
		  		        /* $('#AddVehicleModal').modal('hide');
		  				 getSupportTicketListList();

		  				 document.getElementById('bs-canvas-right2').style.right = '-378px';
		  				 document.getElementById('modal-overlay2').style.display = 'none';*/
		  			}else if(data.status==false){
		  				$('#PaymentSuccessModal').modal('show');
		  				//document.getElementById('bs-canvas-right2').style.right = '0';
		  			    //document.getElementById('modal-overlay2').style.display = 'block';
		  			}
		  	     },
		  	     error: function(e){
		  	         alert('Error: ' + e);
		  	     }
		  	});	
			
		  	
	    }
		

		async function getGeographicListByOrgId(){
			var orgId = document.getElementById("employerId").value;
			//alert("brandmanagement"+orgId);
			$.ajax({
				type: "GET",
			     url:"/getBrupiBrandGeoList",
				 dataType: 'json',   
			      data: {
							"orgid":orgId,
					 },  		 
			        success:function(data){
			        var data1 = data.data;
			        console.log("data",data);
				    console.log("data1",data1);
					if(data.status==true){
						//$('#PaymentSuccessModal').modal('show');
				        /* $('#AddVehicleModal').modal('hide');
						 getSupportTicketListList();

						 document.getElementById('bs-canvas-right2').style.right = '-378px';
						 document.getElementById('modal-overlay2').style.display = 'none';*/
					}else if(data.status==false){
						//$('#PaymentSuccessModal').modal('show');
						//document.getElementById('bs-canvas-right2').style.right = '0';
					    //document.getElementById('modal-overlay2').style.display = 'block';
					}
			     },
			     error: function(e){
			         alert('Error: ' + e);
			     }
			});	
			
		}