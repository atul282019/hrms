

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
	var createdby = document.getElementById("userMobile").value;
	var orgId = document.getElementById("employerId").value;
	var brandName = document.getElementById("brandName").value;
	const outletCountRadioValue = document.querySelector('input[name="outletCount"]:checked').value;
	var multipleValue = document.getElementById("noOfOutlet").value;
	const salesMode = document.querySelector('input[name="salesMode"]:checked').id;

	let outletCount = outletCountRadioValue;
	if (brandName === "") {
		   document.getElementById("brandNameError").innerHTML="Please enter brand name.";
	       document.getElementById("outletName").focus();
	       return false;
	   }
	if (outletCountRadioValue === "multipleOutlets") {
	    if (multipleValue.trim() === "") {
			document.getElementById("outletCountError").innerHTML="Please enter number of outlets.";
			return false;
	    } else if (isNaN(multipleValue) || Number(multipleValue) <= 0) {
			document.getElementById("outletCountError").innerHTML="Number of outlets must be a positive number.";
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
				getBrandOutletList();
				document.getElementById("bs-canvas-right2").classList.remove("show-canvas");
				document.getElementById("modal-overlay1").style.display = "none";
				$('#PaymentSuccessModal').modal('show');
		        /* $('#AddVehicleModal').modal('hide');
				 getSupportTicketListList();

				 document.getElementById('bs-canvas-right2').style.right = '-378px';
				 document.getElementById('modal-overlay2').style.display = 'none';*/
			}else if(data.status==false){
				getBrandOutletList();
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
						document.getElementById('activateBrand').style.display = 'block';
					    document.getElementById('activateGeo').style.display = 'none';
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
						getGeographicListByOrgId();
						document.getElementById('editgio').style.display="block";
						document.getElementById("modal-overlay2").style.display = "none";
					    document.getElementById("bs-canvas-right3").classList.remove("show-canvas");
		  		      
		  			}else if(data.status==false){
		  				$('#PaymentSuccessModal').modal('show');
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
		
async function addBrandOutlet(){
			var orgId = document.getElementById("employerId").value;
			var outletName = document.getElementById("outletName").value;
			var outletType = document.getElementById("outletType").value;
			var outletManager = document.getElementById("outletManager").value;
			var outletContact = document.getElementById("outletContact").value;
			var outletLocation = document.getElementById("outletLocation").value;
			var geography = document.getElementById("geography").value;
			if (outletName === "") {
				   document.getElementById("outletNameError").innerHTML="Please enter Outlet Name.";
			       document.getElementById("outletName").focus();
			       return false;
			   }
			   if (outletType === "") {
				   document.getElementById("outletTypeError").innerHTML="Please select Outlet Type.";
			       document.getElementById("outletType").focus();
			       return false;
			   }
			   if (outletManager === "") {
				   document.getElementById("outletManagerError").innerHTML="Please enter Outlet Manager name.";
			       document.getElementById("outletManager").focus();
			       return false;
			   }
			   if (outletContact === "") {
				   document.getElementById("outletContactError").innerHTML="Please enter Outlet Contact.";
			       document.getElementById("outletContact").focus();
			       return false;
			   }
			   if (outletLocation === "") {
				   document.getElementById("outletLocationError").innerHTML="Please enter Outlet Location.";
			       document.getElementById("outletLocation").focus();
			       return false;
			   }
			   if (geography === "") {
				   document.getElementById("geographyError").innerHTML="Please select Geography.";
			       document.getElementById("geography").focus();
			       return false;
			   }
			   
			$.ajax({
				type: "POST",
			     url:"/addOutletDetail",
				 dataType: 'json',   
			      data: {
							"orgid":orgId,
							"mgrName":outletManager,
							"name":outletName,
							"mgrMobile":outletContact,
							"typeDesc":outletType,
							"location":outletLocation,
							"geocatid":geography,
							"appType":"web",
					 },  		 
			        success:function(data){
			        var data1 = data.data;
					if(data.status==true){
						getOutletDetail();
						document.getElementById("modal-overlay2").style.display = "none";
					    document.getElementById("bs-canvas-right3").classList.remove("show-canvas");
						$('#PaymentSuccessModal').modal('show');
					}else if(data.status==false){
						$('#PaymentSuccessModal').modal('show');
					}
			     },
			     error: function(e){
			         alert('Error: ' + e);
			     }
			});	
			
		}
				/*
async function activateBrand(){
	var createdby = document.getElementById("userMobile").value;
	var orgId = document.getElementById("employerId").value;
	var brandName = document.getElementById("brandName").value;
	const outletCountRadioValue = document.querySelector('input[name="outletCount"]:checked').value;
	var multipleValue = document.getElementById("noOfOutlet").value;
	const salesMode = document.querySelector('input[name="salesMode"]:checked').id;

	let outletCount = outletCountRadioValue;
	if (brandName === "") {
		   document.getElementById("brandNameError").innerHTML="Please enter brand name.";
	       document.getElementById("outletName").focus();
	       return false;
	   }
	if (outletCountRadioValue === "multipleOutlets") {
	    if (multipleValue.trim() === "") {
			document.getElementById("outletCountError").innerHTML="Please enter number of outlets.";
			return false;
	    } else if (isNaN(multipleValue) || Number(multipleValue) <= 0) {
			document.getElementById("outletCountError").innerHTML="Number of outlets must be a positive number.";
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
		         $('#AddVehicleModal').modal('hide');
				 getSupportTicketListList();

				 document.getElementById('bs-canvas-right2').style.right = '-378px';
				 document.getElementById('modal-overlay2').style.display = 'none';
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
*/async function getOutletDetail() {
    var orgId = document.getElementById("employerId").value;

    $.ajax({
        type: "GET",
        url: "/getOutletDetail",
        dataType: "json",
        data: {
            orgid: orgId,
        },
        success: function(response) {
            var outletData = response.data;

            var table = $('#outletDetailTable').DataTable({
                destroy: true,
                responsive: true,
                searching: false,
                bInfo: false,
                paging: false,
                lengthChange: false,
                autoWidth: false,
                pagingType: "full_numbers",
                pageLength: 50,
                buttons: ["copy", "csv", "excel", "pdf", "print", "colvis"],
                language: {
                    emptyTable: 'Currently, no outlets have been added. Click <a class="outlet" onclick="openGeoGraphic()">Add Manually</a> to add your first outlet.'
                },
                aaData: outletData,
                aoColumns: [
                    { mData: "name" },
                    { mData: "typeDesc" },
                    { mData: "location" },
                    { mData: "geocatid" },
                    {
                        mData: null,
                        render: function (data, type, row) {
                            return `${row.mgrName}<br>${row.mgrMobile || ''}`;
                        }
                    },
                    {
                        mData: "id",
                        render: function (data, type, row) {
                            return `
                                <div style="display: flex; gap: 8px; align-items: center;">
                                    <div class="circle green" style="display: flex; align-items: center; justify-content: center; font-size: 12px;">1</div>
                                    <div class="circle yellow" style="display: flex; align-items: center; justify-content: center; font-size: 12px;">2</div>
                                    <div class="circle gray" style="display: flex; align-items: center; justify-content: center; font-size: 12px;">3</div>
                                </div>`;
                        }
                    },
					{
					    mData: "creationdate",
					    render: function (data, type, row) {
					        if (!data) return "";
					        const date = new Date(data);
					        const day = String(date.getDate()).padStart(2, '0');
					        const month = String(date.getMonth() + 1).padStart(2, '0');
					        const year = date.getFullYear();
					        return `${day}/${month}/${year}`;
					    }
					},
                    { mData: "status" }
                ],
                createdRow: function(row, data, dataIndex) {
                    // Optional: Add row-level customization here
                }
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching outlet details:', error);
            alert('Failed to fetch outlet details.');
        }
    });
}

function getGeographicDropdownList() {
    var orgId = document.getElementById("employerId").value;

    $.ajax({
        type: "GET",
        url: "/getBrupiBrandGeoList",
        data: {
            "orgid": orgId,
        },
        dataType: "json",
        success: function(response) {
            console.log(response);
            if (
                response.status &&
                response.data &&
                response.data.length > 0 &&
                response.data[0].geographicLocation
            ) {
                var geoList = response.data[0].geographicLocation;
                var $dropdown = $("#geography");

                $dropdown.empty(); 
                $dropdown.append(
                    $("<option>").val("").text("Geography")
                );

                geoList.forEach(function(location) {
                    $dropdown.append(
                        $("<option>").val(location.id).text(location.name)
                    );
                });
            } else {
                alert("No geography data available.");
            }
        },
        error: function(e) {
            console.error("Error fetching geography data", e);
            alert("Failed to load geographic locations.");
        }
    });
}