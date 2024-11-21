
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
             "language": {"emptyTable": "No Data available"  },
	         "aaData": data2,
      		  "aoColumns": [ 
				{ "mData": "id", "render": function (data2, type, row) {
				   return ' <div class="table-check"><input type="hidden" class="checkBoxClass" value="'+data2+'" id="'+data2+'" name="revoke"></div>';
			    }}, 
				{ "mData": "voucherDesc"},
                { "mData": "name"},   
                { "mData": "mobile"},   
			    { "mData": "amount"},
			   // { "mData": "merchanttxnId"},   
				{ "mData": "purposeCode"},  
				//{ "mData": "mcc"}, 
				{ "mData": "creationDate"},
				{ "mData": "expDate"},
				{ "mData": "redemtionType"},
				{ "mData": "type"},      
				
				{ "mData": "type", "render": function (data2, type, row) {
					
				if(row.type==="Revoke" || row.type==="fail"){
					return '';
				}
				else{
					return '<td>  <button class="btn-revoke" data-toggle="modal" data-target="#tableRevoke" value="'+data2+'" id="btnRevoke"  onclick="revoke(this)"> Revoke </button> </td>';
										
					}
							     
								
			}}, 
			 { "mData": "type", "render": function (data2, type, row) {
			 if(row.type==="Revoke" || row.type==="fail"){
					return '';
				}
				else{
					return '<td>  <button class="btn-attach" data-toggle="modal" data-target="#tableSendSms" value="'+data2+'" id="btnSend" onclick="sendsms(this)"> Send SMS  </button> </td>';
																
				}
												
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
								                     if(type=="Create")
								                     {
														var imgTag = ' <img src="img/table-create.svg" alt="" class="mr-2">';
														 $(row).find('td:eq(9)').html(imgTag);
								                     // $(row).find('td:eq(10)').addClass('tdsubmitted');
								                     }
													 if(type=="Revoke")
					 			                     {
												
														var imgTag = ' <img src="img/table-revoke.svg" alt="" class="mr-2">';
													    $(row).find('td:eq(9)').html(imgTag);
													 
					 			                     }
													 if(type=="Reedeem")
					  			                     {
														var imgTag = ' <img src="img/table-revoke.svg" alt="" class="mr-2">';
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

function sendsms(value){
		 var row = jQuery(value).closest('tr');
		 var  id = $(row).find("input[name='revoke']").val();
		 alert("Are you sure you want to send create sms again");
		  	$.ajax({
		 	type: "POST",
		 	url:"/erupiVoucheSmsSend",
		     data: {
		 			"id": id
		    		 },
		    		  beforeSend : function(xhr) {
		 			//xhr.setRequestHeader(header, token);
		 			},
		         success: function(data){
		         newData = data;
		         //console.log(newData);
		         var data1 = jQuery.parseJSON( newData );
		 	   	 var data2 = data1.data;
				   alert("SMS send to beneficiary mobile number");
			/*	   var modal = document.getElementById("ModalExpensesSubmitted");
				   	 		    modal.style.display = "flex";
				   				modal.removeClass('fade');
				   				modal.addClass('fadeIn');*/
		        },
		      error: function(e){
		          alert('Error: ' + e);
		      }
		 }); 
		 	
}

function revoke(value){
		 var row = jQuery(value).closest('tr');
		 var  id = $(row).find("input[name='revoke']").val();
	     //alert("Are you sure you want to revoke");
		 var modal = document.getElementById("revokeConfirmation");
		 modal.style.display = "block";
		  	$.ajax({
		 	type: "POST",
		 	url:"/revokeCreatedVoucher",
		     data: {
		 			"id": id
		    		 },
		    		  beforeSend : function(xhr) {
		 			//xhr.setRequestHeader(header, token);
		 			},
		         success: function(data){
		         newData = data;
		         //console.log(newData);
		         var data1 = jQuery.parseJSON( newData );
		 		var data2 = data1.data;
				getIssueVoucherList();
		 		/*if(data2.fileType =="application/pdf"){
		 		
		 		
		 		}
		 		else{
		 			
		 		}*/
		 			//var modal = document.getElementById("ModalSubmitExpenseReimbursementView");
		 		  //  modal.style.display = "block";
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