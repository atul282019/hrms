function getBankListWithVocher() {
    const employerId = document.getElementById("employerId").value;

    $.ajax({
        type: "POST",
        url: "/voucherCreateBankList",
        data: { orgId: employerId },
        beforeSend: function (xhr) {
            // You can add headers if needed
        },
        success: function (data) {
            try {
                const parsedData = jQuery.parseJSON(data);
                const dataList = document.getElementById('bankListData');
                const totalIssueCount = document.getElementById('totalIssueCount');
                const totalIssueAmount = document.getElementById('totalIssueAmount');
                const redemVCount = document.getElementById('redemVCount');
                const redemVAmount = document.getElementById('redemVAmount');
                const expRevokeCount = document.getElementById('expRevokeCount');
                const expRevokeAmount = document.getElementById('expRevokeAmount');
                const activeCount = document.getElementById('activeCount');
                const activeAmount = document.getElementById('activeAmount');

                // Clear previous list
                dataList.innerHTML = "";

                parsedData.data.forEach((item) => {
                    const div = document.createElement('div');
                    div.className = 'left-activeupivcmarked';
                    div.innerHTML = `
                        <div class="img-bank">
                            <img src="data:image/png;base64,${item.bankLogo}" width="18" height="18" alt="Bank Logo">
                        </div>
                        <span>${item.bankName}</span>
                        <input type="hidden" value="${item.bankAccount}" />
                        <label>${item.bankAccountMask || ''}</label>
                    `;

                    // Set the default active class for null bank account
                    if (item.bankAccount === null) {
                        div.classList.add('active');

                        // Fetch details for null account on page load
                        $.ajax({
                            type: "POST",
                            url: "/voucherCreateSummaryDetailByAccount",
                            data: {
                                "orgId": employerId,
                                "accNumber": null,
                            },
                            success: function (data) {
                                const jsonData = jQuery.parseJSON(data);
                                totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
                                totalIssueAmount.textContent = jsonData.issueDetail.totalIssueAmount || "0";
                                redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
                                redemVAmount.textContent = jsonData.issueDetail.redemVAmount || "0";
                                expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
                                expRevokeAmount.textContent = jsonData.issueDetail.expRevokeAmount || "0";
                                activeCount.textContent = jsonData.issueDetail.activeCount || "0";
                                activeAmount.textContent = jsonData.issueDetail.activeAmount || "0";
                            },
                            error: function (e) {
                                console.error('Error fetching default account details:', e);
                            },
                        });
                    }

                    // Add a click event listener to the div
                    div.addEventListener('click', () => {
                        const activeDiv = dataList.querySelector('.active');
                        if (activeDiv) activeDiv.classList.remove('active');

                        div.classList.add('active');

                        const bankAccount = item.bankAccount;
                        
                            $.ajax({
                                type: "POST",
                                url: "/voucherCreateSummaryDetailByAccount",
                                data: {
                                    "orgId": employerId,
                                    "accNumber": bankAccount,
                                },
                                success: function (data) {
                                    const jsonData = jQuery.parseJSON(data);
                                    totalIssueCount.textContent = jsonData.issueDetail.totalIssueCount || "0";
                                    totalIssueAmount.textContent = jsonData.issueDetail.totalIssueAmount || "0";
                                    redemVCount.textContent = jsonData.issueDetail.redemVCount || "0";
                                    redemVAmount.textContent = jsonData.issueDetail.redemVAmount || "0";
                                    expRevokeCount.textContent = jsonData.issueDetail.expRevokeCount || "0";
                                    expRevokeAmount.textContent = jsonData.issueDetail.expRevokeAmount || "0";
                                    activeCount.textContent = jsonData.issueDetail.activeCount || "0";
                                    activeAmount.textContent = jsonData.issueDetail.activeAmount || "0";
                                },
                                error: function (e) {
                                    console.error('Error fetching account details:', e);
                                },
                            });
                       
                    });

                    dataList.appendChild(div);
                });

                // Update totals with parsed data
                totalIssueCount.textContent = parsedData.issueDetail.totalIssueCount || "0";
                totalIssueAmount.textContent = parsedData.issueDetail.totalIssueAmount || "0";
                redemVCount.textContent = parsedData.issueDetail.redemVCount || "0";
                redemVAmount.textContent = parsedData.issueDetail.redemVAmount || "0";
                expRevokeCount.textContent = parsedData.issueDetail.expRevokeCount || "0";
                expRevokeAmount.textContent = parsedData.issueDetail.expRevokeAmount || "0";
                activeCount.textContent = parsedData.issueDetail.activeCount || "0";
                activeAmount.textContent = parsedData.issueDetail.activeAmount || "0";
            } catch (error) {
                console.error('Error parsing response:', error);
                alert('Failed to process the response data.');
            }
        },
        error: function (error) {
            console.error('Error in AJAX request:', error);
            alert('Failed to fetch bank list. Please try again.');
        },
    });
}

function  getLinkedBankDetail(){
	
    //document.getElementById("signinLoader").style.display="flex";
 	var employerid = document.getElementById("employerId").value;
 	$.ajax({
	type: "POST",
	url:"/getErupiLinkBankAccountDetail",
       data: {
			"orgId": employerid
      		 },
      		  beforeSend : function(xhr) {
			//xhr.setRequestHeader(header, token);
			},
			   success: function(data){
	          
				var obj = jQuery.parseJSON( data );
				 obj = obj.data;
				 
				if (obj && obj.length > 0) {
				     // Show the div with data
					 document.getElementById("nolinkBankAccount").style.display="none";
	 				 document.getElementById("linkedAccount").style.display="block";
	 				 const issueManually = document.getElementById('issueManually');
	 				 const issueBulk = document.getElementById('issueBulk');
	 				 issueManually.disabled =false;
	 				 issueBulk.disabled =false;
					 
				   } else {
					document.getElementById("nolinkBankAccount").style.display="block";
					 document.getElementById("linkedAccount").style.display="none";
				}		
          },
        error: function(e){
            alert('Error: ' + e);
        }
   }); 
			
}



function getIssueVoucherList(){
	document.getElementById("signinLoader").style.display="flex";
	var employerid = document.getElementById("employerId").value;
	$.ajax({
		type: "POST",
		url: "/getIssueVoucherList",
		data: {
			//"employeeId": employerid,
			"orgId": employerid
		},
		success: function(data) {
			newData = data;
			var data1 = jQuery.parseJSON(newData);
			var data2 = data1.data;
			 //console.log(data2);
			document.getElementById("signinLoader").style.display="none";
			
			var table = $('#issueVoucherTable').DataTable( {
	          destroy: true,	
			 // "dom": 'rtip',
			 //dom: 'Bfrtip',
		     "responsive": true, searching: true,bInfo: false, paging: true,"lengthChange": true, "autoWidth": false,"pagingType": "full_numbers","pageLength": 50,
             "buttons": ["csv", "excel"],
             "language": {"emptyTable": "UPI Vouchers section is currently not enabled. Please link your bank account to enable this section."  },
	         "aaData": data2,
      		  "aoColumns": [ 
				{ "mData": "id", "render": function (data2, type, row) {
				   return ' <div class="table-check"><input type="hidden" class="checkBoxClass" value="'+data2+'" id="'+data2+'" name="revoke"></div>';
			    }}, 
				{ "mData": "name"},
                { "mData": "mobile"},   
			   
			   // { "mData": "merchanttxnId"},   
				{ "mData": "purposeDesc"},  
				//{ "mData": "mcc"}, 
				{ "mData": "amount"},
				{ "mData": "creationDate"},
				{ "mData": "expDate"},
				{ "mData": "redemtionType"},
				{ "mData": "type"},      
				
				{ "mData": "id", "render": function (data2, type, row) {
					
				if(row.type==="Revoke" || row.type==="fail" || row.type==="Redeem"){
					return '';
				}
				else{
					//return '<td>  <button class="btn-revoke" data-toggle="modal" data-target="#tableRevoke" value="'+data2+'" id="btnRevoke"  onclick="revoke(this)"> Revoke </button> </td>';
					return '<td>  <button class="btn-revoke"  value="'+data2+'" id="btnRevoke" onclick="openRevokeDialog(this)" > Revoke </button> </td>';
														
					}													     								
			}}, 
			 { "mData": "id", "render": function (data2, type, row) {
			 if(row.type==="Revoke" || row.type==="fail" || row.type==="Redeem"){
					return '';
				}
				else{
					//return '<td>  <button class="btn-attach" data-toggle="modal" data-target="#tableSendSms" value="'+data2+'" id="btnSend" onclick="sendsms(this)"> Send SMS  </button> </td>';
					return '<td>  <button class="btn-attach"  value="'+data2+'" id="btnSend" onclick="openSendDialog(this)"> Send SMS  </button> </td>';
																					
				 }
				 //return '<td>  <button class="btn-attach"  value="'+data2+'" id="btnSend" onclick="openSendDialog(this)"> Send SMS  </button> </td>';
																
				 }}, 
    		 	],
				
				createdRow: function (row, data2, dataIndex) 
                    {
                   
                 	var voucherDesc = data2.voucherDesc;
                 	var type = data2.type;
					
                     if(voucherDesc=="Meal")
                     {
					 var imgTag = '<img src="img/food.svg" alt="" class="mr-2">'+voucherDesc;
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(voucherDesc=="Miscellaneous")
                     {
					 var imgTag = ' <img src="img/Miscellaneous.svg" alt="" class="mr-2">'+voucherDesc;
 					
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     
                     if(voucherDesc=="Food")
                     {
						 var imgTag = ' <img src="img/food.svg" alt="" class="mr-2">'+voucherDesc;
	 					 $(row).find('td:eq(1)').html(imgTag);
                     }
                     
                     if(voucherDesc=="Cash Advance")
                     {
					 var imgTag = '<img src="img/cash.svg" alt="" class="mr-2">'+voucherDesc;
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(voucherDesc=="Travel")
                     {
					 var imgTag = '<img src="img/Travel.svg" alt="" class="mr-2">'+voucherDesc;
 					
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     if(voucherDesc=="Stay")
                     {
					 var imgTag = '<img src="img/hotel.svg" alt="" class="mr-2">'+voucherDesc;
                      $(row).find('td:eq(1)').html(imgTag);
                     }
                     
                     if(type=="fail")
                     {
						var imgTag = ' <img src="img/table-fail.svg" alt="" class="mr-2">';
						 $(row).find('td:eq(8)').html(imgTag);
                    //  $(row).find('td:eq(10)').addClass('tdactive');
                     }
                     if(type=="Created")
                     {
						var imgTag = ' <img src="img/table-create.svg" alt="" class="mr-2">';
						 $(row).find('td:eq(8)').html(imgTag);
                     // $(row).find('td:eq(10)').addClass('tdsubmitted');
                     }
					 if(type=="Revoke")
                     {
				
						var imgTag = ' <img src="img/Revoke.svg" alt="" class="mr-2">';
					    $(row).find('td:eq(9)').html(imgTag);
					 
                     }
					 if(type=="Redeem")
                     {
						var imgTag = ' <img src="img/Redeem.svg" alt="" class="mr-2">';
						 $(row).find('td:eq(8)').html(imgTag);
                     }
                  }
			});
      		//}).buttons().container().appendTo('#issueVoucherTable_wrapper .col-md-6:eq(0)');		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}
function colosethis(){
	
	document.getElementById("tableRevoke").style.display="none";
}

function sendsms(value){
		 var row = jQuery(value).closest('tr');
		 var  id = $(row).find("input[name='revoke']").val();
		 document.getElementById("signinLoader").style.display="flex";
		  	$.ajax({
		 	type: "POST",
		 	url:"/erupiVoucheSmsSend",
		     data: {
		 			"id": value
		    		 },
		    		  beforeSend : function(xhr) {
		 			//xhr.setRequestHeader(header, token);
		 			},
		         success: function(data){
				 document.getElementById("signinLoader").style.display="none";
		        },
		      error: function(e){
		          alert('Error: ' + e);
		      }
		 }); 
		 
		 $(window).on('click', function(event) {
		       if (event.target.id === 'otpModal') {
		         $('#otpModal').fadeOut();
		       }
		     });
}


function revoke(value){
		 var row = jQuery(value).closest('tr');
		 var  id = $(row).find("input[name='revoke']").val();
		 document.getElementById("signinLoader").style.display="flex";
		  	$.ajax({
		 	type: "POST",
		 	url:"/revokeCreatedVoucher",
		     data: {
		 			"id": value
		    		 },
		    		  beforeSend : function(xhr) {
		 			//xhr.setRequestHeader(header, token);
		 			},
		         success: function(data){
		         newData = data;
		         //console.log(newData);
				 document.getElementById("signinLoader").style.display="none";
		         var data1 = jQuery.parseJSON( newData );
		 		var data2 = data1.data;
				getIssueVoucherList();
		        },
		      error: function(e){
		          alert('Error: ' + e);
		      }
		 }); 
		
}
function sortTable(columnIndex) {
           const rows = Array.from(tableBody.rows);
           const sortedRows = rows.sort((a, b) => {
               const cellA = a.cells[columnIndex].innerText;
               const cellB = b.cells[columnIndex].innerText;
               if (!isNaN(cellA) && !isNaN(cellB)) {
                   return Number(cellA) - Number(cellB);
               }
               return cellA.localeCompare(cellB);
           });

           tableBody.innerHTML = "";
           sortedRows.forEach(row => tableBody.appendChild(row));
       }