

let geoCount = 1;

	  function addGeography() {
	    geoCount++;
	    const container = document.getElementById("geoContainer");

	    const newGroup = document.createElement("div");
	    newGroup.className = "geo-input-group";

	    newGroup.innerHTML = `
	      <span class="geo-number">#${geoCount}</span>
	      <input type="text" placeholder="Enter Region">
	      <span class="delete-geo" onclick="removeGeography(this)">
	        <img src="img/delete.svg" alt="delete">
	      </span>
	    `;

	    container.appendChild(newGroup);
	  }

	  function removeGeography(element) {
	    const group = element.closest('.geo-input-group');
	    group.remove();
	    updateNumbers();
	  }

	  function updateNumbers() {
	    const groups = document.querySelectorAll('#geoContainer .geo-input-group');
	    groups.forEach((group, index) => {
	      const numberSpan = group.querySelector('.geo-number');
	      numberSpan.textContent = `#${index + 1}`;

	      const deleteSpan = group.querySelector('.delete-geo');
	      // Show delete only if not first item
	      if (index === 0) {
	        if (deleteSpan) deleteSpan.remove();
	      } else {
	        if (!deleteSpan) {
	          const newDelete = document.createElement('span');
	          newDelete.className = 'delete-geo';
	          newDelete.onclick = function () { removeGeography(this); };
	          newDelete.innerHTML = `<img src="img/delete.svg" alt="delete">`;
	          group.appendChild(newDelete);
	        }
	      }
	    });
	    geoCount = groups.length;
	  }
	  
async function activateBrand(){
	alert("Offline Only");
	var createdby = document.getElementById("userMobile").value;
	var orgId = document.getElementById("employerId").value;
	var brandName = document.getElementById("brandName").value;
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
					"brandname":brandName,
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
		  var createdby = document.getElementById("userMobile").value;
		  var orgId = document.getElementById("employerId").value;
	     // inputs.forEach(input => {
	    //    values.push({ name: input.value.trim() });
	    //  });

		  var createdby = document.getElementById("userMobile").value;
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
					if(data.status==true && data1.length >=0){
						
						 document.getElementById('geoSave').style.display = 'none';
						 document.getElementById('geoEdit').style.display = 'block';
						 const geoContainer = document.getElementById("geoContainerView");
						   const geoList = data.data[0].geographicLocation;

						   geoList.forEach((geo, index) => {
						       const div = document.createElement("div");
						       div.className = "geo-tag";
						       div.innerHTML = `#${index + 1} <strong>${geo.name}</strong>`;
						       geoContainer.appendChild(div);
						     });
					}else if(data.status==false){

						document.getElementById('geoSave').style.display = 'block';
						document.getElementById('geoEdit').style.display = 'none';
					}
			     },
			     error: function(e){
			         alert('Error: ' + e);
			     }
			});	
			
		}
		
		async function getBrandOutletList(){
					var orgId = document.getElementById("employerId").value;
					//alert("brandmanagement"+orgId);
					$.ajax({
						type: "GET",
					     url:"/getBrandOutletList",
						 dataType: 'json',   
					      data: {
									"orgid":orgId,
							 },  		 
					        success:function(data){
					        var data1 = data.data;
							if(data.status==true && data1.length >=0){
								
								document.getElementById('outletNo').innerHTML = data1[0].outletsNo;
								document.getElementById('storeType').innerHTML = data1[0].storetypedesc;

								 document.getElementById('activateBrand').style.display = 'none';
								 document.getElementById('activateGeo').style.display = 'block';
							}else if(data.status==false){
								document.getElementById('id="activateBrand"').style.display = 'block';
							    document.getElementById('activateGeo').style.display = 'none';
							}
					     },
					     error: function(e){
					         alert('Error: ' + e);
					     }
					});	
					
				}