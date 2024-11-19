
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
			    { "mData": "merchanttxnId"},   
				{ "mData": "purposeCode"},  
				{ "mData": "mcc"}, 
				{ "mData": "creationDate"},
				{ "mData": "creationDate"},
				{ "mData": "type"},      
				{ "mData": "redemtionType"},
				{ "mData": "id", "render": function (data1, type, row) {
				 return '<td>  <button class="btn-attach" id="btnRevoke"  onclick="revoke(this)"> Revoke </button> </td>';
				 }}, 
				 { "mData": "id", "render": function (data1, type, row) {
				 	return '<td>  <button class="btn-attach" id="btnSend" onclick="sendsms(this)"> Send SMS  </button> </td>';
				  }}, 
    		 	],
    		 	
      		}).buttons().container().appendTo('#issueVoucherTable_wrapper .col-md-6:eq(0)');		
			
		},
		error: function(e) {
			alert('Failed to fetch JSON data' + e);
		}
	});
}


function revoke(value){
		var row = jQuery(value).closest('tr');
		 var  id = $(row).find("input[name='revoke']").val();
		 alert("Are you sure you want to revoke");
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