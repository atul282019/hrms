function getVehicleDetaillist() {
		    const orgId = document.getElementById('orgId').value; // Get orgId
		    //document.getElementById("signinLoader").style.display = "flex";

		    $.ajax({
		        type: "GET",
		        url: "/getVehicleList",
		        data: { orgId },
		        success: function (data) {
		           // document.getElementById("signinLoader").style.display = "none";
		            const parseddata = JSON.parse(data);
		            console.log("getVehicleDetaillist() data= ",parseddata);
		            var vehicleData = parseddata.data;


		            // Check if vehicleData exist
		            if (vehicleData.length === 0) {
		                tableBody.append(`<tr><td colspan="16" class="text-center">No Requests Found</td></tr>`);
		                return;
		            }

		            

		            
		        },
		        error: function (e) {
		            alert('Error: ' + e.responseText);
		        }
		    });
		}

		function saveChanges() {
			const orgId = document.getElementById('orgId').value;
			const createdBy = document.getElementById('createdBy').value;
				    const ownership = document.getElementById('ownershipModel').options[
				        document.getElementById('ownershipModel').selectedIndex
				    ].text;

				    const driverName1 = document.getElementById('driverName1').value;

				    const assignmentType = document.getElementById('assignmentType').options[
				        document.getElementById('assignmentType').selectedIndex
				    ].text;

				    const clientName = document.getElementById('clientName').value;
				    const driverName2 = document.getElementById('driverName2').value;

				    const timePeriod = document.getElementById('timePeriod').options[
				        document.getElementById('timePeriod').selectedIndex
				    ].text;

				   

				    if (!ownership || !assignmentType || !driverName2 || !timePeriod) {
				        alert("Please fill in all required fields.");
				        return;
				    }

				    $.ajax({
				        type: "POST",
				        url: "/addVehicleDetails",
				        data:
						{   "orgId":orgId,
							"ownership_type":ownership,
							"ownershipDriver":driverName1,
							"assignmentType":assignmentType,
							"assignmentDriver":driverName2,
							"clientName":clientName,
							"selectedTimeperiod":timePeriod,
							"createdBy":createdBy
						},
				        success: function (data) {
				            const parsedResponse = JSON.parse(data);
				            console.log("saveChanges() response =", parsedResponse);

				            const modal = new bootstrap.Modal(document.getElementById('ModalConfirm1'));
				            modal.show();
				            setTimeout(() => {
				                window.location.href = "/editvehicle";
				            }, 1600);
				        },
				        error: function (e) {
				            alert('Error: ' + e.responseText);
				        }
				    });
				}		