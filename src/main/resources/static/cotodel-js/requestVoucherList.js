
function getSavedVoucherList() {
		    const managerId = document.getElementById('empId').value;
			document.getElementById("signinLoader").style.display="flex";
			$.ajax({
				type: "GET",
		        url: "/erupiVoucherRequestByMngId",
		        data: { managerId },
		        success: function(data) {
					document.getElementById("signinLoader").style.display="none";
		            const parseddata = JSON.parse(data);
		            
					var vouchers=parseddata.data;
					console.log("getSavedVoucherList()",vouchers);
				
					var table = $('#reimbursementTable').DataTable( {
			          destroy: true,	
				     "responsive": true, searching: false,bInfo: false, paging: false,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
			         "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"],
			         "language": {"emptyTable": "No Data available"  },
			        
			         "aaData": vouchers,
			  		  "aoColumns": [ 
					   {
			             "mData": "id", "render": function (vouchers, type, row) {
							 return ' <div class="table-check"><input type="hidden" value="'+vouchers+'" id="revokeId" name="revokeId" ></div>';
			             }},
			            { "mData": "name"}, 
						{ "mData": "mobile"},   
			           // { "mData": "employeeId"},   
					    { "mData": "voucherType"},
					 //   { "mData": "voucherSubType"},   
					 	   
						{ "mData": "amount"},    
						{ 
						  "mData": "creationDate",
						  "render": function(vouchers, type, row) {
						    if (!vouchers) return '';
						    const date = new Date(vouchers);
						    const yyyy = date.getFullYear();
						    const mm = String(date.getMonth() + 1).padStart(2, '0');
						    const dd = String(date.getDate()).padStart(2, '0');
						    return `${yyyy}-${mm}-${dd}`;
						  }
						},
						{ "mData": "remarks"},    
						{ "mData": "statusMessage"},         
						{ "mData": "statusMessage", "render": function (creationDate, type, row) {
						    if (row.statusMessage !== "Requested" || row.statusMessage !== "Requested") {
						        return '';
						    } else {
						        return `
						            <td>
						                <div class="dropdown no-arrow ml-2">
						                    <a class="dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						                        <i class="fas fa-ellipsis-v fa-sm"></i>
						                    </a>
						                    <br>
						                    <div class="dropdown-menu dropdown-menu-right shadow" aria-labelledby="userDropdown">
						                        <button 
						                            class="dropdown-item py-2" 
						                            value="${vouchers}" 
						                            id="btnRevoke" 
						                            onclick="openRevokeDialog('${encodeURIComponent(JSON.stringify(row))}')">
						                            Approve | Reject
						                        </button>
						                       
						                    </div>
						                </div>
						            </td>`;
								}
						    }},
					 	],
			  		});		
					
				},
				error: function(e) {
					alert('Failed to fetch JSON data' + e);
				}
			});
		}	
	
		function approveReject(){
			
				var employerId = document.getElementById("employerId").value;
				var employeeId = document.getElementById("empId").value;
				var revokeId = document.getElementById("revokeId").value;
				var username = document.getElementById("Name").value;
				 document.getElementById("signinLoader").style.display="flex";
				  	$.ajax({
				 	type: "POST",
				 	url:"/approveRejectVoucherRequest",
				     data: {
				 			"id": revokeId,
							"employerId": employerId,
							"employeeId":employeeId,
							"loginuser": username,
							"status": "Approved",
							"rejecctRemark":""
				    		 },
				    		  beforeSend : function(xhr) {
				 			//xhr.setRequestHeader(header, token);
				 			},
				         success: function(data){
							newData = data;
							var data1 = jQuery.parseJSON(newData);
							$('#RevokeUPIVoucherModal').hide();
							document.getElementById("revokeId").value="";
							document.getElementById("signinLoader").style.display="none";
							
							if(data1.status==true){
							$('#revokeUPIVcAuthenticate').show();				
							$('#revokeModal').hide();
							}
							else{
								$('#revokeUPIVcAuthenticateFail').show();	
								$('#revokeModal').hide();	
							}
							getSavedVoucherList();
					        },
					      error: function(e){
							$('#revokeUPIVcAuthenticateFail').show();
							$('#revokeModal').hide();		
					        alert('Error: ' + e);
					      }
				 }); 
				
		}
		
			function rejectVoucher(){
											
				var employerId = document.getElementById("employerId").value;
				var employeeId = document.getElementById("empId").value;
				var revokeId = document.getElementById("revokeId").value;
				var username = document.getElementById("Name").value;
				var rejectRemark = document.getElementById("rejectRemark").value;
				if (rejectRemark === "") {
			        document.getElementById("rejectRemarkerror").innerHTML = "Please Enter Reject Remark";
			        voucherTypeDropdown.focus();
			        return false;
			    } else {
			        document.getElementById("rejectRemarkerror").innerHTML = "";
			    }
				
				 document.getElementById("signinLoader").style.display="flex";
				  	$.ajax({
				 	type: "POST",
				 	url:"/approveRejectVoucherRequest",
				     data: {
				 			"id": revokeId,
							"employerId": employerId,
							"employeeId":employeeId,
							"loginuser": username,
							"status": "Rejected",
							"rejecctRemark":rejectRemark
				    		 },
				    		  beforeSend : function(xhr) {
				 			//xhr.setRequestHeader(header, token);
				 			},
				         success: function(data){
							newData = data;
							var data1 = jQuery.parseJSON(newData);
							$('#RevokeUPIVoucherModal').hide();
							document.getElementById("revokeId").value="";
							document.getElementById("signinLoader").style.display="none";
							
							if(data1.status==true){
							$('#revokeUPIVcAuthenticateReject').show();				
							$('#revokeModal').hide();
							}
							else{
								$('#revokeUPIVcAuthenticateFailReject').show();
								$('#revokeModal').hide();		
							}
							getSavedVoucherList();
					        },
					      error: function(e){
							$('#revokeUPIVcAuthenticateFailReject').show();		
							$('#revokeModal').hide();
					          alert('Error: ' + e);
					      }
				 }); 
				
		}
				