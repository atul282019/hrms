
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
				{ "mData": "mcc"}, 
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
						 $(row).find('td:eq(9)').html(imgTag);
                    //  $(row).find('td:eq(10)').addClass('tdactive');
                     }
                     if(type=="Created")
                     {
						var imgTag = ' <img src="img/table-create.svg" alt="" class="mr-2">';
						 $(row).find('td:eq(9)').html(imgTag);
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
						 $(row).find('td:eq(9)').html(imgTag);
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